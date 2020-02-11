
public class Slave extends Thread{
	private MasterThread Master;
	int slaveID;

	public Slave(int id, MasterThread mas){
		slaveID = id;
		Master = mas;
	}
	public void run(){
		Object command;
		try {
			command = Master.Remove();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System..out.println("Consumer " + slaveID +": " );
	}
}
