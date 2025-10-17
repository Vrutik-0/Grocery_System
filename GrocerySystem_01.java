import java.util.*;
import javax.swing.*;

public class GrocerySystem_01
{
    static String RESET = "\033[0m";
    static String BOLD = "\033[1m";
    static String ITALIC = "\033[3m";
    static String UNDERLINE = "\033[4m";
    static String RED = "\033[0;31m";
    static String GREEN = "\033[0;32m";
    static String BLUE = "\033[0;34m";
    static String YELLOW = "\033[0;33m";

    static JFrame frame = new JFrame();

    static Scanner in = new Scanner(System.in);
    static String name;
    static String[] categories = {"Dairy", "Snacks", "Bakery", "Vegetables", "Fruits", "Hygiene", "Beverages"};

    static String[][] items =
            {
                    {"Milk", "Cheese", "Butter", "Yogurt"},
                    {"Chips", "Cookies", "Noodles","Biscuits"},
                    {"Bread","Pastry", "Donut", "Muffin"},
                    {"Potato(Kg)", "Onion(Kg)", "Tomato(Kg)","Capsicum(Kg)"},
                    {"Apple(Dozen)", "Banana(Dozen)", "Orange(Dozen)", "Mango(Dozen)"},
                    {"Soap", "Shampoo", "Toothpaste","Sanitizer"},
                    {"Juice", "Soft Drink", "Energy Drink", "Water"}
            };

    static int[][] stocks =
            {
                    {20, 25, 25, 15},
                    {30, 20, 25, 18},
                    {15, 12, 20, 15},
                    {15, 13, 10, 8},
                    {7, 8, 5, 6},
                    {12, 10, 8, 10},
                    {10, 20, 15,15}
            };

    static double[][] prices =
            {
                    {32.50, 45, 40, 36.50},
                    {8.50, 21, 14, 9},
                    {35, 25, 12.50, 14.50},
                    {23, 28.50, 24, 55},
                    {59, 54, 64.50, 89.50},
                    {33.50, 54.50, 65, 45},
                    {20, 38.50, 55, 16.50}
            };

    static String[] cartItems = new String[50];
    static int[] cartQuantities = new int[50];
    static double[] cartPrices = new double[50];
    static int cartSize = 0;

    static int[][] initialStocks = new int[stocks.length][stocks.length];

    public static void main(String[] Args)
    {
        System.out.println(BLUE + BOLD + "-----------------------------------------" + RESET);
        System.out.println(BOLD+"|"+ITALIC+"\tWelcome to the Grocery Store\t"+RESET+"|");
        System.out.println(BLUE + BOLD + "-----------------------------------------" + RESET);

        System.out.print(UNDERLINE+"\nEnter your Name: "+RESET);
        name = in.nextLine();
        while(true)
        {
            System.out.print(UNDERLINE+"Enter your Mobile Number: "+RESET);
            if (in.hasNextLong())
            {
                long mobile = in.nextLong();

                if(String.valueOf(mobile).length() == 10)
                {
                    break;
                }
                else
                {
                    System.out.println(RED+"Invalid Mobile Number. Try again."+RESET);
                }
            }
            else
            {
                System.out.println(RED+"Invalid input. Please enter a numeric value."+RESET);
                in.next();
            }
        }
        Categories();
    }
    static void Categories()
    {
        for (int i = 0; i < stocks.length; i++)
        {
            initialStocks[i] = stocks[i].clone();
        }

        int categoryChoice;

        while (true)
        {
            System.out.println(UNDERLINE+"\nCategories:"+RESET);
            for (int i = 0; i < categories.length; i++)
            {
                System.out.println((i + 1) + ". " + categories[i]);
            }
            System.out.println(UNDERLINE+BOLD+"0. Exit"+RESET);
            System.out.print("Choose a category: ");
            if (in.hasNextInt())
            {
                categoryChoice = in.nextInt();
                if (categoryChoice == 0)
                {
                    manageCart();
                    if (categoryChoice == 0) break;
                }
                if (categoryChoice > 0 && categoryChoice <= categories.length) {
                    manageSubCategory(categoryChoice - 1);
                }
                else
                {
                    System.out.println(RED+"Invalid choice. Try again."+RESET);
                }
            }
            else
            {
                System.out.println(RED+"Invalid input. Please enter a numeric value."+RESET);
                in.next();
            }
        }
    }

    static void manageSubCategory(int categoryIndex)
    {
        while (true)
        {
            System.out.println(YELLOW+"\nItems in " +BOLD+ categories[categoryIndex] + ":"+RESET);
            for (int i = 0; i < items[categoryIndex].length; i++)
            {
                System.out.println((i + 1) + ". " + items[categoryIndex][i] + " - Stock: " + stocks[categoryIndex][i] + ", Price: Rs " + prices[categoryIndex][i]);
            }
            System.out.println(BOLD+"0. Go back"+RESET);
            System.out.print("Choose an item to buy or 0 to return: ");
            if (in.hasNextInt())
            {
                int itemChoice = in.nextInt();
                if (itemChoice == 0) break;
                if (itemChoice > 0 && itemChoice <= items[categoryIndex].length) {
                    buyItem(categoryIndex, itemChoice - 1);
                }
                else
                {
                    System.out.println(RED+"Invalid choice. Try again."+RESET);
                }
            }
            else
            {
                System.out.println(RED+"Invalid input. Please enter a numeric value."+RESET);
                in.next();
            }
        }
    }

    static void buyItem(int categoryIndex, int itemIndex)
    {
        System.out.print("Enter quantity to buy (0 to return): ");
        if (in.hasNextInt())
        {
            int quantity = in.nextInt();
            if (quantity == 0) return;
            if (quantity > 0 && quantity <= stocks[categoryIndex][itemIndex]) {
                stocks[categoryIndex][itemIndex] -= quantity;

                boolean itemExists = false;
                for (int i = 0; i < cartSize; i++)
                {
                    if (cartItems[i].equals(items[categoryIndex][itemIndex]))
                    {
                        cartQuantities[i] += quantity;
                        cartPrices[i] += quantity * prices[categoryIndex][itemIndex];
                        itemExists = true;
                        break;
                    }
                }

                if (!itemExists)
                {
                    cartItems[cartSize] = items[categoryIndex][itemIndex];
                    cartQuantities[cartSize] = quantity;
                    cartPrices[cartSize] = quantity * prices[categoryIndex][itemIndex];
                    cartSize++;
                }

                System.out.println(GREEN+"Added to cart successfully."+RESET);
            }
            else
            {
                System.out.println(RED+"Invalid quantity. Available stock: "+RESET + stocks[categoryIndex][itemIndex]);
            }
        }
        else
        {
            System.out.println(RED+"Invalid input. Please enter a numeric value."+RESET);
            in.next();
        }
    }

    static void manageCart()
    {
        while (true)
        {
            System.out.println(UNDERLINE+"\nCart Options:"+RESET);
            System.out.println("1. View Cart");
            System.out.println("2. Modify Cart");
            System.out.println("3. Return to Categories");
            System.out.println("4. Clear Cart");
            System.out.println("5. Generate Bill");
            System.out.print(ITALIC+"\n Choose an option: "+RESET);
            if (in.hasNextInt()) {
                int choice = in.nextInt();
                switch (choice)
                {
                    case 1:
                        viewCart();
                        break;
                    case 2:
                        modifyCart();
                        break;
                    case 3:
                        Categories();
                        break;
                    case 4:
                        clearCart();
                        break;
                    case 5:
                        generateBill();
                        break;
                    default:
                        System.out.println(RED+"Invalid choice. Try again."+RESET);
                }
            }
            else
            {
                System.out.println(RED+"Invalid input. Please enter a numeric value."+RESET);
                in.next();
            }
        }
    }

    static void viewCart()
    {
        System.out.println(BOLD+"\nYour Cart:"+RESET);
        if (cartSize == 0)
        {
            System.out.println("Cart is empty.");
            return;
        }

        for (int i = 0; i < cartSize; i++)
        {
            System.out.println((i + 1) + ". " + cartItems[i] + " - Quantity: " + cartQuantities[i] + ", Price: Rs " + cartPrices[i]);
        }
    }

    static void modifyCart()
    {
        viewCart();

        if (cartSize == 0)
            return;

        System.out.print("Enter item number to modify: ");
        if (in.hasNextInt())
        {
            int itemNumber = in.nextInt();

            if (itemNumber <= 0 || itemNumber > cartSize)
            {
                System.out.println(RED+"Invalid item number."+RESET);
                return;
            }

            int index = itemNumber - 1;

            for (int i = 0; i < items.length; i++)
            {
                for (int j = 0; j < items[i].length; j++)
                {
                    if (items[i][j].equals(cartItems[index]))
                    {
                        System.out.println("Available Stock in system: "+UNDERLINE+stocks[i][j]+RESET);
                    }
                }
            }

            System.out.print("Enter new quantity: ");
            if (in.hasNextInt())
            {
                int newQuantity = in.nextInt();

                if (newQuantity < 0)
                {
                    System.out.println(RED+"Invalid quantity."+RESET);
                    return;
                }

                int currentQuantity = cartQuantities[index];
                int difference = newQuantity - currentQuantity;

                for (int i = 0; i < items.length; i++)
                {
                    for (int j = 0; j < items[i].length; j++)
                    {
                        if (items[i][j].equals(cartItems[index]))
                        {
                            if (difference > 0 && difference > stocks[i][j])
                            {
                                System.out.println(RED+"Insufficient stock available."+RESET);
                                return;
                            }
                            stocks[i][j] -= difference;
                        }
                    }
                }

                cartQuantities[index] = newQuantity;
                cartPrices[index] = (cartPrices[index] / currentQuantity) * newQuantity;
                System.out.println(GREEN+"Quantity updated successfully."+RESET);
            }
            else
            {
                System.out.println(RED+"Invalid input. Please enter a numeric value."+RESET);
                in.next();
            }
        }
        else
        {
            System.out.println(RED+"Invalid input. Please enter a numeric value."+RESET);
            in.next();
        }
    }

    static void clearCart()
    {
        for (int i = 0; i < cartSize; i++)
        {
            for (int j = 0; j < items.length; j++)
            {
                for (int k = 0; k < items[j].length; k++)
                {
                    if (items[j][k].equals(cartItems[i]))
                    {
                        stocks[j][k] += cartQuantities[i];
                    }
                }
            }
        }
        cartSize = 0;
        System.out.println(GREEN+"Cart cleared successfully."+RESET);
    }

    static void generateBill()
    {
        System.out.println(BOLD + "\nYour Bill:" + RESET);
        double total = 0;
        double discount = 0;
        System.out.printf("%-20s %-10s %-10s\n", ITALIC + "Item", "Quantity", "     Price" + RESET);
        System.out.println("------------------------------------------");
        for (int i = 0; i < cartSize; i++)
        {
            System.out.printf("%-20s %-10d Rs %-10.2f\n", cartItems[i], cartQuantities[i], cartPrices[i]);
            total += cartPrices[i];
        }
        System.out.println("------------------------------------------");

        if (total > 2000)
        {
            discount += total * 0.02;
            System.out.println(BOLD + "Discount: Rs " + UNDERLINE +discount+ RESET + "\n");
        }

        System.out.println(BOLD+UNDERLINE+"Your Total bill is: "+total+RESET);

        System.out.println(GREEN + "\nEnter Payment Method: " + RESET);
        System.out.println("1. Cash");
        System.out.println("2. Card "+UNDERLINE+"(5% discount)"+RESET);
        System.out.println("3. UPI " +UNDERLINE+"(10% discount)"+RESET);

        int paymentMethod;
        while (true)
        {
            if (in.hasNextInt())
            {
                paymentMethod = in.nextInt();
                break;
            }
            else
            {
                System.out.println(RED+"Invalid input. Please enter a numeric value."+RESET);
                in.next();
            }
        }

        if (paymentMethod == 1)
        {
            System.out.println("Enter the amount: ");
            double amount = in.nextDouble();
            if (amount < total)
            {
                System.out.println(RED + "Insufficient amount." + RESET);
                return;
            }
            System.out.println(ITALIC+"Change: Rs "+ (amount - total)+RESET);
        }
        else if (paymentMethod == 2)
        {
            discount += total * 0.05;
            total -= discount;
            System.out.println("------------------------------------------");
            System.out.println(BOLD + "Total after discount: Rs " + UNDERLINE + total + RESET);

            long cardNumber;
            while (true)
            {
                System.out.print("Enter card number: ");
                cardNumber = in.nextLong();
                if (String.valueOf(cardNumber).length() == 16)
                {
                    break;
                }
                else
                {
                    System.out.println(RED + "Invalid card number. Must be 16 digits." + RESET);
                }
            }

            int cvv;
            while (true)
            {
                System.out.print("Enter CVV: ");
                cvv = in.nextInt();
                if (String.valueOf(cvv).length() == 3)
                {
                    break;
                }
                else
                {
                    System.out.println(RED + "Invalid CVV. Must be 3 digits." + RESET);
                }
            }

            System.out.print("Enter expiry date (MM/YY): ");
            String expiryDate = in.next();
            System.out.println(GREEN + "Payment successful." + RESET);
        }
        else if (paymentMethod == 3)
        {
            discount += total * 0.10;
            total -= discount;
            System.out.println("------------------------------------------");
            System.out.println(BOLD + "Total after discount: Rs " + UNDERLINE + total + RESET);
            ImageIcon img = new ImageIcon("qrcode.png"); // Ensure you have a QR code image named "qrcode.png" in the project directory
            JOptionPane.showMessageDialog(frame, null,"UPI Scanner", JOptionPane.INFORMATION_MESSAGE, img);
        }
        else
        {
            System.out.println(RED + "Invalid choice." + RESET);
        }
        System.out.println(BOLD + GREEN + "\n Thank you "+ name +" for shopping with us." + RESET);
        System.out.println(BOLD + "Visit Again!" + RESET);
        System.exit(0);
    }
}