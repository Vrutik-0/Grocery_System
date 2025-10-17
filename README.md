# Grocery_System (GrocerySystem_01.java)

A simple console-based grocery store application written in Java. It provides categories and items, allows customers to add items to a cart, modify or clear the cart, and generate a bill with payment options (Cash, Card, UPI).

This README explains how to compile and run the program, lists features and limitations, and suggests improvements.

## Features

- Category-based item browsing (Dairy, Snacks, Bakery, Vegetables, Fruits, Hygiene, Beverages)
- View product stock and prices
- Add items to cart (quantity tracking)
- View and modify cart (change item quantities)
- Clear cart (restores stock)
- Generate bill with:
  - Automatic total calculation
  - Discounts for totals and payment methods
  - Payment methods:
    - Cash (no discount)
    - Card (5% discount)
    - UPI (10% discount, shows a QR image if `qrcode.png` exists)
- Basic input validation for numeric fields (mobile, card number, CVV)

## Prerequisites

- Java Development Kit (JDK) 8 or later
- Terminal or console for running the program
- Optional: `qrcode.png` in the project directory to be shown for UPI payment

Notes:
- The application uses ANSI color escape sequences for console styling. Some terminals (for example, older Windows Command Prompt) may not render these colors correctly. Use a terminal that supports ANSI escape codes (Windows Terminal, PowerShell with ANSI support, Linux/macOS terminals) or remove/adjust color constants in the source code.

## Files

- `GrocerySystem_01.java` — Main program (console application)
- Optional: `qrcode.png` — QR image shown during UPI payment (place in project root)

## How to build and run

1. Save the `GrocerySystem_01.java` file in a directory.
2. Open a terminal in that directory.
3. Compile:

   javac GrocerySystem_01.java

4. Run:

   java GrocerySystem_01

Follow on-screen prompts to enter your name, mobile number, navigate categories, add items, and complete the purchase.

## Usage tips

- Enter numeric values when prompted (mobile number, menu choices, quantities, payment details).
- Mobile number validation requires exactly 10 digits.
- Card number validation requires exactly 16 digits, CVV requires exactly 3 digits.
- To use the UPI option and see a QR code dialog, place a valid `qrcode.png` image file in the same directory before running. The app uses Swing `JOptionPane` to display the QR.

## Known issues and limitations

(This section documents some behaviors you may observe and ideas for improvement.)

- initialStocks array: The code currently initializes `initialStocks` with dimensions `new int[stocks.length][stocks.length]`. This may not match the rectangular shape of `stocks` and could be revised to `new int[stocks.length][stocks[0].length]` or by copying row-by-row (the code does clone rows, but the declaration size is uneven).
- Cart price recalculation in `modifyCart` uses `(cartPrices[index] / currentQuantity) * newQuantity`. If `currentQuantity` were ever 0 this would cause division by zero — in usual flows currentQuantity > 0, but defensive checks would be good.
- Program flow: The code sometimes calls `Categories()` from within `manageCart()` which can create nested loops and complexity of control flow (re-entrant menu calls). Consider converting to a single main loop to simplify navigation.
- No persistent storage: All data (stock, cart) is in-memory; restarting the program resets stock and cart.
- Multi-user / concurrency: Not supported (single-user console app).
- Input robustness: While numeric validation exists, some edge cases may still cause unexpected behavior (e.g., using non-digit characters in some scanf calls).
- Terminal Base(No - UI) - As it is a simple learning project.

## Suggested improvements

- Improve program control flow (single main loop, return codes instead of nested calls).
- Add persistent storage (file-based or database) to track stock between runs.
- Add better error handling and unit tests.
- Replace fixed size cart arrays with dynamic lists (ArrayList) to avoid fixed limits.
- Extract product and cart item into classes for better object-oriented design.
- Improve UI (simple GUI or web frontend) for better user experience.
- Add localization support and currency formatting.

## Contributing

If you'd like to contribute:
- Fork the repository and create a branch for your changes.
- Make improvements, fix bugs, or add features (see Suggested improvements).
- Open a pull request describing your changes.

## License

MIT.
