package me.staartvin.prisongang.commands;

import java.util.ArrayList;
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

public class CreateCommand implements CommandExecutor {

	private PrisonGang plugin;
	
	private String[] codes = {"&0", "&1", "&2", "&3", "&4", "&5", "&6", "&7", "&8", "&9", "&a", "&b", "&c", "&d", "&e", "&f", "&m", "&n", "&l", "&k", "&o"};

	public CreateCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length < 2) {
			sender.sendMessage(ChatColor.YELLOW
					+ "Usage: /gang create <gang name>");
			return true;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(Lang.ONLY_PLAYER_ACTIVITY.getConfigValue(null));
			return true;
		}

		if (!plugin.getCommands().hasPermission(sender, "prisongang.create"))
			return true;

		List<String> newArgs = new ArrayList<String>();

		for (int i = 0; i < args.length; i++) {
			if (i == 0)
				continue;
			else {
				newArgs.add(args[i]);
			}
		}
		
		// Find playerdata
		PlayerData data = plugin.getPlayerDataHandler().getPlayerData(sender.getName(), false);

		String gangName = plugin.getCommands().getFullString(newArgs);

		Gang gang = plugin.getGangHandler().getGang(gangName);

		if (gang != null) {
			sender.sendMessage(Lang.GANG_ALREADY_EXISTS.getConfigValue(null));
			return true;
		}
		
		if (containsInvalidChars(gangName)) {
			sender.sendMessage(Lang.CANNOT_USE_UNICODE_CHARACTERS.getConfigValue(null));
			return true;
		}
		
		gang = plugin.getGangHandler().getGangByLeader(sender.getName());

		if (gang != null) {
			sender.sendMessage(Lang.ALREADY_HAVE_ANOTHER_GANG.getConfigValue(new String[] {gang.getGangName()}));
			return true;
		}
		
		if (data.isInGang()) {
			sender.sendMessage(Lang.ALREADY_IN_ANOTHER_GANG.getConfigValue(new String[] {data.getGangName()}));
			return true;
		}

		gang = plugin.getGangHandler().createNewGang(gangName, sender.getName());
		
		// Add the leader as member
		gang.addMember(sender.getName());
		gang.setInfo("leader", sender.getName());
		
		// Set gang and rank name
		data.setGangName(gang.getGangName());
		data.setRankName(gang.getLeadersTitle());

		sender.sendMessage(Lang.NEW_GANG_CREATED.getConfigValue(new String[] {gangName}));
		
		plugin.getServer().broadcastMessage(Lang.NEW_GANG_ARISEN.getConfigValue(new String[] {gangName}));
		
		return true;
	}
	
	private boolean containsColours(String text) {
		text = text.toLowerCase();
		
		for (String code: codes) {
			if (text.contains(code)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean containsInvalidChars(String text) {
		if (containsColours(text)) return true;
		
		for (char c: text.toCharArray()) {
			
			int ci = c;
			
			//System.out.print(ci);
			
			// Invalid unicode chars (see http://en.wikipedia.org/wiki/Miscellaneous_Symbols and http://www.ssec.wisc.edu/~tomw/java/unicode.html#x0000)
			// Valid are: [a-z][A-Z][0-9][-& ]
			if (ci != 32 && ci != 45 && ci != 38 && !(ci >= 48 && ci <= 59) && !(ci >= 65 && ci <= 90) && !(ci >= 97 && ci <= 122)) {
				return true;
			}
		}
		
		return false;
	}
}
