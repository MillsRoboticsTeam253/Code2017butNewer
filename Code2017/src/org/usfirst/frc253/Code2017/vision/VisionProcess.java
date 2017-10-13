package org.usfirst.frc253.Code2017.vision;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc253.Code2017.Robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;


public class VisionProcess extends Thread {
	private VisionThread visionThread;
	private final Object imgLock = new Object();
	private AnalogGyro gyro;
	
	//Constants
	private final double realHeight = (5.0/12.0); //in feet
	private final double realWidth = (9.5/12.0); //in feet
	private final double focalLength = 333.82; //in pixels; https://www.chiefdelphi.com/forums/showthread.php?p=1653594
	private final double FOV = 60;
    private final double horizontalDPP = FOV/Robot.CAMERA_WIDTH;
    private final double cameraCenter = Robot.CAMERA_WIDTH/2;
    private final double leftPegBearing = 0; //TODO find value
    private final double rightPegBearing = 0; //TODO find value
    private final double offsetRatioThresh = 0.968; //5 degree threshold

	//Intermediate calculations
	//Numbers that are important but only because we need them to calculate other things
	private double perceivedHeight = 0.0;
	private double perceivedWidth = 0.0;
	private double pegCenter = 0.0;

	private double angleFromPeg = 0.0;
	private double distanceDirect = 0.0;
	private double angleRobot = 0.0;
	private double robotBearing = 0.0;
	
	private double distanceOffset = 0.0;
	private double distanceTravel = 0.0;
    
    public VisionProcess() {
    	this.gyro = Robot.sensorData.gyro;
    	visionThread = new VisionThread(Robot.camera, new GripPipeline(), pipeline -> {
            if (!pipeline.filterContoursOutput().isEmpty() && pipeline.filterContoursOutput().size() >= 2) {
                Rect peg1 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
                Rect peg2 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(1));
                synchronized (imgLock) {
                	//comparing real and perceived height to calculate distance
                    perceivedHeight = (peg1.height + peg2.height)/2.0;
                    perceivedWidth = peg1.width;
                    
                	pegCenter = peg1.x + (peg1.width / 2);
                    //robot facing to the right of the peg is positive
                    angleRobot = ((cameraCenter - pegCenter) * horizontalDPP) * (Math.PI / 180);
                    //distanceDirect is calculated in feet
                    distanceDirect = (realHeight * focalLength)/perceivedHeight;
                    //DEPRECATED find angle from peg
                    robotBearing = (gyro.getAngle() % 360) * (Math.PI/180);
                    /*
                     * find legs of the right triangle formed by the peg
                     * and the robot; offset is x and travel is y if peg
                     * is uppermost point of triangle
                     */
                    distanceOffset = distanceDirect * Math.sin(angleFromPeg);
                    distanceTravel = distanceDirect * Math.cos(angleFromPeg);
                    
                    SmartDashboard.putNumber("Perceived Height in pixels", perceivedHeight);
                    SmartDashboard.putNumber("Peg Center in pixels", pegCenter);
                    SmartDashboard.putNumber("Robot Angle in pixels", angleRobot);
                    SmartDashboard.putNumber("Direct Distance to Peg in feet", distanceDirect);
                    SmartDashboard.putNumber("Bearing of the Robot in degrees", robotBearing * (180/Math.PI));
                }
            }
        });
    }
    @Override
    public void run() {
    	this.gyro.reset();
    	visionThread.start();
    }
    
    public double[][] findWaypoints() {
    	synchronized (imgLock) {
    		double angleFromPeg = getAngleFromPeg();
    		double angleRobot = getRobotAngle();
    		double travel = getTravelDistance();
    		double offset = getOffsetDistance();
    		double[][] waypoints = new double[][]{
				{0, 0},
				{travel/4, Math.tan(angleFromPeg - angleRobot) * (travel/4)},
				{travel/2, offset},
				{travel, offset}
    		}; 
    		return waypoints;
    	}
    }
    
    /**
     * @return 1 = left; 2 = center; 3 = right
     */
    public int whichPeg() {
    	synchronized (imgLock) {
    		if(Math.abs(robotBearing - angleRobot) < (2.0 * (Math.PI/180))) {
    			return 2;
    		}
    		return 0; //TODO add conditionals for left and right
    	}
    }
    
    public boolean isOffset() {
    	double perceivedWidth = getPerceivedWidth();
    	double ratio = perceivedWidth / realWidth; //closer to 1 == less offset
    	if(Math.abs(ratio) > offsetRatioThresh) { 
    		return false;
    	} else {
    		return true;
    	}
    }
    
    public double getPerceivedWidth() {
    	return perceivedWidth;
    }
    
    public double getRobotAngle() {
    	return angleRobot;
    }
    
    public double getAngleFromPeg() {
    	return angleFromPeg;
    }
    
    public double getTravelDistance() {
    	return distanceTravel;
    }
    
    public double getOffsetDistance() {
    	return distanceOffset;
    }
}
