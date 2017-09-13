//package org.usfirst.frc253.Code2017.subsystems;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import org.usfirst.frc253.Code2017.Robot;
//
//import edu.wpi.first.wpilibj.networktables.NetworkTable;
//
//public class KinectDrive {
//	public static void main(String[] args){
//		new KinectDrive().run();
//	}
//	
//	public void run(){
//		
//		NetworkTable.setClientMode();
//		NetworkTable.setIPAddress(address);
//		NetworkTable table = NetworkTable.getTable("datatable");
//		
//		while(true){
//			try{
//				Thread.sleep(1000);
//			} catch(InterruptedException ex) {
//				Logger.getLogger(KinectDrive.class.getName()).log(Level.SEVERE, null, ex);
//			}
//			
//			double leftSpeed = table.getNumber("leftSpeed", 0.0);
//			double rightSpeed = table.getNumber("rightSpeed", 0.0);
//			
//			if(Math.abs(leftSpeed) > .125)
//	    		Robot.drivetraintank.setLeft(leftSpeed);
//	    	else
//	    		Robot.drivetraintank.setLeft(0);
//	    		
//	    	
//	    	if(Math.abs(rightSpeed) > .125)
//	    		Robot.drivetraintank.setRight(rightSpeed);
//	    	else
//	    		Robot.drivetraintank.setRight(0);
//	    	if(Math.abs(leftSpeed) > .125)
//	    		Robot.drivetraintank.setLeft_Back(leftSpeed);
//	    	else
//	    		Robot.drivetraintank.setLeft_Back(0);
//	    	
//	    	if(Math.abs(rightSpeed) > .125)
//	    		Robot.drivetraintank.setRight_Back(rightSpeed);
//	    	else
//	    		Robot.drivetraintank.setRight_Back(0);
//			
//		}
//	}
//}
