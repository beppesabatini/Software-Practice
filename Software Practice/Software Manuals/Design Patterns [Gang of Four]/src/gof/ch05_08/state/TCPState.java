package gof.ch05_08.state;

import gof.ch05_08.state.StateSupport.*;
import gof.designpatterns.State;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 310-311. This class is one part of the sample code used to illustrate the
 * {@linkplain gof.designpatterns.State State} design pattern.
 * <p/>
 * The TCPState class is subclassed once for each of several States through
 * which the TCPConnection class travels. The basic response functions can be
 * redefined differently for each of the different States. Each State can change
 * itself into a different State when its work is done. Thus, the State design
 * pattern can be used to build up a functional flow chart of connected
 * States.</div>
 * 
 * </div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_08/state/UML%20Diagram.jpg"
 * /> </div>
 * 
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
