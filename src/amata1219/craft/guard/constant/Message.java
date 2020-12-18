package amata1219.craft.guard.constant;

import static org.bukkit.ChatColor.*;

public class Message {

    public static final String CAN_NOT_BREAK;
    public static final String CAN_NOT_PLACE;
    public static final String CAN_NOT_HARM;
    public static final String CAN_NOT_OPEN;
    public static final String CAN_NOT_CHANGE;
    public static final String CAN_NOT_USE;

    static {
        String format = "" + RED + BOLD + "Hey!" + RESET + GRAY + " Sorry, but you can't %s that here.";
        CAN_NOT_BREAK = format.formatted("break");
        CAN_NOT_PLACE = format.formatted("place");
        CAN_NOT_HARM = format.formatted("harm");
        CAN_NOT_OPEN = format.formatted("open");
        CAN_NOT_CHANGE = format.formatted("change");
        CAN_NOT_USE = format.formatted("use");
    }

}
