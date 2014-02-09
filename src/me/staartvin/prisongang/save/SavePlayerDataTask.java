package me.staartvin.prisongang.save;

import me.staartvin.prisongang.PrisonGang;
import org.bukkit.scheduler.BukkitRunnable;

public class SavePlayerDataTask extends BukkitRunnable {

	private PrisonGang plugin;
	
	public SavePlayerDataTask(PrisonGang instance) {
		plugin = instance;
	}
	
	@Override
	public void run() {
		
		plugin.getSaveManager().savePlayerData();
	}

}
