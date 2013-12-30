package me.staartvin.prisongang.save;

import java.util.List;
import java.util.Set;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.gang.Gang;
import me.staartvin.prisongang.playerdata.PlayerData;

/**
 * Handles all saving of configs.
 * @author Staartvin
 *
 */
public class SaveManager {

	private PrisonGang plugin;
	
	public SaveManager(PrisonGang instance) {
		plugin = instance;
	}
	
	public void savePlayerData() {
		Set<String> data = plugin.getPlayerDataHandler().getAllPlayerData();
		
		plugin.getLogger().info("Saving playerdata.yml...");
		
		for (String key: data) {
			
			PlayerData player = plugin.getPlayerDataHandler().getPlayerData(key);
			
			plugin.getPlayerDataHandler().savePlayerData(player);
		}
	}
	
	public void saveGangData() {
		
		plugin.getLogger().info("Saving gangs.yml...");
		
		List<Gang> gangs = plugin.getGangHandler().getAllGangs();
		
		for (Gang gang: gangs) {
			plugin.getGangHandler().saveGang(gang);
		}
	}
}
