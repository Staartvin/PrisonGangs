package me.staartvin.prisongang.voting;

import java.util.List;
import java.util.Random;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.gang.Gang;
import me.staartvin.prisongang.playerdata.PlayerData;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class VotingCheckerTask extends BukkitRunnable {

	private PrisonGang plugin;
	
	public VotingCheckerTask(PrisonGang instance) {
		plugin = instance;
	}
	
	
	@Override
	public void run() {
		// Get all gangs that are running an election -> check whether the election time has passed -> action accordingly
		
		for (Gang gang: plugin.getGangHandler().getAllGangs()) {
			
			// No election happening
			if (!gang.isVoteInProgress()) continue;
			
			long startTime = gang.getElectionStartTime();
			
			if (startTime < 0) {
				continue;
			}
			
			long currentTime = System.currentTimeMillis();
			
			long difference = currentTime - startTime;
			
			// 1 hour = 3600000 ms
			long difInHours = (difference / 3600000);
			
			// Not yet 24 hours
			if (difInHours <= 24) {
				continue;
			}
			
			// Time has passed.
			// Stop election
			// Find a new leader
			
			// Stop election
			gang.stopElection();
			
			
			// Find a new (random leader)
			
			boolean foundNewLeader = false;
			
			while (!foundNewLeader) {
				Random rand = new Random();
				
				List<String> members = gang.getMembers();
				
				int randMemb = rand.nextInt(members.size());
				
				String member = members.get(randMemb);
				
				// We cannot have the leader we had before
				if (member.equals(gang.getLeader())) {
					continue;
				}
				
				// Get old leader
				PlayerData oldLeader = plugin.getPlayerDataHandler().getPlayerData(gang.getLeader(), true);
				
				// Remove leaders title from old leader
				oldLeader.setRankName(null);
				
				// Set new leader
				
				gang.setInfo("leader", member);
				
				PlayerData newLeader = plugin.getPlayerDataHandler().getPlayerData(member, true);
				
				newLeader.setRankName(gang.getLeadersTitle());

				// Broadcast through server
				plugin.getServer().broadcastMessage(ChatColor.GOLD + member + ChatColor.GREEN + " is now the new leader of " + ChatColor.GOLD + gang.getGangName());
				
				foundNewLeader = true;
				
			}
		}
		
	}

}
