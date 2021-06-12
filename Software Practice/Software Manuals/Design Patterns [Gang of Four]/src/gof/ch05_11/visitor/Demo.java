package gof.ch05_11.visitor;

import gof.ch04_03.composite.CompositeEquipment;
import gof.ch04_03.composite.FloppyDisk;
import gof.designpatterns.Composite;
import gof.designpatterns.Visitor;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 170-172, 340-343. Runs much of the sample code for the {@linkplain Visitor}
 * and {@linkplain Composite} design patterns. This Demo class does not appear
 * in the manual.
 * <p/>
 * The concept of a Visitor design pattern is sound, but the sample code in the
 * manual is only a sketch. If implemented literally (as we have done here) it
 * will be buggy. See the {@linkplain PricingVisitor} for details.</div>
 *
 * <pre></pre>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Demo {

	public static void main(String[] args) {

		System.out.println(" • Building a new Equipment collection:");
		Chassis chassis01 = new Chassis("Classy Chassis");
		Card card = new Card("Graphics Card");
		chassis01.add(card);
		System.out.println(" -- Added: " + card.getName());
		FloppyDisk floppyDisk01 = new FloppyDisk("Report Data");
		chassis01.add(floppyDisk01);
		System.out.println(" -- Added: " + floppyDisk01.getName());
		CompositeEquipment compositeEquipment = new CompositeEquipment("Scrap Pile");
		chassis01.add(compositeEquipment);
		System.out.println(" -- Added: " + compositeEquipment.getName());
		System.out.println();

		System.out.println(" • Initializing pricing visitors:");
		PricingVisitor pricingVisitor = new PricingVisitor();
		System.out.println(" -- PricingVisitor total value: " + pricingVisitor.getTotalPrice().getValue());
		System.out.println();
		System.out.println(" • Adding pricing visitor:");
		chassis01.accept(pricingVisitor);
		System.out.println(" -- PricingVisitor total value: " + pricingVisitor.getTotalPrice().getValue());
		System.out.println();

		System.out.println(" • Adding equipment to CompositeEquipment:");
		FloppyDisk floppyDisk02 = new FloppyDisk("Old Report Data");
		compositeEquipment.add(floppyDisk02);
		System.out.println(" -- Added: " + floppyDisk02.getName());
		System.out.println(" • Relaunching pricing visitor:");
		pricingVisitor = new PricingVisitor();
		System.out.println(" -- PricingVisitor total value: " + pricingVisitor.getTotalPrice().getValue());
		chassis01.accept(pricingVisitor);
		System.out.println(" -- PricingVisitor total value: " + pricingVisitor.getTotalPrice().getValue());
		System.out.println();

	}

}
