package gof.ch05_08.state;

import gof.ch05_08.state.StateSupport.*;
import gof.designpatterns.State;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 309-310. Part of the sample code to illustrate the {@linkplain State} design
 * pattern.
 * <p/>
 * The TCPConnection class passes through many of the requests it receives to
 * its nested State object. Responses can be retailored and redefined in each
 * State class. The Connection and the States themselves can both change the
 * current State to a different State class, in response to circumstances.</div>
 *
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class TCPConnection implements State {

	protected TCPState tcpState;

	public TCPConnection() {
		this.tcpState = TCPClosed.getInstance();
	}

	public void activeOpen() {
		this.tcpState.activeOpen(this);
	}

	public void passiveOpen() {
		this.tcpState.passiveOpen(this);
	}

	public void close() {
		this.tcpState.close(this);
	}

	public void send() {
		this.tcpState.send(this);
	}

	public void acknowledge() {
		this.tcpState.acknowledge(this);
	}

	public void synchronize() {
		this.tcpState.synchronize(this);
	}

	public void processOctet(TCPOctetStream tcpOctetStream) {

	}

	public void transmit() {
		TCPOctetStream tcpOctetStream = new StateSupport().new TCPOctetStream();
		this.tcpState.transmit(this, tcpOctetStream);
	}

	protected void changeState(TCPState tcpState) {
		String stateName = tcpState.getClass().getCanonicalName().split("\\.")[3];
		System.out.println(" -- In TCPConnection, changing state to: " + stateName);
		this.tcpState = tcpState;
	}
}
