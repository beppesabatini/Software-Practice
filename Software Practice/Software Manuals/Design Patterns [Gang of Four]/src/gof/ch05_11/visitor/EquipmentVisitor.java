package gof.ch05_11.visitor;

import gof.ch04_03.composite.CompositeEquipment;
import gof.ch04_03.composite.FloppyDisk;
import gof.ch05_11.visitor.VisitorSupport.*;
import gof.designpatterns.Visitor;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 170-172, 340-343. Part of the sample code illustrating the
 * {@linkplain gof.designpatterns.Visitor Visitor} design pattern.
 * <p/>
 * Note that the methods in the EquipmentVisitor class are empty methods, not
 * abstract ones, so that clients can ignore the elements they do not need when
 * subclassing this.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_11/visitor/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public abstract class EquipmentVisitor implements Visitor {

	public EquipmentVisitor() {
		System.out.println(" -- Initializing EquipmentVisitor");
	}

	public void visitBus(Bus bus) {
		System.out.println(" -- No method defined for Bus");
	}

	public void visitCard(Card card) {
		System.out.println(" -- No method defined for Card");
	}

	public void visitChassis(Chassis chassis) {
		System.out.println(" -- No method defined for Chassis");
	}

	public void visitCompositeEquipment(CompositeEquipment compositeEquipment) {
		System.out.println(" -- No method defined for CompositeEquipment");
	}

	public void visitFloppyDisk(FloppyDisk floppyDisk) {
		System.out.println(" -- No method defined for FloppyDisks, they don't exist anymore.");
	}

	@Override
	public void finalize() {
		// Clean-up before deallocation
	}
}
