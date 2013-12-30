package me.staartvin.prisongang.listeners;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.playerdata.PlayerData;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

	private PrisonGang plugin;
	
	public PlayerJoinListener(PrisonGang instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		PlayerData data = plugin.getPlayerDataHandler().loadPlayerData(event.getPlayer().getName());
		
		// Player was never loaded before
		if (data == null) {
			// Create a new playerdata object and register it
			data = plugin.getPlayerDataHandler().createNewPlayerData(event.getPlayer().getName());
		}
	}
}
