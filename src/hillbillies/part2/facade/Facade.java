package hillbillies.part2.facade;

import hillbillies.model.*;
import hillbillies.model.Boulder;
import hillbillies.model.Faction;
import hillbillies.model.Log;
import hillbillies.part2.listener.TerrainChangeListener;
import ogp.framework.util.ModelException;

import java.util.Set;

public class Facade implements IFacade {
    public Facade() {

    }

    @Override
    public World createWorld(int[][][] terrainTypes, TerrainChangeListener modelListener) throws ModelException {
        return new World(terrainTypes, modelListener);
    }

    @Override
    public int getNbCubesX(World world) throws ModelException {
        return world.getNbCubesX();
    }

    @Override
    public int getNbCubesY(World world) throws ModelException {
        return world.getNbCubesY();
    }

    @Override
    public int getNbCubesZ(World world) throws ModelException {
        return world.getNbCubesZ();
    }

    @Override
    public void advanceTime(World world, double dt) throws ModelException {
        world.advanceTime(dt);
    }

    @Override
    public int getCubeType(World world, int x, int y, int z) throws ModelException {
        return world.getCubeType(x, y, z);
    }

    @Override
    public void setCubeType(World world, int x, int y, int z, int value) throws ModelException {
    	world.setCubeType(x, y, z, value);
    }

    @Override
    public boolean isSolidConnectedToBorder(World world, int x, int y, int z) throws ModelException {
        return world.isSolidConnectedToBorder(x, y, z);
    }

    @Override
    public Unit spawnUnit(World world, boolean enableDefaultBehavior) throws ModelException {
		if (world.getUnits().size() > 100) {
			throw new ModelException("To Many Units in this world");
		}
    		return world.spawnUnit(enableDefaultBehavior);
    }

    @Override
    public void addUnit(Unit unit, World world) throws ModelException {
    	world.addAsUnit(unit);
    }

    @Override
    public Set<Unit> getUnits(World world) throws ModelException {
        return world.getUnits();
    }

    @Override
    public boolean isCarryingLog(Unit unit) throws ModelException {
        return unit.isCarryingLog();
    }

    @Override
    public boolean isCarryingBoulder(Unit unit) throws ModelException {
        return unit.isCarryingBoulder();
    }

    @Override
    public boolean isAlive(Unit unit) throws ModelException {
        return unit.isAlive();
    }

    @Override
    public int getExperiencePoints(Unit unit) throws ModelException {
        return unit.getCurrentExperiencePoints();
    }

    @Override
    public void workAt(Unit unit, int x, int y, int z) throws ModelException {
    	if(unit.getState()!= State.NONE)
    		throw new ModelException("can't work right now");
    	try {
    		unit.work(new Position(new int[]{x,y,z}));
		} catch (IllegalArgumentException exc) {
			throw new ModelException("can't work right now");
		} catch (IllegalStateException exc) {
			throw new ModelException("can't work right now");
		}       
    }

    @Override
    public Faction getFaction(Unit unit) throws ModelException {
        return unit.getFaction();
    }

    @Override
    public Set<Unit> getUnitsOfFaction(Faction faction) throws ModelException {
        return faction.getMembers();
    }

    @Override
    public Set<Faction> getActiveFactions(World world) throws ModelException {
        return world.getActiveFactions();
    }

    @Override
    public double[] getPosition(Boulder boulder) throws ModelException {
        return boulder.getPosition().getDoubleCoordinates();
    }

    @Override
    public Set<Boulder> getBoulders(World world) throws ModelException {
        return world.getBoulders();
    }

    @Override
    public double[] getPosition(Log log) throws ModelException {
        return log.getPosition().getDoubleCoordinates();
    }

    @Override
    public Set<Log> getLogs(World world) throws ModelException {
        return world.getLogs();
    }

    @Override
    public Unit createUnit(String name, int[] initialPosition, int weight, int agility, int strength, int toughness,
                           boolean enableDefaultBehavior) throws ModelException {
        return new Unit(name, initialPosition, weight, agility, strength, toughness, enableDefaultBehavior);
    }

    @Override
    public double[] getPosition(Unit unit) throws ModelException {
        return unit.getPosition().getDoubleCoordinates();
    }

    @Override
    public int[] getCubeCoordinate(Unit unit) throws ModelException {
        return unit.getPosition().getCubeCoordinates();
    }

    @Override
    public String getName(Unit unit) throws ModelException {
        return unit.getName();
    }

    @Override
    public void setName(Unit unit, String newName) throws ModelException {
        try {
            unit.setName(newName);
        } catch (IllegalArgumentException exc) {
//			unit.setName("Billy the Hill");
            throw new ModelException();
        }

    }

    @Override
    public int getWeight(Unit unit) throws ModelException {
        return (int) unit.getWeight();
    }

    @Override
    public void setWeight(Unit unit, int newValue) throws ModelException {
        unit.setWeight(newValue);
    }

    @Override
    public int getStrength(Unit unit) throws ModelException {
        return (int) unit.getStrength();
    }

    @Override
    public void setStrength(Unit unit, int newValue) throws ModelException {
        unit.setStrength(newValue);
    }

    @Override
    public int getAgility(Unit unit) throws ModelException {
        return (int) unit.getAgility();
    }

    @Override
    public void setAgility(Unit unit, int newValue) throws ModelException {
        unit.setAgility(newValue);
    }

    @Override
    public int getToughness(Unit unit) throws ModelException {
        return (int) unit.getToughness();
    }

    @Override
    public void setToughness(Unit unit, int newValue) throws ModelException {
        unit.setToughness(newValue);
    }

    @Override
    public int getMaxHitPoints(Unit unit) throws ModelException {
        return (int) unit.getMaxHitPoints();
    }

    @Override
    public int getCurrentHitPoints(Unit unit) throws ModelException {
        return (int) unit.getCurrentHitPoints();
    }

    @Override
    public int getMaxStaminaPoints(Unit unit) throws ModelException {
        return (int) unit.getMaxStaminaPoints();
    }

    @Override
    public int getCurrentStaminaPoints(Unit unit) throws ModelException {
        return (int) unit.getCurrentStaminaPoints();
    }

    @Override
    public void moveToAdjacent(Unit unit, int dx, int dy, int dz) throws ModelException {
        try {
            unit.moveToAdjacent(dx, dy, dz);
        } catch (IllegalArgumentException exc) {

        }
    }

    @Override
    public double getCurrentSpeed(Unit unit) throws ModelException {
        return unit.getCurrentSpeed();
    }

    @Override
    public boolean isMoving(Unit unit) throws ModelException {
        return unit.getState() == State.MOVING;
    }

    @Override
    public void startSprinting(Unit unit) throws ModelException {
        unit.startSprinting();
    }

    @Override
    public void stopSprinting(Unit unit) throws ModelException {
        unit.stopSprinting();
    }

    @Override
    public boolean isSprinting(Unit unit) throws ModelException {
        return unit.isSprinting();
    }

    @Override
    public double getOrientation(Unit unit) throws ModelException {
        return unit.getOrientation();
    }

    @Override
    public void moveTo(Unit unit, int[] cube) throws ModelException {
        try {
            unit.moveTo(cube);
        } catch (IllegalArgumentException exc) {

        }
    }

    @Override
    public boolean isWorking(Unit unit) throws ModelException {
        return unit.getState() == State.WORKING;
    }

    @Override
    public void fight(Unit attacker, Unit defender) throws ModelException {
    	if(attacker.getState() != State.NONE)
    		throw new ModelException("I can't do that now");
        try {
            attacker.attack(defender);
        } catch (IllegalStateException exc) {

        } catch (IllegalArgumentException exc) {

        }
    }

    @Override
    public boolean isAttacking(Unit unit) throws ModelException {
        return unit.getState() == State.ATTACKING;
    }

    @Override
    public void rest(Unit unit) throws ModelException {
    	if(unit.getCurrentHitPoints() == unit.getMaxHitPoints() && unit.getCurrentStaminaPoints() == unit.getMaxStaminaPoints())
    		throw new ModelException("I'm already full recoverd");
    	try {
            unit.rest();
        } catch (IllegalStateException exc) {

        }

    }

    @Override
    public boolean isResting(Unit unit) throws ModelException {
        return unit.getState() == State.RESTING;
    }

    @Override
    public void setDefaultBehaviorEnabled(Unit unit, boolean value) throws ModelException {
        unit.setDefaultBehaviorEnabled(value);
    }

    @Override
    public boolean isDefaultBehaviorEnabled(Unit unit) throws ModelException {
        return unit.getDefaultBehaviorEnabled();
    }
}
