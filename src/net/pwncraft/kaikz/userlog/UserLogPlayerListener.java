package net.pwncraft.kaikz.userlog;

import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * Handle events for all Player related events
 * @author Kaikz
 */

public class UserLogPlayerListener extends PlayerListener {
    private final UserLog plugin;

    public UserLogPlayerListener(UserLog instance) {
        plugin = instance;
    }

    @Override
    public void onPlayerLogin(PlayerLoginEvent event)
    {
        String playerName = event.getPlayer().getName();
        if (!plugin.isInList(playerName))
        {
            plugin.addUser(playerName);
        }
    }
}

