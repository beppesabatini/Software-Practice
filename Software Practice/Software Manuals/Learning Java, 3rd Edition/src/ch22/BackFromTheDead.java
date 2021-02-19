package ch22;

import javax.swing.*;
import java.beans.*;

// From p. 780-781.

public class BackFromTheDead extends JFrame {

	private static final long serialVersionUID = -6754358783224072412L;

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
		JFrame frame = new BackFromTheDead(args[0]);
		frame.pack();
		// frame.setSize(300,300);
		frame.setVisible(true);
	}
}
