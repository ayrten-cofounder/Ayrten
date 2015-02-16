package com.ayrten.scrots.desktop;

import com.ayrten.scrots.manager.AndroidInterface;
import com.ayrten.scrots.manager.ButtonInterface;
import com.ayrten.scrots.screens.GameScreen;
import com.ayrten.scrots.screens.ScrotsGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher implements AndroidInterface{
	
	private static DesktopLauncher app;
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1920;
		config.height = 1080;
		
		app = new DesktopLauncher();
		
		new LwjglApplication(new ScrotsGame(app), config);
	}

	@Override
	public void shouldShowAd(boolean show) {
		// TODO Auto-generated method stub
	}

	@Override
	public void showToast(String msg) {
		// TODO Auto-generated method stub
	}

	@Override
	public void copyTextToClipboard(String text) {
		// TODO Auto-generated method stub
	}

	@Override
	public void makeYesNoWindow(String title, ButtonInterface yes_interface,
			ButtonInterface no_interface, int color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void makeWindow(String title, String yes_button, String no_button,
			ButtonInterface yes_interface, ButtonInterface no_interface,
			int color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void makeGameOverDialog(ButtonInterface yes_interface,
			ButtonInterface no_interface, int color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void makeGameOverDialogHighScore(GameScreen gameScreen,
			ButtonInterface yes_interface, ButtonInterface no_interface,
			int color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showLeadershipBoard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showAchievements() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unlockAchievement(String name) {
		// TODO Auto-generated method stub
		
	}
}
