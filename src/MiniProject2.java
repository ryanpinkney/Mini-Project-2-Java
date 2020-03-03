import java.util.Scanner;

public class MiniProject2 {

	public static void main(String[] args) {

		// This is for determining the total number of slave threads
		Scanner input = new Scanner(System.in);
		System.out.println("How many slave threads do you want? ");
		int n = input.nextInt();
		System.out.println("How many seconds should maximum request length be? ");
		int m = input.nextInt();
		input.close();
		
		// Create the master (producer) thread which produces requests.
		// The project specifies that the queue length will be equal to the number of slave threads.
		Master master = new Master(n, m);
		master.start();

		// Create and start n slave (consumer) threads which process requests.
		for(int i = 0; i < n; i++) {
			Slave slave = new Slave(master);
			slave.start();
		}
		
	}

}