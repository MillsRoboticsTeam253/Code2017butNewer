package org.usfirst.frc253.Code2017.vision;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc253.Code2017.Robot;
import edu.wpi.first.wpilibj.vision.VisionThread;


public class VisionProcess extends Thread {
	private VisionThread visionThread;
	private final Object imgLock = new Object();
	
	//Constants
	private final double realHeight = 2; //TODO find actual height
	private final double realWidth = 1; //TODO find actual real width
	private final double focalLength = 1; //TODO find actual focal length
	private final double FOV = 60;
    private final double horizontalDPP = FOV/Robot.CAMERA_WIDTH;
    private final double cameraCenter = Robot.CAMERA_WIDTH/2;

	//Intermediate calculations
	//Numbers that are important but only because we need them to calculate other things
	private double perceivedHeight = 0.0;
	private double pegCenter = 0.0;

	private double angleFromPeg = 0.0;
	private double distanceDirect = 0.0;
	private double angleRobot = 0.0;
	
	private double distanceOffset = 0.0;
	private double distanceTravel = 0.0;
    
    public VisionProcess() {
    	visionThread = new VisionThread(Robot.camera, new GripPipeline(), pipeline -> {
            if (!pipeline.filterContoursOutput().isEmpty()) {
                Rect peg = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
                synchronized (imgLock) {
                    pegCenter = peg.x + (peg.width / 2);
                    //robot facing to the right of the peg is positive
                    angleRobot = ((cameraCenter - pegCenter) * horizontalDPP) * (Math.PI / 180);
                    perceivedHeight = peg.height;
                    distanceDirect = (realHeight * focalLength)/perceivedHeight;
                    angleFromPeg = (Math.PI/2) - Math.asin(peg.width / realWidth);
                    distanceOffset = distanceDirect * Math.sin(angleFromPeg);
                    distanceTravel = distanceDirect * Math.cos(angleFromPeg);
                }
            }
        });
    }
    @Override
    public void run() {
    	visionThread.start();
    }
    
    public double[][] findWaypoints() {
    	synchronized (imgLock) {
    		double angleFromPeg = this.angleFromPeg;
    		double angleRobot = this.angleRobot;
    		double[][] waypoints = new double[][]{
				{0, 0},
				{distanceTravel/4, Math.tan(angleFromPeg - angleRobot) * (distanceTravel/4)},
				{distanceTravel/2, distanceOffset},
				{distanceTravel, distanceOffset}
    		}; 
    		return waypoints;
    	}
    }
}
