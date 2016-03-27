package org.adorsys.adcore.env;

import org.junit.Assert;
import org.junit.Test;

public class EnvPropertiesTest {

	@Test
	public void testSimpleStyle1() {
		String testHomeOrig = "SampleHome/test/";
		System.setProperty("test.home", testHomeOrig);
		System.setProperty("full.path", "root/${test.home}/here");
		String testHomeResolved = EnvProperties.getEnvOrSystProp("test.home");
		Assert.assertEquals(testHomeOrig, testHomeResolved);
		String fullPathExpected = "root/"+testHomeOrig+"/here";
		String fullPathResolved = EnvProperties.getEnvOrSystProp("full.path");
		Assert.assertEquals(fullPathExpected, fullPathResolved);
	}

	@Test
	public void testSimpleStyle2Slash() {
		String testHomeOrig = "SampleHome/test/";
		System.setProperty("TEST_HOME", testHomeOrig);
		String testHomeResolved = EnvProperties.getEnvOrSystProp("TEST_HOME");
		Assert.assertEquals(testHomeOrig, testHomeResolved);
		System.setProperty("full.path", "root/$TEST_HOME/here");
		String fullPathExpected = "root/"+testHomeOrig+"/here";
		String fullPathResolved = EnvProperties.getEnvOrSystProp("full.path");
		Assert.assertEquals(fullPathExpected, fullPathResolved);
	}

	@Test
	public void testSimpleStyle2dot() {
		String testHomeOrig = "SampleHome/test/";
		System.setProperty("TEST_HOME", testHomeOrig);
		String testHomeResolved = EnvProperties.getEnvOrSystProp("TEST_HOME");
		Assert.assertEquals(testHomeOrig, testHomeResolved);
		System.setProperty("full.path", "root/$TEST_HOME.here");
		String fullPathExpected = "root/"+testHomeOrig+".here";
		String fullPathResolved = EnvProperties.getEnvOrSystProp("full.path");
		Assert.assertEquals(fullPathExpected, fullPathResolved);
	}
	@Test
	public void testSimpleStyle2Colon() {
		String testHomeOrig = "SampleHome/test/";
		System.setProperty("TEST_HOME", testHomeOrig);
		String testHomeResolved = EnvProperties.getEnvOrSystProp("TEST_HOME");
		Assert.assertEquals(testHomeOrig, testHomeResolved);
		System.setProperty("full.path", "root/$TEST_HOME:here");
		String fullPathExpected = "root/"+testHomeOrig+":here";
		String fullPathResolved = EnvProperties.getEnvOrSystProp("full.path");
		Assert.assertEquals(fullPathExpected, fullPathResolved);
	}
	@Test
	public void testSimpleStyle2Dash() {
		String testHomeOrig = "SampleHome/test/";
		System.setProperty("TEST_HOME", testHomeOrig);
		String testHomeResolved = EnvProperties.getEnvOrSystProp("TEST_HOME");
		Assert.assertEquals(testHomeOrig, testHomeResolved);
		System.setProperty("full.path", "root/$TEST_HOME-here");
		String fullPathExpected = "root/"+testHomeOrig+"-here";
		String fullPathResolved = EnvProperties.getEnvOrSystProp("full.path");
		Assert.assertEquals(fullPathExpected, fullPathResolved);
	}

	@Test
	public void testComplexStyle2Dash() {
		String testHomeOrig = "SampleHome/test/";
		System.setProperty("TEST_HOME", testHomeOrig);
		String simpleOrig = "simple";
		System.setProperty("SIMPLE", simpleOrig);
		System.setProperty("full.path", "root/$TEST_HOME-here:$SIMPLE");
		String fullPathExpected = "root/"+testHomeOrig+"-here:"+simpleOrig;
		String fullPathResolved = EnvProperties.getEnvOrSystProp("full.path");
		Assert.assertEquals(fullPathExpected, fullPathResolved);
	}
}
