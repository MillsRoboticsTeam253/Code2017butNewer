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
	private static final double realHeight = (5.0/12.0); //in feet
	private static final double realWidth = (9.5/12.0); //in feet
	private static final double focalLength = 333.82; //in pixels; https://www.chiefdelphi.com/forums/showthread.php?p=1653594
	private static final double FOV = 60;
    private static final double horizontalDPP = FOV/Robot.CAMERA_WIDTH;
    private static final double cameraCenter = Robot.CAMERA_WIDTH/2.0;
    private static final double leftPegBearing = rad(-60.0);
    private static final double rightPegBearing = rad(60.0);
    private static final double offsetRatioThresh = 0.968; //5 degree threshold
    private static final double centerThresh = rad(5.0); //5 degree threshold

	//Intermediate calculations
	//Numbers that are important but only because we need them to calculate other things
    private double leftHeight = 0.0;
    private double rightHeight = 0.0;
    
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
            if (!pipeline.filterContoursOutput().isEmpty()) {
                Rect peg1 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
                Rect peg2 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(1));
                Rect pegPiece = Imgproc.boundingRect(pipeline.filterContoursOutput().get(2));
                synchronized (imgLock) {
                	//comparing real and perceived height to calculate distance
//                	if(!pegPiece.equals(null)){
//                	} else {
//                		if(peg1.x < peg2.x) {
//                    	leftHeight = peg1.height;
//                    	rightHeight = peg2.height;
//                    	perceivedHeight = (peg1.height + peg2.height)/2.0;
//                    	perceivedWidth = (peg2.x + peg2.width) - peg1.x;
//                    	pegCenter = ((peg2.x + peg2.width) + peg1.x)/2.0;
//                		} else {
//                    	leftHeight = peg2.height;
//                    	rightHeight = peg1.height;
//                    	perceivedHeight = (peg2.height + peg1.height)/2.0;
//                    	perceivedWidth = (peg1.x + peg1.width) - peg2.x;
//                    	pegCenter = ((peg1.x + peg1.width) + peg2.x)/2.0;
//                		}
//                	}
                	
                	//TEST
                	leftHeight = peg1.height;
                	rightHeight = peg2.height;
                	double thirdHeight = pegPiece.height;
                	perceivedHeight = (peg1.height + peg2.height)/2.0;
                	perceivedWidth = (peg2.x + peg2.width) - peg1.x;
                	pegCenter = ((peg2.x + peg2.width) + peg1.x)/2.0;
                	//TEST
                	//robot facing to the right of the peg is positive
                    angleRobot = rad((cameraCenter - pegCenter) * horizontalDPP);
                    //distanceDirect is calculated in feet
                    distanceDirect = (realHeight * focalLength)/perceivedHeight;
                    
                    robotBearing = rad(gyro.getAngle() % 360);
                    angleFromPeg = findAngleFromPeg();
                    /*
                     * find legs of the right triangle formed by the peg
                     * and the robot; offset is x and travel is y if peg
                     * is uppermost point of triangle
                     */
                    distanceOffset = distanceDirect * Math.sin(angleFromPeg);
                    distanceTravel = distanceDirect * Math.cos(angleFromPeg);
                    
                    SmartDashboard.putNumber("Peg1", leftHeight);
                    SmartDashboard.putNumber("Peg2", rightHeight);
                    SmartDashboard.putNumber("Peg3", thirdHeight);
                    
                    
                    SmartDashboard.putNumber("Perceived Height in pixels", perceivedHeight);
                    SmartDashboard.putNumber("Peg Center in pixels", pegCenter);
                    SmartDashboard.putNumber("Robot Angle in pixels", angleRobot);
                    SmartDashboard.putNumber("Direct Distance to Peg in feet", distanceDirect);
                    SmartDashboard.putNumber("Bearing of the Robot in degrees", gyro.getAngle());
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
    
    public double findAngleFromPeg() {
    	synchronized(imgLock) {
    		double bearing = getRobotBearing();
    		int pegNum = whichPeg();
    		if(pegNum == 1) { //left
    			return bearing - leftPegBearing;
    		} else if(pegNum == 3) { //right
    			return bearing - rightPegBearing;
    		} else { //center
    			return bearing;
    		}
    	}
    }
    
    /**
     * @return 1 = left; 2 = center; 3 = right
     */
    public int whichPeg() {
    	synchronized (imgLock) {
    		double bearing = getRobotBearing();
    		double angle = getRobotAngle();
    		double angleDiff = bearing - angle;
    		if(!isOffset()) {
    			//center peg, centered
    			if(Math.abs(angleDiff) < centerThresh) {
    				return 2;
    			//right peg, centered
    			} else if(angleDiff > rightPegBearing - centerThresh && angleDiff < rightPegBearing + centerThresh) {
    				return 3;
    			//left peg, centered
    			} else {
    				return 1;
    			}
    		} else {
    			if(bearing >= rad(90)) { //right peg, right offset
    				return 3;
    			} else if(bearing <= rad(-90)) { //left peg, left offset
    				return 1;
    			} else if(bearing < 0 && offsetDirection() == 3) { //left peg, right offset
    				return 1;
    			} else if(bearing > 0 && offsetDirection() == 1) { //right peg, left offset
    				return 3;
    			} else {
    				return 2; //all else is center
    			}
    		}
    	}
    }
    
    public boolean isOffset() {
    	synchronized(imgLock) {
    		double perceivedWidth = getPerceivedWidth();
    		double ratio = perceivedWidth / realWidth; //closer to 1 == less offset
    		if(Math.abs(ratio) > offsetRatioThresh) { 
    			return false;
    		} else {
    			return true;
    		}
    	}
    }
    
    /**
     * @return 1 = left; 3 = right; 0 = not offset
     */
    public int offsetDirection() {
    	synchronized(imgLock) {
    		double left = getLeftHeight();
    		double right = getRightHeight();
    		if(isOffset()) {
    			if(left > right) {
    				return 1;
    			} else {
    				return 3;
    			}
    		} else {
    			return 0;
    		}
    	}
    }
    
    public static double rad(double input) {
    	return input * (Math.PI/180.0);
    }
    
    public static double deg(double input) {
    	return input * (180.0/Math.PI);
    }
    
    public double getLeftHeight() {
    	return leftHeight;
    }
    
    public double getRightHeight() {
    	return rightHeight;
    }
    
    public double getRobotBearing() {
    	return robotBearing;
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
