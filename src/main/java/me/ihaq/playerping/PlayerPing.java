package me.ihaq.playerping;

import me.ihaq.configmanager.ConfigManager;
import me.ihaq.configmanager.data.ConfigValue;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerPing extends JavaPlugin implements Listener {

    @ConfigValue("sound")
    private boolean sound;

    @ConfigValue("change_color")
    private boolean changeColor;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        new ConfigManager(this).register(this).load();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (e.getMessage().contains(player.getName())) {
                if (changeColor) {
                    String lastColor = ChatColor.getLastColors(e.getMessage());
                    e.setMessage(e.getMessage().replaceAll(player.getName(), ChatColor.GOLD + player.getName() + (lastColor.isEmpty() ? ChatColor.RESET : lastColor)));
                }
                if (sound) {
                    if (Bukkit.getVersion().contains("1.9") || Bukkit.getVersion().contains("1.10") || Bukkit.getVersion().contains("1.11") || Bukkit.getVersion().contains("1.12")) {
                        player.playSound(player.getEyeLocation(), Sound.valueOf("UI_BUTTON_CLICK"), 1, 1);
                    } else {
                        player.playSound(player.getEyeLocation(), Sound.valueOf("CLICK"), 1, 1);
                    }
                }
            }
        });
    }

}