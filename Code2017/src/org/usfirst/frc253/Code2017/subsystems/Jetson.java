package org.usfirst.frc253.Code2017.subsystems;

import java.nio.charset.StandardCharsets;
import org.zeromq.ZMQ;

public class Jetson implements Runnable{

	private static double leftDisplacement = 0.0;
	private static double rightDisplacement = 0.0;
	private static String jetsonIP = ;
	
	private Thread t;
	private String threadName;
	
	private ZMQ.Socket subscriber = null;
	
	public Jetson(String name){
		threadName = name;
	}
	
	public void run() {
		String data = subscriber.recvStr(StandardCharsets.UTF_8).trim();
    	String[] strings = data.split(" ");
    			
    	String str = strings[1].trim().replaceAll("[^\\d|\\.|-]", "");
    	double L = Double.parseDouble(str);
    	
    	String str2 = strings[1].trim().replaceAll("[^\\d|\\.|-]", "");
    	double R = Double.parseDouble(str2);
    	
    	setLeftDisplacement(L);
    	setRightDisplacement(R);
    	
    	System.out.println("vision displacement: L: " + L + " R: " + R);
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