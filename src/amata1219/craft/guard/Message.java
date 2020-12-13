package amata1219.craft.guard;

import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

public enum Message {

    CAN_NOT_USE("use"),
    CAN_NOT_BREAK("break"),
    CAN_NOT_PLACE("place"),
    CAN_NOT_HARM("harm");

    private final String text;

    private Message(String actionName) {
        text = "" + RED + BOLD + "Hey!" + RESET + GRAY + "Sorry, but you can't " + actionName + " that here.";
    }

    public void message(Player recipient) {
        recipient.sendMessage(text);
    }

}
