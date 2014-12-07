package com.ayrten.scrots.android;

import android.os.Bundle;


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
		super.showAds(show);
	}
}
