package ch18;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * p. 654-655. The application's look-and-feel can be changed on the fly by
 * selecting a menu bar item.
 * 
 */
public class QuickChange extends JFrame {

	private static final long serialVersionUID = -5130872889572658858L;

	public QuickChange() {
		super("QuickChange v1.0");
		createGUI();
	}

	protected void createGUI() {
		setSize(300, 200);

		// create a simple File menu
		JMenu file = new JMenu("File", true);
		JMenuItem quit = new JMenuItem("Quit");
		file.add(quit);
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		// create the Look & Feel menu
		JMenu lnf = new JMenu("!!! Look & Feel (Click Me!) !!!", true);
		ButtonGroup buttonGroup = new ButtonGroup();
		final UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
		for (int i = 0; i < info.length; i++) {
			JRadioButtonMenuItem item = new JRadioButtonMenuItem(info[i].getName(), i == 0);
			final String className = info[i].getClassName();
			item.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					try {
						UIManager.setLookAndFeel(className);
					} catch (Exception e) {
						System.out.println(e);
					}
					SwingUtilities.updateComponentTreeUI(QuickChange.this);
				}
			});
			buttonGroup.add(item);
			lnf.add(item);
		}

		// add the menu bar
		JMenuBar mb = new JMenuBar();
		mb.add(file);
		mb.add(lnf);
		setJMenuBar(mb);

		// add some components
		JPanel jp = new JPanel();
		jp.add(new JCheckBox("JCheckBox"));
		String[] names = new String[] { "Tosca", "Cavaradossi", "Scarpia", "Angelotti", "Spoletta", "Sciarrone",
				"Carceriere", "Il sagrestano", "Un pastore" };
		jp.add(new JComboBox<String>(names));
		jp.add(new JButton("JButton"));
		jp.add(new JLabel("JLabel"));
		jp.add(new JTextField("JTextField"));
		JPanel main = new JPanel(new GridLayout(1, 2));
		main.add(jp);
		main.add(new JScrollPane(new JList<String>(names)));
		setContentPane(main);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new QuickChange().setVisible(true);
	}
}
