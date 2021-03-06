public class Slave extends Thread {

	/**
	 * A count to generate new IDs from.
	 */
	private static int idCount = 0;

	/**
	 * A pointer to the master thread.
	 */
	private Master master;

	/**
	 * Unique ID of this slave thread.
	 */
	private int id;

	public Slave(Master master) {
		this.master = master;
		id = idCount++;
	}

	/**
	 * Simulate spending resources processing a request.
	 * @param r The request to process.
	 */
	public synchronized void handleRequest(Request r) {

		try {
			// No real processing, just sleep for the specified delay.
			sleep(r.getLength());
		} catch(InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void run() {

		while(true) {

			try {

				// Get a request from the master thread. If there are none, master.remove() will wait.
				print("Waiting for a request to be available.");
				Request request = master.remove();

				// Handle request
				print("Handing request id=" + request.getId() + ", length=" + request.getLength());
				handleRequest(request);

			} catch(InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * Helper method to ensure we're always printing that we are the consumer and our id.
	 * @param s The string to print.
	 */
	private void print(String s) {
		System.out.println("Consumer id=" + id + ": " + s);
	}

}
