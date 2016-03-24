package hillbillies.model.gameobject;

import hillbillies.model.Unit;

import java.util.*;

public class Faction extends GameObject {

	/**
	 * @param string
	 */
	public Faction(String name) {
		this.setFactionName(name);
	}

	/**
	 * Return the factionName of this Faction.
	 */
	public String getFactionName() {
		return factionName;
	}

	/**
	 * Set the factionName of this Faction to the given factionName.
	 *
	 * @param  factionName
	 *         The factionName to set.
	 * @post   The factionName of this of this Faction is equal to the given factionName.
	 *       | new.getfactionName() == factionName
	 */
	public void setFactionName(String factionName) {
		this.factionName = factionName;
	}
	
	public void addMember(Unit unit) {
		unit.setFaction(this);
		members.add(unit);
	}

	Set<Unit> members = new HashSet<>();
	
	/**
	 * Return the members of this Faction.
	 */
	public Set<Unit> getMembers() {
		return members;
	}

	private String factionName;
	

}