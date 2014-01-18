package me.staartvin.prisongang.gang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import me.staartvin.prisongang.PrisonGang;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * This class represents one gang. When the server starts, all gangs from the
 * config are loaded into such class. Every gang has its own Gang class.
 * You can get all the information about a gang here. (creation date, members,
 * leader, description, etc.)
 * 
 * @author Staartvin
 * 
 */
public class Gang {

	private PrisonGang plugin;

	public Gang(PrisonGang instance, String gangName) {
		plugin = instance;
		this.gangName = gangName;
	}

	// Stores members of the gang
	private List<String> members = new ArrayList<String>();

	// Stores valuable information
	private HashMap<String, String> info = new HashMap<String, String>();

	// Leader's title
	private String leadersTitle = "Crime Lord";

	// Gang name
	private String gangName = "Default gang name";

	// Allies of the gang
	private List<String> allies = new ArrayList<String>();

	// Enemies of the gang
	private List<String> enemies = new ArrayList<String>();
	
	// Tag that is used to get the ally requests
	private String allyRequestsTag = "allyrequests";
	
	// Tag that is used to get the people who voted.
	private String votesTag = "votes";

	/**
	 * Get the members that are in this group
	 * 
	 * @return list containing all players that are in this group
	 */
	public List<String> getMembers() {
		return members;
	}

	/**
	 * Add a member to the gang
	 * 
	 * @param playerName player to add
	 * @return true if he/she was added; false otherwise
	 */
	public boolean addMember(String playerName) {
		if (!getMembers().contains(playerName)) {
			members.add(playerName);
			return true;
		}
		return false;
	}

	/**
	 * Remove a player from the gang
	 * 
	 * @param playerName player to remove
	 * @return true if he/she was removed; false otherwise
	 */
	public boolean removeMember(String playerName) {
		if (getMembers().contains(playerName)) {
			members.remove(playerName);
			return true;
		}
		return false;
	}

	/**
	 * Get the rank name of a player
	 * 
	 * @param playerName player to get the rank name of
	 * @return name of the rank a player has
	 */
	//public String getRankName(String playerName) {
	//	return rank.get(playerName);
	//}

	/**
	 * Set the rank of a player
	 * 
	 * @param playerName player that will get a new rank name
	 * @param rankName name to set the rank to
	 */
	//public void setRank(String playerName, String rankName) {
	//	rank.put(playerName, rankName);
	//}

	/**
	 * Get certain information (such as creation date, player that created the
	 * gang, reputation, kdr, etc.)
	 * 
	 * @param argumentName argument to get information of
	 * @return value of the argument. It's all in strings, though. When you ask
	 *         for kdr, you'll get a string that can be converted to int.
	 */
	public String getInfo(String argumentName) {
		String arg = findArgument(argumentName);

		if (arg == null) {
			throw new IllegalArgumentException(argumentName
					+ " is not a valid argument!");
		}

		String value;

		// Check if the info contains the key 'arg', is not null, is not equal to '' and is not equal to the string 'null'.
		value = (info.containsKey(arg) && info.get(arg) != null
				&& !info.get(arg).equals("") && !info.get(arg).equals("null")) ? info
				.get(arg) : null;

		return value;
	}

	/**
	 * Set a certain argument to a value
	 * 
	 * @param argumentName argument to change
	 * @param value value to change it to
	 */
	public void setInfo(String argumentName, String value) {

		// Add new info
		info.put(argumentName, value);
	}

	private String findArgument(String argumentName) {
		String argument = null;

		for (String args : info.keySet()) {
			if (args.equalsIgnoreCase(argumentName)) {
				argument = args;
			}
		}

		return argument;
	}

	/**
	 * Get the leader of this gang
	 * 
	 * @return player that leads this gang
	 */
	public String getLeader() {
		return getInfo("leader");
	}

	/**
	 * Get the name of the gang
	 * 
	 * @return name of the gang
	 */
	public String getGangName() {
		return gangName;
	}

	/**
	 * Get allies of the gang
	 * 
	 * @return list containing names of gangs that are allies of this gang
	 */
	public List<String> getAllies() {
		return allies;
	}

	/**
	 * Get enemies of the gang
	 * 
	 * @return list containing names of gangs that are enemies of this gang
	 */
	public List<String> getEnemies() {
		return enemies;
	}

	/**
	 * Add an ally to the gang
	 * 
	 * @param allyName ally to add
	 * @return true if it was added; false otherwise
	 */
	public boolean addAlly(String allyName) {
		if (!getAllies().contains(allyName)) {
			allies.add(allyName);
			return true;
		}
		return false;
	}

	/**
	 * Remove an ally from the gang
	 * 
	 * @param allyName ally to remove
	 * @return true if it was removed; false otherwise
	 */
	public boolean removeAlly(String allyName) {
		if (getAllies().contains(allyName)) {
			allies.remove(allyName);
			return true;
		}
		return false;
	}

	/**
	 * Add an enemt to the gang
	 * 
	 * @param enemyName enemy to add
	 * @return true if it was added; false otherwise
	 */
	public boolean addEnemy(String enemyName) {
		if (!getEnemies().contains(enemyName)) {
			enemies.add(enemyName);
			return true;
		}
		return false;
	}

	/**
	 * Remove an enemy from the gang
	 * 
	 * @param enemyName enemy to remove
	 * @return true if it was removed; false otherwise
	 */
	public boolean removeEnemy(String enemyName) {
		if (getEnemies().contains(enemyName)) {
			enemies.remove(enemyName);
			return true;
		}
		return false;
	}

	/**
	 * Get the title of the leader of this gang
	 * 
	 * @return title of the leader
	 */
	public String getLeadersTitle() {
		return leadersTitle;
	}

	/**
	 * Set the title a leader has for this gang
	 * 
	 * @param leadersTitle title to set it to
	 */
	public void setLeadersTitle(String leadersTitle) {
		this.leadersTitle = leadersTitle;
	}

	/**
	 * Get all the info arguments that are saved
	 * 
	 * @return a list of arguments saved for this gang
	 */
	public Set<String> getInfoKeys() {
		return info.keySet();
	}

	/**
	 * Check whether a certain gang is befriended with this gang.
	 * 
	 * @param gangName Gang name to check for
	 * @return true if allies; false otherwise
	 */
	public boolean isAlly(String gangName) {
		return allies.contains(gangName);
	}

	/**
	 * Check whether a certain gang is enemy of this gang.
	 * 
	 * @param gangName Gang name to check for
	 * @return true if enemies; false otherwise
	 */
	public boolean isEnemy(String gangName) {
		return enemies.contains(gangName);
	}

	/**
	 * Check whether a certain gang is neutral with this gang.
	 * 
	 * @param gangName Gang name to check for
	 * @return true if neutral; false otherwise
	 */
	public boolean isNeutral(String gangName) {
		return !isAlly(gangName) && !isEnemy(gangName);
	}

	/**
	 * Check whether this gang is a private gang.
	 * A player can only join a private gang when he or she is invited.
	 * 
	 * @return true if private; false otherwise.
	 */
	public boolean isPrivate() {
		return (info.containsKey("private") && info.get("private").equals(
				"true"));
	}

	/**
	 * Check whether a player is invited to this gang
	 * 
	 * @param playerName Name of the player to check
	 * @return true if invited; false otherwise
	 */
	public boolean isInvited(String playerName) {
		if (!info.containsKey("invited"))
			return false;
		// No invited players

		// Players are divided by commas (,)
		List<String> invited = getInvited();

		// Check if the player is amongst the invited
		for (String invite : invited) {
			if (invite.equalsIgnoreCase(playerName))
				return true;
		}

		return false;
	}

	/**
	 * Get a list of invited players for this gang
	 * 
	 * @return all players that are invited for this gang
	 */
	public List<String> getInvited() {
		if (!info.containsKey("invited"))
			return new ArrayList<String>();
		// No invited players

		// Players are divided by commas (,)
		List<String> invited = new ArrayList<String>();

		String invitedString = info.get("invited");

		// Invalid string
		if (invitedString == null || !invitedString.contains(","))
			return new ArrayList<String>();

		String[] array = invitedString.split(",");

		for (String ar : array) {
			invited.add(ar.trim());
			// Populate invited list
		}

		return invited;
	}

	/**
	 * Add a player to the 'invited' list.
	 * If a gang is private, only players that are on the invited list can join
	 * the gang.
	 * 
	 * @param playerName Name of the player to add to the list
	 */
	public void invitePlayer(String playerName) {
		// Player is already invited!
		if (isInvited(playerName))
			return;

		String invited = null;

		if (!info.containsKey("invited")) {
			invited = "";
		} else {
			invited = info.get("invited");
		}

		invited = invited + playerName + ",";

		// Store new data
		info.put("invited", invited);
	}

	public void unInvitePlayer(String playerName) {
		// If player is not invited, I cannot uninvite him/her!
		if (!isInvited(playerName))
			return;

		String invited = null;

		if (!info.containsKey("invited")) {
			invited = "";
		} else {
			invited = info.get("invited");
		}

		// Remove name from invited list (+ comma)
		invited = invited.replace(playerName + ",", "").trim();

		// There are no invitees on the list, so it can be null
		if (invited.equals("") || invited.equals("null")) {
			info.remove(invited);
		}

		// Update value
		info.put("invited", invited);
	}

	/**
	 * Set the allies of this gang
	 * 
	 * @param allies allies to set for the gang
	 */
	public void setAllies(List<String> allies) {
		this.allies = allies;
	}

	/**
	 * Set the enemies of this gang
	 * 
	 * @param enemies enemies to set for the gang
	 */
	public void setEnemies(List<String> enemies) {
		this.enemies = enemies;
	}

	/**
	 * Check if a player has voted for a new leader
	 * 
	 * @param playerName Name of the player to check for
	 * @return true if the player has voted; false otherwise.
	 */
	public boolean hasVoted(String playerName) {
		List<String> votes = getVotes();
		
		return votes != null && votes.contains(playerName);
	}

	/**
	 * Check if there is an election in progress
	 * 
	 * @return true if it is; false otherwise.
	 */
	public boolean isVoteInProgress() {
		if (!info.containsKey(votesTag))
			return false;

		if (info.get(votesTag) == null)
			return false;

		if (info.get(votesTag).trim().equals(""))
			return false;

		return true;
	}

	/**
	 * Let a player vote for the election
	 * 
	 * @param playerName Name of the player that will vote
	 */
	public void vote(String playerName) {
		if (!isVoteInProgress())
			return;

		if (hasVoted(playerName))
			return;

		String voted = info.get(votesTag);

		voted = voted + "," + playerName;

		info.put(votesTag, voted);
	}

	/**
	 * Broadcast a message to all online members of this gang.
	 * 
	 * @param message Message to broadcast
	 */
	public void broadcastMessage(String message) {
		// Get all online players
		for (String member : this.members) {
			Player player = plugin.getServer().getPlayer(member);

			// Player is not online
			if (player == null)
				continue;

			player.sendMessage(ChatColor.GOLD + "[Gang Message] "
					+ ChatColor.GREEN + message);
		}
	}

	/**
	 * Start the election for a new leader
	 */
	public void startElection() {
		info.put(votesTag, System.currentTimeMillis() + "");
	}

	/**
	 * Stop the current running election
	 */
	public void stopElection() {
		info.remove(votesTag);
	}

	/**
	 * Get the time (in milliseconds, since Jan 1st 1970) when the election was called.
	 * @return time in milliseconds, negative if invalid.
	 */
	public long getElectionStartTime() {
		if (!isVoteInProgress())
			return -1;

		// First argument == time that vote started, rest of the args are votes.

		String voted = info.get(votesTag);
		String[] args = voted.split(",");
		
		String timeInString = args[0];
		long startTime = -1;
		
		try {
			startTime = Long.parseLong(timeInString);
		} catch (Exception e) {
			throw new IllegalArgumentException("VOTE TIME FOR GANG '" + this.gangName + "' IS INCORRECT!");
		}
		
		return startTime;
	}
	
	/**
	 * Get all players that voted.
	 * @return a list of player that voted; null if error.
	 */
	public List<String> getVotes() {
		if (!info.containsKey(votesTag))
			return null;

		String voted = info.get(votesTag);

		// Invalid string
		if (!voted.contains(","))
			return null;

		// First argument == time that vote started, rest of the args are votes.

		String[] args = voted.split(",");

		// Store a list of players that voted
		List<String> players = new ArrayList<String>();

		// Does not contain any players
		if (args.length < 2)
			return null;

		for (int i = 0; i < args.length; i++) {
			// First argument is the time the voted was called, so that is not a player
			if (i == 0)
				continue;

			players.add(args[i]);
		}

		return players;
	}
	
	/**
	 * Check whether this group is full and cannot accept any more new members
	 * @return true if full; false otherwise.
	 */
	public boolean isFull() {
		return members.size() >= plugin.getMainConfig().getMaxPlayers();
	}
	
	/**
	 * Get a list of gangs that want to be allies with this gang.
	 * @return a list of gangs that want to be allies.
	 */
	public List<String> getAllyRequests() {
		List<String> requestedAllies = new ArrayList<String>();
		
		if (!info.containsKey(allyRequestsTag)) {
			return requestedAllies;
		}
		
		String requests = info.get(allyRequestsTag);
		
		if (!requests.contains(",")) return requestedAllies;
		
		String[] array = requests.split(",");
		
		for (String ar: array) {
			requestedAllies.add(ar.trim());
		}
		
		return requestedAllies;
	}
	
	/**
	 * Remove a gang from the ally request list
	 * @param gang Name of the gang to remove
	 */
	public void removeAllyRequest(String gang) {
		List<String> requests = getAllyRequests();
		
		// That gang never requested allies
		if (!requests.contains(gang)) return;
		
		// Remove gang from list.
		requests.remove(gang);
		
		// Construct the new line.
		String line = "";
		
		for (String req: requests) {
			line = line + req + ",";
		}
		
		if (!line.equals("")) {
			// Save new line
			info.put(allyRequestsTag, line);
		} else {
			// Remove line because we have no requests
			info.remove(allyRequestsTag);
		}
	}
	
	/**
	 * Add a gang to the ally request list
	 * @param gang Name of the gang to add
	 */
	public void addAllyRequest(String gang) {
		String requests = "";
		
		if (info.containsKey(allyRequestsTag)) {
			requests = info.get(allyRequestsTag);
		}
		
		// Update line
		requests = requests + gang + ",";
		
		// Save line
		info.put(allyRequestsTag, requests);
	}
	
	/**
	 * Check to see if this gang has an ally request from another gang
	 * @param gangName Name of the gang that you want it checked for.
	 * @return true if this gang has an ally request from the given gang; false otherwise.
	 */
	public boolean hasAllyRequest(String gangName) {
		return getAllyRequests().contains(gangName);
	}
}
