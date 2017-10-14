package org.usfirst.frc253.Code2017.commands;

import org.usfirst.frc253.Code2017.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoSmoothCenter extends CommandGroup {
	
    public AutoSmoothCenter() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetraintank);
    	
    	final double[] leftVelocity = new double[]{ 0.0,
    												5.0380339289071445,
    												6.499942671025054,
    												7.293798160774711,
    												7.6842188915257585,
    												7.801345106765694,
    												7.68421888069578,
    												7.2937981422534355,
    												6.499942650664825,
    												5.038033914461834,
    												0.0};
    	
    	final double[] rightVelocity = new double[]{0.0, 
    												5.0380339289071445,
    												6.499942671025054,
    												7.293798160774711,
    												7.6842188915257585,
    												7.801345106765694,
    												7.68421888069578,
    												7.2937981422534355,
    												6.499942650664825,
    												5.038033914461834,
    												0.0};
    	
    	for(int i = 0; i < leftVelocity.length-1; i++) {
    		addSequential(new Step(leftVelocity[i] / 10.0, rightVelocity[i] / 10.0));
    	}
    	addSequential(new Pause());
    	addSequential(new AutoGearDeploy());
    	addSequential(new AutoRetract());
    	
    }
}
