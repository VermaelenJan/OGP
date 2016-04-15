package hillbillies.part2.facade;

import java.util.Set;

import hillbillies.model.Faction;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.model.Boulder;
import hillbillies.model.Log;

import hillbillies.part2.listener.TerrainChangeListener;
import ogp.framework.util.ModelException;

/**
 * Implement this interface to connect your code to the graphical user interface
 * (GUI).
 * 
 * <ul>
 * <li>For separating the code that you wrote from the code that was provided to
 * you, put <b>ALL your code in the <code>src</code> folder.</b> The code that
 * is provided to you stays in the <code>src-provided</code> folder. Only if you
 * modify the provided code, it's advised to move the modified code to the
 * <code>src</code> folder, so that your changes cannot be accidentally
 * overwritten by an updated version of the provided code.</li>
 * 
 * <li>You should at least <b>create the following classes</b> in the package
 * <code>hillbillies.model</code>:
 * <ul>
 * <li>a class {@link Unit} for representing a Hillbilly Unit</li>
 * <li>a class {@link World} for representing a Hillbilly World</li>
 * <li>a class {@link Faction} for representing a unit faction</li>
 * <li>a class {@link Boulder} for representing a boulder object</li>
 * <li>a class {@link Log} for representing a log object</li>
 * </ul>
 * You may, of course, add additional classes as you see fit.
 * 
 * <li>You should <b>create a class</b> {@link Facade} that implements this
 * interface. This class should be placed <b>in the package
 * <code>hillbillies.part2.facade</code></b> in your <code>src</code> folder.
 * </li>
 * 
 * <li>The <b>header of that {@link Facade} class</b> should look as follows:
 * <br>
 * <code>public class Facade implements IFacade { ... }</code><br>
 * Consult the <a href=
 * "http://docs.oracle.com/javase/tutorial/java/IandI/createinterface.html">
 * Java tutorial</a> for more information on interfaces, if necessary.</li>
 *
 * <li>Your <code>Facade</code> class should offer a <b>default constructor</b>.
 * </li>
 * 
 * <li><b>Each non-deprecated method</b> defined in this {@link IFacade}
 * interface and the interface from part 1 (
 * {@link hillbillies.part1.facade.IFacade}) must be implemented by your @{link
 * Facade} class. For example, the implementation of
 * {@link #getCubeCoordinate(Unit)} should call one or more methods on the given
 * <code>unit</code> to retrieve its current cube coordinate.</li>
 * 
 * <li>The methods {@link #advanceTime(Unit, double)} and {@link #work(Unit)}
 * from part 1 are <b>deprecated</b> and no longer need to be implemented.</li>
 * 
 * <li>Methods in this interface are <b>only allowed to throw exceptions of
 * type</b> {@link ModelException} (this class is provided). No other exception
 * types are allowed. This exception can only be thrown if (1) calling a method
 * of your <code>Unit</code> class with the given parameters would violate a
 * precondition, or (2) if the method of your <code>Unit</code> class throws an
 * exception (if so, wrap the exception in the {@link ModelException}).</li>
 * 
 * <li>{@link ModelException} should not be used anywhere outside of your
 * {@link Facade} implementation.</b></li>
 * 
 * <li>Your {@link Facade} implementation should <b>only contain trivial
 * code</b> (for example, calling a method, combining multiple return values
 * into an array, creating @Value instances, catching exceptions and wrapping it
 * in a ModelException). All non-trivial code should be placed in the other
 * classes that you create.</li>
 * 
 * <li>The rules described above and the documentation described below for each
 * method apply only to your {@link Facade} class. <b>Your other classes (e.g.,
 * {@link Unit}, {@link World}) should follow the rules described in the
 * assignment.</b></li>
 * 
 * <li><b>Do not modify the signatures</b> of the methods defined in this
 * interface.</li>
 * 
 * </ul>
 *
 */
public interface IFacade extends hillbillies.part1.facade.IFacade {

	/* WORLD */

	/**
	 * Create a new world of the given size and with the given terrain. To keep
	 * the GUI display up to date, the method in the given listener must be
	 * called whenever the terrain type of a cube in the world changes.
	 * 
	 * @param terrainTypes
	 *            A three-dimensional array (structured as [x][y][z]) with the
	 *            types of the terrain, encoded as integers. The terrain always
	 *            has the shape of a box (i.e., the array terrainTypes[0] has
	 *            the same length as terrainTypes[1] etc.). The integer types
	 *            are as follows:
	 *            <ul>
	 *            <li>0: air</li>
	 *            <li>1: rock</li>
	 *            <li>2: tree</li>
	 *            <li>3: workshop</li>
	 *            </ul>
	 * @param modelListener
	 *            An object with a single method,
	 *            {@link TerrainChangeListener#notifyTerrainChanged(int, int, int)}
	 *            . This method must be called by your implementation whenever
	 *            the terrain type of a cube changes (e.g., as a consequence of
	 *            cave-ins), so that the GUI will correctly update the display.
	 *            The coordinate of the changed cube must be given in the form
	 *            of the parameters x, y and z. You do not need to call this
	 *            method during the construction of your world.
	 * @return
	 * @throws ModelException
	 */
	public World createWorld(int[][][] terrainTypes, TerrainChangeListener modelListener) throws ModelException;

	/**
	 * Return the number of cubes in the world in the x-direction.
	 * 
	 * @param world
	 *            The world for which to retrieve the number of cubes.
	 * @return The number of cubes in the x-direction.
	 * @throws ModelException
	 */
	public int getNbCubesX(World world) throws ModelException;

	/**
	 * Return the number of cubes in the world in the y-direction.
	 * 
	 * @param world
	 *            The world for which to retrieve the number of cubes.
	 * @return The number of cubes in the y-direction.
	 * @throws ModelException
	 */
	public int getNbCubesY(World world) throws ModelException;

	/**
	 * Return the number of cubes in the world in the z-direction.
	 * 
	 * @param world
	 *            The world for which to retrieve the number of cubes.
	 * @return The number of cubes in the z-direction.
	 * @throws ModelException
	 */
	public int getNbCubesZ(World world) throws ModelException;

	/**
	 * Advance the state of the given world by the given time period.
	 * 
	 * @param world
	 *            The world for which to advance the time
	 * @param dt
	 *            The time period, in seconds, by which to advance the world's
	 *            state.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 * 
	 * @note This method replaces the {@link #advanceTime(Unit, double)} method
	 *       from part 1.
	 */
	public void advanceTime(World world, double dt) throws ModelException;

	/**
	 * Return the terrain type of the cube at the given coordinates.
	 * 
	 * @param world
	 *            The world from which to retrieve the type.
	 * @param x
	 *            The x-coordinate of the cube
	 * @param y
	 *            The y-coordinate of the cube
	 * @param z
	 *            The z-coordinate of the cube
	 * @return The terrain type of the given cube, encoded as an integer
	 *         according to the values in
	 *         {@link #createWorld(int[][][], TerrainChangeListener)}.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	public int getCubeType(World world, int x, int y, int z) throws ModelException;

	/**
	 * Set the terrain type of the cube at the given coordinates the given
	 * value.
	 * 
	 * @param world
	 *            The world in which to set the type.
	 * @param x
	 *            The x-coordinate of the cube
	 * @param y
	 *            The y-coordinate of the cube
	 * @param z
	 *            The z-coordinate of the cube
	 * @param value
	 *            The new value of the terrain type of the cube, encoded as an
	 *            integer according to the values in
	 *            {@link #createWorld(int[][][], TerrainChangeListener)}.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */

	public void setCubeType(World world, int x, int y, int z, int value) throws ModelException;

	/**
	 * Return whether the cube at the given coordinates is solid and connected
	 * to the border of the world.
	 * 
	 * @param world
	 *            The world to which the cube belongs
	 * @param x
	 *            The x-coordinate of the cube
	 * @param y
	 *            The y-coordinate of the cube
	 * @param z
	 *            The z-coordinate of the cube
	 * @return true if the given cube is solid and connected to the border of
	 *         the world; false otherwise.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 * 
	 */
	public boolean isSolidConnectedToBorder(World world, int x, int y, int z) throws ModelException;

	/* UNITS */

	/**
	 * Spawn a new unit in the world, according to the rules in the assignment
	 * (section 1.1.2).
	 * 
	 * @param world
	 *            The world in which to spawn a new unit
	 * 
	 * @param enableDefaultBehavior
	 *            Whether the unit should act according to the default behaviour
	 *            or not.
	 * 
	 * @return The newly spawned unit, or null if no unit could be spawned.
	 * 
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	public Unit spawnUnit(World world, boolean enableDefaultBehavior) throws ModelException;

	/**
	 * Adds the given unit to the given world.
	 * 
	 * @param unit
	 * @param world
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	public void addUnit(Unit unit, World world) throws ModelException;

	/**
	 * Return all units that are currently part of the world.
	 * 
	 * @param world
	 *            The world from which to retrieve units
	 * @return A set containing all units from the world.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	public Set<Unit> getUnits(World world) throws ModelException;

	/**
	 * Return whether the given unit is currently carrying a log.
	 * 
	 * @param unit
	 *            The unit
	 * @return true if the unit is currently carrying a log; false otherwise
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	public boolean isCarryingLog(Unit unit) throws ModelException;

	/**
	 * Return whether the given unit is currently carrying a boulder.
	 * 
	 * @param unit
	 *            The unit
	 * @return true if the unit is currently carrying a boulder; false otherwise
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	public boolean isCarryingBoulder(Unit unit) throws ModelException;

	/**
	 * Return whether the given unit is currently alive.
	 * 
	 * @param unit
	 *            The unit
	 * @return true if the unit is currently alive; false if it's dead
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	public boolean isAlive(Unit unit) throws ModelException;

	/**
	 * Return the current number of experience points of the given unit.
	 * 
	 * @param unit
	 * @return The number of experience points.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	public int getExperiencePoints(Unit unit) throws ModelException;

	/**
	 * Make the given unit start working on the given target cube.
	 * 
	 * @param unit
	 *            The unit that should start working.
	 * @param x
	 *            The x-coordinate of the target cube
	 * @param y
	 *            The y-coordinate of the target cube
	 * @param z
	 *            The z-coordinate of the target cube
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 * 
	 * @note This method replaces the {@link #work(Unit)} method from part 1.
	 */
	public void workAt(Unit unit, int x, int y, int z) throws ModelException;

	/**
	 * This method is no longer necessary, and is replaced by the
	 * {@link #workAt(Unit, int[])} method.
	 */
	@Override
	@Deprecated
	default void work(Unit unit) throws ModelException {
		throw new NoSuchMethodError("This method no longer needs to be supported");
	}

	/**
	 * This method is no longer necessary, and is replaced by the
	 * {@link #advanceTime(World, double)} method.
	 */
	@Override
	@Deprecated
	default void advanceTime(Unit unit, double dt) throws ModelException {
		throw new NoSuchMethodError("This method no longer needs to be supported");
	}

	/* FACTIONS */
	/**
	 * Return the current faction of the given unit.
	 * 
	 * @param unit
	 *            The unit of which to retrieve the faction.
	 * @return The current faction of the unit.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	public Faction getFaction(Unit unit) throws ModelException;

	/**
	 * Return the units that are part of the given faction.
	 * 
	 * @param faction
	 *            The faction of which to retrieve the members.
	 * @return The set of units that belong to the faction.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	public Set<Unit> getUnitsOfFaction(Faction faction) throws ModelException;

	/**
	 * Return all the active factions of the given world.
	 * 
	 * @param
	 * @return A set of all active (i.e., non-empty) factions in the world.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	public Set<Faction> getActiveFactions(World world) throws ModelException;

	/* BOULDERS */

	/**
	 * Get the precise coordinates of the given boulder.
	 * 
	 * @param boulder
	 *            The boulder for which to return the position.
	 * @return The coordinate of the center of the boulder, as an array with 3
	 *         doubles {x, y, z}.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	public double[] getPosition(Boulder boulder) throws ModelException;

	/**
	 * Return all boulders that are part of the given world.
	 * 
	 * @param world
	 *            The world from which to retrieve the boulders.
	 * @return A set containing all boulders present in the given world (i.e.,
	 *         not picked up, consumed, destroyed, ...).
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	public Set<Boulder> getBoulders(World world) throws ModelException;

	/* LOGS */

	/**
	 * Get the precise coordinate of the given log.
	 * 
	 * @param log
	 *            The log for which to return the position.
	 * @return The coordinate of the center of the log, as an array with 3
	 *         doubles {x, y, z}.
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	public double[] getPosition(Log log) throws ModelException;

	/**
	 * Return all logs that are part of the given world.
	 * 
	 * @param world
	 *            The world from which to retrieve the logs.
	 * @return A set containing all logs present in the given world (i.e., not
	 *         picked up, consumed, destroyed, ...).
	 * @throws ModelException
	 *             A precondition was violated or an exception was thrown.
	 */
	public Set<Log> getLogs(World world) throws ModelException;

}
