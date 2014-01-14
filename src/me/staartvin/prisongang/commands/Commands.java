package me.staartvin.prisongang.commands;

import java.util.HashMap;
import java.util.List;

import me.staartvin.prisongang.PrisonGang;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {

	private PrisonGang plugin;
	private CreateCommand create;
	private InfoCommand info;
	private JoinCommand join;
	private GangsListCommand list;
	private LeaveCommand leave;
	private DisbandCommand disband;
	private AdminRemoveCommand adminRemove;
	private KickCommand kick;
	private HelpCommand help;
	private TitleCommand title;
	private StatusCommand status;
	private SetCommand set;
	private AdminSaveCommand adminSave;
	private ChatSwitchCommand chatSwitch;
	private SetListCommand setList;
	private AbilityAddCommand abilityAdd;
	private AbilityRemoveCommand abilityRemove;
	private AbilityListCommand abilityList;
	private ShowMembersCommand showMembers;
	private ShowAlliesCommand showAllies;
	private ShowEnemiesCommand showEnemies;
	private InviteCommand invite;
	private UnInviteCommand unInvite;
	private VoteCommand vote;
	private CallVoteCommand callVote;
	private GangBroadcastCommand broadcast;

	public Commands(PrisonGang instance) {
		plugin = instance;
		create = new CreateCommand(instance);
		info = new InfoCommand(instance);
		join = new JoinCommand(instance);
		list = new GangsListCommand(instance);
		leave = new LeaveCommand(instance);
		disband = new DisbandCommand(instance);
		adminRemove = new AdminRemoveCommand(instance);
		kick = new KickCommand(instance);
		help = new HelpCommand(instance);
		title = new TitleCommand(instance);
		status = new StatusCommand(instance);
		set = new SetCommand(instance);
		adminSave = new AdminSaveCommand(instance);
		chatSwitch = new ChatSwitchCommand(instance);
		setList = new SetListCommand(instance);
		abilityAdd = new AbilityAddCommand(instance);
		abilityRemove = new AbilityRemoveCommand(instance);
		abilityList = new AbilityListCommand(instance);
		showMembers = new ShowMembersCommand(instance);
		showAllies = new ShowAlliesCommand(instance);
		showEnemies = new ShowEnemiesCommand(instance);
		invite = new InviteCommand(instance);
		unInvite = new UnInviteCommand(instance);
		vote = new VoteCommand(instance);
		callVote = new CallVoteCommand(instance);
		broadcast = new GangBroadcastCommand(instance);
	}
	
	public static String[] allowedSetArgs = {"desc", "leader", "private", "leaderTitle"};
	
	// Stores chat mode of player. (global, ally only, gang only)
	public HashMap<String, String> chatMode = new HashMap<String, String>();

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length == 0) {
			sender.sendMessage(ChatColor.BLUE
					+ "-----------------------------------------------------");
			sender.sendMessage(ChatColor.GOLD + "Developed by: "
					+ ChatColor.GRAY + "Staartvin");
			sender.sendMessage(ChatColor.GOLD + "Version: " + ChatColor.GRAY
					+ plugin.getDescription().getVersion());
			sender.sendMessage(ChatColor.YELLOW
					+ "Type /gang help for a list of commands.");
			return true;
		}

		if (args[0].equalsIgnoreCase("create")) {
			return create.onCommand(sender, command, label, args);
		} else if (args[0].equalsIgnoreCase("info")) {
			return info.onCommand(sender, command, label, args);
		} else if (args[0].equalsIgnoreCase("join")) {
			return join.onCommand(sender, command, label, args);
		} else if (args[0].equalsIgnoreCase("list")) {
			return list.onCommand(sender, command, label, args);
		} else if (args[0].equalsIgnoreCase("leave")) {
			return leave.onCommand(sender, command, label, args);
		} else if (args[0].equalsIgnoreCase("disband")) {
			return disband.onCommand(sender, command, label, args);
		} else if (args[0].equalsIgnoreCase("kick")) {
			return kick.onCommand(sender, command, label, args);
		} else if (args[0].equalsIgnoreCase("help")) {
			return help.onCommand(sender, command, label, args);
		} else if (args[0].equalsIgnoreCase("admin")) {
			
			
			if (args.length >= 2) {
				if (args[1].equalsIgnoreCase("remove")) {
					return adminRemove.onCommand(sender, command, label, args);
				} else if (args[1].equalsIgnoreCase("save")) {
					return adminSave.onCommand(sender, command, label, args);
				}
			}
			
			
		} else if (args[0].equalsIgnoreCase("ability") || args[0].equalsIgnoreCase("abi") || args[0].equalsIgnoreCase("abil") ) {

			
			if (args.length >= 2) {
				if (args[1].equalsIgnoreCase("add")) {
					return abilityAdd.onCommand(sender, command, label, args);
				} else if (args[1].equalsIgnoreCase("remove")) {
					return abilityRemove.onCommand(sender, command, label, args);
				} else if (args[1].equalsIgnoreCase("list")) {
					return abilityList.onCommand(sender, command, label, args);
				}
			}
			
			
		} else if (args[0].equalsIgnoreCase("title")) {
			return title.onCommand(sender, command, label, args);
		} else if (args[0].equalsIgnoreCase("neutral") || args[0].equalsIgnoreCase("enemy") || args[0].equalsIgnoreCase("ally")) {
			return status.onCommand(sender, command, label, args);
		} else if (args[0].equalsIgnoreCase("set") && args.length == 2 && args[1].equalsIgnoreCase("list")) {
			return setList.onCommand(sender, command, label, args);
		} else if (args[0].equalsIgnoreCase("set")) {
			return set.onCommand(sender, command, label, args);
		} else if (args[0].equalsIgnoreCase("chat")) {
			return chatSwitch.onCommand(sender, command, label, args);
		} else if (args[0].equalsIgnoreCase("members")) {
			return showMembers.onCommand(sender, command, label, args);
		} else if (args[0].equalsIgnoreCase("allies")) {
			return showAllies.onCommand(sender, command, label, args);
		} else if (args[0].equalsIgnoreCase("enemies")) {
			return showEnemies.onCommand(sender, command, label, args);
		} else if (args[0].equalsIgnoreCase("invite")) {
			return invite.onCommand(sender, command, label, args);
		} else if (args[0].equalsIgnoreCase("uninvite")) {
			return unInvite.onCommand(sender, command, label, args);
		} else if (args[0].equalsIgnoreCase("vote")) {
			return vote.onCommand(sender, command, label, args);
		} else if (args[0].equalsIgnoreCase("callvote")) {
			return callVote.onCommand(sender, command, label, args);
		}  else if (args[0].equalsIgnoreCase("broadcast")) {
			return broadcast.onCommand(sender, command, label, args);
		}

		sender.sendMessage(ChatColor.RED + "Invalid command.");
		sender.sendMessage(ChatColor.YELLOW
				+ "Type /gang help for a list of commands.");
		return true;
	}

	public boolean hasPermission(CommandSender sender, String permission) {
		if (!sender.hasPermission(permission)) {
			sender.sendMessage(ChatColor.RED + "You need " + permission
					+ " to do this!");
			return false;
		}
		return true;
	}

	public String getFullString(List<String> args) {
		String newString = "";

		for (int i = 0; i < args.size(); i++) {
			if (args.get(i) == null) continue;
			
			if (i == (args.size() - 1)) {
				newString = newString + args.get(i);
			} else {
				newString = newString + args.get(i) + " ";
			}
		}

		return newString;
	}
}
