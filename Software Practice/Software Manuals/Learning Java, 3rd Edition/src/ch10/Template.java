package ch10;

import java.util.*;
import java.util.regex.*;

/**
 * From Learning Java, 3rd Edition, p. 339-340. For when you can't or don't want
 * to install a templating system such as JSP.
 */
public class Template {
	Properties values = new Properties();
	Pattern templateComment = Pattern.compile("(?si)<!--\\s*TEMPLATE:(\\w+).*?-->");

	public void set(String name, String value) {
		values.setProperty(name, value);
	}

	public String fillIn(String text) {
		Matcher matcher = templateComment.matcher(text);

		StringBuffer buffer = new StringBuffer();
		while (matcher.find()) {
			String name = matcher.group(1);
			String value = values.getProperty(name);
			matcher.appendReplacement(buffer, value);
		}
		matcher.appendTail(buffer);
		return buffer.toString();
	}

	public static void main(String[] args) {
		String templateText = "";
		templateText += "<html>\n";
		templateText += "  <head></head>\n";
		templateText += "  <body>\n" + "This is some text.\n";
		templateText += /**/ "<!-- TEMPLATE:foo  -->\n";
		templateText += /**/ "Some more text.\n";
		templateText += /**/ "\n";
		templateText += /**/ "<!--template:bar This is text -->\n"; 
		templateText += /**/ "More text.\n";
		templateText += /**/ "<!-- TEMPLATE:bar \n"; // The tag is split across two lines here.
		templateText += /**/ "-->\n"; // It works here but won't work in most tag systems.
		templateText += "  </body>\n";
		templateText += "</html>\n";

		Template template = new Template();
		template.set("foo", "Value \"foo\" filled in here");
		template.set("bar", "Value \"bar\" filled in here");
		System.out.println("+++ Original Empty Template:");
		System.out.println(templateText);
		System.out.println("+++ Filled-In Template:");
		System.out.println(template.fillIn(templateText));
	}

}
