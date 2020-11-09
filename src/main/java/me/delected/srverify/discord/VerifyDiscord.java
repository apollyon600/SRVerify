package me.delected.srverify.discord;

import me.delected.srverify.SRVerify;
import me.delected.srverify.VerifyConfig;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffectType;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VerifyDiscord extends ListenerAdapter {

    HashMap<UUID, String> code = SRVerify.getCode();

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        String[] message = e.getMessage().getContentRaw().split(" ");
        if (!message[0].equalsIgnoreCase(".verify")) return;
        if (e.getAuthor().isBot() || e.getAuthor().isFake()) return;
        EmbedBuilder emb = new EmbedBuilder();
        emb.setColor(Color.RED);
        emb.setDescription("Incorrect usage! Use .verify <code>. \n If you don't know what your code is, \n run /verify on Minecraft first.");
        emb.setAuthor("Error!", null, "https://cdn.pixabay.com/photo/2017/02/12/21/29/false-2061132_960_720.png");
        if (message.length != 2) { e.getChannel().sendMessage(emb.build()).queue(); return; }
        String codeArg = message[1];
        if (code.containsValue(codeArg)) {
            for (Map.Entry<UUID, String> entry : code.entrySet()) {
                if (entry.getValue().equals(codeArg)) {
                    UUID match = entry.getKey();
                    EmbedBuilder em = new EmbedBuilder();
                    em.setColor(Color.getHSBColor(258,68,66));
                    em.setAuthor("You have been verified!", null, "https://minotar.net/helm/" + Bukkit.getPlayer(match).getName());
                    e.getChannel().sendMessage(em.build()).queue();
                    code.remove(match);
                    VerifyConfig.get().set(match.toString(), e.getMessage().getAuthor().getId());
                    VerifyConfig.save();
                    Bukkit.broadcastMessage(ChatColor.BLUE + Bukkit.getPlayer(match).getName() + " just verified!");
                    Bukkit.getPlayer(match).removePotionEffect(PotionEffectType.BLINDNESS);
                    Bukkit.getPlayer(match).setWalkSpeed(0.2f);
                    break;
                }
            }
        }
        else{
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.RED);
            embed.setDescription("This code is incorrect. Please try again.");
            embed.setAuthor("Error!", null, "https://cdn.pixabay.com/photo/2017/02/12/21/29/false-2061132_960_720.png");
            e.getChannel().sendMessage(embed.build()).queue();
        }
    }
}


