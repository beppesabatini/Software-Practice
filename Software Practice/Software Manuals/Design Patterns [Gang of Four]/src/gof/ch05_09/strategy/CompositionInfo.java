package gof.ch05_09.strategy;

import gof.ch04_01.adapter.text.Coordinate;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 321. This class is part of the sample code illustrating the
 * {@linkplain gof.designpatterns.Strategy Strategy} design pattern. This class
 * does not appear in the manual; it's necessary because C++ and Java handle
 * arrays differently.</div>
 *
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class CompositionInfo {

	private Coordinate[] naturalSizes;
	private Coordinate[] stretchability;
	private Coordinate[] shrinkability;
	private int componentCount;
	private int lineWidth;
	private int breakCount;
	private int[] lineBreaks;
	private int lineCount;

	public Coordinate[] getNaturalSizes() {
		return naturalSizes;
	}

	public void setNaturalSizes(Coordinate[] naturalSizes) {
		this.naturalSizes = naturalSizes;
	}

	public Coordinate[] getStretchability() {
		return stretchability;
	}

	public void setStretchability(Coordinate[] stretchability) {
		this.stretchability = stretchability;
	}

	public Coordinate[] getShrinkability() {
		return shrinkability;
	}

	public void setShrinkability(Coordinate[] shrinkability) {
		this.shrinkability = shrinkability;
	}

	public int getComponentCount() {
		return componentCount;
	}

	public void setComponentCount(int componentCount) {
		this.componentCount = componentCount;
	}

	public int[] getLineBreaks() {
		return lineBreaks;
	}

	public void setLineBreaks(int[] lineBreaks) {
		this.lineBreaks = lineBreaks;
	}

	public int getBreakCount() {
		return breakCount;
	}

	public void setBreakCount(int breakCount) {
		this.breakCount = breakCount;
	}

	public int getLineWidth() {
		return lineWidth;
	}

	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}

	public int getLineCount() {
		return lineCount;
	}

	public void setLineCount(int lineCount) {
		this.lineCount = lineCount;
	}
}
