package hillbillies.tests.facade;

import static hillbillies.tests.util.PositionAsserts.assertDoublePositionEquals;
import static hillbillies.tests.util.PositionAsserts.assertIntegerPositionEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import hillbillies.model.Unit;
import hillbillies.part1.facade.Facade;
import hillbillies.part1.facade.IFacade;
import ogp.framework.util.ModelException;

public class Part1TestPartial {

	private Facade facade;

	@Before
	public void setup() {
		this.facade = new Facade();
	}

	@Test
	public void testCubeCoordinate() throws ModelException {
		Unit unit = facade.createUnit("TestUnit", new int[] { 1, 2, 3 }, 50, 50, 50, 50, false);
		assertIntegerPositionEquals("A valid position should be accepted", 1, 2, 3, facade.getCubeCoordinate(unit));
	}

	@Test
	public void testPosition() throws ModelException {
		Unit unit = facade.createUnit("TestUnit", new int[] { 12, 11, 5 }, 50, 50, 50, 50, false);
		assertDoublePositionEquals("Position must be the center of the cube", 12.5, 11.5, 5.5,
				facade.getPosition(unit));
	}

	@Test
	public void testInitialAgilityTooLow() throws ModelException {
		Unit unit = facade.createUnit("TestUnit", new int[] { 1, 2, 3 }, 50, 24, 50, 50, false);
		assertTrue("An attribute value of 24 should be replaced with a valid value",
				25 <= facade.getAgility(unit) && facade.getAgility(unit) <= 100);
	}

	@Test
	public void testSetValidName() throws ModelException {
		Unit unit = facade.createUnit("TestUnit", new int[] { 1, 1, 1 }, 50, 50, 50, 50, false);
		facade.setName(unit, "John \"Johnnie\" O'Hare the first");
		assertEquals("This should be a valid name", "John \"Johnnie\" O'Hare the first", facade.getName(unit));
	}

	@Test
	public void testSetNameWithoutCapital() throws ModelException {
		Unit unit = facade.createUnit("TestUnit", new int[] { 1, 1, 1 }, 50, 50, 50, 50, false);
		try {
			facade.setName(unit, "john O'Hare");
		} catch (ModelException e) {
			// that's OK
		}
		assertEquals("This name is invalid because it doesn't start with a capital", "TestUnit", facade.getName(unit));
	}

	@Test
	public void testCorrectPosition() throws ModelException {
		IFacade facade = new Facade();
		Unit unit = facade.createUnit("TestUnit", new int[] { 1, 2, 3 }, 50, 50, 50, 50, false);
		facade.moveToAdjacent(unit, 1, 0, -1);
		double speed = facade.getCurrentSpeed(unit);
		double distance = Math.sqrt(2);
		double time = distance / speed;
		advanceTimeFor(facade, unit, time, 0.05);
		assertDoublePositionEquals(2.5, 2.5, 2.5, facade.getPosition(unit));
	}

	/**
	 * Helper method to advance time for the given unit by some time.
	 * 
	 * @param time
	 *            The time, in seconds, to advance.
	 * @param step
	 *            The step size, in seconds, by which to advance.
	 */
	private static void advanceTimeFor(IFacade facade, Unit unit, double time, double step) throws ModelException {
		int n = (int) (time / step);
		for (int i = 0; i < n; i++)
			facade.advanceTime(unit, step);
		facade.advanceTime(unit, time - n * step);
	}

}
