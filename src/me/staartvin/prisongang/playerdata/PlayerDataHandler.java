package me.staartvin.prisongang.playerdata;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.config.Config;
import me.staartvin.prisongang.gang.Gang;
import me.staartvin.prisongang.permissions.GangAbility;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PlayerDataHandler {

	private PrisonGang plugin;

	public PlayerDataHandler(PrisonGang instance) {
		plugin = instance;
	}

	/**
	 * A key of the hashmap is a playername. It will return the playerdata class
	 * associated with it, or create a new one if it doesn't exist yet.
	 */
	private HashMap<String, PlayerData> playerData = new HashMap<String, PlayerData>();

	/**
	 * Get the PlayerData class of this player
	 * 
	 * @param playerName name of the player
	 * @return PlayerData class associated with this player
	 */
	public PlayerData getPlayerData(String playerName) {
		return playerData.get(playerName);
	}
	
	/**
	 * Get the PlayerData class of this player.
	 * If this PlayerData class cannot be found in the memory, it will search for the file in the file system.
	 * <p>
	 * If the file must be loaded from the file system, it will be automatically stored in the memory for when you need it again.
	 * <b>NOTE:</b> running this frequently can cause a lot of server lag because the plugin has to search the file system.
	 * @param playerName Name of the player
	 * @return {@link PlayerDataHandler} object that holds all information about the player
	 */
	public PlayerData getForcedPlayerData(String playerName) {
		// This method will get the player data. If this was not loaded because the player didn't join before, it will grab it from the file system.
		
		PlayerData pData = getPlayerData(playerName);
		
		if (pData != null) {
			return pData;
		}
		
		pData = loadPlayerData(playerName);
		
		if (pData != null) {
			return pData;
		} else {
			throw new NullPointerException("Player '" + playerName + "' exists in the database but cannot be found?! Did the file system change?");
		}
		
	}

	/**
	 * Create a new playerdata class for a player
	 * 
	 * @param playerName name of the player
	 * @return newly created playerdata class
	 */
	public PlayerData createNewPlayerData(String playerName) {
		// If there is already a class found
		if (playerData.containsKey(playerName)) {
			return playerData.get(playerName);
		}

		PlayerData data = new PlayerData(playerName);

		// Register new class
		playerData.put(playerName, data);

		// Return object
		return data;
	}

	/**
	 * Gets all loaded players
	 * @return a list of a players loaded right now
	 */
	public Set<String> getAllPlayerData() {
		return playerData.keySet();
	}

	/**
	 * This method stores the all the playerdata into a file.
	 * 
	 * @param data playerdata that needs to be stored.
	 */
	public void savePlayerData(PlayerData data) {
		if (data == null)
			return;

		Config file = plugin.getPlayerDataFile();

		FileConfiguration config = file.getConfig();

		// Save individual items

		String prefix = "players." + data.getPlayerName();

		config.set(prefix + ".rankName", data.getRankName());
		config.set(prefix + ".gangName", data.getGangName());
		
		if (!data.getAbilities().isEmpty()) {
			config.set(prefix + ".abilities", data.getAbilities());	
		}

		// Save config
		file.saveConfig();
	}

	/**
	 * Load a PlayerData class of a player.
	 * @param playerName name of the player
	 * @return playerdata class that is associated with this player
	 */
	public PlayerData loadPlayerData(String playerName) {
		if (playerName == null)
			return null;
		
		// Check if he already exists
		if (playerData.containsKey(playerName)) {
			return playerData.get(playerName);
		}

		PlayerData data = new PlayerData(playerName);

		Config file = plugin.getPlayerDataFile();

		FileConfiguration config = file.getConfig();

		// Get individual items

		String prefix = "players." + data.getPlayerName();
		
		data.setGangName(config.getString(prefix + ".gangName"));
		data.setRankName(config.getString(prefix + ".rankName"));
		
		// Load abilities
		List<String> abil = config.getStringList(prefix + ".abilities");
		
		for (String abi: abil) {
			data.addAbility(GangAbility.valueOf(abi));
		}
		
		// Register class
		playerData.put(playerName, data);
		
		return data;
	}
	
	/**
	 * Check whether a player is an ally of another player
	 * @param one player one is the 'original' player
	 * @param two Player two will be checked against player one.
	 * @return true if allies; false otherwise
	 */
	public boolean isAlly(Player one, Player two) {
		PlayerData oneData = getPlayerData(one.getName());
		PlayerData twoData = getPlayerData(two.getName());
		
		if (!oneData.isInGang() || !twoData.isInGang()) return false;
		
		Gang oneGang = plugin.getGangHandler().getGang(oneData.getGangName());
		
		for (String gangName: oneGang.getAllies()) {
			Gang gang = plugin.getGangHandler().getGang(gangName);
			
			for (String playerName: gang.getMembers()) {
				if (playerName.equalsIgnoreCase(two.getName())) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Check whether a player is an enemy of another player
	 * @param one player one is the 'original' player
	 * @param two Player two will be checked against player one.
	 * @return true if enemies; false otherwise
	 */
	public boolean isEnemy(Player one, Player two) {
		PlayerData oneData = getPlayerData(one.getName());
		PlayerData twoData = getPlayerData(two.getName());
		
		if (!oneData.isInGang() || !twoData.isInGang()) return false;
		
		Gang oneGang = plugin.getGangHandler().getGang(oneData.getGangName());
		
		for (String gangName: oneGang.getEnemies()) {
			Gang gang = plugin.getGangHandler().getGang(gangName);
			
			for (String playerName: gang.getMembers()) {
				if (playerName.equalsIgnoreCase(two.getName())) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Check whether a player is also in the same gang
	 * @param one player one is the 'original' player
	 * @param two Player two will be checked against player one.
	 * @return true if in the same gang; false otherwise
	 */
	public boolean isGangPartner(Player one, Player two) {
		PlayerData oneData = getPlayerData(one.getName());
		PlayerData twoData = getPlayerData(two.getName());
		
		if (!oneData.isInGang() || !twoData.isInGang()) return false;
		
		return oneData.getGangName().equals(twoData.getGangName());
	}

}
