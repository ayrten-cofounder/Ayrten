package com.ayrten.scrots.manager;

public interface AndroidInterface {
	public void shouldShowAd(boolean show);

	public void showToast(String msg);

	public void copyTextToClipboard(String text);
	
	public void showLeadershipBoard();
	
	public void showAchievements();

	public void unlockAchievement(String name);
	
	public void gplay_signin();
	
	public void gplay_logout();
	
	public boolean is_gplay_signedin();
	
	public float getAppVersion();
}
