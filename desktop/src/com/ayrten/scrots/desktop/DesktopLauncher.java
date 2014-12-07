package com.ayrten.scrots.desktop;

import com.ayrten.scrots.screens.AndroidInterface;
import com.ayrten.scrots.screens.ScrotsGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher implements AndroidInterface{
	
	private static DesktopLauncher app;
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1200;
		config.height = 600;
		
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
}
