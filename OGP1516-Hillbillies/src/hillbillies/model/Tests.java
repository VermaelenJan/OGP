package hillbillies.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import hillbillies.tests.TaskFactory.TaskFactoryTest;
import hillbillies.tests.boulder.BoulderTest;
import hillbillies.tests.facade.Part2TestPartial;
import hillbillies.tests.facade.Part3TestPartial;
import hillbillies.tests.log.LogTest;
import hillbillies.tests.scheduler.SchedulerTest;
import hillbillies.tests.task.TaskTest;
import hillbillies.tests.unit.UnitTest;
import hillbillies.tests.world.WorldTest;

/**
* @author Maxime Pittomvils (r0580882) and Jan Vermaelen (r0591389)
* The collection of all tests
*/

@RunWith(Suite.class)
@Suite.SuiteClasses( {UnitTest.class, WorldTest.class, BoulderTest.class, LogTest.class, TaskFactoryTest.class,
						Part2TestPartial.class, Part3TestPartial.class, SchedulerTest.class, TaskTest.class} )
public class Tests {}