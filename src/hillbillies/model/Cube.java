package hillbillies.model;

import hillbillies.model.terrain.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public Log log;
	
	public Boulder getBoulder() {
		return boulder;
	}

	public void setBoulder(Boulder boulder) {
		this.boulder = boulder;
	}

	public Boulder boulder;
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
    public Position getPosition() {
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
            //this.getWorld().calculateConnectedToBorder();
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
//        if (ThreadLocalRandom.current().nextInt(5) == 0) {
            this.spawnBoulderOrLog();
//        }
       this.getWorld().calculateConnectedToBorder();
	}

    private void spawnBoulderOrLog() {
    	if (this.getTerrain() instanceof Tree) {
        	Log log = new Log(this.getPosition(), this.getWorld());
            this.getWorld().addLog(log);
            this.setLog(log);
    	} else {
    		Boulder boulder = new Boulder(this.getPosition(), this.getWorld());
        	this.getWorld().addBoulder(boulder);
        	this.setBoulder(boulder);
    	}
    }

	/**
     * Returns whether or not this cube has solid neighbouring cubes.
     *
	 * @return  True if this cube has solid neighbouring cubes,
     *          false otherwise.
	 */
	public boolean hasSolidNeighboringCubes() {
		return this.getNeighboringCubes().stream().anyMatch(Cube::isSolid);
	}

	public boolean isNeighboringCube(Position positionToLook){
        return this.getNeighboringCubes().contains(this.getWorld().getCube(positionToLook));
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
     */
    public void advanceTime(double dt) {
        if (!this.getWorld().isSolidConnectedToBorder(
                this.getPosition().getCubeCoordinates()[0],
                this.getPosition().getCubeCoordinates()[1],
                this.getPosition().getCubeCoordinates()[2])
                && this.isSolid()) {
        		this.caveIn();
        }
    }

	public List<Cube> getNeighboringCubes() {
        int posX = this.getPosition().getCubeCoordinates()[0];
        int posY = this.getPosition().getCubeCoordinates()[1];
        int posZ = this.getPosition().getCubeCoordinates()[2];
        int[][] allNeighboringCubePositions =  new int[][]{
                {posX-1, posY, posZ},
                {posX+1, posY, posZ},
                {posX, posY-1, posZ},
                {posX, posY+1, posZ},
                {posX, posY, posZ-1},
                {posX, posY, posZ+1}
        };
		List<Cube> validNeighboringCubes = new ArrayList<>();
		for (int[] cubePosition :
				allNeighboringCubePositions) {
			if (isValidPosition(cubePosition)) {
				validNeighboringCubes.add(this.getWorld().getCube(cubePosition[0], cubePosition[1], cubePosition[2]));
			}
		}
		return validNeighboringCubes;
	}

	/**
	 * Check whether the given coordinates are valid coordinates for
	 * any position.
	 * @param cubeCoordinates The coordinates to check.
	 * @return True if all coordinates are within range, false otherwise. | result ==
	 * |	(coordinates[0] >= 0) && (coordinates[0] < 50) &&
	 * |	(coordinates[1] >= 0) && (coordinates[1] < 50) &&
	 * | 	(coordinates[2] >= 0) && (coordinates[2] < 50)
	 */
	private boolean isValidPosition(int[] cubeCoordinates) {
		return	(cubeCoordinates[0] >= 0) && (cubeCoordinates[0] < this.getWorld().getNbCubesX()) &&
				(cubeCoordinates[1] >= 0) && (cubeCoordinates[1] < this.getWorld().getNbCubesY()) &&
				(cubeCoordinates[2] >= 0) && (cubeCoordinates[2] < this.getWorld().getNbCubesZ());
	}
    
    private double timeToCaveInCounter;

	/**
	 * @return
	 */
	public boolean hasLog() {
		if(this.getLog() == null)
			return false;
		return true;
	}

	/**
	 * @return
	 */
	public boolean hasBoulder() {
		if(this.getBoulder() == null)
			return false;
		return true;
	}

	/**
	 * Return the timeToCaveInCounter of this Cube.
	 */
	public double getTimeToCaveInCounter() {
		return timeToCaveInCounter;
	}

	/**
	 * Set the timeToCaveInCounter of this Cube to the given timeToCaveInCounter.
	 *
	 * @param  timeToCaveInCounter
	 *         The timeToCaveInCounter to set.
	 * @post   The timeToCaveInCounter of this of this Cube is equal to the given timeToCaveInCounter.
	 *       | new.gettimeToCaveInCounter() == timeToCaveInCounter
	 */
	public void setTimeToCaveInCounter(double timeToCaveInCounter) {
		this.timeToCaveInCounter = timeToCaveInCounter;
	}

    @Override
    public String toString() {
        return String.format("%s: %s", this.getTerrain(), this.getPosition().toString());
    }
}