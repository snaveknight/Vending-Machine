package com.techelevator;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.techelevator.view.Menu;

public class DispenseTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	//DispenseTest test = new DispenseTest();
	VendingMachineCLI tester = new VendingMachineCLI(null);
	
	@Test
	public void oneDollarMakesFourQuarters() {
		BigDecimal oneDollar = new BigDecimal(1.00);
		String actual = tester.makeChange(oneDollar);
		String expected = "Your change is 4 quarters and 0 dimes and 0 nickels.";
		Assert.assertEquals(expected, actual);
	}
	@Test
	public void oneDollarTwoFiveMakesFiveQuarters() {
		BigDecimal oneDollar = new BigDecimal(1.25);
		String actual = tester.makeChange(oneDollar);
		String expected = "Your change is 5 quarters and 0 dimes and 0 nickels.";
		Assert.assertEquals(expected, actual);
	}
	@Test
	public void OneEightyFiveMakesThreeQuartersOneDime() {
		BigDecimal oneDollar = new BigDecimal(1.85);
		String actual = tester.makeChange(oneDollar);
		String expected = "Your change is 7 quarters and 1 dimes and 0 nickels.";
		Assert.assertEquals(expected, actual);
	}
	@Test
	public void onethirtyMakesFiveQOneN() {
		BigDecimal oneDollar = new BigDecimal(1.30);
		String actual = tester.makeChange(oneDollar);
		String expected = "Your change is 5 quarters and 0 dimes and 1 nickels.";
		Assert.assertEquals(expected, actual);
	}

}
