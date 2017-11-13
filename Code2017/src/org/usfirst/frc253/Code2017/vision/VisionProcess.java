package org.usfirst.frc253.Code2017.vision;

import javax.annotation.processing.Processor;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc253.Code2017.Robot;

import edu.wpi.first.wpilibj.vision.VisionRunner;
import edu.wpi.first.wpilibj.vision.VisionRunner.Listener;

public class VisionProcess {
	
	static final int IMG_WIDTH = 640;
	static final int IMG_HEIGHT = 480;

	final Listener<GripPipeline> listener;
	public final VisionRunner<GripPipeline> gripRunner;
	
	private double pegX1 = 0.0;
	private double pegX2 = 0.0;
	
	public VisionProcess() {
		this.listener = processor -> {
			if (!processor.filterContoursOutput().isEmpty()) {
				Rect peg1 = Imgproc.boundingRect(processor.findContoursOutput().get(0));
				Rect peg2 = Imgproc.boundingRect(processor.findContoursOutput().get(1));
				pegX1 = peg1.x;
				pegX2 = peg2.x;
			}
		};
		this.gripRunner = new VisionRunner<>(
						Robot.camera,
						new GripPipeline(), 
						listener);
	}
	
	public double getPegX1(){
		return pegX1;
	}
	
	public double getPegX2(){
		return pegX2;
	}
}