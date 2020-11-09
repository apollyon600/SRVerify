package me.delected.srverify.spigotstuff;

import me.delected.srverify.SRVerify;
import me.delected.srverify.ServerBooleans;
import me.delected.srverify.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerJoin implements Listener {
    ServerBooleans s = new ServerBooleans();

    HashMap<UUID, String> code = SRVerify.getCode();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!s.isPlayerVerified(p)) {
            if (!s.isVerificationAllowed()) {
                p.kickPlayer(Utils.chat("&cSorry! Apollo is not accepting new testers right now."));
            }
            int randomNum = ThreadLocalRandom.current().nextInt(1000, 9999 + 1);
            String randCode = String.valueOf(randomNum);
            if (code.containsValue(randCode)) {
                p.kickPlayer(Utils.chat("&cThere was an error generating a code! Please try again!"));
            } else {
                code.put(p.getUniqueId(), randCode);
                p.sendMessage(Utils.chat("&7SBR &8» &7Welcome to &6SkyblockRemastered&7, &b" + p.getName() + "&7!"));
                p.sendMessage(Utils.chat("&7Join our discord by clicking the link &8» &ahttps://discord.gg/gbXcMta"));
                p.sendMessage(Utils.chat("&7After joining the discord, please run the command &a/verify"));
                p.sendMessage(Utils.chat("&7Type the following command in the #commands channel &8» &a.verify " + randCode));
                p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 255, true, false));
                p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 255, true, false));
                p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 255, true, false));
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 255, true, false));
                p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 255, true, false));
                p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 255, true, false));
                Bukkit.getScheduler().runTaskLater(SRVerify.plugin, () -> {
                    code.remove(p.getUniqueId());
                    p.kickPlayer(Utils.chat("&cUh oh.. Your current verification code has expired! Please rejoin and try again."));
                }, 6000);
            }
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        code.remove(p.getUniqueId());
    }
}
