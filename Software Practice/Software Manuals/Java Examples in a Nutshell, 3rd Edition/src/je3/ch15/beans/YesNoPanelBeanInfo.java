/*
 * Copyright (c) 2004 David Flanagan.  All rights reserved.
 * This code is from the book Java Examples in a Nutshell, 3nd Edition.
 * It is provided AS-IS, WITHOUT ANY WARRANTY either expressed or implied.
 * You may study, use, and modify it for any non-commercial purpose,
 * including teaching and use in open-source projects.
 * You may distribute it non-commercially as long as you retain this notice.
 * For a commercial use license, or to purchase the book, 
 * please visit http://www.davidflanagan.com/javaexamples3.
 */
package je3.ch15.beans;

import java.awt.Image;
import java.beans.SimpleBeanInfo;
import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 469-470. This BeanInfo
 * class provides additional information about the {@link YesNoPanel} bean in
 * addition to what can be obtained through introspection alone. Test this by
 * launching the YesNoPanel Demo, and observing the mouse-over descriptions and
 * the icon.
 */
public class YesNoPanelBeanInfo extends SimpleBeanInfo {
	/**
	 * Return an icon for the bean. We should really check the kind argument to see
	 * what size icon the beanbox wants, but since we only have one icon to offer,
	 * we just return it and let the beanbox deal with it.
	 */
	public Image getIcon(int kind) {
		return loadImage("images/61+Yyszc3LL.ed02.png");
	}

	/**
	 * Return a descriptor for the bean itself. It specifies a customizer for the
	 * bean class. We could also add a description string here.
	 */
	public BeanDescriptor getBeanDescriptor() {
		return new BeanDescriptor(YesNoPanel.class, YesNoPanelCustomizer.class);
	}

	/** This is a convenience method for creating PropertyDescriptor objects. */
	static PropertyDescriptor prop(String name, String description) {
		try {
			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(name, YesNoPanel.class);
			propertyDescriptor.setShortDescription(description);
			return propertyDescriptor;
		} catch (IntrospectionException introspectionException) {
			introspectionException.printStackTrace(System.err);
			return null;
		}
	}

	/**
	 * Initialize a static array of PropertyDescriptor objects that provide
	 * additional information about the properties supported by the bean. By
	 * explicitly specifying property descriptors, we are able to provide simple
	 * help strings for each property; these would not be available to the bean box
	 * through simple introspection. The downside is that some of our variable names
	 * such as "messageText" are exposed to the end-users as menu items.
	 * <p/>
	 * We are also able to register a special property editor for the messageText
	 * property, the {@link YesNoPanelMessageEditor}.
	 */
	static PropertyDescriptor[] props = initPropertyDescriptors();

	static PropertyDescriptor[] initPropertyDescriptors() {

		PropertyDescriptor[] propertyDescriptors = new PropertyDescriptor[9];
		propertyDescriptors[0] = prop("messageText", "The prominent text in the widget");
		propertyDescriptors[1] = prop("alignment", "The alignment of the prominent text");
		propertyDescriptors[2] = prop("yesLabel", "The label for the Yes button");
		propertyDescriptors[3] = prop("noLabel", "The label for the No button");
		propertyDescriptors[4] = prop("cancelLabel", "The label for the Cancel button");
		propertyDescriptors[5] = prop("messageFont", "The font for the prominent text");
		propertyDescriptors[6] = prop("messageColor", "The color of the prominent text");
		propertyDescriptors[7] = prop("buttonFont", "The font for the buttons");
		propertyDescriptors[8] = prop("background", "The background color");

		return propertyDescriptors;
	}

	static {
 		props[0].setPropertyEditorClass(YesNoPanelMessageEditor.class);
	}

	/** Return the property descriptors for this bean. */
	public PropertyDescriptor[] getPropertyDescriptors() {
		return props;
	}

	/** The message property is most often customized; make it the default. */
	public int getDefaultPropertyIndex() {
		return 0;
	}

	public static class Demo {
		public static void main(String[] args) {
			YesNoPanel.Demo.main(args);
			System.out.print("Demo for MultiLineLabel as used in the YesNoPanel. ");
		}
	}
}
