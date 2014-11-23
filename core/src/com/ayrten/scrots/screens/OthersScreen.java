package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class OthersScreen extends ScrotsScreen
{
	protected Table table;
	
	// Labels
	protected Label options;
	protected Label tutorial;
	protected Label contact_us;
	protected Label credits;
	
	// Screens
	protected OptionsScreen 	options_screen;
	protected TutorialScreen 	tutorial_screen;
	protected ContactScreen		contact_screen;
	protected CreditsScreen		credits_screen;
	
	public OthersScreen(Screen bscreen)
	{
		super(bscreen, true);
		
		table = new Table(Assets.skin);
		table.setFillParent(true);
		
		options_screen  = new OptionsScreen(this);
		tutorial_screen = new TutorialScreen(this);
		contact_screen = new ContactScreen(this);
		credits_screen  = new CreditsScreen(this);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = Assets.font_64;
		labelStyle.fontColor = Assets.ORANGE;
		
		options = new Label("Options", labelStyle);
		options.setBounds(options.getX(), options.getY(), options.getWidth(), options.getHeight());
		options.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.game.setScreen(options_screen);
			}
		});
		
		tutorial = new Label("Tutorial", labelStyle);
		tutorial.setBounds(tutorial.getX(), tutorial.getY(), tutorial.getWidth(), tutorial.getHeight());
		tutorial.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.game.setScreen(tutorial_screen);
			}
		});
		
		contact_us = new Label("Contact Us", labelStyle);
		contact_us.setBounds(contact_us.getX(), contact_us.getY(), contact_us.getWidth(), contact_us.getHeight());
		contact_us.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.game.setScreen(contact_screen);
			}
		});
		
		credits = new Label("Credits", labelStyle);
		credits.setBounds(credits.getX(), credits.getY(), credits.getWidth(), credits.getHeight());
		credits.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.game.setScreen(credits_screen);
			}
		});
		
		table.add(options);
		table.row();
		table.add(tutorial);
		table.row();
		table.add(contact_us);
		table.row();
		table.add(credits);
		
		setupStage();
		stage.addActor(table);
	}
}
