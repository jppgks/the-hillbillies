package hillbillies.test.unit;

import hillbillies.model.State;
import hillbillies.model.Unit;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static hillbillies.tests.util.PositionAsserts.assertDoublePositionEquals;
import static hillbillies.tests.util.PositionAsserts.assertIntegerPositionEquals;
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

	@Before
	public void setUp() {
		this.unit = new Unit("TestUnit", new int[] { 1, 2, 3 }, 50, 50, 50, 50, false);
		unitClass = Unit.class;
	}

	@Test
	public void CreateUnit_LegalCase() {
		assertEquals("TestUnit", this.unit.getName());

		double halfCubeSideLength = this.unit.position.cubeSideLength / 2;
		assertDoublePositionEquals(
				1 + halfCubeSideLength, 2 + halfCubeSideLength, 3 +halfCubeSideLength,
				this.unit.position.getUnitCoordinates()
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
	public void CreatePosition_LegalCase() {
		Unit.Position testPosition = unit.new Position(new int[] {1, 2, 3});
		assertDoublePositionEquals(1.5, 2.5, 3.5, testPosition.getUnitCoordinates());
	}

	@Test
	public void CreatePosition_IllegalCase() {
		Unit.Position testPosition = unit.new Position(new int[] {1, 2, 50});
	}

	@Test
	public void testGetCubeCoordinates() {
		assertIntegerPositionEquals(1, 2, 3, this.unit.position.getCubeCoordinates());
	}

	// This method is private now.
//	@Test
//	public void SetCubeCoordinates_LegalCase() {
//		unit.position.setOccupyingCubeCoordinates(new int[]{6, 7, 8});
//		assertIntegerPositionEquals(6, 7, 8, unit.position.getCubeCoordinates());
//	}

	@Test
	public void testMoveToAdjacent() {
		double initialX = this.unit.position.getUnitCoordinates()[0];
		double initialY = this.unit.position.getUnitCoordinates()[1];
		double initialZ = this.unit.position.getUnitCoordinates()[2];

		this.unit.moveToAdjacent(0, -1, 0);
		this.unit.advanceTime(1.34);

		assertDoublePositionEquals(
				initialX,
				initialY - 1,
				initialZ,
				this.unit.position.getUnitCoordinates());
	}

	@Test
	public void testMoveTo() {
		this.unit.moveTo(new int[]{10, 2, 3});
		for(int i = 0; i < 6; i++)
			this.unit.advanceTime(1);
		assertDoublePositionEquals(
				7.5, 2.5, 3.5,
				this.unit.position.getUnitCoordinates()
		);

		this.unit.advanceTime(1);

		this.unit.moveTo(new int[]{7, 4, 3});
		for(int i = 0; i < 100; i++)
			this.unit.advanceTime(.1);
		assertDoublePositionEquals(
				7.5, 4.5, 3.5,
				this.unit.position.getUnitCoordinates()
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

//	@Test
//	public void testWork_Doing_Nothing() {
//		unit.setState(State.NONE);
//		try {
//			unit.work();
//		} catch (Exception exc) {
//
//		}
//		assertEquals(State.WORKING, unit.getState());
//		assertEquals(500/unit.getStrength(),unit.getTimeForWork(),1);
//	}

//	@Test
//	public void testWork_Doing_Activity() {
//		unit.setState(State.RESTING);
//		try {
//			unit.work();
//		} catch (Exception exc) {
//
//		}
//		assertEquals(State.RESTING, unit.getState());
//	}

	@Test
	public void testRest_Full_Hit_Stamina_Points() {
		try {
			unit.rest();
		} catch (IllegalStateException exc) {
			//do nothing
		}
		assertEquals(State.NONE, unit.getState());
	}

	// These methods are private now.
//	@Test
//	public void testGetRegenHitPoints() {
//		assertEquals(this.unit.getToughness()/200, this.unit.getRegenHitPoints());
//	}
//
//	@Test
//	public void testGetRegenStamina() {
//		assertEquals(this.unit.getToughness()/100, this.unit.getRegenStamina());
//	}

//	@Test
//	public void testRest_NotFull_Hit_Stamina_Points() {
//		unit.setCurrentHitPoints(10);
//		unit.rest();
//		assertEquals(State.RESTING, unit.getState());
//	}
//
//	@Test
//	public void testRest_Doing_Other_Activity() {
//		unit.setCurrentHitPoints(10);
//		unit.setState(State.MOVING);
//		try {
//			unit.rest();
//		} catch (IllegalStateException exc) {
//			// TODO: handle exception
//		}
//		assertEquals(State.MOVING, unit.getState());
//	}

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

	// This method is private now.
//	@Test
//	public void testMinWeight() {
//		assertEquals((unit.getStrength()+ unit.getAgility())/2, unit.getMinWeight());
//	}

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

	// These methods are private now.
//	@Test
//	public void testSetHitPoints() {
//		this.unit.setCurrentHitPoints(50);
//		assertEquals(50, this.unit.getCurrentHitPoints());
//	}
//
//	@Test
//	public void IsValidHitPoints_LegalCase() {
//		assertEquals(true,this.unit.isValidHitPoints(50));
//		assertEquals(true,this.unit.isValidHitPoints(17));
//	}
//	@Test
//	public void IsValidHitPoints_IllegalCase() {
//		assertEquals(false,this.unit.isValidHitPoints(this.unit.getMaxHitPoints()+1));
//		assertEquals(false,this.unit.isValidHitPoints(-17));
//	}

	@Test
	public void testGetMaxHitPoints() {
		assertEquals(200*this.unit.getWeight()/100*this.unit.getToughness()/100, this.unit.getMaxHitPoints(), .01);
	}

	@Test
	public void testGetStamina() {
		assertEquals(50,this.unit.getCurrentStaminaPoints(), .01);
	}

	// These methods are private now.
//	@Test
//	public void isValidStamina_LegalCase() {
//		assertEquals(true, this.unit.isValidStamina(50));
//		assertEquals(true, this.unit.isValidStamina(36));
//	}
//
//	@Test
//	public void isValidStamina_IllegalCase() {
//		assertEquals(false, this.unit.isValidStamina(100));
//		assertEquals(false, this.unit.isValidStamina(-23));
//	}
//
//	@Test
//	public void testSetStamina() {
//		this.unit.setCurrentStaminaPoints(15);
//		assertEquals(15, this.unit.getCurrentStaminaPoints());
//
//	}

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
		this.unit.attack(defender);

		assertEquals(State.ATTACKING, this.unit.getState());

	}

	@Test(expected = IllegalStateException.class)
	public void AttackNotNeighboringUnit_IllegalCase() throws IllegalStateException {
		Unit defender = new Unit("TestUnit", new int[] { 1, 2, 3 }, 50, 50, 50, 50, false);
		this.unit.attack(defender);
	}

	@Test
	public void testDefend() {
		fail("Not yet implemented");
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
}