package ch03;

import java.net.*;

/* 
 * This program is supposed to be "evil" because it accesses
 * another computer on the Internet. Two run configurations are 
 * defined; one runs with no security, and the other runs with the 
 * default java.security.manager. The function succeeds with no 
 * security, and fails when security is enforced. Note that when no 
 * run configuration has been invoked, but only one run configuration 
 * has been defined, Eclipse defaults to invoking the defined one.
 * 
 */

public class EvilEmpire {
	public static void main(String[] arguments) throws Exception {
		Socket s = null;
		try {
			// To get this IP address I ran ipconfig and arp -a,
			// and picked the first IP address.
			s = new Socket("192.168.1.1", 80);
			System.out.println("Connected!");
		} catch (SecurityException e) {
			System.out.println("SecurityException: could not connect.");
		}
		if (s != null) {
			System.out.println("Closing socket connection");
			s.close();
		}
	}
}
