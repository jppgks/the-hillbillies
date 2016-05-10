package hillbillies.part2.facade;

import hillbillies.model.*;
import hillbillies.model.Boulder;
import hillbillies.model.Faction;
import hillbillies.model.Log;
import hillbillies.part2.listener.TerrainChangeListener;
import ogp.framework.util.ModelException;

import java.util.Set;

public class Facade extends hillbillies.part1.facade.Facade implements IFacade {

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


}
