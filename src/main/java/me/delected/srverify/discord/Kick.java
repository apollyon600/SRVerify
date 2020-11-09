package me.delected.srverify.discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.awt.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Kick extends ListenerAdapter {
    @lombok.SneakyThrows
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        String[] message = e.getMessage().getContentRaw().split(" ");
        if (!message[0].equalsIgnoreCase(".kick")) return;
        if (e.getAuthor().isBot() || e.getAuthor().isFake()) return;
        if (!Objects.requireNonNull(e.getMember()).hasPermission(Permission.KICK_MEMBERS)) {
            EmbedBuilder emb = new EmbedBuilder();
            emb.setColor(Color.RED);
            emb.setDescription("You need the `Kick Members` permission to do this!");
            emb.setAuthor("Error!", null, "https://cdn.pixabay.com/photo/2017/02/12/21/29/false-2061132_960_720.png");
            return;
        }
        EmbedBuilder emb = new EmbedBuilder();
        emb.setColor(Color.RED);
        emb.setDescription("Incorrect usage! Use .kick <IGN> <REASON>");
        emb.setAuthor("Error!", null, "https://cdn.pixabay.com/photo/2017/02/12/21/29/false-2061132_960_720.png");
        if (message.length != 3) { e.getChannel().sendMessage(emb.build()).queue(); return; }
        Player p = Bukkit.getPlayer(message[1]);
        p.kickPlayer(message[2]);
    }
}
