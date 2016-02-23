import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import hillbillies.model.IllegalNameException;
import hillbillies.model.IllegalPositionException;
import hillbillies.model.Unit;
import ogp.framework.util.Util;

public class UnitTest {

	private static final String ValidName = "This 's a \"test\"";

	@Test
	public void constructor_Legal() throws IllegalPositionException, IllegalNameException {
		List <Double> location = new ArrayList<Double>();
		location.add((double) 10); location.add((double) 20); location.add((double) 30);
		Unit unit = new Unit(location, ValidName, 0, 0, 0, 0, 0, 0, 0);
		List<Double> position = unit.getLocation();
		assertEquals(10, position.get(0), Util.DEFAULT_EPSILON);
		assertEquals(20, position.get(1), Util.DEFAULT_EPSILON);
		assertEquals(30, position.get(2), Util.DEFAULT_EPSILON);
	}

	@Test (expected=IllegalPositionException.class)
	public void constructor_Illegal_OutOfWorld_Pos() throws IllegalPositionException, IllegalNameException {
		List<Double> location = new ArrayList<Double>();
		location.add((double) 0); location.add((double) 0); location.add((double) 49.001);
		new Unit(location, ValidName, 0, 0, 0, 0, 0, 0, 0);
	}
	
	@Test (expected=IllegalPositionException.class)
	public void constructor_Illegal_OutOfWorld_Neg() throws IllegalPositionException, IllegalNameException {
		List<Double> location = new ArrayList<Double>();
		location.add((double) 10); location.add((double) 10); location.add((double) -0.001);
		new Unit(location, ValidName, 0, 0, 0, 0, 0, 0, 0);
	}	
	
	@Test (expected=IllegalNameException.class)
	public void constructor_Illegal_NameChars() throws IllegalPositionException, IllegalNameException {
		List<Double> location = new ArrayList<Double>();
		location.add((double) 10); location.add((double) 10); location.add((double) 10);
		new Unit(location, "N0 v@lid name", 0, 0, 0, 0, 0, 0, 0);
	}
	
	@Test (expected=IllegalNameException.class)
	public void constructor_Illegal_NameCapt() throws IllegalPositionException, IllegalNameException {
		List<Double> location = new ArrayList<Double>();
		location.add((double) 10); location.add((double) 10); location.add((double) 10);
		new Unit(location, "no valid name", 0, 0, 0, 0, 0, 0, 0);
	}
	
	@Test
	public void getOccupiedCube_Correct_1() throws IllegalPositionException, IllegalNameException {
		List<Double> location = new ArrayList<Double>();
		 location.add((double) 0.9); location.add((double) 1.9); location.add((double) 2.9);
		Unit unit = new Unit(location, ValidName, 0, 0, 0, 0, 0, 0, 0);
		List<Integer> cube = unit.getOccupiedCube();
		assertEquals(0, cube.get(0), Util.DEFAULT_EPSILON);
		assertEquals(1, cube.get(1), Util.DEFAULT_EPSILON);
		assertEquals(2, cube.get(2), Util.DEFAULT_EPSILON);

	}
	
	@Test
	public void getOccupiedCube_Correct_2() throws IllegalPositionException, IllegalNameException {
		List<Double> location = new ArrayList<Double>();
		location.add((double) 0.1); location.add((double) 1.1); location.add((double) 2.1);
		Unit unit = new Unit(location, ValidName, 0, 0, 0, 0, 0, 0, 0);
		List<Integer> cube = unit.getOccupiedCube();
		assertEquals(0, cube.get(0), Util.DEFAULT_EPSILON);
		assertEquals(1, cube.get(1), Util.DEFAULT_EPSILON);
		assertEquals(2, cube.get(2), Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void getLocation_Correct() throws IllegalPositionException, IllegalNameException {
		List<Double> location = new ArrayList<Double>();
		location.add((double) 49); location.add((double) 10.001); location.add((double) 0);
		Unit unit = new Unit(location, ValidName, 0, 0, 0, 0, 0, 0, 0);
		assertEquals(49, unit.getLocation().get(0), Util.DEFAULT_EPSILON);
		assertEquals(10.001, unit.getLocation().get(1), Util.DEFAULT_EPSILON);
		assertEquals(0, unit.getLocation().get(2), Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setHitpoints_Check() throws IllegalPositionException, IllegalNameException {
		List<Double> location = new ArrayList<Double>();
		location.add((double) 0); location.add((double) 0); location.add((double) 0);
		Unit unit = new Unit(location, ValidName, 0, 0, 0, 0, 0, 0, 0);
		unit.setWeight(198);
		unit.setToughness(21);
		unit.setHitpoints(84);
		assertEquals(84, unit.getHitpoints());
	}
}
