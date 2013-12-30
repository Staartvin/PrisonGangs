package me.staartvin.prisongang.commands;

import java.util.List;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.gang.Gang;
import me.staartvin.prisongang.playerdata.PlayerData;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShowEnemiesCommand implements CommandExecutor {

	private PrisonGang plugin;

	public ShowEnemiesCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length < 1) {
			sender.sendMessage(ChatColor.YELLOW + "Usage: /gang enemies");
			return true;
		}

		PlayerData player;
		Gang gang;
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED
					+ "Only players can see the enemies list!");
			return true;
		}

		player = plugin.getPlayerDataHandler().getPlayerData(sender.getName());

		if (!player.isInGang()) {
			sender.sendMessage(ChatColor.RED + "You're not in a gang!");
			return true;
		}

		gang = plugin.getGangHandler().getGang(player.getGangName());

		if (gang == null) {
			sender.sendMessage(ChatColor.RED + "There is no such gang!");
			return true;
		}

		List<String> enemies = gang.getEnemies();

		StringBuilder fullString = new StringBuilder("");

		for (int i = 0; i < enemies.size(); i++) {
			String enemy = enemies.get(i);
			if (i == (enemies.size() - 1)) {
				fullString.append(enemy);
			} else {
				fullString.append(enemy + ", ");
			}
		}
		
		if (enemies.size() > 0) {
			sender.sendMessage(ChatColor.GOLD + "------[Enemies]------");
			sender.sendMessage(ChatColor.BLUE + fullString.toString());	
		} else {
			sender.sendMessage(ChatColor.BLUE + "You have no enemies!");
		}	
		
		return true;
	}
}
