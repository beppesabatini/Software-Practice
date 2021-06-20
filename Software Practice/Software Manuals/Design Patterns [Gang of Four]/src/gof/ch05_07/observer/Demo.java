package gof.ch05_07.observer;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 303. One class in the sample code that demonstrates the
 * {@linkplain gof.designpatterns.Observer Observer} or
 * <b>publisher-subscriber</b> design pattern. This class is not in the manual;
 * it is provided to let clients test the sample code.</div>
 *
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Demo {

	public static void main(String[] args) throws InterruptedException {
		System.out.println(" • Creating a Subject, ClockTimer");
		ClockTimer clockTimer = new ClockTimer("The Precise Clock Timer");
		System.out.println();

		System.out.println(" • Creating an Observer, AnalogClock");
		new AnalogClock(clockTimer);
		System.out.println();

		System.out.println(" • Creating an Observer, DigitalClock");
		new DigitalClock(clockTimer);
		System.out.println();

		System.out.println(" • Starting the ClockTimer ticking: ");
		for (int i = 0; i < 3; i++) {
			clockTimer.tick();
			Thread.sleep(1000);
		}
	}
}
