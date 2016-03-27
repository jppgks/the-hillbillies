package hillbillies.model.gameobject;

import hillbillies.model.Position;
import hillbillies.model.World;
import hillbillies.model.terrain.Solid;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Material extends GameObject {

	/**
	 * Variable registering the weight of this material.
	 */
	final double weight = ThreadLocalRandom.current().nextInt(10,51);

	/**
	 * Variable registering the position of this material.
	 */
	Position position;

	/**
	 * Variable registering whether or not this material is falling.
	 */
	boolean falling;

    /**
     * Variable registering the world this material is on.
     */
    World world;

    /**
     * Returns the weight of this material.
     *
     * @return The weight of this material.
     */
	public double getWeight() {
		return this.weight;
	}

    /**
     * Returns the position object for this material.
     *
     * @return This material's position.
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * Returns whether or not this material is currently falling.
     *
     * @return True when this material is currently falling,
     *         false otherwise.
     */
	public boolean isFalling() {
		return this.falling;
	}

    /**
     * Set whether or not this material is currently falling.
     *
     * @post The value of the falling attribute of this material is set to
     *       the value of the given falling parameter.
     *
     * @param falling
     */
	public void setFalling(boolean falling) {
		this.falling = falling;
	}

    /**
     * Returns whether or not this material is currently above a solid cube.
     *
     * @return Whether or not this material is currently above a solid cube.
     */
	public boolean isAboveSolidCube() {
        int[] positionCoordinates = this.getPosition().getCubeCoordinates();
		return (this.world.getCube(positionCoordinates[0], positionCoordinates[1], positionCoordinates[2]-1).getTerrain() instanceof Solid);
	}

	public void advanceTime(double dt) {
		// TODO - implement Material.advanceTime
        if (! this.isAboveSolidCube()) {
            this.setFalling(true);
        }
	}

}