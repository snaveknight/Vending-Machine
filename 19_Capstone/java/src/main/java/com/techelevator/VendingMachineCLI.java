package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
	public static final String changeCalculator = null;

	private Menu menu;
	static String auditLine = "";
	BigDecimal feedMoney = new BigDecimal(0.00);
	private List<ProductAbstract> listOfItems = new ArrayList<>();

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() throws FileNotFoundException {
		/// EXTRACT METHOD
		// *******took this block of code and made new method loadInventoryItems();
		// File inputFile = new File("vendingmachine.csv");
		// Scanner productListScanner = new Scanner(inputFile.getAbsoluteFile());
		// while (productListScanner.hasNextLine()) {

		// String currentLine = productListScanner.nextLine();
		// String[] splitStuff = currentLine.split("\\|");
		// String code = splitStuff[0];
		// String name = splitStuff[1];
		// BigDecimal cost = new BigDecimal(splitStuff[2]);
		// String type = splitStuff[3];

		// // adding items to the list of itmes
		// if (type.equals("Chip")) {
		// listOfItems.add(new Chip(type, name, code, cost, 5));
		// }
		// if (type.equals("Gum")) {
		// listOfItems.add(new Gum(type, name, code, cost, 5));
		// }
		// if (type.equals("Drink")) {
		// listOfItems.add(new Drink(type, name, code, cost, 5));
		// }
		// if (type.equals("Candy")) {
		// listOfItems.add(new Candy(type, name, code, cost, 5));
		// }
		// }
		// *********************^^^^^^^^^^^^^^^^^^^^^^^^ */
		loadInventoryItems();

		// ********************** start of main loop **********************
		while (true) {

			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {

				// display vending machine items
				productList();

			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {

				while (true) {

					// purchase menu
					String purchaseMenu = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

					if (purchaseMenu.equals(FEED_MONEY)) {

						// feed money
						System.out.println("Please Insert Money");
						Scanner userInput = new Scanner(System.in);
						String userFeedMoney = userInput.nextLine();
						BigDecimal userFeedMoneyBigD = new BigDecimal(userFeedMoney);
						feedMoney = feedMoney.add(userFeedMoneyBigD);
						System.out.println("Current Balance: " + feedMoney);

						// BigDecimal oldMoney = feedMoney;
						getDateAndTime(FEED_MONEY, userFeedMoneyBigD, feedMoney);

					} else if (purchaseMenu.equals(SELECT_PRODUCT)) {

						// show product list
						productList();

						// have the user input a product code
						System.out.println("Please Select Product");

						// Take users choice
						Scanner userCodeEntry = new Scanner(System.in);
						String usersChoice = userCodeEntry.nextLine();
						usersChoice = usersChoice.toUpperCase();

						// create variable to hold the current balance
						BigDecimal currentBalance = feedMoney;

						// call dispense method
						System.out.print(dispenseMethod(usersChoice));

						for (int i = 0; i < listOfItems.size(); i++) {
							if (listOfItems.get(i).getCode().equals(usersChoice)) {
								usersChoice = listOfItems.get(i).getName();
							}
						}

						// call the logfile method
						getDateAndTime(usersChoice, currentBalance, feedMoney);

					} else if (purchaseMenu.equals(FINISH_TRANSACTION)) {

						// create a current balance
						BigDecimal currentBalance = feedMoney;

						// System.out.println("THIS WORKS");
						ChangeCalculator changeCalculator = new ChangeCalculator();
						System.out.println(changeCalculator.makeChange(feedMoney));
						feedMoney = feedMoney.ZERO;

						// call the logfile method
						getDateAndTime(FINISH_TRANSACTION, currentBalance, feedMoney);
						break;
					}

				}
			} else if (choice.equals(MAIN_MENU_EXIT))
				break;
		}
	}

	private void loadInventoryItems() throws FileNotFoundException {
		// new file to call the vending machine file
		File inputFile = new File("vendingmachine.csv");

		// call the inventory file
		Scanner productListScanner = new Scanner(inputFile.getAbsoluteFile());

		// use a while loop to populate our list with the file
		while (productListScanner.hasNextLine()) {

			String currentLine = productListScanner.nextLine();
			String[] splitStuff = currentLine.split("\\|");
			String code = splitStuff[0];
			String name = splitStuff[1];
			BigDecimal cost = new BigDecimal(splitStuff[2]);
			String type = splitStuff[3];

			// adding items to the list of itmes
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
	}

	// main method
	public static void main(String[] args) throws FileNotFoundException {
		// make a new file for the audit
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();

	}

	// method to add to the file audit
	public static void getDateAndTime(String action, BigDecimal addMoney, BigDecimal currentMoney) {
		// import the date util to make date and time for the audit file
		// DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		//
		// Date dateObject = new Date();

		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime dateObject = LocalDateTime.now();

		if (action.equals(SELECT_PRODUCT)) {
			auditLine = dateFormat.format(dateObject) + " " + action + "     $" + addMoney + " $" + currentMoney + "\n";
		} else if (action.equals(FEED_MONEY)) {
			auditLine = dateFormat.format(dateObject) + " " + action + "         $" + addMoney + " $" + currentMoney
					+ "\n";
		} else {
			auditLine = dateFormat.format(dateObject) + " " + action + " $" + addMoney + " $" + currentMoney + "\n";
		}

		try {
			getNewAuditFile(auditLine);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// store the current and added balance in variables

	}

	public static void getNewAuditFile(String auditLine) throws IOException {

		try {
			File file = new File("log.txt");
			PrintWriter pw = null;

			if (file.exists()) {
				pw = new PrintWriter(new FileOutputStream(file.getAbsoluteFile(), true));

			} else {
				pw = new PrintWriter(file.getAbsoluteFile());

			}
			pw.append(auditLine);
			pw.flush();
			pw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	// our method for the purchase menu dispensing
	public String dispenseMethod(String usersChoice) {
		String thisDispenseMessage = "";
		int counter = 0;
		// validate users choice doesn't exist
		for (int i = 0; i < listOfItems.size(); i++) {
			if (!listOfItems.get(i).getCode().equals(usersChoice)) {
				counter++;
			}
		}
		if (counter == listOfItems.size()) {
			return "Code does not exist";
		}
		// for loop to validate quantity and make sure enough to pay
		for (int i = 0; i < listOfItems.size(); i++) {
			// Gets uersChoice code
			if (listOfItems.get(i).getCode().equals(usersChoice)) {
				// Check quantity of item
				if (listOfItems.get(i).getQuantity() < 1) {
					return "That item is sold out.";
				}
				// make sure have enough to pay
				else if (feedMoney.compareTo(listOfItems.get(i).getCost()) < 0) {
					return "You don't have enought depostited for that item.";
				} else {
					// executes vending
					int quantity = listOfItems.get(i).getQuantity();
					quantity--;
					listOfItems.get(i).setQuantity(quantity);
					feedMoney = feedMoney.subtract(listOfItems.get(i).getCost());
					thisDispenseMessage = listOfItems.get(i).getDispenseMessage();
				}
			}
		}
		return thisDispenseMessage;
	}

	public void productList() {
		for (ProductAbstract item : listOfItems) {
			String[] test = new String[] { item.getCode(), item.getName(), "$" + item.getCost().toString(),
					"Quantity " + item.getQuantity(), "Quantity " + "Sold Out" };
			int spacingSeperation = 3;
			int longest = "Little League Chew".length();
			int spacing = longest + spacingSeperation;
			// if else for is the product is sold out or not
			if (item.getQuantity() < 1) {
				System.out.print(String.format(
						"%-" + spacing + "s%-" + spacing + "s%-" + spacing + "s%-" + spacing + "s\n", test[0],
						test[1], test[2], test[4]));
			} else {
				System.out.print(String.format(
						"%-" + spacing + "s%-" + spacing + "s%-" + spacing + "s%-" + spacing + "s\n", test[0],
						test[1], test[2], test[3]));
			}
		}
	}
}
