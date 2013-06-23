package com.github.InspiredOne.InspiredNations;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class Board {

	InspiredNations plugin;
	public Scoreboard board;
	public Objective obj;
	public Team team;
	
	public Board(InspiredNations instance, Player player) {
		plugin = instance;
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		obj = board.registerNewObjective("money", "words");
		obj.setDisplayName("18.54 coins\nWords\n" + ChatColor.RED + "Color\n");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		team = board.registerNewTeam("stinky");
		team.addPlayer(player);
		player.setScoreboard(this.board);
		Score score= obj.getScore(player);
		score.setScore(2);
	}
	


}
