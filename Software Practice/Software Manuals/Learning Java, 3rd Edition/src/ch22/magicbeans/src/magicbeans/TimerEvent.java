package ch22.magicbeans.src.magicbeans;

public class TimerEvent extends java.util.EventObject {

	private static final long serialVersionUID = 3130676208549784012L;

	int value;

	TimerEvent(Timer source) {
		super(source);
	}
}