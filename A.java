import java.util.Scanner;

public class A {

  // @debug
//  static final Debug _debuggerpm = new Debug();
//
//  @SafeVarargs
//  static <T> void debug(T... args) {
//    System.out.print(_debuggerpm._debug(args));
//  }
//
//  @SafeVarargs
//  static <T> void debugln(T... args) {
//    System.out.println(_debuggerpm._debug(args));
//  }
//
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
    int n = in.nextInt(), mx = Integer.MIN_VALUE;
    for (int i = 0; i < n; i++) {
      mx = Math.max(mx, in.nextInt());
    }
    System.out.println(mx < in.nextInt() ? "NO" : "YES");
  }
}
