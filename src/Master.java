import java.util.Random;

public class Master extends Thread {

	/**
	 * A circular buffer array used as a queue to store requests.
	 */
	private Request[] queue;

	/**
	 * A count of the total number of requests that have been placed into the queue.
	 */
	private int queueCount;

	/**
	 * The amount of space currently available in the queue.
	 */
	private int queueSpace;

	/**
	 * The maximum duration that a request could require. Specified by user input.
	 */
	private int maxRequestLength;

	/**
	 * Construct a new Master thread with specific queue length and maximum request duration.
	 * @param n The maximum size of the request queue.
	 * @param m The maximum duration of generated requests.
	 */
	Master(int n, int m) {

		queue = new Request[n];
		queueCount = 0;
		queueSpace = n;
		maxRequestLength = m * 1000; // Convert from seconds to milliseconds.

	}

	/**
	 * Place a request into the queue of requests waiting to be processed.
	 * @param request The request to be processed.
	 * @throws InterruptedException
	 */
	public synchronized void add(Request request) throws InterruptedException {

		// While the queue is full, wait until there is space.
		while(queueSpace == 0) {
			print("Queue length exceeded. Waiting to add request.");
			wait();
		}

		// Add the request to the queue, and modify the backend queue variables.
		queueSpace--;
		queue[queueCount++ % queue.length] = request;

		// Notify threads of a state change.
		notifyAll();

	}

	/**
	 * Remove a finished request from the queue of requests waiting to be processed.
	 * @return The finished request.
	 * @throws InterruptedException
	 */
	public synchronized Request remove() throws InterruptedException {

		// While the queue is empty, wait until there is a new request generated.
		while(queueSpace == queue.length) {
			print("No requests are available. Waiting to receive request.");
			wait();
		}

		// Retrieve the request from the queue.
		Request result = queue[(queueCount + queueSpace++) % queue.length];

		// Notify threads of a state change.
		notifyAll();

		return result;

	}

	public synchronized void run() {

		// Setup RNG and start time (so we can display relative time)
		Random rand = new Random();
		long startTime = System.currentTimeMillis();

		while(true) {

			try {

				int requestTime = rand.nextInt(maxRequestLength) + 1;
				Request request = new Request(requestTime);
				add(request);
				print("Produced request id=" + request.getId() + " of length " + requestTime + "ms at time " + (System.currentTimeMillis() - startTime) + "ms");

				int sleepTime = rand.nextInt(1000) + 1;
				print("Sleeping for " + sleepTime + "ms");
				Thread.sleep(sleepTime);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * Helper method to ensure we're always printing that we are the producer.
	 * @param s The string to print.
	 */
	private void print(String s) {
		System.out.println("Producer: " + s);
	}
	
}
