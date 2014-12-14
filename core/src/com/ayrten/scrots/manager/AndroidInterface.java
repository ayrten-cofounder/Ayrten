package com.ayrten.scrots.manager;

public interface AndroidInterface 
{
	public void shouldShowAd(boolean show);
	
	public void showToast(String msg);
	
	public void copyTextToClipboard(String text);
	
	public void makeYesNoWindow(String title, final ButtonInterface yes_interface, final ButtonInterface no_interface);
}

