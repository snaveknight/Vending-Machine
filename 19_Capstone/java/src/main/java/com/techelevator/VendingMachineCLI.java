package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.techelevator.view.Menu;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE,
			MAIN_MENU_EXIT };
	private static final String FEED_MONEY = "Feed Money";
	private static final String SELECT_PRODUCT = "Select Product";
	private static final String FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { FEED_MONEY, SELECT_PRODUCT, FINISH_TRANSACTION };

	private Menu menu;
	Double feedMoney = 0.0;
	private List<ProductAbstract> listOfItems = new ArrayList<>();
	// ProductAbstract products = new ProductAbstract();

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() throws FileNotFoundException {
		
		File inputFile = new File("vendingmachine.csv");
		// call the inventory file
		Scanner productListScanner = new Scanner(inputFile.getAbsoluteFile());
		String soldOut = "Sold Out";

		// use a while loop to populate our list with the file
		while (productListScanner.hasNextLine()) {
			String currentLine = productListScanner.nextLine();
			String[] splitStuff = currentLine.split("\\|");
			String code = splitStuff[0];
			String name = splitStuff[1];
			Double cost = Double.parseDouble(splitStuff[2]);
			String type = splitStuff[3];
			if (type.equals("Chip")) {
				listOfItems.add(new Chip(type, name, code, cost, 5));
			}
			if (type.equals("Gum")) {
				listOfItems.add(new Gum(type, name, code, cost, 5));
			}
			if (type.equals("Drink")) {
				listOfItems.add(new Drink(type, name, code, cost, 5));
			}
			if (type.equals("Candy")) {
				listOfItems.add(new Candy(type, name, code, cost, 5));
			}
		}

		// ********************** start of main loop **********************
		while (true) {

			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				for (ProductAbstract item : listOfItems) {
					//if else for is the product is sold out or not
					if (item.getQuantity() < 1) {
						System.out.println(
								item.getCode() + "   " + item.getName() + "    $" + item.getCost() + " Quantity  " 
						+ soldOut);
					}
					else {
					System.out.println(
							item.getCode() + "   " + item.getName() + "    $" + item.getCost() + " Quantity  " 
					+ item.getQuantity());
					}
				}

			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// purchase menu
				String choicePurchaseMenu = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
				// System.out.println("Current Money Provided " + feedMoney);

				if (choicePurchaseMenu.equals(FEED_MONEY)) {
					// feed money
					System.out.println("Please Insert Money");
					Scanner userInput = new Scanner(System.in);
					String userFeedMoney = userInput.nextLine();
					Integer userFeedMoneyInt = Integer.parseInt(userFeedMoney);
					feedMoney += userFeedMoneyInt;
					System.out.println("Current Balance: " + feedMoney);

				} else if (choicePurchaseMenu.equals(SELECT_PRODUCT)) {
					// show product list
					for (ProductAbstract item : listOfItems) {
						System.out.println(item.getCode() + "   " + item.getName() + "    $" + item.getCost()
								+ item.getQuantity());
					}
					// have the user input a product code
					System.out.println("Please Select Product");

					// Take users choice
					Scanner userCodeEntry = new Scanner(System.in);
					String usersChoice = userCodeEntry.nextLine();
					usersChoice = usersChoice.toUpperCase();

					// call dispense method
					System.out.print(dispenseMethod(usersChoice));
				} else if (choicePurchaseMenu.equals(FINISH_TRANSACTION)) {
					// System.out.println("THIS WORKS");
					System.out.println(makeChange(feedMoney));
					feedMoney = 0.0;
				}
			} else if (choice.equals(MAIN_MENU_EXIT)) 
				break;
			}
		}
	
	//main method
	public static void main(String[] args) throws FileNotFoundException {
		//make a new file for the audit
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
		
	}
	
	//method to add to the file audit 
	public static void logFile(String dispenseType, Double moneyFeed, Double currentBalance) 
			throws FileNotFoundException {
		
		File newFile = new File("Log.txt");
		PrintWriter pw = new PrintWriter(newFile.getAbsoluteFile());
		//import the date util to make date and time for the audit file 
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		//store the current and added balance in variables
		
		
	}
	
	
	//method for making change
	public String makeChange(Double userBalance) {
		int quarters = 0;
		int dimes = 0;
		int nickels = 0;
		int balance = (int) (userBalance * 100);
		while (balance > 0) {
				//Determines Quarters
				quarters = balance / 25;
				int totalCoins = quarters * 25;
				balance -= totalCoins;
				balance = balance%25;
				//Determines dimes
				dimes = balance / 10;
				totalCoins = dimes / 10;
				balance -= totalCoins;
				balance = balance%10;		
				//Deremines nickels
				nickels = balance / 5;
				totalCoins = nickels * 5;
				balance -= totalCoins;
		}
		return "Your change is " + quarters + " quarters and " + dimes + " dimes and " + nickels + " nickels.";
	}
	//our method for the purchase menu dispensing 
	public String dispenseMethod(String usersChoice) {
		String thisDispenseMessage = "";
		int counter = 0;
		// validate users choice doesn't exist
		for (int i = 0; i < listOfItems.size(); i++) {
			if (!listOfItems.get(i).getCode().equals(usersChoice)) {
				counter++;
			}
		}
		if (counter < 1) {
			return "Code does not exist";
		}
		//for loop to validate quantity and make sure enough to pay 
		for (int i = 0; i < listOfItems.size(); i++) {
			// Gets uersChoice code
			if (listOfItems.get(i).getCode().equals(usersChoice)) {
				// Check quantity of item
				if (listOfItems.get(i).getQuantity() < 1) {
					return "That item is sold out.";
				}
				// make sure have enough to pay
				else if (feedMoney < listOfItems.get(i).getCost()) {
					return "You don't have enought depostited for that item.";
				} else {
					// executes vending
					int quantity = listOfItems.get(i).getQuantity();
					quantity--;
					listOfItems.get(i).setQuantity(quantity);
					feedMoney -= listOfItems.get(i).getCost();
					thisDispenseMessage = listOfItems.get(i).getDispenseMessage();
				}
			}
		}
		return thisDispenseMessage;
	}
}
