package gof.ch05_08.state;

import gof.ch05_08.state.StateSupport.*;
import gof.designpatterns.State;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 310-311. Part of the sample code to illustrate the {@linkplain State} design
 * pattern.
 * <p/>
 * The TCPState class is subclassed once for each of several States through
 * which the TCPConnection class travels. The basic response functions can be
 * redefined differently for each of the different States.</div>
 *
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public abstract class TCPState implements State {

	// Mostly defaults to be overridden:
	public void transmit(TCPConnection tcpConnection, TCPOctetStream tcpOctetStream) {
	};

	public void activeOpen(TCPConnection tcpConnection) {
	};

	public void passiveOpen(TCPConnection tcpConnection) {
	};

	public void close(TCPConnection tcpConnection) {
	};

	public void synchronize(TCPConnection tcpConnection) {
	};

	public void acknowledge(TCPConnection tcpConnection) {
	};

	public void send(TCPConnection tcpConnection) {
	};

	public void changeState(TCPConnection tcpConnection, TCPState tcpState) {
		String stateName = tcpState.getClass().getCanonicalName().split("\\.")[3];
		System.out.println(" -- Changing state to: " + stateName);
		tcpConnection.changeState(tcpState);
	}
}
