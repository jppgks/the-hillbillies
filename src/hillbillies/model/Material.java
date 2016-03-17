package hillbillies.model;

public abstract class Material extends GameObject {

	double weight;
	Position position;
	boolean falling;
	boolean aboveSolidCube;

	public double getWeight() {
		return this.weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
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

	public void advanceTime() {
		// TODO - implement Material.advanceTime
		throw new UnsupportedOperationException();
	}

}