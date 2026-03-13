import java.util.Scanner;
import java.util.Stack;

// PalindromeChecker class encapsulating palindrome logic
class PalindromeChecker {

    // Method to check palindrome using Stack
    public boolean checkPalindrome(String input) {

        Stack<Character> stack = new Stack<>();
        String cleaned = input.replaceAll("\\s+", "").toLowerCase();

        // Push characters to stack
        for (int i = 0; i < cleaned.length(); i++) {
            stack.push(cleaned.charAt(i));
        }

        // Compare characters
        for (int i = 0; i < cleaned.length(); i++) {
            if (cleaned.charAt(i) != stack.pop()) {
                return false;
            }
        }

        return true;
    }
}

public class Rept{

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        PalindromeChecker checker = new PalindromeChecker();

        System.out.println("Enter a string to check if it is a palindrome:");
        String input = scanner.nextLine();

        boolean result = checker.checkPalindrome(input);

        if (result) {
            System.out.println("The given string is a Palindrome.");
        } else {
            System.out.println("The given string is NOT a Palindrome.");
        }

        scanner.close();
    }
}