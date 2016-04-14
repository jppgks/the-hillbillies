package hillbillies.part3.facade;

import hillbillies.model.Scheduler;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.model.Boulder;
import hillbillies.model.Faction;
import hillbillies.model.Log;
import hillbillies.part2.listener.TerrainChangeListener;
import hillbillies.part3.programs.ITaskFactory;
import ogp.framework.util.ModelException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by joppegeluykens on 14/04/16.
 */
public class Facade implements IFacade {
    @Override
    public ITaskFactory<?, ?, Task> createTaskFactory() {
        return null;
    }

    @Override
    public boolean isWellFormed(Task task) throws ModelException {
        return false;
    }

    @Override
    public Scheduler getScheduler(Faction faction) throws ModelException {
        return null;
    }

    @Override
    public void schedule(Scheduler scheduler, Task task) throws ModelException {

    }

    @Override
    public void replace(Scheduler scheduler, Task original, Task replacement) throws ModelException {

    }

    @Override
    public boolean areTasksPartOf(Scheduler scheduler, Collection<Task> tasks) throws ModelException {
        return false;
    }

    @Override
    public Iterator<Task> getAllTasksIterator(Scheduler scheduler) throws ModelException {
        return null;
    }

    @Override
    public Set<Scheduler> getSchedulersForTask(Task task) throws ModelException {
        return null;
    }

    @Override
    public Unit getAssignedUnit(Task task) throws ModelException {
        return null;
    }

    @Override
    public Task getAssignedTask(Unit unit) throws ModelException {
        return null;
    }

    @Override
    public String getName(Task task) throws ModelException {
        return null;
    }

    @Override
    public int getPriority(Task task) throws ModelException {
        return 0;
    }

    @Override
    public World createWorld(int[][][] terrainTypes, TerrainChangeListener modelListener) throws ModelException {
        return null;
    }

    @Override
    public int getNbCubesX(World world) throws ModelException {
        return 0;
    }

    @Override
    public int getNbCubesY(World world) throws ModelException {
        return 0;
    }

    @Override
    public int getNbCubesZ(World world) throws ModelException {
        return 0;
    }

    @Override
    public void advanceTime(World world, double dt) throws ModelException {

    }

    @Override
    public int getCubeType(World world, int x, int y, int z) throws ModelException {
        return 0;
    }

    @Override
    public void setCubeType(World world, int x, int y, int z, int value) throws ModelException {

    }

    @Override
    public boolean isSolidConnectedToBorder(World world, int x, int y, int z) throws ModelException {
        return false;
    }

    @Override
    public Unit spawnUnit(World world, boolean enableDefaultBehavior) throws ModelException {
        return null;
    }

    @Override
    public void addUnit(Unit unit, World world) throws ModelException {

    }

    @Override
    public Set<Unit> getUnits(World world) throws ModelException {
        return null;
    }

    @Override
    public boolean isCarryingLog(Unit unit) throws ModelException {
        return false;
    }

    @Override
    public boolean isCarryingBoulder(Unit unit) throws ModelException {
        return false;
    }

    @Override
    public boolean isAlive(Unit unit) throws ModelException {
        return false;
    }

    @Override
    public int getExperiencePoints(Unit unit) throws ModelException {
        return 0;
    }

    @Override
    public void workAt(Unit unit, int x, int y, int z) throws ModelException {

    }

    @Override
    public Faction getFaction(Unit unit) throws ModelException {
        return null;
    }

    @Override
    public Set<Unit> getUnitsOfFaction(Faction faction) throws ModelException {
        return null;
    }

    @Override
    public Set<Faction> getActiveFactions(World world) throws ModelException {
        return null;
    }

    @Override
    public double[] getPosition(Boulder boulder) throws ModelException {
        return new double[0];
    }

    @Override
    public Set<Boulder> getBoulders(World world) throws ModelException {
        return null;
    }

    @Override
    public double[] getPosition(Log log) throws ModelException {
        return new double[0];
    }

    @Override
    public Set<Log> getLogs(World world) throws ModelException {
        return null;
    }

    @Override
    public Unit createUnit(String name, int[] initialPosition, int weight, int agility, int strength, int toughness, boolean enableDefaultBehavior) throws ModelException {
        return null;
    }

    @Override
    public double[] getPosition(Unit unit) throws ModelException {
        return new double[0];
    }

    @Override
    public int[] getCubeCoordinate(Unit unit) throws ModelException {
        return new int[0];
    }

    @Override
    public String getName(Unit unit) throws ModelException {
        return null;
    }

    @Override
    public void setName(Unit unit, String newName) throws ModelException {

    }

    @Override
    public int getWeight(Unit unit) throws ModelException {
        return 0;
    }

    @Override
    public void setWeight(Unit unit, int newValue) throws ModelException {

    }

    @Override
    public int getStrength(Unit unit) throws ModelException {
        return 0;
    }

    @Override
    public void setStrength(Unit unit, int newValue) throws ModelException {

    }

    @Override
    public int getAgility(Unit unit) throws ModelException {
        return 0;
    }

    @Override
    public void setAgility(Unit unit, int newValue) throws ModelException {

    }

    @Override
    public int getToughness(Unit unit) throws ModelException {
        return 0;
    }

    @Override
    public void setToughness(Unit unit, int newValue) throws ModelException {

    }

    @Override
    public int getMaxHitPoints(Unit unit) throws ModelException {
        return 0;
    }

    @Override
    public int getCurrentHitPoints(Unit unit) throws ModelException {
        return 0;
    }

    @Override
    public int getMaxStaminaPoints(Unit unit) throws ModelException {
        return 0;
    }

    @Override
    public int getCurrentStaminaPoints(Unit unit) throws ModelException {
        return 0;
    }

    @Override
    public void moveToAdjacent(Unit unit, int dx, int dy, int dz) throws ModelException {

    }

    @Override
    public double getCurrentSpeed(Unit unit) throws ModelException {
        return 0;
    }

    @Override
    public boolean isMoving(Unit unit) throws ModelException {
        return false;
    }

    @Override
    public void startSprinting(Unit unit) throws ModelException {

    }

    @Override
    public void stopSprinting(Unit unit) throws ModelException {

    }

    @Override
    public boolean isSprinting(Unit unit) throws ModelException {
        return false;
    }

    @Override
    public double getOrientation(Unit unit) throws ModelException {
        return 0;
    }

    @Override
    public void moveTo(Unit unit, int[] cube) throws ModelException {

    }

    @Override
    public boolean isWorking(Unit unit) throws ModelException {
        return false;
    }

    @Override
    public void fight(Unit attacker, Unit defender) throws ModelException {

    }

    @Override
    public boolean isAttacking(Unit unit) throws ModelException {
        return false;
    }

    @Override
    public void rest(Unit unit) throws ModelException {

    }

    @Override
    public boolean isResting(Unit unit) throws ModelException {
        return false;
    }

    @Override
    public void setDefaultBehaviorEnabled(Unit unit, boolean value) throws ModelException {

    }

    @Override
    public boolean isDefaultBehaviorEnabled(Unit unit) throws ModelException {
        return false;
    }
}
