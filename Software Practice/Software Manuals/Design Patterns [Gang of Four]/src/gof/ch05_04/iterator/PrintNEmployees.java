package gof.ch05_04.iterator;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 267-270. Part of the sample code used to illustrate the
 * {@linkplain gof.designpatterns.Iterator Iterator} design pattern. This is a
 * subclass of the ListTraverser, one which uses most of its logic but adds a
 * counter.</div>
 *
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class PrintNEmployees<DataType> extends ListTraverser<DataType> implements gof.designpatterns.Iterator {

	private int maxEmployeesToPrint;
	private int count;

	public PrintNEmployees(List<DataType> list, int maxEmployeesToPrint) {
		super(list);
		this.maxEmployeesToPrint = maxEmployeesToPrint;
		this.count = 0;
	}

	@Override
	protected boolean processItem(final DataType listItem) {
		count++;
		String spacer = "";
		if (count < 10) {
			spacer = " ";
		}
		System.out.println(" -- In PrintNEmployees: " + spacer + count + ". " + listItem.toString());
		if (count < maxEmployeesToPrint) {
			return (true);
		}
		return (false);
	}
}
