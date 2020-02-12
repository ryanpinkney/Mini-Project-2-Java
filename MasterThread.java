import java.util.LinkedList;
import java.util.Queue;

public class  MasterThread extends Thread {
	//various private variables for the thread
	private Queue request = new LinkedList();
	private int iD = 0;
	private long start;
	private Slave[] slaves;
	
	//constructor prepares all the necessary slave threads
	public MasterThread(int n, int m, long s) throws InterruptedException{
		start = s;
		slaves = new Slave[n];
		for(int i = 0; i < n; i++) {
			slaves[i] = new Slave(i, this, start);
			slaves[i].start();
		}
	}
	//the add function queues a new request and attempts to notify a slave
	public synchronized void Add(int item) {	
		int req[] = new int[2];
		req[0] = item;
		req[1] = iD;
		request.add(req);
		System.out.println("Producer: produced request ID " + iD + ", length " + item + " at time " + System.currentTimeMillis());
		iD++;
		notify();
		}
	//this method takes the head of the queue and returns it
	public synchronized Object Remove() throws InterruptedException {
		while(request.peek() == null) {
				wait();
		}
		Object returnable = request.remove();
		return returnable;
	}
}
