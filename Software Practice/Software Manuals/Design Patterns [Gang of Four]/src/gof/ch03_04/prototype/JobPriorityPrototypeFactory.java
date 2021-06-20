package gof.ch03_04.prototype;

import gof.designpatterns.Prototype;
import gof.designpatterns.Singleton;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 117-134. This class is not in the manual. This is an instance of both the
 * {@linkplain gof.designpatterns.Prototype Prototype} design pattern and the
 * {@linkplain gof.designpatterns.Singleton Singleton} design pattern. This is a
 * Prototype (a little like a clone manager), because it stores the original
 * results of expensive calculations--prioritizing different jobs--and clones
 * and dispenses copies of the results as required. It is a Singleton, because
 * the same (expensive) calculations are relevant everywhere, and so are stored
 * in one unique instance of an object, which is widely accessible. The UML
 * Diagram is below.
 * <p/>
 * The end products of the Jobs--such as usage statistics, or which ads should
 * be targeted where--are probably being stored in a database, but if concise
 * enough they could be kept here in the Prototype also.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch03_04/prototype/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class JobPriorityPrototypeFactory implements Prototype, Singleton {

	private static JobPriorityPrototypeFactory instance = null;

	private static AdTargetingJobPriority currentAdTargetingJobPriority = new AdTargetingJobPriority();
	private static UserNotificationJobPriority currentUserNotificationJobPriority = new UserNotificationJobPriority();
	private static UsageStatisticsJobPriority currentUsageStatisticsJobPriority = new UsageStatisticsJobPriority();

	public synchronized AdTargetingJobPriority getAdTargetingJobPriority() {
		return currentAdTargetingJobPriority.clone();
	}

	public synchronized void setAdTargetingJobPriority(AdTargetingJobPriority adTargetingJobPriority) {
		currentAdTargetingJobPriority = adTargetingJobPriority;
	}

	public synchronized UserNotificationJobPriority getUserNotificationJobPriority() {
		return currentUserNotificationJobPriority.clone();
	}

	public synchronized void setUserNotificationJobPriority(UserNotificationJobPriority userNotificationJobPriority) {
		currentUserNotificationJobPriority = userNotificationJobPriority;
	}

	public UsageStatisticsJobPriority getUsageStatisticsJobPriority() {
		return currentUsageStatisticsJobPriority.clone();
	}

	public void setUsageStatisticsJobPriority(UsageStatisticsJobPriority usageStatisticsJobPriority) {
		currentUsageStatisticsJobPriority = usageStatisticsJobPriority;
	}

	private JobPriorityPrototypeFactory() {
		// No one's allowed to call this because this is a Singleton.
	}

	public static JobPriorityPrototypeFactory getInstance() {
		if (instance == null) {
			instance = new JobPriorityPrototypeFactory();
		}
		return (instance);
	}

	static public class Demo {
		static public void main(String[] args) throws InterruptedException {
			gof.ch03_04.prototype.Demo.main(args);
		}
	}
}
