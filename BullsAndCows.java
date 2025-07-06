import java.util.Scanner;
import java.util.Random;

public class BullsAndCows {
    public static void main(String[] args) {
        playBullsAndCows(123);
    }

    public static boolean contains(int[] a, int x) {
        for (int i = 0; i < a.length; i++) {
            if (x == a[i]) return true;
        }
        return false;
    }

    public static int[] generateSecretDigits(int seed) {
        boolean included = false;
        Random r = new Random(seed);
        int[] secretDigits = new int[4];
        for (int i = 0; i < 4; i++) {
            int a = r.nextInt(10);
            while (contains(secretDigits, a)) {
                if (a == 0 && !included) {
                    included = true;
                    break;
                }
                a = r.nextInt(10);
            }
            secretDigits[i] = a;
        }
        return secretDigits;
    }

    public static int[] extractDigits(int x) {
        if (x < 0) x = -x;
        String num = Integer.toString(x);
        int len = num.length();
        int[] number;

        if (len < 4) {
            number = new int[4];
            len = 4;
        } else {
            number = new int[len];
        }

        while (x > 0) {
            number[len - 1] = x % 10;
            len--;
            x /= 10;
        }

        return number;
    }

    public static int getNumOfBulls(int[] secret, int[] guess) {
        int bulls = 0;
        if (secret.length != guess.length)
            throw new IllegalArgumentException("Mismatched array lengths.");
        for (int i = 0; i < guess.length; i++) {
            if (guess[i] == secret[i]) {
                for (int j = 0; j < secret.length; j++) {
                    if (secret[j] == guess[i]) bulls++;
                }
            }
        }
        return bulls;
    }

    public static int getNumOfCows(int[] secret, int[] guess) {
        if (secret.length != guess.length)
            throw new IllegalArgumentException("Mismatched array lengths.");
        int cows = 0;
        for (int i = 0; i < secret.length; i++) {
            if (contains(guess, secret[i]) && secret[i] != guess[i]) cows++;
        }
        return cows;
    }

    public static void playBullsAndCows(int seed) {
        int[] secret = generateSecretDigits(seed);
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to Bulls and Cows.");
        System.out.println("Are you ready to play?");

        int guessCount = 1;
        int bulls = 0;

        while (bulls < 4) {
            System.out.println("Guess #" + guessCount + ": Enter a four-digit number:");
            int number = input.nextInt();
            if (number < 0) {
                System.out.println("Invalid input. Try again.");
            } else {
                int[] guess = extractDigits(number);
                if (guess.length != secret.length) {
                    System.out.println("Invalid input. Try again.");
                } else {
                    bulls = getNumOfBulls(secret, guess);
                    int cows = getNumOfCows(secret, guess);
                    System.out.println("Bulls: " + bulls + ", Cows: " + cows);
                    input.nextLine();
                    if (bulls == 4) {
                        System.out.println("Congratulations! You guessed the secret number in " + guessCount + " tries!");
                        break;
                    }
                }
                if (guessCount % 5 == 0) {
                    System.out.println("Want to give up? (y/n)");
                    String answer = input.nextLine();
                    if (answer.equals("y")) {
                        System.out.println("You gave up after " + guessCount + " tries.");
                        break;
                    }
                }
            }
            guessCount++;
        }
    }
}
