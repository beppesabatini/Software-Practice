package gof.ch04_03.composite;

import gof.ch05_11.visitor.EquipmentVisitor;
import gof.designpatterns.Composite;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 171. An
 * element of the sample code for the {@linkplain gof.designpatterns.Composite
 * Composite} and {@linkplain gof.designpatterns.Visitor Visitor} design
 * patterns. The FloppyDisk is included in that sample as an instance of a
 * concrete subclass of the abstract Equipment root class. See the
 * {@linkplain Equipment} class for more detail.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch04_03/composite/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class FloppyDisk extends Equipment implements Composite {

	public FloppyDisk(String name) {
		super(name);
		setPricePoint(PricePoint.NET_PRICE);
	}

	@Override
	public Watt getPower() {
		return new Watt(100);
	}

	@Override
	public Currency getNetPrice() {
		return new Currency(50);
	}

	@Override
	public Currency getDiscountPrice() {
		return new Currency(25);
	}

	/*
	 * Don't implement these next three--they are not meaningful for simple
	 * (non-composite) equipment like a FloppyDisk.
	 */

//	@Override
//	public void add(Equipment equipment) {
//		this.equipmentDispenser.add(equipment);
//	}
//
//	@Override
//	public void remove(Equipment equipment) {
//		this.equipmentDispenser.remove(equipment);
//	}
//
//	@Override
//	public Iterator<Equipment> createIterator() {
//		return (this.equipmentDispenser.iterator());
//	}

	public void acceptVisitor(EquipmentVisitor equipmentVisitor) {
		accept(equipmentVisitor);
	}

	public void accept(EquipmentVisitor equipmentVisitor) {
		equipmentVisitor.visitFloppyDisk(this);
	}

	@Override
	public void finalize() {
		// Clean-up at deallocation time.
		// Do things like remove the disk from a HashMap of disks.
	}

}
