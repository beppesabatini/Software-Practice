package ch10;

import java.util.*;

// From p. 309. 

public class Hello {
	public static void main(String[] args) {
		ResourceBundle bun;
		bun = ResourceBundle.getBundle("resources.Message", Locale.US);
		System.out.println(bun.getString("hello.world"));
		System.out.println(bun.getString("bye.for.now"));
		bun = ResourceBundle.getBundle("resources.Message", Locale.ITALY);
		System.out.println(bun.getString("hello.world"));
		System.out.println(bun.getString("bye.for.now"));
		bun = ResourceBundle.getBundle("resources.Message", Locale.CHINA);
		System.out.println(bun.getString("hello.world"));
		System.out.println(bun.getString("bye.for.now"));
	}
}
