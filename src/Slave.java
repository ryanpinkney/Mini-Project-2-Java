
public class Slave extends Thread {

	private Master master;

	public Slave(Master master) {
		this.master = master;
	}

	public synchronized void handleRequest(int delay) {

		try {
			sleep(delay);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void run() {

		while(true) {

			try {

				//wait for a request to be available
				System.out.println("Waiting for a request to be available.");
				Request request = master.remove();

				//print request info
				System.out.println("Handing request id=" + request.getId() + ", length=" + request.getLength());

				//handle request
				handleRequest(request.getLength());

			} catch(InterruptedException e) {
				e.printStackTrace();
			}

		}

	}
}
