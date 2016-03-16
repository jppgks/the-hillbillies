package hillbillies.model;

public class Log extends GameObject {

	private double weight;
	private Position position;
	private boolean falling;
	private boolean aboveSolidCube;

	public double getWeight() {
		return this.weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public void advanceTime() {
		// TODO - implement Log.advanceTime
		throw new UnsupportedOperationException();
	}

	public boolean isFalling() {
		return this.falling;
	}

	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	public boolean isAboveSolidCube() {
		return this.aboveSolidCube;
	}
}