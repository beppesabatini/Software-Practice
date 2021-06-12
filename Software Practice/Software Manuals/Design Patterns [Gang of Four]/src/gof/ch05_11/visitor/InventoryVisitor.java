package gof.ch05_11.visitor;

import gof.ch04_03.composite.FloppyDisk;
import gof.ch05_11.visitor.VisitorSupport.*;
import gof.designpatterns.Visitor;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 340-343. Part of the sample pseudo-code illustrating the {@linkplain Visitor}
 * design pattern. The intent is to show that while one EquipmentVisitor can
 * calculate Pricing, a different one can travel through the same Equipment
 * Composite and calculate Inventory. Neither the class in the manual nor the
 * one below is functional.</div>
 *
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class InventoryVisitor extends EquipmentVisitor implements Visitor {
	private Inventory inventory;

	public InventoryVisitor() {
		this.inventory = new VisitorSupport().new Inventory();
		System.out.println("Initializing EquipmentVisitor");
	}

	public Inventory getInventory() {
		return (this.inventory);
	}

	public void visitFloppyDisk(FloppyDisk floppyDisk) {
		this.inventory.accumulate(floppyDisk);
	}

	public void visitChassis(Chassis chassis) {
		this.inventory.accumulate(chassis);
	}

}
