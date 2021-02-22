package ch09;

import java.net.*;
import java.io.*;
import java.util.concurrent.*;

import ch13.TinyHttpdConnection;

/**
 * From Learning Java, 3rd Edition, p. 284. Not clear yet how to use this.
 */
public class ExecutorHttpd {
	ExecutorService executor = Executors.newFixedThreadPool(3);

	public void start(int port) throws IOException {
		final ServerSocket ss = new ServerSocket(port);
		while (!executor.isShutdown()) {
			executor.submit(new TinyHttpdConnection(ss.accept()));
		}
		ss.close();
	}

	public void shutdown() throws InterruptedException {
		System.out.println("Shutting down");
		executor.shutdown();
		executor.awaitTermination(30, TimeUnit.SECONDS);
		executor.shutdownNow();
	}

	public static void main(String argv[]) throws Exception {
		// new ExecutorHttpd().start(Integer.parseInt(argv[0]));
		new ExecutorHttpd().start(Integer.parseInt("1234"));
	}
}
