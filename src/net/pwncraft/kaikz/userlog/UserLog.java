package net.pwncraft.kaikz.userlog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.logging.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * UserLog for Bukkit
 *
 * @author Kaikz
 */

public class UserLog extends JavaPlugin {
    private final UserLogPlayerListener playerListener = new UserLogPlayerListener(this);
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    
    // Console logger
    Logger log = Logger.getLogger("Minecraft");
    
    // File shit
    private ArrayList<String> filedUsers;
    private String users = "users.txt";
    private String usersInfo = "users-info.txt";
    private File folder;
      
    
    public void onEnable() {
        folder = getDataFolder();
        filedUsers = new ArrayList<String>();
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
        
        //Create folders and files
        if (!folder.exists())
        {
            System.out.print("[UserLog] Data folder missing, creating...");
            folder.mkdir();
        }
        File usersFile = new File(folder.getAbsolutePath() + File.separator + users);
        if (!usersFile.exists())
        {
            System.out.print("[UserLog] Users file is missing, creating...");
            try
            {
                usersFile.createNewFile();
            } catch (IOException ex)
            {
                System.out.println("[UserLog] Users file creation failed.");
            }
        }
        File usersInfoFile = new File(folder.getAbsolutePath() + File.separator + usersInfo);
        if (!usersInfoFile.exists())
        {
            System.out.print("[UserLog] Users information file is missing, creating...");
            try
            {
                usersInfoFile.createNewFile();
            } catch (IOException ex)
            {
                System.out.println("[UserLog] Users information file creation failed.");
            }
        }
        
        loadUsers();
        
        PluginDescriptionFile pdfFile = this.getDescription();
        log.info("[UserLog]" + " v" + pdfFile.getVersion() + " is enabled!");
    }
        
    public void onDisable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        log.info("[UserLog]" + " v" + pdfFile.getVersion() + " is disabled!");
    }
    
    public boolean isDebugging(final Player player) {
        if (debugees.containsKey(player)) {
            return debugees.get(player);
        } else {
            return false;
        }
    }

    public void setDebugging(final Player player, final boolean value) {
        debugees.put(player, value);
    }
    
    public boolean loadUsers()
    {
        try
        {
            filedUsers.clear();
            BufferedReader reader = new BufferedReader(new FileReader((folder.getAbsolutePath() + File.separator + users)));
            String line = reader.readLine();
            while (line != null)
            {
                filedUsers.add(line);
                line = reader.readLine();
            }
            reader.close();
        }
        catch (Exception ex)
        {
            System.out.println("[UserLog] Load failed: " + ex);
            return false;
        }
        return true;
    }
    
    public boolean isInList(String playerName)
    {
        for (String player : filedUsers)
        {
            if (player.compareToIgnoreCase(playerName) == 0)
            {
                return true;
            }
        }
        return false;
    }
    
    public boolean addUser(String playerName)
    {
        if (!isInList(playerName))
        {
            filedUsers.add(playerName);
            return saveUsers();
        }
        return false;
    }
    
    public boolean saveUsers()
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter((folder.getAbsolutePath() + File.separator + users)));
            for (String player : filedUsers)
            {
                writer.write(player);
                writer.newLine();
            }
            writer.close();
            } catch (Exception ex)
            {
                System.out.println("[UserLog] Error: " + ex);
                return false;
            }
            return true;
    }
    
    public boolean saveUsersInfo(String player, String time, String ip)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter((folder.getAbsolutePath() + File.separator + usersInfo)));
            writer.write(player + " " + ip + " " + time);
            writer.newLine();
            writer.close();
            } catch (Exception ex)
            {
                System.out.println("[UserLog] Error: " + ex);
                return false;
            }
            return true;
    }
}