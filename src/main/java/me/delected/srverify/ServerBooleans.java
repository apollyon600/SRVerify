package me.delected.srverify;

import org.bukkit.entity.Player;

public class ServerBooleans {
    boolean serverIsOnline;
    boolean playerIsVerified;
    public boolean isServerOnline() {
        return serverIsOnline;
    }

    public void setServerOnline(boolean serverIsOnline) {
        this.serverIsOnline = serverIsOnline;
    }

    public boolean isVerificationAllowed() { return SRVerify.plugin.getConfig().getBoolean("accepting"); }

    public boolean isPlayerVerified(Player p) { return VerifyConfig.get().get(p.getUniqueId().toString()) != null; }
}
