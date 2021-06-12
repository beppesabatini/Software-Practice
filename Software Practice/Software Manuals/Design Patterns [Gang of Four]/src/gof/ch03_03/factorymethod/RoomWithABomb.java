package gof.ch03_03.factorymethod;

import gof.ch03_00.intro.Room;
import gof.designpatterns.FactoryMethod;

/**
 * <div class="javadoc-text">Mentioned at Design Patterns [Gang of Four], p.
 * 115. An element in the {@linkplain FactoryMethod} example.</div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class RoomWithABomb extends Room {
	private Bomb liveBomb;

	public Bomb getLiveBomb() {
		return liveBomb;
	}

	public void setLiveBomb(Bomb liveBomb) {
		this.liveBomb = liveBomb;
	}

	public RoomWithABomb(int roomNumber, Bomb liveBomb) {
		super(roomNumber);
		this.liveBomb = liveBomb;
	}
}
