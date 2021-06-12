package gof.ch04_03.composite;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import gof.ch05_11.visitor.EquipmentVisitor;
import gof.designpatterns.Composite;
import gof.designpatterns.Visitor;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 171. An
 * element of the sample code for the {@linkplain Composite} and
 * {@linkplain Visitor} design patterns. The FloppyDisk is included in that
 * sample as an instance of a concrete subclass of the abstract Equipment root
 * class.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class FloppyDisk extends Equipment implements Composite {

	private List<Equipment> equipmentDispenser;

	public FloppyDisk(String name) {
		super(name);
		this.equipmentDispenser = new ArrayList<Equipment>();
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

	@Override
	public void add(Equipment equipment) {
		this.equipmentDispenser.add(equipment);
	}

	@Override
	public void remove(Equipment equipment) {
		this.equipmentDispenser.remove(equipment);
	}

	@Override
	public Iterator<Equipment> createIterator() {
		return (this.equipmentDispenser.iterator());
	}

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
