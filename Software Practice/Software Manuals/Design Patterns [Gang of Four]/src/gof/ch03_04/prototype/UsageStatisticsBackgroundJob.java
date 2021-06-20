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
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class UsageStatisticsBackgroundJob {
	public void run() throws InterruptedException {

		System.out.println("UsageStatisticsBackgroundJob is waking up...");
		System.out.println("Checking my priorities before I begin...");
		System.out.println("");
		Thread.sleep(2000);

		JobPriorityPrototypeFactory priorities = JobPriorityPrototypeFactory.getInstance();
		BackgroundJobPriority adPriorities = priorities.getUsageStatisticsJobPriority();
		System.out.println(adPriorities.toString());
		System.out.println("");
		Thread.sleep(2000);

		System.out.println("Now beginning time-consuming calculations for Usage Statistics...");
		Thread.sleep(2000);
		System.out.println("Done, going back to sleep.");
		System.out.println("");
	}

	public static class Demo {
		public static void main(String[] args) throws InterruptedException {
			new UsageStatisticsBackgroundJob().run();
		}
	}
}
