package com.techelevator;

public class dummyClass {

	public static void notMain(String[] args) {
		
		Double quarters = 0.0;
		Double dimes = 0.0;
		Double nickels = 0.0;
		Double userBalance = 0.0;
		
		for (int i = 0; i < userBalance; i++)
		if (userBalance > .05 && userBalance < .10) {
			userBalance -= .05;
			nickels  += 1;
		} else if (userBalance > .10 && userBalance < .25) {
			userBalance -=  .10;
			dimes += 1;
		} else if (userBalance > .25) {
			userBalance -= .25;
			dimes += 1;
		}
		
		System.out.println( "Your change is " + quarters + " quarters and " + dimes + " dimes and " + nickels + " nickels.");
	}

}
