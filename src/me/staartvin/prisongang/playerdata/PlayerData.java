package me.staartvin.prisongang.playerdata;

import java.util.ArrayList;
import java.util.List;

import me.staartvin.prisongang.permissions.GangAbility;

/**
 * Stores data of one player
 * A class will be created once a player joins
 * @author Staartvin
 */
public class PlayerData {

	
	/**
	 * Create a new playerdata class. 
	 * This class stores all necessary info about the player.
	 * 
	 * @param playerName name of the player
	 */
	public PlayerData(String playerName) {
		this.playerName = playerName;
	}
	
	/**
	 * The player that is linked associated with this class
	 */
	private String playerName = null;
	
	
	/**
	 * Rank the player has inside a group.
	 * (Null when not a member of a gang)
	 */
	private String rankName = null;
	
	
	/**
	 * Gang the player is part of.
	 * (Null when not a member of a gang)
	 */
	private String gangName = null;
	
	
	/**
	 * A list containing all abilities a player has inside a gang.
	 * 
	 */
	private List<GangAbility> abilities = new ArrayList<GangAbility>();


	/**
	 * Get the gang the player is in
	 * @return name of the gang the player is in
	 */
	public String getGangName() {
		return gangName;
	}


	/**
	 * Set the gang the player is in
	 * @param gangName name of the gang
	 */
	public void setGangName(String gangName) {
		this.gangName = gangName;
	}


	/**
	 * Get the name of the rank the player has.
	 * This will be null when the player is not in a gang
	 * @return
	 */
	public String getRankName() {
		return rankName;
	}


	/**
	 * Set the rankname of the player
	 * @param rankName name of the rank
	 */
	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

	
	/**
	 * Add an ability to the player
	 * @param abi ability to add
	 */
	public void addAbility(GangAbility abi) {

		if (abi == null) return;
		
		// When the player doesn't already have this ability, add it.
		if (!abilities.contains(abi)) {
			abilities.add(abi);
		}
	}
	
	/**
	 * Remove an ability from the player
	 * @param abi ability to remove
	 */
	public void removeAbility(GangAbility abi) {
		
		if (abi == null) return;
		
		// When the player has the ability, remove it.
		if (abilities.contains(abi)) {
			abilities.remove(abi);
		}
	}


	/**
	 * Get the player that is associated with this class
	 * @return name of the player
	 */
	public String getPlayerName() {
		return playerName;
	}
	
	/**
	 * Get the abilities of the player
	 * @return a list containing all abilities the player has
	 */
	public List<GangAbility> getAbilities() {
		return abilities;
	}
	
	/** 
	 * Check whether the player is in a gang
	 * @return true if the player is in a gang
	 */
	public boolean isInGang() {
		return gangName != null && !gangName.trim().equals("");
	}
	
	/**
	 * Resets the abilities of this player.
	 * <p>
	 * This is used when the player leaves a gang so
	 * that the abilities of the old gang
	 * do not carry over to the next one.
	 */
	public void resetAbilities() {
		// Set it to an empty list
		abilities = new ArrayList<GangAbility>();
	}
}
