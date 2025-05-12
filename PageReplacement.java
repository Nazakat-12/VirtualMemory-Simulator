package our_os_project;

public class PageReplacement {
	final static int cyclesForDisk = 300;
	final static int cyclesForMemoryAccess = 1;
	static int cycles = 0;

	public static int getCycles() {
		return cycles;
	}
	public static void setCycles( int cycles ) {
		PageReplacement.cycles=cycles;
	}
	
	public static int secondChanceFIFO(int num_f, int[] pages) {
		int pgf = 0;// number of page faults
		int page_num = pages.length;
		int page;// to represent the page
		boolean flag;// to check if page is in memory
		int pages_per_process[] = new int[page_num]; // Create array to hold page numbers
		int frames[] = new int[num_f];// frames array
		boolean second_chance[] = new boolean[num_f];// Create second chance array to represent the reference bit.
		int frame_ptr = 0;

		for (int i = 0; i < num_f; i++) {// initilize frames
			frames[i] = -1;
		}
		for (int i = 0; i < page_num; i++) {
			pages_per_process[i] = pages[i];
		}
		for (int i = 0; i < page_num; i++) {
			page = pages_per_process[i];
			flag = true;
			for (int j = 0; j < num_f; j++) {// to check if page is already in memory -->if so, no page fault
				if (page == frames[j]) {
					flag = false;
					second_chance[j] = true;
					cycles += cyclesForMemoryAccess;
					break;
				}
			}

			if (flag) {
				frame_ptr = searchForPageVictime(page, frames, second_chance, num_f, frame_ptr);
				cycles += cyclesForDisk;
				pgf++;// increase page faults
			}
		}
		return pgf;
	}

	public static int searchForPageVictime(int page, int frames[], boolean second_chance[], int frame_num,
			int pointer) {
		while (true) {

			// Search for victime page
			if (!second_chance[pointer]) {

				frames[pointer] = page;
				return (pointer + 1) % frame_num;
			}

			second_chance[pointer] = false;// set reference bit to zero

			// Pointer is updated in round robin manner
			pointer = (pointer + 1) % frame_num;
		}
	}

	public static int LRU(int num_f, int[] pages) {
		int index = 0;
		int pgf = 0;// number of page faults
		int page_num = pages.length;
		int page;// to represent the page
		boolean flag;// to check if page is in memory
		int pages_per_process[] = new int[page_num];
		int frames[] = new int[num_f];// frames array
		int a[] = new int[num_f]; // to trace thr least recently used frame.
		int b[] = new int[num_f];

		for (int i = 0; i < num_f; i++) {// initilize frames
			frames[i] = -1;
			a[i] = -1;
			b[i] = -1;
		}
		for (int i = 0; i < page_num; i++) {
			pages_per_process[i] = pages[i];
		}
		for (int k = 0; k < page_num; k++) {
			page = pages_per_process[k];
			flag = true;
			for (int j = 0; j < num_f; j++) {// to check if page is already in memory -->if so, no page fault
				if (page == frames[j]) {
					flag = false;
					cycles += cyclesForMemoryAccess;
					break;
				}
			}

			for (int j = 0; j < num_f && flag; j++) {// search for the least recently used page.
				if (frames[j] == a[num_f - 1]) {// the least recently used will be located at the last location in array
												// a.
					index = j;
					break;
				}
			}
			if (flag) {// page is not in memory --> page fault occurs
				cycles += cyclesForDisk;
				frames[index] = page;// place page in the available frame.
				System.out.println("frame :");
				for (int j = 0; j < num_f; j++)
					System.out.print(frames[j] + "  ");
				pgf++;// increase page faults
				System.out.println();
			} else {
				System.out.println("frame :");
				for (int j = 0; j < num_f; j++)
					System.out.print(frames[j] + "  ");
				System.out.println();
			}
			// to optain the least recently used page.
			int p = 1;
			b[0] = page;
			for (int j = 0; j < a.length; j++) {
				if (page != a[j] && p < num_f) {
					b[p] = a[j];
					p++;
				}
			}
			for (int j = 0; j < num_f; j++) {
				a[j] = b[j];
			}

		}

		return pgf;
	}

	public static int fifo(int num_f, int[] pages) {
		int pgf = 0;// number of page faults
		int page_num = pages.length;
		int page;// to represent the page
		boolean flag;// to check if page is in memory
		int pages_per_process[] = new int[page_num];
		int frames[] = new int[num_f];// frames array
		int frame_ptr = 0;

		for (int i = 0; i < num_f; i++) {// initilize frames
			frames[i] = -1;
		}
		for (int i = 0; i < page_num; i++) {
			pages_per_process[i] = pages[i];
		}
		for (int k = 0; k < page_num; k++) {
			page = pages_per_process[k];
			flag = true;
			for (int j = 0; j < num_f; j++) {// to check if page is already in memory -->if so, no page fault
				if (page == frames[j]) {
					flag = false;
					cycles += cyclesForMemoryAccess;
					break;
				}
			}
			if (flag) {// page is not in memory --> page fault occurs
				frames[frame_ptr] = page;
				cycles += cyclesForDisk;
				frame_ptr++;// move pointer to the next frame
				if (frame_ptr == num_f)// when reaching the last fram
					frame_ptr = 0;// to ensure circular search in the pyhisical memory
				System.out.print("frame :");
				for (int j = 0; j < num_f; j++)
					System.out.print(frames[j] + "   ");
				System.out.println();
				pgf++;// increase page faults
			} else {
				System.out.print("frame :");
				for (int j = 0; j < num_f; j++)
					System.out.print(frames[j] + "  ");
				System.out.println();
			}
		}
		return pgf;
	}
}
