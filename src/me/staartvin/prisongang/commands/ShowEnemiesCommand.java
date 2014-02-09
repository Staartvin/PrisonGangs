package me.staartvin.prisongang.commands;

import java.util.List;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.gang.Gang;
import me.staartvin.prisongang.playerdata.PlayerData;
import me.staartvin.prisongang.translation.Lang;

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
			sender.sendMessage(Lang.ONLY_PLAYER_ACTIVITY.getConfigValue(null));
			return true;
		}

		player = plugin.getPlayerDataHandler().getPlayerData(sender.getName(), false);

		if (!player.isInGang()) {
			sender.sendMessage(Lang.NOT_IN_A_GANG.getConfigValue(null));
			return true;
		}

		gang = plugin.getGangHandler().getGang(player.getGangName());

		if (gang == null) {
			sender.sendMessage(Lang.GANG_DOES_NOT_EXIST.getConfigValue(null));
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
