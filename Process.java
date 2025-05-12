package our_os_project;

public class Process {

	// every process has the following attributes:
	private int process_ID; // Every process has a PID that is unique to it
	private Page[] pages; // must get array of objects of type page(there are multiple pages)
	private int[] pageReferences;
	private int minNumOfFrames;
	private int duration;
	private int size;
	private int TAtime; // turn around time
	private int WTime; // waiting time
	private int arrivalTime; // start time = arrival time
	private int faultsNumber;

	public Process() {
	}

	public Process(int processID, int arrivalTime, int duration, int size, int[] pageReferences) {
		this.setProcessID(processID);
		this.setArrivalTime(arrivalTime);
		this.setDuration(duration);
		this.setSize(size);
		this.setPageReferences(pageReferences);
	}

	public void setFaultsNumber(int faultsNumber) {
		this.faultsNumber = faultsNumber;
	}

	public int getFaultsNumber() {
		return this.faultsNumber;
	}

	public void setProcessID(int PID) {
		this.process_ID = PID;
	}

	public int getProcessID() {
		return this.process_ID;
	}

	public int getMinNumOfFrames() {
		return minNumOfFrames;
	}

	public void setMinNumOfFrames(int maxNumOfFrames) {
		this.minNumOfFrames = maxNumOfFrames;
	}

	public int[] getPageReferences() {
		return pageReferences;
	}

	public void setPageReferences(int[] PageReferences) {
		this.pageReferences = PageReferences;
	}

	public void printPageReferences() {
		for (int element : pageReferences) {
			System.out.println(element);
		}
	}
	/*
	 * public void setPageReference(int PageNum, int index) { pageReferences = new
	 * int[size]; if (index < size) { pageReferences[index] = PageNum;
	 * System.out.println(PageNum); System.out.println(index); } }
	 */

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Page[] getPages() {// returns the pages for this certain process
		return pages;
	}

	public void setPages(Page[] pages) {
		this.pages = pages;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getTAtime() {
		return TAtime;
	}

	public void setTAtime(int tAtime) {
		TAtime = tAtime;
	}

	public int getWTime() {
		return WTime;
	}

	public void setWTime(int wTime) {
		WTime = wTime;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}

}
