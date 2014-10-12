package com.ayrten.scrots.desktop;

import com.ayrten.scrots.screens.ScrotsGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 900;
		config.height = 600;
		System.out.println("WIDTH: " + config.width);
		System.out.println("HEIGHT: " + config.height);
		new LwjglApplication(new ScrotsGame(), config);
	}
}
