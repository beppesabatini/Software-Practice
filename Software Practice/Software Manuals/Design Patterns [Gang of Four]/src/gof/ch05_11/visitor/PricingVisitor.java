package gof.ch05_11.visitor;

import gof.ch04_03.composite.CompositeEquipment;
import gof.ch04_03.composite.Currency;
import gof.ch04_03.composite.FloppyDisk;
import gof.ch05_11.visitor.VisitorSupport.Bus;
import gof.designpatterns.Composite;
import gof.designpatterns.Visitor;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 170-172, 340-343. Part of the sample code for the {@linkplain Visitor} and
 * {@linkplain Composite} design patterns. The intent is to show how a Visitor
 * can travel through a Composite piece of Equipment and, in this case, total up
 * the price of every contained piece.
 * <p/>
 * The pseudo-code for the Visitor design pattern is just a sketch, and taken
 * literally, it is buggy. In this PricingVisitor, the price of each piece of
 * Equipment is added to the total price again and again, every time the piece
 * is added to a piece of composite equipment, and every time another piece of
 * composite equipment is added to the original composite equipment. Similar
 * problems will arise with the InventoryVisitor. One fix might be to have the
 * PricingVisitor mark each node in the tree which has been added to the total
 * with a "visited" flag.</div>
 *
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
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
		System.out.println(" -- Adding Card: " + card.getNetPrice().getValue());
		this.totalPrice.setValue(totalPrice.getValue() + card.getNetPrice().getValue());
	}

	public void visitChassis(Chassis chassis) {
		System.out.println(" -- Adding Chassis: " + chassis.getDiscountPrice().getValue());
		this.totalPrice.setValue(totalPrice.getValue() + chassis.getDiscountPrice().getValue());
	}

	public void visitCompositeEquipment(CompositeEquipment compositeEquipment) {
		System.out.println(" -- Adding CompositeEquipment: " + compositeEquipment.getDiscountPrice().getValue());
		this.totalPrice.setValue(totalPrice.getValue() + compositeEquipment.getDiscountPrice().getValue());
	}

	public void visitFloppyDisk(FloppyDisk floppyDisk) {
		System.out.println(" -- Adding FloppyDisk: " + floppyDisk.getNetPrice().getValue());
		this.totalPrice.setValue(totalPrice.getValue() + floppyDisk.getNetPrice().getValue());
	}

}
