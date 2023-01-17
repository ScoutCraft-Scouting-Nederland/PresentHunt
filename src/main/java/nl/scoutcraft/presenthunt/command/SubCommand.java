package nl.scoutcraft.presenthunt.command;

import nl.scoutcraft.eagle.server.locale.IMessage;
import org.bukkit.entity.Player;

public abstract class SubCommand {

    private final IMessage<String> message;
    private final IMessage<String> usage;
    private final String permission;
    private final String[] commands;

    public SubCommand(IMessage<String> message, IMessage<String> usage, String permission, String... commands) {
        this.message = message;
        this.usage = usage;
        this.permission = permission;
        this.commands = commands;
    }

    public abstract void onCommand(Player player, String[] args);

    public boolean matches(String command) {
        for (String cmd : this.commands)
            if (cmd.equalsIgnoreCase(command))
                return true;

        return false;
    }

    public String getCommandName() {
        return this.commands[0];
    }

    public IMessage<String> getMessage() {
        return this.message;
    }

    public IMessage<String> getUsage() {
        return this.usage;
    }

    public String getPermission() {
        return this.permission;
    }

    public String[] getCommands() {
        return this.commands;
    }
}
