package ch13;

import java.net.*;
import java.io.*;
import java.util.concurrent.*;

/**
 * From Learning Java, 3rd Edition, p. 444.
 */
public class TinyHttpd {
	public static void main(String argv[]) throws IOException {
		Executor executor = Executors.newFixedThreadPool(3);
		@SuppressWarnings("resource")
		ServerSocket ss = new ServerSocket(Integer.parseInt(argv[0]));
		while (true)
			executor.execute(new TinyHttpdConnection(ss.accept()));
	}
}
