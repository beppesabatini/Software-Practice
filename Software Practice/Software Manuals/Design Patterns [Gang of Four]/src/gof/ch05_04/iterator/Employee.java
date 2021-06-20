package gof.ch05_04.iterator;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 265. Part of the sample code used to illustrate the
 * {@linkplain gof.designpatterns.Iterator Iterator} design pattern.
 * {@linkplain ListTraverser} and {@linkplain PrintNEmployees} represent an
 * <b>internal traverser</b>, while {@linkplain ListIterator} and
 * {@linkplain ListDirection} represent an <b>external traverser</b>. Both
 * traverse the same Genericized List.</div>
 *
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Employee {
	private String lastName;

	public Employee(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return (this.lastName);
	}

	@Override
	public String toString() {
		return "Employee::lastName: " + getLastName();
	}

	@Override
	public Employee clone() {
		return (new Employee(this.lastName));
	}

	public boolean equals(Employee otherEmployee) {
		if (this.lastName.contentEquals(otherEmployee.getLastName())) {
			return (true);
		}
		return (false);
	}
}
