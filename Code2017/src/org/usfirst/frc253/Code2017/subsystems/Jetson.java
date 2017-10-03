package org.usfirst.frc253.Code2017.subsystems;

import org.zeromq.ZMQ;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Jetson implements Runnable{

	private String data = "newnull";
	private static String jetsonIP = "10.2.53.255";
	
	private Thread t;
	private String threadName;
	
	private ZMQ.Socket subscriber = null;
	
	public Jetson(String name){
		threadName = name;
	}

	
	public void run() {
		ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.SUB);
        socket.setReceiveTimeOut(1000); //Set a receive timeout so that the data is marked as invalid if we don't get anything
        socket.subscribe("".getBytes());
        socket.connect("tcp://" + jetsonIP + ":5801");
        
        for(;;){
        	
        	data = socket.recvStr();
        	SmartDashboard.putString("Data", data);
        }
        //socket.close();
	}
}