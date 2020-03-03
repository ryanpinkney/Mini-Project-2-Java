import java.util.Random;

public class Master extends Thread {

	private Request[] queue;
	private int queueCount;
	private int queueSpace;
	private int maxRequestLength;
	
	Master(int n, int m) {

		queue = new Request[n];
		queueCount = 0;
		queueSpace = n;
		maxRequestLength = m * 1000;

	}

	public synchronized void add(Request request) throws InterruptedException {

		while(queueSpace == 0) {
			System.out.println("Queue length exceeded. Waiting to add request.");
			wait();
		}

		queueSpace--;
		queue[queueCount++ % queue.length] = request;

		notifyAll();

	}

	public synchronized Request remove() throws InterruptedException {

		while(queueSpace == queue.length) {
			System.out.println("No requests are available. Waiting to receive request.");
			wait();
		}

		Request result = queue[(queueCount + queueSpace++) % queue.length];
		notifyAll();
		return result;

	}

	public synchronized void run() {

		Random rand = new Random();
		long startTime = System.currentTimeMillis();

		while(true) {

			try {

				int requestTime = rand.nextInt(maxRequestLength) + 1;
				Request request = new Request(requestTime);
				add(request);
				System.out.println("Producer: produced request id=" + request.getId() + " of length " + requestTime + "ms at time " + (System.currentTimeMillis() - startTime) + "ms");

				int sleepTime = rand.nextInt(1000) + 1;
				System.out.println("Producer: sleeping for " + sleepTime + "ms");
				Thread.sleep(sleepTime);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}
	
}
