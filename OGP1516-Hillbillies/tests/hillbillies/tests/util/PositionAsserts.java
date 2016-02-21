package hillbillies.tests.util;

import org.junit.Assert;

import ogp.framework.util.Util;

public class PositionAsserts {

	public static void assertDoublePositionEquals(double x, double y, double z, double[] position) {
		Assert.assertArrayEquals(new double[] { x, y, z }, position, Util.DEFAULT_EPSILON);
	}

	public static void assertDoublePositionEquals(String message, double x, double y, double z, double[] position) {
		Assert.assertArrayEquals(message, new double[] { x, y, z }, position, Util.DEFAULT_EPSILON);
	}

	public static void assertPositionXEquals(String message, double x, double[] position) {
		Assert.assertEquals(message, x, position[0], Util.DEFAULT_EPSILON);
	}

	public static void assertPositionXEquals(double x, double[] position) {
		Assert.assertEquals(x, position[0], Util.DEFAULT_EPSILON);
	}

	public static void assertPositionYEquals(String message, double y, double[] position) {
		Assert.assertEquals(message, y, position[1], Util.DEFAULT_EPSILON);
	}

	public static void assertPositionYEquals(double y, double[] position) {
		Assert.assertEquals(y, position[1], Util.DEFAULT_EPSILON);
	}

	public static void assertPositionZEquals(String message, double z, double[] position) {
		Assert.assertEquals(message, z, position[2], Util.DEFAULT_EPSILON);
	}

	public static void assertPositionZEquals(double z, double[] position) {
		Assert.assertEquals(z, position[2], Util.DEFAULT_EPSILON);
	}

	public static void assertIntegerPositionEquals(String message, int x, int y, int z, int[] position) {
		Assert.assertArrayEquals(message, new int[] { x, y, z }, position);
	}

	public static void assertIntegerPositionEquals(int x, int y, int z, int[] position) {
		Assert.assertArrayEquals(new int[] { x, y, z }, position);
	}

	public static void assertIntegerPositionXEquals(String message, int x, int[] position) {
		Assert.assertEquals(message, x, position[0]);
	}

	public static void assertIntegerPositionXEquals(int x, int[] position) {
		Assert.assertEquals(x, position[0]);
	}

	public static void assertIntegerPositionYEquals(String message, int y, int[] position) {
		Assert.assertEquals(message, y, position[1]);
	}

	public static void assertIntegerPositionYEquals(int y, int[] position) {
		Assert.assertEquals(y, position[1]);
	}

	public static void assertIntegerPositionZEquals(String message, int z, int[] position) {
		Assert.assertEquals(message, z, position[2]);
	}

	public static void assertIntegerPositionZEquals(int z, int[] position) {
		Assert.assertEquals(z, position[2]);
	}

}
