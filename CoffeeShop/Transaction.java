import java.time.LocalDateTime; // For timestamps
import java.time.format.DateTimeFormatter; // Format and parse date and time 

public class Transaction {
    private LocalDateTime dateTime;
    private String itemName;
    private double price;
    private Double amountTendered; // Only for cash transactions
    private Double changeGiven; // Only for cash transactions
    private String cardType; // Only for card transactions

    // For cash transactions
    public Transaction(LocalDateTime dateTime, String itemName, double price, double amountTendered, double changeGiven) {
        this.dateTime = dateTime;
        this.itemName = itemName;
        this.price = price;
        this.amountTendered = amountTendered;
        this.changeGiven = changeGiven;
        this.cardType = null; // Explicitly setting cardType to null for clarity
    }

    // For card transactions
    public Transaction(LocalDateTime dateTime, String itemName, double price, String cardType) {
        this.dateTime = dateTime;
        this.itemName = itemName;
        this.price = price;
        this.cardType = cardType;
        this.amountTendered = null; // Not applicable for card transactions
        this.changeGiven = null; // Not applicable for card transactions
    }

    // Method to return formatted transaction details
    public String getFormattedDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (cardType == null) { // Cash transaction
            return String.format("%s, %s, %.2f, %.2f, %.2f",
                    dateTime.format(formatter), itemName, price, amountTendered, changeGiven);
        } else { // Card transaction
            return String.format("%s, %s, %.2f, %s",
                    dateTime.format(formatter), itemName, price, cardType);
        }
    }
}
