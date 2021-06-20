package gof.designpatterns;

/**
 * <div class="javadoc-text">An instance of the <b>ChainOfResponsibility</b>
 * pattern, described in <i>Design Patterns</i> [{@linkplain GangOfFour Gang of
 * Four}], pp. 223-232. Avoid coupling the sender of a request to its receiver
 * by giving more than one object a chance to handle the request. Chain the
 * receiving objects and and pass the request along the chain until an object
 * handles it.
 * <p/>
 * In this illustration of a simple Chain of Responsibility, an end-user is
 * trying to get Help information concerning a Button widget. The system will
 * first look for a help message in the Button itself, then look in its parent
 * the Dialog widget, and finally look in the Application for a global list of
 * supported help messages. The UML Diagram is below. See also the
 * {@linkplain gof.designpatterns.structures.ChainOfResponsibilityStructure
 * ChainOfResponsibility Structure} diagram.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_01/chainofresponsibility/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <style> div.javadoc-text { width: 580px; }</style>
 * <p/>
 * <style> div.javadoc-diagram {margin-left: 5px;}</style>
 */
public interface ChainOfResponsibility extends GangOfFour {

}
