package com.ayrten.scrots.statistics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class StatisticsManager {
	protected final String STATISTICS_FILENAME = "scs";
	
	public static class PlayerStats
	{
		public Statistics reg_dot_1 = new Statistics();
		public Statistics reg_dot_2 = new Statistics();
		public Statistics reg_dot_3 = new Statistics(); 
		
		public Statistics pen_dot_1 = new Statistics();
		public Statistics pen_dot_2 = new Statistics();
		
		public Statistics dwd_reg_dot_1 = new Statistics();
		public Statistics dwd_reg_dot_2 = new Statistics();
		public Statistics dwd_reg_dot_3 = new Statistics();
		
		public Statistics dwd_pen_dot_1 = new Statistics();
		public Statistics dwd_pen_dot_2 = new Statistics();
		public Statistics dwd_pen_dot_3 = new Statistics();
		
		public Statistics power_dot_magnet = new Statistics();
		public Statistics power_dot_invincible = new Statistics();
		public Statistics power_dot_rainbow = new Statistics();
	}
	
	private PlayerStats playerStats;
	private Json json;

	public StatisticsManager()
	{
		json = new Json();
		playerStats = getPlayerStatsFromFile();
	}
	
	public void writePlayerStatsToFile() { writeFile(STATISTICS_FILENAME, json.toJson(playerStats)); }
	public void clearPlayerStatsFromFile(){ writeFile(STATISTICS_FILENAME, json.toJson(new PlayerStats())); }
	public PlayerStats getPlayerStats() { return this.playerStats; }
	
	/**
	 * Helpers
	 */
	public PlayerStats getPlayerStatsFromFile() {
		String file = readFile(STATISTICS_FILENAME);
		
		if (!file.isEmpty()) {
			return json.fromJson(PlayerStats.class, file);

		} else {
			PlayerStats ps = new PlayerStats();
			writeFile(STATISTICS_FILENAME, json.toJson(ps));
			return ps;
		}
	}
	
	private static String readFile(String fileName) {
		FileHandle file = Gdx.files.local(fileName);
		if (file != null && file.exists()) {
			String s = file.readString();
			if (!s.isEmpty()) {
				return com.badlogic.gdx.utils.Base64Coder.decodeString(s);
			}
		}
		return "";
	}

	private static void writeFile(String fileName, String s) {
		FileHandle file = Gdx.files.local(fileName);
		file.writeString(com.badlogic.gdx.utils.Base64Coder.encodeString(s), false);
	}
}