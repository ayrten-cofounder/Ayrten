package com.ayrten.scrots.manager;

import com.ayrten.scrots.screens.GameScreen;

public interface AndroidInterface {
	public void shouldShowAd(boolean show);

	public void showToast(String msg);

	public void copyTextToClipboard(String text);

	public void makeYesNoWindow(String title, ButtonInterface yes_interface,
			ButtonInterface no_interface, int color);

	public void makeWindow(String title, String yes_button, String no_button,
			ButtonInterface yes_interface, ButtonInterface no_interface, int color);

	public void makeGameOverDialog(ButtonInterface yes_interface,
			ButtonInterface no_interface, int color);
	
	public void makeGameOverDialogHighScore(GameScreen gameScreen, ButtonInterface yes_interface,
			ButtonInterface no_interface, int color);
	
	public void showLeadershipBoard();
	
	public void showAchievements();

	public void unlockAchievement(String name);
}
