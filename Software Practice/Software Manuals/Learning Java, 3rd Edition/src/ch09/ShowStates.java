package ch09;

import java.net.*;
import java.io.*;

/**
 * This code not included in Learning Java chap. 09.
 */
public class ShowStates {

	public static void main(String[] args) throws Exception {
		new Thread() {
			@SuppressWarnings("resource")
			public void run() {
				try {
					new ServerSocket(1234).accept();
				} catch (IOException e) {
				}
			}
		}.start();
		Thread.sleep(2000);

		while (true) {
			Thread[] threads = new Thread[10]; // max threads
			int num = Thread.enumerate(threads);
			for (int i = 0; i < num; i++) {
				System.out.println(threads[i]);
				System.out.println(threads[i].getState());
			}
			Thread.sleep(1000);
		}
	}
}
