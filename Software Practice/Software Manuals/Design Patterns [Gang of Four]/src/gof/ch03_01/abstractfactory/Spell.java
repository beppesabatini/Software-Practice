package gof.ch03_01.abstractfactory;

import gof.designpatterns.AbstractFactory;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p. 93.
 * Part of the sample code for the {@linkplain AbstractFactory} design pattern.
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Spell {

	private String magicWord;

	public String getMagicWord() {
		return (this.magicWord);
	}

	public Spell(String magicWord) {
		this.magicWord = magicWord;
	}

	public boolean castSpell(String magicWord) {
		if (magicWord == null || "".equals(magicWord)) {
			System.err.println("Sorry, you must say the magic word");
			return (false);
		}
		if (this.magicWord == null || "".equals(this.magicWord)) {
			System.err.println("Sorry, the magic word is not defined");
			return (false);
		}
		if (this.magicWord.equals(magicWord) == false) {
			System.out.println("Sorry, that is the wrong magic word");
			return (false);
		}
		System.out.println("Good! That is the right magic word");
		return (true);
	}
}
