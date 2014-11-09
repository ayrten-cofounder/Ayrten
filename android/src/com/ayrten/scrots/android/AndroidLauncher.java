package com.ayrten.scrots.android;

import android.os.Bundle;

import com.ayrten.scrots.screens.AdCallback;
import com.ayrten.scrots.screens.ScrotsGame;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AdLauncher {
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
//		initialize(new ScrotsGame(this), config);
	}

	@Override
	public void showAds(boolean show) {
		// TODO Auto-generated method stub
		
	}
}
