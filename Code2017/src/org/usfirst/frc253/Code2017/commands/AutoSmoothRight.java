package org.usfirst.frc253.Code2017.commands;

import org.usfirst.frc253.Code2017.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoSmoothRight extends CommandGroup {
	
    public AutoSmoothRight() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetraintank);
    	
    	final double[] leftVelocity = new double[]{ 0.0,
    												2.741883600215282,
    												3.86207472322545,
    												4.460534186486134,
    												4.7497097235444885,
    												4.8458217685554805,
    												4.810826847083649,
    												4.677825548273737,
    												4.467531875744372,
    												4.202708539408434,
    												3.918738421934395,
    												3.67590578628679,
    												3.5438394152200634,
    												3.5835418043711704,
    												3.7561352892253925,
    												3.999509883602034,
    												4.24854788118738,
    												4.459745115771671,
    												4.603004927299043,
    												4.653186419031242,
    												4.575512962144545,
    												4.309029698793852,
    												3.741264395901651,
    												2.6671886290425073,
    												0.0};
    	
    	final double[] rightVelocity = new double[]{0.0,
    												2.8173986089813767,
    												4.005715981156318,
    												4.690097058162856,
    												5.093269570820705,
    												5.342785433538236,
    												5.512442847524216,
    												5.644990671210519,
    												5.764526229592841,
    												5.880180652249843,
    												5.9878517085807506,
    												6.063679857053408,
    												6.078393721537351,
    												6.001042521647629,
    												5.873915395772152,
    												5.731517450393232,
    												5.5967780490720385,
    												5.470226239360329,
    												5.338775986166914,
    												5.1756910421196665,
    												4.937288945036455,
    												4.550866960262487,
    												3.8924076368584153,
    												2.746247730133493,
    												0.0};
    	
    	for(int i = 0; i < leftVelocity.length-1; i++) {
    		addSequential(new Step(leftVelocity[i] / 10.0, rightVelocity[i] / 10.0));
    	}
    	addSequential(new Pause());
    	addSequential(new AutoGearDeploy());
    	addSequential(new AutoRetract());
    }
}
