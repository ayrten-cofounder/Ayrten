package com.ayrten.scrots.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ayrten.scrots.Scrots;
import com.ayrten.scrots.ScrotsGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		// new LwjglApplication(new Scrots(), config);
		new LwjglApplication(new ScrotsGame(), config);
	}
}
