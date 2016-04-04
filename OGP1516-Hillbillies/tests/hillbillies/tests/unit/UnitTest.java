package hillbillies.tests.unit;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.Cube;
import hillbillies.model.CubeType;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.model.exceptions.IllegalAdjacentPositionException;
import hillbillies.model.exceptions.IllegalAdvanceTimeException;
import hillbillies.model.exceptions.IllegalAttackPosititonException;
import hillbillies.model.exceptions.IllegalNameException;
import hillbillies.model.exceptions.IllegalPositionException;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import ogp.framework.util.Util;

/**
 *
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.0
 * based on black-box testing
 */
public class UnitTest {
	
	private static String ValidName = "This 's a \"test\"";
	private static int[] ValidLocation = {0, 2, 4};
	private static Unit testUnit;
	private static Unit validUnit;
	private static World smallWorld;

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception {
		smallWorld = createSmallWorld();
		
		validUnit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, smallWorld);
		testUnit = new Unit(ValidLocation, ValidName, 60, 50, 70, 90, smallWorld);
	}
	
	public World createSmallWorld() {
		DefaultTerrainChangeListener defaultTerrainChangeListener = new DefaultTerrainChangeListener();
		Cube[][][] worldCubes = new Cube[5][5][5];
		for (int xIndex = 0; xIndex<worldCubes.length; xIndex++) {
			for (int yIndex = 0; yIndex<worldCubes[0].length; yIndex++) {
				for (int zIndex = 0; zIndex<worldCubes[0][0].length; zIndex++) {
					int[] position = {xIndex, yIndex, zIndex};
					Cube cube = new Cube(position, CubeType.AIR);
					worldCubes[xIndex][yIndex][zIndex] = cube;
				}	
			}	
		}
		int[] pos1 = {0, 2, 3};
		Cube cube1 = new Cube(pos1, CubeType.ROCK);
		worldCubes[0][2][3] = cube1;
		int[] pos2 = {0, 3, 3};
		Cube cube2 = new Cube(pos2, CubeType.ROCK);
		worldCubes[0][3][3] = cube2;
		smallWorld = new World(worldCubes, defaultTerrainChangeListener);
		return smallWorld;
	}
	
	// Position tests

	@Test
	public void constructor_Legal() throws Exception {
		int[] location = {0, 2, 4};
		Unit unit = new Unit(location, ValidName, 0, 0, 0, 0, smallWorld);
		double[] position = unit.getLocation();
		assertEquals(0.5, position[0], Util.DEFAULT_EPSILON);
		assertEquals(2.5, position[1], Util.DEFAULT_EPSILON);
		assertEquals(4.5, position[2], Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void constructor_EdgeWorld() throws Exception {
		int[] location = {4, 4, 0};
		Unit unit = new Unit(location, ValidName, 0, 0, 0, 0, smallWorld);
		double[] position = unit.getLocation();
		assertEquals(4.5, position[0], Util.DEFAULT_EPSILON);
		assertEquals(4.5, position[1], Util.DEFAULT_EPSILON);
		assertEquals(0.5, position[2], Util.DEFAULT_EPSILON);
	}

	@Test (expected=IllegalPositionException.class)
	public void constructor_OutOfWorldPos() throws Exception {
		int[] location = {0, 0, 5};
		new Unit(location, ValidName, 0, 0, 0, 0, smallWorld);
	}
	
	@Test (expected=IllegalPositionException.class)
	public void constructor_OutOfWorldNeg() throws Exception {
		int[] location = {0, 0, -1};
		new Unit(location, ValidName, 0, 0, 0, 0, smallWorld);
	}	

	
	// Name tests
	
	@Test
	public void constructor_LegalName() throws Exception {
		new Unit(ValidLocation, ValidName, 0, 0, 0, 0, smallWorld);
	}
	
	@Test
	public void constructor_LegalNameSetNGet() throws Exception {
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, smallWorld);
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
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, smallWorld);
		assertEquals(25,unit.getWeight(),Util.DEFAULT_EPSILON);
		assertEquals(25,unit.getStrength(),Util.DEFAULT_EPSILON);
		assertEquals(25,unit.getToughness(),Util.DEFAULT_EPSILON);
		assertEquals(25,unit.getAgility(),Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void construction_PropertiesMax() throws Exception {
		Unit unit = new Unit(ValidLocation, ValidName, 4000, 3989, 2045, 20000, smallWorld);
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
		Unit unit = new Unit(location, ValidName, 60, 50, 70, 90, smallWorld);
		assertEquals(108,unit.getStamina(),Util.DEFAULT_EPSILON);
		unit.startSprinting();
		int[] target = {4, 4, 0};
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

		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, smallWorld);
		assertFalse(unit.isActualMoving());
		unit.moveToAdjacent(0, 1, 0);
		unit.advanceTime(0.001);
		assertTrue(unit.isActualMoving());
		for (int i = 1; i<10; i++) {
			try {
				unit.advanceTime(0.1);
			} catch (IllegalAdvanceTimeException e) {}
		}
		assertFalse(unit.isActualMoving());
		List <Double> expectedLocation = new ArrayList <Double>();
		expectedLocation.add(0.5);expectedLocation.add(3.5);expectedLocation.add(4.5);
		assertEquals(expectedLocation.get(0), unit.getLocation()[0], Util.DEFAULT_EPSILON);
		assertEquals(expectedLocation.get(1), unit.getLocation()[1], Util.DEFAULT_EPSILON);
		assertEquals(expectedLocation.get(2), unit.getLocation()[2], Util.DEFAULT_EPSILON);
	}
	
	@Test (expected=IllegalAdjacentPositionException.class)
	public void moveToAdjacent_AdjacentException() throws Exception {
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, smallWorld);
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
		int[] location = {0, 2, 4};
		Unit unit = new Unit(location, ValidName, 0, 0, 0, 0, smallWorld);
		
		int[] target = {0, 3, 4};
		
		assertEquals(0.5, unit.getLocation()[0], Util.DEFAULT_EPSILON);
		assertEquals(2.5, unit.getLocation()[1], Util.DEFAULT_EPSILON);
		assertEquals(4.5, unit.getLocation()[2], Util.DEFAULT_EPSILON);

		unit.moveTo(target);
		unit.advanceTime(0.1);
		
		assertEquals(0.5, unit.getLocation()[0], Util.DEFAULT_EPSILON);
		assertNotEquals(2.5, unit.getLocation()[1], Util.DEFAULT_EPSILON);
		assertEquals(4.5, unit.getLocation()[2], Util.DEFAULT_EPSILON);

		assertEquals(0, (int) unit.getOccupiedCube()[0]);
		assertEquals(2, (int) unit.getOccupiedCube()[1]);
		assertEquals(4, (int) unit.getOccupiedCube()[2]);
				
		for (int i = 1; i<10; i++) {
			unit.advanceTime(0.1);
		}
		
		assertEquals((double) 3.5, unit.getLocation()[1], Util.DEFAULT_EPSILON);
	}
	
	@Test (expected=IllegalPositionException.class)
	public void moveTo_PositionException() throws Exception {
		int[] target = {0, 0, 50};
		Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0);
		unit.moveTo(target);
		
	}
	
	
	// Speed tests
	
	@Test
	public void getSpeed_Walk() {
		int[] target = {0, 3, 4}; 
		testUnit.moveTo(target);
		assertEquals(1.5*(50.0+70.0)/(200.0*60.0/100.0), testUnit.getCurrentSpeedMagShow(), Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void getSpeed_Sprint() {
		int[] target = {0, 3, 4}; 
		testUnit.moveTo(target);
		testUnit.startSprinting();
		assertEquals(2*1.5*(50.0+70.0)/(200.0*60.0/100.0), testUnit.getCurrentSpeedMagShow(), Util.DEFAULT_EPSILON);
	}
	
	// Rest test (eg. stamina)
	
	@Test
	public void rest_Check() {
		int[] target = {0, 3, 4};
		validUnit.moveTo(target);
		validUnit.startSprinting();
		validUnit.advanceTime(0.1);
		assertTrue(validUnit.isSprinting());
		for (int i = 1; i<10; i++) {
			validUnit.advanceTime(0.1);
		}
		assertFalse(validUnit.isActualMoving());
		assertNotEquals(validUnit.getMaxHitpointsStamina(), validUnit.getStamina(), Util.DEFAULT_EPSILON);
		validUnit.moveTo(validUnit.getOccupiedCube());
		validUnit.rest();
		double prevStamina = validUnit.getStamina();
		validUnit.advanceTime(0.1);
		assertEquals(prevStamina, validUnit.getStamina(), Util.DEFAULT_EPSILON);
		validUnit.advanceTime(0.1);
		assertEquals((double) prevStamina + (double) validUnit.getToughness()/100, validUnit.getStamina(), Util.DEFAULT_EPSILON);
		
		for (int i = 1; i<13; i++) {
			validUnit.advanceTime(0.18);
			assertTrue(validUnit.getMaxHitpointsStamina() > (int) validUnit.getStamina());
		}
		validUnit.advanceTime(0.18);
		assertEquals(validUnit.getMaxHitpointsStamina(), (int) validUnit.getStamina());
		
	}
	
	// Rest oneHP test
	@Test
	public void rest_OneHPRestTimeCheck() {
		int[] target = {0, 3, 4};
		validUnit.moveTo(target);
		validUnit.startSprinting();
		for (int i = 1; i<10; i++) {
			validUnit.advanceTime(0.1);
		}
		assertNotEquals(validUnit.getMaxHitpointsStamina(), validUnit.getStamina(), Util.DEFAULT_EPSILON);
		
		float timeForOneHP = (float) ((float) 1/(float)(validUnit.getToughness()/200.0/0.2));
		validUnit.rest();
		while (timeForOneHP > 0.1) {
			timeForOneHP-=0.1;
			validUnit.advanceTime(0.1);
		}
		int[] target2 = {0, 2, 4};
		validUnit.moveTo(target2);
		assertFalse(validUnit.isActualMoving());
		assertTrue(validUnit.isResting());
		validUnit.work();
		assertTrue(validUnit.isResting());
		assertFalse(validUnit.isWorkingShow());
		validUnit.advanceTime(0.1);
		int[] workCube = {0, 2, 3}; 
		validUnit.workAt(workCube);
		assertTrue(validUnit.isWorkingShow());
		assertFalse(validUnit.isResting());
		
		validUnit.rest();
		assertTrue(validUnit.isResting());
		
		int[] location = {validUnit.getOccupiedCube()[0], validUnit.getOccupiedCube()[1], validUnit.getOccupiedCube()[2]};
		Unit unit = new Unit(location, ValidName, 0, 0, 0, 0, smallWorld);
		
		smallWorld.addUnit(validUnit);
		smallWorld.addUnit(validUnit);
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
		int[] location = {0,0,0};
		Unit unit = new Unit(location, ValidName, 60, 50, 70, 90);
		assertEquals(Math.PI/2,unit.getOrientation(), Util.DEFAULT_EPSILON);
		unit.moveToAdjacent(0, 1, 0);
		unit.advanceTime(0.1);
		assertEquals(Math.PI/2, unit.getOrientation(), Util.DEFAULT_EPSILON);
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
		int[] location1 = {0, 0, 0};
		Unit unit1 = new Unit(location1, ValidName, 0, 0, 0, 0);
		smallWorld.addUnit(unit1);
		int[] location2 = {0, 1, 0};
		Unit unit2 = new Unit(location2, ValidName, 0, 0, 0, 0);
		smallWorld.addUnit(unit2);
		
		unit1.attack(unit2);
		unit1.advanceTime(0.1);
		assertEquals(Math.PI/2, unit1.getOrientation(), Util.DEFAULT_EPSILON);
		assertTrue(unit1.isAttacking());
		
		for (int i = 1; i<9; i++) {
		unit1.advanceTime(0.1);
		}
		
		assertTrue(unit1.isAttacking());
		
		unit1.advanceTime(0.1);
		
		assertFalse(unit1.isAttacking());

		boolean blocked = false; boolean hitted = false; boolean dodged = false;
		while (!(blocked && hitted && dodged)){ 
			boolean oneBlocked = false; boolean oneHitted = false; boolean oneDodged = false;
			Unit unit3 = new Unit(location2, ValidName, 0, 0, 0, 0);
			unit3.defend(unit1);
			
			if (	(unit3.getLocation()[0] == location2[0] + 0.5) && 
					(unit3.getLocation()[1] == location2[1] + 0.5) && 
					(unit3.getLocation()[2] == location2[2] + 0.5) &&
					(unit3.getHitpoints() == unit3.getMaxHitpointsStamina())	
				){
				oneBlocked = true;
				blocked = true;
				}
			
			else if (	(unit3.getLocation()[0] == location2[0] + 0.5) && 
						(unit3.getLocation()[1] == location2[1] + 0.5) && 
						(unit3.getLocation()[2] == location2[2] + 0.5) &&
 						(unit3.getHitpoints() <= unit2.getMaxHitpointsStamina())
 				){
				oneHitted = true;
				hitted = true;
				}
			
			else if(	(unit3.getLocation()[0] != location2[0] + 0.5) ||
						(unit3.getLocation()[1] != location2[1] + 0.5) || 
						(unit3.getLocation()[2] != location2[2] + 0.5)
				){
				oneDodged = true;
				dodged = true;
				}
			
			assertTrue(oneBlocked ^ oneHitted ^ oneDodged);
		}
		assertTrue(blocked && hitted && dodged);
	}
	
	@Test (expected=IllegalAttackPosititonException.class)
	public void fight_IllegalAttPos() throws Exception {
		int[] location1 = {0, 0, 0};
		Unit unit1 = new Unit(location1, ValidName, 0, 0, 0, 0, smallWorld);
		
		int[] location2 = {0, 2, 0};
		Unit unit2 = new Unit(location2, ValidName, 0, 0, 0, 0, smallWorld);
		
		unit1.attack(unit2);
	}
	
	
	// Work test
	
	@Test
	public void work_Check() {
		assertFalse(validUnit.isWorkingShow());
		int[] cubeWork = {0, 3, 3};
		validUnit.workAt(cubeWork);
		assertTrue(validUnit.isWorkingShow());
		
		float timeToWork = (float) 500/validUnit.getStrength();
		
		while (timeToWork >= 0.15) {
			timeToWork -= 0.15;
			validUnit.advanceTime(0.15);
			assertTrue(validUnit.isWorkingShow());
		}
		
		validUnit.advanceTime(timeToWork-0.0001);
		assertTrue(validUnit.isWorkingShow());
		
		validUnit.advanceTime(0.0001);
		assertFalse(validUnit.isWorkingShow());
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
		boolean working = false; boolean resting = false; boolean moving = false; boolean attacking = false;
		while (!(working && resting && moving && attacking)){
			smallWorld = createSmallWorld();
			Unit unit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, smallWorld);
			validUnit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, smallWorld);
			int[] target = {0,3,4};
			unit.moveTo(target);
			unit.startSprinting();
			for (int j = 1; j<20; j++) {
			smallWorld.advanceTime(0.2);
			}
			assertTrue(Arrays.equals(target, unit.getOccupiedCube()));
			assertFalse(unit.isActualMoving()||unit.isResting()||unit.isWorkingShow()||unit.isAttacking());
			unit.startDefaultBehaviour();
			assertTrue(unit.isDefaultBehaviourEnabled());
			
			while (!(unit.isWorkingShow() || unit.isActualMoving() || unit.isResting() || unit.isAttacking())) {
				smallWorld.advanceTime(0.01);
			}

			if (!working){
				working = unit.isWorkingShow();
			}
			if (!resting){
				resting = unit.isResting();
			}
			if (!moving){
				moving = unit.isActualMoving();
			}
			if (!attacking){
				attacking = unit.isAttacking();
			}
			assertTrue((unit.isWorkingShow() || unit.isActualMoving() || unit.isResting() || unit.isAttacking()));
		}
	assertTrue(working && resting && moving && attacking);
	}
}
