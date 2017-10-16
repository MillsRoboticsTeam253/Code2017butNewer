package org.usfirst.frc253.Code2017.commands;

import org.usfirst.frc253.Code2017.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class NudgeLeft extends Command {

    public NudgeLeft() {
        
    	//Subsystem requirements
    	requires(Robot.drivetraintank);
    }

    protected void initialize() {
    }

    protected void execute() {
    	
    	//Counterclockwise rotation
    	Robot.drivetraintank.setDriveTrain(.25, -.25);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	
    	//Stops drive train
    	Robot.drivetraintank.setDriveTrain(0, 0);
    }

    protected void interrupted() {
    }
}
