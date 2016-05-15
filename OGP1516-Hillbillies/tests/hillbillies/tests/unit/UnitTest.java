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
import hillbillies.model.exceptions.IllegalFightFactionException;
import hillbillies.model.exceptions.IllegalNameException;
import hillbillies.model.exceptions.IllegalPositionException;
import hillbillies.model.exceptions.IllegalWorkPositionException;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import ogp.framework.util.Util;

/**
 *
 * @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
 * @version 1.8
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
	
	private World createSmallWorld() {
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
		World temp = new World(worldCubes, defaultTerrainChangeListener);
		return temp;
	}
	
	@Test
	public void getAndSetWorldTest() throws Exception {
		int[] location = {0, 2, 4};
		Unit unit = new Unit(location, ValidName, 0, 0, 0, 0, smallWorld);
		assertEquals(unit.getWorld(), smallWorld);
		World newSmallWorld = createSmallWorld();
		assertNotEquals(smallWorld, newSmallWorld);
		unit.setWorld(newSmallWorld);
		assertNotEquals(smallWorld, unit.getWorld());
		assertEquals(newSmallWorld, unit.getWorld());
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
	
	@Test
	public void falling() {
		Unit unit = new Unit(ValidLocation, ValidName, 90, 90, 90, 90, smallWorld); 

		smallWorld.setCubeType(0, 2, 3, CubeType.AIR);
		smallWorld.setCubeType(0, 3, 3, CubeType.AIR);
		smallWorld.advanceTime(0.1);
		
		assertEquals(3, unit.getCurrentSpeedMagShow(), Util.DEFAULT_EPSILON);
		unit.startSprinting();
		assertFalse(unit.isSprinting());
		
		smallWorld.setCubeType(0, 3, 3, CubeType.ROCK);

		unit.moveTo(new int[] {0, 3, 4});
		double first = unit.getLocation()[2];
		smallWorld.advanceTime(0.1);
		double next = unit.getLocation()[2];
		assertTrue(next < first);
		
		Unit unit2 = new Unit(new int[] {0, 3, 4}, ValidName, 90, 90, 90, 90, smallWorld); 
		unit.attack(unit2);
		assertFalse(unit.isAttacking());
		
		while (unit.getLocation()[2] != 0.5) {
			int prevZ = unit.getOccupiedCube()[2]; double prevHp = unit.getHitpoints();
			smallWorld.advanceTime(0.1);
			if (unit.getOccupiedCube()[2] < prevZ) {
				assertEquals(prevHp-10, unit.getHitpoints(),  Util.DEFAULT_EPSILON);
			}			
		}
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
		validUnit.setWorld(smallWorld);
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
		unit1.setWorld(smallWorld);
		int[] location2 = {0, 1, 0};
		Unit unit2 = new Unit(location2, ValidName, 0, 0, 0, 0);
		unit2.setWorld(smallWorld);
		
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
			int currDefExp = unit3.getExperience();
			int currAttExp = unit1.getExperience();
			
			int hpBeforeDef = (int) unit3.getHitpoints();
			unit3.defend(unit1);
			
			if (	(unit3.getLocation()[0] == location2[0] + 0.5) && 
					(unit3.getLocation()[1] == location2[1] + 0.5) && 
					(unit3.getLocation()[2] == location2[2] + 0.5) &&
					(unit3.getHitpoints() == hpBeforeDef)	
				){
				oneBlocked = true;
				blocked = true;
				assertTrue(unit3.getExperience() == currDefExp+20);
				assertTrue(unit1.getExperience() == currAttExp);
				}
			
			else if (	(unit3.getLocation()[0] == location2[0] + 0.5) && 
						(unit3.getLocation()[1] == location2[1] + 0.5) && 
						(unit3.getLocation()[2] == location2[2] + 0.5) &&
 						(unit3.getHitpoints() < hpBeforeDef)
 				){
				oneHitted = true;
				hitted = true;
				assertTrue(unit3.getExperience() == currDefExp);
				assertTrue(unit1.getExperience() == currAttExp+20);
				}
			
			else if(	(unit3.getLocation()[0] != location2[0] + 0.5) ||
						(unit3.getLocation()[1] != location2[1] + 0.5) || 
						(unit3.getLocation()[2] != location2[2] + 0.5)
				){
				oneDodged = true;
				dodged = true;
				assertTrue(unit3.getExperience() == currDefExp+20);
				assertTrue(unit1.getExperience() == currAttExp);
				}
			else {
				assertTrue(false);
			}
			
			assertTrue(oneBlocked ^ oneHitted ^ oneDodged);
		}
		assertTrue(blocked && hitted && dodged);
	}
	
	@Test
	public void defendAgainstMultiple() {
		while (smallWorld.getAllUnits().size() != 10 && Arrays.equals(testUnit.getOccupiedCube(), new int [] {0, 2, 4})) {
			validUnit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, smallWorld);
			try {
				validUnit.attack(testUnit);
				testUnit.defend(validUnit);
			}catch(IllegalFightFactionException e) {/**do nothing**/}
		}
		smallWorld.advanceTime(0.1);
	}
	
	@Test (expected=IllegalAttackPosititonException.class)
	public void fight_IllegalAttPos() throws Exception {
		int[] location1 = {0, 0, 0};
		Unit unit1 = new Unit(location1, ValidName, 0, 0, 0, 0, smallWorld);
		
		int[] location2 = {0, 2, 0};
		Unit unit2 = new Unit(location2, ValidName, 0, 0, 0, 0, smallWorld);
		
		unit1.attack(unit2);
	}
	
	@Test (expected=IllegalFightFactionException.class)
	public void sameFactionFight() throws Exception {
		while (smallWorld.getAllUnits().size() != 6) {
			validUnit = new Unit(ValidLocation, ValidName, 0, 0, 0, 0, smallWorld);
		}
		
		for (Unit unit1 : smallWorld.getAllUnits()) {
			for (Unit unit2 : smallWorld.getAllUnits()) {
				if (!unit1.equals(unit2) && unit1.getFaction().equals(unit2.getFaction())) {
					unit1.attack(unit2);
				}
			}
		}
	}
	
	
	// Work test
	
	@Test
	public void work1_check() {
		work3_check();
		work4_check();
	}
	
	@Test
	public void work2_check() {
		while (smallWorld.getBouldersWorld().size() == 0 || smallWorld.getLogsWorld().size() == 0) {
			DefaultTerrainChangeListener defaultTerrainChangeListener = new DefaultTerrainChangeListener();
			Cube[][][] worldCubes = new Cube[3][3][20];
			for (int xIndex = 0; xIndex<worldCubes.length; xIndex++) {
				for (int yIndex = 0; yIndex<worldCubes[0].length; yIndex++) {
					for (int zIndex = 0; zIndex<worldCubes[0][0].length; zIndex++) {
						int[] position = {xIndex, yIndex, zIndex};
						Cube cube = new Cube(position, CubeType.AIR);
						worldCubes[xIndex][yIndex][zIndex] = cube;
					}	
				}	
			}
			int[] pos1 = {1, 1, 18};
			Cube cube1 = new Cube(pos1, CubeType.ROCK);
			worldCubes[1][1][18] = cube1;
			int[] pos2 = {1, 1, 16};
			Cube cube2 = new Cube(pos2, CubeType.WOOD);
			worldCubes[1][1][16] = cube2;
			smallWorld = new World(worldCubes, defaultTerrainChangeListener);
			smallWorld.advanceTime(0.2);
		}
		
		Unit unit = new Unit(new int[] {1, 1, 0}, ValidName, 25, 25, 25, 25, smallWorld);
		for (int i = 0; i<=100; i++) {
			smallWorld.advanceTime(0.1);
		}
		
		smallWorld.setCubeType(1, 1, 0, CubeType.WORKSHOP);
		
		assertTrue(Arrays.equals(smallWorld.getBouldersWorld().iterator().next().getLocation(), unit.getLocation()));
		assertTrue(Arrays.equals(smallWorld.getLogsWorld().iterator().next().getLocation(), unit.getLocation()));
		
		int prevExp = unit.getExperience();
		int prevWeight = unit.getWeight();
		int prevToughness = unit.getToughness();
		
		unit.workAt(unit.getOccupiedCube());
		for (int i = 0; i<=200; i++) {
			smallWorld.advanceTime(0.1);
		}
		
		assertEquals(prevExp + 10, unit.getExperience());
		
		assertEquals(0, smallWorld.getBouldersWorld().size());
		assertEquals(0, smallWorld.getLogsWorld().size());
		
		assertTrue(unit.getToughness() > prevToughness);
		assertTrue(unit.getWeight() > prevWeight);

	}
	
	@Test
	public void work3_check() {
		while (smallWorld.getBouldersWorld().size() == 0 || smallWorld.getLogsWorld().size() == 0) {
			DefaultTerrainChangeListener defaultTerrainChangeListener = new DefaultTerrainChangeListener();
			Cube[][][] worldCubes = new Cube[3][3][20];
			for (int xIndex = 0; xIndex<worldCubes.length; xIndex++) {
				for (int yIndex = 0; yIndex<worldCubes[0].length; yIndex++) {
					for (int zIndex = 0; zIndex<worldCubes[0][0].length; zIndex++) {
						int[] position = {xIndex, yIndex, zIndex};
						Cube cube = new Cube(position, CubeType.AIR);
						worldCubes[xIndex][yIndex][zIndex] = cube;
					}	
				}	
			}
			int[] pos1 = {1, 1, 18};
			Cube cube1 = new Cube(pos1, CubeType.ROCK);
			worldCubes[1][1][18] = cube1;
			int[] pos2 = {1, 1, 16};
			Cube cube2 = new Cube(pos2, CubeType.WOOD);
			worldCubes[1][1][16] = cube2;
			smallWorld = new World(worldCubes, defaultTerrainChangeListener);
			smallWorld.advanceTime(0.2);
		}
		
		for (int i = 0; i<=100; i++) {
			smallWorld.advanceTime(0.1);
		}
		
		Unit unit = new Unit(new int[] {1, 1, 0}, ValidName, 0, 0, 0, 0, smallWorld);
		
		int prevExp = unit.getExperience();
		
		unit.workAt(unit.getOccupiedCube());
		
		for (int i = 0; i<=200; i++) {
			smallWorld.advanceTime(0.1);
		}
		
		assertEquals(prevExp+10, unit.getExperience());
		prevExp = unit.getExperience();
		
		assertTrue(unit.isCarryingBoulder());
		assertFalse(unit.isCarryingLog());
		assertEquals(0, smallWorld.getBouldersWorld().size());
		
		unit.workAt(new int[] {0, 0, 0});
		
		for (int i = 0; i<=200; i++) {
			smallWorld.advanceTime(0.1);
		}

		assertFalse(unit.isCarryingBoulder());
		assertEquals(prevExp + 10, unit.getExperience());
		assertEquals(1, smallWorld.getBouldersWorld().size());
	}
	
	@Test
	public void work4_check() {
		while (smallWorld.getLogsWorld().size() == 0) {
			DefaultTerrainChangeListener defaultTerrainChangeListener = new DefaultTerrainChangeListener();
			Cube[][][] worldCubes = new Cube[3][3][20];
			for (int xIndex = 0; xIndex<worldCubes.length; xIndex++) {
				for (int yIndex = 0; yIndex<worldCubes[0].length; yIndex++) {
					for (int zIndex = 0; zIndex<worldCubes[0][0].length; zIndex++) {
						int[] position = {xIndex, yIndex, zIndex};
						Cube cube = new Cube(position, CubeType.AIR);
						worldCubes[xIndex][yIndex][zIndex] = cube;
					}	
				}	
			}
			int[] pos2 = {1, 1, 16};
			Cube cube2 = new Cube(pos2, CubeType.WOOD);
			worldCubes[1][1][16] = cube2;
			smallWorld = new World(worldCubes, defaultTerrainChangeListener);
			smallWorld.advanceTime(0.2);
		}
		
		for (int i = 0; i<=100; i++) {
			smallWorld.advanceTime(0.1);
		}
		
		Unit unit = new Unit(new int[] {1, 1, 0}, ValidName, 0, 0, 0, 0, smallWorld);
		
		int prevExp = unit.getExperience();
		
		unit.workAt(unit.getOccupiedCube());
		
		for (int i = 0; i<=200; i++) {
			smallWorld.advanceTime(0.1);
		}
		
		assertEquals(prevExp + 10, unit.getExperience());
		prevExp = unit.getExperience();
		
		assertTrue(unit.isCarryingLog());
		assertEquals(0, smallWorld.getLogsWorld().size());
		
		unit.workAt(new int[] {0, 0, 0});
		
		for (int i = 0; i<=200; i++) {
			smallWorld.advanceTime(0.1);
		}
		assertFalse(unit.isCarryingLog());
		assertEquals(prevExp + 10, unit.getExperience());
		assertEquals(1, smallWorld.getLogsWorld().size());
	}
	
	@Test
	public void work5_Check() {
		int startExp = validUnit.getExperience();
		
		assertFalse(validUnit.isWorkingShow());
		
		int[] cubeWork = {0, 3, 3};
		validUnit.workAt(cubeWork);
		assertTrue(validUnit.isWorkingShow());
		assertTrue(smallWorld.getCubeType(0, 3, 3) == CubeType.ROCK);
		
		float timeToWork = (float) 500/validUnit.getStrength();
		
		while (timeToWork >= 0.15) {
			timeToWork -= 0.15;
			validUnit.advanceTime(0.15);
			assertTrue(validUnit.isWorkingShow());
		}
		
		validUnit.advanceTime(timeToWork);
		assertFalse(validUnit.isWorkingShow());
		assertTrue(smallWorld.getCubeType(0, 3, 3) == CubeType.AIR);
		
		assertEquals(startExp + 10, validUnit.getExperience());
	}
		
	@Test(expected=IllegalWorkPositionException.class)
	public void illegalWork_check() throws Exception {
		validUnit.workAt(new int[] {0, 0, 4});
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
	
	// Experience test
	
	@Test
	public void experienceGetAndEarn() throws Exception {
		Unit unit = new Unit(ValidLocation, ValidName, 90, 90, 90, 90, smallWorld); 
		assertEquals(0, unit.getExperience());
		int unitsStrength = unit.getStrength();
		int unitsAgility = unit.getAgility();
		int unitsToughness = unit.getToughness();
		
		unit.moveTo(new int[] {0, 3, 4});
		for (int j = 1; j<20; j++) {
		smallWorld.advanceTime(0.1);
		}

		assertEquals(1, unit.getExperience());
		
		unit.workAt(new int[] {0, 3, 3});
		smallWorld.advanceTime(0.1);
		assertTrue(unit.isWorkingShow());
		unit.moveTo(new int[] {0, 2, 4});
		assertEquals(1,  unit.getExperience());
		
		unit.workAt(new int[] {0, 3, 3});
		while (unit.isWorkingShow()) {
			smallWorld.advanceTime(0.1);
		}
		
		assertEquals(11, unit.getExperience());
		
		assertTrue((unit.getAgility() == unitsAgility+1) ^ (unit.getStrength() == unitsStrength+1) ^ (unit.getToughness() == unitsToughness+1));
		
		unit.moveTo(new int[] {0, 2, 4});
		smallWorld.advanceTime(0.01);
		assertTrue(unit.isActualMoving()); 
		smallWorld.setCubeType(0, 2, 3, CubeType.AIR);
		smallWorld.setCubeType(0, 3, 3, CubeType.AIR);
		
		while (unit.getLocation()[2] != 0.5) {
			smallWorld.advanceTime(0.1);
		}
		
		assertEquals(11, unit.getExperience());
		
		fight();
		
		setUp();
		work1_check();
		setUp();
		work2_check();
		setUp();
		work3_check();
		setUp();
		work4_check();
		setUp();
		work5_Check();
	}
	
	
	// Death test
	
	@Test
	public void death() {
		while (smallWorld.getBouldersWorld().size() == 0) {
			DefaultTerrainChangeListener defaultTerrainChangeListener = new DefaultTerrainChangeListener();
			Cube[][][] worldCubes = new Cube[3][3][20];
			for (int xIndex = 0; xIndex<worldCubes.length; xIndex++) {
				for (int yIndex = 0; yIndex<worldCubes[0].length; yIndex++) {
					for (int zIndex = 0; zIndex<worldCubes[0][0].length; zIndex++) {
						int[] position = {xIndex, yIndex, zIndex};
						Cube cube = new Cube(position, CubeType.AIR);
						worldCubes[xIndex][yIndex][zIndex] = cube;
					}	
				}	
			}
			int[] pos1 = {1, 1, 18};
			Cube cube1 = new Cube(pos1, CubeType.ROCK);
			worldCubes[1][1][18] = cube1;
			int[] pos2 = {1, 1, 16};
			Cube cube2 = new Cube(pos2, CubeType.ROCK);
			worldCubes[1][1][16] = cube2;
			int[] pos3 = {0, 1, 16};
			Cube cube3 = new Cube(pos3, CubeType.ROCK);
			worldCubes[0][1][16] = cube3;
			smallWorld = new World(worldCubes, defaultTerrainChangeListener);
			smallWorld.advanceTime(0.2);
			
		}
		
		Unit unit = new Unit(new int[] {1, 1, 17}, ValidName, 0, 0, 0, 0, smallWorld);
		for (int i = 0; i<=20; i++) {
			smallWorld.advanceTime(0.1);
		}

		unit.workAt(unit.getOccupiedCube());
		for (int i = 0; i<=200; i++) {
			smallWorld.advanceTime(0.1);
		}
		assertTrue(unit.isCarryingBoulder());
		
		smallWorld.setCubeType(1, 1, 16, CubeType.AIR);
		smallWorld.setCubeType(0, 1, 16, CubeType.AIR);
		
		smallWorld.advanceTime(0.1);
		assertFalse(unit.isTerminated());
		assertEquals(0, smallWorld.getBouldersWorld().size());
		
		while (unit.getHitpoints() > 0) {
			smallWorld.advanceTime(0.1);
		}
		
		double[] deathpos = unit.getLocation();
		
		smallWorld.advanceTime(0);
		assertTrue(unit.isTerminated());
		assertEquals(1, smallWorld.getBouldersWorld().size());
		assertTrue(Arrays.equals(smallWorld.getBouldersWorld().iterator().next().getLocation(), deathpos));
		
		smallWorld.advanceTime(0.2);
		
		assertTrue(smallWorld.getBouldersWorld().iterator().next().getLocation()[2] < deathpos[2]);
		
		while (smallWorld.getBouldersWorld().iterator().next().getLocation()[2] != 0.5) {
			smallWorld.advanceTime(0.1);
		}
		
		assertTrue(Arrays.equals(smallWorld.getBouldersWorld().iterator().next().getLocation(), 
				new double[] {deathpos[0], deathpos[1], 0.5}));
	}
}
