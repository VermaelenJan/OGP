import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.IllegalNameException;
import hillbillies.model.IllegalPositionException;
import hillbillies.model.Unit;
import ogp.framework.util.Util;

public class UnitTest {
	
	private static String ValidName;
	private static List<Integer> ValidLocation;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	ValidName = "This 's a \"test\"";
	ValidLocation = new ArrayList<Integer>(); 
	ValidLocation.add((int) 0); ValidLocation.add((int) 24); ValidLocation.add((int) 49);
	}

	@Test
	public void constructor_Legal() throws IllegalPositionException, IllegalNameException {
		List <Integer> location = new ArrayList<Integer>();
		location.add(10); location.add(20); location.add( 30);
		Unit unit = new Unit(location, ValidName, 0, 0, 0, 0);
		List<Double> position = unit.getLocation();
		assertEquals(10.5, position.get(0), Util.DEFAULT_EPSILON);
		assertEquals(20.5, position.get(1), Util.DEFAULT_EPSILON);
		assertEquals(30.5, position.get(2), Util.DEFAULT_EPSILON);
	}

	@Test (expected=IllegalPositionException.class)
	public void constructor_Illegal_OutOfWorld_Pos() throws IllegalPositionException, IllegalNameException {
		List<Integer> location = new ArrayList<Integer>();
		location.add(0); location.add(0); location.add(50);
		new Unit(location, ValidName, 0, 0, 0, 0);
	}
	
	@Test (expected=IllegalPositionException.class)
	public void constructor_Illegal_OutOfWorld_Neg() throws IllegalPositionException, IllegalNameException {
		List<Integer> location = new ArrayList<Integer>();
		location.add(10); location.add(10); location.add(-1);
		new Unit(location, ValidName, 0, 0, 0, 0);
	}	
	
	@Test
	public void constructor_Legal_Name() throws IllegalPositionException, IllegalNameException {
		new Unit(ValidLocation, ValidName, 0, 0, 0, 0);
	}
	
	@Test (expected=IllegalNameException.class)
	public void constructor_Illegal_NameChars() throws IllegalPositionException, IllegalNameException {
		new Unit(ValidLocation, "N0 v@lid name", 0, 0, 0, 0);
	}
	
	@Test (expected=IllegalNameException.class)
	public void constructor_Illegal_NameCapt() throws IllegalPositionException, IllegalNameException {
		new Unit(ValidLocation, "no valid name", 0, 0, 0, 0);
	}
	
	@Test (expected=IllegalNameException.class)
	public void constructor_Illegal_NameLength() throws IllegalPositionException, IllegalNameException {
		new Unit(ValidLocation, "T", 0, 0, 0, 0);
	}
	
	@Test
	public void getOccupiedCube_Correct_1() throws IllegalPositionException, IllegalNameException {
		List<Integer> location = new ArrayList<Integer>();
		location.add(0); location.add(0); location.add(0);
		
		Unit unit = new Unit(location, ValidName, 0, 0, 0, 0);
		
		List<Double> location1 = new ArrayList<Double>();
		location1.add((double) 0.1); location1.add((double) 2.1); location1.add((double) 4.9);
		unit.setLocation(location1);
		
		List<Integer> cube = unit.getOccupiedCube();
		assertEquals(0, cube.get(0), Util.DEFAULT_EPSILON);
		assertEquals(2, cube.get(1), Util.DEFAULT_EPSILON);
		assertEquals(4, cube.get(2), Util.DEFAULT_EPSILON);

	}
	
	@Test
	public void getLocation_Correct() throws IllegalPositionException, IllegalNameException {
		List<Integer> initLoc = new ArrayList<Integer>();
		initLoc.add(0); initLoc.add(0); initLoc.add(0);
		List<Double> location = new ArrayList<Double>();
		location.add((double) 49); location.add((double) 10.001); location.add((double) 0);
		Unit unit = new Unit(initLoc, ValidName, 0, 0, 0, 0);
		unit.setLocation(location);
		assertEquals(49, unit.getLocation().get(0), Util.DEFAULT_EPSILON);
		assertEquals(10.001, unit.getLocation().get(1), Util.DEFAULT_EPSILON);
		assertEquals(0, unit.getLocation().get(2), Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setHitpoints_Check() throws IllegalPositionException, IllegalNameException {
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, 0);
		unit.setWeight(198);
		unit.setToughness(21);
		unit.setHitpoints(84);
		assertEquals(84, unit.getHitpoints(), Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void someBiggerTest() throws IllegalPositionException, IllegalNameException {
		List <Integer> location = new ArrayList<Integer>();
		location.add(10); location.add(20); location.add( 30);
		Unit unit = new Unit(location, ValidName, 0, 0, 0, 0, 0);
		List <Integer> target = new ArrayList<Integer>();
		target.add(11); target.add(20); target.add( 30);
		unit.moveTo(target);
		unit.advanceTime(0.1);
		assertEquals((int) 10, (int) unit.getOccupiedCube().get(0));
		assertEquals((int) 20, (int) unit.getOccupiedCube().get(1));
		assertEquals((int) 30, (int) unit.getOccupiedCube().get(2));

		assertNotEquals((double) 10.5, unit.getLocation().get(0));
		assertEquals((double) 20.5, unit.getLocation().get(1), Util.DEFAULT_EPSILON);
		assertEquals((double) 30.5, unit.getLocation().get(2), Util.DEFAULT_EPSILON);
		
		for (int i = 1; i<100; i++) {
		unit.advanceTime(0.1);
		}
		
		assertEquals((double) 11.5, unit.getLocation().get(0), Util.DEFAULT_EPSILON);
	}
}
