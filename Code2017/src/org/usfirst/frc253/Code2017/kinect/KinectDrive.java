package org.usfirst.frc253.Code2017.kinect;

import java.lang.Math;

import org.usfirst.frc253.Code2017.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A command for the DrivetrainTank. This command drives the chassis using generic tank drive controls. There is no feedback currently. 
 */
public class KinectDrive extends Command {

	Kinect kinect = new Kinect();
	
	private double leftWristY;
	private double rightWristY;
	private double spineBaseY;
	
    public KinectDrive() {
        // Use requires() here to declare subsystem dependencies
        requires(org.usfirst.frc253.Code2017.Robot.drivetraintank);
        
        kinect.start(Kinect.SKELETON);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	leftWristY = kinect.getLeftWristY();
        rightWristY = kinect.getRightWristY();
        spineBaseY = kinect.getSpineBaseY();
    	
    	double leftSpeed = leftWristY - spineBaseY;
    	double rightSpeed = rightWristY - spineBaseY;
    	
    	Robot.drivetraintank.setDriveTrain(leftSpeed, rightSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
