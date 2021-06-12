package gof.ch03_04.prototype;

import gof.designpatterns.Prototype;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 117-134. This class is not in the manual. This is an element in an example of
 * the {@linkplain Prototype} design pattern. See the
 * {@linkplain JobPriorityPrototypeFactory} JavaDoc, and the manual, for more
 * detail.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class AdTargetingJobPriority extends BackgroundJobPriority {

	public AdTargetingJobPriority() {
		this.setInterval(Interval.HOURLY);
		this.setSampleSize(SampleSize.ONE_PERCENT);
	}

	public AdTargetingJobPriority clone() {
		AdTargetingJobPriority cloneJob = new AdTargetingJobPriority();
		cloneJob.setInterval(this.getInterval());
		cloneJob.setSampleSize(this.getSampleSize());
		return (cloneJob);
	}

	public String toString() {
		String message = "";
		message += "In AdTargetingJobPriority: \n";
		message += "  Interval: " + this.getInterval().name() + "\n";
		message += "  Sample Size: " + this.getSampleSize().name();
		return (message);
	}
}