package net.pwncraft.kaikz.userlog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerJoinEvent;

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
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        String playerName = event.getPlayer().getName();
        // Normal list
        if (!plugin.isInList(playerName))
        {
            plugin.addUser(playerName);
        }
        
        // Info list (IP, date/time)
        // Getting the current date and time.
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String dateString = dateFormat.format(date);
        String playerIP = event.getPlayer().getAddress().getHostName().toString();
        plugin.saveUsersInfo(playerName, dateString, playerIP);
    }
}