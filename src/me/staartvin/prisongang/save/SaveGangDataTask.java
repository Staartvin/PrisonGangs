package me.staartvin.prisongang.save;

import me.staartvin.prisongang.PrisonGang;
import org.bukkit.scheduler.BukkitRunnable;

public class SaveGangDataTask extends BukkitRunnable {

	private PrisonGang plugin;
	
	public SaveGangDataTask(PrisonGang instance) {
		plugin = instance;
	}
	
	@Override
	public void run() {
		
		plugin.getSaveManager().saveGangData();
	}

}
