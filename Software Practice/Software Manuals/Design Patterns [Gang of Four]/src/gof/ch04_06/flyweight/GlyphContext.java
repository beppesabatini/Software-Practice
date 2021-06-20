package gof.ch04_06.flyweight;

import gof.ch04_06.flyweight.FlyweightSupport.BTree;
import gof.ch04_06.flyweight.FlyweightSupport.Font;
import gof.designpatterns.Flyweight;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 202. Sample
 * code to illustrate the {@linkplain gof.designpatterns.Flyweight Flyweight}
 * design pattern. See the local {@linkplain gof.ch04_06.flyweight.Character
 * Character} object for more detail.
 * <p/>
 * The point to notice is that most of the information concerning Characters and
 * Glyphs is stored extrinsically or externally, right here in this GlyphContext
 * class.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch04_06/flyweight/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class GlyphContext implements Flyweight {

	private int index;
	private BTree<Integer, Font> fonts;

	public GlyphContext() {
		// Stub
	}

	public void next(Integer step) {
		if (step == null) {
			step = 1;
		}
		// Stub
	}

	public void insert(Integer quantity) {
		if (quantity == null) {
			quantity = 1;
		}
		// Stub
	}

	public Font getFont() {
		// Stub
		return (this.fonts.get(this.index));
	}

	public void setFont(Font font, Integer span) {
		if (span == null) {
			span = 1;
		}
		// Stub
	}

	@Override
	public void finalize() {
		// Any clean-up needed at deallocation time.
	}
}
