import java.util.LinkedList;
import java.util.Queue;

public class  MasterThread extends Thread {
	//various private variables for the thread
	private int[][] request = new int[9][2];
	private int enque = 0;
	private int deque = 0;
	private int iD = 0;
	private Slave[] slaves;
	
	//constructor prepares all the necessary slave threads
	public MasterThread(int n) throws InterruptedException{
		slaves = new Slave[n];
		for(int i = 0; i < n; i++) {
			slaves[i] = new Slave(i, this);
			slaves[i].start();
		}
	}
	//the add function queues a new request and attempts to notify a slave
	public synchronized void Add(int item) {	
		int req[] = new int[2];
		req[0] = item;
		req[1] = iD;
		while(request[enque][0] != 0) {
		}
		request[enque] = (req);
		//informs user the request has been made
		System.out.println("Producer: produced request ID " + iD + ", length " + item + " at time " + System.currentTimeMillis());
		iD++;
		enque++;
		if(enque > 8) {
			enque = 0;
		}
		notify();
		}
	//this method takes the head of the queue and returns it
	public synchronized Object Remove() throws InterruptedException {
		while(request[deque][0] == 0) {
				wait();
		}
		int[] returnable = request[deque];
		request[deque][0] = 0;
		request[deque][1] = 0;
		deque++;
		if(deque > 8) {
			deque = 0;
		}
		return returnable;
	}
}
