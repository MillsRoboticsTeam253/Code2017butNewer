package org.usfirst.frc253.Code2017.commands;

import org.usfirst.frc253.Code2017.Robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ReadSensors extends Command {

	//Declares sensors
	private Ultrasonic ultraLeft;
	private Ultrasonic ultraRight;
	private DigitalInput photo;
	private AnalogGyro gyro;
	
    public ReadSensors() {
    	requires(Robot.sensorData);
    	ultraLeft = Robot.sensorData.ultraLeft;
    	ultraRight = Robot.sensorData.ultraRight;
    	ultraLeft.setAutomaticMode(true);
    	ultraRight.setAutomaticMode(true);
    	photo = Robot.sensorData.photo;
    	gyro = Robot.sensorData.gyro;
    }

    protected void initialize() {
    }

    protected void execute() {
    	//Reads sensor data
    	double rangeLeft = ultraLeft.getRangeInches();
    	double rangeRight = ultraRight.getRangeInches();
    	boolean isGearAligned = photo.get();
    	double gyroAngle = gyro.getAngle();
    	SmartDashboard.putNumber("Gyroscope Angle", gyroAngle);
    	SmartDashboard.putNumber("Left Ultrasonic", rangeLeft);
    	SmartDashboard.putNumber("Right Ultrasonic", rangeRight);
    	SmartDashboard.putBoolean("Will the spoke collide with the gear?", isGearAligned);
    	
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
