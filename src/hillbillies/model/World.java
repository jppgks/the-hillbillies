package hillbillies.model;

import hillbillies.part2.listener.TerrainChangeListener;

import java.util.Collection;

public class World {

	/**
	 * Advance the state of this world by the given time period.
	 * @param dt 
	 * The time period, in seconds, by which to advance the world's state.
	 */
	public void advanceTime(double dt) {
		// TODO - implement World.advanceTime
		throw new UnsupportedOperationException();
	}

	public Collection<Unit> getUnits() {
		return this.units;
	}

	public void setUnits(Collection<Unit> units) {
		this.units = units;
	}

	public Collection<Cube> getCubes() {
		return this.cubes;
	}

	public void setCubes(Collection<Cube> cubes) {
		this.cubes = cubes;
	}

	public Collection<Faction> getFactions() {
		return this.factions;
	}

	public void setFactions(Collection<Faction> factions) {
		this.factions = factions;
	}

	/**
	 * 
	 *   * Create a new world of the given size and with the given terrain. To keep  * the GUI display up to date, the method in the given listener must be  * called whenever the terrain type of a cube in the world changes.  *   * @param terrainTypes  *            A three-dimensional array (structured as [x][y][z]) with the  *            types of the terrain, encoded as integers. The terrain always  *            has the shape of a box (i.e., the array terrainTypes[0] has  *            the same length as terrainTypes[1] etc.). The integer types  *            are as follows:  *            <ul>  *            <li>0: air</li>  *            <li>1: rock</li>  *            <li>2: tree</li>  *            <li>3: workshop</li>  *            </ul>  * @param modelListener  *            An object with a single method,  *            {@link TerrainChangeListener#notifyTerrainChanged(int, int, int)}  *            . This method must be called by your implementation whenever  *            the terrain type of a cube changes (e.g., as a consequence of  *            cave-ins), so that the GUI will correctly update the display.  *            The coordinate of the changed cube must be given in the form  *            of the parameters x, y and z. You do not need to call this  *            method during the construction of your world.  * @return  * @throws ModelException  
	 * @param terrainTypes
	 * @param modelListener
	 */
	public World(int[][][] terrainTypes, TerrainChangeListener modelListener) {
		// TODO - implement World.World
		throw new UnsupportedOperationException();
	}

	/**
	 * Return the number of cubes in the world in the x-direction.
	 *
	 * @return The number of cubes in the x-direction.
	 */
	public int getNbCubesX() {
		// TODO - implement World.getNbCubesX
		throw new UnsupportedOperationException();
	}

	/**
	 * Return the number of cubes in the world in the y-direction.
	 *
	 * @return The number of cubes in the y-direction.
	 */
	public int getNbCubesY() {
		// TODO - implement World.getNbCubesY
		throw new UnsupportedOperationException();
	}

	/**
	 * Return the number of cubes in the world in the z-direction.
	 *
	 * @return The number of cubes in the z-direction.
	 */
	public int getNbCubesZ() {
		// TODO - implement World.getNbCubesZ
		throw new UnsupportedOperationException();
	}

    /**
     * Return the terrain type of the cube at the given coordinates.
     *
     * @param x
     *            The x-coordinate of the cube
     * @param y
     *            The y-coordinate of the cube
     * @param z
     *            The z-coordinate of the cube
     * @return The terrain type of the given cube, encoded as an integer
     *         according to the values in
     *         {@link #createWorld(int[][][], TerrainChangeListener)}.
     */
	public int getCubeType(int x, int y, int z) {
		// TODO - implement World.getCubeType
		throw new UnsupportedOperationException();
	}

    /**
     * Set the terrain type of the cube at the given coordinates the given
     * value.
     *
     * @param x
     *            The x-coordinate of the cube
     * @param y
     *            The y-coordinate of the cube
     * @param z
     *            The z-coordinate of the cube
     * @param value
     *            The new value of the terrain type of the cube, encoded as an
     *            integer according to the values in
     *            {@link #createWorld(int[][][], TerrainChangeListener)}.
     */
	public void setCubeType(int x, int y, int z, int value) {
		// TODO - implement World.setCubeType
		throw new UnsupportedOperationException();
	}

	Collection<Unit> units;
	Collection<Cube> cubes;
	Collection<Faction> factions;

}