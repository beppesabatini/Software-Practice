package ch22;

import ch22.magicbeans.src.magicbeans.sunw.demo.juggler.Juggler;
import java.io.*;

// From  p. 780.

public class SerializeJuggler {
	public static void main(String[] args) throws Exception {
		Juggler duke = new Juggler();
		ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream("output/juggler.ser"));
		oout.writeObject(duke);
		oout.close();
	}
}
