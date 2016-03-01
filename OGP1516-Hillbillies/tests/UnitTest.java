import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
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
	private static Unit valid_unit;

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	ValidName = "This 's a \"test\"";
	ValidLocation = new ArrayList<Integer>(); 
	ValidLocation.add((int) 0); ValidLocation.add((int) 24); ValidLocation.add((int) 49);
	unit_test2 = new Unit(ValidLocation, ValidName,60 ,50 ,70 ,90 );

	}
	
	@Before
	public void setUp() throws Exception {
		valid_unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, 0);
	}
	
	// Position tests

	@Test
	public void constructor_Legal() throws Exception {
		List <Integer> location = new ArrayList<Integer>();
		location.add(10); location.add(20); location.add( 30);
		Unit unit = new Unit(location, ValidName, 0, 0, 0, 0);
		List<Double> position = unit.getLocation();
		assertEquals(10.5, position.get(0), Util.DEFAULT_EPSILON);
		assertEquals(20.5, position.get(1), Util.DEFAULT_EPSILON);
		assertEquals(30.5, position.get(2), Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void constructor_EdgeWorld() throws Exception {
		List <Integer> location = new ArrayList<Integer>();
		location.add(0); location.add(0); location.add( 49);
		Unit unit = new Unit(location, ValidName, 0, 0, 0, 0);
		List<Double> position = unit.getLocation();
		assertEquals(0.5, position.get(0), Util.DEFAULT_EPSILON);
		assertEquals(0.5, position.get(1), Util.DEFAULT_EPSILON);
		assertEquals(49.5, position.get(2), Util.DEFAULT_EPSILON);
	}

	@Test (expected=IllegalPositionException.class)
	public void constructor_OutOfWorldPos() throws Exception {
		List<Integer> location = new ArrayList<Integer>();
		location.add(0); location.add(0); location.add(50);
		new Unit(location, ValidName, 0, 0, 0, 0);
	}
	
	@Test (expected=IllegalPositionException.class)
	public void constructor_OutOfWorldNeg() throws Exception {
		List<Integer> location = new ArrayList<Integer>();
		location.add(10); location.add(10); location.add(-1);
		new Unit(location, ValidName, 0, 0, 0, 0);
	}	

	// Name tests
	
	@Test
	public void constructor_LegalName() throws Exception {
		new Unit(ValidLocation, ValidName, 0, 0, 0, 0);
	}
	
	@Test (expected=IllegalNameException.class)
	public void constructor_IllegalNameChars() throws Exception {
		new Unit(ValidLocation, "N0 v@lid name", 0, 0, 0, 0);
	}
	
	@Test (expected=IllegalNameException.class)
	public void constructor_IllegalNameCapt() throws Exception {
		new Unit(ValidLocation, "no valid name", 0, 0, 0, 0);
	}
	
	@Test (expected=IllegalNameException.class)
	public void constructor_IllegalNameLength() throws Exception {
		new Unit(ValidLocation, "T", 0, 0, 0, 0);
	}
	
	// Test construction weight, strength, toughness, agility
	@Test
	public void construction_PropertiesMin() throws Exception {
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0);
		assertEquals(25,unit.getWeight(),Util.DEFAULT_EPSILON);
		assertEquals(25,unit.getStrength(),Util.DEFAULT_EPSILON);
		assertEquals(25,unit.getToughness(),Util.DEFAULT_EPSILON);
		assertEquals(25,unit.getAgility(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void construction_PropertiesMax() throws Exception {
		Unit unit = new Unit(ValidLocation, ValidName, 4000, 3989, 2045, 20000);
		assertEquals(100,unit.getWeight(),Util.DEFAULT_EPSILON);
		assertEquals(100,unit.getStrength(),Util.DEFAULT_EPSILON);
		assertEquals(100,unit.getToughness(),Util.DEFAULT_EPSILON);
		assertEquals(100,unit.getAgility(),Util.DEFAULT_EPSILON);
	}
	
	// Weight tests
	
	@Test
	public void setWeight_BelowAfterConstr() throws Exception {

		valid_unit.setAgility(0);
		valid_unit.setStrength(0);
		valid_unit.setWeight(-20);
		Util.fuzzyEquals(1,valid_unit.getWeight());
	}
	
	@Test
	public void setWeight_BelowConstr() throws Exception {
		valid_unit.setWeight(-20);
		assertEquals(25,valid_unit.getWeight(),Util.DEFAULT_EPSILON);
	}
		
	
	@Test
	public void setWeight_Below() throws Exception{
		valid_unit.setStrength(50);
		valid_unit.setAgility(50);
		valid_unit.setWeight(-20);
		assertEquals(50, valid_unit.getWeight(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setWeight_Betweenboundaries() throws Exception {
		valid_unit.setWeight(70);
		assertEquals(70, valid_unit.getWeight(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setWeight_MaxValue() throws Exception {
		valid_unit.setWeight(200);
		assertEquals(200, valid_unit.getWeight(),Util.DEFAULT_EPSILON);
	}
	
	
	@Test
	public void setWeight_Overflow() throws Exception {
		valid_unit.setWeight(250);
		assertEquals(200, valid_unit.getWeight(),Util.DEFAULT_EPSILON);
	}
	
	
	// Strength tests
	@Test
	public void setStrength_BelowConstr()  throws Exception {
		assertEquals(25, valid_unit.getStrength(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setStrength_BelowAfterConstr()  throws Exception {
		valid_unit.setStrength(-20);
		assertEquals(1, valid_unit.getStrength(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setStrength_Betweenboundaries() throws Exception {
		valid_unit.setStrength(70);
		assertEquals(70, valid_unit.getStrength(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setStrength_MaxValue() throws Exception {
		valid_unit.setStrength(200);
		assertEquals(200, valid_unit.getStrength(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setStrength_Overflow() throws Exception {
		valid_unit.setStrength(250);
		assertEquals(200, valid_unit.getStrength(),Util.DEFAULT_EPSILON);
	
	}
	
	
	// Agility tests
	
	@Test
	public void setAgility_BelowConstr()  throws Exception {
		assertEquals(25, valid_unit.getAgility(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setAgility_BelowAfterConstr()  throws Exception {
		valid_unit.setAgility(-20);
		assertEquals(1, valid_unit.getAgility(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setAgility_Betweenboundaries() throws Exception {
		valid_unit.setAgility(70);
		assertEquals(70, valid_unit.getAgility(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setAgility_MaxValue() throws Exception {
		valid_unit.setAgility(200);
		assertEquals(200, valid_unit.getAgility(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setAgility_Overflow() throws Exception {
		valid_unit.setAgility(250);
		assertEquals(200, valid_unit.getAgility(),Util.DEFAULT_EPSILON);
	
	}
	
	// Toughness tests based on white box testing. 
	
	@Test
	public void setToughness_BelowConstr()  throws Exception {
		assertEquals(25, valid_unit.getToughness(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setToughness_BelowAfterConstr()  throws Exception {
		valid_unit.setToughness(-20);
		assertEquals(1, valid_unit.getToughness(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setToughness_Betweenboundaries() throws Exception {
		valid_unit.setToughness(70);
		assertEquals(70, valid_unit.getToughness(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setToughness_MaxValue() throws Exception {
		valid_unit.setToughness(200);
		assertEquals(200, valid_unit.getToughness(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setToughness_Overflow() throws Exception {
		valid_unit.setToughness(250);
		assertEquals(200, valid_unit.getToughness(),Util.DEFAULT_EPSILON);
	
	}
	
	// Hitpoints Tests
	
	@Test
	public void getMaxHitpoints_check(){
		assertEquals(200*60*90/100/100, unit_test2.getMaxHitpointsStamina(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void getHitpoints_check(){
		assertEquals(200*60*90/100/100, unit_test2.getHitpoints(),Util.DEFAULT_EPSILON);
	}
	
	// Stamina Tests
	
	@Test
	public void getMaxStamina_check(){
		assertEquals(200*60*90/100/100, unit_test2.getMaxHitpointsStamina(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void getCurrentStamina_check(){
		assertEquals(200*60*90/100/100, unit_test2.getStamina(),Util.DEFAULT_EPSILON);
	}
	
	
	@Test
	public void moveToAdjacent_check() throws Exception {

		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, 0);
		unit.moveToAdjacent(1, 0, -1);
		for (int i = 1; i<100; i++) {
		try {
			unit.advanceTime(0.1);
		} catch (IllegalAdvanceTimeException e) {}
		}
		List <Double> expected_location = new ArrayList <Double>();
		expected_location.add(1.5);expected_location.add(24.5);expected_location.add(48.5);
		assertEquals(expected_location.get(0), unit.getLocation().get(0));
	}
	
	@Test
	public void sprint_check(){
		assertEquals(false,valid_unit.isSprinting());
		valid_unit.startSprinting();
		assertEquals(true, valid_unit.isSprinting());
		valid_unit.stopSprinting();
		assertEquals(false,valid_unit.isSprinting());
	}
	
	@Test
	public void someBiggerTest() throws Exception {
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
