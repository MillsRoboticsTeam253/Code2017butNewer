package org.usfirst.frc253.Code2017.commands;

import org.usfirst.frc253.Code2017.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoRetract extends Command {

    public AutoRetract() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.gearFlip);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(1.5);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.gearFlip.Retract();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.gearFlip.FullStop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
