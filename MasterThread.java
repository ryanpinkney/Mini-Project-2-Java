import java.util.LinkedList;
import java.util.Queue;

public class  MasterThread {
	private int totalslaves;
	private int length;
	private Queue request = new LinkedList();
	private int ID = 0;
	private long start;
	
	MasterThread(int n, int m) throws InterruptedException{
		totalslaves = n;
		length = m;
		start = System.currentTimeMillis();
	}
	public synchronized void Add(Object item, int ID) {	
		request.add(item);
		ID++;
		notify();
	}
	public synchronized Object Remove() throws InterruptedException {
		while(request.peek() != null) {
			wait();
		}
		Object returnable = request.remove();
		return returnable;
	}
	public synchronized void run() {
		while(true) {
			int requestTime = (int) Math.random() * 10;
			Add(requestTime, ID);
			System.out.println("Producer: produced request ID " + ID + ", length " + (requestTime)/1000 + " at time " + (start - System.currentTimeMillis())/1000);
			int sleepTime = (int) (1000 + Math.random() * length);
			try {
				Thread.sleep(sleepTime);
				System.out.println("Producer: sleeping for " + sleepTime + " seconds");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
