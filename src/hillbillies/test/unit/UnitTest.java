package hillbillies.test.unit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.net.httpserver.Authenticator.Success;

import hillbillies.model.Unit;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public class UnitTest {
	
	private Unit unit;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.unit = new Unit("TestUnit", new int[] { 1, 2, 3 }, 50, 50, 50, 50, false);
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#Unit(java.lang.String, int[], int, int, int, int, boolean)}.
	 */
	@Test
	public void CreateUnit_LegalCase() {
		fail();
	}

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
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#getRegenStamina()}.
	 */
	@Test
	public void testGetRegenStamina() {
		fail("Not yet implemented");
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
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#setStrength(int)}.
	 */
	@Test
	public void testSetStrength() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#getAgility()}.
	 */
	@Test
	public void testGetAgility() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#setAgility(int)}.
	 */
	@Test
	public void testSetAgility() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#getWeight()}.
	 */
	@Test
	public void testGetWeight() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#setWeight(int)}.
	 */
	@Test
	public void testSetWeight() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#minWeight()}.
	 */
	@Test
	public void testMinWeight() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#getToughness()}.
	 */
	@Test
	public void testGetToughness() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#setToughness(int)}.
	 */
	@Test
	public void testSetToughness() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#getActivity(java.lang.String)}.
	 */
	@Test
	public void testGetActivity() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#setActivity(java.lang.String)}.
	 */
	@Test
	public void testSetActivity() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#isValidActivity(java.lang.String)}.
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
	 * Test method for {@link hillbillies.model.Unit#getHitPoints()}.
	 */
	@Test
	public void testGetHitPoints() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#setHitPoints(int)}.
	 */
	@Test
	public void testSetHitPoints() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#isValidHitPoints(int)}.
	 */
	@Test
	public void testIsValidHitPoints() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#maxHitPoints()}.
	 */
	@Test
	public void testMaxHitPoints() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#getStamina()}.
	 */
	@Test
	public void testGetStamina() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#isValidStamina(int)}.
	 */
	@Test
	public void testIsValidStamina() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#setStamina(int)}.
	 */
	@Test
	public void testSetStamina() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link hillbillies.model.Unit#getMaxStaminaPoints()}.
	 */
	@Test
	public void testGetMaxStaminaPoints() {
		fail("Not yet implemented");
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
