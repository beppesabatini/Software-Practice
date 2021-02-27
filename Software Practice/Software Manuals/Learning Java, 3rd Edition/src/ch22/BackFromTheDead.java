package ch22;

import javax.swing.*;
import java.beans.*;

import utils.LearningJava3Utils;

/**
 * From Learning Java, 3rd Edition, p. 780-781. The manual is wrong on this one.
 * This class is supposed to be deserializing a bean which had been serialized
 * to disk, but in fact this function works just fine even if the serialized
 * file has been deleted. In other words this function has no idea where to find
 * the serialized object, and never looks for it, and only instantiates the
 * class definition.
 * 
 */
@SuppressWarnings("unused")
public class BackFromTheDead extends JFrame {

	private static final long serialVersionUID = -6754358783224072412L;

	/**
	 * 
	 * @param name The full path for the class you are trying to deserialize, in
	 *             fact "magicbeans.sunw.demo.juggler.Juggler". The method doesn't
	 *             work correctly, though, it doesn't so much as look at the
	 *             serialized object. It just whips it up from thin air using the
	 *             class definition.
	 */
	public BackFromTheDead(String name) {
		super("Revived Beans!");
		try {
			Object bean = Beans.instantiate(getClass().getClassLoader(), name);

			if (Beans.isInstanceOf(bean, JComponent.class)) {
				JComponent comp = (JComponent) Beans.getInstanceOf(bean, JComponent.class);
				getContentPane().add("Center", comp);
			} else {
				System.out.println("Bean is not a Component...");
			}
		} catch (java.io.IOException e1) {
			System.out.println("Error loading the serialized object");
		} catch (ClassNotFoundException e2) {
			System.out.println("Can't find the class that goes with the object");
		}
	}

	public static void main(String[] args) {
		LearningJava3Utils.confirmContinueWithDisfunctional();
		JFrame frame = new BackFromTheDead(args[0]);
		frame.pack();
		// frame.setSize(300,300);
		frame.setVisible(true);
	}
}
