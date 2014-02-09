package me.staartvin.prisongang.commands.admin;

import me.staartvin.prisongang.PrisonGang;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AdminSaveCommand implements CommandExecutor {

	private PrisonGang plugin;

	public AdminSaveCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(final CommandSender sender, Command command,
			String label, String[] args) {
		
		if (!plugin.getCommands().hasPermission(sender, "prisongang.admin.save"))
			return true;
		
		// Save asynchronous
		plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
			public void run() {
				plugin.getSaveManager().saveGangData();
				plugin.getSaveManager().savePlayerData();
				
				sender.sendMessage(ChatColor.GREEN + "Successfully saved files.");
			}
		});

		return true;
	}
}
