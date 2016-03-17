package hillbillies.model;

import java.util.*;

public class World {

	/**
	 * @param terrainTypes
	 */
	public World(int[][][] terrainTypes) {
		this.setDementionGameWorld(new int[]{terrainTypes.length,terrainTypes[0].length,terrainTypes[0][0].length});
		cubesInGameWorld = new ArrayList<>();
		for (int i = 0; i < getDementionGameWorldZ(); i++) {
			for (int j = 0; j < getDementionGameWorldY(); j++) {
				for (int z = 0; z < getDementionGameWorldX(); z++) {
					Cube cube= new Cube(i, j, z, terrainTypes[z][j][i]);
					cubesInGameWorld.add(cube);
				}
			}
		}
	}

	public void advanceTime() {
		// TODO - implement World.advanceTime
		throw new UnsupportedOperationException();
	}

	public Unit getUnits() {
		return this.units;
	}

	/**
	 * 
	 * @param units
	 */
	public void setUnits(Unit units) {
		this.units = units;
	}

	public Cube getCube(int x, int y, int z) {
		return cubesInGameWorld.get(cubeIndexInCubeList(x, y, z));
	}


	public Faction getFactions() {
		return this.factions;
	}

	/**
	 * 
	 * @param factions
	 */
	public void setFactions(Faction factions) {
		this.factions = factions;
	}
	
	public int cubeIndexInCubeList(int x, int y, int z) {

		return x+getDementionGameWorldX()*y+
				(getDementionGameWorldX()*getDementionGameWorldY())*z;
	}
	/**
	 * Set the dementionGameWorld of this World to the given dementionGameWorldX.
	 *
	 * @param  dementionGameWorld
	 *         The dementionGameWorld to set.
	 * @post   The dementionGameWorld of this of this World is equal to the given dementionGameWorld.
	 *       | new.getdementionGameWorldX() == dementionGameWorldx
	 */
	public void setDementionGameWorld(int[] dementionGameWorld) {
		this.dementionGameWorldX = dementionGameWorld[0];
		this.dementionGameWorldY = dementionGameWorld[1];
		this.dementionGameWorldZ = dementionGameWorld[2];
	}
	/**
	 * Return the dementionGameWorldY of this World.
	 */
	public int getDementionGameWorldY() {
		return dementionGameWorldY;
	}
	/**
	 * Return the dementionGameWorldX of this World.
	 */
	public int getDementionGameWorldX() {
		return dementionGameWorldX;
	}
	/**
	 * Return the dementionGameWorldZ of this World.
	 */
	public int getDementionGameWorldZ() {
		return dementionGameWorldZ;
	}
	
	private int dementionGameWorldX;
	private int dementionGameWorldY;
	private int dementionGameWorldZ;
	List<Unit> units;
	public List<Cube> cubesInGameWorld;
	List<Faction> factions;

}