package gof.ch03_00.intro;

/**
 * Adapted from Design Patterns [Gang of Four], pp. 82-83. The manual calls this
 * class just "Wall".
 */
public class WallWithoutDoor extends MapSite implements Wall {

	@Override
	public boolean enter() {
		System.out.println("Ouch! I bumped my nose.");
		return false;
	}
	
	public WallWithoutDoor clone() {

		WallWithoutDoor wallClone = null;
		try {
			wallClone = this.getClass().getDeclaredConstructor().newInstance();
		} catch (Exception exception) {
			exception.printStackTrace(System.err);
		}
		return (wallClone);
	}
}
