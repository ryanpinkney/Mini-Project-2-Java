
public class Slave extends Thread{
	//important data
	private MasterThread master;
	private int slaveID;
	private long start;

	//simple constructor
	public Slave(int id, MasterThread mas, long s){
		slaveID = id;
		master = mas;
		start = s;
	}
	//primary part of the slave thread
	public synchronized void run(){
		//lets user know the slave has been created
		System.out.println("slave " + slaveID + " has started");
		while(true) {
		//prepares dummy command variables incase of issues on try-catch
		int[] command = new int[2];
		command[0] = 1;
		command[1] = 1;
		//attempts to call the master-threads queue for a request
		try {
			command = (int[]) master.Remove();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//informs user that a request went through
		System.out.println("Consumer " + slaveID +": assigned request ID " + command[1] + " processing request for the next " + command[0] +" seconds, " + "current time is " + System.currentTimeMillis());
		//slave processes request, represented by going to sleep
		try {
			Thread.sleep(command[0]);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//inform user that it completed request
		System.out.println("Consumer " + slaveID + ": completed request ID " + command[1] + " at time " + System.currentTimeMillis());
		}
	}
}
