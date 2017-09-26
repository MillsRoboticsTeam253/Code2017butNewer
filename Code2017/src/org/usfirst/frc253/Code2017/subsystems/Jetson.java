package org.usfirst.frc253.Code2017.subsystems;

import org.zeromq.ZMQ;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Jetson implements Runnable{

	private static double leftDisplacement = 0.0;
	private static double rightDisplacement = 0.0;
	private static String jetsonIP = "10.2.53.107";
	
	private Thread t;
	private String threadName;
	
	private ZMQ.Socket subscriber = null;
	
	public Jetson(String name){
		threadName = name;
	}
	
	public void run() {
		String data = subscriber.recvStr();
	
    	SmartDashboard.putString("Data", data);
	}
	
	public void start () {
		ZMQ.Context context = ZMQ.context(1);
		subscriber = context.socket(ZMQ.SUB);
		
		subscriber.connect("tcp://" + jetsonIP + ":5801");
		
		String filter = "displacement";
		subscriber.subscribe(filter.getBytes());
		
		System.out.println("Starting " +  threadName );
		
	    if (t == null) {
	    	t = new Thread (this, threadName);
	    	t.start ();
	    }
	}
	
	public static double getLeftDisplacement(){
		return leftDisplacement;
	}
	
	public static double getRightDisplacement(){
		return rightDisplacement;
	}
	
	synchronized private static void setLeftDisplacement(double l){
		leftDisplacement = l;
	}
	
	synchronized private static void setRightDisplacement(double r){
		rightDisplacement = r;
	}
}