import java.util.Scanner;

public class MiniProject2 {

	public static void main(String[] args) {

		//this is for determining the total number of slave threads
		Scanner input = new Scanner(System.in);
		System.out.println("how many slave threads do you want? ");
		int N = input.nextInt();
		System.out.println("how long should maximum request length be? ");
		int M = input.nextInt();
		
		//this creates the master thread which queues requests
		MasterThread Master = new MasterThread(N,M);
		Slave[] slaves = new Slave[N];
		for(int i = 0; i < N; i++) {
			slaves[i] = new Slave(i, Master);
			slaves[i].run();
		}
		Master.run();
		
	}

}
