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

public class ChangeCalculator {
    // The class where the makeChange method will be moved to

    public String makeChange(BigDecimal userBalance) {
        int quarters = 0;
        int dimes = 0;
        int nickels = 0;
        BigDecimal oneHundred = new BigDecimal(100);
        userBalance = userBalance.multiply(oneHundred);
        int balance = userBalance.intValue();
        while (balance > 0) {
            // Determines Quarters
            quarters = balance / 25;
            int totalCoins = quarters * 25;
            balance -= totalCoins;
            balance = balance % 25;
            // Determines dimes
            dimes = balance / 10;
            totalCoins = dimes / 10;
            balance -= totalCoins;
            balance = balance % 10;
            // Determines nickels
            nickels = balance / 5;
            totalCoins = nickels * 5;
            balance -= totalCoins;
            break;
        }
        return "Your change is " + quarters + " quarters and " + dimes + " dimes and " + nickels + " nickels.";
    }
}