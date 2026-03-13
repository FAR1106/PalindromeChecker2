import java.util.*;

/*
 =====================================================
 MAIN CLASS - UseCase12PalindromeCheckerApp
 =====================================================
 Use Case 12: Strategy Pattern for Palindrome Algorithms

 Description:
 Demonstrates how different palindrome validation
 algorithms can be selected dynamically at runtime
 using the Strategy Design Pattern.

 At this stage the application:
 - Defines a common PalindromeStrategy interface
 - Implements StackStrategy and DequeStrategy
 - Injects the strategy at runtime
 - Executes the chosen algorithm
 =====================================================
*/

public class Rept{

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("==== Palindrome Checker (Strategy Pattern) ====");
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        System.out.println("\nChoose Algorithm:");
        System.out.println("1. Stack Strategy");
        System.out.println("2. Deque Strategy");
        System.out.print("Enter choice: ");

        int choice = scanner.nextInt();

        PalindromeStrategy strategy;

        if (choice == 1) {
            strategy = new StackStrategy();
        } else {
            strategy = new DequeStrategy();
        }

        PalindromeChecker checker = new PalindromeChecker(strategy);

        boolean result = checker.check(input);

        if (result)
            System.out.println("Result: Palindrome");
        else
            System.out.println("Result: Not a Palindrome");

        scanner.close();
    }
}

/*
 =====================================================
 INTERFACE - PalindromeStrategy
 =====================================================
 Contract for all palindrome algorithms
*/

interface PalindromeStrategy {

    boolean isPalindrome(String input);
}

/*
 =====================================================
 CLASS - StackStrategy
 =====================================================
 Uses Stack data structure
*/

class StackStrategy implements PalindromeStrategy {

    public boolean isPalindrome(String input) {

        Stack<Character> stack = new Stack<>();

        for (char c : input.toCharArray()) {
            stack.push(c);
        }

        String reversed = "";

        while (!stack.isEmpty()) {
            reversed += stack.pop();
        }

        return input.equals(reversed);
    }
}

/*
 =====================================================
 CLASS - DequeStrategy
 =====================================================
 Uses Deque data structure
*/

class DequeStrategy implements PalindromeStrategy {

    public boolean isPalindrome(String input) {

        Deque<Character> deque = new ArrayDeque<>();

        for (char c : input.toCharArray()) {
            deque.addLast(c);
        }

        while (deque.size() > 1) {

            if (deque.removeFirst() != deque.removeLast()) {
                return false;
            }
        }

        return true;
    }
}

/*
 =====================================================
 CLASS - PalindromeChecker (Context Class)
 =====================================================
 Uses injected strategy
*/

class PalindromeChecker {

    private PalindromeStrategy strategy;

    public PalindromeChecker(PalindromeStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean check(String input) {
        return strategy.isPalindrome(input);
    }
}