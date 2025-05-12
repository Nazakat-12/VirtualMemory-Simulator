package our_os_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FileInput {

	public ArrayList<Process> readConfigurationFile() {
		File file1 = new File("configurationfile1.txt");
		try {
			Scanner input = new Scanner(file1);
			int pointer = 0, numberofprocesses = 0, numberofframes = 0, minnumberofframes = 0;
			ArrayList<Process> processes = new ArrayList<>();

			while (input.hasNextLine()) {
				String line = input.nextLine();
				if (pointer == 0) {
					numberofprocesses = Integer.parseInt(line); // number of processes
				} else if (pointer == 1) {
					numberofframes = Integer.parseInt(line); // size of physical memory in frames = number of frames
				} else if (pointer == 2) {
					minnumberofframes = Integer.parseInt(line); // minimum frames per process = minimum number of pages
																// per process
				} else { // a line for each process - N lines total
					int pid = 0, start = 0, duration = 0, size = 0;
					ArrayList<Integer> list1 = new ArrayList<Integer>();
					String tokens[] = line.split(" "); // array of tokens of each process line
					// System.out.println(line);
					int flag2 = 0, i = 0;
					Process p = new Process();
					for (String number : tokens) {

						if (flag2 == 0) {
							pid = Integer.parseInt(number);
							// System.out.print("for process " + pid);
							p.setProcessID(pid);
						} else if (flag2 == 1) {
							start = Integer.parseInt(number);
							// System.out.print(", start time = " + start);
							p.setArrivalTime(start);
						} else if (flag2 == 2) {
							duration = Integer.parseInt(number);
							// System.out.print(", duration = " + duration);
							p.setDuration(duration);
						} else if (flag2 == 3) {
							size = Integer.parseInt(number);
							// System.out.print(", size = " + size + "\n");
							p.setSize(size);
						} else {
							String[] address = new String[size]; // number of addresses equals the size of process =
							// number of the page
							address[i] = number;
							int[] pageNumber = new int[size];
							// to remove the lower 12 bits from the address and get the corresponding page
							// number
							pageNumber[i] = (int) (Integer.parseInt(address[i].trim(), 16) / Math.pow(2, 12));
							list1.add(pageNumber[i]);
							// System.out.println("address" + i + "= " + address[i] + ", page number = " +
							// pageNumber[i]);
							i++;
						}
						flag2++;
					}
					int[] pages = new int[size];
					for (int i2 = 0; i2 < list1.size(); i2++) {
						// System.out.println(list1.get(i2) + " ");
						pages[i2] = list1.get(i2);
						// System.out.println("gggggggggg" + pages[i2] + " ");
					}
					p.setPageReferences(pages);
					/*
					 * System.out.println("id = " + p.getProcessID()); System.out.println("start = "
					 * + p.getArrivalTime()); System.out.println("dur = " + p.getDuration());
					 * System.out.println("size = " + p.getSize()); System.out.println("ref = ");
					 * System.out.println(Arrays.toString(p.getPageReferences()));
					 */
					processes.add(p);
					// System.out.println("*********************************************************");

				}
				pointer++;
			}
			System.out.println("total informations : ");
			System.out.println("number of processes = " + numberofprocesses);
			// System.out.println("number of frames(size of ram) = numberofframes = " +
			// numberofframes);
			System.out.println("minumum number of frames per process = " + minnumberofframes);

			input.close();
			return processes;
		} catch (FileNotFoundException e) {
			System.out.println(e);
			System.out.println(e.getMessage());
			System.out.println("ERROR : FILE NOT FOUND :( ");
			return null;
		}
	}

	public ArrayList<Process> readGeneratedFile() {

		// create a file object
		File file = new File("generatedFile.txt");
		try {
			// create a new file with name specified by the file object
			boolean value = file.createNewFile();
			if (value)
				System.out.println("New Java File is created.");
			else
				System.out.println("The file already exists.");
		} catch (Exception e) {
			e.getStackTrace();
		}

		// generate N = processes number
		int N = 0;
		int min_N = 4, max_N = 10;
		N = (int) Math.floor(Math.random() * (max_N - min_N + 1) + min_N);
		// System.out.println("N = " + N);

		// generate M = frames number = size of RAM
		int M = 0;
		ArrayList<Integer> list = new ArrayList<Integer>(); // list that will contain RAM sizes which could be
															// 40,200,500
		list.add(40);
		list.add(200);
		list.add(500);
		Random rand = new Random();
		M = list.get(rand.nextInt(list.size()));// selects a random value from RAM possible values in the list
		// System.out.println("M = " + M);

		// generate S = minimum number of frames in each process
		int S = 0;
		int min_S = 15, max_S = 20;
		S = (int) Math.floor(Math.random() * (max_S - min_S + 1) + min_S);
		// System.out.println("S = " + S);

		int[] pid = new int[N];
		double[] start = new double[N];
		double[] duration = new double[N];
		int[] size = new int[N];

		// write to the file
		try {
			FileWriter myWriter = new FileWriter("generatedFile.txt");
			myWriter.write(N + "\n");
			myWriter.write(M + "\n");
			myWriter.write(S + "\n");

			for (int j = 0; j < N; j++) {
				// generate pid, it could be from 0 up to N-1(N = processes number)
				pid[j] = j;
				// System.out.println("pid = " + pid[j]);

				// note: edit this for an ascending genration (from smaller time to bigger time)
				// generate start time, it could be from 0 up to 3000
				int min_time = 0, max_time = 100;
				// Random rand = new Random();
				if (Double.valueOf(max_time - min_time).isInfinite() == false)
					start[j] = min_time + ((max_time - min_time) * rand.nextDouble());
				DecimalFormat df = new DecimalFormat("#.#"); // to approximate the generated time to one Decimal point
				start[j] = Double.parseDouble(df.format(start[j]));
				// System.out.println("time = " + start[j]);

				// generate duration, it could be from 1hour up to 10hours
				int min_duration = 1, max_duration = 10;
				// Random rand = new Random();
				if (Double.valueOf(max_duration - min_duration).isInfinite() == false)
					duration[j] = min_duration + ((max_duration - min_duration) * rand.nextDouble());
				// DecimalFormat df = new DecimalFormat("#.#"); // to approximate the generated
				// duration to one Decimal point
				duration[j] = Double.parseDouble(df.format(duration[j]));
				// System.out.println("duration = " + duration[j]);

				// note: edit this for an big duration large references , for small small
				// generate process size, it must be bigger than (S = the minimum frames per
				// process)
				int min_size = S, max_size = 50;
				size[j] = (int) Math.floor(Math.random() * (max_size - min_size + 1) + min_size);
				// System.out.println("size = " + size[j]);

				myWriter.write(pid[j] + " " + (int) start[j] + " " + (int) duration[j] + " " + size[j] + " ");
				// generate memory addresses, it must be bigger than (S=the minimum frames per
				// process)
				int[] page_references = new int[size[j]];
				String[] address = new String[size[j]]; // address = memory_references = number of addresses equals the
														// size
														// of
														// process = number of pages
				for (int i = 0; i < size[j]; i++) {
					page_references[i] = rand.nextInt(size[j] - 1);
					// System.out.print("page reference." + i + " = " + page_references[i]);
					int dig1 = (int) Math.floor(Math.random() * (15 - 0 + 1) + 0);
					int dig2 = (int) Math.floor(Math.random() * (15 - 0 + 1) + 0);
					int dig3 = (int) Math.floor(Math.random() * (15 - 0 + 1) + 0);
					address[i] = Integer.toHexString(page_references[i]) + Integer.toHexString(dig1)
							+ Integer.toHexString(dig2) + Integer.toHexString(dig3);
					// System.out.println(", address." + i + " = " + address[i]);
					myWriter.write(address[i] + " ");
				}
				myWriter.write("\n");
			}
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		// reading data
		File file1 = new File("generatedFile.txt");
		try {
			Scanner input = new Scanner(file1);
			int pointer = 0, numberofprocesses = 0, numberofframes = 0, minnumberofframes = 0;
			ArrayList<Process> processes = new ArrayList<>();

			while (input.hasNextLine()) {
				String line = input.nextLine();
				if (pointer == 0) {
					numberofprocesses = Integer.parseInt(line); // number of processes
				} else if (pointer == 1) {
					numberofframes = Integer.parseInt(line); // size of physical memory in frames = number of frames
				} else if (pointer == 2) {
					minnumberofframes = Integer.parseInt(line); // minimum frames per process = minimum number of pages
																// per process
				} else { // a line for each process - N lines total
					int pid2 = 0, start2 = 0, duration2 = 0, size2 = 0;
					ArrayList<Integer> list1 = new ArrayList<Integer>();
					String tokens[] = line.split(" "); // array of tokens of each process line
					// System.out.println(line);
					int flag2 = 0, i = 0;
					Process p = new Process();
					for (String number : tokens) {

						if (flag2 == 0) {
							pid2 = Integer.parseInt(number);
							// System.out.print("for process " + pid2);
							p.setProcessID(pid2);
						} else if (flag2 == 1) {
							start2 = Integer.parseInt(number);
							// System.out.print(", start time = " + start2);
							p.setArrivalTime(start2);
						} else if (flag2 == 2) {
							duration2 = Integer.parseInt(number);
							// System.out.print(", duration = " + duration2);
							p.setDuration(duration2);
						} else if (flag2 == 3) {
							size2 = Integer.parseInt(number);
							// System.out.print(", size = " + size2 + "\n");
							p.setSize(size2);
						} else {
							String[] address = new String[size2]; // number of addresses equals the size of process =
							// number of the page
							address[i] = number;
							int[] pageNumber = new int[size2];
							// to remove the lower 12 bits from the address and get the corresponding page
							// number
							pageNumber[i] = (int) (Integer.parseInt(address[i].trim(), 16) / Math.pow(2, 12));
							list1.add(pageNumber[i]);
							// System.out.println("address" + i + "= " + address[i] + ", page number = " +
							// pageNumber[i]);
							i++;
						}
						flag2++;
					}
					int[] pages = new int[size2];
					for (int i2 = 0; i2 < list1.size(); i2++) {
						// System.out.println(list1.get(i2) + " ");
						pages[i2] = list1.get(i2);
						// System.out.println("gggggggggg" + pages[i2] + " ");
					}
					p.setPageReferences(pages);
					/*
					 * System.out.println("id = " + p.getProcessID()); System.out.println("start = "
					 * + p.getArrivalTime()); System.out.println("dur = " + p.getDuration());
					 * System.out.println("size = " + p.getSize()); System.out.println("ref = ");
					 * System.out.println(Arrays.toString(p.getPageReferences()));
					 */
					processes.add(p);
				}
				pointer++;
			}
			System.out.println("total informations : ");
			System.out.println("number of processes = " + numberofprocesses);
			// System.out.println("number of frames(size of ram) = numberofframes = " +
			// numberofframes);
			System.out.println("minumum number of frames per process = " + minnumberofframes);

			input.close();
			return processes;
		} catch (FileNotFoundException e) {
			System.out.println(e);
			System.out.println(e.getMessage());
			System.out.println("ERROR : FILE NOT FOUND :( ");
			return null;
		}

	}

}
