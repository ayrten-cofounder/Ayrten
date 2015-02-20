package com.ayrten.scrots.statistics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class StatisticsManager {
	protected String file = "statistics.txt";
	
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
//		System.out.println(Stat.values().length);
	}
	
	public Stats stats;

	public StatisticsManager()
	{
		stats = getStats();
	}

//	public void setStats(int amount) {
//		Stats stats = getStats();
//		Json json = new Json();
//
//		stats.green_dots_popped += amount;
//
//		writeFile(this.file, json.toJson(stats));
//	}

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
