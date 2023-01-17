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

public class MainCommand implements CommandExecutor, TabExecutor {
    private static final List<SubCommand> COMMAND_LIST = new ArrayList<>();
    private static final List<String> COMMANDS = new ArrayList<>();

    public MainCommand() {
        COMMAND_LIST.add(new Wand());
        COMMAND_LIST.forEach(subCommand -> Collections.addAll(COMMANDS, subCommand.getCommands()));
    }

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

    public static String[] subArray(int startIndex, String[] args) {
        if (startIndex >= args.length) return new String[0];

        String[] out = new String[args.length - startIndex];
        System.arraycopy(args, startIndex, out, 0, out.length);
        return out;
    }

    @Nullable
    private SubCommand getCommand(String name) {
        for (SubCommand subCommand : COMMAND_LIST) {
            if (subCommand.getClass().getSimpleName().equalsIgnoreCase(name) || subCommand.matches(name))
                return subCommand;
        }

        return null;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String string, @NotNull String[] args) {
        if (!(sender instanceof Player) || args.length == 0)
            return new ArrayList<>();

        if (args.length == 1)
            return StringUtil.copyPartialMatches(args[0], COMMANDS, new ArrayList<>());

        return new ArrayList<>();
    }
}

