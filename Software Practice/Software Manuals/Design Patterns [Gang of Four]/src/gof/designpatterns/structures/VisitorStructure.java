package gof.designpatterns.structures;

import gof.ch04_03.composite.*;
import gof.ch05_11.visitor.*;
import gof.designpatterns.Composite;
import gof.designpatterns.Visitor;

/**
 * <table class="javadoc-structure">
 * <tr>
 * <td><div class="diagram-title">Structure Diagram:
 * {@linkplain gof.designpatterns.Visitor Visitor} </div>
 * <p/>
 * <center>from Design Patterns [{@linkplain gof.designpatterns.GangOfFour Gang
 * of Four}], p. 334. ©1995 by Addison-Wesley.</center></td>
 * </tr>
 * <tr>
 * <td class="structure-diagram">
 * <img src="../../ch05_11/visitor/image-7185.png"/></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Visitor</i></b> — corresponds to the abstract
 * {@linkplain EquipmentVisitor}</li>
 * <li><b><i>ConcreteVisitor1</i></b> — the concrete subclass
 * {@linkplain PricingVisitor}</li>
 * <li><b><i>ConcreteVisitor2</i></b> — the concrete subclass
 * {@linkplain InventoryVisitor}</li>
 * </ul>
 * 
 * <ul>
 * <li><b><i>ObjectStructure</i></b> — corresponds to the class
 * {@linkplain Chassis}, a {@linkplain Composite}</li>
 * <li><b><i>Element</i></b> — corresponds to the abstract class
 * {@linkplain Equipment}</li>
 * <li><b><i>ConcreteElementA</i></b> — the concrete subclass
 * {@linkplain Card}</li>
 * <li><b><i>ConcreteElementB</i></b> — the concrete subclass
 * {@linkplain FloppyDisk}</li>
 * </ul>
 * </td>
 * </tr>
 * </table>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface VisitorStructure extends Visitor {

}
