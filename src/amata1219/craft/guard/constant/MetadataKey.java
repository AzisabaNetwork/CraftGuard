package amata1219.craft.guard.constant;

public class MetadataKey {

    public static final String LIGHTNING_STRIKE;
    public static final String FALLING_BLOCK;

    static {
        String name = "craft-guard";
        String separator = ":";
        String prefix = name + separator;

        LIGHTNING_STRIKE = prefix + "lightning-strike";
        FALLING_BLOCK = prefix + "falling-block";
    }

}
