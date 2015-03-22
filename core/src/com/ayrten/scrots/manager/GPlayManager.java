package com.ayrten.scrots.manager;

import java.util.HashMap;

import com.badlogic.gdx.utils.Json;

public class GPlayManager {
	private final String LVL_REGEXP = "passed_lvl%d";
	private final String DOT_REGEXP = "popped_%d_dots";
	private final String CHECKLIST_NAME = "gplay_achievement_checklist";

	private int dot_count;

	// Achievement types.
	private HashMap<Integer, String> level_achievements;
	private HashMap<Integer, String> dot_achievements;

	// Checklist for checking if you already unlocked the achievement.
	private HashMap<String, Boolean> checklist_achievements;

	// Conversions of achievements to points.
	private HashMap<String, Integer> achievement_points;

	public GPlayManager() {
		dot_count = 0;
		
		initializeHashMaps();

		addAchievement(5, LVL_REGEXP, level_achievements, 50);
		addAchievement(10, LVL_REGEXP, level_achievements, 100);
		addAchievement(15, LVL_REGEXP, level_achievements, 200);

		addAchievement(1000, DOT_REGEXP, dot_achievements, 100);
		addAchievement(5000, DOT_REGEXP, dot_achievements, 200);
		addAchievement(10000, DOT_REGEXP, dot_achievements, 400);
		addAchievement(25000, DOT_REGEXP, dot_achievements, 600);
		addAchievement(50000, DOT_REGEXP, dot_achievements, 1000);
		addAchievement(100000, DOT_REGEXP, dot_achievements, 2000);

		addAchievements("unlock_rainbow", false, checklist_achievements, 150);
		addAchievements("unlock_invincible", false, checklist_achievements, 150);
		addAchievements("unlock_magnet", false, checklist_achievements, 150);
	}

	private void initializeHashMaps() {
		level_achievements = new HashMap<Integer, String>();
		dot_achievements = new HashMap<Integer, String>();
		achievement_points = new HashMap<String, Integer>();

		// String text = Assets.readFile(CHECKLIST_NAME);
		// if(!text.isEmpty()) {
		// Json json = new Json();
		// checklist_achievements = json.fromJson(HashMap.class, text);
		// System.out.println(json.prettyPrint(text));
		// }
		// else
		// checklist_achievements = new HashMap<String, Boolean>();

//		if (Assets.game.apk_intf.is_gplay_signedin()) {
//			boolean isOK = Assets.game.apk_intf.loadAchievements(checklist_achievements);
//			if (!isOK)
//				checklist_achievements = new HashMap<String, Boolean>();
//		}
//		else
			checklist_achievements = new HashMap<String, Boolean>();
	}

	private void addAchievement(int num, String regexp,
			HashMap<Integer, String> map, int points) {
		String achievement_name = String.format(regexp, num);
		map.put(num, achievement_name);
		achievement_points.put(achievement_name, points);
		checklist_achievements.put(achievement_name, false);
	}

	private void addAchievements(String name, boolean value,
			HashMap<String, Boolean> map, int points) {
		map.put(name, value);
		achievement_points.put(name, points);
	}

	public boolean isAchievementLevel(int lvl) {
		return (level_achievements.containsKey(lvl));
	}

	public boolean isDotAchievement(int dot_count) {
		return (dot_achievements.containsKey(dot_count));
	}

	public void unlockLevelAchievement(int lvl) {
		unlockAchievement(String.format(LVL_REGEXP, lvl));
	}

	public boolean isAchievementPop(int num) {
		return (dot_achievements.containsKey(num));
	}

	public void unlockDotAchievement(int num) {
		unlockAchievement(String.format(DOT_REGEXP, num));
	}

	public void unlockAchievement(String achievement_name) {
		if (achievement_points.containsKey(achievement_name)
				&& Assets.game.apk_intf.is_gplay_signedin()
				&& !isUnlocked(achievement_name)) {
			Assets.game.apk_intf.unlockAchievement(achievement_name);
			checklist_achievements.put(achievement_name, true);
		}
	}
	
	public void addPoints(String achievement_name)
	{
		Assets.points_manager.addPoints(achievement_points.get(achievement_name));
	}

	public boolean isUnlocked(String achievement_name) {
		return (checklist_achievements.get(achievement_name) == true);
	}

	public void increment_dot_count() {
		dot_count++;
		if (isDotAchievement(dot_count))
			unlockDotAchievement(dot_count);
	}

	public void dispose() {
		Json json = new Json();
		Assets.writeFile(CHECKLIST_NAME, json.toJson(checklist_achievements));
		Assets.writeFile(Assets.GPLAY_FILE, json.toJson(this));
	}
}
