package gof.designpatterns.structures;

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
 * <td class="structure-diagram"><img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_11/visitor/image-7185.png"/></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Visitor</i></b> — corresponds to the abstract
 * {@linkplain gof.ch05_11.visitor.EquipmentVisitor EquipmentVisitor}</li>
 * <li><b><i>ConcreteVisitor1</i></b> — the concrete subclass
 * {@linkplain gof.ch05_11.visitor.PricingVisitor PricingVisitor}</li>
 * <li><b><i>ConcreteVisitor2</i></b> — the concrete subclass
 * {@linkplain gof.ch05_11.visitor.InventoryVisitor InventoryVisitor}</li>
 * </ul>
 * 
 * <ul>
 * <li><b><i>ObjectStructure</i></b> — corresponds to the class
 * {@linkplain gof.ch05_11.visitor.Chassis Chassis} (a
 * {@linkplain gof.designpatterns.Composite Composite})</li>
 * <li><b><i>Element</i></b> — corresponds to the abstract class
 * {@linkplain gof.ch04_03.composite.Equipment Equipment}</li>
 * <li><b><i>ConcreteElementA</i></b> — the concrete subclass
 * {@linkplain gof.ch05_11.visitor.Card Card}</li>
 * <li><b><i>ConcreteElementB</i></b> — the concrete subclass
 * {@linkplain gof.ch04_03.composite.FloppyDisk FloppyDisk}</li>
 * </ul>
 * </td>
 * </tr>
 * </table>
 * 
 * <pre>
 * <style> 
 * table.javadoc-structure { 
 *     padding: 5px; width: 580px; 
 * }
 *
 * div.diagram-title { 
 *     font-size: 16px; font-weight: bold; text-align:center; 
 * }
 *
 * td.structure-diagram { 
 *     padding: 6px 0 0 0; 
 * }
 * </style>
 * </pre>
 */
public interface VisitorStructure extends Visitor {

}
