package gof.ch03_04.prototype;

import gof.ch03_04.prototype.BackgroundJobPriority.Interval;
import gof.ch03_04.prototype.BackgroundJobPriority.SampleSize;
import gof.designpatterns.Prototype;

/**
 * <div class="javadoc-text">Demonstrating the {@linkplain Prototype} design
 * pattern, which is discussed in Design Patterns [Gang of Four], pp. 117-126.
 * These classes being tested are mostly stubs, and do not appear in the manual.
 * The point is that the priorities for the Jobs are being stored, cloned,
 * retrieved, and dynamically updated, in a Prototype object.</div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Demo implements Prototype {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Beginning test of the JobPriorityPrototypeFactory program...");
		Thread.sleep(2000);

		AdTargetingBackgroundJob adTargetingBackgroundJob = new AdTargetingBackgroundJob();
		adTargetingBackgroundJob.run();
		UsageStatisticsBackgroundJob usageStatisticsBackgroundJob = new UsageStatisticsBackgroundJob();
		usageStatisticsBackgroundJob.run();
		UserNotificationBackgroundJob userNotificationBackgroundJob = new UserNotificationBackgroundJob();
		userNotificationBackgroundJob.run();

		System.out.println("Now evaluating Job performance, and recalculating priorities and resources...");
		System.out.println("This may take some time...");
		System.out.println("");
		Thread.sleep(2000);

		JobPriorityPrototypeFactory prioritiesFactory = JobPriorityPrototypeFactory.getInstance();

		System.out.println("Raising priorities for Ad Targeting:");
		AdTargetingJobPriority adTargetingJobPriority = prioritiesFactory.getAdTargetingJobPriority();
		adTargetingJobPriority.setInterval(Interval.DAILY);
		adTargetingJobPriority.setSampleSize(SampleSize.TEN_PERCENT);
		prioritiesFactory.setAdTargetingJobPriority(adTargetingJobPriority);
		System.out.println(adTargetingJobPriority.toString());
		System.out.println("");

		System.out.println("Raising priorities for Usage Statistics:");
		UsageStatisticsJobPriority usageStatisticsJobPriority = prioritiesFactory.getUsageStatisticsJobPriority();
		usageStatisticsJobPriority.setInterval(Interval.WEEKLY);
		usageStatisticsJobPriority.setSampleSize(SampleSize.ENTIRETY);
		prioritiesFactory.setUsageStatisticsJobPriority(usageStatisticsJobPriority);
		System.out.println(usageStatisticsJobPriority.toString());
		System.out.println("");

		System.out.println("Lowering priorities for User Notification:");
		UserNotificationJobPriority userNotificationJobPriority = prioritiesFactory.getUserNotificationJobPriority();
		userNotificationJobPriority.setInterval(Interval.HOURLY);
		userNotificationJobPriority.setSampleSize(SampleSize.ONE_PERCENT);
		prioritiesFactory.setUserNotificationJobPriority(userNotificationJobPriority);
		System.out.println(userNotificationJobPriority.toString());
		System.out.println("");

		adTargetingBackgroundJob.run();
		usageStatisticsBackgroundJob.run();
		userNotificationBackgroundJob.run();

		System.out.println("Good, test successful.");
	}
}
