package gof.ch03_04.prototype;

import gof.designpatterns.Prototype;
import gof.designpatterns.Singleton;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 117-134. This class is not in the manual. This is an element in an example of
 * both the {@linkplain Prototype} design pattern and the {@linkplain Singleton}
 * design pattern. See the {@linkplain JobPriorityPrototypeFactory} JavaDoc, and
 * the manual, for more detail.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css"> s
 */
public abstract class BackgroundJobPriority {

	protected enum Interval {
		HOURLY, DAILY, WEEKLY
	};

	protected enum SampleSize {
		ONE_PERCENT, TEN_PERCENT, ENTIRETY
	};

	private Interval interval;
	private SampleSize sampleSize;

	protected Interval getInterval() {
		return interval;
	}

	protected void setInterval(Interval interval) {
		this.interval = interval;
	}

	protected SampleSize getSampleSize() {
		return sampleSize;
	}

	protected void setSampleSize(SampleSize sampleSize) {
		this.sampleSize = sampleSize;
	}

//	protected BackgroundJobPriority clone() {
//		BackgroundJobPriority backgroundJobPriority = null;
//		try {
//			backgroundJobPriority = (BackgroundJobPriority) super.clone();
//		} catch(CloneNotSupportedException ex) {
//			System.err.println("Sorry, could not clone the backgroundJobPriority information.");
//		}
//		return (backgroundJobPriority);
//	}
}
