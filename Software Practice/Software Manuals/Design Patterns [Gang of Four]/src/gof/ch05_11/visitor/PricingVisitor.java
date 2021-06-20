package gof.ch05_11.visitor;

import gof.ch04_03.composite.Equipment;
import gof.ch04_03.composite.Equipment.PricePoint;
import gof.ch04_03.composite.CompositeEquipment;
import gof.ch04_03.composite.Currency;
import gof.ch04_03.composite.FloppyDisk;
import gof.ch05_11.visitor.VisitorSupport.Bus;
import gof.designpatterns.Visitor;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 170-172, 340-343. Part of the sample code for the
 * {@linkplain gof.designpatterns.Visitor Visitor} and
 * {@linkplain gof.designpatterns.Composite Composite} design patterns.The
 * intent is to show how a Visitor can travel through a Composite piece of
 * Equipment and, in this case, total up the price of every contained
 * piece.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_11/visitor/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class PricingVisitor extends EquipmentVisitor implements Visitor {

	private Currency totalPrice;

	public PricingVisitor() {
		System.out.println(" -- Initializing PricingVisitor");
		this.totalPrice = new Currency(0);
	}

	public Currency getTotalPrice() {
		return (this.totalPrice);
	}

	public void visitBus(Bus bus) {
		System.out.println(" -- No method defined for Bus");
	}

	public void visitCard(Card card) {
		this.visitEquipment(card);
	}

	public void visitChassis(Chassis chassis) {
		this.visitEquipment(chassis);
	}

	public void visitCompositeEquipment(CompositeEquipment compositeEquipment) {
		this.visitEquipment(compositeEquipment);
	}

	public void visitFloppyDisk(FloppyDisk floppyDisk) {
		this.visitEquipment(floppyDisk);
	}

	private void visitEquipment(Equipment equipment) {
		PricePoint pricePoint = equipment.getPricePoint();
		int equipmentPrice = 0;
		if (pricePoint == PricePoint.NET_PRICE) {
			equipmentPrice = equipment.getNetPrice().getValue();
		} else if (pricePoint == PricePoint.DISCOUNT_PRICE) {
			equipmentPrice = equipment.getDiscountPrice().getValue();
		}
		String equipmentName = equipment.getName();
		System.out.println(" -- Adding " + equipmentName + ": " + equipmentPrice);
		this.totalPrice.setValue(totalPrice.getValue() + equipmentPrice);
	}

}
