package com.ayrten.scrots.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.ayrten.scrots.screens.ScrotsGame;

public class AndroidLauncher extends AdLauncher {
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new ScrotsGame(this), config);
	}
}
