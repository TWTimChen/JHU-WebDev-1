package assignment2;

public class IntegerProduct {

    // Method that returns the product of two integers
    public static int multiply(int a, int b) {
        return a * b;
    }

    public static void main(String[] args) {
        // Ensure two arguments are provided
        if (args.length != 2) {
            System.out.println("Please provide exactly two integer arguments.");
            return;
        }

        try {
            // Convert arguments to integers
            int num1 = Integer.parseInt(args[0]);
            int num2 = Integer.parseInt(args[1]);

            // Calculate product
            int result = multiply(num1, num2);

            // Print result. If it's negative, print it inside parenthesis.
            if (result < 0) {
                System.out.println("(" + Math.abs(result) + ")");
            } else {
                System.out.println(result);
            }

        } catch (NumberFormatException e) {
            System.out.println("Please ensure you provide valid integer arguments.");
        }
    }
}
