package amata1219.craft.guard.constant;

public class MetadataKey {


    public static final String TRIDENT;
    public static final String FALLING_BLOCK;

    static {
        String name = "craft-guard";
        String separator = ":";
        String prefix = name + separator;

        TRIDENT = prefix + "trident";
        FALLING_BLOCK = prefix + "falling-block";
    }

}
