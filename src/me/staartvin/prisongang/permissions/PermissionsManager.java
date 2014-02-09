package me.staartvin.prisongang.permissions;

import java.util.List;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.gang.Gang;
import me.staartvin.prisongang.playerdata.PlayerData;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This class allows has several methods to work with the special permissions system this plugin is using.
 * @author Staartvin
 *
 */
public class PermissionsManager {

	private PrisonGang plugin;
	
	
	public PermissionsManager(PrisonGang instance) {
		plugin = instance;
	}
	
	/**
	 * Check whether a player has an certain ability
	 * @param player Player to check
	 * @param abi Ability to check for
	 * @return true if the player has this ability; false otherwise
	 */
	public boolean hasAbility(Player player, GangAbility abi) {
		PlayerData data = plugin.getPlayerDataHandler().getPlayerData(player.getName(), false);
		
		if (data == null) return false;
		
		if (!data.isInGang()) return false;
		
		Gang gang = plugin.getGangHandler().getGang(data.getGangName());
		
		// If this player is the leader of the gang, he is allowed to do anything.
		if (gang.getLeader().equals(player.getName())) {
			return true;
		}
		
		List<GangAbility> abilities = data.getAbilities();
		
		for (GangAbility abil: abilities) {
			if (abil.name().equals(abi.name())) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasAbility(CommandSender sender, GangAbility abi) {
		if (!(sender instanceof Player)) {
			return true;
		} else {
			return hasAbility((Player) sender, abi); 
		}
	}
}
