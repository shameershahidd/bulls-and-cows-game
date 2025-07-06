import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

public class BullsAndCows {
  public static void main(String[] args) {
    int[] x = {-2, 7};
    int[] y = {9, 0, 2, 6};
    int[] z = {};
    int[] secret = {2, 0, 6, 9};
    int[] guessOne = {9, 5, 6, 2};
    int[] guessTwo = {2, 0, 6, 2};
    int[] guessThree = {1, 2, 3, 4, 5, 6};
    int[] guessFour = {1, 3, 4, 4, 0, 5};
    playBullsAndCows(123);
  }

  public static boolean contains(int[] a, int x) {
    for (int i = 0; i < a.length; i++) {
      if (x == a[i]) {
        return true;
      }
    }
    return false;
  }

  public static int[] generateSecretDigits(int x) {
    boolean included = false;
    Random r = new Random(x);
    int[] secretDigits = new int[4];
    for (int i = 0; i < 4; i++) {
      int a = r.nextInt(10);
      while (contains(secretDigits, a)) {
        if (a == 0 && included == false) {
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
    int length = num.length();
    int number[];

    if (length < 4) {
      number = new int[4];
      length = 4;
    } else {
      number = new int[length];
    }

    while (x > 0) {
      number[length - 1] = x % 10;
      length--;
      x = x / 10;
    }

    return number;
  }

  public static int getNumOfBulls(int[] x, int[] y) {
    int bulls = 0;

    if (x.length != y.length) {
      throw new IllegalArgumentException("Your guess and the secret number do not have the same number of elements.");
    } else {
      for (int i = 0; i < y.length; i++) {
        if (y[i] == x[i]) {
          for (int j = 0; j < x.length; j++) {
            if (x[j] == y[i]) {
              bulls++;
            }
          }
        }
      }
    }
    return bulls;
  }

  public static int getNumOfCows(int[] a, int[] b) {
    if (a.length != b.length) {
      throw new IllegalArgumentException("the arrays should have equal length");
    }
    int cows = 0;
    for (int i = 0; i < a.length; i++) {
      if (contains(b, a[i]) && a[i] != b[i]) {
        cows++;
      }
    }
    return cows;
  }

  public static void playBullsAndCows(int a) {
    int[] secret = generateSecretDigits(a);
    Scanner input = new Scanner(System.in);

    System.out.println("Welcome to Bulls and Cows.");
    System.out.println("Are you ready to play?");

    int guess = 1;
    int bulls = 0;
    int cows = 0;

    while (bulls <= 4) {
      System.out.println("Guess #" + guess + ". Please enter a four digit number:");
      int number = input.nextInt();
      if (number < 0) {
        System.out.println("You wasted a guess! You must enter a positive integer with at most four digits. Try again!");
      } else {
        int[] digits = extractDigits(number);
        if (digits.length != secret.length) {
          System.out.println("You wasted a guess! You must enter a positive integer with at most four digits. Try again!");
        } else {
          bulls = getNumOfBulls(secret, digits);
          cows = getNumOfCows(secret, digits);
          System.out.println("Bulls: " + bulls + ", Cows: " + cows);

          input.nextLine();
          if (bulls == 4) {
            System.out.println("Congratulations! You guessed the secret number in " + guess + " tries!");
            break;
          }
        }
        if (guess % 5 == 0) {
          System.out.println("This is taking a while... Ready to give up? (y/n)");
          String answer = input.nextLine();
          if (answer.equals("y")) {
            System.out.println("You gave up after " + guess + " tries...");
            break;
          }
        }
      }
      guess++;
    }
  }
}
