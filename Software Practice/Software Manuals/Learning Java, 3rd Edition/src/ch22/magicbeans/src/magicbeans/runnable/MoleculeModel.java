package magicbeans.runnable;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.GroupLayout.*;
import javax.swing.GroupLayout.Alignment;
import magicbeans.sunw.demo.molecule.*;

/**
 * This file was originally generated by the NetBeans IDE. It is posted here for
 * experimental and demo purposes. It has been edited by hand and can no longer
 * be edited in the NetBeans IDE. Someday we may be able to re-engineer this
 * back into being a drag-and-drop reusable GUI Bean.
 *
 * @author Beppe Sabatini
 */
public class MoleculeModel extends JFrame {

	private static final long serialVersionUID = 3389943704458117690L;

	private static Logger logger = Logger.getLogger(MoleculeModel.class.getName());

	public class MoleculeComboBoxChangeListener implements ItemListener {

		public void itemStateChanged(ItemEvent event) {
			// Check to see if the MoleculeComboBox triggered this event.
			if (event.getSource() != moleculeComboBox) {
				return;
			}
			// The combo box fires off two change events, on for the option
			// which was selected and one for the option which was unselected.
			if (event.getStateChange() != ItemEvent.SELECTED) {
				return;
			}

			ItemSelectable itemSelectable = event.getItemSelectable();
			Object[] selectedObjects = itemSelectable.getSelectedObjects();
			if (selectedObjects.length != 1 || selectedObjects[0] instanceof String == false) {
				throw new IllegalArgumentException("Bad argument returned from the Molecule Combo Box");
			}

			hidePreviousMoleculeFrame(event);

			String moleculeLabel = (String) selectedObjects[0];
			String moleculeName = MoleculeDataMap.getMoleculeDataMap().get(moleculeLabel);
			new MoleculeModel("").new MoleculeModelLauncher(moleculeName, moleculeLabel);
		}
	}

	private void hidePreviousMoleculeFrame(ItemEvent event) {
		@SuppressWarnings("unchecked")
		JComboBox<String> source = (JComboBox<String>) event.getSource();
		JPanel panel = (JPanel) source.getParent();
		JLayeredPane jLayeredPane = (JLayeredPane) panel.getParent();
		JRootPane jRootPane = (JRootPane) jLayeredPane.getParent();
		MoleculeModel moleculeModel = (MoleculeModel) jRootPane.getParent();
		moleculeModel.setVisible(false);
	}

	public MoleculeModel() {
		initComponents();
	}

	public MoleculeModel(String dummy) {
		/**
		 * Do nothing. This is a dummy function to help instantiate an internal private
		 * class, MoleculeModelLauncher.
		 */
	}

	private void initComponents() {

		timer1 = new magicbeans.Timer();
		timer2 = new magicbeans.Timer();
		molecule1 = new Molecule();
		molecule1.setMoleculeName(moleculeName);
		jLabel1 = new JLabel();
		moleculeComboBox = new JComboBox<>();

		timer1.addTimerListener(new magicbeans.TimerListener() {
			public void timerFired(magicbeans.TimerEvent evt) {
				timer1TimerFired(evt);
				timer1TimerFired1(evt);
			}
		});

		timer2.setDelay(400L);
		timer2.addTimerListener(new magicbeans.TimerListener() {
			public void timerFired(magicbeans.TimerEvent evt) {
				timer2TimerFired(evt);
				timer2TimerFired1(evt);
			}
		});

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jLabel1.setFont(new Font("Tahoma", 0, 18)); // NOI18N
		jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel1.setText(moleculeLabel);
		String[] moleculeLabelStrings = MoleculeDataMap.getKeySetArray();
		moleculeComboBox.setModel(new DefaultComboBoxModel<>(moleculeLabelStrings));
		moleculeComboBox.setSelectedItem(moleculeLabel);
		moleculeComboBox.addItemListener(new MoleculeComboBoxChangeListener());

		buildJFrameLayout();
	}

	private void buildJFrameLayout() {

		int defaultSize = GroupLayout.DEFAULT_SIZE;
		int preferredSize = GroupLayout.PREFERRED_SIZE;

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);

		SequentialGroup labelGroup = layout.createSequentialGroup();
		labelGroup.addComponent(jLabel1, preferredSize, 158, preferredSize);
		labelGroup.addGap(94, 94, 94);

		SequentialGroup moleculeGroup = layout.createSequentialGroup();
		moleculeGroup.addComponent(molecule1, preferredSize, 266, preferredSize);
		moleculeGroup.addGap(39, 39, 39);

		SequentialGroup comboBoxGroup = layout.createSequentialGroup();
		comboBoxGroup.addGap(100, 100, 100);
		comboBoxGroup.addComponent(moleculeComboBox, preferredSize, defaultSize, preferredSize);
		comboBoxGroup.addGap(0, 0, Short.MAX_VALUE);

		ParallelGroup parallelComponentGroups = layout.createParallelGroup(Alignment.LEADING);
		parallelComponentGroups.addGroup(Alignment.TRAILING, labelGroup);
		parallelComponentGroups.addGroup(Alignment.TRAILING, moleculeGroup);

		SequentialGroup containerGroup = layout.createSequentialGroup();
		containerGroup.addContainerGap(40, Short.MAX_VALUE);
		containerGroup.addGroup(parallelComponentGroups);

		ParallelGroup headerGroup = layout.createParallelGroup(Alignment.LEADING);
		layout.setHorizontalGroup(headerGroup.addGroup(containerGroup).addGroup(comboBoxGroup));

		SequentialGroup bodyGroup = layout.createSequentialGroup();
		bodyGroup.addGap(9, 9, 9);
		bodyGroup.addComponent(jLabel1, defaultSize, defaultSize, Short.MAX_VALUE);
		bodyGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
		bodyGroup.addComponent(molecule1, preferredSize, 242, preferredSize);
		bodyGroup.addGap(18, 18, 18);
		bodyGroup.addComponent(moleculeComboBox, preferredSize, defaultSize, preferredSize);
		bodyGroup.addGap(35, 35, 35);

		ParallelGroup parallelBodyGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		parallelBodyGroup.addGroup(Alignment.TRAILING, bodyGroup);

		layout.setVerticalGroup(parallelBodyGroup);
		pack();
	}

	private void timer1TimerFired(magicbeans.TimerEvent evt) {
		molecule1.rotateOnX();
	}

	private void timer2TimerFired(magicbeans.TimerEvent evt) {
		molecule1.rotateOnY();
	}

	private void timer1TimerFired1(magicbeans.TimerEvent evt) {
		molecule1.rotateOnX();
	}

	private void timer2TimerFired1(magicbeans.TimerEvent evt) {
		molecule1.rotateOnY();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		if (args.length < 2) {
			System.out.println("Usage: <moleculeName> <moleculeLabel>");
			System.exit(1);
		}

		new MoleculeModel("").new MoleculeModelLauncher(args[0], args[1]);
	}

	private class MoleculeModelLauncher {
		MoleculeModelLauncher(String newMoleculeName, String newMoleculeLabel) {
			moleculeName = newMoleculeName;
			moleculeLabel = newMoleculeLabel;

			try {
				for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
					if ("Nimbus".equals(info.getName())) {
						UIManager.setLookAndFeel(info.getClassName());
						break;
					}
				}
			} catch (ClassNotFoundException ex) {
				logger.log(java.util.logging.Level.SEVERE, null, ex);
			} catch (InstantiationException ex) {
				logger.log(java.util.logging.Level.SEVERE, null, ex);
			} catch (IllegalAccessException ex) {
				logger.log(java.util.logging.Level.SEVERE, null, ex);
			} catch (javax.swing.UnsupportedLookAndFeelException ex) {
				logger.log(java.util.logging.Level.SEVERE, null, ex);
			}

			new MoleculeModel().setVisible(true);
		}
	}

	private static String moleculeName;
	private static String moleculeLabel;

	private javax.swing.JLabel jLabel1;
	private Molecule molecule1;
	private magicbeans.Timer timer1;
	private magicbeans.Timer timer2;
	private JComboBox<String> moleculeComboBox;
}
