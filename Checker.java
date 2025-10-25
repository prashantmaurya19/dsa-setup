import java.io.File;
import java.util.Scanner;

public class Checker {

  static String RED = "\u001b[31m";
  static String GREEN = "\u001b[32m";
  static String YELLOW = "\u001b[33m";
  static String BLUE = "\u001b[34m";
  static String MAGENTA = "\u001b[35m";
  static String CYAN = "\u001b[36m";
  static String WHITE = "\u001b[37m";
  static String RESET = "\u001b[0m";
  static String BLACK_LIGHT = "\u001b[90m";
  static String RED_LIGHT = "\u001b[91m";
  static String GREEN_LIGHT = "\u001b[92m";
  static String YELLOW_LIGHT = "\u001b[93m";
  static String BLUE_LIGHT = "\u001b[94m";
  static String MAGENTA_LIGHT = "\u001b[95m";
  static String CYAN_LIGHT = "\u001b[96m";
  static String WHITE_LIGHT = "\u001b[97m";

  static int count = 0;
  static int pass_count = 0;
  static int fail_count = 0;

  static <T> String getColorStr(T s, String clr) {
    return clr + s + RESET;
  }

  static class Util {

    static enum TextAlignment {
      CENTER,
      END,
      START;
    }

    public static void addSpace(TextAlignment at, StringBuilder sb, int n) {
      for (int i = 0; i < n; i++) {
        switch (at) {
          case START:
            {
              sb.insert(0, ' ');
              break;
            }
          case END:
            {
              sb.insert(sb.length(), ' ');
            }
          default:
            break;
        }
      }
    }

    public static String alignText(TextAlignment align, String s, int len) {
      if (len < 1) return s;

      StringBuilder sb = new StringBuilder(s);
      switch (align) {
        case CENTER:
          {
            addSpace(TextAlignment.START, sb, Math.floorDiv(len, 2));
            addSpace(TextAlignment.END, sb, Math.ceilDiv(len, 2));
            break;
          }
        case END:
          {
            addSpace(TextAlignment.START, sb, 0);
            addSpace(TextAlignment.END, sb, len);
            break;
          }
        case START:
          {
            addSpace(TextAlignment.START, sb, len);
            addSpace(TextAlignment.END, sb, 0);
            break;
          }
      }

      return sb.toString();
    }

    public static String truncate(String a, int truncate_length) {
      return alignText(TextAlignment.CENTER, a, truncate_length - a.length());
    }
  }

  public static class CheckHanders {
    // check each line is same
    public static void sameLine(Scanner test, Scanner in, Scanner ans) {
      String t1, t2;
      int t = test.nextInt();
      for (int i = 0; in.hasNextLine() && ans.hasNextLine(); i++) {
        t1 = in.nextLine();
        t2 = ans.nextLine();
        printResult(t1, t2, t1.equals(t2));
      }
    }
  }

  public static <T> void printResult(T out, T ans, boolean pass) {
    count++;
    System.out.println(
        count
            + ": "
            + (pass ? GREEN_LIGHT : RED_LIGHT)
            + Util.truncate(out + "", 18)
            + RESET
            + " | "
            + Util.truncate(ans + "", 18)
            + (pass ? GREEN_LIGHT : RED_LIGHT)
            + (pass ? " " : " ")
            + RESET);
    if (pass) pass_count++;
    else fail_count++;
  }

  public static void main(String[] args) {
    try {
      File test_file = new File("./in_out/testcase.txt");
      Scanner tin = new Scanner(test_file);
      File ans_file = new File("./in_out/answer.txt");
      Scanner ans = new Scanner(ans_file);
      Scanner in = new Scanner(System.in);
      CheckHanders.sameLine(tin, in, ans);
      System.out.println(
          getColorStr("pass: " + pass_count, GREEN_LIGHT)
              + (fail_count == 0 ? "" : getColorStr(" fail: " + fail_count, RED_LIGHT))
              + " "
              + getColorStr(
                  fail_count == 0 ? "" : "", fail_count == 0 ? GREEN_LIGHT : RED_LIGHT));
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
