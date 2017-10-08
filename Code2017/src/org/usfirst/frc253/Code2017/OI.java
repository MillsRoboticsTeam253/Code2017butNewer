// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc253.Code2017;

import org.usfirst.frc253.Code2017.commands.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {
	
	//Joystick and JoystickButton declarations
	public Joystick leftJoystick;
    public Joystick rightJoystick;
    public Joystick buttonBoard;
    
    public JoystickButton StraightDrive;
    public JoystickButton ReverseStraightDrive;
    public JoystickButton NudgeLeft;
    public JoystickButton NudgeRight;
    public JoystickButton ManualAuto;
    
    public JoystickButton GearAutoCorrect;
    public JoystickButton GearBackUp;
    public JoystickButton DeployGear;
    public JoystickButton Retract;
    
    public JoystickButton ServoSpinForward;
    public JoystickButton ServoSpinBackward;
//    public JoystickButton ServoSpin;
    
    public JoystickButton ReadSensors;
    
    public JoystickButton ClimbUp;
    public JoystickButton ClimbDown;
    // These objects are referenced below in public OI()

    public OI() {
    	//Setting buttons to commands
    	rightJoystick = new Joystick(1);
        leftJoystick = new Joystick(0);
        buttonBoard =  new Joystick(2);
        
        //Manual movement buttons
        StraightDrive = new JoystickButton(buttonBoard, 4);
        StraightDrive.whileHeld(new StraightDrive());
        
        ReverseStraightDrive = new JoystickButton(buttonBoard, 5);
        ReverseStraightDrive.whileHeld(new ReverseStraightDrive());
        
        NudgeLeft = new JoystickButton(rightJoystick, 4);
        NudgeLeft.whileHeld(new NudgeLeft());
        
        NudgeRight = new JoystickButton(rightJoystick, 5);
        NudgeRight.whileHeld(new NudgeRight());
        
        ManualAuto = new JoystickButton(buttonBoard, 10);
        ManualAuto.whenPressed(new ManualAuto());
        
        //Gear system buttons
        DeployGear = new JoystickButton(rightJoystick, 1);
        DeployGear.whileHeld(new DeployGear());
        
        Retract = new JoystickButton(rightJoystick, 2);
        Retract.whileHeld(new Retract());
        
        GearAutoCorrect = new JoystickButton(buttonBoard, 2);
        GearAutoCorrect.whileHeld(new GearAutoCorrect());
        
        GearBackUp = new JoystickButton(buttonBoard, 3);
        GearBackUp.whileHeld(new GearBackUp());
        
        //Servo system buttons
        ServoSpinForward = new JoystickButton(buttonBoard, 9);
        ServoSpinForward.whileHeld(new ServoSpinForward());
        
        ServoSpinBackward = new JoystickButton(buttonBoard, 11);
        ServoSpinBackward.whileHeld(new ServoSpinBackward());
        
//        ServoSpin = new JoystickButton(leftJoystick, 3);
//        ServoSpin.whenPressed(new ServoSpin());
        
        ReadSensors = new JoystickButton(buttonBoard, 7);
        ReadSensors.whenPressed(new ReadSensors());
        
        ClimbUp = new JoystickButton(buttonBoard, 8);
        ClimbUp.whileHeld(new ClimbUpAccel());
        
        ClimbDown = new JoystickButton(buttonBoard, 1);
        ClimbDown.whileHeld(new ClimbDownAccel());
        
        SmartDashboard.putData("Go to Peg", new GoToPeg());
    }

    public Joystick getLeftJoystick() {
        return leftJoystick;
    }

    public Joystick getRightJoystick() {
        return rightJoystick;
    }

}

     