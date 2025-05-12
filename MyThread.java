package our_os_project;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.ArrayList;

public class MyThread extends Thread {
	private Process[] arrp;
	private FCFS f = new FCFS();
	private PageReplacement pr = new PageReplacement();
	private int ramsize = 0;
	private int flag = 0;

	public MyThread() {
	 // start();
	}

	public void run() {
		FCFSThread();
		for (int i = 0; i < arrp.length; i++) {
			pagePeplacementThread(arrp[i]);
		}
	}

	public int getRamsize() {
		return ramsize;
	}

	public void setRamsize(int ramsize) {
		this.ramsize = ramsize;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Process[] getArrp() {
		return arrp;
	}

	public void setArrp(Process[] arrp) {
		this.arrp = arrp;
	}

	public FCFS getF() {
		return f;
	}

	public void setF(FCFS f) {
		this.f = f;
	}

	public PageReplacement getPr() {
		return pr;
	}

	public void setPr(PageReplacement pr) {
		this.pr = pr;
	}

	public void FCFSThread() {
		f.setPro(arrp);
		f.setRAMsize(ramsize);
		f.find_W_T(f.getPro());
		f.find_T_T(f.getPro());
		f.find_A_T(f.getPro());
	}

	public void pagePeplacementThread(Process p) {
		if (flag == 1) {
			p.setFaultsNumber(pr.fifo(ramsize, p.getPageReferences()));
		} else if (flag == 2) {
			p.setFaultsNumber(pr.secondChanceFIFO(ramsize, p.getPageReferences()));
		} else if (flag == 3) {
			p.setFaultsNumber(pr.LRU(ramsize, p.getPageReferences()));
		}
	}
}
