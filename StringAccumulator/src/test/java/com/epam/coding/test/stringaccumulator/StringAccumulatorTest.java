package com.epam.coding.test.stringaccumulator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for StringAccumulator.
 */
public class StringAccumulatorTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public StringAccumulatorTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(StringAccumulatorTest.class);
	}

	public void testAddEmptyString() throws NegativeNumberException {
		assertEquals(0, StringAccumulator.add(""));
	}
	
	public void testAddCommaDelimited() throws NegativeNumberException {
		assertEquals(1, StringAccumulator.add("1"));
		assertEquals(3, StringAccumulator.add("1,2"));
		assertEquals(6, StringAccumulator.add("1,2,3"));
	}
	
	public void testAddMultipleNumbers() throws NegativeNumberException {
		assertEquals(9, StringAccumulator.add("1,2,3,3"));
	}
	
	public void testAddCommaAndNewLineDelimited() throws NegativeNumberException {
		assertEquals(9, StringAccumulator.add("1\n2,\n3,3"));
	}
	
	public void testAddChangeDelimiter() throws NegativeNumberException {
		assertEquals(9, StringAccumulator.add("//;\n1;2;3;3"));
	}
	
	public void testAddNegativeNumbers() throws NegativeNumberException {
		try {
			StringAccumulator.add("//;\n1;-2;3;-3");
			assertTrue(false); // assert unreachable
		} catch (NegativeNumberException e) {
			assertEquals("negatives not allowed : -2, -3", e.getMessage());
		}
	}
	
	public void testAddIgnoreBiggerThanOneThousand() throws NegativeNumberException {
		assertEquals(7, StringAccumulator.add("//;\n1;2002;3;3"));
	}
	
	public void testAddLongDelimiter() throws NegativeNumberException {
		assertEquals(8, StringAccumulator.add("//ddd\n2ddd3ddd3"));
		assertEquals(38, StringAccumulator.add("//***\n2***23***13"));
		assertEquals(38, StringAccumulator.add("//[\n2[23[13["));
	}
	
	public void testAddMultipleDelimiters() throws NegativeNumberException {
		assertEquals(28, StringAccumulator.add("//d|e\n2d23e3"));
	}

	public void testAddMultipleLongDelimiters() throws NegativeNumberException {
		assertEquals(30, StringAccumulator.add("//delim1|delim2\n2delim23delim13delim222"));
	}
}
