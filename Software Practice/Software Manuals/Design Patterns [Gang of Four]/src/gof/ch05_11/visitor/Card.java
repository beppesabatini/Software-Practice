package gof.ch05_11.visitor;

import gof.ch04_03.composite.Currency;
import gof.ch04_03.composite.Equipment;
import gof.ch04_03.composite.Watt;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 170-172, 340-343. Part of the sample code for the
 * {@linkplain gof.designpatterns.Visitor Visitor} and
 * {@linkplain gof.designpatterns.Composite Composite} design patterns. The name
 * Card refers to a piece of hardware such as a graphics card. This Card class
 * does not appear in the manual; it is only mentioned in passing.</div>
 *
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_11/visitor/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Card extends Equipment {

	public Card(String cardName) {
		super(cardName);
		setPricePoint(PricePoint.NET_PRICE);
	}

	/**
	 * The manual notes (p. 339) that this is a <b>double-dispatch</b> operation.
	 * The operation is defined by two types: the Visitor (here an
	 * EquipmentVisitor), and the Element (which here is Card, a piece of
	 * Equipment).
	 */
	@Override
	public void accept(EquipmentVisitor equipmentVisitor) {
		equipmentVisitor.visitCard(this);
	}

	@Override
	public Watt getPower() {
		return new Watt(75);
	}

	@Override
	public Currency getNetPrice() {
		return new Currency(100);
	}

	@Override
	public Currency getDiscountPrice() {
		return new Currency(50);
	}

	@Override
	public void finalize() {
		// Clean-up before deallocation.
	}

}
