package gof.ch04_03.composite;

import java.util.Iterator;

import gof.ch05_11.visitor.EquipmentVisitor;
import gof.designpatterns.Composite;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 170, 340.
 * This is the root object for the sample code for the
 * {@linkplain gof.designpatterns.Composite Composite} and
 * {@linkplain gof.designpatterns.Visitor Visitor} design patterns. The
 * Composite pattern might be appropriate for software dealing with a library or
 * a bookstore. A book, a trilogy, an encyclopedia, and other holdings, could
 * all be handled interchangeably. The manual (p. 173) mentions the use case of
 * a financial portfolio, which uses a Composite to generalize the interface for
 * the portfolio with the interface for an individual asset.
 * <p/>
 * The examples in the manual, the {@linkplain gof.ch02_02.structure.Glyph
 * Glyph} class from chapter 2 and the Equipment class below, don't speak well
 * for the Composite pattern. Both are root objects of a class hierarchy, one in
 * which every class is so overgeneralized, that the pattern defeats all the
 * benefits of strong data typing. Instantiations and subclasses have to define
 * a lot of dummy stub functions they don't need, just to satisfy a sprawling
 * interface or a sprawling abstract superclass. See the comments in the source
 * code.
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch04_03/composite/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public abstract class Equipment implements Composite {

	public static enum PricePoint {
		NET_PRICE, DISCOUNT_PRICE
	}

	private PricePoint pricePoint;

	private String name;

	public Equipment(String name) {
		this.name = name;
	}

	public String getName() {
		return (this.name);
	}

	public PricePoint getPricePoint() {
		return (this.pricePoint);
	}

	public void setPricePoint(PricePoint pricePoint) {
		this.pricePoint = pricePoint;
	}

	public abstract Watt getPower();

	/*
	 * It looks as if someone thought equipment units should cost full price if
	 * purchased separately, and discount price if purchased as part of some
	 * CompositeEquipment. The manual seems to imply this (p. 341) but isn't
	 * specific about it.
	 */
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
