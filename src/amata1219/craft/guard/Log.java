package amata1219.craft.guard;

public class Log {

    public static void println(Object o) {
        System.out.println(o);
    }

    public static void printf(String category, Object... keysToValues) {
        if (keysToValues.length % 2 != 0) throw new IllegalArgumentException("Keys to values of " + category + " is invalid.");
        System.out.println(category);
        for (int i = 0; i < keysToValues.length; i += 2) System.out.println(keysToValues[i] + ": " + keysToValues[i + 1]);
    }

}
