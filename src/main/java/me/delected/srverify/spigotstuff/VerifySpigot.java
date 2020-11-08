package me.delected.srverify.spigotstuff;

import me.delected.srverify.SRVerify;
import me.delected.srverify.ServerBooleans;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class VerifySpigot implements CommandExecutor {

    ServerBooleans s = new ServerBooleans();
    HashMap<UUID, String> code = SRVerify.getCode();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { sender.sendMessage("This command can only be executed by a player."); return true;}
        Player p = (Player) sender;
        if (args.length != 0) { p.sendMessage(ChatColor.RED + "Incorrect Usage. Just type '/verify'"); return true;}
        if (s.isPlayerVerified(p)) { p.sendMessage(ChatColor.RED + "You are already verified."); return true;}
        if (code.containsKey(p.getUniqueId())) { p.sendMessage(ChatColor.RED + "You have already been given a code. Please wait 5 minutes before obtaining another."); return true;}

        int randomNum = ThreadLocalRandom.current().nextInt(1000, 9999 + 1);

        String randCode = String.valueOf(randomNum);

        if (code.containsValue(randCode)) { p.sendMessage(ChatColor.RED + "There was an error generating a code! Please try again!"); return true; }

        code.put(p.getUniqueId(), randCode);
        p.sendMessage(ChatColor.BLUE + "Success! Please continue the verification process by running .verify " + randCode + ". You can join our Discord by typing /discord.");
        Bukkit.getScheduler().runTaskLater(SRVerify.plugin, () -> code.remove(p.getUniqueId()), 6000);
        return true;
    }
}
