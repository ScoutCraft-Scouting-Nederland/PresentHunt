package nl.scoutcraft.presenthunt.lang;

import nl.scoutcraft.eagle.libs.locale.Internationalization;
import nl.scoutcraft.eagle.server.locale.CompoundMessage;
import nl.scoutcraft.eagle.server.locale.IMessage;
import nl.scoutcraft.eagle.server.locale.Message;
import nl.scoutcraft.eagle.server.locale.MessagePlaceholder;
import nl.scoutcraft.presenthunt.PresentHunt;

public final class Locale {

    private static final Internationalization LANG = PresentHunt.getInstance().getLang();

    public static IMessage<String> PREFIX = new Message(LANG, "prefix");

    public static IMessage<String> WAND_NAME = new Message(LANG, "wand.name");
    public static IMessage<String> WAND_LORE = new Message(LANG, "wand.lore");

    public static IMessage<String> COLLECTIBLE_NAME = new Message(LANG, "collectible.name");
    public static IMessage<String> COLLECTIBLE_LORE = new Message(LANG, "collectible.lore");

    public static IMessage<String> WAND_USAGE = new CompoundMessage(LANG, "usage.wand", new MessagePlaceholder("%prefix%", PREFIX));

    public static IMessage<String> PRESENT_ADD = new CompoundMessage(LANG, "present.add", new MessagePlaceholder("%prefix%", PREFIX));
    public static IMessage<String> PRESENT_REMOVE = new CompoundMessage(LANG, "present.remove", new MessagePlaceholder("%prefix%", PREFIX));
    public static IMessage<String> PRESENT_NOPRESENT = new CompoundMessage(LANG, "present.nopresent", new MessagePlaceholder("%prefix%", PREFIX));
    public static IMessage<String> PRESENT_ALREADYHERE = new CompoundMessage(LANG, "present.alreadyhere", new MessagePlaceholder("%prefix%", PREFIX));
    public static IMessage<String> PRESENT_FOUND = new CompoundMessage(LANG, "present.found", new MessagePlaceholder("%prefix%", PREFIX));
    public static IMessage<String> PRESENT_ALREADYFOUND = new CompoundMessage(LANG, "present.alreadyfound", new MessagePlaceholder("%prefix%", PREFIX));
    public static IMessage<String> PRESENT_CANTBREAK = new CompoundMessage(LANG, "present.cantbreak", new MessagePlaceholder("%prefix%", PREFIX));
    public static IMessage<String> PRESENT_COMPLETED = new CompoundMessage(LANG, "present.completed", new MessagePlaceholder("%prefix%", PREFIX));

    public static IMessage<String> COMMAND_NOPERM = new Message(LANG, "command.noPerm");
    public static IMessage<String> COMMAND_HELP = new CompoundMessage(LANG, "command.helpMenu", new MessagePlaceholder("%prefix%", PREFIX));
    public static IMessage<String> ACTION_BAR = new Message(LANG, "present.actionbar");

    private Locale() {
    }
}
