package ch22.magicbeans.src.magicbeans;

/**
 * Not in the Learning Java manual.
 */
public interface TimerListener extends java.util.EventListener {
	void timerFired(TimerEvent e);
}
