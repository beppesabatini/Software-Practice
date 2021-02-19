package ch09;

import java.util.*;
import java.util.concurrent.*;

import java.net.*;
import java.io.IOException;

// From p. 293-294
public class SiteTimer {
	CyclicBarrier barrier;
	List<Result> results = new ArrayList<Result>();

	private class Result implements Comparable<Result> {
		Long time;
		String site;

		Result(Long time, String site) {
			this.time = time;
			this.site = site;
		}

		public int compareTo(Result r) {
			return time.compareTo(r.time);
		}
	}

	static long timeConnect(String site) {
		long start = System.currentTimeMillis();
		try {
			new URL(site).openConnection().connect();
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		return System.currentTimeMillis() - start;
	}

	void showResults() {
		Collections.sort(results);
		for (Result result : results) {
			System.out.printf("%-30.30s : %d ms\n", result.site, result.time);
		}
		System.out.println("------------------");
	}

	public void start(String[] args) {
		Runnable showResultsAction = new Runnable() {
			public void run() {
				showResults();
				results.clear();
			}
		};
		barrier = new CyclicBarrier(args.length, showResultsAction);

		for (final String site : args)
			new Thread() {
				public void run() {
					while (true) {
						long time = timeConnect(site);
						results.add(new Result(time, site));
						try {
							barrier.await();
						} catch (BrokenBarrierException e) {
							return;
						} catch (InterruptedException e) {
							return;
						}
					}
				}
			}.start();
	}

	public static void main(String[] args) throws IOException {
		if (args.length <= 0) {
			System.out.println("Usage: <url1>, <url2>, <url3>, ...");
			System.exit(0);
		}
		new SiteTimer().start(args);
	}
}
