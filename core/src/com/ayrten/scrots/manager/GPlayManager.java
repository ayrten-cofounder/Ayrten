package com.ayrten.scrots.manager;

import java.util.HashMap;

import com.badlogic.gdx.utils.Json;

public class GPlayManager {
	public static final String ACHIEVEMENT_GREEN_POP_1000 = "CgkIxpik37gbEAIQAg";
	public static final String ACHIEVEMENT_GREEN_POP_5000 = "CgkIxpik37gbEAIQBw";
	public static final String ACHIEVEMENT_GREEN_POP_10000 = "CgkIxpik37gbEAIQCA";
	public static final String ACHIEVEMENT_GREEN_POP_25000 = "CgkIxpik37gbEAIQCQ";
	public static final String ACHIEVEMENT_GREEN_POP_50000 = "CgkIxpik37gbEAIQCg";
	public static final String ACHIEVEMENT_GREEN_POP_100000 = "CgkIxpik37gbEAIQCw";

	public static final String ACHIEVEMENT_LEVEL_CLEAR_5 = "CgkIxpik37gbEAIQBg";
	public static final String ACHIEVEMENT_LEVEL_CLEAR_10 = "CgkIxpik37gbEAIQDA";
	public static final String ACHIEVEMENT_LEVEL_CLEAR_15 = "CgkIxpik37gbEAIQDQ";

	public static final String ACHIEVEMENT_UNLOCK_DOT_RAINBOW = "CgkIxpik37gbEAIQAw";
	public static final String ACHIEVEMENT_UNLOCK_DOT_INVINCIBLE = "CgkIxpik37gbEAIQBA";
	public static final String ACHIEVEMENT_UNLOCK_DOT_MAGNET = "CgkIxpik37gbEAIQBQ";

	private final String CHECKLIST_NAME = "gplay_achievement_checklist";

	private HashMap<Integer, String> dot_achievements;
	private HashMap<Integer, String> level_achievements;

	// Checklist for checking if you already unlocked the achievement.
	private HashMap<String, Boolean> checklist_achievements;

	public GPlayManager() {
		initializeHashMaps();
	}

	@SuppressWarnings("unchecked")
	private void initializeHashMaps() {
		dot_achievements = new HashMap<Integer, String>();
		level_achievements = new HashMap<Integer, String>();

		dot_achievements.put(1000, ACHIEVEMENT_GREEN_POP_1000);
		dot_achievements.put(5000, ACHIEVEMENT_GREEN_POP_5000);
		dot_achievements.put(10000, ACHIEVEMENT_GREEN_POP_10000);
		dot_achievements.put(25000, ACHIEVEMENT_GREEN_POP_25000);
		dot_achievements.put(50000, ACHIEVEMENT_GREEN_POP_50000);
		dot_achievements.put(100000, ACHIEVEMENT_GREEN_POP_100000);

		level_achievements.put(5, ACHIEVEMENT_LEVEL_CLEAR_5);
		level_achievements.put(10, ACHIEVEMENT_LEVEL_CLEAR_10);
		level_achievements.put(15, ACHIEVEMENT_LEVEL_CLEAR_15);

		String file = Assets.readFile(CHECKLIST_NAME);

		if (!file.isEmpty()) {
			Json json = new Json();
			checklist_achievements = json.fromJson(HashMap.class, file);
		} else {
			checklist_achievements = new HashMap<String, Boolean>();
			checklist_achievements.put(ACHIEVEMENT_GREEN_POP_1000, false);
			checklist_achievements.put(ACHIEVEMENT_GREEN_POP_5000, false);
			checklist_achievements.put(ACHIEVEMENT_GREEN_POP_10000, false);
			checklist_achievements.put(ACHIEVEMENT_GREEN_POP_25000, false);
			checklist_achievements.put(ACHIEVEMENT_GREEN_POP_50000, false);
			checklist_achievements.put(ACHIEVEMENT_GREEN_POP_100000, false);

			checklist_achievements.put(ACHIEVEMENT_LEVEL_CLEAR_5, false);
			checklist_achievements.put(ACHIEVEMENT_LEVEL_CLEAR_10, false);
			checklist_achievements.put(ACHIEVEMENT_LEVEL_CLEAR_15, false);

			checklist_achievements.put(ACHIEVEMENT_UNLOCK_DOT_RAINBOW, false);
			checklist_achievements
					.put(ACHIEVEMENT_UNLOCK_DOT_INVINCIBLE, false);
			checklist_achievements.put(ACHIEVEMENT_UNLOCK_DOT_MAGNET, false);
		}
	}

	public boolean isAchievementLevel(int lvl) {
		return (level_achievements.containsKey(lvl));
	}

	public boolean isDotAchievement(int dot_count) {
		return (dot_achievements.containsKey(dot_count));
	}

	public void unlockLevelAchievement(int lvl) {
		if (isAchievementLevel(lvl) && !isUnlocked(level_achievements.get(lvl)))
			unlockAchievement(level_achievements.get(lvl));
	}

	public void unlockDotAchievement(int num) {
		if (isDotAchievement(num) && !isUnlocked(dot_achievements.get(num)))
			unlockAchievement(dot_achievements.get(num));
	}

	public void unlockUnlockPowerDot(String achievement) {
		if (!isUnlocked(achievement))
			unlockAchievement(achievement);
	}

	private void unlockAchievement(String achievement_name) {
		if (Assets.game.apk_intf.is_gplay_signedin() && !isUnlocked(achievement_name)) {
			Assets.game.apk_intf.unlockAchievement(achievement_name);
			checklist_achievements.put(achievement_name, true);
			addPoints(achievement_name);
			dispose();
		}
	}

	private void addPoints(String achievement_name) {
		if (ACHIEVEMENT_GREEN_POP_1000.equals(achievement_name)) {
			Assets.points_manager.addPoints(100);
		} else if (ACHIEVEMENT_GREEN_POP_5000.equals(achievement_name)) {
			Assets.points_manager.addPoints(200);
		} else if (ACHIEVEMENT_GREEN_POP_10000.equals(achievement_name)) {
			Assets.points_manager.addPoints(400);
		} else if (ACHIEVEMENT_GREEN_POP_25000.equals(achievement_name)) {
			Assets.points_manager.addPoints(600);
		} else if (ACHIEVEMENT_GREEN_POP_50000.equals(achievement_name)) {
			Assets.points_manager.addPoints(1000);
		} else if (ACHIEVEMENT_GREEN_POP_100000.equals(achievement_name)) {
			Assets.points_manager.addPoints(2000);
		} else if (ACHIEVEMENT_LEVEL_CLEAR_5.equals(achievement_name)) {
			Assets.points_manager.addPoints(50);
		} else if (ACHIEVEMENT_LEVEL_CLEAR_10.equals(achievement_name)) {
			Assets.points_manager.addPoints(100);
		} else if (ACHIEVEMENT_LEVEL_CLEAR_15.equals(achievement_name)) {
			Assets.points_manager.addPoints(1000);
		} else if (ACHIEVEMENT_UNLOCK_DOT_RAINBOW.equals(achievement_name)) {
			Assets.points_manager.addPoints(150);
		} else if (ACHIEVEMENT_UNLOCK_DOT_INVINCIBLE.equals(achievement_name)) {
			Assets.points_manager.addPoints(150);
		} else if (ACHIEVEMENT_UNLOCK_DOT_MAGNET.equals(achievement_name)) {
			Assets.points_manager.addPoints(150);
		}
	}

	public boolean isUnlocked(String achievement_name) {
		return checklist_achievements.get(achievement_name);
	}

	public void dispose() {
		Json json = new Json();
		Assets.writeFile(CHECKLIST_NAME, json.toJson(checklist_achievements));
	}
}
