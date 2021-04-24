package je3.ch18.sql;

public enum SqlDialect {
	MYSQL("mysql"), SQLITE("sqlite");

	private String urlName;

	SqlDialect(String urlName) {
		this.urlName = urlName;
	}

	public String getUrlName() {
		return (this.urlName);
	}

	static public SqlDialect fromUrlName(String urlName) {
		for (SqlDialect sqlDialect : SqlDialect.values()) {
			if (sqlDialect.getUrlName().equals(urlName)) {
				return (sqlDialect);
			}
		}
		return (null);
	}
}
