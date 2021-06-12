package gof.ch03_01.abstractfactory;

import gof.ch03_00.intro.Room;
import gof.ch03_00.intro.WallWithDoor;
import gof.designpatterns.AbstractFactory;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p. 84.
 * Part of the sample code for the {@linkplain AbstractFactory} design pattern.
 * The manual calls this class "DoorNeedingSpell".</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"><img src="UML Diagram.jpg"/></div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class WallWithDoorNeedingSpell extends WallWithDoor {

	Spell spell;

	public WallWithDoorNeedingSpell(Room room01, Room room02, String magicWord) {
		super(room01, room02);
		this.spell = new Spell(magicWord);
	}

	@Override
	public boolean enter() {
		System.out.println("Sorry, you need a Spell to open this door");
		return (false);
	}

	public boolean enter(String magicWord) {
		boolean outcome = this.spell.castSpell(magicWord);
		return (outcome);
	}
}
