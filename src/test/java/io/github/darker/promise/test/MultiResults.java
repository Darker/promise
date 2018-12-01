package io.github.darker.promise.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.darker.promise.multi.MultiResult;

class MultiResults {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSetRes1() {
		final MultiResult<String, Integer,Void,Void> results = MultiResult.create2();
		results.setRes1("1");
		results.setRes2(1);
		assertEquals("1", results.getRes1());
		assertEquals(true, results.isSet1());
		assertEquals(true, results.allSet(), "All values were set now.");
	}

	@Test
	void testSetRes2() {
		final MultiResult<Void, String, Void,Void> results = MultiResult.create2();
		results.setRes2("1");
		assertEquals("1", results.getRes2());
		assertEquals(true, results.isSet2());
		assertEquals(false, results.allSet());
	}

	@Test
	void testSetRes3() {
		final MultiResult<Void, String, String,Void> results = MultiResult.create3();
		results.setRes3("1");
		assertEquals("1", results.getRes3());
		assertEquals(true, results.isSet3());
		assertEquals(false, results.allSet());
	}

	@Test
	void testSetRes4() {
		final MultiResult<Void, String, String,String> results = new MultiResult<>();
		results.setRes4("1");
		assertEquals("1", results.getRes4());
		assertEquals(true, results.isSet4());
		assertEquals(false, results.allSet());
	}

	@Test
	void testAllSet() {
		final MultiResult<String, String, String,String> results = new MultiResult<>();
		results.setRes4("1");
		assertEquals(false, results.allSet());
		results.setRes4("1");
		assertEquals(false, results.allSet());
		results.setRes3("1");
		assertEquals(false, results.allSet());
		results.setRes2("1");
		assertEquals(false, results.allSet());
		results.setRes1("1");
		assertEquals(true, results.allSet());
		
		final MultiResult<String, String, Void, Void> results2 = MultiResult.create2();
		assertEquals(false, results2.allSet());
		results2.setRes2("1");
		assertEquals(false, results2.allSet());
		results2.setRes1("1");
		assertEquals(true, results2.allSet());
	}

}
