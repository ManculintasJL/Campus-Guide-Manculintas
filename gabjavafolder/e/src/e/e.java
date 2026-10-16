package e;
import java.util.Scanner;
import java.util.Stack;

public class e {

    public static String reverseWord(String word) {
        Stack<Character> stack = new Stack<>();

        // Push each character into a stack
        for (int i = 0; i < word.length(); i++) {
            stack.push(word.charAt(i));
        }

        // Pop characters until the stack is empty
        StringBuilder reversed = new StringBuilder();
        while (!stack.isEmpty()) {
            reversed.append(stack.pop());
        }

        return reversed.toString();
    }

    public static boolean isPalindrome(String word) {
        String reversed = reverseWord(word);
        return word.equalsIgnoreCase(reversed);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a word: ");
        String word = scanner.nextLine();

        String reversedWord = reverseWord(word);
        System.out.println("Reversed word: " + reversedWord);

        if (isPalindrome(word)) {
            System.out.println(word + " is a palindrome!");
        } else {
            System.out.println(word + " is not a palindrome.");
        }

        scanner.close();
    }
}