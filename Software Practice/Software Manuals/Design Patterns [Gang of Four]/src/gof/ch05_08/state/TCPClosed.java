package gof.ch05_08.state;

import gof.designpatterns.Singleton;
import gof.designpatterns.State;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 312. Part of the sample code to illustrate the {@linkplain State} design
 * pattern.</div>
 *
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class TCPClosed extends TCPState implements Singleton, State {

	private static TCPClosed instance;

	public static TCPClosed getInstance() {
		if (instance == null) {
			instance = new TCPClosed();
		}
		return (instance);
	}

	@Override
	public void activeOpen(TCPConnection tcpConnection) {
		// Send SYN, receive SYN, ACK, etc.
		System.out.println(" -- Running TCPClosed::activeOpen()");
		this.changeState(tcpConnection, TCPEstablished.getInstance());
	}

	@Override
	public void passiveOpen(TCPConnection tcpConnection) {
		System.out.println(" -- Running TCPClosed::passiveOpen()");
		this.changeState(tcpConnection, TCPListen.getInstance());
	}
}
