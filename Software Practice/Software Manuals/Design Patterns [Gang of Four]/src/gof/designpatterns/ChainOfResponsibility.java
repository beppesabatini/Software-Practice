package gof.designpatterns;

/**
 * <div class="javadoc-text">An instance of the <b>ChainOfResponsibility</b>
 * pattern, described in <i>Design Patterns</i> [{@linkplain GangOfFour Gang of Four}],
 * pp. 223-232. Avoid coupling the sender of a request to its receiver by giving
 * more than one object a chance to handle the request. Chain the receiving
 * objects and and pass the request along the chain until an object handles it.
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
 * <div class="javadoc-diagram">
 * <img src="../ch05_01/chainofresponsibility/UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../styles/gof.css">
 */
public interface ChainOfResponsibility extends GangOfFour {

}
