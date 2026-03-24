import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class QSPalindromeChecker {
    public static void main(String[] args) {
        String str = "ABCDE";

        Queue<Character> queue = new LinkedList<>();
        Stack<Character> stack = new Stack<>();

        for (char c : str.toCharArray()) {
            queue.add(c);
            stack.push(c);
        }

        System.out.println("FIFO (Queue):");
        while (!queue.isEmpty()) {
            System.out.print(queue.poll() + " ");
        }
        System.out.println();

        System.out.println("LIFO (Stack):");
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }
        System.out.println();
    }
}
