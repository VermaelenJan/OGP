import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import hillbillies.model.IllegalPositionException;
import hillbillies.model.Unit;
import ogp.framework.util.Util;

public class UnitTest {

	@Test
	public void constructor_Legal() throws IllegalPositionException {
		List <Double> location = new ArrayList<Double>();
		location.add((double) 10); location.add((double) 20); location.add((double) 30);
		Unit unit = new Unit(location, null, 0, 0, 0, 0, 0, 0, 0);
		List<Double> position = unit.getLocation();
		assertEquals(10, position.get(0), Util.DEFAULT_EPSILON);
		assertEquals(20, position.get(1), Util.DEFAULT_EPSILON);
		assertEquals(30, position.get(2), Util.DEFAULT_EPSILON);
	}

	@Test (expected=IllegalPositionException.class)
	public void constructor_Illegal_OutOfWorld_Pos() throws IllegalPositionException {
		List<Double> location = new ArrayList<Double>();
		location.add((double) 0); location.add((double) 0); location.add((double) 49.001);
		new Unit(location, null, 0, 0, 0, 0, 0, 0, 0);
	}
	
	@Test (expected=IllegalPositionException.class)
	public void constructor_Illegal_OutOfWorld_Neg() throws IllegalPositionException {
		List<Double> location = new ArrayList<Double>();
		location.add((double) 10); location.add((double) 10); location.add((double) -0.001);
		new Unit(location, null, 0, 0, 0, 0, 0, 0, 0);
	}	
	
	@Test
	public void getOccupiedCube_Correct_1() throws IllegalPositionException {
		List<Double> location = new ArrayList<Double>();
		 location.add((double) 0.9); location.add((double) 1.9); location.add((double) 2.9);
		Unit unit = new Unit(location, null, 0, 0, 0, 0, 0, 0, 0);
		List<Integer> cube = unit.getOccupiedCube();
		assertEquals(0, cube.get(0), Util.DEFAULT_EPSILON);
		assertEquals(1, cube.get(1), Util.DEFAULT_EPSILON);
		assertEquals(2, cube.get(2), Util.DEFAULT_EPSILON);

	}
	
	@Test
	public void getOccupiedCube_Correct_2() throws IllegalPositionException {
		List<Double> location = new ArrayList<Double>();
		location.add((double) 0.1); location.add((double) 1.1); location.add((double) 2.1);
		Unit unit = new Unit(location, null, 0, 0, 0, 0, 0, 0, 0);
		List<Integer> cube = unit.getOccupiedCube();
		assertEquals(0, cube.get(0), Util.DEFAULT_EPSILON);
		assertEquals(1, cube.get(1), Util.DEFAULT_EPSILON);
		assertEquals(2, cube.get(2), Util.DEFAULT_EPSILON);
		
	}
	
	@Test
	public  void getLocation_Correct() throws IllegalPositionException {
		List<Double> location = new ArrayList<Double>();
		 location.add((double) 49); location.add((double) 10.001); location.add((double) 0);
		Unit unit = new Unit(location, null, 0, 0, 0, 0, 0, 0, 0);
		assertEquals(unit.getLocation().get(0), 49, Util.DEFAULT_EPSILON);
		assertEquals(unit.getLocation().get(1), 10.001, Util.DEFAULT_EPSILON);
		assertEquals(unit.getLocation().get(2), 0, Util.DEFAULT_EPSILON);
	}
}
