package our_os_project;

import java.io.IOException;
import java.util.ArrayList;

public class RoundRobin {
	public static int N = 3;
	private static Process CPUprocess;
	private static int RAMsize;
	private static int faults;
	private static Process[] pro;
	private static int CPUWorkTime = 0;
	private static int quantum;
	private static int numofCS = 0;
	private static ArrayList<Process> pQ = new ArrayList<>();// job queue
	private static ArrayList<Process> rQ = new ArrayList<>(); // ready queue
	private static ArrayList<Process> fQ = new ArrayList<>(); // finished queue
	private static int[] time_table;
	public static int cycles = 0;

	public static void main(String args[]) throws IOException {
		Process[] p = new Process[3];
		int pages1[] = { 7, 0, 1, 2, 0, 3, 8, 4, 2, 9, 0, 3, 2, 1, 2, 0, 1, 7, 0, 1 };
		int pages2[] = { 7, 0, 1, 2, 0 };
		int pages3[] = { 1, 7, 0, 1 };

		p[0] = new Process(0, 0, 24, 5, pages1);
		p[1] = new Process(1, 5, 3, 3, pages2);
		p[2] = new Process(2, 6, 3, 4, pages3);
		quantum = 4;

		pro = new Process[p.length];
		RoundRobin s = new RoundRobin();

		s.setRAMsize(6);
		PageReplacement op = new PageReplacement();
		// op.setNum_frames(4);

		s.setPro(p);

		insertToPQueue(pro);
		RR(1);
	}

	public static int getRAMsize() {
		return RAMsize;
	}

	public static void setRAMsize(int rAMsize) {
		RAMsize = rAMsize;
	}

	public static void setPro(Process[] p) {
		RoundRobin.pro = p;
	}

	public static int getQuantum() {
		return quantum;
	}

	public static void setQuantum(int quantum) {
		RoundRobin.quantum = quantum;
	}

	public static Process[] insertToPQueue(Process[] ps) {
		numofCS = 0;
		CPUWorkTime = 0;
		for (int i = 0; i < ps.length; i++) {
			pQ.add(i, ps[i]);
		}

		return ps;
	}

	public static void updatequeue(Process pro, int CPUWorkTime) {
		// this means that if the process has arrived and is not done yet (not
		// processed)
		// will be added to the ready queue;

		if ((pro.getArrivalTime() < CPUWorkTime) && pro.getDuration() != 0) {
			rQ.add(pro);
		}

		else if ((pro.getArrivalTime() < CPUWorkTime) && pro.getDuration() == 0) {
			fQ.add(pro);
		}
	}

	public static void RR(int ReplacePolicy) {
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!");

		int totalt = 0;
		for (int i = 0; i < pro.length; i++) {
			totalt += pro[i].getDuration();
		}

		time_table = new int[totalt];
		CPUprocess = pro[0];
		CPUprocess.setProcessID(0);
		pQ.add(CPUprocess);
		int newQ = 0; // to iterate to reach the time quantum
		int newvar = 0;
		for (int i = 0; i < totalt; i++) {
			time_table[i] = CPUprocess.getProcessID();
			CPUprocess.setDuration(CPUprocess.getDuration() - 1);
			calc_WT_TA();
			if (i != 0) {// not at time 0
				if (CPUprocess.getProcessID() != time_table[i - 1]) {
					System.out.print("--" + i + "--P" + (CPUprocess.getProcessID() + 1));
					numofCS++;
				}
			}

			else
				System.out.print(i + "--P" + (CPUprocess.getProcessID() + 1));
			if (i == totalt - 1)
				System.out.print("--" + (i + 1));

			/*
			 * int[] newp = new int[4];
			 * 
			 * if (newvar < 4 ) {
			 * 
			 * if( i<CPUprocess.getPageReferences().length && newvar!=4) { //flag++;
			 * newp[newvar] = CPUprocess.getPageReferences()[i];
			 * System.out.println("\t("+i+")new ("+ newvar+") at this "+newp[newvar]);
			 * newvar++;} if (newvar==3) {CPUprocess.setPageReferences(newp); newvar=0;}
			 * 
			 * }
			 */
			// flag++;
			newQ++; // it has done one instance of time from the quantum
			CPUWorkTime++;
			if ((newQ == quantum) || CPUprocess.getDuration() == 0) {
				// System.out.println("\n");
				updatequeue(CPUprocess, CPUWorkTime);
				newQ = 0; // the quantum is returned to zero
				for (int k = 0; k < pro.length; k++) {
					int sth = 0;
					sth = CPUprocess.getProcessID();
					if ((sth + 1) < pro.length) {

						CPUprocess = pro[sth + 1];
						CPUprocess.setProcessID(sth + 1);

					} else if ((sth + 1) == pro.length) {

						CPUprocess = pro[0];
						CPUprocess.setProcessID(0);

					}

					if (CPUprocess.getDuration() != 0) { // has not finished processing

						break;
					}
				}
			}
		}
		print_table();
	}

	public static void calc_WT_TA() {

		for (int j = 0; j < pro.length; j++) {

			if (pro[j].getDuration() != 0) { // the process has not finished, its turn around increase
				pro[j].setTAtime(pro[j].getTAtime() + 1);
				if (j != CPUprocess.getProcessID()) // i is not the process in the CPU
					pro[j].setWTime(pro[j].getWTime() + 1); // then the waiting time for the processor chnages

			} else if (j == CPUprocess.getProcessID()) // i represent the id of the process in the CPU
				pro[j].setTAtime(pro[j].getTAtime() + 1);

		}

		return;
	}

	public static void print_table() {
		System.out.println("\nP\t WaitT  TurnArT  ");
		for (int i = 0; i < pro.length; i++) {
			System.out.printf("%d\t%3dms\t%3dms", i + 1, pro[i].getWTime(), pro[i].getTAtime());
			System.out.println();
		}

		float WT = 0, TT = 0;
		for (int i = 0; i < pro.length; i++) {
			WT += pro[i].getWTime();
			TT += pro[i].getTAtime();
		}
		WT /= pro.length;
		TT /= pro.length;
		System.out.println("The Average WT is: " + WT + "ms");
		System.out.println("The Average TAT is: " + TT + "ms");
		System.out.println("The total number of Context Switches is: " + numofCS);
	}

}