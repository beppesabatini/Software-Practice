package gof.designpatterns;

/**
 * <table border="1" cellpadding="10">
 * <tr>
 * <td colspan="3" class="gof-table-title"><center>Table 1.2: Design aspects
 * that design patterns let you vary.</center> <center> From <i>Design
 * Patterns</i> [{@linkplain GangOfFour Gang of Four}], p. 30.</center></td>
 * </tr>
 * <tr>
 * <td class="gof-table-subsubtitle">Purpose</td>
 * <td class="gof-table-subsubtitle">Design Pattern</td>
 * <td class="gof-table-subsubtitle">Aspect(s) That Can Vary</td>
 * </tr>
 * <tr>
 * <td rowspan="5" class="gof-table-subtitle">Creational</td>
 * <td>{@linkplain AbstractFactory}</td>
 * <td>families of product objects</td>
 * </tr>
 * <tr>
 * <td>{@linkplain Builder}</td>
 * <td>how a composite object gets created</td>
 * </tr>
 * <tr>
 * <td>{@linkplain FactoryMethod}</td>
 * <td>subclass of object that is instantiated</td>
 * </tr>
 * <tr>
 * <td>{@linkplain Prototype}</td>
 * <td>class of object that is instantiated</td>
 * </tr>
 * <tr>
 * <td>{@linkplain Singleton}</td>
 * <td>the sole instance of a class</td>
 * </tr>
 * <tr>
 * <td rowspan="7" class="gof-table-subtitle">Structural</td>
 * <td>{@linkplain Adapter}</td>
 * <td>interface to an object</td>
 * </tr>
 * <tr>
 * <td>{@linkplain Bridge}</td>
 * <td>implementation of an object</td>
 * </tr>
 * <tr>
 * <td>{@linkplain Composite}</td>
 * <td>structure and composition of an object</td>
 * </tr>
 * <tr>
 * <td>{@linkplain Decorator}</td>
 * <td>responsibilities of an object without subclassing</td>
 * </tr>
 * <tr>
 * <td>{@linkplain Facade}</td>
 * <td>interface to a subsystem</td>
 * </tr>
 * <tr>
 * <td>{@linkplain Flyweight}</td>
 * <td>storage costs of objects</td>
 * </tr>
 * <tr>
 * <td>{@linkplain Proxy}</td>
 * <td>how an object is accessed; its location</td>
 * </tr>
 * <tr>
 * <td rowspan="11" class="gof-table-subtitle">Behavioral></td>
 * <td>{@linkplain ChainOfResponsibility}</td>
 * <td>object that can fulfill a request</td>
 * </tr>
 * <tr>
 * <td>{@linkplain Command}</td>
 * <td>when and how a request is fulfilled</td>
 * </tr>
 * <tr>
 * <td>{@linkplain Interpreter}</td>
 * <td>grammar and interpretation of a language</td>
 * </tr>
 * <tr>
 * <td>{@linkplain Iterator}</td>
 * <td>how an aggregate's elements are accessed and traversed</td>
 * </tr>
 * <tr>
 * <td>{@linkplain Mediator}</td>
 * <td>how and which objects interact with each other</td>
 * </tr>
 * <tr>
 * <td>{@linkplain Memento}</td>
 * <td>what private information is stored outside an object, and when</td>
 * </tr>
 * <tr>
 * <td>{@linkplain Observer}</td>
 * <td>number of objects that depend on another object; how the dependent
 * objects stay up to date</td>
 * </tr>
 * <tr>
 * <td>{@linkplain State}</td>
 * <td>states of an object</td>
 * </tr>
 * <tr>
 * <td>{@linkplain Strategy}</td>
 * <td>an algorithm</td>
 * </tr>
 * <tr>
 * <td>{@linkplain TemplateMethod}</td>
 * <td>steps of an algorithm</td>
 * </tr>
 * <tr>
 * <td>{@linkplain Visitor}</td>
 * <td>operations that can be applied to object(s) without changing their
 * class(es)</td>
 * </tr>
 * </table>
 * <link rel="stylesheet" href="../styles/gof-table.css">
 */
public interface VariationsEnabled extends GangOfFour {

}
