package gof.ch03_00.intro;

import java.io.IOException;

/**
 * Adapted from Design Patterns [Gang of Four], pp. 82-83. The manual calls this
 * class just "Door". Used in several design pattern illustrations.
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"><img src="UML Diagram.jpg"/></div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class WallWithDoor extends MapSite implements Wall {

	private Room room01;
	private Room room02;
	private boolean isOpen = false;

	public Room getRoom01() {
		return (this.room01);
	}

	public Room getRoom02() {
		return (this.room02);
	}

	public Room getOtherSideFromRoom(Room sourceRoom) throws IOException {
		if (sourceRoom == room01) {
			return (this.room02);
		}
		if (sourceRoom == room02) {
			return (this.room01);
		}
		throw new IOException(sourceRoom.toString() + "is on neither side of " + this.toString());
	}

	public boolean isOpen() {
		return (this.isOpen);
	}

	public boolean getIsOpen() {
		return (this.isOpen);
	}

	public WallWithDoor(Room room01, Room room02) {
		this.room01 = room01;
		this.room02 = room02;
	}

	@Override
	public boolean enter() {
		if (this.isOpen == true) {
			// If we ever implement a gamePlayer object, add this:
			// gamePlayer.setRoom(getOtherSideFromRoom(gamePlayer.getRoom()));
			return (true);
		}
		return (false);
	}

	private boolean isMalformedWall(WallWithDoor otherWall) {
		if (this.room01 == null || this.room02 == null) {
			return (true);
		}
		if (otherWall.getRoom01() == null || otherWall.getRoom02() == null) {
			return (true);
		}
		return (false);
	}

	public boolean deepEquals(WallWithDoor otherWall) {
		if (otherWall == null) {
			return (false);
		}
		if (isMalformedWall(otherWall)) {
			String message = "This should never happen. Broken logic in WallWithDoor#deepEquals()";
			new IOException(message).printStackTrace(System.err);
			System.exit(1);
		}
		if (this.room01.getRoomNumber() != otherWall.getRoom01().getRoomNumber()) {
			return (false);
		}
		if (this.room02.getRoomNumber() != otherWall.getRoom02().getRoomNumber()) {
			return (false);
		}

		return (true);
	}
}
