package ch11;

import java.util.logging.*;

//This code does not appear in Learning Java ch. 11.

public class LogTest2 {
	public static void main(String argv[]) {
		Logger logger = Logger.getLogger("DeviceLog");

		logger.info("<Device id=\"99\"/>");
	}
}
