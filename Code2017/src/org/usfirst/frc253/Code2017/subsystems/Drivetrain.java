
package org.usfirst.frc253.Code2017.subsystems;

import org.usfirst.frc253.Code2017.Robot;
import org.usfirst.frc253.Code2017.RobotMap;
<<<<<<< HEAD:Code2017/src/org/usfirst/frc253/Code2017/subsystems/Drivetrain.java
import org.usfirst.frc253.Code2017.commands.TankDrive;

import edu.wpi.first.wpilibj.Talon;
=======
import org.usfirst.frc253.Code2017.subsystems.AltDrive;
>>>>>>> ThrottleDrive:Code2017/src/org/usfirst/frc253/Code2017/commands/Drivetrain.java
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem {
    
	//Motor declarations
	private Talon driveMotorLeft;
	private Victor driveMotorLeft_Back;
	private VictorSP driveMotorRight;
	private Talon driveMotorRight_Back;
	//These are referenced in Drivetrain()
	
	Command driveTrain;
	
	public Drivetrain() {
		
		//Creates motor objects
		super();
		driveMotorLeft = new Talon(RobotMap.driveMotorLeft);
		driveMotorLeft_Back = new  Victor(RobotMap.driveMotorLeft_Back);
		driveMotorRight = new VictorSP(RobotMap.driveMotorRight);
		driveMotorRight_Back = new Talon(RobotMap.driveMotorRight_Back);
		
		this.driveTrain = (Command) Robot.driveChooser.getSelected();
	}
	
    public void initDefaultCommand() {
<<<<<<< HEAD:Code2017/src/org/usfirst/frc253/Code2017/subsystems/Drivetrain.java
        setDefaultCommand(this.driveTrain);
    }
    
    public void setDriveTrain(double left, double right) {
    	driveMotorLeft.set(left * RobotMap.driveMotorLeftDir);
    	driveMotorLeft_Back.set(left * RobotMap.driveMotorLeftBackDir);
    	driveMotorRight.set(right * RobotMap.driveMotorRightDir);
    	driveMotorRight_Back.set(right * RobotMap.driveMotorRightBackDir);
=======
        setDefaultCommand(new AltDrive());
>>>>>>> ThrottleDrive:Code2017/src/org/usfirst/frc253/Code2017/commands/Drivetrain.java
    }
    
    public void setLeft(double power) {
    	driveMotorLeft.set(power * RobotMap.driveMotorLeftDir);
    }
    
    public void setLeft_Back(double power) {
    	driveMotorLeft_Back.set(power * RobotMap.driveMotorLeftBackDir);
    }
    public void setRight(double power) {
    	driveMotorRight.set(power * RobotMap.driveMotorRightDir);
    	
    }
    public void setRight_Back(double power) {
    	driveMotorRight_Back.set(power * RobotMap.driveMotorRightBackDir);
    }
    
    public double getLeftSpeed() {
    	return driveMotorLeft.getSpeed();
    }
    public double getLeft_BackSpeed() {
    	return driveMotorLeft_Back.getSpeed();
    }
    
    public double getRightSpeed() {
    	return driveMotorRight.getSpeed();
    }
    public double getRight_BackSpeed() {
    	return driveMotorRight_Back.getSpeed();
    }

}