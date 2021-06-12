package gof.ch04_03.composite;

import java.util.Iterator;

import gof.ch02_02.structure.Glyph;
import gof.ch05_11.visitor.EquipmentVisitor;
import gof.designpatterns.Composite;
import gof.designpatterns.Visitor;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 170, 340.
 * This is the root object for the sample code for the {@linkplain Composite}
 * and {@linkplain Visitor} design patterns. This pattern might be appropriate
 * for software dealing with a library or a bookstore. A book, a trilogy, an
 * encyclopedia, and other holdings, could all be handled interchangeably. The
 * manual (p. 173) mentions the use case of a financial portfolio, which uses a
 * Composite to generalize the interface for the portfolio with the interface
 * for an individual asset.
 * <p/>
 * The examples in the manual, the {@linkplain Glyph} class from chapter 2 and
 * the Equipment class below, don't speak well for the Composite pattern. Both
 * are root objects of a class hierarchy, one in which every class is so
 * overgeneralized, that the pattern defeats all the benefits of strong data
 * typing. Instantiations and subclasses have to define a lot of dummy stub
 * functions they don't need, just to satisfy a sprawling interface or a
 * sprawling abstract superclass. See the comments below.
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public abstract class Equipment implements Composite {

	private String name;

	public Equipment(String name) {
		this.name = name;
	}

	public String getName() {
		return (this.name);
	}

	public abstract Watt getPower();

	// Simple equipment is charged net price
	public abstract Currency getNetPrice();

	// Composite equipment is charged discount price
	public abstract Currency getDiscountPrice();

	/**
	 * Override for complex equipment (Bus, Chassis, CompositeEquipment). The next
	 * three methods are not meaningful for simple (i.e. non-composite) equipment
	 * and in fact introduce bugs in the manual's pseudo-code.
	 */
	public void add(Equipment equipment) {
		// Override for complex equipment (Bus, Chassis, CompositeEquipment).
		// Probably do not implement for simple equipment (Floppy Disk, Card).
	}

	public void remove(Equipment equipment) {
		// Override for complex equipment (Bus, Chassis, CompositeEquipment).
		// Probably do not implement for simple equipment (Floppy Disk, Card).
	}

	public Iterator<Equipment> createIterator() {
		// Override for complex equipment (Bus, Chassis, CompositeEquipment).
		// Probably do not implement for simple equipment (Floppy Disk, Card).
		return (null);
	}

	public void acceptVisitor(EquipmentVisitor equipmentVisitor) {
		accept(equipmentVisitor);
	}

	public abstract void accept(EquipmentVisitor equipmentVisitor);

	// Clean-up at deallocation time.
	public abstract void finalize();
}
