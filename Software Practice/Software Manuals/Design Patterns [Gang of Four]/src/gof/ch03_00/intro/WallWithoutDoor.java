package gof.ch03_00.intro;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 82-83. A simple solid Wall. The manual refers to this class as simply "Wall".
 * The Wall object is used to define Mazes in several design pattern
 * illustrations. </div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch03_00/intro/UML%20Diagram.jpg"/>
 * </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
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
