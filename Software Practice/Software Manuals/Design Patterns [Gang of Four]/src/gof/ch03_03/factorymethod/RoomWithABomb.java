package gof.ch03_03.factorymethod;

import gof.ch03_00.intro.Room;

/**
 * <div class="javadoc-text">Mentioned at Design Patterns [Gang of Four], p.
 * 115. A class used in the {@linkplain gof.designpatterns.FactoryMethod
 * FactoryMethod} sample code. See {@linkplain BombedMazeFactory} for more
 * information.</div>
 * 
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
