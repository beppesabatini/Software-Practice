package gof.ch05_11.visitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gof.ch04_03.composite.Currency;
import gof.ch04_03.composite.Equipment;
import gof.ch04_03.composite.Watt;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 341. Part of the sample code for the {@linkplain gof.designpatterns.Visitor
 * Visitor} and {@linkplain gof.designpatterns.Composite Composite} design
 * patterns. The Chassis class appears only as a fragment in the manual.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_11/visitor/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Chassis extends Equipment {

	private List<Equipment> parts;

	public Chassis(String chassisName) {
		super(chassisName);
		setPricePoint(PricePoint.NET_PRICE);
		this.parts = new ArrayList<Equipment>();
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
		if (this.parts.size() <= 0) {
			this.setPricePoint(PricePoint.NET_PRICE);
		} else {
			// Bundled with equipment, use the discount price.
			this.setPricePoint(PricePoint.DISCOUNT_PRICE);
		}
		equipmentVisitor.visitChassis(this);
		this.setPricePoint(originalChassisPricePoint);
	}

	@Override
	public Watt getPower() {
		return new Watt(100);
	}

	/**
	 * This method should return the price of the container, not the price of what
	 * it contains. See the accept() function above.
	 */
	@Override
	public Currency getNetPrice() {
		return (new Currency(200));
	}

	/**
	 * This method should return the price of the container, not the price of what
	 * it contains. See the accept() function above.
	 */
	@Override
	public Currency getDiscountPrice() {
		return (new Currency(150));
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
