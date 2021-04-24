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
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.BeanInfo;
import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.beans.XMLDecoder;
import java.io.InputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 478-485. This class
 * encapsulates a bean object and its {@link BeanInfo}. It is a key part of the
 * {@link ShowBean} "beanbox" program, and demonstrates how to instantiate and
 * instrospect beans, and how to use reflection to set properties and invoke
 * methods. It also illustrates how to work with {@link PropertyEditor} classes.
 */
public class Bean {
	// The bean object we encapsulate:
	private Object bean;
	// Information about beans of that type:
	private BeanInfo info;
	// Map property names to PropertyDescriptor objects:
	private Map<String, PropertyDescriptor> properties;
	// Map command names to MethodDescriptor objects:
	private Map<String, MethodDescriptor> commands;
	// Whether to include "expert" properties and commands:
	@SuppressWarnings("unused")
	private boolean expert;

	// Utility object used when invoking no-arg methods.
	private static final Object[] NOARGS = new Object[0];

	/**
	 * This constructor introspects the specified component. Typically you would use
	 * one of the static factory methods instead.
	 */
	public Bean(Object bean, boolean expert) throws IntrospectionException {
		// The object to instrospect:
		this.bean = bean;
		// Is the end-user an expert?:
		this.expert = expert;
		// Introspect to get BeanInfo for the bean:
		info = Introspector.getBeanInfo(bean.getClass());

		// Now create a map of property names to PropertyDescriptor objects.
		properties = new HashMap<String, PropertyDescriptor>();
		PropertyDescriptor[] propertyDescriptors = info.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			/*
			 * Skip hidden properties, indexed properties, and expert properties, unless the
			 * end-user is an expert.
			 */
			if (propertyDescriptors[i].isHidden()) {
				continue;
			}
			if (propertyDescriptors[i] instanceof IndexedPropertyDescriptor) {
				continue;
			}
			if (expert == false && propertyDescriptors[i].isExpert()) {
				continue;
			}
			properties.put(propertyDescriptors[i].getDisplayName(), propertyDescriptors[i]);
		}

		/*
		 * Create a map of command names to MethodDescriptor objects Commands are
		 * methods with no arguments and no return value. We skip commands defined in
		 * Object, Component, Container, and JComponent because they contain methods
		 * that meet this definition but are not intended for end-users.
		 */
		commands = new HashMap<String, MethodDescriptor>();
		MethodDescriptor[] methodDescriptors = info.getMethodDescriptors();
		for (int i = 0; i < methodDescriptors.length; i++) {
			// Skip the method if it is hidden or expert (unless the user is expert).
			if (methodDescriptors[i].isHidden()) {
				continue;
			}
			if (expert == false && methodDescriptors[i].isExpert()) {
				continue;
			}
			Method method = methodDescriptors[i].getMethod();
			// Skip the method if it has arguments or a return value.
			if (method.getParameterTypes().length > 0) {
				continue;
			}
			if (method.getReturnType() != Void.TYPE) {
				continue;
			}
			/*
			 * Check the declaring class, and skip unhelpful superclasses. The declaring
			 * class is the class where the method lives.
			 */
			Class<?> declaringClass = method.getDeclaringClass();
			if (declaringClass == JComponent.class || declaringClass == Component.class) {
				continue;
			}
			if (declaringClass == Container.class || declaringClass == Object.class) {
				continue;
			}
			// Get the unqualifed class name with which to prefix the method name.
			String classname = declaringClass.getName();
			classname = classname.substring(classname.lastIndexOf('.') + 1);
			// Otherwise, this is a valid command, so add it to the list.
			commands.put(classname + "." + method.getName(), methodDescriptors[i]);
		}
	}

	/** Factory method to instantiate a bean from a named class. */
	public static Bean forClassName(String className, boolean expert)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IntrospectionException {
		// Load the named bean class.
		Class<?> namedClass = Class.forName(className);
		// Instantiate it to create the component instance.
		Object bean = null;
		try {
			bean = namedClass.getDeclaredConstructor().newInstance();
		} catch (InvocationTargetException instantiationException) {
			instantiationException.printStackTrace(System.err);
		} catch (NoSuchMethodException noSuchMethodException) {
			noSuchMethodException.printStackTrace(System.err);
		}

		return new Bean(bean, expert);
	}

	/** Factory method to read a serialized bean. */
	public static Bean fromSerializedStream(ObjectInputStream in, boolean expert)
			throws IOException, ClassNotFoundException, IntrospectionException {
		return new Bean(in.readObject(), expert);
	}

	/** Factory method to read a persistent XMLEncoded bean from a stream. */
	public static Bean fromPersistentStream(InputStream inputStream, boolean expert) throws IntrospectionException {
		XMLDecoder xmlDecoder = new XMLDecoder(inputStream);
		Object beanObject = xmlDecoder.readObject();
		xmlDecoder.close();
		Bean streamedBean = new Bean(beanObject, expert);
		return streamedBean;
	}

	/** Return the bean object itself. */
	public Object getBean() {
		return bean;
	}

	/** Return the name of the bean. */
	public String getDisplayName() {
		return info.getBeanDescriptor().getDisplayName();
	}

	/** Return an icon for the bean. */
	public Image getIcon() {
		Image icon = info.getIcon(BeanInfo.ICON_COLOR_32x32);
		if (icon != null) {
			return icon;
		} else {
			return info.getIcon(BeanInfo.ICON_COLOR_16x16);
		}
	}

	/** Return a short description for the bean. */
	public String getShortDescription() {
		return info.getBeanDescriptor().getShortDescription();
	}

	/**
	 * Return an alphabetized list of property names for the bean. Note the elegant
	 * use of the Collections Framework.
	 */
	public List<String> getPropertyNames() {
		// Make a List from a Set (from a Map), and sort it before returning.
		List<String> names = new ArrayList<String>(properties.keySet());
		Collections.sort(names);
		return names;
	}

	/**
	 * Return an alphabetized list of command names for the bean.
	 */
	public List<String> getCommandNames() {
		List<String> names = new ArrayList<String>(commands.keySet());
		Collections.sort(names);
		return names;
	}

	/** Get a description of a property. Useful for tool tips. */
	public String getPropertyDescription(String name) {
		PropertyDescriptor propertyDescriptor = properties.get(name);
		if (propertyDescriptor == null) {
			throw new IllegalArgumentException(name);
		}
		return propertyDescriptor.getShortDescription();
	}

	/**
	 * Get a description of a command. Useful for tool tips.
	 */
	public String getCommandDescription(String name) {
		MethodDescriptor methodDescriptor = commands.get(name);
		if (methodDescriptor == null) {
			throw new IllegalArgumentException(name);
		}
		return methodDescriptor.getShortDescription();
	}

	/**
	 * Return true if the named property is read-only.
	 */
	public boolean isReadOnly(String name) {
		PropertyDescriptor propertyDescriptor = properties.get(name);
		if (propertyDescriptor == null) {
			throw new IllegalArgumentException(name);
		}
		return propertyDescriptor.getWriteMethod() == null;
	}

	/** Invoke the named (no-arg) method of the bean. */
	public void invokeCommand(String name) throws IllegalAccessException, InvocationTargetException {
		MethodDescriptor methodDescriptor = commands.get(name);
		if (methodDescriptor == null) {
			throw new IllegalArgumentException(name);
		}
		Method method = methodDescriptor.getMethod();
		method.invoke(bean, NOARGS);
	}

	/**
	 * Return the value of the named property as a string. This method relies on the
	 * toString() method of the returned value. A more robust implementation might
	 * use a PropertyEditor.
	 */
	public String getPropertyValue(String name) throws IllegalAccessException, InvocationTargetException {
		PropertyDescriptor propertyDescriptor = properties.get(name);
		if (propertyDescriptor == null) {
			throw new IllegalArgumentException(name);
		}
		// Property accessor method:
		Method readMethod = propertyDescriptor.getReadMethod();
		// Invoke the method to get the value:
		Object value = readMethod.invoke(bean, NOARGS);
		if (value == null) {
			return "null";
		}
		// Use the toString method():
		return value.toString();
	}

	/**
	 * Set the named property to the named value, if possible. This method knows how
	 * to convert a handful of well-known types. It attempts to use a PropertyEditor
	 * for those types it does not know about, but this only works for editors that
	 * have working setAsText() methods.
	 */
	public void setPropertyValue(String propertyName, String propertyValue)
			throws IllegalAccessException, InvocationTargetException {
		// Get the descriptor for the named property
		PropertyDescriptor propertyDescriptor = properties.get(propertyName);
		// Make sure we can set the property name:
		if (propertyDescriptor == null || isReadOnly(propertyName)) {
			throw new IllegalArgumentException(propertyName);
		}
		// Store the converted string value here:
		Object convertedValue;
		Class<?> propertyType = propertyDescriptor.getPropertyType();

		// Convert common types in well-known ways.
		if (propertyType == String.class) {
			convertedValue = propertyValue;
		} else if (propertyType == boolean.class) {
			convertedValue = Boolean.valueOf(propertyValue);
		} else if (propertyType == byte.class) {
			convertedValue = Byte.valueOf(propertyValue);
		} else if (propertyType == char.class) {
			convertedValue = (char) propertyValue.charAt(0);
		} else if (propertyType == short.class) {
			convertedValue = Short.valueOf(propertyValue);
		} else if (propertyType == int.class) {
			convertedValue = Integer.valueOf(propertyValue);
		} else if (propertyType == long.class) {
			convertedValue = Long.valueOf(propertyValue);
		} else if (propertyType == float.class) {
			convertedValue = Float.valueOf(propertyValue);
		} else if (propertyType == double.class) {
			convertedValue = Double.valueOf(propertyValue);
		} else if (propertyType == Color.class) {
			convertedValue = Color.decode(propertyValue);
		} else if (propertyType == Font.class) {
			convertedValue = Font.decode(propertyValue);
		} else {
			// Try to find a property editor for unknown types.
			PropertyEditor editor = PropertyEditorManager.findEditor(propertyType);
			if (editor != null) {
				editor.setAsText(propertyValue);
				convertedValue = editor.getValue();
			}
			// Otherwise, give up.
			else {
				throw new UnsupportedOperationException("Can't set properties of type " + propertyType.getName());
			}
		}

		/*
		 * Now get the Method object for the property setter method and invoke it on the
		 * bean object, passing the converted value.
		 */
		Method setter = propertyDescriptor.getWriteMethod();
		setter.invoke(bean, new Object[] { convertedValue });
	}

	/*
	 * Return a component that allows the user to edit the property value while the
	 * component is live, and changes the property value in real time; there is no
	 * need to call setPropertyValue().
	 */
	public Component getPropertyEditor(final String propertyName)
			throws IllegalAccessException, InvocationTargetException, InstantiationException, Exception {
		// Get the descriptor for the named property.
		PropertyDescriptor propertyDescriptor = (PropertyDescriptor) properties.get(propertyName);
		// Make sure we can edit it:
		if (propertyDescriptor == null || isReadOnly(propertyName)) {
			throw new IllegalArgumentException(propertyName);
		}

		/* Find a PropertyEditor for the property. */
		PropertyEditor propertyEditor = null;
		if (propertyDescriptor.getPropertyEditorClass() != null) {
			// If there is a custom editor for this property, instantiate it.
			try {
				Class<?> propertyEditorClass = propertyDescriptor.getPropertyEditorClass();

				propertyEditor = (PropertyEditor) propertyEditorClass.getDeclaredConstructor().newInstance();
			} catch (NoSuchMethodException noSuchMethodException) {
				noSuchMethodException.printStackTrace(System.err);
			}
		} else {
			// Otherwise, look up an editor based on the property type.
			Class<?> propertyType = propertyDescriptor.getPropertyType();
			propertyEditor = PropertyEditorManager.findEditor(propertyType);
			// If there is no editor, give up.
			if (propertyEditor == null) {
				throw new UnsupportedOperationException("Can't set properties of type " + propertyType.getName());
			}
		}
		if (propertyEditor == null) {
			throw new Exception("Sorry, coundn't find an appropriate property editor for: " + propertyName);
		}

		/*
		 * Get the property accessor methods for this property so we can query the
		 * initial value and set the edited value.
		 */
		Method getter = propertyDescriptor.getReadMethod();
		Method setter = propertyDescriptor.getWriteMethod();

		/*
		 * Use Java reflection to find the current property value. Then tell the
		 * property editor about it.
		 */
		Object currentValue = getter.invoke(bean, NOARGS);
		propertyEditor.setValue(currentValue);

		/*
		 * If the PropertyEditor has a custom editor, then we'll just return that custom
		 * editor component from this method. User changes to that component change the
		 * value in the PropertyEditor, which generates a PropertyChangeEvent. We
		 * register a listener so that these changes set the same property on the bean
		 * as well.
		 */
		if (propertyEditor.supportsCustomEditor()) {
			Component editComponent = propertyEditor.getCustomEditor();
			/*
			 * Note that we register the listener on the PropertyEditor, not on its custom
			 * editor Component.
			 */

			BeanPropertyChangeListener beanPropertyChangeListener = null;
			beanPropertyChangeListener = new BeanPropertyChangeListener(propertyEditor, setter, editComponent);
			propertyEditor.addPropertyChangeListener(beanPropertyChangeListener);
			return editComponent;
		}

		/*
		 * Otherwise, if the PropertyEditor is for an enumerated type based on a fixed
		 * list of possible values, then return a JComboBox component that allows the
		 * user to select one of the values.
		 */
		String[] tags = propertyEditor.getTags();
		if (tags != null) {
			// Create the component:
			JComboBox<String> jComboBox = new JComboBox<String>(tags);
			/*
			 * Use the current value of the property as the currently selected item in the
			 * combo box.
			 */
			jComboBox.setSelectedItem(propertyEditor.getAsText());
			/*
			 * Add a listener to hook the combo box up to the property. When the user
			 * selects an item, set the property value.
			 */
			BeanItemListener beanItemListener = new BeanItemListener(jComboBox, propertyEditor, setter);
			jComboBox.addItemListener(beanItemListener);

			return jComboBox;
		}

		/*
		 * Otherwise, the property type is not enumerated, and we use a JTextField to
		 * allow the user to enter arbitrary text for conversion by the setAsText()
		 * method of the PropertyEditor.
		 */
		JTextField jTextField = new JTextField();
		// Display the current value of the property in the field.
		jTextField.setText(propertyEditor.getAsText());
		// Hook the JTextField up to the PropertyEditor.
		BeanActionListener beanActionListener = new BeanActionListener(jTextField, propertyEditor, setter);
		jTextField.addActionListener(beanActionListener);
		return jTextField;
	}

	private class BeanPropertyChangeListener implements PropertyChangeListener {
		private PropertyEditor propertyEditor;
		private Method setter;
		private Component editComponent;

		BeanPropertyChangeListener(PropertyEditor propertyEditor, Method setter, Component editComponent) {
			this.propertyEditor = propertyEditor;
			this.setter = setter;
			this.editComponent = editComponent;
		}

		public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
			try {
				// Pass edited value to property setter:
				Object editedValue = this.propertyEditor.getValue();
				setter.invoke(bean, new Object[] { editedValue });
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(editComponent, ex, ex.getClass().getName(), JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private class BeanItemListener implements ItemListener {
		private JComboBox<String> jComboBox;
		private PropertyEditor propertyEditor;
		private Method setter;

		public BeanItemListener(JComboBox<String> jComboBox, PropertyEditor propertyEditor, Method setter) {
			this.jComboBox = jComboBox;
			this.propertyEditor = propertyEditor;
			this.setter = setter;
		}

		public void itemStateChanged(ItemEvent itemEvent) {
			// Ignore deselect events:
			if (itemEvent.getStateChange() == ItemEvent.DESELECTED) {
				return;
			}
			try {
				// Get the user's selected string from combo box:
				String selectedTag = (String) this.jComboBox.getSelectedItem();
				// Tell the editor about this string value:
				this.propertyEditor.setAsText(selectedTag);
				// Ask the editor to convert to the property type:
				Object editedValue = this.propertyEditor.getValue();
				// Pass this value to the property setter method:
				this.setter.invoke(bean, new Object[] { editedValue });
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(jComboBox, ex, ex.getClass().getName(), JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private class BeanActionListener implements ActionListener {
		private JTextField jTextField;
		private PropertyEditor propertyEditor;
		private Method setter;

		public BeanActionListener(JTextField jTextField, PropertyEditor propertyEditor, Method setter) {
			this.jTextField = jTextField;
			this.propertyEditor = propertyEditor;
			this.setter = setter;
		}

		// This is called when the user strikes the Enter key:
		public void actionPerformed(ActionEvent actionEvent) {
			try {
				// Get the user's input from the text field:
				String newText = jTextField.getText();
				// Tell the editor about it:
				propertyEditor.setAsText(newText);
				// Ask the editor to convert to the property type:
				Object editedValue = propertyEditor.getValue();
				// Pass this value to the property setter method:
				setter.invoke(bean, new Object[] { editedValue });
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(jTextField, ex, ex.getClass().getName(), JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
