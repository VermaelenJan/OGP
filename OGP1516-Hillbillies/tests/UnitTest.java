import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.IllegalAdjacentPositionException;
import hillbillies.model.IllegalAdvanceTimeException;
import hillbillies.model.IllegalNameException;
import hillbillies.model.IllegalPositionException;
import hillbillies.model.Unit;
import ogp.framework.util.Util;

public class UnitTest {
	
	private static String ValidName;
	private static List<Integer> ValidLocation;
	private static Unit unit_test2;

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	ValidName = "This 's a \"test\"";
	ValidLocation = new ArrayList<Integer>(); 
	ValidLocation.add((int) 0); ValidLocation.add((int) 24); ValidLocation.add((int) 49);
	unit_test2 = new Unit(ValidLocation, ValidName,60 ,50 ,70 ,90 );

	}
	
	// Position tests

	@Test
	public void constructor_Legal() throws IllegalPositionException, IllegalNameException, IllegalAdjacentPositionException {
		List <Integer> location = new ArrayList<Integer>();
		location.add(10); location.add(20); location.add( 30);
		Unit unit = new Unit(location, ValidName, 0, 0, 0, 0);
		List<Double> position = unit.getLocation();
		assertEquals(10.5, position.get(0), Util.DEFAULT_EPSILON);
		assertEquals(20.5, position.get(1), Util.DEFAULT_EPSILON);
		assertEquals(30.5, position.get(2), Util.DEFAULT_EPSILON);
	}

	@Test (expected=IllegalPositionException.class)
	public void constructor_OutOfWorldPos() throws IllegalPositionException, IllegalNameException,IllegalAdjacentPositionException {
		List<Integer> location = new ArrayList<Integer>();
		location.add(0); location.add(0); location.add(50);
		new Unit(location, ValidName, 0, 0, 0, 0);
	}
	
	@Test (expected=IllegalPositionException.class)
	public void constructor_OutOfWorldNeg() throws IllegalPositionException, IllegalNameException, IllegalAdjacentPositionException {
		List<Integer> location = new ArrayList<Integer>();
		location.add(10); location.add(10); location.add(-1);
		new Unit(location, ValidName, 0, 0, 0, 0);
	}	
	
	// SETLOCATION ==> private
//	@Test
//	public void getOccupiedCube_Correct_1() throws IllegalPositionException, IllegalNameException {
//		List<Integer> location = new ArrayList<Integer>();
//		location.add(0); location.add(0); location.add(0);
//		
//		Unit unit = new Unit(location, ValidName, 0, 0, 0, 0);
//		
//		List<Double> location1 = new ArrayList<Double>();
//		location1.add((double) 0.1); location1.add((double) 2.1); location1.add((double) 4.9);
//		unit.setLocation(location1);
//		
//		List<Integer> cube = unit.getOccupiedCube();
//		assertEquals(0, cube.get(0), Util.DEFAULT_EPSILON);
//		assertEquals(2, cube.get(1), Util.DEFAULT_EPSILON);
//		assertEquals(4, cube.get(2), Util.DEFAULT_EPSILON);
//	}
	
	// SETLOCATION ==> private
//	@Test
//	public void getLocation_Correct() throws IllegalPositionException, IllegalNameException {
//		List<Integer> initLoc = new ArrayList<Integer>();
//		initLoc.add(0); initLoc.add(0); initLoc.add(0);
//		List<Double> location = new ArrayList<Double>();
//		location.add((double) 49); location.add((double) 10.001); location.add((double) 0);
//		Unit unit = new Unit(initLoc, ValidName, 0, 0, 0, 0);
//		unit.setLocation(location);
//		assertEquals(49, unit.getLocation().get(0), Util.DEFAULT_EPSILON);
//		assertEquals(10.001, unit.getLocation().get(1), Util.DEFAULT_EPSILON);
//		assertEquals(0, unit.getLocation().get(2), Util.DEFAULT_EPSILON);
//	}
	
	// Name tests
	
	@Test
	public void constructor_LegalName() throws IllegalPositionException, IllegalNameException , IllegalAdjacentPositionException {
		new Unit(ValidLocation, ValidName, 0, 0, 0, 0);
	}
	
	@Test (expected=IllegalNameException.class)
	public void constructor_IllegalNameChars() throws IllegalPositionException, IllegalNameException , IllegalAdjacentPositionException {
		new Unit(ValidLocation, "N0 v@lid name", 0, 0, 0, 0);
	}
	
	@Test (expected=IllegalNameException.class)
	public void constructor_IllegalNameCapt() throws IllegalPositionException, IllegalNameException, IllegalAdjacentPositionException {
		new Unit(ValidLocation, "no valid name", 0, 0, 0, 0);
	}
	
	@Test (expected=IllegalNameException.class)
	public void constructor_IllegalNameLength() throws IllegalPositionException, IllegalNameException, IllegalAdjacentPositionException {
		new Unit(ValidLocation, "T", 0, 0, 0, 0);
	}
	
	// Weight (and Agility,Strength) tests based on white box testing.
	
	@Test
	public void setWeight_BelowAfterConstr() throws IllegalPositionException, IllegalNameException , IllegalAdjacentPositionException {
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, 0);
		unit.setAgility(0);
		unit.setStrength(0);
		unit.setWeight(-20);
		assertEquals(1,unit.getWeight(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setWeight_BelowConstr() throws IllegalPositionException, IllegalNameException , IllegalAdjacentPositionException {
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, 0);
		unit.setWeight(-20);
		assertEquals(25,unit.getWeight(),Util.DEFAULT_EPSILON);
	}
	
	
	@Test
	public void setWeight_Below() throws IllegalPositionException, IllegalNameException , IllegalAdjacentPositionException {
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, 0);
		unit.setStrength(50);
		unit.setAgility(50);
		unit.setWeight(-20);
		assertEquals(50, unit.getWeight(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setWeight_Betweenboundaries() throws IllegalPositionException, IllegalNameException , IllegalAdjacentPositionException {
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, 0);
		unit.setWeight(70);
		assertEquals(70, unit.getWeight(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setWeight_MaxValue() throws IllegalPositionException, IllegalNameException , IllegalAdjacentPositionException {
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, 0);
		unit.setWeight(200);
		assertEquals(200, unit.getWeight(),Util.DEFAULT_EPSILON);
	}
	
	
	@Test
	public void setWeight_Overflow() throws IllegalPositionException, IllegalNameException , IllegalAdjacentPositionException {
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, 0);
		unit.setWeight(250);
		assertEquals(200, unit.getWeight(),Util.DEFAULT_EPSILON);
	}
	
	
	// Strength tests based on white box testing.
	@Test
	public void setStrength_BelowConstr()  throws IllegalPositionException, IllegalNameException , IllegalAdjacentPositionException {
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, 0);
		assertEquals(25, unit.getStrength(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setStrength_BelowAfterConstr()  throws IllegalPositionException, IllegalNameException , IllegalAdjacentPositionException {
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, 0);
		unit.setStrength(-20);
		assertEquals(1, unit.getStrength(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setStrength_Betweenboundaries() throws IllegalPositionException, IllegalNameException , IllegalAdjacentPositionException {
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, 0);
		unit.setStrength(70);
		assertEquals(70, unit.getStrength(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setStrength_MaxValue() throws IllegalPositionException, IllegalNameException , IllegalAdjacentPositionException {
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, 0);
		unit.setStrength(200);
		assertEquals(200, unit.getStrength(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setStrength_Overflow() throws IllegalPositionException, IllegalNameException , IllegalAdjacentPositionException {
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, 0);
		unit.setStrength(250);
		assertEquals(200, unit.getStrength(),Util.DEFAULT_EPSILON);
	
	}
	
	
	// Agility tests based on white box testing
	
	@Test
	public void setAgility_BelowConstr()  throws IllegalPositionException, IllegalNameException , IllegalAdjacentPositionException {
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, 0);
		assertEquals(25, unit.getAgility(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setAgility_BelowAfterConstr()  throws IllegalPositionException, IllegalNameException , IllegalAdjacentPositionException {
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, 0);
		unit.setAgility(-20);
		assertEquals(1, unit.getAgility(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setAgility_Betweenboundaries() throws IllegalPositionException, IllegalNameException , IllegalAdjacentPositionException {
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, 0);
		unit.setAgility(70);
		assertEquals(70, unit.getAgility(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setAgility_MaxValue() throws IllegalPositionException, IllegalNameException , IllegalAdjacentPositionException {
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, 0);
		unit.setAgility(200);
		assertEquals(200, unit.getAgility(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setAgility_Overflow() throws IllegalPositionException, IllegalNameException , IllegalAdjacentPositionException {
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, 0);
		unit.setAgility(250);
		assertEquals(200, unit.getAgility(),Util.DEFAULT_EPSILON);
	
	}
	
	// Toughness tests based on white box testing. 
	
	@Test
	public void setToughness_BelowConstr()  throws IllegalPositionException, IllegalNameException , IllegalAdjacentPositionException {
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, 0);
		assertEquals(25, unit.getToughness(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setToughness_BelowAfterConstr()  throws IllegalPositionException, IllegalNameException , IllegalAdjacentPositionException {
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, 0);
		unit.setToughness(-20);
		assertEquals(1, unit.getToughness(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setToughness_Betweenboundaries() throws IllegalPositionException, IllegalNameException , IllegalAdjacentPositionException {
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, 0);
		unit.setToughness(70);
		assertEquals(70, unit.getToughness(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setToughness_MaxValue() throws IllegalPositionException, IllegalNameException , IllegalAdjacentPositionException {
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, 0);
		unit.setToughness(200);
		assertEquals(200, unit.getToughness(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setToughness_Overflow() throws IllegalPositionException, IllegalNameException , IllegalAdjacentPositionException {
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, 0);
		unit.setToughness(250);
		assertEquals(200, unit.getToughness(),Util.DEFAULT_EPSILON);
	
	}
	
	// Hitpoints Tests
	
	@Test
	public void getMaxHitpoints_check(){
		assertEquals(108, unit_test2.getMaxHitpointsStamina(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void getCurrentHitpoints_check(){
		assertEquals(108, unit_test2.getHitpoints(),Util.DEFAULT_EPSILON);
	}
	
	// Stamina Tests
	
	@Test
	public void getMaxStamina_check(){
		assertEquals(108, unit_test2.getMaxHitpointsStamina(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void getCurrentStamina_check(){
		assertEquals(108, unit_test2.getStamina(),Util.DEFAULT_EPSILON);
	}
	
	
	
	
	
	
//	@Test
//	public void setProperties() throws IllegalPositionException, IllegalNameException {
//		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, 0);
//		unit.setWeight(50);
//		unit.setToughness(60);
//		
//	}
	
	// Hitpoints tests
	
//	@Test
//	public void setHitpoints_Check() throws IllegalPositionException, IllegalNameException {
//		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, 0);
//		unit.setWeight(198);
//		unit.setToughness(21);
//		unit.setHitpoints(84);
//		assertEquals(84, unit.getHitpoints(), Util.DEFAULT_EPSILON);
//	}
	
	@Test
	public void someBiggerTest() throws IllegalPositionException, IllegalNameException, IllegalAdvanceTimeException , IllegalAdjacentPositionException {
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
