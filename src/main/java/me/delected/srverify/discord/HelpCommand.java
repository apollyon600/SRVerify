package me.delected.srverify.discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class HelpCommand extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        String[] message = e.getMessage().getContentRaw().split(" ");
        if (!message[0].equalsIgnoreCase(".help")) return;
        if (e.getAuthor().isBot() || e.getAuthor().isFake()) return;
        EmbedBuilder em = new EmbedBuilder();
        em.setColor(new Color(52, 222, 235));
        em.setTitle("Help", null);
        em.setThumbnail("https://cdn.discordapp.com/avatars/723408019865731142/5c956f1b7ee7ede7ace7c5f160f29f13.png?size=128");
        em.setDescription("For more info on a command, run it without any parameters.");
        em.addField(".verify", "Verifies a user, links their Discord and Minecraft accounts.", true);
        em.addField(".online", "Gets all players on the Minecraft server.", true);
        em.addField(".give", "Staff-only command that adds a role to a user.", true);
        em.addField(".strip", "Staff-only command that removes a role from a user.", true);
        em.addField(".kick", "Staff-only command that kicks a user from the Minecraft server.", true);
        em.addField(".ban", "Staff-only command that bans a user from the Minecraft server.", true);
        em.setFooter("Made by Delected with ❤ for SBR");

        e.getChannel().sendMessage(em.build()).queue();
    }
}
