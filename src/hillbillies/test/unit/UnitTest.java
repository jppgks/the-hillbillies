package hillbillies.test.unit;

import hillbillies.model.Unit;
import org.junit.Before;
import org.junit.Test;

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

	@Before
	public void setUp() {
		this.unit = new Unit("TestUnit", new int[] { 1, 2, 3 }, 50, 50, 50, 50, false);
	}

	@Test
	public void CreateUnit_LegalCase() {
		assertEquals("TestUnit", this.unit.getName());

		double halfCubeSideLength = this.unit.position.cubeSideLength / 2;
		assertDoublePositionEquals(
				1 + halfCubeSideLength, 2 + halfCubeSideLength, 3,
				this.unit.position.getUnitCoordinates()
		);

		assertEquals(50, this.unit.getWeight(), 0.01);
		assertEquals(50, this.unit.getAgility(), 0.01);
		assertEquals(50, this.unit.getStrength(), 0.01);
		assertEquals(50, this.unit.getToughness(), 0.01);
		assertFalse(this.unit.getDefaultBehaviorEnabled());

		assertEquals(this.unit.getMaxHitPoints(),this.unit.getCurrentHitPoints(), 0.01);
		assertEquals(this.unit.getMaxStaminaPoints(), this.unit.getCurrentStaminaPoints(), 0.01);
	}
	
	@Test
	public void CreatePosition_LegalCase() {
		Unit.Position testPosition = unit.new Position(new int[] {1, 2, 3});
		assertDoublePositionEquals(1.5, 2.5, 3, testPosition.getUnitCoordinates());
	}

	@Test
	public void CreatePosition_IllegalCase() {
		Unit.Position testPosition = unit.new Position(new int[] {1, 2, 50});
	}

	@Test
	public void testGetCubeCoordinates() {
		assertIntegerPositionEquals(1, 2, 3, this.unit.position.getCubeCoordinates());
	}

//	@Test
//	public void SetCubeCoordinates_LegalCase() {
//		unit.position.setOccupyingCubeCoordinates(new int[]{6, 7, 8});
//		assertIntegerPositionEquals(6, 7, 8, unit.position.getCubeCoordinates());
//	}
//
//	@Test(expected = IllegalCoordinateException.class)
//	public void SetCubeCoordinates_IllegalCase() throws IllegalCoordinateException {
//		unit.position.setOccupyingCubeCoordinates(new int[]{1, 2, 50});
//	}

	/**
	 * Test method for {@link hillbillies.model.Unit#moveToAdjacent(hillbillies.model.Unit.Position)}.
	 */
	@Test
	public void testMoveToAdjacent() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#moveTo(hillbillies.model.Unit.Position)}.
	 */
	@Test
	public void testMoveTo() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#getUnitBaseSpeed()}.
	 */
	@Test
	public void testGetUnitBaseSpeed() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#getUnitWalkSpeed(hillbillies.model.Unit.Position)}.
	 */
	@Test
	public void testGetUnitWalkSpeed() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#startSprinting()}.
	 */
	@Test
	public void testStartSprinting() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#stopSprinting()}.
	 */
	@Test
	public void testStopSprinting() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#isSprinting()}.
	 */
	@Test
	public void testIsSprinting() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#work(java.lang.String)}.
	 */
	@Test
	public void testWork() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#isValidWorkActivity(java.lang.String)}.
	 */
	@Test
	public void testIsValidWorkActivity() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#getTimeForWork()}.
	 */
	@Test
	public void testGetTimeForWork() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#rest()}.
	 */
	@Test
	public void testRest() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#getRegenHitPoints()}.
	 */
	@Test
	public void testGetRegenHitPoints() {
		assertEquals(this.unit.getToughness()/200, this.unit.getRegenHitPoints());
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#getRegenStamina()}.
	 */
	@Test
	public void testGetRegenStamina() {
		assertEquals(this.unit.getToughness()/100, this.unit.getRegenStamina());
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#getOrientation()}.
	 */
	@Test
	public void testGetOrientation() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#getStrength()}.
	 */
	@Test
	public void testGetStrength() {
		this.unit.setStrength(55);
		assertEquals(55, this.unit.getStrength());
	}

	@Test
	public void SetStrength_LegalCase() {
		this.unit.setStrength(67);
		assertEquals(67, this.unit.getStrength());
	}

	@Test
	public void SetStrength_InputLowerThanMinAttrValue() {
		this.unit.setStrength(0);
		assertEquals(1, this.unit.getStrength());
	}

	@Test
	public void SetStrength_InputHigherThanMaxAttrValue() {
		this.unit.setStrength(235);
		assertEquals(200, this.unit.getStrength());
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#getAgility()}.
	 */
	@Test
	public void testGetAgility() {
		this.unit.setAgility(55);
		assertEquals(55, this.unit.getAgility());
	}

	@Test
	public void SetAgility_LegalCase() {
		this.unit.setAgility(67);
		assertEquals(67, this.unit.getAgility());
	}

	@Test
	public void SetAgility_InputLowerThanMinAttrValue() {
		this.unit.setAgility(0);
		assertEquals(1, this.unit.getAgility());
	}

	@Test
	public void SetAgility_InputHigherThanMaxAttrValue() {
		this.unit.setAgility(235);
		assertEquals(200, this.unit.getAgility());
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#getWeight()}.
	 */
	@Test
	public void testGetWeight() {
		assertEquals(50,this.unit.getWeight());
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#setWeight(int)}.
	 */
	@Test
	public void testSetWeight() {
		this.unit.setWeight(100);
		assertEquals(100, this.unit.getWeight());
		this.unit.setAgility(50);
		this.unit.setStrength(50);
		this.unit.setWeight(-5);
		assertEquals((unit.getAgility()+unit.getStrength())/2, this.unit.getWeight());
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#getMinWeight()}.
	 */
	@Test
	public void testMinWeight() {
		assertEquals((unit.getStrength()+ unit.getAgility())/2, unit.getMinWeight());
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#getToughness()}.
	 */
	@Test
	public void testGetToughness() {
		this.unit.setToughness(55);
		assertEquals(55, this.unit.getToughness());
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#setToughness(int)}.
	 */
	@Test
	public void SetToughness_LegalCase() {
		this.unit.setToughness(67);
		assertEquals(67, this.unit.getToughness());
	}

	@Test
	public void SetToughness_InputLowerThanMinAttrValue() {
		this.unit.setToughness(0);
		assertEquals(1, this.unit.getToughness());
	}

	@Test
	public void SetToughness_InputHigherThanMaxAttrValue() {
		this.unit.setToughness(235);
		assertEquals(200, this.unit.getToughness());
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#getActivity(java.lang.String)}.
	 */
	@Test
	public void testGetActivity() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#setState(java.lang.String)}.
	 */
	@Test
	public void testSetActivity() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#isValidState(java.lang.String)}.
	 */
	@Test
	public void testIsValidActivity() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#validActivities()}.
	 */
	@Test
	public void testValidActivities() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#startDefaultBehaviour()}.
	 */
	@Test
	public void testStartDefaultBehaviour() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#stopDefaultBehavior()}.
	 */
	@Test
	public void testStopDefaultBehavior() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#toggleDefaultBehavior()}.
	 */
	@Test
	public void testToggleDefaultBehavior() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#getCurrentHitPoints()}.
	 */
	@Test
	public void testGetHitPoints() {
		assertEquals(50, this.unit.getCurrentHitPoints());
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#setCurrentHitPoints(int)}.
	 */
	@Test
	public void testSetHitPoints() {
		this.unit.setCurrentHitPoints(50);
		assertEquals(50, this.unit.getCurrentHitPoints());
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#isValidHitPoints(int)}.
	 */
	@Test
	public void IsValidHitPoints_LegalCase() {
		assertEquals(true,this.unit.isValidHitPoints(50));
		assertEquals(true,this.unit.isValidHitPoints(17));
	}
	@Test
	public void IsValidHitPoints_IllegalCase() {
		assertEquals(false,this.unit.isValidHitPoints(this.unit.getMaxHitPoints()+1));
		assertEquals(false,this.unit.isValidHitPoints(-17));
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#getMaxHitPoints()}.
	 */
	@Test
	public void testGetMaxHitPoints() {
		assertEquals(200*this.unit.getWeight()/100*this.unit.getToughness()/100, this.unit.getMaxHitPoints());
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#getCurrentStaminaPoints()}.
	 */
	@Test
	public void testGetStamina() {
		assertEquals(50,this.unit.getCurrentStaminaPoints());
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#isValidStamina(int)}.
	 */
	@Test
	public void isValidStamina_LegalCase() {
		assertEquals(true, this.unit.isValidStamina(50));
		assertEquals(true, this.unit.isValidStamina(36));
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#isValidStamina(int)}.
	 */
	@Test
	public void isValidStamina_IllegalCase() {
		assertEquals(false, this.unit.isValidStamina(100));
		assertEquals(false, this.unit.isValidStamina(-23));
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#setStamina(int)}.
	 */
	@Test
	public void testSetStamina() {
		this.unit.setCurrentStamina(15);
		assertEquals(15, this.unit.getCurrentStaminaPoints());
		
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#getMaxStaminaPoints()}.
	 */
	@Test
	public void testGetMaxStaminaPoints() {
		assertEquals(200*this.unit.getWeight()/100*this.unit.getToughness()/100, this.unit.getMaxHitPoints());
		
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#getName()}.
	 */
	@Test
	public void testGetName() {
		assertEquals("TestUnit", this.unit.getName());
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#isValidName(java.lang.String)}.
	 */
	@Test
	public void isValidName_LegalCase() {
		assertEquals(true, Unit.isValidName("\"Test 'Name\""));
	}
	
	@Test
	public void isValidName_IllegalCase() {
		assertEquals(false, Unit.isValidName("|Test name 12"));
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#setName(java.lang.String)}.
	 */
	@Test
	public void setName_LegalCase() {
		this.unit.setName("New Test Name");
		assertEquals("New Test Name", this.unit.getName());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setName_IllegalCase() throws IllegalArgumentException {
		this.unit.setName("New @Test5Name");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#fight(hillbillies.model.Unit, hillbillies.model.Unit)}.
	 */
	@Test
	public void testFight() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#attack(hillbillies.model.Unit)}.
	 */
	@Test
	public void testAttack() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#defend(hillbillies.model.Unit)}.
	 */
	@Test
	public void testDefend() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#advanceTime(double)}.
	 */
	@Test
	public void testAdvanceTime() {
		fail("Not yet implemented");
	}

}
