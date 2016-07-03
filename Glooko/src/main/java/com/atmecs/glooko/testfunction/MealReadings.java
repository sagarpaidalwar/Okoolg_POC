package com.atmecs.glooko.testfunction;

public class MealReadings {

	private int below;
	
	private int inRange;
	private int above;
	
	
	public int getBelow() {
		return below;
	}
	public int getInRange() {
		return inRange;
	}
	public int getAbove() {
		return above;
	}
	public void setBelow(int below) {
		this.below = below;
	}
	public void setInRange(int inRange) {
		this.inRange = inRange;
	}
	public void setAbove(int above) {
		this.above = above;
	}
	@Override
	public String toString() {
		return "MealReadings [below=" + below + ", inRange=" + inRange + ", above=" + above + "]";
	}
}
