import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.IllegalAdjacentPositionException;
import hillbillies.model.IllegalAdvanceTimeException;
import hillbillies.model.IllegalAttackPosititonException;
import hillbillies.model.IllegalNameException;
import hillbillies.model.IllegalPositionException;
import hillbillies.model.Unit;
import ogp.framework.util.Util;

public class UnitTest {
	
	private static String ValidName;
	private static List<Integer> ValidLocation;
	private static Unit unit_test;
	private static Unit valid_unit;

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	ValidName = "This 's a \"test\"";
	ValidLocation = new ArrayList<Integer>(); 
	ValidLocation.add(0); ValidLocation.add(24); ValidLocation.add(49);
	}
	
	@Before
	public void setUp() throws Exception {
		valid_unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0);
		unit_test = new Unit(ValidLocation, ValidName, 60, 50, 70, 90);
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
		assertEquals(1,valid_unit.getWeight());
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
		assertEquals(200*60*90/100/100, unit_test.getMaxHitpointsStamina(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void getHitpoints_check(){
		assertEquals(200*60*90/100/100, unit_test.getHitpoints(),Util.DEFAULT_EPSILON);
	}
	
	// Stamina Tests
	
	@Test
	public void getMaxStamina_check(){
		assertEquals(200*60*90/100/100, unit_test.getMaxHitpointsStamina(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void getCurrentStamina_check(){
		assertEquals(200*60*90/100/100, unit_test.getStamina(),Util.DEFAULT_EPSILON);
	}
	
	// Movement tests
	
	@Test
	public void moveToAdjacent_check() throws Exception {

		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0);
		assertFalse(unit.isActualMoving());
		unit.moveToAdjacent(1, 0, -1);
		assertTrue(unit.isActualMoving());
		for (int i = 1; i<100; i++) {
			try {
				unit.advanceTime(0.1);
			} catch (IllegalAdvanceTimeException e) {}
		}
		assertFalse(unit.isActualMoving());
		List <Double> expected_location = new ArrayList <Double>();
		expected_location.add(1.5);expected_location.add(24.5);expected_location.add(48.5);
		assertEquals(expected_location.get(0), unit.getLocation().get(0), Util.DEFAULT_EPSILON);
		assertEquals(expected_location.get(1), unit.getLocation().get(1), Util.DEFAULT_EPSILON);
		assertEquals(expected_location.get(2), unit.getLocation().get(2), Util.DEFAULT_EPSILON);
	}
	
	@Test (expected=IllegalAdjacentPositionException.class)
	public void moveToAdjacent_adjacentException() throws Exception {

		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0);
		unit.moveToAdjacent(2, 0, -1);
		
	}
	
	@Test (expected=IllegalPositionException.class)
	public void moveToAdjacent_positionException() throws Exception {
		List <Integer> location = new ArrayList<Integer>();
		location.add(0); location.add(0); location.add(0);
		Unit unit = new Unit(location, ValidName, 0, 0, 0, 0);
		unit.moveToAdjacent(1, 0, -1);
		
	}
	
	@Test
	public void sprint_check(){
		assertFalse(valid_unit.isSprinting());
		valid_unit.startSprinting();
		assertTrue(valid_unit.isSprinting());
		valid_unit.stopSprinting();
		assertFalse(valid_unit.isSprinting());
	}
	
	@Test
	public void moveTo_check() throws Exception {
		List <Integer> location = new ArrayList<Integer>();
		location.add(10); location.add(20); location.add(30);
		Unit unit = new Unit(location, ValidName, 0, 0, 0, 0);
		
		List <Integer> target = new ArrayList<Integer>();
		target.add(12); target.add(20); target.add(30);
		
		assertEquals(10.5, unit.getLocation().get(0), Util.DEFAULT_EPSILON);
		assertEquals(20.5, unit.getLocation().get(1), Util.DEFAULT_EPSILON);
		assertEquals(30.5, unit.getLocation().get(2), Util.DEFAULT_EPSILON);

		unit.moveTo(target);
		unit.advanceTime(0.1);

		assertEquals(10, (int) unit.getOccupiedCube().get(0));
		assertEquals(20, (int) unit.getOccupiedCube().get(1));
		assertEquals(30, (int) unit.getOccupiedCube().get(2));
		
		assertNotEquals(10.5, unit.getLocation().get(0), Util.DEFAULT_EPSILON);
		assertEquals(20.5, unit.getLocation().get(1), Util.DEFAULT_EPSILON);
		assertEquals(30.5, unit.getLocation().get(2), Util.DEFAULT_EPSILON);
		
		for (int i = 1; i<200; i++) {
		unit.advanceTime(0.1);
		}
		
		assertEquals((double) 12.5, unit.getLocation().get(0), Util.DEFAULT_EPSILON);
	}
	
	@Test (expected=IllegalPositionException.class)
	public void moveTo_positionException() throws Exception {
		List <Integer> target = new ArrayList<Integer>();
		target.add(0); target.add(0); target.add(50);
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0);
		unit.moveTo(target);
		
	}
	
	//Orientation tests
	
	@Test
	public void orientation_movingECheck () throws Exception {
		assertEquals(Math.PI/2,valid_unit.getOrientation(), Util.DEFAULT_EPSILON);
		valid_unit.moveToAdjacent(1, 0, 0);
		valid_unit.advanceTime(0.1);
		assertEquals(0, valid_unit.getOrientation(), Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void orientation_movingNCheck () throws Exception {
		assertEquals(Math.PI/2,valid_unit.getOrientation(), Util.DEFAULT_EPSILON);
		valid_unit.moveToAdjacent(0, -1, 0);
		valid_unit.advanceTime(0.1);
		assertEquals(-Math.PI/2, valid_unit.getOrientation(), Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void orientation_movingNECheck () throws Exception {
		assertEquals(Math.PI/2,valid_unit.getOrientation(), Util.DEFAULT_EPSILON);
		valid_unit.moveToAdjacent(1, -1, 0);
		valid_unit.advanceTime(0.1);
		assertEquals(-Math.PI/4, valid_unit.getOrientation(), Util.DEFAULT_EPSILON);
	}
		
	
	//Attack tests
	
	@Test
	public void fight() throws Exception {
		List <Integer> location1 = new ArrayList<Integer>();
		location1.add(10); location1.add(10); location1.add(10);
		Unit unit1 = new Unit(location1, ValidName, 0, 0, 0, 0);
		
		List <Integer> location2 = new ArrayList<Integer>();
		location2.add(11); location2.add(10); location2.add(10);
		Unit unit2 = new Unit(location2, ValidName, 0, 0, 0, 0);
		
		unit1.attack(unit2);
		unit1.advanceTime(0.1);
		assertEquals(0, unit1.getOrientation(), Util.DEFAULT_EPSILON);
		assertTrue(unit1.isAttacking());
		
		for (int i = 1; i<9; i++) {
		unit1.advanceTime(0.1);
		}
		
		assertTrue(unit1.isAttacking());
		
		unit1.advanceTime(0.1);
		
		assertFalse(unit1.isAttacking());

		unit2.defend(unit1);
		
		assertTrue(	(	(unit2.getLocation().get(0) == location2.get(0)+0.5) && 
						(unit2.getLocation().get(1) == location2.get(1)+0.5) && 
						(unit2.getLocation().get(2) == location2.get(2)+0.5) &&
						(unit2.getHitpoints() == unit2.getMaxHitpointsStamina())	)
			||
					(	(unit2.getLocation().get(0) == location2.get(0)+0.5) && 
						(unit2.getLocation().get(1) == location2.get(1)+0.5) && 
						(unit2.getLocation().get(2) == location2.get(2)+0.5) &&
						(unit2.getHitpoints() <= unit2.getMaxHitpointsStamina())	)
			||
					(	(unit2.getLocation().get(0) != location2.get(0)+0.5) ||
						(unit2.getLocation().get(1) != location2.get(1)+0.5) || 
						(unit2.getLocation().get(2) != location2.get(2)+0.5)		));
	}
	
	@Test (expected=IllegalAttackPosititonException.class)
	public void fight_IllegalAttPos() throws Exception {
		List <Integer> location1 = new ArrayList<Integer>();
		location1.add(10); location1.add(10); location1.add(10);
		Unit unit1 = new Unit(location1, ValidName, 0, 0, 0, 0);
		
		List <Integer> location2 = new ArrayList<Integer>();
		location2.add(12); location2.add(10); location2.add(10);
		Unit unit2 = new Unit(location2, ValidName, 0, 0, 0, 0);
		
		unit1.attack(unit2);
	}
}
