import java.util.*;
public class MiniProject2 {

	public static synchronized void main(String[] args) throws InterruptedException {
		Scanner input = new Scanner(System.in);
		
		//this is for determining the total number of slave threads
		System.out.println("how many slave threads do you want? ");
		int N = input.nextInt();
		
		//this is for determing the maximum length of a request
		System.out.println("how long should maximum request length be? ");
		int M = input.nextInt();
		
		//this creates the master thread which queues requests
		MasterThread master = new MasterThread(N,M);
		input.close();
		//The primary part of the main thread which constantly runs
		while(true) {
			//prepares a new request
			double requestTime = 1000 * Math.random() * M;
			master.Add((int) requestTime);
			//tries to sleep a while before producing a new request
			double sleepTime =(1000 + Math.floor(1000 * Math.random() * (M - 1)));
			try {
				System.out.println("Producer: sleeping for " + sleepTime/1000 + " seconds");
				Thread.sleep((int) sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
