package me.sheepie.ragingredstone;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
// import java.util.UUID;

public final class Ragingredstone extends JavaPlugin implements Listener {

    // Map to store players and their corresponding PotionEffectTypes
    private Map<String, PotionEffectType[]> playerEffects;

    @Override
    public void onEnable() {
        // Initialize the map
        playerEffects = new HashMap<>();

        // UUIDs
        // String sheepieUUID = "0937b604c1ce446a96ff818d752a19f6";
        // String chesterUUID = "bee839c056254816bc71c3eca1600372";
        // String horizonUUID = "e7218140bc8645ec9cd14ef67b711adf";
        // String chickyUUID = "01f464e191684794b6afc27479b60ac1";
        // String c4t_jubbalic_UUID = "5db1af5052724c039225a556cca0f091";
        // String khanUUID = "14c4df5dc1184f5b86da935527414c59";
        // String aironeUUID = "0741ae663c344a82bca14c396014648b";

        // Set PotionEffectTypes for each player
        playerEffects.put("KookieArmy64", new PotionEffectType[]{PotionEffectType.REGENERATION, PotionEffectType.WATER_BREATHING});
        playerEffects.put("SheepieGamer20", new PotionEffectType[]{PotionEffectType.NIGHT_VISION, PotionEffectType.WATER_BREATHING});
        playerEffects.put("Chesterreborn322", new PotionEffectType[]{PotionEffectType.INVISIBILITY, PotionEffectType.NIGHT_VISION});
        playerEffects.put("LordHorizon", new PotionEffectType[]{PotionEffectType.FIRE_RESISTANCE, PotionEffectType.SATURATION});
        playerEffects.put("t3rmite", new PotionEffectType[]{PotionEffectType.FIRE_RESISTANCE, PotionEffectType.INCREASE_DAMAGE});
        playerEffects.put("Jubbalicous", new PotionEffectType[]{PotionEffectType.FIRE_RESISTANCE, PotionEffectType.SLOW_FALLING});
        playerEffects.put("Shi_Khan", new PotionEffectType[]{PotionEffectType.FIRE_RESISTANCE, PotionEffectType.INVISIBILITY});
        playerEffects.put("Airone27", new PotionEffectType[]{PotionEffectType.REGENERATION, PotionEffectType.FAST_DIGGING});
        playerEffects.put("RogueBread", new PotionEffectType[]{PotionEffectType.INVISIBILITY, PotionEffectType.SLOW_FALLING});

        // Register events
        getServer().getPluginManager().registerEvents(this, this);

        // Register the /start command with its executor
        getCommand("start").setExecutor(new StartCommandExecutor(this));

        // Schedule the /start command to run every 5 seconds (100 ticks)
        getServer().getScheduler().runTaskTimer(this, () -> executeStartCommandForAllPlayers(), 0, 100);

        // Plugin startup logic
        getLogger().info("Enabled successfully.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Disabled successfully.");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Execute /start command for the joined player
        Player joinedPlayer = event.getPlayer();
        executeStartCommand(joinedPlayer);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        // Execute /start command for the respawning player
        executeStartCommandForAllPlayers();
    }

    private void executeStartCommandForAllPlayers() {
        // Execute /start command for all online players
//        for (Player player : getServer().getOnlinePlayers()) {
//            executeStartCommand(player);
//        }
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "start");
    }

    private void executeStartCommand(Player player) {
        // Apply both regeneration and speed effects for each player with level 0
        // Check if the player is online before applying the effects
        // If the player is online, apply the effects; otherwise, do nothing
        for (Map.Entry<String, PotionEffectType[]> entry : playerEffects.entrySet()) {
            String playerName = entry.getKey();
            PotionEffectType[] effectTypes = entry.getValue();

            if (player.getUniqueId().equals(playerName)) {
                for (PotionEffectType effectType : effectTypes) {
                    player.addPotionEffect(new PotionEffect(effectType, 2147483647, 0));
                }
                break; // No need to check further
            }
        }
    }

    private static class StartCommandExecutor implements CommandExecutor {
        private final Ragingredstone plugin;

        // Constructor to pass the plugin reference
        public StartCommandExecutor(Ragingredstone plugin) {
            this.plugin = plugin;
        }

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (!plugin.isEnabled()) {
                sender.sendMessage("Plugin is disabled.");
                return true;
            }

            // Apply both effects for specified players with level 0
            // Check if each player is online before applying the effects
            // If the player is online, apply the effects; otherwise, do nothing
            for (Map.Entry<String, PotionEffectType[]> entry : plugin.playerEffects.entrySet()) {
                String playerName = entry.getKey();
                PotionEffectType[] effectTypes = entry.getValue();

                Player player = sender.getServer().getPlayer(playerName);

                if (player != null) {
                    for (PotionEffectType effectType : effectTypes) {
                        player.addPotionEffect(new PotionEffect(effectType, 2147483647, 0));
                    }
//                    player.sendMessage("Effects applied!");
                } else {
                    // pass
                }
            }

            return true;
        }
    }
}
