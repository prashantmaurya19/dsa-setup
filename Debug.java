import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Debug {
  @FunctionalInterface
  public static interface MyCustomizer<T> {
    T getInput(Scanner in, int index);
  }

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

  static String ARGS_COLOR = CYAN;
  static String VALUE_COLOR = YELLOW;

  static final HashMap<Character, Character> brakets = new HashMap<>();
  static final HashMap<String, LinkedList<String>> cache = new HashMap<>();
  StackTraceElement pointer;
  static String[] solve_lines = init();

  private Debug() {}

  static String[] init() {
    brakets.put(')', '(');
    brakets.put(']', '[');
    brakets.put('}', '{');
    brakets.put('\'', '\'');
    brakets.put('"', '"');
    return getLines();
  }

  static void test(String... a) {
    String[] lines = getLines();
    // Line 10: Create an exception to get the stack trace
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

    // The first element is the current method (anotherMethod)
    // The second element is the calling method (someMethod)
    StackTraceElement caller = stackTrace[1];

    String className = caller.getClassName();
    String methodName = caller.getMethodName();
    String fileName = caller.getFileName();
    int lineNumber = caller.getLineNumber();

    System.out.println("Caller Class: " + className);
    System.out.println("Caller Method: " + methodName);
    System.out.println("Caller File: " + fileName);
    System.out.println("Caller Line: " + lineNumber);
    System.out.println("Caller Line Content: " + lines[lineNumber - 1]);
    System.out.println("Caller Line Statement: " + getStatement(lines[lineNumber - 1]));
    System.out.println(
        "Caller Line Parsed Statement: " + parseStatement(getStatement(lines[lineNumber - 1])));
  }

  @SafeVarargs
  static final <T> String _debug(T... args) {
    Exception e = new Exception();
    StackTraceElement[] stackTrace = e.getStackTrace();
    int ln = 0;
    for (int i = 0; i < stackTrace.length; i++) {
      // System.out.println(
      // i + " " + stackTrace[i].getMethodName() + " " + stackTrace[i].getLineNumber());
      if (stackTrace[i].getMethodName().equals("debug")
          || stackTrace[i].getMethodName().equals("debugln")) {
        ln = stackTrace[i + 1].getLineNumber();
      }
    }
    String[] args_name = parseStatement(getStatement(solve_lines[ln - 1])).toArray(new String[0]);
    return "["
        + getColorStr(ln, RED_LIGHT)
        + "] "
        + getColorStr("Debug", CYAN_LIGHT)
        + getColorStr(":", MAGENTA_LIGHT)
        + " "
        + "["
        + mapArgsNameWithValue(" = ", args_name, args)
        + "]";
  }

  static final <T> String mapKeyValue(String key, T val) {

    return getColorStr(key, ARGS_COLOR) + getColorStr(toValue(val), VALUE_COLOR);
  }

  static final <T> String mapArgsNameWithValue(String sep, String[] names, T[] values) {
    if (names.length == 0 || values.length == 0) return "";
    StringBuilder res = new StringBuilder();
    for (int i = 0; i < values.length - 1; i++) {
      res.append(
          mapKeyValue(
                  (names.length == 1 && values.length > 1 ? names[0] + "[" + i + "]" : names[i])
                      + sep,
                  values[i])
              + ", ");
    }
    res.append(
        mapKeyValue(
            (names.length == 1 && values.length > 1
                    ? names[0] + "[" + (values.length - 1) + "]"
                    : names[names.length - 1])
                + sep,
            values[values.length - 1]));
    return res.toString();
  }

  static <T> String toValue(T val) {
    if (val instanceof Object[]) {
      return Arrays.toString((Object[]) val);
    }
    return val.toString();
  }

  static <T> String getColorStr(T s, String clr) {
    return clr + s + RESET;
  }

  static LinkedList<String> parseStatement(String statement) {
    if (cache.containsKey(statement)) return cache.get(statement);
    Stack<Character> braket = new Stack<>();
    LinkedList<String> args = new LinkedList<>();
    int i = 0, temp = 0;
    for (; i < statement.length(); i++) {
      if (statement.charAt(i) == ',' && braket.size() == 0) {
        args.add(statement.substring(temp, i));
        temp = i + 1;
        continue;
      }
      if (brakets.containsValue(statement.charAt(i))) braket.push(statement.charAt(i));
      if (braket.size() > 0 && brakets.getOrDefault(statement.charAt(i), '-') == braket.peek())
        braket.pop();
    }

    if (temp < i) {
      args.add(statement.substring(temp, i));
    }

    cache.put(statement, args);
    return args;
  }

  static String getStatement(String line) {
    int l = 0, r = line.length() - 1;
    while (l < line.length()) {
      if (line.charAt(l) == '(') break;
      l++;
    }
    while (r > -1) {
      if (line.charAt(r) == ')') break;
      r--;
    }
    return line.substring(l + 1, r);
  }

  static String[] getLines() {
    Path filePath = Paths.get("Solve.java");
    try {
      List<String> lines = Files.readAllLines(filePath);
      return lines.toArray(new String[0]);
    } catch (Exception e) {
      return new String[] {};
    }
  }

  @SafeVarargs
  public static <T> void debug(T... args) {
    System.out.print(_debug(args));
  }

  @SafeVarargs
  static <T> void debugln(T... args) {
    System.out.println(_debug(args));
  }
}
