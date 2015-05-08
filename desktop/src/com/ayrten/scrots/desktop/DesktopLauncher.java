package com.ayrten.scrots.desktop;

import java.util.HashMap;

import com.ayrten.scrots.manager.AndroidInterface;
import com.ayrten.scrots.manager.Assets.LeaderboardType;
import com.ayrten.scrots.manager.GPlayManager;
import com.ayrten.scrots.screens.ScrotsGame;
import com.ayrten.scrots.shop.IAP;
import com.ayrten.scrots.shop.IAPInterface;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class DesktopLauncher implements AndroidInterface, IAP {
	private static DesktopLauncher app;

	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1920;
		config.height = 1080;
		config.width = 1280;
		config.height = 720;
		
		app = new DesktopLauncher();
		new LwjglApplication(new ScrotsGame(app, app), config);
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
	public void gplay_signin() {
		// TODO Auto-generated method stub

	}

	@Override
	public void gplay_logout() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean is_gplay_signedin() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void showAchievements() {
		// TODO Auto-generated method stub

	}

	@Override
	public void unlockAchievement(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getPrice(String item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void purchase(String item, IAPInterface callback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void consume(String item, IAPInterface callback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void queryPurchaseItems() {
		// TODO Auto-generated method stub

	}

	@Override
	public float getAppVersion() {
		// TODO Auto-generated method stub
		return 1.3f;
	}

	@Override
	public boolean loadAchievements(HashMap<String, Boolean> map) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setGPlayManager(GPlayManager manager) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setGPlayButton(Label button) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean retrievedItems() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDescription(String item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showLeaderboard(LeaderboardType lb_type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void submitLeaderboardScore(LeaderboardType lb_type, long score) {
	}

	@Override
	public void rateMe() {
		// TODO Auto-generated method stub
		
	}
}
