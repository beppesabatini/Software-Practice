package gof.ch04_03.composite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gof.ch05_11.visitor.EquipmentVisitor;
import gof.designpatterns.Composite;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 171. An
 * element of the sample code for the {@linkplain Composite} design pattern. For
 * more detail see the {@linkplain Equipment} class.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class CompositeEquipment extends Equipment implements Composite {

	private List<Equipment> equipmentDispenser;

	public CompositeEquipment(String name) {
		super(name);
		this.equipmentDispenser = new ArrayList<Equipment>();
	}

	@Override
	public void accept(EquipmentVisitor equipmentVisitor) {
		Iterator<Equipment> partsIterator = createIterator();
		while (partsIterator.hasNext()) {
			partsIterator.next().accept(equipmentVisitor);
		}
		equipmentVisitor.visitCompositeEquipment(this);
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
			value += equipment.getDiscountPrice().getValue();
		}
		return (new Currency(value));
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

	@Override
	public void finalize() {
		/*
		 * Clean-up at deallocation time. Do things like deallocate every piece of
		 * equipment in the equipment dispenser.
		 */
	}
}
