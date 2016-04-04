package hillbillies.model;

import hillbillies.model.gameobject.Boulder;
import hillbillies.model.gameobject.Faction;
import hillbillies.model.gameobject.Log;
import hillbillies.model.terrain.*;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.util.ConnectedToBorder;

import java.util.*;

public class World {

    /**
     * Create a new world of the given size and with the given terrain. To keep
     * the GUI display up to date, the method in the given listener must be
     * called whenever the terrain type of a cube in the world changes.
     *
     * @param terrainTypes  A three-dimensional array (structured as [x][y][z]) with the
     *                      types of the terrain, encoded as integers. The terrain always
     *                      has the shape of a box (i.e., the array terrainTypes[0] has
     *                      the same length as terrainTypes[1] etc.). The integer types
     *                      are as follows:
     *                      <ul>
     *                      <li>0: air</li>
     *                      <li>1: rock</li>
     *                      <li>2: tree</li>
     *                      <li>3: workshop</li>
     *                      </ul>
     * @param modelListener An object with a single method,
     *                      {@link TerrainChangeListener#notifyTerrainChanged(int, int, int)}
     *                      . This method must be called by your implementation whenever
     *                      the terrain type of a cube changes (e.g., as a consequence of
     *                      cave-ins), so that the GUI will correctly update the display.
     *                      The coordinate of the changed cube must be given in the form
     *                      of the parameters x, y and z. You do not need to call this
     *                      method during the construction of your world.
     */
    public World(int[][][] terrainTypes, TerrainChangeListener modelListener) {
        this.setNbCubes(new int[]{terrainTypes.length, terrainTypes[0].length, terrainTypes[0][0].length});
        cubesInWorld = new Cube[terrainTypes.length][terrainTypes[0].length][terrainTypes[0][0].length];
        this.factions.add(faction1);
        this.factions.add(faction2);
        this.factions.add(faction3);
        this.factions.add(faction4);
        this.factions.add(faction5);
        connectedToBorder = new ConnectedToBorder(this.getNbCubesX(), this.getNbCubesY(), this.getNbCubesZ());
        for (int x = 0; x < terrainTypes.length; x++) {
            for (int y = 0; y < terrainTypes[0].length; y++) {
                for (int z = 0; z < terrainTypes[0][0].length; z++) {
                    Cube cube = new Cube(x, y, z, terrainTypes[x][y][z], this);
                    cubesInWorld[x][y][z]= cube;
                    cubes.add(cube);
                }
            }
        }
        this.calculateConnectedToBorder();
        this.setTerrainChangeListener(modelListener);
    }

    /**
     * Variable registering the size of the X-dimension of this world.
     */
    private int sizeX;

    /**
     * Variable registering the size of the Y-dimension of this world.
     */
    private int sizeY;

    /**
     * Variable registering the size of the Z-dimension of this world.
     */
    private int sizeZ;

    /**
     * Variable registering the cubes present in this world.
     */
    private List<Cube> cubes = new ArrayList<>();

    /**
     * Variable registering the units present in this world.
     */
    private Set<Unit> units = new HashSet<>();

    /**
     * Variable registering the factions present in this world.
     */
    private Set<Faction> factions = new HashSet<>();

    /**
     * Variable registering the boulders present in this world.
     */
    private Set<Boulder> boulders = new HashSet<>();

    /**
     * Variable registering the logs present in this world.
     */
    private Set<Log> logs = new HashSet<>();

    private Faction faction1 = new Faction("team 1");
    private Faction faction2 = new Faction("team 2");
    private Faction faction3 = new Faction("team 3");
    private Faction faction4 = new Faction("team 4");
    private Faction faction5 = new Faction("team 5");
    
    private Cube[][][] cubesInWorld;

    /**
     * Variable registering the ConnectedToBorder object for the cubes in this world.
     */
    ConnectedToBorder connectedToBorder;

    /**
     * Variable registering the TerrainChangeListener for this world.
     */
    private TerrainChangeListener terrainChangeListener;

    /**
     * Set the nbCubes of this world to the given nbCubes.
     *
     * @post      The size of this of this World is equal to the given size.
     *
     * @param     nbCubes
     *            The nbCubes to set for x, y and z.
     */
    private void setNbCubes(int[] nbCubes) {
        this.sizeX = nbCubes[0];
        this.sizeY = nbCubes[1];
        this.sizeZ = nbCubes[2];
    }

    /**
     * Return the number of cubes in the x-direction of this world.
     */
    public int getNbCubesX() {
        return sizeX;
    }

    /**
     * Return the number of cubes in the y-direction of this world.
     */
    public int getNbCubesY() {
        return sizeY;
    }

    /**
     * Return the number of cubes in the z-direction of this world.
     */
    public int getNbCubesZ() {
        return sizeZ;
    }

    /**
     * Return all the active factions of the world.
     *
     * @return A set of all active (i.e., non-empty) factions in the world.
     */
    public Set<Faction> getActiveFactions() {
        return this.factions;
    }

    /**
     * Returns all cubes in this world.
     *
     * @return A collection of all cubes in this world.
     */
    private Collection<Cube> getCubes() {
        return this.cubes;
    }

    /**
     * Returns the cube at the given coordinates.
     *
     * @param     x
     *            The X-coordinate of the cube.
     * @param     y
     *            The Y-coordinate of the cube.
     * @param     z
     *            The Z-coordinate of the cube.
     *
     * @return The cube at the given position.
     */
    public Cube getCube(int x, int y, int z) {
    	return cubesInWorld[x][y][z];
        //return cubes.get(cubeIndexInCubeList(x, y, z));
    }

    /**
     * Set the terrain type of the cube at the given coordinates the given
     * value.
     *
     * @param     x
     *            The x-coordinate of the cube
     * @param     y
     *            The y-coordinate of the cube
     * @param     z
     *            The z-coordinate of the cube
     * @param     value
     *            The new value of the terrain type of the cube, encoded as an
     *            integer according to the values in IFacade.
     */
    public void setCubeType(int x, int y, int z, int value) {
        this.getCube(x, y, z).setTerrain(value, false);
    }

    /**
     * Return the terrain type of the cube at the given coordinates.
     *
     * @param     x
     *            The x-coordinate of the cube
     * @param     y
     *            The y-coordinate of the cube
     * @param     z
     *            The z-coordinate of the cube
     *
     * @return The terrain type of the given cube, encoded as an integer
     *         according to the values in IFacade.
     */
    public int getCubeType(int x, int y, int z) {
        Terrain terrain = this.getCube(x, y, z).getTerrain();
        if (terrain instanceof Air) {
            return 0;
        }
        if (terrain instanceof Rock) {
            return 1;
        }
        if (terrain instanceof Tree) {
            return 2;
        }
        if (terrain instanceof Workshop) {
            return 3;
        }
        return -1;
    }

    /**
     * Returns the index in the list of cubes for the cube at the given coordinates.
     *
     * @param     x
     *            The X-coordinate of the cube.
     * @param     y
     *            The Y-coordinate of the cube.
     * @param     z
     *            The Z-coordinate of the cube.
     * @return The index in the list of cubes for the cube at the given coordinates.
     */
    private int cubeIndexInCubeList(int x, int y, int z) {
        return x+ getNbCubesX()*y+
                (getNbCubesX()* getNbCubesY())*z;
    }

    /**
     * (Re)calculates the connectedToBorder object for the cubes in this world.
     *
     * @post      The cubes that get disconnected cave in.
     */
    void calculateConnectedToBorder() {
        List<int[]> newlyDisconnectedCubes = new ArrayList<>();
        for (int i = 0; i < this.getNbCubesX(); i++) {
            for (int j = 0; j < this.getNbCubesY(); j++) {
                for (int k = 0; k < this.getNbCubesZ(); k++) {
                    if (this.getCube(i, j, k).getTerrain() instanceof Passable) {
                        newlyDisconnectedCubes.addAll(connectedToBorder.changeSolidToPassable(i,j,k));
                    } else {
                        connectedToBorder.changePassableToSolid(i,j,k);
                    }
                }
            }
        }
        for (int[] cubeCoordinate :
                newlyDisconnectedCubes) {
            this.getCube(cubeCoordinate[0], cubeCoordinate[1], cubeCoordinate[2]).caveIn();
        }
    }

    /**
     * Return whether the cube at the given coordinates is solid and connected
     * to the border of the world.
     *
     * @param     x
     *            The x-coordinate of the cube
     * @param     y
     *            The y-coordinate of the cube
     * @param     z
     *            The z-coordinate of the cube
     * @return true if the given cube is solid and connected to the border of
     *         the world; false otherwise.
     *
     */
    public boolean isSolidConnectedToBorder(int x, int y, int z) {
        return this.connectedToBorder.isSolidConnectedToBorder(x, y, z);
    }

    /**
     * Set the TerrainChangeListener of this world to the given TerrainChangeListener.
     *
     * @param     terrainChangeListener
     *            The TerrainChangeListener to set.
     */
    private void setTerrainChangeListener(TerrainChangeListener terrainChangeListener) {
        this.terrainChangeListener = terrainChangeListener;
    }

    /**
     * Notifies the TerrainChangeListener of this world when the terrain of a cube in this world changed.
     *
     * @param     x
     *            The X-coordinate of the changed cube.
     * @param     y
     *            The Y-coordinate of the changed cube.
     * @param     z
     *            The Z-coordinate of the changed cube.
     */
    void notifyTerrainChangeListener(int x, int y, int z) {
        this.terrainChangeListener.notifyTerrainChanged(x, y, z);
    }

    /**
     * Return all units that are currently part of the world.
     *
     * @return A collection containing all units from the world.
     */
    public Set<Unit> getUnits() {
		return this.units;
	}

	/**
	 * Returns whether or not the given unit can be added to the collection of units of this world.
	 *
	 * @param 	  unit
	 * 			  The unit to be added.
	 * @return true if the unit can be added; false otherwise.
     */
    private boolean canHaveAsUnit(Unit unit) {
        return this.getUnits().size() < 100;
    }

    /**
     * Spawn a new unit in the world, according to the rules in the assignment
     * (section 1.1.2).
     *
     * @param     enableDefaultBehavior
     *            Whether the unit should act according to the default behaviour
     *            or not.
     *
     * @return The newly spawned unit.
     */
    public Unit spawnUnit(boolean enableDefaultBehavior){
		int cubeX = new Random().nextInt(this.getNbCubesX());
		int cubeY = new Random().nextInt(this.getNbCubesY());
		int cubeZ = new Random().nextInt(this.getNbCubesZ());
		while(! validSpawnCoordinates(cubeX, cubeY, cubeZ)) {
			cubeX = new Random().nextInt(this.getNbCubesX());
			cubeY = new Random().nextInt(this.getNbCubesY());
			cubeZ = new Random().nextInt(this.getNbCubesZ());
		}
		Unit unit = new Unit("Hilly", new int[]{cubeX,cubeY,cubeZ},50, 50, 50, 50, false);
		this.getCube(cubeX, cubeY, cubeZ).setUnit(unit);
		unit.world = this;
		this.addUnit(unit);
		addUnitToFaction(unit);
		return unit;
	}
	
	/**
     * Returns whether or not the cube at the given coordinates is a valid cube to spawn on.
     *
     * @return true when the given cube is passable, has solid neighboring cubes and is not occupied; false otherwise.
	 */
	private boolean validSpawnCoordinates(int x, int y, int z) {
		if((!this.getCube(x, y, z).isSolid()) && (this.getCube(x, y, z).hasSolidNeighboringCubes()) && !(this.getCube(x, y, z).isOccupied()))
			{
				return true;
			}
		return false;
	}

	/**
     * Adds the given unit to a random faction that is part of this world.
     *
	 * @param     unit
     *            The unit to add to a faction.
	 */
	private void addUnitToFaction(Unit unit){
		Random rd = new Random();
		int factionNumer = rd.nextInt(4);
		switch (factionNumer) {
		case 0:
			faction1.addMember(unit);
			break;
		case 1:
			faction2.addMember(unit);
			break;
		case 2:
			faction3.addMember(unit);
			break;
		case 3:
			faction4.addMember(unit);
			break;
		case 4:
			faction5.addMember(unit);
			break;	
		default:
			break;
		}
		
	}

    /**
     * Adds the given unit to the collection of units in this world.
     *
     * @param     unit
     *            The unit to add to the collection of units in this world.
     */
    public void addUnit(Unit unit) {
		this.units.add(unit);
	}


    /**
     * Return all boulders that are part of the world.
     *
     * @return A set containing all boulders present in the given world (i.e.,
     *         not picked up, consumed, destroyed, ...).
     */
    public Set<Boulder> getBoulders() {
		return this.boulders;
	}

    /**
     * Adds the given boulder to the collection of boulders in this world.
     *
     * @param     boulder
     *            The boulder to add to the collection of boulders in this world.
     */
    void addBoulder(Boulder boulder) {
		this.boulders.add(boulder);
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

    /**
     * Adds the given log to the collection of logs in this world.
     *
     * @param     log
     *            The log to add to the collection of logs in this world.
     */
    void addLog(Log log) {
		this.logs.add(log);
	}

    /**
     * Advance the state of this world by the given time period.
     *
     * @param 	  dt
     * 			  The time period, in seconds, by which to advance the world's state.
     */
    public void advanceTime(double dt) {
        for (Cube cube :
                this.getCubes()) {
            cube.advanceTime(dt);
        }
        for (Unit unit :
                this.getUnits()) {
            unit.advanceTime(dt);
        }
        for (Log log :
                this.getLogs()) {
            log.advanceTime(dt);
        }
        for (Boulder boulder :
                this.getBoulders()) {
            boulder.advanceTime(dt);
        }
    }
}