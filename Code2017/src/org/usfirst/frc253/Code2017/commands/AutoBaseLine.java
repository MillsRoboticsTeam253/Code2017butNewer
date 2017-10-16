package org.usfirst.frc253.Code2017.commands;

import org.usfirst.frc253.Code2017.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoBaseLine extends Command {

    public AutoBaseLine() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetraintank);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(5.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetraintank.setDriveTrain(-.5, -.5);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetraintank.setDriveTrain(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetraintank.setDriveTrain(0, 0);
    }
}
