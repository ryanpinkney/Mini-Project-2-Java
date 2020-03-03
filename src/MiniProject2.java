import java.util.Scanner;

public class MiniProject2 {

	public static void main(String[] args) {

		//This is for determining the total number of slave threads
		Scanner input = new Scanner(System.in);
		System.out.println("How many slave threads do you want? ");
		int n = input.nextInt();
		System.out.println("How many seconds should maximum request length be? ");
		int m = input.nextInt();
		
		//This creates the master thread which queues requests
		Master master = new Master(n, m);
		master.start();
		for(int i = 0; i < n; i++) {
			Slave slave = new Slave(master);
			slave.start();
		}
		
	}

}