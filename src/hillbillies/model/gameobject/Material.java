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
	
	double fallingSpeed = 3.0;

    /**
     * Variable registering the world this material is on.
     */
    World world;

	private Position startPosition;

	private double fallDistance = 0;

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
	
	private boolean getFalling(){
		return this.falling;
	}
    /**
     * Returns whether or not this material is currently above a solid cube.
     *
     * @return Whether or not this material is currently above a solid cube.
     */
	public boolean isAboveSolidCube() {
        int[] positionCoordinates = this.getPosition().getCubeCoordinates();
		if(positionCoordinates[2]==0)
			return true;
        return (this.world.getCube(positionCoordinates[0], positionCoordinates[1], positionCoordinates[2]-1).getTerrain() instanceof Solid);
	}

	public void advanceTime(double dt) {
		// TODO - implement Material.advanceTime
        if (! this.isAboveSolidCube()) {
            this.setFalling(true);
            startPosition = this.getPosition();
            world.getCube(this.getPosition().getCubeCoordinates()[0],
            		this.getPosition().getCubeCoordinates()[1], 
            		this.getPosition().getCubeCoordinates()[2]).logOrBoulder = null;
        }
        if(this.getFalling()){
        	fall(dt);
        }
	}
	
	/**
	 * @param dt 
	 * 
	 */
	private void fall(double dt) {
		if(Math.abs(this.getFalldistance()) >= 1){
			position = new Position(new double[]{startPosition.getDoubleCoordinates()[0],
					startPosition.getDoubleCoordinates()[1],
					startPosition.getDoubleCoordinates()[2] -1});
					setFalldistance(0);
					this.setFalling(false);
					world.getCube(startPosition.getCubeCoordinates()[0],
							startPosition.getCubeCoordinates()[1],
							startPosition.getCubeCoordinates()[2] -1).logOrBoulder = this;
					
		}else
			this.setFalldistance(this.getFalldistance() + this.fallingSpeed*dt);
	}

	/**
	 * @return
	 */
	private double getFalldistance() {
		return fallDistance;
	}
	private void setFalldistance(double distance){
		this.fallDistance = distance;
	}
	public void setPosition(Position position){
		this.position = position;
	}

}