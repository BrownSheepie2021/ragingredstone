package me.sheepie.ragingredstone;

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

public final class Ragingredstone extends JavaPlugin implements Listener {

    // Map to store players and their corresponding PotionEffectTypes
    private Map<String, PotionEffectType[]> playerEffects;

    @Override
    public void onEnable() {
        // Initialize the map
        playerEffects = new HashMap<>();

        // Set PotionEffectTypes for each player
        playerEffects.put("SheepieGamer20", new PotionEffectType[]{PotionEffectType.NIGHT_VISION, PotionEffectType.WATER_BREATHING});
//        playerEffects.put("KookieArmy64", new PotionEffectType[]{PotionEffectType.EFFECT, PotionEffectType.EFFECT});
        playerEffects.put("Chesterreborn322", new PotionEffectType[]{PotionEffectType.JUMP, PotionEffectType.WATER_BREATHING});
        playerEffects.put("LordHorizon", new PotionEffectType[]{PotionEffectType.DAMAGE_RESISTANCE, PotionEffectType.REGENERATION});
        playerEffects.put("chickyNuggles", new PotionEffectType[]{PotionEffectType.FIRE_RESISTANCE, PotionEffectType.INCREASE_DAMAGE});
        playerEffects.put("C4t_L1feplayz", new PotionEffectType[]{PotionEffectType.FIRE_RESISTANCE, PotionEffectType.SLOW_FALLING});
        playerEffects.put("Shi_Khan", new PotionEffectType[]{PotionEffectType.FIRE_RESISTANCE, PotionEffectType.FAST_DIGGING});
//        playerEffects.put("Airone_27", new PotionEffectType[]{PotionEffectType.EFFECT, PotionEffectType.EFFECT});
        // Register events
        getServer().getPluginManager().registerEvents(this, this);

        // Register the /start command with its executor
        getCommand("start").setExecutor(new StartCommandExecutor(this));

        // Schedule the /start command to run every minute (1200 ticks)
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
        Player respawnedPlayer = event.getPlayer();
        executeStartCommand(respawnedPlayer);
    }

    private void executeStartCommandForAllPlayers() {
        // Execute /start command for all online players
        for (Player player : getServer().getOnlinePlayers()) {
            executeStartCommand(player);
        }
    }

    private void executeStartCommand(Player player) {
        // Apply both regeneration and speed effects for each player with level 0
        // Check if the player is online before applying the effects
        // If the player is online, apply the effects; otherwise, do nothing
        for (Map.Entry<String, PotionEffectType[]> entry : playerEffects.entrySet()) {
            String playerName = entry.getKey();
            PotionEffectType[] effectTypes = entry.getValue();

            if (player.getName().equals(playerName)) {
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
                    player.sendMessage("Effects applied!");
                } else {
                    // pass
                }
            }

            return true;
        }
    }
}
