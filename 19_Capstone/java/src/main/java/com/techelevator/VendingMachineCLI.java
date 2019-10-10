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
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE,
			MAIN_MENU_EXIT };
	private static final String FEED_MONEY = "Feed Money";
	private static final String SELECT_PRODUCT = "Select Product";
	private static final String FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { FEED_MONEY, SELECT_PRODUCT, FINISH_TRANSACTION };

	private Menu menu;

	private List<ProductAbstract> listOfItems = new ArrayList<>();

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() throws FileNotFoundException {
		Double feedMoney = 0.0;

		File inputFile = new File("vendingmachine.csv");
		
		Integer quantity = 5;
		Scanner productListScanner = new Scanner(inputFile.getAbsoluteFile());
		String soldOut = "Sold Out";
		
		while (productListScanner.hasNextLine()) {
			String currentLine = productListScanner.nextLine();
			String[] splitStuff = currentLine.split("\\|");
			String code = splitStuff[0];
			String name = splitStuff[1];
			Double cost = Double.parseDouble(splitStuff[2]);
			String type = splitStuff[3];
			if (type.equals("Chip")) {
				listOfItems.add(new Chip(type, name, code, cost, 5));
				//System.out.println(listOfItems);
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
			if (quantity < 1) {
				System.out.println(currentLine + "|Quantity: " + soldOut);
			}
		}
		while (true) {

			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				for (ProductAbstract item : listOfItems) {
					System.out.println(item.getCode() + "   " + item.getName() + "    $" + item.getCost() + quantity);
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
					System.out.println("Please Select Product");
					while (productListScanner.hasNextLine()) {
						String currentLine = productListScanner.nextLine();
						if (quantity == 0) {
							System.out.println(currentLine + "|Quantity: " + soldOut);
						} else {
							System.out.println(currentLine + "|Quantity: " + quantity);
						}
					}

					// Take users choice
					Scanner userInput = new Scanner(System.in);
					String usersChoice = userInput.nextLine();
					usersChoice = usersChoice.toUpperCase();
					String[] codes = new String[] { "A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "C1", "C2", "C3",
							"C4", "D1", "D2", "D3", "D4" };

					int count = 0;
					for (int i = 0; i < codes.length; i++) {
						if (usersChoice.equals(codes[i])) {
							count++;
						}
					}

					// if users choice doesn't exist
					if (count < 1) {
						System.out.println("code does not exist");
					} else if (quantity < 1) {
						System.out.println(soldOut);
					}
				}

				// At this point we decide we need to make classes and an interface

			} else if (choice.equals(MAIN_MENU_EXIT)) {
				break;
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
