package hillbillies.tests.unit;
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

/**
 *
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 * based on black-box testing
 */
public class UnitTest {
	
	private static String ValidName;
	private static int[] ValidLocation = {0, 24, 49};
	private static Unit testUnit;
	private static Unit validUnit;

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	ValidName = "This 's a \"test\"";
	}
	
	@Before
	public void setUp() throws Exception {
		validUnit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0);
		testUnit = new Unit(ValidLocation, ValidName, 60, 50, 70, 90);
	}
	
	
	// Position tests

	@Test
	public void constructor_Legal() throws Exception {
		int[] location = {10, 20, 30};
		Unit unit = new Unit(location, ValidName, 0, 0, 0, 0);
		double[] position = unit.getLocation();
		assertEquals(10.5, position[0], Util.DEFAULT_EPSILON);
		assertEquals(20.5, position[1], Util.DEFAULT_EPSILON);
		assertEquals(30.5, position[2], Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void constructor_EdgeWorld() throws Exception {
		int[] location = {0, 0, 49};
		Unit unit = new Unit(location, ValidName, 0, 0, 0, 0);
		double[] position = unit.getLocation();
		assertEquals(0.5, position[0], Util.DEFAULT_EPSILON);
		assertEquals(0.5, position[1], Util.DEFAULT_EPSILON);
		assertEquals(49.5, position[2], Util.DEFAULT_EPSILON);
	}

	@Test (expected=IllegalPositionException.class)
	public void constructor_OutOfWorldPos() throws Exception {
		int[] location = {0, 0, 50};
		new Unit(location, ValidName, 0, 0, 0, 0);
	}
	
	@Test (expected=IllegalPositionException.class)
	public void constructor_OutOfWorldNeg() throws Exception {
		int[] location = {10, 10, -1};
		new Unit(location, ValidName, 0, 0, 0, 0);
	}	

	
	// Name tests
	
	@Test
	public void constructor_LegalName() throws Exception {
		new Unit(ValidLocation, ValidName, 0, 0, 0, 0);
	}
	
	@Test
	public void constructor_LegalNameSetNGet() throws Exception {
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0);
		unit.setName("This is OK");
		assertEquals("This is OK", unit.getName());
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

		validUnit.setAgility(0);
		validUnit.setStrength(0);
		validUnit.setWeight(-20);
		assertEquals(1,validUnit.getWeight());
	}
	
	@Test
	public void setWeight_BelowConstr() throws Exception {
		validUnit.setWeight(-20);
		assertEquals(25,validUnit.getWeight(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setWeight_Below() throws Exception{
		validUnit.setStrength(50);
		validUnit.setAgility(50);
		validUnit.setWeight(-20);
		assertEquals(50, validUnit.getWeight(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setWeight_Betweenboundaries() throws Exception {
		validUnit.setWeight(70);
		assertEquals(70, validUnit.getWeight(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setWeight_MaxValue() throws Exception {
		validUnit.setWeight(200);
		assertEquals(200, validUnit.getWeight(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setWeight_Overflow() throws Exception {
		validUnit.setWeight(250);
		assertEquals(200, validUnit.getWeight(),Util.DEFAULT_EPSILON);
	}
	
	
	// Strength tests
	
	@Test
	public void setStrength_BelowConstr()  throws Exception {
		assertEquals(25, validUnit.getStrength(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setStrength_BelowAfterConstr()  throws Exception {
		validUnit.setStrength(-20);
		assertEquals(1, validUnit.getStrength(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setStrength_Betweenboundaries() throws Exception {
		validUnit.setStrength(70);
		assertEquals(70, validUnit.getStrength(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setStrength_MaxValue() throws Exception {
		validUnit.setStrength(200);
		assertEquals(200, validUnit.getStrength(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setStrength_Overflow() throws Exception {
		validUnit.setStrength(250);
		assertEquals(200, validUnit.getStrength(),Util.DEFAULT_EPSILON);
	
	}
	
	
	// Agility tests
	
	@Test
	public void setAgility_BelowConstr()  throws Exception {
		assertEquals(25, validUnit.getAgility(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setAgility_BelowAfterConstr()  throws Exception {
		validUnit.setAgility(-20);
		assertEquals(1, validUnit.getAgility(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setAgility_Betweenboundaries() throws Exception {
		validUnit.setAgility(70);
		assertEquals(70, validUnit.getAgility(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setAgility_MaxValue() throws Exception {
		validUnit.setAgility(200);
		assertEquals(200, validUnit.getAgility(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setAgility_Overflow() throws Exception {
		validUnit.setAgility(250);
		assertEquals(200, validUnit.getAgility(),Util.DEFAULT_EPSILON);
	
	}
	
	
	// Toughness tests 
	
	@Test
	public void setToughness_BelowConstr()  throws Exception {
		assertEquals(25, validUnit.getToughness(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setToughness_BelowAfterConstr()  throws Exception {
		validUnit.setToughness(-20);
		assertEquals(1, validUnit.getToughness(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setToughness_Betweenboundaries() throws Exception {
		validUnit.setToughness(70);
		assertEquals(70, validUnit.getToughness(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setToughness_MaxValue() throws Exception {
		validUnit.setToughness(200);
		assertEquals(200, validUnit.getToughness(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void setToughness_Overflow() throws Exception {
		validUnit.setToughness(250);
		assertEquals(200, validUnit.getToughness(),Util.DEFAULT_EPSILON);
	
	}
	
	
	// Hitpoints tests
	
	@Test
	public void getMaxHitpoints_Check(){
		assertEquals(200*60*90/100/100, testUnit.getMaxHitpointsStamina(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void getHitpoints_Check(){
		assertEquals(200*60*90/100/100, testUnit.getHitpoints(),Util.DEFAULT_EPSILON);
	}
	
	
	// Stamina tests
	
	@Test
	public void getMaxStamina_Check(){
		assertEquals(200*60*90/100/100, testUnit.getMaxHitpointsStamina(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void getCurrentStamina_Check(){
		assertEquals(200*60*90/100/100, testUnit.getStamina(),Util.DEFAULT_EPSILON); // (stamina == 108)
	}
	
	@Test
	public void getCurrentStamina_SprintCheck() {
		int[] location = {0, 0, 0};
		Unit unit = new Unit(location, ValidName, 60, 50, 70, 90);
		assertEquals(108,unit.getStamina(),Util.DEFAULT_EPSILON);
		unit.startSprinting();
		int[] target = {10, 20, 30};
		unit.moveTo(target);
		unit.advanceTime(0.1);
		assertEquals(107, unit.getStamina(),Util.DEFAULT_EPSILON);
		unit.advanceTime(0.2);
		assertEquals(105, unit.getStamina(),Util.DEFAULT_EPSILON);

	}
	
	
	// AdvanceTime exception tests (Normal behaviour implicitly checked) 
	
	@Test (expected=IllegalAdvanceTimeException.class)
	public void advanceTime_IllegalAdvanceTimeExceptionOverflow() {
		testUnit.advanceTime(0.21);
	}
	
	@Test (expected=IllegalAdvanceTimeException.class)
	public void advanceTime_IllegalAdvanceTimeExceptionNeg() {
		testUnit.advanceTime(-0.1);
	}
	
	
	// Movement tests
	
	@Test
	public void moveToAdjacent_Check() throws Exception {

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
		List <Double> expectedLocation = new ArrayList <Double>();
		expectedLocation.add(1.5);expectedLocation.add(24.5);expectedLocation.add(48.5);
		assertEquals(expectedLocation.get(0), unit.getLocation()[0], Util.DEFAULT_EPSILON);
		assertEquals(expectedLocation.get(1), unit.getLocation()[1], Util.DEFAULT_EPSILON);
		assertEquals(expectedLocation.get(2), unit.getLocation()[2], Util.DEFAULT_EPSILON);
	}
	
	@Test (expected=IllegalAdjacentPositionException.class)
	public void moveToAdjacent_AdjacentException() throws Exception {
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0);
		unit.moveToAdjacent(2, 0, -1);
	}
	
	@Test (expected=IllegalPositionException.class)
	public void moveToAdjacent_PositionException() throws Exception {
		int[] location = {0, 0, 0};
		Unit unit = new Unit(location, ValidName, 0, 0, 0, 0);
		unit.moveToAdjacent(1, 0, -1);
	}
	
	@Test
	public void sprint_Check(){
		assertFalse(validUnit.isSprinting());
		validUnit.startSprinting();
		assertTrue(validUnit.isSprinting());
		validUnit.stopSprinting();
		assertFalse(validUnit.isSprinting());
	}
	
	@Test
	public void moveTo_Check() throws Exception {
		int[] location = {10, 20, 30};
		Unit unit = new Unit(location, ValidName, 0, 0, 0, 0);
		
		int[] target = {12, 20, 30};
		
		assertEquals(10.5, unit.getLocation()[0], Util.DEFAULT_EPSILON);
		assertEquals(20.5, unit.getLocation()[1], Util.DEFAULT_EPSILON);
		assertEquals(30.5, unit.getLocation()[2], Util.DEFAULT_EPSILON);

		unit.moveTo(target);
		unit.advanceTime(0.1);

		assertEquals(10, (int) unit.getOccupiedCube()[0]);
		assertEquals(20, (int) unit.getOccupiedCube()[1]);
		assertEquals(30, (int) unit.getOccupiedCube()[2]);
		
		assertNotEquals(10.5, unit.getLocation()[0], Util.DEFAULT_EPSILON);
		assertEquals(20.5, unit.getLocation()[1], Util.DEFAULT_EPSILON);
		assertEquals(30.5, unit.getLocation()[2], Util.DEFAULT_EPSILON);
		
		for (int i = 1; i<200; i++) {
			unit.advanceTime(0.1);
		}
		
		assertEquals((double) 12.5, unit.getLocation()[0], Util.DEFAULT_EPSILON);
	}
	
	@Test (expected=IllegalPositionException.class)
	public void moveTo_PositionException() throws Exception {
		int[] target = {0, 0, 50};
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0);
		unit.moveTo(target);
		
	}
	
	
	// Speed tests
	
	@Test
	public void getSpeed_XYWalk() {
		int[] target = {24, 0, 49}; 
		testUnit.moveTo(target);
		assertEquals(1.5*(50.0+70.0)/(200.0*60.0/100.0), testUnit.getCurrentSpeedMagShow(), Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void getSpeed_XYSprint() {
		int[] target = {24, 0, 49}; 
		testUnit.moveTo(target);
		testUnit.startSprinting();
		assertEquals(2*1.5*(50.0+70.0)/(200.0*60.0/100.0), testUnit.getCurrentSpeedMagShow(), Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void getSpeed_ZWalkUp() {
		int[] location = {0,0,0};
		Unit unit = new Unit(location, ValidName, 60, 50, 70, 90);
		int[] target = {0, 0, 2}; 
		unit.moveTo(target);
		assertEquals(0.5*1.5*(50.0+70.0)/(200.0*60.0/100.0), unit.getCurrentSpeedMagShow(), Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void getSpeed_ZWalkDown() {
		int[] target = {0, 24, 40}; 
		validUnit.moveTo(target);
		assertEquals(1.2*1.5*(50.0+70.0)/(200.0*60.0/100.0), validUnit.getCurrentSpeedMagShow(), Util.DEFAULT_EPSILON);
	}
	
	public void getSpeed_ZSprintUp() {
		int[] location = {0,0,0};
		Unit unit = new Unit(location, ValidName, 60, 50, 70, 90);
		int[] target = {0, 0, 2}; 
		unit.moveTo(target);
		unit.startSprinting();
		assertEquals(2.0*0.5*1.5*(50.0+70.0)/(200.0*60.0/100.0), unit.getCurrentSpeedMagShow(), Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void getSpeed_XYZSprintDown() {
		int[] target = {24, 0, 0}; 
		validUnit.moveTo(target);
		validUnit.startSprinting();
		assertEquals(2.0*1.2*1.5*(50.0+70.0)/(200.0*60.0/100.0), validUnit.getCurrentSpeedMagShow(), Util.DEFAULT_EPSILON);
	}
	
	// Rest test (eg. stamina)
	
	@Test
	public void rest_Check() {
		int[] target = {49, 49, 49};
		validUnit.moveTo(target);
		validUnit.startSprinting();
		
		for (int i = 1; i<16; i++) {
			validUnit.advanceTime(0.1);
		}
		
		assertTrue(validUnit.isSprinting());
		validUnit.advanceTime(0.1);		
		assertFalse(validUnit.isSprinting());
		assertEquals(0, validUnit.getStamina(), Util.DEFAULT_EPSILON);
		validUnit.rest();
		validUnit.advanceTime(0.1);
		assertEquals(0, validUnit.getStamina(), Util.DEFAULT_EPSILON);
		validUnit.advanceTime(0.1);
		assertEquals((double) validUnit.getToughness()/100, validUnit.getStamina(), Util.DEFAULT_EPSILON);
		
		for (int i = 1; i<57; i++) {
			validUnit.advanceTime(0.18);
			assertTrue(validUnit.getMaxHitpointsStamina() > (int) validUnit.getStamina());
		}
		
		validUnit.advanceTime(0.18);
		assertEquals(validUnit.getMaxHitpointsStamina(), (int) validUnit.getStamina());
		
	}
	
	// Rest oneHP test
	@Test
	public void rest_OneHPRestTimeCheck() {
		int[] target = {49, 49, 49};
		validUnit.moveTo(target);
		validUnit.startSprinting();
		while (validUnit.getStamina() > 0) {
			validUnit.advanceTime(0.1);
		}
		assertEquals(0, validUnit.getStamina(), Util.DEFAULT_EPSILON);
		
		float timeForOneHP = (float) ((float) 1/(float)(validUnit.getToughness()/200.0/0.2));
		validUnit.rest();
		while (timeForOneHP >= 0.1) {
			timeForOneHP-=0.1;
			validUnit.advanceTime(0.1);
		}
		int[] target2 = {49, 0, 0};
		validUnit.moveTo(target2);
		assertFalse(validUnit.isActualMoving());
		assertTrue(validUnit.isResting());
		validUnit.work();
		assertTrue(validUnit.isResting());
		assertFalse(validUnit.isWorking());
		validUnit.advanceTime(0.1);
		validUnit.work();
		assertTrue(validUnit.isWorking());
		assertFalse(validUnit.isResting());
		
		validUnit.rest();
		
		int[] location = {validUnit.getOccupiedCube()[0], validUnit.getOccupiedCube()[1], validUnit.getOccupiedCube()[2]};
		Unit unit = new Unit(location, ValidName, 0, 0, 0, 0);
		
		validUnit.attack(unit);
		assertTrue(validUnit.isAttacking());
		assertFalse(validUnit.isResting());

		
	}
	
	
	// Orientation tests
		
	@Test
	public void orientation_MovingNCheck () throws Exception {
		assertEquals(Math.PI/2,validUnit.getOrientation(), Util.DEFAULT_EPSILON);
		validUnit.moveToAdjacent(0, -1, 0);
		validUnit.advanceTime(0.1);
		assertEquals(3*Math.PI/2, validUnit.getOrientation(), Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void orientation_MovingWheck () throws Exception {
		int[] location = {10,10,10};
		Unit unit = new Unit(location, ValidName, 60, 50, 70, 90);
		assertEquals(Math.PI/2,unit.getOrientation(), Util.DEFAULT_EPSILON);
		unit.moveToAdjacent(-1, 0, 0);
		unit.advanceTime(0.1);
		assertEquals(Math.PI, unit.getOrientation(), Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void orientation_MovingNECheck () throws Exception {
		assertEquals(Math.PI/2,validUnit.getOrientation(), Util.DEFAULT_EPSILON);
		validUnit.moveToAdjacent(1, -1, 0);
		validUnit.advanceTime(0.1);
		assertEquals(7*Math.PI/4, validUnit.getOrientation(), Util.DEFAULT_EPSILON);
	}
		
	
	//Attack tests
	
	@Test
	public void fight() throws Exception {
		int[] location1 = {10, 10, 10};
		Unit unit1 = new Unit(location1, ValidName, 0, 0, 0, 0);
		int[] location2 = {11, 10, 10};
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

		boolean blocked = false; boolean hitted = false; boolean dodged = false;
		for (int i = 1; i<1000; i++){
			
			Unit unit3 = new Unit(location2, ValidName, 0, 0, 0, 0);
			unit3.defend(unit1);
			
			if (	(unit3.getLocation()[0] == location2[0] + 0.5) && 
					(unit3.getLocation()[1] == location2[1] + 0.5) && 
					(unit3.getLocation()[2] == location2[2] + 0.5) &&
					(unit3.getHitpoints() == unit3.getMaxHitpointsStamina())	
				){
				blocked = true;
				}
			
			else if (	(unit3.getLocation()[0] == location2[0] + 0.5) && 
						(unit3.getLocation()[1] == location2[1] + 0.5) && 
						(unit3.getLocation()[2] == location2[2] + 0.5) &&
 						(unit3.getHitpoints() <= unit2.getMaxHitpointsStamina())
 				){
				hitted = true;
				}
			
			else if(	(unit3.getLocation()[0] != location2[0] + 0.5) ||
						(unit3.getLocation()[1] != location2[1] + 0.5) || 
						(unit3.getLocation()[2] != location2[2] + 0.5)
				){
				dodged = true;
				}
			
			if (i==1){
				assertTrue(blocked || hitted || dodged);
			}
		}
		assertTrue(blocked && hitted && dodged);
	}
	
	@Test (expected=IllegalAttackPosititonException.class)
	public void fight_IllegalAttPos() throws Exception {
		int[] location1 = {10, 10, 10};
		Unit unit1 = new Unit(location1, ValidName, 0, 0, 0, 0);
		
		int[] location2 = {12, 10, 10};
		Unit unit2 = new Unit(location2, ValidName, 0, 0, 0, 0);
		
		unit1.attack(unit2);
	}
	
	
	// Work test
	
	@Test
	public void work_Check() {
		assertFalse(validUnit.isWorking());
		validUnit.work();
		assertTrue(validUnit.isWorking());
		
		float timeToWork = (float) 500/validUnit.getStrength();
		
		while (timeToWork >= 0.15) {
			timeToWork -= 0.15;
			validUnit.advanceTime(0.15);
			assertTrue(validUnit.isWorking());
		}
		
		validUnit.advanceTime(timeToWork-0.0001);
		assertTrue(validUnit.isWorking());
		
		validUnit.advanceTime(0.0001);
		assertFalse(validUnit.isWorking());
	}
	
	
	// Default behaviour tests
	
	@Test
	public void defaultBehaviour_Check() {
	validUnit.startDefaultBehaviour();
	assertTrue(validUnit.isDefaultBehaviourEnabled());
	validUnit.stopDefaultBehaviour();
	assertFalse(validUnit.isDefaultBehaviourEnabled());
	}
	
	@Test
	public void defaultBehaviour_Posibilities() {
	boolean working = false; boolean resting = false; boolean moving = false;
	for (int i = 1; i<200; i++){
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0);
		int[] target = {2,24,49};
		unit.moveTo(target);
		unit.startSprinting();
		for (int j = 1; j<20; j++) {
		unit.advanceTime(0.2);
		}
		unit.startDefaultBehaviour();
		unit.advanceTime(0.001);
		if (!working){
			working = unit.isWorking();
		}
		if (!resting){
			resting = unit.isResting();
		}
		if (!moving){
			moving = unit.isActualMoving();
		}
		assertTrue((unit.isWorking() || unit.isActualMoving() || unit.isResting()));
		}
	assertTrue(working && resting && moving);
	}
	
}
