package ch22;

import magicbeans.sunw.demo.juggler.Juggler;
import java.io.*;

/**
 * From Learning Java, 3rd Edition, p. 780. A trivial example of serializing
 * (NetBeans uses it in some contexts). Note that the "Juggler" class is in the
 * magicbeans.jar, which is checked in to the same directory as this file you
 * are reading. The serialized output is supposed to be deserialized with the
 * class "BackFromTheDead," also in this same directory, but that function is
 * irrelevant.
 * 
 */
public class SerializeJuggler {
	public static void main(String[] args) throws Exception {
		System.out.println("Current dir: " + System.getProperty("user.dir"));
		Juggler duke = new Juggler();

		ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream("src/output/juggler.ser"));
		oout.writeObject(duke);
		oout.close();
	}
}
