package me.delected.srverify.discord;

import me.delected.srverify.SRVerify;
import me.delected.srverify.VerifyConfig;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.handle.GuildMemberAddHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class VerifyDiscord extends ListenerAdapter {

    HashMap<UUID, String> code = SRVerify.getCode();

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        String[] message = e.getMessage().getContentRaw().split(" ");
        if (!message[0].equalsIgnoreCase("/verify")) return;
        if (e.getAuthor().isBot() || e.getAuthor().isFake()) return;
        EmbedBuilder emb = new EmbedBuilder();
        emb.setColor(Color.RED);
        emb.setDescription("Incorrect usage! Use /verify <code>.\nIf you don't know what your code is, relog and read the chat.");
        emb.setAuthor("Error!", null, "https://cdn.pixabay.com/photo/2017/02/12/21/29/false-2061132_960_720.png");
        if (message.length != 2) {
            e.getChannel().sendMessage(emb.build()).queue();
            return;
        }
        String codeArg = message[1];
        if (code.containsValue(codeArg)) {
            for (Map.Entry<UUID, String> entry : code.entrySet()) {
                if (entry.getValue().equals(codeArg)) {
                    // Embed Sending
                    UUID match = entry.getKey();
                    EmbedBuilder em = new EmbedBuilder();
                    em.setColor(Color.GREEN);
                    em.setAuthor("You have been verified!", null, "https://minotar.net/helm/" + Bukkit.getPlayer(match).getName());
                    e.getChannel().sendMessage(em.build()).queue();

                    // Logging & Role Adding
                    Role verified = e.getGuild().getRoleById("773810251320918016");
                    Role unverified = e.getGuild().getRoleById("754224457958031390");
                    if (verified != null && unverified != null && e.getMember() != null) {
                        e.getGuild().addRoleToMember(e.getMember(), verified).queue();
                        e.getGuild().removeRoleFromMember(e.getMember(), unverified).queue();
                    }
                    TextChannel channel = e.getGuild().getTextChannelById("775448599706206228");
                    if (channel != null && e.getMember() != null) {
                        EmbedBuilder embed = new EmbedBuilder();
                        embed.setDescription(e.getMember().getAsMention() + " [`" + e.getMember().getId() + "`] has verified with the account, `" + Bukkit.getPlayer(match).getName());
                        channel.sendMessage(embed.build()).queue();
                    }

                    // Cleanup and Database saving
                    code.remove(match);
                    VerifyConfig.get().set(match.toString(), e.getMessage().getAuthor().getId());
                    VerifyConfig.save();
                    for (PotionEffect effect : Bukkit.getPlayer(match).getActivePotionEffects()) {
                        Bukkit.getPlayer(match).removePotionEffect(effect.getType());
                    }
                    break;
                }
            }
        } else {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setDescription("The code is incorrect. Please try again.");
            embed.setAuthor("Error!", null, "https://cdn.pixabay.com/photo/2017/02/12/21/29/false-2061132_960_720.png");
            e.getChannel().sendMessage(embed.build()).queue();
        }
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent e) {
        Role unverified = e.getGuild().getRoleById("754224457958031390");
        if (unverified != null)
            e.getGuild().addRoleToMember(e.getMember(), unverified).queue();
    }
}


