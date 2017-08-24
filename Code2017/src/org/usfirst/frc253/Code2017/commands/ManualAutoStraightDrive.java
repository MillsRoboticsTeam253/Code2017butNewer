package org.usfirst.frc253.Code2017.commands;

import org.usfirst.frc253.Code2017.Robot;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ManualAutoStraightDrive extends Command {
	
	private Ultrasonic ultraLeft;
	private Ultrasonic ultraRight;
	
    public ManualAutoStraightDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetraintank);
    	requires(Robot.sensorData);
    	ultraLeft = Robot.sensorData.ultraLeft;
    	ultraRight = Robot.sensorData.ultraRight;
    	ultraLeft.setAutomaticMode(true);
		ultraRight.setAutomaticMode(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(1.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double rangeLeft = ultraLeft.getRangeInches();
    	double rangeRight = ultraRight.getRangeInches();
    	SmartDashboard.putNumber("Left Ultrasonic", rangeLeft);
    	SmartDashboard.putNumber("Right Ultrasonic", rangeRight);
    	
    	if(((rangeLeft + rangeRight)/2.00) > 13.00){
    		Robot.drivetraintank.setLeft_Back(-.5);
    		Robot.drivetraintank.setLeft(-.5);
    		Robot.drivetraintank.setRight(-.5);
    		Robot.drivetraintank.setRight_Back(-.5);
    	} else {
    		Robot.drivetraintank.setLeft_Back(0);
        	Robot.drivetraintank.setLeft(0);
        	Robot.drivetraintank.setRight(0);
        	Robot.drivetraintank.setRight_Back(0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetraintank.setLeft_Back(0);
    	Robot.drivetraintank.setLeft(0);
    	Robot.drivetraintank.setRight(0);
    	Robot.drivetraintank.setRight_Back(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
