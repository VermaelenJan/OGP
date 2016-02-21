import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import ogp.framework.util.Util;

public class UnitTest {

	@Test
	public void constructor_Legal() throws IllegalPositionException {
		Unit unit = new Unit(10, 20, 30, null, 0, 0, 0, 0);
		ArrayList<Double> position = unit.getLocation();
		assertEquals(10, position.get(0), Util.DEFAULT_EPSILON);
		assertEquals(20, position.get(1), Util.DEFAULT_EPSILON);
		assertEquals(30, position.get(2), Util.DEFAULT_EPSILON);
	}

	@Test (expected=IllegalPositionException.class)
	public void constructor_Illegal() throws IllegalPositionException {
		new Unit(0, 0, 50, null, 0, 0, 0, 0);
	}

}
