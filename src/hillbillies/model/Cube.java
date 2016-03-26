package hillbillies.model;

import hillbillies.model.gameobject.Boulder;
import hillbillies.model.gameobject.Log;
import hillbillies.model.terrain.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Cube {

    /**
     * Creates a new cube and initializes it with given position, terrain type and associated world.
     *
	 * @param x
	 * 			X-coordinate for this new cube
	 * @param y
	 * 			Y-coordinate for this new cube
	 * @param z
	 * 			Z-coordinate for this new cube
	 * @param type
	 * 			The terrain type for this new cube, encoded as integer.
     * @param world
     *          The world this cube is part of.
	 *
	 * @post An new cube is created and initialized with the given position, terrain type and world.
	 *
	 */
	public Cube(int x, int y, int z, int type, World world){
        this.setPosition(new Position(new int[] {x,y,z}));
        this.setTerrain(type, true);
        this.setWorld(world);
	}

    /**
     * Variable registering the terrain type of this cube.
     */
	private Terrain terrain;

    /**
     * Variable registering the directly adjacent cubes of this cube.
     */
    private Set<Cube> directlyAdjacentCubes = new HashSet<>();

    /**
     * Variable registering the position of this cube.
     */
    private Position position;

    /**
     * Variable registering the world this cube is in.
     */
    private World world;

    /**
     * Variable registering the units currently present on this cube.
     */
    private Set<Unit> unit = new HashSet<>();

    /**
     * Set the position of this cube to the given position.
     *
     * @post      The position of this cube is equal to the given position.
     *          | new.getposition() == position
     *
     * @param     position
     *            The position to set.
     */
    private void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Return the position of this cube.
     *
     * @return The position of this cube.
     */
    private Position getPosition() {
        return position;
    }

    /**
     * Sets the terrain of this cube according to the given terrain type.
     *
     * @post The terrain type of this cube is equal to the given type.
     *
     * @param type
     *          The terrain type encoded as an integer value as described in IFacade.
     */
    public void setTerrain(int type, boolean constructing){
        switch (type) {
            case 0:
                this.terrain = new Air();
                break;
            case 1:
                this.terrain = new Rock();
                break;
            case 2:
                this.terrain = new Tree();
                break;
            case 3:
                this.terrain = new Workshop();
                break;
        }
        if (!constructing) {
            this.getWorld().notifyTerrainChangeListener(
                    this.getPosition().getCubeCoordinates()[0],
                    this.getPosition().getCubeCoordinates()[1],
                    this.getPosition().getCubeCoordinates()[2]
            );
            this.getWorld().calculateConnectedToBorder();
        }
    }
    /**
     * Returns the terrain type of this cube.
     *
     * @return The terrain type of this cube.
     */
    public Terrain getTerrain(){
        return this.terrain;
    }

	/**
	 * @param world
	 */
	private void setWorld(World world) {
		this.world = world;
		
	}

    /**
     *
     *
     * @return
     */
	private World getWorld() {
		return this.world;
	}

    /**
     * Calculates the directly adjacent cubes for this cube.
     *
     * @post      The set of directly adjacent cubes for this cube is updated.
     */
    private void calculateDirectlyAdjacentCubes() {
        int thisX = this.getPosition().getCubeCoordinates()[0];
        int thisY = this.getPosition().getCubeCoordinates()[1];
        int thisZ = this.getPosition().getCubeCoordinates()[2];
        Cube left = this.getWorld()
                .getCube(
                        thisX-1,
                        thisY,
                        thisZ
                );
        Cube right = this.getWorld()
                .getCube(
                        thisX+1,
                        thisY,
                        thisZ
                );
        Cube front = this.getWorld()
                .getCube(
                        thisX,
                        thisY+1,
                        thisZ
                );
        Cube back = this.getWorld()
                .getCube(
                        thisX,
                        thisY-1,
                        thisZ
                );
        Cube under = this.getWorld()
                .getCube(
                        thisX,
                        thisY,
                        thisZ-1
                );
        Cube over = this.getWorld()
                .getCube(
                        thisX,
                        thisY,
                        thisZ+1
                );
        this.directlyAdjacentCubes.add(left);
        this.directlyAdjacentCubes.add(right);
        this.directlyAdjacentCubes.add(front);
        this.directlyAdjacentCubes.add(back);
        this.directlyAdjacentCubes.add(under);
        this.directlyAdjacentCubes.add(over);
	}
	
	/**
	 * Method that's invoked when a solid cube is not connected to border.
	 * 			
	 * @post ...
	 */
    void caveIn() {
        this.setTerrain(0, false);
        if (ThreadLocalRandom.current().nextInt(5) == 0) {
            this.spawnBoulderOrLog();
        }
	}

    private void spawnBoulderOrLog() {
        if (ThreadLocalRandom.current().nextInt(2) == 0) {
            this.getWorld().addLog(new Log(this.getPosition(), this.getWorld()));
        } else {
            this.getWorld().addBoulder(new Boulder(this.getPosition(), this.getWorld()));
        }
    }

	/**
     * Returns whether or not this cube has solid neighbouring cubes.
     *
	 * @return  True if this cube has solid neighbouring cubes,
     *          false otherwise.
	 */
	public boolean hasSolidNeighboringCubes() {
		for (int i = -1; i < 1; i++) {
			if( 0 <= (position.getCubeCoordinates()[0]-i) &&
					(position.getCubeCoordinates()[0]-i) < this.world.getNbCubesX())
				if(this.getWorld().getCube(position.getCubeCoordinates()[0]-i,
						 position.getCubeCoordinates()[1],
						 position.getCubeCoordinates()[2]).isSolid())
					return true;
			if( 0 <= (position.getCubeCoordinates()[1]-i) &&
					(position.getCubeCoordinates()[1]-i) < this.world.getNbCubesY())
				if(this.getWorld().getCube(position.getCubeCoordinates()[0],
						 position.getCubeCoordinates()[1]-i,
						 position.getCubeCoordinates()[2]).isSolid())
					return true;
			if( 0 <= (position.getCubeCoordinates()[2]-i) &&
					(position.getCubeCoordinates()[2]-i) < this.world.getNbCubesZ())
				if(this.getWorld().getCube(position.getCubeCoordinates()[0],
						 position.getCubeCoordinates()[1],
						 position.getCubeCoordinates()[2]-i).isSolid())
					return true;
		}
		return false;
	}

    /**
     * Places a given unit at this cube.
     *
     * @param     unit
     *            The unit to add to this cube.
     */
	public void setUnit(Unit unit) {
		this.unit.add(unit);
	}

    /**
     * Returns all units present on this cube.
     *
     * @return A set of units present on this cube, empty set when none such exist.
     */
	private Set<Unit> getUnits() {
		return unit;
	}

	/**
     * Returns whether or not the terrain type of this cube is solid.
     *
	 * @return true when the terrain type of this cube is impassable;
     *         false otherwise.
	 */
	public boolean isSolid() {
		return (this.getTerrain() instanceof Solid);
	}

    /**
     * Returns whether or not there are units on this cube.
     *
     * @return true if there are units present on this cube;
     *         false otherwise.
     */
	public boolean isOccupied() {
		return (this.getUnits().size() > 0);
	}

    /**
     * Advance the state of this cube by the given time period.
     *
     * @param     dt
     *            The time period, in seconds, by which to advance the cube's state.
     *
     * @note Need to check whether or not this cube needs to cave in.
     */
    public void advanceTime(double dt) {
        // TODO - implement World.advanceTime
        if (! this.getWorld().isSolidConnectedToBorder(
                this.getPosition().getCubeCoordinates()[0],
                this.getPosition().getCubeCoordinates()[1],
                this.getPosition().getCubeCoordinates()[2])
                ) {
            this.caveIn();
        }
    }
}