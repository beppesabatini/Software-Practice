package gof.ch05_08.state;

import gof.designpatterns.Singleton;
import gof.designpatterns.State;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 311-312. Part of the sample code to illustrate the {@linkplain State} design
 * pattern.</div>
 *
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class TCPListen extends TCPState implements Singleton, State {

	static TCPListen instance;

	public static TCPState getInstance() {
		if (instance == null) {
			instance = new TCPListen();
		}
		return (instance);
	}

	@Override
	public void send(TCPConnection tcpConnection) {
		// Send SYN, receive SYN, ACK, etc.
		System.out.println(" -- Running TCPListen::send()");
		changeState(tcpConnection, TCPEstablished.getInstance());
	}
}
