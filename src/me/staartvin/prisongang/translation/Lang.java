package me.staartvin.prisongang.translation;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Every enumeration value has its path and default value.
 * To get the path, do {@link #getPath()}.
 * To get the default value, do {@link #getDefault()}.
 * 
 * For the defined value in the lang.yml config, use
 * {@link #getConfigValue(String[])}.
 * A string array is expected for the input. This can be null
 * 
 * @author Staartvin and gomeow
 * 
 */
public enum Lang {
	/**
	 * {0} is not online!
	 */
	PLAYER_NOT_ONLINE("player-not-online", "&4{0} is not online!"),
	/**
	 * Only players can do this!
	 */
	ONLY_PLAYER_ACTIVITY("only-player-activity", "&4Only players can do this!"),
	/**
	 * You're not in a gang!
	 */
	NOT_IN_A_GANG("not-in-a-gang", "&4You're not in a gang!"),
	/**
	 * You're not authorised to do this.
	 */
	NOT_AUTHORISED("not-authorised", "&4You're not authorised to do this."),
	/**
	 * That gang does not exist.
	 */
	GANG_DOES_NOT_EXIST("gang-does-not-exist", "&4That gang does not exist."),
	/**
	 * {0} is not in the same gang as you are.
	 */
	PLAYER_IS_NOT_IN_SAME_GANG("player-is-not-in-same-gang", "&4{0} is not in the same gang as you are."),
	/**
	 * {0} already has that ability.
	 */
	PLAYER_ALREADY_HAS_ABILITY("player-already-has-ability", "&4{0} already has that ability."),
	/**
	 * You've given {0} '{1}'.
	 */
	GAVE_ABILITY_TO_PLAYER("gave-ability-to-player", "&aYou've given &6{0}&a '{1}'."),
	/**
	 * You got '{0}' from {1}
	 */
	GOT_ABILITY_FROM_PLAYER("got-ability-from-player", "&aYou got '{0}' from &6{1}."),
	/**
	 * You cannot remove abilities from the leader.
	 */
	CANNOT_REMOVE_ABILITIES_FROM_LEADER("cannot-remove-abilities-from-leader", "&4You cannot remove abilities from the leader."),
	/**
	 * {0} doesn't have that ability.
	 */
	PLAYER_DOES_NOT_HAVE_ABILITY("player-does-not-have-ability", "&4{0} doesn't have that ability."),
	/**
	 * Took '{0}' from {1}.
	 */
	TOOK_ABILITY_FROM_PLAYER("took-ability-from-player", "&aTook '{0}' from &6{1}."),
	/**
	 * {0} took '{1}' from you.
	 */
	LOST_ABILITY_FROM_PLAYER("lost-ability-from-player", "&6{0}&a took '{1}' from you."),
	/**
	 * Gang '{0}' has fallen!
	 */
	GANG_DISBANDED("gang-disbanded", "&eGang '&6{0}&e' has fallen!"),
	/**
	 * There already is an election running.
	 */
	ELECTION_ALREADY_RUNNING("election-already-running", "&4There already is an election running."),
	/**
	 * {0} started an election for a new leader!
	 */
	PLAYER_STARTED_ELECTION("player-started-election", "&6{0}&e started an election for a new leader!"),
	/**
	 * Chat mode has been switched to {0}.
	 */
	CHAT_MODE_SWITCHED("chat-mode-switched", "&aChat mode has been switched to &6{0}&a."),
	/**
	 * There already is a gang with that name.
	 */
	GANG_ALREADY_EXISTS("gang-already-exists", "&4There already is a gang with that name."),
	/**
	 * You cannot use formatting codes or unicode symbols.
	 */
	CANNOT_USE_UNICODE_CHARACTERS("cannot-use-unicode-characters", "&4You cannot use formatting codes or unicode symbols."),
	/**
	 * You already have another gang called '{0}'.
	 */
	ALREADY_HAVE_ANOTHER_GANG("already-have-another-gang", "&4You already have another gang called '&6{0}&4'."),
	/**
	 * You already are in another gang called '{0}'
	 */
	ALREADY_IN_ANOTHER_GANG("already-in-another-gang", "&4You already are in another gang called '&6{0}&4'."),
	/**
	 * New gang '{0}' has been created.
	 */
	NEW_GANG_CREATED("new-gang-created", "&aNew gang '&e{0}&a' has been created."),
	/**
	 * The new gang '{0}' has arisen!
	 */
	NEW_GANG_ARISEN("new-gang-arisen", "&eThe new gang '&6{0}&e' has arisen!"),
	/**
	 * Only a leader can disband a gang.
	 */
	ONLY_LEADER_CAN_DISBAND("only-leader-can-disband", "&4Only a leader can disband a gang."),
	/**
	 * Please provide a gang.
	 */
	PLEASE_PROVIDE_GANG("please-provide-gang", "&4Please provide a gang."),
	/**
	 * This is not a private gang and therefore anybody can join.
	 */
	NOT_A_PRIVATE_GANG_ANYONE_CAN_JOIN("not-a-private-gang-anyone-can-join", "&4This is not a private gang and therefore anybody can join."),
	/**
	 * {0} is already invited.
	 */
	PLAYER_ALREADY_INVITED("player-already-invited", "&6{0}&4 is already invited."),
	/**
	 * {0} is already in your gang.
	 */
	PLAYER_ALREADY_IN_GANG("player-already-in-gang", "&6{0}&4 is already in your gang."),
	/**
	 * {0} has been invited to your gang.
	 */
	INVITED_PLAYER_TO_GANG("invited-player-to-gang", "&6{0}&a has been invited to your gang."),
	/**
	 * {0} has invited you to {1}.
	 */
	YOU_ARE_INVITED("you-are-invited", "&6{0}&a has invited you to &6{1}."),
	/**
	 * This is a private gang. You need to be invited first.
	 */
	GANG_IS_PRIVATE("gang-is-private", "&4This is a private gang. &eYou need to be invited first."),
	/**
	 * You joined {0}.
	 */
	JOINED_GANG("joined-gang", "&aYou joined &6{0}."),
	/**
	 * You cannot kick the leader.
	 */
	CANNOT_KICK_LEADER("cannot-kick-leader", "&4You cannot kick the leader."),
	/**
	 * You cannot kick yourself, try '/gang leave' instead.
	 */
	CANNOT_KICK_YOURSELF("cannot-kick-yourself", "&4You cannot kick yourself, try '&6/gang leave&4' instead."),
	/**
	 * {0} does not exist.
	 */
	PLAYER_DOES_NOT_EXIST("player-does-not-exist", "&6{0}&4 does not exist."),
	/**
	 * {0} is not in your gang.
	 */
	PLAYER_IS_NOT_IN_YOUR_GANG("player-is-not-in-your-gang", "&6{0}&4 is not in your gang."),
	/**
	 * You kicked {0} out of {1}.
	 */
	KICKED_PLAYER_OUT_OF_GANG("kicked-player-out-of-gang", "&aYou kicked &6{0}&a out of &6{1}&a."),
	/**
	 * You got kicked out of {0} by {1}.
	 */
	YOU_GOT_KICKED("you-got-kicked", "&aYou got kicked out of &6{0}&a by &6{1}&a."),
	/**
	 * You cannot leave your gang, try '/gang disband' instead.
	 */
	YOU_CANNOT_LEAVE_BECAUSE_LEADER("you-cannot-leave-because-leader", "&4You cannot leave your gang, try '&6/gang disband&4' instead."),
	/**
	 * You left {0}.
	 */
	LEFT_GANG("left-gang", "&aYou left &6{0}."),
	/**
	 * Changed private mode to {0}.
	 */
	CHANGED_PRIVATE_MODE("changed-private-mode", "&aChanged private mode to &6{0}&a."),
	/**
	 * You cannot target your own gang!
	 */
	CANNOT_TARGET_OWN_GANG("cannot-target-own-gang", "&4You cannot target your own gang!"),
	/**
	 * You and {0} are already {1}.
	 */
	ALREADY_STATUS("already-status", "&4You and &6{0}&4 are already &6{1}&a."),
	/**
	 * You and {0} are now {1}.
	 */
	CHANGED_STATUS("changed-status", "&aYou and &6{0}&a are now &6{1}&a."),
	/**
	 * You cannot use the leaders title as title.
	 */
	CANNOT_USE_LEADERS_TITLE("cannot-use-leaders-title", "&4You cannot use the leaders title as title."),
	/**
	 * You cannot change the title of the leader.
	 */
	CANNOT_CHANGE_TITLE_LEADER("cannot-change-title-leader", "&4You cannot change the title of the leader."),
	/**
	 * You changed the title of {0} to {1}.
	 */
	CHANGED_TITLE_OF_PLAYER("changed-title-of-player", "&aYou changed the title of &6{0}&a to &6{1}&a."),
	/**
	 * {0} is not invited.
	 */
	PLAYER_NOT_INVITED("player-not-invited", "&6{0}&4 is not invited."),
	/**
	 * {0} is no longer invited.
	 */
	PLAYER_NO_LONGER_INVITED("player-no-longer-invited", "&6{0}&a is no longer invited."),
	/**
	 * You are no longer invited by {0}.
	 */
	YOU_ARE_NO_LONGER_INVITED("you-are-no-longer-invited", "&aYou are no longer invited by &6{0}&a."),
	/**
	 * There is no election running.
	 */
	NO_ELECTION_RUNNING("no-election-running", "&4There is no election running."),
	/**
	 * You already voted for this election.
	 */
	YOU_ALREADY_VOTED("you-already-voted", "&4You already voted for this election."),
	/**
	 * You voted for this election.
	 */
	YOU_VOTED("you-voted", "&aYou voted for this election."),
	/**
	 * {0} voted for a new leader!
	 */
	PLAYER_VOTED_BROADCAST("player-voted-broadcast", "&6{0}&a voted for a new leader!"),
	/**
	 * {0} is now the new leader of {1}!
	 */
	PLAYER_IS_NEW_LEADER("player-is-new-leader", "&6{0}&a is now the new leader of &6{1}&a!"),
	/**
	 * You cannot join {0}, it is full.
	 */
	GANG_IS_FULL("gang-is-full", "&4You cannot join &6{0}&4, it is full."),
	/**
	 * This message is invalid.
	 */
	INVALID_BROADCAST_MESSAGE("invalid-broadcast-message", "&4This message is invalid."),
	/**
	 * You've already sent an ally request to {0}.
	 */
	GANG_ALREADY_SENT_ALLY_REQUEST("gang-already-sent-ally-request", "&4You've already sent an ally request to &6{0}&4."),
	/**
	 * You've sent {0} an ally request.
	 */
	SENT_GANG_ALLY_REQUEST("sent-gang-ally-request", "&aYou've sent &6{0}&a an ally request."),
	/**
	 * {0} has sent your gang an ally request!
	 */
	BROADCAST_NEW_ALLY_REQUEST("broadcast-new-ally-request", "&6{0}&a has sent your gang an ally request!"),
	/**
	 * You and {0} are now {1}!
	 */
	BROADCAST_NEW_STATUS("broadcast-new-status", "&aYou and &6{0}&a are now &6{1}&a!"),
	/**
	 * {0} has joined the gang!
	 */
	BROADCAST_NEW_MEMBER("broadcast-new-member", "&6{0}&a has joined the gang!"),
	/**
	 * {0} has been kicked from the gang!
	 */
	BROADCAST_KICK_MEMBER("broadcast-kick-member", "&6{0}&a has been kicked from the gang!"),
	/**
	 * {0} has been kicked from the gang!
	 */
	NO_NEW_LEADER_CHOSEN("no-new-leader-chosen", "There is no new leader chosen because there is only one player!"),
	
	
	
	;

	private String path, def;
	private static FileConfiguration LANG;

	/**
	 * Lang enum constructor.
	 * 
	 * @param path The string path.
	 * @param start The default string.
	 */
	Lang(final String path, final String start) {
		this.path = path;
		this.def = start;
	}

	/**
	 * Set the {@code FileConfiguration} to use.
	 * 
	 * @param config The config to set.
	 */
	public static void setFile(final FileConfiguration config) {
		LANG = config;
	}

	/**
	 * Get the value in the config with certain arguments
	 * 
	 * @param args arguments that need to be given. (Can be null)
	 * @return value in config or otherwise default value
	 */
	public String getConfigValue(final String[] args) {
		String value = ChatColor.translateAlternateColorCodes('&',
				LANG.getString(this.path, this.def));

		if (args == null)
			return value;
		else {
			if (args.length == 0)
				return value;

			for (int i = 0; i < args.length; i++) {
				value = value.replace("{" + i + "}", args[i]);
			}
		}

		return value;
	}

	/**
	 * Get the default value of the path.
	 * 
	 * @return The default value of the path.
	 */
	public String getDefault() {
		return this.def;
	}

	/**
	 * Get the path to the string.
	 * 
	 * @return The path to the string.
	 */
	public String getPath() {
		return this.path;
	}
}
