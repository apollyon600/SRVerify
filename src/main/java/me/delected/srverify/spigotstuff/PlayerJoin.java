package me.delected.srverify.spigotstuff;

import me.delected.srverify.ServerBooleans;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        ServerBooleans s = new ServerBooleans();
        Player p = e.getPlayer();
        if (!s.isPlayerVerified(p)) {
            if (!s.isVerificationAllowed()) { p.kickPlayer(ChatColor.RED + "" + ChatColor.BOLD + "Sorry! \n" + ChatColor.DARK_GRAY + "The server is not accepting new testers right now."); }
            p.sendMessage(ChatColor.RED + "Welcome! In order to continue, you must verify. Use " + ChatColor.DARK_GRAY + "/verify" + ChatColor.RED + " to get started.");
            // no need for flyspeed I think
            p.setWalkSpeed(0);
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 10, true, false));
        }
    }
}
