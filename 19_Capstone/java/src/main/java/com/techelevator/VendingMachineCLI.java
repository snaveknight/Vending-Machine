package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.techelevator.view.Menu;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, 
			MAIN_MENU_OPTION_PURCHASE,
			MAIN_MENU_EXIT };
	private static final String FEED_MONEY = "Feed Money";
	private static final String SELECT_PRODUCT = "Select Product";
	private static final String FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { FEED_MONEY, SELECT_PRODUCT, 
			FINISH_TRANSACTION };

	private Menu menu;

	private List<ProductAbstract> listOfItems = new ArrayList<>();
	// ProductAbstract products = new ProductAbstract();

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	Double feedMoney = 0.0;
	public void run() throws FileNotFoundException {

		File inputFile = new File("vendingmachine.csv");
		//call the inventory file 
		Scanner productListScanner = new Scanner(inputFile.getAbsoluteFile());
		String soldOut = "Sold Out";
		
		//use a while loop to populate our list with the file 
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
		
		//  ********************** start of main loop **********************
		while (true) {
			
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			
			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				for (ProductAbstract item : listOfItems) {
					System.out.println(item.getCode() + "   "
				+ item.getName() + "    $" + item.getCost() + item.getQuantity());
				}

			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// purchase menu
				String choicePurchaseMenu = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
				System.out.println("Current Money Provided " + feedMoney);

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
						System.out
								.println(item.getCode() + "   " + item.getName()
								+ "    $" + item.getCost() + item.getQuantity());
					}
					//have the user input a product code
					System.out.println("Please Select Product");
				
					// Take users choice
					Scanner userCodeEntry = new Scanner(System.in);
					String usersChoice = userCodeEntry.nextLine();
					usersChoice = usersChoice.toUpperCase();
					
					//call dispense method
					System.out.print(dispenseMethod(usersChoice));
				}
			} 
			else if (choice.equals(MAIN_MENU_EXIT)) {
				break;
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
	public  String dispenseMethod(String usersChoice) {
		String thisDispenseMessage = "";
		// validate users choice doesn't exist
		if (!listOfItems.contains(usersChoice)) {
			return "Code does not exist.";				
		} 
		for (int i = 0; i < listOfItems.size(); i ++) {
			//validate the code exists after the user puts in their choice

			//now we need to take the users choice and put it to the ProductAbstract list
			// verify have enough qty is valid
			if (listOfItems.get(i).getQuantity() < 1) {
				return "That item is sold out.";
			} 
			// make sure have enough to pay
			else if (feedMoney < listOfItems.get(i).getCost()) {
				return "You don't have enought depostited for that item.";
			}
			else {
				int quantity = listOfItems.get(i).getQuantity();
				quantity--;
				listOfItems.get(i).setQuantity(quantity);
				feedMoney -= listOfItems.get(i).getCost();
				thisDispenseMessage = listOfItems.get(i).getDispenseMessage();
			}
		}	
		return thisDispenseMessage;
	}
}
