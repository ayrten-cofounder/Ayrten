package com.ayrten.scrots.android;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import android.os.Bundle;


public class AndroidLauncher extends AdLauncher {
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		initialize(new ScrotsGame(this), config);
	}
}
