package gof.ch03_04.prototype;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 117-134. This class is not in the manual. This is an element in a new example
 * of both the {@linkplain gof.designpatterns.Prototype Prototype} design
 * pattern and the {@linkplain gof.designpatterns.Singleton Singleton} design
 * pattern. See the {@linkplain JobPriorityPrototypeFactory} JavaDoc, and the
 * manual, for more detail.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch03_04/prototype/UML%20Diagram.jpg"
 * /> </div>
 * 
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
