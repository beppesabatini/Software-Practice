package ch22.magicbeans.src.magicbeans;

import java.beans.*;

/**
 * Not in the Learning Java manual.
 */
public class TimerBeanInfo extends SimpleBeanInfo {

	public PropertyDescriptor[] getPropertyDescriptors() {
		try {
			PropertyDescriptor delay = new PropertyDescriptor("delay", Timer.class);
			delay.setBound(false);

			PropertyDescriptor running = new PropertyDescriptor("running", Timer.class);

			return new PropertyDescriptor[] { delay, running };
		} catch (IntrospectionException e) {
			return null;
		}
	}

	public EventSetDescriptor[] getEventSetDescriptors() {
		try {
			EventSetDescriptor timer = new EventSetDescriptor(Timer.class, "timerFired", TimerListener.class, "timerFired");
			timer.setDisplayName("Timer Fired");

			return new EventSetDescriptor[] { timer };
		} catch (IntrospectionException e) {
			return null;
		}
	}

}
