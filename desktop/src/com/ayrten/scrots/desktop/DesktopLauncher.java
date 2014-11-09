package com.ayrten.scrots.desktop;

import com.ayrten.scrots.screens.AdCallback;
import com.ayrten.scrots.screens.ScrotsGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher implements AdCallback{
	
	private static DesktopLauncher app;
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1200;
		config.height = 600;
		
		app = new DesktopLauncher();
		
		new LwjglApplication(new ScrotsGame(app), config);
	}

	@Override
	public void showAds(boolean show) {
		// TODO Auto-generated method stub
		System.out.println("show my fucking adds");
		
	}
}
