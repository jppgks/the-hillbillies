package hillbillies.model;

import hillbillies.model.terrain.Passable;
import hillbillies.model.terrain.Solid;
import hillbillies.model.terrain.Workshop;

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
	boolean falling = false;
	
	double fallingSpeed = 3.0;

    /**
     * Variable registering the world this material is on.
     */
    World world;

	private Position startPosition;

	private double fallDistance = 0;

	private int floorsToFall = 0;

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
		if(this.world.getCube(positionCoordinates[0], positionCoordinates[1], positionCoordinates[2]).getTerrain() instanceof Workshop)
			return true;
        return (this.world.getCube(positionCoordinates[0], positionCoordinates[1], positionCoordinates[2]-1).getTerrain() instanceof Solid);
	}

	public void advanceTime(double dt) {
		// TODO - implement Material.advanceTime
        if ((!this.isAboveSolidCube()) && isFalling() == false) {
            this.setFalling(true);
            this.calculateFloorsTofall();
            startPosition = this.getPosition();
            if (this instanceof Log) {
            	world.getCube(this.getPosition().getCubeCoordinates()[0],
                		this.getPosition().getCubeCoordinates()[1], 
                		this.getPosition().getCubeCoordinates()[2]).setLog(null);
            } else {
            	world.getCube(this.getPosition().getCubeCoordinates()[0],
            		this.getPosition().getCubeCoordinates()[1], 
            		this.getPosition().getCubeCoordinates()[2]).setBoulder(null);
            }
        }
        if(this.getFalling()){
        	fall(dt);
        }
	}
	
	/**
	 * 
	 */
	private void calculateFloorsTofall() {
		int[] positionCoordinates = this.getPosition().getCubeCoordinates();
		while(true){
			if(positionCoordinates[2]==0)
				break;
			if((this.world.getCube(positionCoordinates[0], positionCoordinates[1], --positionCoordinates[2]).getTerrain() instanceof Passable))
				floorsToFall  += 1;
			else
				break;
		}
		
	}

	/**
	 * @param dt 
	 * 
	 */
	private void fall(double dt) {
		if(Math.abs(this.getFalldistance()) >= floorsToFall){
			position = new Position(new double[]{startPosition.getDoubleCoordinates()[0],
					startPosition.getDoubleCoordinates()[1],
					startPosition.getDoubleCoordinates()[2] -floorsToFall});
					setFalldistance(0);
					this.setFalling(false);
					if(this instanceof Log) {
						world.getCube(startPosition.getCubeCoordinates()[0],
							startPosition.getCubeCoordinates()[1],
							startPosition.getCubeCoordinates()[2] -floorsToFall).setLog((Log) this);
					} else {
						world.getCube(startPosition.getCubeCoordinates()[0],
								startPosition.getCubeCoordinates()[1],
								startPosition.getCubeCoordinates()[2] -floorsToFall).setBoulder((Boulder) this);
					}
					floorsToFall=0;
					
		}else
			this.setFalldistance(this.getFalldistance() + this.fallingSpeed*dt);
		position = new Position(new double[]{this.getPosition().getDoubleCoordinates()[0],
				this.getPosition().getDoubleCoordinates()[1],
				this.getPosition().getDoubleCoordinates()[2] + this.fallingSpeed*dt});
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