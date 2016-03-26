package hillbillies.model;

import hillbillies.model.terrain.*;

import java.util.List;
import java.util.Set;

public class Cube {

	/**
	 * @param x
	 * 			coordinate
	 * @param y
	 * 			coordinate
	 * @param z
	 * 			coordinate
	 * @param type
	 * 			the terrain type if the cube
	 *
	 * @post create an new cube with the given coordinates and terrain type
	 *
	 */
	public Cube(int x, int y, int z, int type, World world){
		this.setWorld(world);
		this.setPosition(new Position(new int[] {x,y,z}));
		this.setTerrain(type);
	}

    /**
     * Variable registering the terrain type of this cube.
     */
	private Terrain terrain;

    /**
     * Variable registering the directly adjacent cubes of this cube.
     */
    private List<Cube> directlyAdjacentCubes;

    /**
     * Variable registering the position of this cube.
     */
    private Position position;

    /**
     * Variable registering whether or not this cube is connected to a border.
     */
    private boolean connectedToBorder;

    /**
     * Variable registering the world this cube is in.
     */
    private World world;

    /**
     * Variable registering the units currently present on this cube.
     */
    private Set<Unit> unit; // To initialize with empty set

	/**
	 * @param world
	 */
	private void setWorld(World world) {
		this.world = world;
		
	}

	private World getWorld() {
		return this.world;
	}

    private List<Cube> calculateDirectlyAdjacentCubes() {
		// TODO - implement Cube.calculateDirectlyAdjacentCubes
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns whether the cube at the given position is a solid cube that is
	 * connected to a border of the world through other directly adjacent solid
	 * cubes.
	 * 
	 * @note The result is pre-computed, so this query returns immediately.
	 * 
	 * @param x
	 *            The x-coordinate of the cube to test
	 * @param y
	 *            The y-coordinate of the cube to test
	 * @param z
	 *            The z-coordinate of the cube to test
	 * @return true if the cube is connected; false otherwise
	 */
	private boolean isConnectedToBorder() {
		// TODO - implement Cube.connectedToBorder
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Method that's invoked when a solid cube is not connected to border.
	 * 			
	 * @post ...
	 */
	private void caveIn() {
		// TODO - implement Cube.caveIn
		throw new UnsupportedOperationException();
	}

	/**
     * Returns the chance for spawning a log or a boulder.
     *
     * @pre The cube is caved in.
     *
	 * @return The chance for spawning a log or a boulder.
	 */
	private double getChanceForMaterial() {
		// TODO - implement Cube.getChanceForMaterial
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns whether or not the terrain of this cube is passable.
     *
     * @return  True if the cube is passable and the unit can walk through,
     *          false otherwise.
	 */
	private boolean isPassable() {
		return (this.terrain instanceof Passable);
	}

	/**
     * Sets the terrain of this cube according to the given terrain type.
     *
	 * @post The terrain type of this cube is equal to the given type.
	 *
     * @param type
     *          The terrain type encoded as an integer value as described in IFacade.
     */
	public void setTerrain(int type){
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
	public void setUnit(Unit unit) {
		this.unit.add(unit);
	}
	
	private Set<Unit> getUnit() {
		return unit;
	}
	/**
	 * @return
	 */
	public boolean isSolid() {
		return (this.getTerrain() instanceof Impassable);
	}
	public boolean isOccupied() {
		return (this.getUnit() != null);
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
     * Set the position of this cube to the given position.
     *
     * @post The position of this cube is equal to the given position.
     *       | new.getposition() == position
     *
     * @param position
     *         The position to set.
     */
    private void setPosition(Position position) {
        this.position = position;
    }
}