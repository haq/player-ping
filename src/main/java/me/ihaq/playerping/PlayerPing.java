package me.ihaq.playerping;

import me.ihaq.keeper.Keeper;
import me.ihaq.keeper.data.ConfigValue;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerPing extends JavaPlugin implements Listener {

    @ConfigValue("sound")
    private boolean sound = true;

    @ConfigValue("change_color")
    private boolean changeColor = true;

    @Override
    public void onEnable() {
        new Keeper(this).register(this).load();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        // loop through the online players to find the player pinged
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (e.getMessage().contains(player.getName())) {
                if (changeColor) {
                    String lastColor = ChatColor.getLastColors(e.getMessage());
                    e.setMessage(e.getMessage().replaceAll(
                            player.getName(), ChatColor.GOLD + player.getName() + (lastColor.isEmpty() ? ChatColor.RESET : lastColor)
                    ));
                }
                if (sound) {
                    Sound buttonSound = getButtonClick();

                    // unable to get button click sound
                    if (buttonSound == null) {
                        return;
                    }

                    player.playSound(player.getEyeLocation(), buttonSound, 1, 1);
                }
            }
        });
    }

    private Sound getButtonClick() {
        try {
            return Sound.valueOf("UI_BUTTON_CLICK");
        } catch (IllegalArgumentException e) {
            try {
                return Sound.valueOf("UI_BUTTON_CLICK");
            } catch (IllegalArgumentException e1) {
                e1.printStackTrace();
                return null;
            }
        }
    }

}