
    import java.util.ArrayDeque;
import java.util.Deque;

    public class DequePalindromeChecker {
        public static boolean checkPalindrome(String str) {
            Deque<Character> deque = new ArrayDeque<>();
            for (char c : str.toCharArray()) {
                deque.addLast(c);
            }
            while (deque.size() > 1) {
                if (!deque.removeFirst().equals(deque.removeLast())) {
                    return false;
                }
            }
            return true;
        }

        public static void main(String[] args) {
            String str = "racecar";
            System.out.println(checkPalindrome(str));
        }
    }

