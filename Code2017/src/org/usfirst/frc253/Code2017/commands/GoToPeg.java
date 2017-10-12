package org.usfirst.frc253.Code2017.commands;

import org.usfirst.frc253.Code2017.Robot;
import org.usfirst.frc253.Code2017.vision.PathPlanner;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GoToPeg extends CommandGroup {

	double[][] waypoints;
	double totalTime = 2; //TODO add function to determine optimal total time
	final PathPlanner path;
	double[] leftVelocity;
	double[] rightVelocity;
	
	private final double[][] centerTest = new double[][]{
		{0,0},
		{7.8125,0}
	};
	
    public GoToPeg() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetraintank);
    	
    	this.waypoints = Robot.visionProcess.findWaypoints();
    	this.path = new PathPlanner(centerTest); //TODO change back to waypoints
    	this.path.calculate(totalTime, Robot.TIME_STEP, Robot.TRACK_WIDTH);
    	this.leftVelocity = PathPlanner.getYVector(path.smoothLeftVelocity);
    	this.rightVelocity = PathPlanner.getYVector(path.smoothRightVelocity);
    	
    	for(int i = 0; i < leftVelocity.length-1; i++) {
    		addSequential(new Step(leftVelocity[i] / 10, rightVelocity[i] / 10));
    	}
    }
}
