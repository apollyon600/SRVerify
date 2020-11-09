package me.delected.srverify;

import me.delected.srverify.discord.VerifyDiscord;
import me.delected.srverify.spigotstuff.PlayerJoin;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class SRVerify extends JavaPlugin {
    public static JavaPlugin plugin;
    ServerBooleans s = new ServerBooleans();
    private static final HashMap<UUID, String> code = new HashMap<>();

    public static HashMap<UUID, String> getCode() {
        return code;
    }

    private JDA jda;

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        s.setServerOnline(true);
        verifyConfig();
        getServer().getPluginManager().registerEvents(new PlayerJoin(), plugin);
        try {
            JDABuilder builder = JDABuilder.createDefault(plugin.getConfig().getString("token"));
            builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
            builder.addEventListeners(new VerifyDiscord());
            builder.build();
            builder.setActivity(Activity.playing("Server Status: Online"));

            jda = (JDA) builder;
        } catch (LoginException e) {
            System.out.println("Invalid bot token");
        }

    }

    @Override
    public void onDisable() {
        jda.shutdownNow();
        s.setServerOnline(false);
    }

    public void verifyConfig() {
        try {
            VerifyConfig.setup();
            VerifyConfig.get().options().copyHeader(true).header("Format: MC-UUID:DISCORD-ID");
            VerifyConfig.get().options().copyDefaults(true);
            VerifyConfig.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
