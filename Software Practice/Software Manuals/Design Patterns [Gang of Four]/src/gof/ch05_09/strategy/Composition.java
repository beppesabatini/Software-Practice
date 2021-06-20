package gof.ch05_09.strategy;

import java.util.List;

import gof.ch04_01.adapter.text.Coordinate;
import gof.ch05_09.strategy.StrategySupport.*;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 320. This class is part of the sample code illustrating the
 * {@linkplain gof.designpatterns.Strategy Strategy} design pattern. Not the
 * same Composition class as seen in earlier chapters. Both versions look
 * complex, but are just stubs for illustration purposes.
 * <p/>
 * In this example, an end-user simply has his choice of three different
 * algorithms for calculating where to place line breaks in a text document.
 * Each algorithm is defined and encapsulated in a Strategy object.</div>
 *
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_09/strategy/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Composition {

	private Compositor compositor;
	// The list of components:
	@SuppressWarnings("unused")
	private List<Component> components;
	// The number of components:
	@SuppressWarnings("unused")
	private int componentCount;
	// The Composition's line width:
	@SuppressWarnings("unused")
	private int lineWidth;
	// The position of line breaks.
	@SuppressWarnings("unused")
	private int[] lineBreaks;
	// The number of lines:
	@SuppressWarnings("unused")
	private int lineCount;

	public Composition(Compositor compositor) {
		this.compositor = compositor;
	}

	@SuppressWarnings("unused")
	public void repair() {
		Coordinate[] naturalSizes;
		Coordinate[] stretchability;
		Coordinate[] shrinkability;
		int componentCount;

		// Prepare the arrays with the desired component sizes.

		// Determine where the line breaks are:
		int breakCount;
		CompositionInfo compositionInfo = compositor.compose();
		if (compositionInfo == null) {
			return;
		}
		naturalSizes = compositionInfo.getNaturalSizes();
		stretchability = compositionInfo.getStretchability();
		shrinkability = compositionInfo.getShrinkability();
		componentCount = compositionInfo.getComponentCount();
		breakCount = compositionInfo.getBreakCount();
		lineWidth = compositionInfo.getLineWidth();
		lineBreaks = compositionInfo.getLineBreaks();
		lineCount = compositionInfo.getLineCount();

		// Lay out components according to breaks.
	}
}
