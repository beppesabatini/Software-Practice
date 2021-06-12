package gof.ch03_03.factorymethod;

import gof.designpatterns.FactoryMethod;

/**
 * <div style="javadoc-text">Not in the manual. An element in the illustration
 * of the {@linkplain FactoryMethod} design pattern.</div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Bomb {
	private int explosivePower;
	private int fuseLength;

	public Bomb(int explosivePower, int fuseLength) {
		this.explosivePower = explosivePower;
		this.fuseLength = fuseLength;
	}

	public int getExplosivePower() {
		return explosivePower;
	}

	public void setExplosivePower(int explosivePower) {
		this.explosivePower = explosivePower;
	}

	public int getFuseLength() {
		return fuseLength;
	}

	public void setFuseLength(int fuseLength) {
		this.fuseLength = fuseLength;
	}
}
