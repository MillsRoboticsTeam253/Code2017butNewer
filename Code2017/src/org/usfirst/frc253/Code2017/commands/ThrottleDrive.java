package org.usfirst.frc253.Code2017.commands;

import java.lang.Math;

import org.usfirst.frc253.Code2017.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A command for the DrivetrainTank. This command drives the chassis using generic tank drive controls. There is no feedback currently. 
 */
public class ThrottleDrive extends Command {

    public ThrottleDrive() {
        // Use requires() here to declare subsystem dependencies
        requires(org.usfirst.frc253.Code2017.Robot.drivetraintank);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double throttle = Robot.oi.getLeftJoystick().getY();	//Saved locally for quicker responses (read-write is slow)
    	double direction = Robot.oi.getLeftJoystick().getX();
    	
    	double turnModifier = (direction + 1.00);
    	
    	//Joystick anti-drift
    	if(Math.abs(throttle) > .125){
    		Robot.drivetraintank.setDriveTrain(throttle * turnModifier, throttle * (2.00 - turnModifier));
    	} else if(Math.abs(throttle) < .125 && Math.abs(direction) > .125){
    		Robot.drivetraintank.setDriveTrain(-direction, direction);
    	} else {
    		Robot.drivetraintank.setDriveTrain(0, 0);
    	}
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
