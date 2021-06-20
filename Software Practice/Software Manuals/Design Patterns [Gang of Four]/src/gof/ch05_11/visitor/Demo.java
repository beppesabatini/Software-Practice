package gof.ch05_11.visitor;

import gof.ch04_03.composite.CompositeEquipment;
import gof.ch04_03.composite.FloppyDisk;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 170-172, 340-343. Runs much of the sample code for the
 * {@linkplain gof.designpatterns.Visitor Visitor} and
 * {@linkplain gof.designpatterns.Composite Composite} design patterns. This
 * Demo class does not appear in the manual.</div>
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

		System.out.println(" • Adding $25 disk to Scrap Pile:");
		FloppyDisk floppyDisk02 = new FloppyDisk("Old Report Data");
		compositeEquipment.add(floppyDisk02);
		System.out.println(" -- Added: " + floppyDisk02.getName());
		System.out.println(" • Adding $25 disk to Scrap Pile:");
		FloppyDisk floppyDisk03 = new FloppyDisk("Even Older Report Data");
		compositeEquipment.add(floppyDisk03);
		System.out.println(" -- Added: " + floppyDisk03.getName());
		System.out.println(" • Relaunching pricing visitor:");
		pricingVisitor = new PricingVisitor();
		System.out.println(" -- PricingVisitor total value: " + pricingVisitor.getTotalPrice().getValue());
		chassis01.accept(pricingVisitor);
		System.out.println(" -- PricingVisitor total value: " + pricingVisitor.getTotalPrice().getValue());
		System.out.println();

	}

}
