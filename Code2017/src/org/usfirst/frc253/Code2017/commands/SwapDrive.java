package org.usfirst.frc253.Code2017.commands;

import java.lang.Math;

import org.usfirst.frc253.Code2017.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A command for the DrivetrainTank. This command drives the chassis using generic tank drive controls. There is no feedback currently. 
 */
public class SwapDrive extends Command {

	boolean changeDrive = false;
	boolean toggle = true;
	
    public SwapDrive() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetraintank);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double leftY = Robot.oi.getLeftJoystick().getY();	//Saved locally for quicker responses (read-write is slow)
    	double rightX = Robot.oi.getRightJoystick().getX();
    	double rightY =  Robot.oi.getRightJoystick().getY();
    	
    	boolean isPressed = Robot.oi.getLeftJoystick().getRawButton(7);
    	
    	if(toggle && isPressed) {
    		toggle = false;
    		changeDrive = !changeDrive;
    	} else if(!isPressed) {
    		toggle = true;
    	}
    	
    	if(!changeDrive) {
    		Robot.drivetraintank.setDriveTrain(leftY, rightY);
    	} else {
    		throttleDrive(leftY, rightX);
    	}
    	
    	//Joystick anti-drift
//    	if(Math.abs(leftSpeed) > .125)
//    		Robot.drivetraintank.setLeft(leftSpeed);
//    	else
//    		Robot.drivetraintank.setLeft(0);
//    		
//    	
//    	if(Math.abs(rightSpeed) > -.125)
//    		Robot.drivetraintank.setRight(rightSpeed);
//    	else
//    		Robot.drivetraintank.setRight(0);
//    	if(Math.abs(leftSpeed) > .125)
//    		Robot.drivetraintank.setLeft_Back(leftSpeed);
//    	else
//    		Robot.drivetraintank.setLeft_Back(0);
//    	
//    	if(Math.abs(rightSpeed) > .125)
//    		Robot.drivetraintank.setRight_Back(rightSpeed);
//    	else
//    		Robot.drivetraintank.setRight_Back(0);
    }
    
    public static void throttleDrive(double throttle, double direction) {
    	double turnModifier = (direction + 1.00);

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
