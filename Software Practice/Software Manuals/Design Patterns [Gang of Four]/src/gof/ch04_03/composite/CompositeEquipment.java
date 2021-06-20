package gof.ch04_03.composite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gof.ch05_11.visitor.EquipmentVisitor;
import gof.designpatterns.Composite;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 171. An
 * element of the sample code for the {@linkplain gof.designpatterns.Composite
 * Composite} design pattern. For more detail see the {@linkplain Equipment}
 * class.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch04_03/composite/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class CompositeEquipment extends Equipment implements Composite {

	public static final String DEBUG = "true";

	private List<Equipment> equipmentDispenser;

	public CompositeEquipment(String name) {
		super(name);
		setPricePoint(PricePoint.NET_PRICE);
		this.equipmentDispenser = new ArrayList<Equipment>();
	}

	/*
	 * It looks as if someone thought equipment units should cost full price if
	 * purchased separately, and discount price if purchased as part of some
	 * CompositeEquipment. The manual seems to imply this (p. 341) but isn't
	 * specific about it.
	 */
	@Override
	public void accept(EquipmentVisitor equipmentVisitor) {
		Iterator<Equipment> partsIterator = createIterator();
		while (partsIterator.hasNext()) {
			Equipment currentEquipment = partsIterator.next();
			PricePoint originalPricePoint = currentEquipment.getPricePoint();
			// Components bundled as CompositeEquipment are sold at the discount price.
			currentEquipment.setPricePoint(PricePoint.DISCOUNT_PRICE);
			currentEquipment.accept(equipmentVisitor);
			currentEquipment.setPricePoint(originalPricePoint);
		}
		PricePoint originalChassisPricePoint = this.getPricePoint();
		if (this.equipmentDispenser.size() <= 0) {
			this.setPricePoint(PricePoint.NET_PRICE);
			if (Boolean.valueOf(DEBUG) == true) {
				System.out.println(" -- Charging full price for " + this.getName() + " sold by itself");
			}
		} else {
			// Bundled with equipment, use the discount price.
			this.setPricePoint(PricePoint.DISCOUNT_PRICE);
			if (Boolean.valueOf(DEBUG) == true) {
				System.out.println(" -- Charging discount price for " + this.getName() + " bundled with hardware");
			}
		}
		equipmentVisitor.visitCompositeEquipment(this);
		this.setPricePoint(originalChassisPricePoint);
	}

	@Override
	public Watt getPower() {
		return new Watt(100);
	}

	/**
	 * This getNetPrice() method should return the price of the container, not the
	 * price of what it contains, at least it should if the accept() function above
	 * is correct. This is the old version from p. 171:
	 * 
	 * <pre>
	 * public Currency getNetPrice() {
	 * 	int value = 0;
	 * 	Iterator<Equipment> equipmentDispenser = createIterator();
	 * 	while (equipmentDispenser.hasNext()) {
	 * 		Equipment equipment = equipmentDispenser.next();
	 * 		value += equipment.getNetPrice().getValue();
	 * 	}
	 * 	return (new Currency(value));
	 * }
	 * </pre>
	 *
	 * When the accept() method above is added (on p. 340), the old getNetPrice()
	 * method above needs to be changed to the new version below.
	 *
	 */
	@Override
	public Currency getNetPrice() {
		return (new Currency(100));
	}

	/**
	 * This is the price of the container, not what it contains.
	 */
	@Override
	public Currency getDiscountPrice() {
		return (new Currency(75));
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
