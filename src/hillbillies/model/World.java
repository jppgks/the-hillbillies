package hillbillies.model;

import hillbillies.part2.listener.TerrainChangeListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class World {
    /**
     * Create a new world of the given size and with the given terrain. To keep
     * the GUI display up to date, the method in the given listener must be
     * called whenever the terrain type of a cube in the world changes.
     * @param terrainTypes
     *              A three-dimensional array (structured as [x][y][z]) with the
     *              types of the terrain, encoded as integers. The terrain always
     *              has the shape of a box (i.e., the array terrainTypes[0] has
     *              the same length as terrainTypes[1] etc.). The integer types
     *              are as follows:
     *              <ul>
     *                  <li>0: air</li>
     *                  <li>1: rock</li>
     *                  <li>2: tree</li>
     *                  <li>3: workshop</li>
     *              </ul>
     * @param modelListener
     *              An object with a single method,
     *              {@link TerrainChangeListener#notifyTerrainChanged(int, int, int)}
     *              . This method must be called by your implementation whenever
     *              the terrain type of a cube changes (e.g., as a consequence of
     *              cave-ins), so that the GUI will correctly update the display.
     *              The coordinate of the changed cube must be given in the form
     *              of the parameters x, y and z. You do not need to call this
     *              method during the construction of your world.
     */
    public World(int[][][] terrainTypes, TerrainChangeListener modelListener) {
		this.setDimensionGameWorld(new int[]{terrainTypes.length,terrainTypes[0].length,terrainTypes[0][0].length});
		cubes = new ArrayList<>();
		for (int i = 0; i < getDimensionGameWorldZ(); i++) {
			for (int j = 0; j < getDimensionGameWorldY(); j++) {
				for (int z = 0; z < getDimensionGameWorldX(); z++) {
					Cube cube= new Cube(i, j, z, terrainTypes[z][j][i]);
					cubes.add(cube);
				}
			}
		}
	}

    /**
     * Advance the state of this world by the given time period.
     * @param dt
     * The time period, in seconds, by which to advance the world's state.
     */
    public void advanceTime(double dt) {
		// TODO - implement World.advanceTime
		throw new UnsupportedOperationException();
	}

    /**
     * Return all units that are currently part of the world.
     *
     * @return A set containing all units from the world.
     */
	public Set<Unit> getUnits() {
		return this.units;
	}

	public void setUnits(Set<Unit> units) {
		this.units = units;
	}

	public Collection<Cube> getCubes() {
		return this.cubes;
	}

	public void setCubes(List<Cube> cubes) {
		this.cubes = cubes;
	}

	public Cube getCube(int x, int y, int z) {
		return cubes.get(cubeIndexInCubeList(x, y, z));
	}

    /**
     * Return all the active factions of the world.
     *
     * @return A set of all active (i.e., non-empty) factions in the world.
     */
	public Collection<Faction> getActiveFactions() {
		return this.factions;
	}

	public void setActiveFactions(List<Faction> factions) {
		this.factions = factions;
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
     *         according to the values in IFacade.
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
     *            integer according to the values in IFacade.
     */
	public void setCubeType(int x, int y, int z, int value) {
		// TODO - implement World.setCubeType
		throw new UnsupportedOperationException();
	}

    /**
     * Return whether the cube at the given coordinates is solid and connected
     * to the border of the world.
     *
     * @param x
     *            The x-coordinate of the cube
     * @param y
     *            The y-coordinate of the cube
     * @param z
     *            The z-coordinate of the cube
     * @return true if the given cube is solid and connected to the border of
     *         the world; false otherwise.
     *
     */
	public boolean isSolidConnectedToBorder(int x, int y, int z) {
		// TODO - implement World.isSolidConnectedToBorder
		throw new UnsupportedOperationException();
	}

    /**
     * Spawn a new unit in the world, according to the rules in the assignment
     * (section 1.1.2).
     *
     * @param enableDefaultBehavior
     *            Whether the unit should act according to the default behaviour
     *            or not.
     * @return The newly spawned unit.
     */
	public Unit spawnUnit(boolean enableDefaultBehavior) {
		// TODO - implement World.spawnUnit
		throw new UnsupportedOperationException();
	}
	
	public int cubeIndexInCubeList(int x, int y, int z) {
        return x+getDimensionGameWorldX()*y+
                (getDimensionGameWorldX()*getDimensionGameWorldY())*z;
    }

    /**
     * Adds the given unit to the world.
     *
     * @param unit
     */
	public void addUnit(Unit unit) {
		// TODO - implement World.addUnit
		throw new UnsupportedOperationException();
	}


    /**
     * Return all boulders that are part of the world.
     *
     * @return A set containing all boulders present in the given world (i.e.,
     *         not picked up, consumed, destroyed, ...).
     */
    public Set<Boulder> getBoulders(World world) {
		return this.boulders;
	}

	public void setBoulders(Set<Boulder> boulders) {
		this.boulders = boulders;
	}

    /**
     * Return all logs that are part of the given world.
     *
     * @return A set containing all logs present in the given world (i.e., not
     *         picked up, consumed, destroyed, ...).
     */
	public Set<Log> getLogs() {
		return this.logs;
	}

	public void setLogs(Set<Log> logs) {
		this.logs = logs;
	}

	/**
	 * Set the dementionGameWorld of this World to the given dementionGameWorldX.
	 * @post The dementionGameWorld of this of this World is equal to the given dementionGameWorld. | new.getdementionGameWorldX() == dementionGameWorldx
	 * @param dementionGameWorld The dementionGameWorld to set.
	 */
	public void setDimensionGameWorld(int[] dementionGameWorld) {
		this.dimensionGameWorldX = dementionGameWorld[0];
		this.dimensionGameWorldY = dementionGameWorld[1];
		this.dimensionGameWorldZ = dementionGameWorld[2];
	}
	/**
	 * Return the dementionGameWorldY of this World.
	 */
	public int getDimensionGameWorldY() {
		return dimensionGameWorldY;
	}
	/**
	 * Return the dementionGameWorldX of this World.
	 */
	public int getDimensionGameWorldX() {
		return dimensionGameWorldX;
	}
	/**
	 * Return the dementionGameWorldZ of this World.
	 */
	public int getDimensionGameWorldZ() {
		return dimensionGameWorldZ;
	}
	
	private int dimensionGameWorldX;
	private int dimensionGameWorldY;
	private int dimensionGameWorldZ;
	Set<Unit> units;
	public List<Cube> cubes;
	List<Faction> factions;
    private Set<Boulder> boulders;
    private Set<Log> logs;
}