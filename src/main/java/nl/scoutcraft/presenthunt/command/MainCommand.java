package nl.scoutcraft.presenthunt.command;

import nl.scoutcraft.presenthunt.command.sub.Wand;
import nl.scoutcraft.presenthunt.lang.Locale;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * It's a class that implements the Bukkit CommandExecutor and TabExecutor interfaces, and it's used to
 * handle the /hunt command
 */
public class MainCommand implements CommandExecutor, TabExecutor {
    private static final List<SubCommand> COMMAND_LIST = new ArrayList<>();
    private static final List<String> COMMANDS = new ArrayList<>();

    public MainCommand() {
        COMMAND_LIST.add(new Wand());
        COMMAND_LIST.forEach(subCommand -> Collections.addAll(COMMANDS, subCommand.getCommands()));
    }

    /**
     * If the sender is a player, and the command is valid, and the player has permission, then execute
     * the command
     * 
     * @param sender The CommandSender who executed the command.
     * @param command The command that was executed.
     * @param string The command that was typed.
     * @param args The arguments that the player typed in.
     * @return A boolean
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String string, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("No Player!");
            return true;
        }

        if (args.length == 0) {
            Locale.COMMAND_HELP.send(player);
            return true;
        }

        SubCommand subCommand = getCommand(args[0]);

        if (subCommand == null) {
            Locale.COMMAND_HELP.send(player);
            return true;
        }

        if (!player.hasPermission(subCommand.getPermission())) {
            Locale.COMMAND_NOPERM.send(player);
            return true;
        }

        subCommand.onCommand(player, subArray(1, args));

        return true;
    }

    /**
     * Given an array and a starting index, return a new array containing all the elements of the
     * original array starting at the given index.
     * 
     * @param startIndex The index of the first element to copy.
     * @param args The array of arguments to be parsed.
     * @return A new array of strings that is a copy of the original array, but with the first element
     * removed.
     */
    public static String[] subArray(int startIndex, String[] args) {
        if (startIndex >= args.length) return new String[0];

        String[] out = new String[args.length - startIndex];
        System.arraycopy(args, startIndex, out, 0, out.length);
        return out;
    }

    /**
     * It loops through all the subcommands and returns the first one that matches the name
     * 
     * @param name The name of the command.
     * @return A SubCommand object
     */
    @Nullable
    private SubCommand getCommand(String name) {
        for (SubCommand subCommand : COMMAND_LIST) {
            if (subCommand.getClass().getSimpleName().equalsIgnoreCase(name) || subCommand.matches(name))
                return subCommand;
        }

        return null;
    }

    /**
     * If the sender is a player and the command has at least one argument, return a list of possible
     * completions for the first argument
     * 
     * @param sender The CommandSender that sent the command.
     * @param command The command that was executed.
     * @param string The command that was entered.
     * @param args The arguments that the player has entered.
     * @return A list of strings.
     */
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String string, @NotNull String[] args) {
        if (!(sender instanceof Player) || args.length == 0)
            return new ArrayList<>();

        if (args.length == 1)
            return StringUtil.copyPartialMatches(args[0], COMMANDS, new ArrayList<>());

        return new ArrayList<>();
    }
}

