package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.techelevator.view.Menu;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_EXIT };
	private static final String FEED_MONEY = "Feed Money";
	private static final String SELECT_PRODUCT = "Select Product";
	private static final String FINISH_TRANSACTION = "Finish Transaction";
	private static final Double CURRENT_MONEY_PROVIDED = 0.0;
	private static final String[] PURCHASE_MENU_OPTIONS = {FEED_MONEY, SELECT_PRODUCT, FINISH_TRANSACTION};
	
	
	private Menu menu;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}
	
	File inputFile = new File("vendingmachine.csv");
	
	public void run() throws FileNotFoundException {
		Double feedMoney = 0.0;
		
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
				
			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				Scanner productListScanner = new Scanner(inputFile.getAbsoluteFile());
				productListScanner.close();
				Integer quantity = 5;
				String soldOut = "Sold Out";
				
				while (productListScanner.hasNextLine()) {
				String currentLine = productListScanner.nextLine();
				if (quantity ==0) {
					
				System.out.println(currentLine + "|Quantity: " + soldOut);
				} else {
				System.out.println(currentLine + "|Quantity: " + quantity);
				}
				}
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				
				String choicePurchaseMenu = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
				System.out.println(choicePurchaseMenu + "Current Money Provided " + feedMoney);
				if (choicePurchaseMenu.equals(FEED_MONEY)) {
					System.out.println("Please Insert Money");
					Scanner userInput = new Scanner(System.in);
					String userFeedMoney = userInput.nextLine();
					Integer userFeedMoneyInt = Integer.parseInt(userFeedMoney);
					feedMoney += userFeedMoneyInt;
					System.out.println("Current Balance: " + feedMoney);
				}  else if (choicePurchaseMenu.equals(SELECT_PRODUCT)) {
				
				//Purchase menu must be completed
					
					System.out.println("Please Select Product");
					Scanner userInput = new Scanner(System.in);
					String userFeedMoney = userInput.nextLine();
					Integer userFeedMoneyInt = Integer.parseInt(userFeedMoney);
					feedMoney += userFeedMoneyInt;
					System.out.println("Current Balance: " + feedMoney);
				} 
				
				//Finish Transaction Must be completed
				
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
}
