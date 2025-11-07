import java.util.Scanner;

public class B {

  // @debug
  static final Debug _debuggerpm = new Debug();

  @SafeVarargs
  static <T> void debug(T... args) {
    System.out.print(_debuggerpm._debug(args));
  }

  @SafeVarargs
  static <T> void debugln(T... args) {
    System.out.println(_debuggerpm._debug(args));
  }

  // @debugend

  static void takeArrayInput(Integer[] a, Scanner in) {
    for (int i = 0; i < a.length; i++) {
      a[i] = in.nextInt();
    }
  }

  static void takeArrayInput(int[] a, Scanner in) {
    for (int i = 0; i < a.length; i++) {
      a[i] = in.nextInt();
    }
  }

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int t = in.nextInt();
    postT(in);
    for (int z = 0; z < t; z++) {
      solver(in);
    }
  }

  public static void postT(Scanner in) {}

  public static void solver(Scanner in) {
    int n = in.nextInt();
    Integer[] a = new Integer[n];
    for (int i = 0; i < n; i++) {
      a[i] = in.nextInt();
    }
    for (int i = n - 1; i > 0; i--) {
      for (int j = 0; j < i; j++) {
        if (((a[i] % a[j]) & 1) == 0) {
          System.out.println(a[j] + " " + a[i]);
          return;
        }
      }
    }
    System.out.println(-1);
  }
}
