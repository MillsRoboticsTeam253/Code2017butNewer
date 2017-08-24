package org.usfirst.frc253.Code2017.commands;

import org.usfirst.frc253.Code2017.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ReverseStraightDrive extends Command {

    public ReverseStraightDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetraintank);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Drives straight backwards
    	Robot.drivetraintank.setLeft_Back(.35);
    	Robot.drivetraintank.setLeft(.35);
    	Robot.drivetraintank.setRight(.35);
    	Robot.drivetraintank.setRight_Back(.35);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	//Stops motors
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
