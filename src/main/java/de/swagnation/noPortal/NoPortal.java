package de.swagnation.noPortal;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class NoPortal extends JavaPlugin implements Listener {

    // Variablen, um den Zustand der Portale zu speichern
    private boolean allowNether = true;
    private boolean allowEnd = true;
    private String blockMessage;

    @Override
    public void onEnable() {
        // Plugin-Konfiguration laden oder Standardwerte erstellen
        saveDefaultConfig();
        loadConfigValues();

        // Events und Befehle registrieren
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("noportal").setExecutor(new NoPortalCommand(this));

        getLogger().info("NoPortal wurde erfolgreich aktiviert!");
    }

    @Override
    public void onDisable() {
        getLogger().info("NoPortal wurde deaktiviert.");
    }

    // Lädt die Werte aus der config.yml
    public void loadConfigValues() {
        // Konfiguration neu laden, um aktuelle Werte zu erhalten
        reloadConfig();
        allowNether = getConfig().getBoolean("portals.allow-nether", true);
        allowEnd = getConfig().getBoolean("portals.allow-end", true);
        blockMessage = ChatColor.translateAlternateColorCodes('&',
                getConfig().getString("messages.block-message", "&cDer Zugang zu dieser Dimension ist deaktiviert!"));
    }

    // EventHandler, der ausgelöst wird, wenn ein Spieler ein Portal benutzt
    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {
        Player player = event.getPlayer();
        PlayerTeleportEvent.TeleportCause cause = event.getCause();

        // Nether-Portal-Logik
        if (cause == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
            if (!allowNether) {
                event.setCancelled(true); // Teleportation abbrechen
                player.sendMessage(blockMessage);
            }
        }
    }

    // EventHandler für End-Portale (da diese anders funktionieren)
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        PlayerTeleportEvent.TeleportCause cause = event.getCause();

        // End-Portal-Logik
        if (cause == PlayerTeleportEvent.TeleportCause.END_PORTAL) {
            if (!allowEnd) {
                event.setCancelled(true); // Teleportation abbrechen
                player.sendMessage(blockMessage);

                // Verhindert, dass der Spieler im End-Portal-Block stecken bleibt
                teleportPlayerBack(player);
            }
        }
    }

    /**
     * Teleportiert den Spieler sicher aus dem End-Portal-Block heraus.
     */
    private void teleportPlayerBack(Player player) {
        Location portalLocation = player.getLocation();
        // Finde einen sicheren Block in der Nähe
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (x == 0 && z == 0) continue; // Überspringe die exakte Portal-Position

                Location safeSpot = portalLocation.clone().add(x, 0, z);
                if (isSafeLocation(safeSpot)) {
                    // Eine kleine Verzögerung, damit der Teleport stabiler ist
                    getServer().getScheduler().runTaskLater(this, () -> player.teleport(safeSpot), 1L);
                    return;
                }
            }
        }
        // Fallback, wenn kein sicherer Ort gefunden wird
        getServer().getScheduler().runTaskLater(this, () -> player.teleport(player.getWorld().getSpawnLocation()), 1L);
    }

    /**
     * Prüft, ob eine Location für den Spieler sicher ist (keine soliden Blöcke).
     */
    private boolean isSafeLocation(Location location) {
        Material feet = location.getBlock().getType();
        Material head = location.clone().add(0, 1, 0).getBlock().getType();
        return !feet.isSolid() && !head.isSolid();
    }

    // Getter-Methoden, damit die Command-Klasse darauf zugreifen kann
    public boolean isAllowNether() {
        return allowNether;
    }

    public boolean isAllowEnd() {
        return allowEnd;
    }
}