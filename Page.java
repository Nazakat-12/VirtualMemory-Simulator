package our_os_project;

public class Page { // object that represents each page in a process

	private int PID; // process ID of the process in which the page is located
	private int PageID; // page ID

	public Page() {
	}

	public Page(int PID, int PageID) { // initialize page
		this.PID = PID;
		this.PageID = PageID;
	}

	// setters and getters
	public int getPageID() {
		return PageID;
	}

	public void setPageID(int PageID) {
		this.PageID = PageID;
	}

	public int getPID() {
		return PID;
	}

	public void setPID(int PID) {
		this.PID = PID;
	}

}
