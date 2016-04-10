package hillbillies.model;

import hillbillies.model.gameobject.Boulder;
import hillbillies.model.gameobject.Faction;
import hillbillies.model.gameobject.Log;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static hillbillies.model.tests.util.PositionAsserts.assertDoublePositionEquals;
import static hillbillies.model.tests.util.PositionAsserts.assertIntegerPositionEquals;
import static org.junit.Assert.*;

/**
 * A class of unit tests.
 *
 * @author  Iwein Bau & Joppe Geluykens
 * @version 1.0
 */
public class UnitTest {
	
	private Unit unit;
	private Class unitClass;
	private Method method;
    private World world;

	@Before
	public void setUp() {
		this.unit = new Unit("TestUnit", new int[] { 1, 2, 3 }, 50, 50, 50, 50, false);
		this.unitClass = Unit.class;
        int[][][] types = new int[4][4][4];
        types[1][1][0] = 1;
        types[1][1][1] = 2;
        types[1][2][3] = 3;
        this.world = new World(types, new DefaultTerrainChangeListener());
        this.unit.setWorld(world);
	}

	@Test
	public void CreateUnit_LegalCase() {
		assertEquals("TestUnit", this.unit.getName());
		double halfCubeSideLength = this.unit.getPosition().getCubeSideLength() / 2;
		assertDoublePositionEquals(
				1 + halfCubeSideLength, 2 + halfCubeSideLength, 3 +halfCubeSideLength,
				this.unit.getPosition().getDoubleCoordinates()
		);

		assertEquals(50, this.unit.getWeight(), .01);
		assertEquals(50, this.unit.getAgility(), .01);
		assertEquals(50, this.unit.getStrength(), .01);
		assertEquals(50, this.unit.getToughness(), .01);
		assertFalse(this.unit.getDefaultBehaviorEnabled());

		assertEquals(this.unit.getMaxHitPoints(),this.unit.getCurrentHitPoints(), .01);
		assertEquals(this.unit.getMaxStaminaPoints(), this.unit.getCurrentStaminaPoints(), .01);
	}

	@Test
	public void testGetCubeCoordinates() {
		assertIntegerPositionEquals(1, 2, 3, this.unit.getPosition().getCubeCoordinates());
	}

	@Test
	public void testMoveToAdjacent() {
		double initialX = this.unit.getPosition().getDoubleCoordinates()[0];
		double initialY = this.unit.getPosition().getDoubleCoordinates()[1];
		double initialZ = this.unit.getPosition().getDoubleCoordinates()[2];

		this.unit.moveToAdjacent(0, -1, 0);
		this.unit.advanceTime(1.34);

		assertDoublePositionEquals(
				initialX,
				initialY - 1,
				initialZ,
				this.unit.getPosition().getDoubleCoordinates());
	}

	@Test
	public void testMoveTo() {
		this.unit.moveTo(new int[]{10, 2, 3});
		for(int i = 0; i < 6; i++)
			this.unit.advanceTime(1);
		assertDoublePositionEquals(
				7.5, 2.5, 3.5,
				this.unit.getPosition().getDoubleCoordinates()
		);

		this.unit.advanceTime(1);

		this.unit.moveTo(new int[]{7, 4, 3});
		for(int i = 0; i < 100; i++)
			this.unit.advanceTime(.1);
		assertDoublePositionEquals(
				7.5, 4.5, 3.5,
				this.unit.getPosition().getDoubleCoordinates()
		);
	}

	@Test
	public void testStartSprinting() {
		try {
			method = unitClass.getDeclaredMethod("setState", State.class);
			method.setAccessible(true);
			method.invoke(this.unit, State.MOVING);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		this.unit.startSprinting();
		assertTrue(this.unit.isSprinting());
	}

	@Test
	public void testStopSprinting() {
		this.testStartSprinting();
		this.unit.stopSprinting();
		assertFalse(this.unit.isSprinting());
	}

	@Test
	public void testIsSprinting() {
		this.testStartSprinting();
		assertTrue(this.unit.isSprinting());
		this.unit.stopSprinting();
		assertFalse(this.unit.isSprinting());
	}

	@Test
	public void testWork_Doing_Nothing() {
		try {
			method = unitClass.getDeclaredMethod("setState", State.class);
			method.setAccessible(true);
			method.invoke(this.unit, State.NONE);
		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
			e.printStackTrace();
		}
        try {
			unit.work(this.unit.getPosition().getCubeCoordinates()[0], this.unit.getPosition().getCubeCoordinates()[1], this.unit.getPosition().getCubeCoordinates()[2]);
		} catch (Exception ignored) {

		}
        if (this.unit.getWorld()
                .getCube(
                        this.unit.getPosition().getCubeCoordinates()[0],
                        this.unit.getPosition().getCubeCoordinates()[1],
                        this.unit.getPosition().getCubeCoordinates()[2])
                .isSolid()) {
            assertEquals(State.WORKING, unit.getState());
        }
		assertEquals(State.NONE, unit.getState());
	}

	@Test
	public void testWork_Doing_Activity() {
		try {
			method = unitClass.getDeclaredMethod("setState", State.class);
			method.setAccessible(true);
			method.invoke(this.unit, State.RESTING);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		try {
            unit.work(
                    this.unit.getPosition().getCubeCoordinates()[0],
                    this.unit.getPosition().getCubeCoordinates()[1],
                    this.unit.getPosition().getCubeCoordinates()[2]
            );
		} catch (Exception exc) {

		}
		assertEquals(State.RESTING, unit.getState());
	}

	@Test
	public void testRest_Full_Hit_Stamina_Points() {
		try {
			unit.rest();
		} catch (IllegalStateException exc) {
			//do nothing
		}
		assertEquals(State.NONE, unit.getState());
	}

	@Test
	public void testGetOrientation() {
		assertEquals(Math.PI / 2, this.unit.getOrientation(), .01);
	}

	@Test
	public void testGetStrength() {
		this.unit.setStrength(55);
		assertEquals(55, this.unit.getStrength(), .01);
	}

	@Test
	public void SetStrength_LegalCase() {
		this.unit.setStrength(67);
		assertEquals(67, this.unit.getStrength(), .01);
	}

	@Test
	public void SetStrength_InputLowerThanMinAttrValue() {
		this.unit.setStrength(0);
		assertEquals(1, this.unit.getStrength(), .01);
	}

	@Test
	public void SetStrength_InputHigherThanMaxAttrValue() {
		this.unit.setStrength(235);
		assertEquals(200, this.unit.getStrength(), .01);
	}

	@Test
	public void testGetAgility() {
		this.unit.setAgility(55);
		assertEquals(55, this.unit.getAgility(), .01);
	}

	@Test
	public void SetAgility_LegalCase() {
		this.unit.setAgility(67);
		assertEquals(67, this.unit.getAgility(), .01);
	}

	@Test
	public void SetAgility_InputLowerThanMinAttrValue() {
		this.unit.setAgility(0);
		assertEquals(1, this.unit.getAgility(), .01);
	}

	@Test
	public void SetAgility_InputHigherThanMaxAttrValue() {
		this.unit.setAgility(235);
		assertEquals(200, this.unit.getAgility(), .01);
	}

	@Test
	public void testGetWeight() {
		assertEquals(50,this.unit.getWeight(), .01);
	}

	@Test
	public void testSetWeight() {
		this.unit.setWeight(100);
		assertEquals(100, this.unit.getWeight(), .01);
		this.unit.setAgility(50);
		this.unit.setStrength(50);
		this.unit.setWeight(-5);
		assertEquals((unit.getAgility()+unit.getStrength())/2, this.unit.getWeight(), .01);
	}

	@Test
	public void testGetToughness() {
		this.unit.setToughness(55);
		assertEquals(55, this.unit.getToughness(), .01);
	}

	@Test
	public void SetToughness_LegalCase() {
		this.unit.setToughness(67);
		assertEquals(67, this.unit.getToughness(), .01);
	}

	@Test
	public void SetToughness_InputLowerThanMinAttrValue() {
		this.unit.setToughness(0);
		assertEquals(1, this.unit.getToughness(), .01);
	}

	@Test
	public void SetToughness_InputHigherThanMaxAttrValue() {
		this.unit.setToughness(235);
		assertEquals(200, this.unit.getToughness(), .01);
	}

	@Test
	public void testGetHitPoints() {
		assertEquals(50, this.unit.getCurrentHitPoints(), .01);
	}

	@Test
	public void testGetMaxHitPoints() {
		assertEquals(200*this.unit.getWeight()/100*this.unit.getToughness()/100, this.unit.getMaxHitPoints(), .01);
	}

	@Test
	public void testGetStamina() {
		assertEquals(50,this.unit.getCurrentStaminaPoints(), .01);
	}

	@Test
	public void testGetMaxStaminaPoints() {
		assertEquals(200*this.unit.getWeight()/100*this.unit.getToughness()/100, this.unit.getMaxHitPoints(), .01);
		
	}

	@Test
	public void testGetName() {
		assertEquals("TestUnit", this.unit.getName());
	}

	@Test
	public void isValidName_LegalCase() {
		try {
			method = unitClass.getDeclaredMethod("isValidName", String.class);
			method.setAccessible(true);
			assertEquals(true, method.invoke(null, "Test 'Name"));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		//assertEquals(true, Unit.isValidName("\"Test 'Name\""));
	}
	
	@Test
	public void isValidName_IllegalCase() {
		try {
			method = unitClass.getDeclaredMethod("isValidName", String.class);
			method.setAccessible(true);
			assertEquals(false, method.invoke(null, "|Test name 12"));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		//assertEquals(false, Unit.isValidName("|Test name 12"));
	}

	@Test
	public void setName_LegalCase() {
		this.unit.setName("New Test Name");
		assertEquals("New Test Name", this.unit.getName());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setName_IllegalCase() throws IllegalArgumentException {
		this.unit.setName("New @Test5Name");
	}

	@Test
	public void AttackNeighboringUnit_LegalCase() {
		Unit defender = new Unit("TestUnit", new int[] { 2, 2, 3 }, 50, 50, 50, 50, false);
        defender.setWorld(world);
        defender.setFaction(new Faction(""));
		this.unit.attack(defender);

		assertEquals(State.ATTACKING, this.unit.getState());
	}

	@Test(expected = IllegalStateException.class)
	public void AttackNotNeighboringUnit_IllegalCase() throws IllegalStateException {
		Unit defender = new Unit("TestUnit", new int[] { 1, 2, 3 }, 50, 50, 50, 50, false);
        defender.setFaction(new Faction(""));
		this.unit.attack(defender);
	}

	@Test
	public void testSetDefaultBehaviorEnabled() {
		this.unit.setDefaultBehaviorEnabled(true);
		assertTrue(this.unit.getDefaultBehaviorEnabled());
		this.unit.setDefaultBehaviorEnabled(false);
		assertFalse(this.unit.getDefaultBehaviorEnabled());
	}

	@Test
	public void testGetState() {
		assertEquals(State.NONE, this.unit.getState());
	}

	@Test
	public void testGetCurrentSpeed() {
		assertEquals(0, this.unit.getCurrentSpeed(), .01);
	}

	@Test
	public void testGetDefaultBehaviorEnabled() {
		assertFalse(this.unit.getDefaultBehaviorEnabled());
	}

    @Test
    public void getPosition() throws Exception {
        assertArrayEquals(new int[]{1,2,3}, this.unit.getPosition().getCubeCoordinates());
    }

    @Test
    public void setAndGetFaction() throws Exception {
        Faction faction = new Faction("");
        this.unit.setFaction(faction);
        assertEquals(faction, this.unit.getFaction());
    }

    @Test
    public void getCurrentExperiencePoints() throws Exception {
        assertEquals(0, this.unit.getCurrentExperiencePoints());
    }

    @Test
    public void setAndGetWorld() {
        this.unit.setWorld(world);
        assertEquals(world, this.unit.getWorld());
    }

    @Test
    public void notCarryingLog() {
        assertFalse(this.unit.isCarryingLog());
    }

    @Test
    public void carryingLog() {
        this.unit.setMaterial(new Log(new Position(new int[]{1,2,3}), world));
        assertTrue(this.unit.isCarryingLog());
    }

    @Test
    public void notCarryingBoulder() {
        assertFalse(this.unit.isCarryingBoulder());
    }

    @Test
    public void carryingBoulder() {
        this.unit.setMaterial(new Boulder(new Position(new int[]{1,2,3}), world));
        assertTrue(this.unit.isCarryingBoulder());
    }

    @Test
    public void isAlive() throws Exception {
        assertTrue(this.unit.isAlive());
    }
}
