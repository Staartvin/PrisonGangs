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
	
	/**
	 * Delete a gang from the load and the file system.
	 * <p>
	 * The gang will be unloaded and all references to it will be losted.
	 * Afterwards, the gang gets cleared of the file system.
	 * @param gangName name of gang to unload and remove
	 */
	public void deleteGang(String gangName) {
		// Gang 
		Gang gang = getGang(gangName);
		
		if (gang == null) {
			return;
		}
		
		// Remove all players
		for (String player: gang.getMembers()) {
			PlayerData p = plugin.getPlayerDataHandler().getPlayerData(player, false);
						
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
		
		// Do verify checks
		doAllyVerify(gang);
		doEnemyVerify(gang);

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
	
	/**
	 * This will check what allies do no longer exist.
	 * If they are deleted or unused, they will be removed from the allies list.
	 * @param gang Gang that you want to check the allies for
	 */
	private void doAllyVerify(Gang gang) {
		// Check if ally allies still exist.
		// If an ally doesn't exist anymore, remove it from the list.
		List<String> allies = gang.getAllies();
		List<String> invalidAllies = new ArrayList<String>();
		
		// Check for every ally
		for (String ally: allies) {
			Gang allyGang = getGang(ally);
			
			if (allyGang == null) {
				invalidAllies.add(ally);
			}
		}
		
		// Remove allies that are invalid
		allies.removeAll(invalidAllies);
		
		// Update gang
		gang.setAllies(allies);
		
	}
	
	/**
	 * This will check what enemies do no longer exist.
	 * If they are deleted or unused, they will be removed from the enemies list.
	 * @param gang Gang that you want to check the enemies for
	 */
	private void doEnemyVerify(Gang gang) {
		// Check if ally enemies still exist.
		// If an enemy doesn't exist anymore, remove it from the list.
		List<String> enemies = gang.getEnemies();
		List<String> invalidEnemies = new ArrayList<String>();
		
		// Check for every enemy
		for (String enemy: enemies) {
			Gang enemyGang = getGang(enemy);
			
			if (enemyGang == null) {
				invalidEnemies.add(enemy);
			}
		}
		
		// Remove enemies that are invalid
		enemies.removeAll(invalidEnemies);
		
		// Update gang
		gang.setEnemies(enemies);
		
	}

	/**
	 * Get all loaded gangs
	 * @return a list of loaded gangs
	 */
	public List<Gang> getAllGangs() {
		return gangs;
	}
	
	/**
	 * Unload all gangs. This will cause all gangs to be lost forever.
	 */
	public void resetAllGangs() {
		gangs = new ArrayList<Gang>();
	}
}
