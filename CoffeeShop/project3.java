import java.util.ArrayList; //import arraylist for storing lists of objects
import java.io.FileReader; //filereader for reading text files
import java.io.BufferedReader; //buffers chaaracter to read arrays etc.
import java.io.FileWriter; //for writing to files
import java.io.PrintWriter; //write to a certain destination
import java.io.IOException; //handle errors when operation fails
import java.time.LocalDateTime; //for time stamps 

public class project3 {
    private static ArrayList<Transaction> transactions = new ArrayList<>(); //Static list to hold all transactions

    //Display menu of items
    public static void displayMenu(ArrayList<MenuItem> items) { //display menu of items from inventory
        System.out.println("M E N U");
        System.out.println("================");
        for (int i = 0; i < items.size(); i++) { // loop through each menu item
            MenuItem item = items.get(i); //load current menu item
            // print menu item no., name and price
            System.out.printf("%d. %-25s  %.2f\n", i + 1, item.getName(), item.getPrice());// i +1 first so menu doesnt start at 0
        }
        System.out.printf("%d. Exit\n", items.size() + 1);
        System.out.println("================");
    }
    // complete a transaction based on users menu choice
    public static void completeTransaction(int choice, ArrayList<MenuItem> items) {
        Keyboard key = new Keyboard(); // import keyboard.java
        MenuItem item = items.get(choice - 1); // Adjust user's choice to zero-based index
         // Print the selected item name and price
        System.out.printf("You selected: %s - $%.2f\n", item.getName(), item.getPrice());

        System.out.println("Select Payment Method:");
        System.out.println("1. Cash");
        System.out.println("2. Card");
        System.out.println("3. Staff Discount");  // additional functionality
        //prompt for payment choice and validate input
        int paymentChoice = key.readInteger("Choose an option: ", "Error: Invalid input. Please enter 1 for Cash, 2 for Card or 3 for Staff.", 1, 3);

        // Declare cardTypeChoice and cardType at the start
        int cardTypeChoice = 0;
        String cardType = null;

        if (paymentChoice == 1) { // Cash option
            double amountTendered = key.readDouble("Amount tendered: $", "Error: Invalid amount. Please enter a valid number.");
            if (amountTendered < item.getPrice()) {
                System.out.println("Insufficient Cash");// if amount tendered is less than price
            } else {
                double change = amountTendered - item.getPrice();//work out the remaining change
                System.out.printf("Receipt:\n%s - $%.2f\nCash Tendered: $%.2f\nChange: $%.2f\nThank you for your purchase!\n",
                                  item.getName(), item.getPrice(), amountTendered, change);
                transactions.add(new Transaction(LocalDateTime.now(), item.getName(), item.getPrice(), amountTendered, change));// record it onto transactions.txt
            }
            } else if (paymentChoice == 2) { // Card option
            System.out.println("Enter Card Type (1 for Visa, 2 for MasterCard):");
            cardTypeChoice = key.readInteger("Card type: ", "Error: Invalid input. Please enter 1 for Visa or 2 for MasterCard.", 1, 2);
            cardType = (cardTypeChoice == 1) ? "Visa" : "MasterCard";// if 1 or true visa, if not mastercard
            System.out.printf("Receipt:\n%s - $%.2f\nPaid by: %s Card\nThank you for your purchase!\n", item.getName(), item.getPrice(), cardType);
            transactions.add(new Transaction(LocalDateTime.now(), item.getName(), item.getPrice(), cardType));
        } else if (paymentChoice == 3) { // Staff Discount option additional functionality
            System.out.println("Applying Staff Discount...");
            double discountRate = 0.50; // 50% discount for staff
            double discountedPrice = item.getPrice() * (1 - discountRate);
            System.out.printf("Original Price: $%.2f\nDiscounted Price: $%.2f\n", item.getPrice(), discountedPrice);
            
            System.out.println("Select Final Payment Method for Discounted Price:");//pay discounted price with remainder of money
            System.out.println("1. Cash");
            System.out.println("2. Card");
            int finalPaymentChoice = key.readInteger("Choose an option: ", "Error: Invalid input. Please enter 1 for Cash or 2 for Card.", 1, 2);
            
            if (finalPaymentChoice == 1) { // Cash option for discounted price
                double amountTendered = key.readDouble("Amount tendered: $", "Error: Invalid amount. Please enter a valid number.");
                if (amountTendered < discountedPrice) {
                    System.out.println("Error: Insufficient amount tendered. Transaction cancelled. Please start again.");
                } else {
                    double change = amountTendered - discountedPrice;
                    System.out.printf("Receipt:\n%s - $%.2f\nCash Tendered: $%.2f\nChange: $%.2f\nThank you for your purchase, Please also grab a sandwich from our counter for your lunch!\n",
                                      item.getName(), discountedPrice, amountTendered, change);
                    transactions.add(new Transaction(LocalDateTime.now(), item.getName(), discountedPrice, amountTendered, change));
                }
            } else if (finalPaymentChoice == 2) { // Card option for discounted price
                System.out.println("Enter Card Type (1 for Visa, 2 for MasterCard):");
                cardTypeChoice = key.readInteger("Card type: ", "Error: Invalid input. Please enter 1 for Visa or 2 for MasterCard.", 1, 2);
                cardType = (cardTypeChoice == 1) ? "Visa" : "MasterCard";
                System.out.printf("Receipt:\n%s - $%.2f\nPaid by: %s Card\nThank you for your purchase, Please also grab a sandwich from our counter for your lunch!\n", item.getName(), discountedPrice, cardType);
                transactions.add(new Transaction(LocalDateTime.now(), item.getName(), discountedPrice, cardType));
            }
        } else {
            System.out.println("Invalid Payment Method Selected.");
        }
    }
    // Read from inventory file
    public static void readinventoryFile(String fileName, ArrayList<MenuItem> items) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) { //Read each line till end of file
                String[] tokens = line.split(","); // price and item is split by ","
                String name = tokens[0]; //print first item , e.g item[0]
                double price = Double.parseDouble(tokens[1]); // print second item but change it to a double using parse
                items.add(new MenuItem(name, price));// Add it to MenuItem
            }
        } catch (IOException e) {
            System.out.println("Error - Cannot read from file " + fileName);// Handle errors
        }
    }

    // Method to write transactions to a file
    private static void writeTransactionsToFile() {
        // Open transaction.txt in append mode
        try (PrintWriter out = new PrintWriter(new FileWriter("transactions.txt", true))) {
            for (Transaction transaction : transactions) { // go over every transaction
                out.println(transaction.getFormattedDetails()); // Write the transactions to file
            }
            System.out.println("Transactions have been saved to the file.");
        } catch (IOException e) {
            System.err.println("Error writing transactions to file: " + e.getMessage());// Handle errors
        }
    }

    //Main method
    public static void main(String[] args) {
        ArrayList<MenuItem> menuItems = new ArrayList<>();// Create a list to store menu items
        readinventoryFile("inventory.txt", menuItems);// Read inventory files
        int EXIT = menuItems.size() + 1; // add an exit after all the menu items
        Keyboard key = new Keyboard(); 

        displayMenu(menuItems); // Display menu to user
        //Enter choice within high and low
        int choice = key.readInteger("Enter choice: ", "Error invalid input.", 1, EXIT);
        // As long as choice is not exit execute the following
        while (choice != EXIT) {
            completeTransaction(choice, menuItems);
            displayMenu(menuItems);
            //Prompt next choice
            choice = key.readInteger("Enter choice: ", "Error invalid input.", 1, EXIT);
        }
        writeTransactionsToFile();// Write transactions to file once user exits
        System.out.println("Goodbye, Call again!");
    }
}
