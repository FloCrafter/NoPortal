package de.swagnation.noPortal;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class NoPortalCommand implements CommandExecutor {

    private final NoPortal plugin;

    public NoPortalCommand(NoPortal plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Prüft, ob der Absender die nötige Berechtigung hat
        if (!sender.hasPermission("noportal.admin")) {
            sender.sendMessage(ChatColor.RED + "Du hast keine Berechtigung, diesen Befehl zu verwenden.");
            return true;
        }

        // Prüft, ob die Argumente korrekt sind
        if (args.length != 2) {
            sendUsage(sender);
            return true;
        }

        String dimension = args[0].toLowerCase();
        String valueStr = args[1].toLowerCase();
        boolean value;

        // Konvertiert das zweite Argument in einen boolean-Wert
        if (valueStr.equals("true")) {
            value = true;
        } else if (valueStr.equals("false")) {
            value = false;
        } else {
            sendUsage(sender);
            return true;
        }

        // Setzt den Wert für die ausgewählte Dimension
        switch (dimension) {
            case "nether":
                plugin.getConfig().set("portals.allow-nether", value);
                sender.sendMessage(ChatColor.GREEN + "Nether-Zugang wurde auf '" + value + "' gesetzt.");
                break;
            case "end":
                plugin.getConfig().set("portals.allow-end", value);
                sender.sendMessage(ChatColor.GREEN + "End-Zugang wurde auf '" + value + "' gesetzt.");
                break;
            default:
                sendUsage(sender);
                return true;
        }

        // Speichert die Änderungen in der config.yml und lädt sie neu
        plugin.saveConfig();
        plugin.loadConfigValues();

        return true;
    }

    private void sendUsage(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "Falsche Benutzung! Korrekt: /noportal <nether|end> <true|false>");
        sender.sendMessage(ChatColor.YELLOW + "Aktueller Status:");
        sender.sendMessage(ChatColor.YELLOW + " - Nether: " + (plugin.isAllowNether() ? ChatColor.GREEN + "erlaubt" : ChatColor.RED + "gesperrt"));
        sender.sendMessage(ChatColor.YELLOW + " - End: " + (plugin.isAllowEnd() ? ChatColor.GREEN + "erlaubt" : ChatColor.RED + "gesperrt"));
    }
}