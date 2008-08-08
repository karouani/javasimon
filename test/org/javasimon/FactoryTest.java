package org.javasimon;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;

/**
 * FactoryTest.
 *
 * @author <a href="mailto:virgo47@gmail.com">Richard "Virgo" Richter</a>
 * @created Aug 8, 2008
 */
public final class FactoryTest {
	private static final int FRESH_FACTORY_SIMON_LIST_SIZE = 1;
	private static final int SIMON_COUNT_AFTER_COUNTER_ADDED = 5;

	private static final String ORG_JAVASIMON_TEST = "org.javasimon.test";
	private static final String ORG_JAVASIMON_TEST_COUNTER = "org.javasimon.test.counter";

	@BeforeMethod
	public void resetAndEnable() {
		SimonFactory.reset();
		SimonFactory.enable();
	}

	@Test
	public void testSimonCreation() {
		Assert.assertEquals(SimonFactory.simonNames().size(), FRESH_FACTORY_SIMON_LIST_SIZE);
		SimonFactory.getCounter(ORG_JAVASIMON_TEST_COUNTER).increment();
		Assert.assertEquals(SimonFactory.simonNames().size(), SIMON_COUNT_AFTER_COUNTER_ADDED);

		Assert.assertTrue(SimonFactory.getSimon(ORG_JAVASIMON_TEST) instanceof UnknownSimon);
		Assert.assertEquals(SimonFactory.getSimon(ORG_JAVASIMON_TEST).getChildren().size(), 1);
		SimonFactory.getCounter(ORG_JAVASIMON_TEST);
		Assert.assertTrue(SimonFactory.getSimon(ORG_JAVASIMON_TEST) instanceof SimonCounter);
		Assert.assertEquals(SimonFactory.getSimon(ORG_JAVASIMON_TEST).getChildren().size(), 1);
	}

	@Test
	public void testDisabledSimons() {
		SimonFactory.getRootSimon().disable(true);
		Assert.assertFalse(SimonFactory.getRootSimon().isEnabled());
		Assert.assertFalse(SimonFactory.getCounter(ORG_JAVASIMON_TEST_COUNTER).isEnabled());

		SimonFactory.getCounter(ORG_JAVASIMON_TEST_COUNTER).enable(false);
		Assert.assertTrue(SimonFactory.getCounter(ORG_JAVASIMON_TEST_COUNTER).isEnabled());
		Assert.assertFalse(SimonFactory.getCounter(ORG_JAVASIMON_TEST).isEnabled());

		SimonFactory.getCounter(ORG_JAVASIMON_TEST_COUNTER).inheritState(false);
		Assert.assertFalse(SimonFactory.getCounter(ORG_JAVASIMON_TEST_COUNTER).isEnabled());
		Assert.assertFalse(SimonFactory.getCounter(ORG_JAVASIMON_TEST).isEnabled());

		SimonFactory.getCounter(ORG_JAVASIMON_TEST_COUNTER).disable(false);

		SimonFactory.disable();
		Assert.assertNull(SimonFactory.getSimon(ORG_JAVASIMON_TEST));
		Assert.assertTrue(SimonFactory.getRootSimon() instanceof DisabledUnknown);
		Assert.assertEquals(SimonFactory.getRootSimon().getName(), SimonFactory.ROOT_SIMON_NAME);
	}

	@Test
	public void testGeneratedNames() {
		Assert.assertEquals(SimonFactory.generateName("-stopwatch", true), getClass().getName() + ".testGeneratedNames-stopwatch");

		SimonFactory.disable();
		Assert.assertNull(SimonFactory.generateName("-stopwatch", true));
	}
}
