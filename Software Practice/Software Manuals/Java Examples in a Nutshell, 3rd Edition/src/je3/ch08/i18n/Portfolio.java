/*
 * Copyright (c) 2004 David Flanagan.  All rights reserved.
 * This code is from the book Java Examples in a Nutshell, 3nd Edition.
 * It is provided AS-IS, WITHOUT ANY WARRANTY either expressed or implied.
 * You may study, use, and modify it for any non-commercial purpose,
 * including teaching and use in open-source projects.
 * You may distribute it non-commercially as long as you retain this notice.
 * For a commercial use license, or to purchase the book, 
 * please visit http://www.davidflanagan.com/javaexamples3.
 */
package je3.ch08.i18n;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import javax.swing.JOptionPane;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 217-219. A partial
 * implementation of a hypothetical stock portfolio class. We use it only to
 * demonstrate number and date internationalization.
 */
public class Portfolio {
	// The positions (purchases) in the portfolio:
	EquityPosition[] positions;
	// Used as the time for current quotes:
	Date lastQuoteTime = new Date();

	// Create a Portfolio
	public Portfolio(EquityPosition[] positions, Date lastQuoteTime) {
		this.positions = positions;
		this.lastQuoteTime = lastQuoteTime;
	}

	// A helper class; this represents a single stock purchase.
	static class EquityPosition {
		// Name of the stock:
		String name;
		// Number of shares held:
		int shares;
		// When purchased:
		Date purchased;
		// What currency are the prices expressed in?
		Currency currency;
		// Purchase price per share:
		double bought;
		// Current price per share:
		double current;

		/*
		 * Format objects like this one are useful for parsing strings as well as
		 * formating them. This is for converting date strings to Dates.
		 */
		static DateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");

		EquityPosition(String name, int shares, String date, Currency currency, double then, double now)
				throws ParseException {
			/*
			 * Convert the purchased date string to a Date object. The string must be in the
			 * format yyyy-mm-dd.
			 */
			purchased = dateParser.parse(date);
			// ...and store the rest of the fields, too.
			this.name = name;
			this.shares = shares;
			this.currency = currency;
			this.bought = then;
			this.current = now;
		}
	}

	// Return a localized HTML-formatted string describing the portfolio.
	public String toString() {

		// Obtain NumberFormat and DateFormat objects to format our data.
		NumberFormat number = NumberFormat.getInstance();
		NumberFormat price = NumberFormat.getCurrencyInstance();
		NumberFormat percent = NumberFormat.getPercentInstance();
		DateFormat shortdate = DateFormat.getDateInstance(DateFormat.MEDIUM);
		DateFormat fulldate = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);

		// Print some introductory data.
		String portfolio = "";
		portfolio += "<html>";
		portfolio += "  <body>";
		portfolio += "    <i>";
		portfolio += "      " + fulldate.format(lastQuoteTime);
		portfolio += "    </i>";
		portfolio += "    <table border=\"1\">";
		portfolio += "      <tr>";
		portfolio += "        <th>";
		portfolio += "          Symbol";
		portfolio += "        </th>";
		portfolio += "        <th>";
		portfolio += "          Shares";
		portfolio += "        </th>";
		portfolio += "        <th>";
		portfolio += "          Purchased";
		portfolio += "        </th>";
		portfolio += "        <th>";
		portfolio += "          At";
		portfolio += "        </th>";
		portfolio += "        <th>";
		portfolio += "          Quote";
		portfolio += "        </th>";
		portfolio += "        <th>";
		portfolio += "          Change";
		portfolio += "        </th>";
		portfolio += "      </tr>";
		// Display the table using the format() methods of the Format objects.
		for (int i = 0; i < positions.length; i++) {
			portfolio += "  <tr>";
			portfolio += "    <td>";
			portfolio += "      " + positions[i].name;
			portfolio += "    </td>";
			portfolio += "    <td>";
			portfolio += "      " + number.format(positions[i].shares);
			portfolio += "    </td>";
			portfolio += "    <td>";
			portfolio += "      " + shortdate.format(positions[i].purchased);
			portfolio += "    </td>";

			// Set the currency to use when printing the following prices.
			price.setCurrency(positions[i].currency);
			portfolio += "    <td>";
			portfolio += "      " + price.format(positions[i].bought);
			portfolio += "    </td>";
			portfolio += "    <td>";
			portfolio += "      " + price.format(positions[i].current);
			portfolio += "    </td>";
			portfolio += "    <td>";
			double change = (positions[i].current - positions[i].bought) / positions[i].bought;
			portfolio += "      " + percent.format(change);
			portfolio += "    </td>";
			portfolio += "  </tr>";
		}
		portfolio += "    </table>";
		portfolio += "  </body>";
		portfolio += "</html>";

		return (portfolio);
	}

	/**
	 * This is a test program that demonstrates the class.
	 **/
	public static void main(String[] args) throws ParseException {
		Currency dollars = Currency.getInstance("USD");
		Currency pounds = Currency.getInstance("GBP");
		Currency euros = Currency.getInstance("EUR");
		Currency yen = Currency.getInstance("JPY");

		// This is the portfolio to display.
		EquityPosition[] positions = new EquityPosition[] {
				new EquityPosition("WWW", 400, "2003-01-03", dollars, 11.90, 13.00),
				new EquityPosition("XXX", 1100, "2003-02-02", pounds, 71.09, 27.25),
				new EquityPosition("YYY", 6000, "2003-04-17", euros, 23.37, 89.12),
				new EquityPosition("ZZZ", 100, "2003-8-10", yen, 100000, 121345) };

		// Create the portfolio from these positions.
		Portfolio portfolio = new Portfolio(positions, new Date());

		/*
		 * Set the default locale using the language code and country code specified on
		 * the command line.
		 */
		if (args.length == 2) {
			Locale.setDefault(new Locale(args[0], args[1]));
		}

		/*
		 * Now display the portfolio. We use a Swing dialog box to display it, because
		 * the console may not be able to display non-ASCII characters like the currency
		 * symbols for pounds, euros, and yen.
		 */
		// This is displayed in the title bar:
		String defaultLocale = Locale.getDefault().getDisplayName();
		JOptionPane.showMessageDialog(null, portfolio, defaultLocale, JOptionPane.INFORMATION_MESSAGE);

		/*
		 * The modal dialog starts another thread running, so we have to exit explicitly
		 * when the user dismisses it.
		 */
		System.exit(0);
	}
}
