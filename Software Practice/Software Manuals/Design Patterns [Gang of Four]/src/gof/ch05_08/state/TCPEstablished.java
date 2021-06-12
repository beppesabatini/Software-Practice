package gof.ch05_08.state;

import gof.ch05_08.state.StateSupport.*;
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
public class TCPEstablished extends TCPState implements Singleton, State {

	static TCPEstablished instance;

	public static TCPState getInstance() {
		if (instance == null) {
			instance = new TCPEstablished();
		}
		return (instance);
	}

	public void close(TCPConnection tcpConnection) {
		// Send FIN, receive ACK of FIN.
		System.out.println(" -- Running TCPEstablished::close()");
		changeState(tcpConnection, TCPListen.getInstance());
	}

	public void transmit(TCPConnection tcpConnection) {
		TCPOctetStream tcpOctetStream = new StateSupport().new TCPOctetStream();
		transmit(tcpConnection, tcpOctetStream);
	}

	public void transmit(TCPConnection tcpConnection, TCPOctetStream tcpOctetStream) {
		System.out.println(" -- Running TCPEstablished::transmit()");
		tcpConnection.processOctet(tcpOctetStream);
	}
}
