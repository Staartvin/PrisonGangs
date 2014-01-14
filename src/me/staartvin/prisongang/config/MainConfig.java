/**
 * 
 */
package me.staartvin.prisongang.config;

import me.staartvin.prisongang.PrisonGang;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Date created: 22:15:38
 * 14 jan. 2014
 * 
 * @author Staartvin
 * 
 */
public class MainConfig {

	private PrisonGang plugin;
	private ConfigWrapper configWrapper;
	private FileConfiguration config;
	
	public MainConfig(PrisonGang instance) {
		plugin = instance;
	}
	
	public void setupMainConfig() {
		// Initialise for reference
		configWrapper = plugin.getMainConfigFile();
		config = configWrapper.getConfig();
		

		// Change header
		config.options().header("All the settings can be changed here."
				+ " \nAll the messages can be changed in the messages.yml."
				+ " \nYou can use the prefixes when you want to change the chat."
				+ "	\nMax players of a gang can be any number or -1 for an infinite amount.");
		

		// Config all options for main config here

		config.addDefault("chat.gang prefix", "&7(&2%gang%&7)&r");
		config.addDefault("chat.title prefix", "&7[&4%title%&7]&r");
		config.addDefault("chat.chatmode prefix", "[&6%chatmode%&r]");
		
		config.addDefault("gangs.max players", 10);
		
		config.addDefault("logger.log verbose", true);
		
		config.addDefault("logger.debug", false);
		
		config.options().copyDefaults(true);
		
		configWrapper.saveConfig();
		
		// Load main config
		loadMainConfig();
	}
	
	public void loadMainConfig() {
		// Loads all options in memory
		plugin.gang_chat_prefix = getGangPrefix();
		plugin.title_chat_prefix = getTitlePrefix();
		plugin.chatmode_prefix = getChatModePrefix();
	}
	
	public String getGangPrefix() {
		return config.getString("chat.gang prefix");
	}
	
	public String getTitlePrefix() {
		return config.getString("chat.title prefix");
	}
	
	public String getChatModePrefix() {
		return config.getString("chat.chatmode prefix");
	}
	
	public int getMaxPlayers() {
		// if a negative number return a big number.
		return config.getInt("gangs.max players", 10) > 0 ? config.getInt("gangs.max players", 10) : 999999;
	} 
	
	public boolean doLogVerbose() {
		return config.getBoolean("logger.log verbose", true);
	}
	
	public boolean doDebug() {
		return config.getBoolean("logger.debug", false);
	}
}
