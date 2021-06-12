package gof.ch05_04.iterator;

/**
 * <div class="javadoc-text">More non-functional stubs. Most of these are only
 * mentioned in passing in the manual. These are included here primarily to get
 * other stubs to compile.</div>
 *
 * <pre></pre>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class ListSupport {

	public static class Employee {
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

}
