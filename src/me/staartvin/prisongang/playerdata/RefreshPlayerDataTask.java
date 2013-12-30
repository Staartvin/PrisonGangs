package me.staartvin.prisongang.playerdata;

import me.staartvin.prisongang.PrisonGang;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RefreshPlayerDataTask extends BukkitRunnable {

	private PrisonGang plugin;
	
	public RefreshPlayerDataTask(PrisonGang instance) {
		plugin = instance;
	}
	
	
	@Override
	public void run() {
		// Get all players online and check if their data file is correct
		for (Player p: plugin.getServer().getOnlinePlayers()) {
			
			PlayerData data;
			
			data = plugin.getPlayerDataHandler().getPlayerData(p.getName());
		
			// Player has not been loaded
			if (data == null) {
				data = plugin.getPlayerDataHandler().loadPlayerData(p.getName());
			}
			
			// Player was never loaded before
			if (data == null) {
				// Create a new playerdata object and register it
				data = plugin.getPlayerDataHandler().createNewPlayerData(p.getName());
			}
			
			
		}
		
	}

}
