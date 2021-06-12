package gof.ch05_11.visitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gof.ch04_03.composite.Currency;
import gof.ch04_03.composite.Equipment;
import gof.ch04_03.composite.Watt;
import gof.designpatterns.Composite;
import gof.designpatterns.Visitor;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 341. Part of the sample code for the {@linkplain Visitor} and
 * {@linkplain Composite} design patterns. This Chassis class appears only as a
 * fragment in the manual.</div>
 *
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Chassis extends Equipment {

	private List<Equipment> parts;

	public Chassis(String chassisName) {
		super(chassisName);
		this.parts = new ArrayList<Equipment>();
	}

	@Override
	public void accept(EquipmentVisitor equipmentVisitor) {
		Iterator<Equipment> partsIterator = createIterator();
		while (partsIterator.hasNext()) {
			partsIterator.next().accept(equipmentVisitor);
		}
		equipmentVisitor.visitChassis(this);
	}

	@Override
	public Watt getPower() {
		return new Watt(100);
	}

	@Override
	public Currency getNetPrice() {
		int value = 0;
		Iterator<Equipment> equipmentDispenser = createIterator();
		while (equipmentDispenser.hasNext()) {
			Equipment equipment = equipmentDispenser.next();
			value += equipment.getNetPrice().getValue();
		}
		return (new Currency(value));
	}

	@Override
	public Currency getDiscountPrice() {
		int value = 0;
		Iterator<Equipment> equipmentDispenser = createIterator();
		while (equipmentDispenser.hasNext()) {
			Equipment equipment = equipmentDispenser.next();
			value += equipment.getNetPrice().getValue();
		}
		return (new Currency(value));
	}

	@Override
	public void add(Equipment equipment) {
		parts.add(equipment);
	}

	public void remove(Equipment equipment) {
		parts.remove(equipment);
	}

	@Override
	public Iterator<Equipment> createIterator() {
		Iterator<Equipment> partsIterator = this.parts.listIterator();
		return (partsIterator);
	}

	@Override
	public void finalize() {
		// Clean-up before deallocation.
	}
}
