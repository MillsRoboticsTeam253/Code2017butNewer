package org.usfirst.frc253.Code2017.commands;

import org.usfirst.frc253.Code2017.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Step extends Command {

	private double leftVelocity;
	private double rightVelocity;
	
    public Step(double left, double right) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetraintank);
    	this.leftVelocity = left;
    	this.rightVelocity = right;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(0.1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetraintank.setLeft_Back(-leftVelocity);
    	Robot.drivetraintank.setLeft(-leftVelocity);
    	Robot.drivetraintank.setRight(-rightVelocity);
    	Robot.drivetraintank.setRight_Back(-rightVelocity);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
