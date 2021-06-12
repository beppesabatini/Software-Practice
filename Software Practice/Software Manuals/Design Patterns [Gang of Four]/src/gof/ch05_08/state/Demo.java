package gof.ch05_08.state;

import gof.designpatterns.State;

/**
 * <div style="width: 580px;">Adapted from Design Patterns [Gang of Four], pp.
 * 309-312. Part of the sample code to illustrate the {@linkplain State} design
 * pattern. This Demo class does not appear in the manual.</div>
 *
 * <pre></pre>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Demo {

	public static void main(String args[]) {
		System.out.println(" • Opening a TCP Connection:");
		TCPConnection tcpConnection = new TCPConnection();
		tcpConnection.activeOpen();
		System.out.println();

		System.out.println(" • Connection has timed out, close:");
		tcpConnection.close();
		System.out.println();

		System.out.println(" • Try and reopen:");
		tcpConnection.send();
		System.out.println();

		System.out.println(" • Send a message:");
		tcpConnection.transmit();
		System.out.println();

		System.out.println(" • Close the connection and reopen:");
		tcpConnection.changeState(TCPClosed.getInstance());
		tcpConnection.passiveOpen();
		System.out.println();

	}
}
