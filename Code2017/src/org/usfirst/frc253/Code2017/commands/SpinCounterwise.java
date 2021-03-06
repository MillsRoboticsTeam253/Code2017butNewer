package org.usfirst.frc253.Code2017.commands;

import org.usfirst.frc253.Code2017.Robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SpinCounterwise extends Command {

	private AnalogGyro gyro;
	
    public SpinCounterwise(){
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetraintank);
    	requires(Robot.sensorData);
    	gyro = Robot.sensorData.gyro;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	gyro.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double gyroAngle = gyro.getAngle();
    	SmartDashboard.putNumber("Gyroscope Angle", gyroAngle);
    	if(gyroAngle > -110){
    		Robot.drivetraintank.setDriveTrain(.5, -.5);
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
    	Robot.drivetraintank.setDriveTrain(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetraintank.setDriveTrain(0, 0);
    }
}
