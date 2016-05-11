package hillbillies.model;

import java.util.*;

public class Faction extends GameObject {

	private Scheduler scheduler;

	/**
	 * Creates a new faction object and initializes it with the given name.
     *
     * @post      The name of this new faction is equal to the given name.
     *
	 * @param name
     *            The name to initialize this faction with.
	 */
	public Faction(String name) {
		this.setName(name);
	}

    /**
     * Variable registering this faction's name.
     */
    private String name;

    /**
     * Variable registering the members of this faction.
     */
    Set<Unit> members = new HashSet<>();

	/**
	 * Returns the name of this Faction.
     *
     * @return The name of this faction.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of this Faction to the given name.
	 *
     * @post   The name of this faction is equal to the given name.
     *       | new.getName() == name
     *
     * @param name
	 *         The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

    /**
     * Add the given unit to the set of members of this faction.
     *
     * @param unit
     *         The unit to add to the set of members.
     */
	public void addMember(Unit unit) {
		unit.setFaction(this);
		members.add(unit);
	}

	/**
	 * Returns the members of this faction.
     *
     * @return The set of members of this faction.
	 */
	public Set<Unit> getMembers() {
		return members;
	}

	public Scheduler getScheduler() {
		return scheduler;
	}
}