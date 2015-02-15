package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.ButtonInterface;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class MessageScreen 
{
	private Stage stage;
	
	private Image background_overlay;
	private Label message;
	private Label negative_button;
	private Label positive_button;
	
	public MessageScreen(Stage stage)
	{
		this.stage = stage;
		
		background_overlay = new Image(Assets.transparent_background);
		background_overlay.setBounds(0, 0, Assets.width, Assets.height);
	}
	
	public void makeYesNoWindow(final String title,
			final ButtonInterface yes_interface,
			final ButtonInterface no_interface, final int color)
	{
		
	}
}
