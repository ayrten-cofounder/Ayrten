package com.ayrten.scrots.statistics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class StatisticsManager {
	protected String file = "statistics2.txt";
	
	public enum Stat
	{
		reg_dot_1, 
		reg_dot_2, 
		reg_dot_3, 
		
		pen_dot_1,
		pen_dot_2,
		
		dwd_reg_dot_1,
		dwd_reg_dot_2,
		dwd_reg_dot_3,
		
		dwd_pen_dot_1,
		dwd_pen_dot_2,
		dwd_pen_dot_3,
		
		power_dot_magnet,
		power_dot_invincible,
		power_dot_rainbow
	};

	public static class Stats
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
	
	public Stats stats;

	public StatisticsManager()
	{
		stats = getStats();
	}

	public Stats getStats() {
		String file = readFile(this.file);

		if (!file.isEmpty()) {
			Json json = new Json();
			Stats points = json.fromJson(Stats.class, file);

			return points;
		} else {
			Json json = new Json();
			Stats stats = new Stats();

			writeFile(this.file, json.toJson(stats));
		}

		return new Stats();
	}
	
	public void writeToFile()
	{
		Json json = new Json();

		writeFile(this.file, json.toJson(stats));
	}

	public void ClearStats()
	{
		Stats stats = new Stats();
		Json json = new Json();

		writeFile(this.file, json.toJson(stats));
	}

	public static void writeFile(String fileName, String s) {
		FileHandle file = Gdx.files.local(fileName);
		file.writeString(com.badlogic.gdx.utils.Base64Coder.encodeString(s),
				false);
	}

	public static String readFile(String fileName) {
		FileHandle file = Gdx.files.local(fileName);
		if (file != null && file.exists()) {
			String s = file.readString();
			if (!s.isEmpty()) {
				return com.badlogic.gdx.utils.Base64Coder.decodeString(s);
			}
		}
		return "";
	}
}
