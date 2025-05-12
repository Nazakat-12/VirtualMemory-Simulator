package our_os_project;

import java.text.ParseException;

public class FCFS {
	public static int N;
	private static int RAMsize;
	private static Process[] pro;
	public static int cyclesFC = 0;
	private static float AVG_TAT;
	private static float AVG_WT;
	private static int context_switch;

	public FCFS() {
	}

	public static int getN() {
		return N;
	}

	public static void setN(int n) {
		N = n;
	}

	public static int getCycles() {
		return getContext_switch() * 5;
	}

	public static void setCycles() {
		FCFS.cyclesFC = getContext_switch() * 5;
	}

	public static int getContext_switch() {
		return context_switch;
	}

	public static void setContext_switch(int N) { // N number of proccesses
		FCFS.context_switch = N - 1;
	}

	public static int getRAMsize() {
		return RAMsize;
	}

	public static void setRAMsize(int rAMsize) {
		RAMsize = rAMsize;
	}

	public static float getAVG_TAT() {
		return AVG_TAT;
	}

	public static void setAVG_TAT(float avgTA) {
		FCFS.AVG_TAT = avgTA;
	}

	public static float getAVG_WT() {
		return AVG_WT;
	}

	public static void setAVG_WT(float AVG_WT) {
		FCFS.AVG_WT = AVG_WT;
	}

	public static Process[] getPro() {
		return pro;
	}

	public static void setPro(Process[] pro) {
		FCFS.pro = pro;
	}

	// Function to find the waiting time for all processes
	static void find_W_T(Process[] pro) {
		// waiting time for first process is 0
		// calculating waiting time
		int wt = 0;
		for (int i = 0; i < pro.length; i++) {
			if (i == 0) {
				pro[i].setWTime(wt);
			} else {
				wt = pro[i - 1].getDuration() + pro[i - 1].getWTime();
				pro[i].setWTime(wt);
			}
		}
	}

	// Function to calculate turn around time
	static void find_T_T(Process[] pro) {
		// calculating turnaround time by adding burst time [i] + waiting time[i]
		int ta = 0;
		for (int i = 0; i < pro.length; i++) {
			ta = pro[i].getDuration() + pro[i].getWTime();
			pro[i].setTAtime(ta);
		}
	}

	// Function to calculate average time
	static void find_A_T(Process[] pro) {
		float WT = 0, TA = 0;
		for (int i = 0; i < pro.length; i++) {
			WT += pro[i].getWTime();
			TA += pro[i].getTAtime();
		}
		float AvgWT = (float) WT / (float) pro.length;
		float AvgTA = (float) TA / (float) pro.length;
		FCFS.setAVG_TAT(AvgTA);
		FCFS.setAVG_WT(AvgWT);

		// System.out.printf("Average waiting time = %f", AvgWT);
		// System.out.printf("\n");
		// System.out.printf("Average turn around time = %f ", AvgTA);
	}

}
