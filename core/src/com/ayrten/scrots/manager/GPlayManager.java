package com.ayrten.scrots.manager;

import java.util.HashMap;

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
	public static final String ACHIEVEMENT_UNLOCK_DOT_DECELERATE = "CgkIxpik37gbEAIQDg";

	// Googleplay achievement checklist.
	private final String CHECKLIST_NAME = "gac";

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
			checklist_achievements = Assets.json.fromJson(HashMap.class, file);
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
			checklist_achievements.put(ACHIEVEMENT_UNLOCK_DOT_DECELERATE, false);
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
		int amount = 0;
		if (ACHIEVEMENT_GREEN_POP_1000.equals(achievement_name)) {
			Assets.points_manager.addPoints(100);
			amount = 100;
		} else if (ACHIEVEMENT_GREEN_POP_5000.equals(achievement_name)) {
			Assets.points_manager.addPoints(200);
			amount = 200;
		} else if (ACHIEVEMENT_GREEN_POP_10000.equals(achievement_name)) {
			Assets.points_manager.addPoints(400);
			amount = 400;
		} else if (ACHIEVEMENT_GREEN_POP_25000.equals(achievement_name)) {
			Assets.points_manager.addPoints(600);
			amount = 600;
		} else if (ACHIEVEMENT_GREEN_POP_50000.equals(achievement_name)) {
			Assets.points_manager.addPoints(1000);
			amount = 1000;
		} else if (ACHIEVEMENT_GREEN_POP_100000.equals(achievement_name)) {
			Assets.points_manager.addPoints(2000);
			amount = 2000;
		} else if (ACHIEVEMENT_LEVEL_CLEAR_5.equals(achievement_name)) {
			Assets.points_manager.addPoints(50);
			amount = 50;
		} else if (ACHIEVEMENT_LEVEL_CLEAR_10.equals(achievement_name)) {
			Assets.points_manager.addPoints(100);
			amount = 100;
		} else if (ACHIEVEMENT_LEVEL_CLEAR_15.equals(achievement_name)) {
			Assets.points_manager.addPoints(1000);
			amount = 1000;
		} else if (ACHIEVEMENT_UNLOCK_DOT_RAINBOW.equals(achievement_name)) {
			Assets.points_manager.addPoints(150);
			amount = 150;
		} else if (ACHIEVEMENT_UNLOCK_DOT_INVINCIBLE.equals(achievement_name)) {
			Assets.points_manager.addPoints(150);
			amount = 150;
		} else if (ACHIEVEMENT_UNLOCK_DOT_MAGNET.equals(achievement_name)) {
			Assets.points_manager.addPoints(150);
			amount = 150;
		} else if (ACHIEVEMENT_UNLOCK_DOT_DECELERATE.equals(achievement_name)) {
			Assets.points_manager.addPoints(150);
			amount = 150;
		}
			
		Assets.game.apk_intf.showToast(String.format("Received %0d points for unlocking an achievement!", amount));
	}

	public boolean isUnlocked(String achievement_name) {
		return checklist_achievements.get(achievement_name);
	}

	public void dispose() {
		Assets.writeFile(CHECKLIST_NAME, Assets.json.toJson(checklist_achievements));
	}
}
