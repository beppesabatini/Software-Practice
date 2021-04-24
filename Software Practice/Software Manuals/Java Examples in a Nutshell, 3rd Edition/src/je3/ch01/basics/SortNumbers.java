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
package je3.ch01.basics;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 21-22. This class
 * demonstrates how to sort numbers using a simple algorithm.
 */
public class SortNumbers {
	/**
	 * This is a very simple sorting algorithm that is not very efficient when
	 * sorting large numbers of things.
	 **/
	public static void sort(double[] nums) {
		/*
		 * Loop through each element of the array, sorting as we go. Each time through,
		 * find the smallest remaining element, and move it to the first unsorted
		 * position in the array.
		 */
		for (int i = 0; i < nums.length; i++) {
			// Holds the index of the smallest element:
			int min = i;
			// Find the smallest one between i and the end of the array.
			for (int j = i; j < nums.length; j++) {
				if (nums[j] < nums[min]) {
					min = j;
				}
			}
			// Now swap the smallest one with element i.
			// This leaves all elements between 0 and i sorted.
			double tmp;
			tmp = nums[i];
			nums[i] = nums[min];
			nums[min] = tmp;
		}
	}

	/** This is a simple test program for the algorithm above */
	public static void main(String[] args) {
		// Create an array to hold numbers:
		double[] nums = new double[10];
		// Generate random numbers:
		for (int i = 0; i < nums.length; i++) {
			nums[i] = Math.random() * 100;
		}
		// Sort them:
		sort(nums);
		// Print them out:
		for (int i = 0; i < nums.length; i++) {
			System.out.println(nums[i]);
		}
	}
}
