package je3.ch11.gui;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import je3.ch15.beans.Bean;

/**
 * Not in the manual. This is an abstract class. To test this, test any of its
 * subclasses such as YesNoPanel. The ShowBean platform used here supports a
 * wide variety of Beans, including many not yet invented, and usually many or
 * most of its menu items will not work for any given Bean.
 * 
 * @author Beppe Sabatini
 *
 */
public abstract class JPanelDemoable extends JPanel {

	private static final long serialVersionUID = 5274283557992178L;

	public static final String[] juliaSetBeans = initJuliaSetBeans();

	private static String[] initJuliaSetBeans() {
		String[] juliaSetBeans = new String[3];
		juliaSetBeans[0] = "je3.ch13.print.JuliaSet1";
		juliaSetBeans[1] = "je3.ch13.print.JuliaSet2";
		juliaSetBeans[2] = "je3.ch13.print.JuliaSet3";

		return (juliaSetBeans);
	}

	protected static ShowBean launchInShowBean(String beanName, int width, int height) {
		String[] beanNames = new String[1];
		beanNames[0] = beanName;
		Dimension dimension = new Dimension(width, height);
		List<Bean> beansToShow = ShowBean.parseArgs(beanNames, dimension);
		ShowBean showBean = new ShowBean(beansToShow);
		showBean.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		showBean.setVisible(true);

		return (showBean);
	}

	protected static ShowBean launchBeansInShowBean(String[] beanNames, int width, int height) {
		Dimension dimension = new Dimension(width, height);
		List<Bean> beansToShow = ShowBean.parseArgs(beanNames, dimension);
		ShowBean showBean = new ShowBean(beansToShow);
		showBean.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		showBean.setVisible(true);

		return (showBean);
	}

	public static class Demo {
		public static void main(String[] args) {
			System.out.print("Demo for JPanelDemoable as used by YesNoPanel. ");
			System.out.println("Edit YesNoPanel through the Properties Menu.");
			System.out.println("The buttons are non-functional.");
			launchInShowBean("je3.ch15.beans.YesNoPanel", 400, 240);
		}
	}
}
