package org.usfirst.frc253.Code2017.commands;

import org.usfirst.frc253.Code2017.Robot;
import org.usfirst.frc253.Code2017.vision.PathPlanner;
import org.usfirst.frc253.Code2017.vision.VisionProcess;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GoToPeg extends CommandGroup {
	
	private final double[][] centerTest = new double[][]{
		{0,0},
		{7.8125,0}
	};
	
    public GoToPeg() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetraintank);
    	
    	double[][] waypoints = Robot.visionProcess.findWaypoints();
    	final PathPlanner path = new PathPlanner(waypoints);
    	double distanceDirect = Math.hypot(waypoints[3][0], waypoints[3][1]);
    	double totalTime = distanceDirect/5.0; //for robot speed 5 ft/s
    	path.calculate(totalTime, Robot.TIME_STEP, Robot.TRACK_WIDTH);
    	double[] leftVelocity = PathPlanner.getYVector(path.smoothLeftVelocity);
    	double[] rightVelocity = PathPlanner.getYVector(path.smoothRightVelocity);
    	
    	for(int i = 0; i < leftVelocity.length-1; i++) {
    		addSequential(new Step(leftVelocity[i] / 10.0, rightVelocity[i] / 10.0));
    	}
    }
}
