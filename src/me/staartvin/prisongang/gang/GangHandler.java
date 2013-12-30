package me.staartvin.prisongang.gang;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.config.Config;
import me.staartvin.prisongang.playerdata.PlayerData;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class GangHandler {

	private PrisonGang plugin;

	// Stores all gangs that are in the config
	private List<Gang> gangs = new ArrayList<Gang>();

	public GangHandler(PrisonGang instance) {
		plugin = instance;
	}

	/**
	 * Get the gang class with a specific name
	 * 
	 * @param gangName name of the gang
	 * @return {@link Gang} object that represents a gang
	 */
	public Gang getGang(String gangName) {
		Gang gang = null;

		for (Gang g : gangs) {
			if (g != null) {
				if (g.getGangName().equalsIgnoreCase(gangName)) {
					gang = g;
				}
			}
		}
		return gang;
	}

	/**
	 * Get the gang class with a specific leader
	 * 
	 * @param leaderName name of the player that is leader of this class
	 * @return {@link Gang} object that represents a gang
	 */
	public Gang getGangByLeader(String leaderName) {
		Gang gang = null;

		for (Gang g : gangs) {
			if (g != null) {
				if (g.getLeader().equalsIgnoreCase(leaderName)) {
					gang = g;
				}
			}
		}
		return gang;
	}

	/**
	 * Gets all the gangs from the gangs.yml, creates a new {@link Gang} object
	 * for each of them
	 * and stores them in a list for later reference.
	 * This method will automatically get called by the plugin itself on load,
	 * so there is not need to call it yourself.
	 * 
	 * @return number of gangs loaded
	 */
	public int loadGangsFromConfig() {
		int count = 0;

		ConfigurationSection gangSection = plugin.getGangDataFile().getConfig()
				.getConfigurationSection("gangs");

		if (gangSection != null) {
			for (String gangName : gangSection.getKeys(false)) {
				Gang gang = loadGang(gangName);

				if (gang != null) {
					
					if (alreadyContains(gang.getGangName())) {
						continue;
					}
					
					gangs.add(gang);
					++count;
				}
			}
		}

		return count;
	}
	
	/**
	 * Does there already exist such gang?
	 * @param gangName gang name
	 * @return true if already exists; false otherwise
	 */
	private boolean alreadyContains(String gangName) {
		for (Gang g: gangs) {
			if (g.getGangName().equalsIgnoreCase(gangName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method is used to convert a flat-file gang to a {@link Gang} object.
	 * 
	 * @param gangName name of the gang to load
	 * @return {@link Gang} that was created
	 */
	public Gang loadGang(String gangName) {
		if (gangName == null)
			return null;

		// Check if it already exists
		if (getGang(gangName) != null) {
			return getGang(gangName);
		}

		Config file = plugin.getGangDataFile();

		FileConfiguration config = file.getConfig();

		String prefix = "gangs." + gangName;

		Gang gang = new Gang(plugin, gangName);

		// Load individual items

		gang.setLeadersTitle(config.getString(prefix + ".leadersTitle"));

		// Load lists

		// Add all members
		for (String member : config.getStringList(prefix + ".members")) {
			gang.addMember(member);
		}

		// Add all enemies
		for (String enemy : config.getStringList(prefix + ".enemies")) {
			gang.addEnemy(enemy);
		}

		// Add all allies
		for (String ally : config.getStringList(prefix + ".ally")) {
			gang.addAlly(ally);
		}

		// Load complex list

		ConfigurationSection infoSection = config
				.getConfigurationSection(prefix + ".info");

		if (infoSection != null) {
			for (String key : infoSection.getKeys(false)) {
				gang.setInfo(key, config.getString(prefix + ".info." + key));
			}
		}

		// Register gang
		//gangs.add(gang);

		return gang;

	}

	/**
	 * This method will create a new gang
	 * 
	 * @return new gang object associated with it.
	 */
	public Gang createNewGang(String gangName, String creator) {

		// Is there already an existing gang?
		Gang gang = getGang(gangName);

		if (gang != null) {
			return gang;
		}

		// Create a new gang
		gang = new Gang(plugin, gangName);

		// Set creation date
		gang.setInfo("creationDate", new Date().toString());
		
		// Set creator
		gang.setInfo("creator", creator);
		
		// Set description
		gang.setInfo("description", "example description");

		// Register gang
		gangs.add(gang);

		return gang;
	}
	
	public void deleteGang(String gangName) {
		// Gang 
		Gang gang = getGang(gangName);
		
		if (gang == null) {
			return;
		}
		
		// Remove all players
		for (String player: gang.getMembers()) {
			PlayerData p = plugin.getPlayerDataHandler().getPlayerData(player);
			
			// Reset rank name
			p.setRankName(null);
			
			// Reset gang
			p.setGangName(null);
			
			// Reset abilities
			p.resetAbilities();
		}
		
		// Remove in file
		plugin.getGangDataFile().getConfig().set("gangs." + gang.getGangName(), null);
		
		// Save
		plugin.getGangDataFile().saveConfig();
		
		// Remove gang reference
		gangs.remove(gang);

		// Set gang null for GC
		gang = null;
		
	}

	/**
	 * Saves a gang class to file
	 * 
	 * @param gang gang to save
	 */
	public void saveGang(Gang gang) {
		if (gang == null)
			return;

		Config file = plugin.getGangDataFile();

		FileConfiguration config = file.getConfig();

		// Save individual items

		String prefix = "gangs." + gang.getGangName();

		config.set(prefix + ".leadersTitle", gang.getLeadersTitle());
		config.set(prefix + ".enemies", gang.getEnemies());
		config.set(prefix + ".allies", gang.getAllies());
		config.set(prefix + ".members", gang.getMembers());

		// Save extra info

		for (String key : gang.getInfoKeys()) {
			String value = gang.getInfo(key);

			config.set(prefix + ".info." + key, value);
		}

		// Save config
		file.saveConfig();
	}

	public List<Gang> getAllGangs() {
		return gangs;
	}
	
	public void resetAllGangs() {
		gangs = new ArrayList<Gang>();
	}
}
