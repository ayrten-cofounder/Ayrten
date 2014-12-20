package com.ayrten.scrots.manager;

public interface AndroidInterface {
	public void shouldShowAd(boolean show);

	public void showToast(String msg);

	public void copyTextToClipboard(String text);

	public void makeYesNoWindow(String title, ButtonInterface yes_interface,
			ButtonInterface no_interface);

	public void makeWindow(String title, String yes_button, String no_button,
			ButtonInterface yes_interface, ButtonInterface no_interface);
}
