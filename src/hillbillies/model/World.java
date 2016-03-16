package hillbillies.model;

import java.util.*;

public class World {

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

	public Cube getCubes() {
		return this.cubes;
	}

	/**
	 * 
	 * @param cubes
	 */
	public void setCubes(Cube cubes) {
		this.cubes = cubes;
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

	Collection<Unit> units;
	Collection<Cube> cubes;
	Collection<Faction> factions;

}