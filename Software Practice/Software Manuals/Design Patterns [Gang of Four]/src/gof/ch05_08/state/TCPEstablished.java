package gof.ch05_08.state;

import gof.ch05_08.state.StateSupport.*;
import gof.designpatterns.Singleton;
import gof.designpatterns.State;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 311-312. This class is one part of the sample code used to illustrate the
 * {@linkplain gof.designpatterns.State State} design pattern.
 * <p/>
 * Each State can change itself into a different State when its work is done.
 * Thus, the State design pattern can be used to build up a functional flow
 * chart of connected States.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_08/state/UML%20Diagram.jpg"
 * /> </div>
 * 
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
