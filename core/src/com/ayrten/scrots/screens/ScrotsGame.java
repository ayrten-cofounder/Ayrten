package com.ayrten.scrots.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

// Game is an ApplicationListener just like initial setup of Scrots, but with more functionality.
public class ScrotsGame extends Game 
{
	public SpriteBatch batch;
    public BitmapFont font;
    public Preferences prefs;
    
    // Going to create the main screens here since we don't want to create them 
    // on the fly, compared to the loading screen.
    // Note: if it takes up too much phone resources, we can always create them on the fly later
    public MainMenuScreen main_menu;
    
    FreeTypeFontGenerator font_generator;
    
	// Different screens.
    LoadingScreen loading_screen;
    
	@Override
	public void create() 
	{
		// Initialize variables
		batch = new SpriteBatch();
		font = new BitmapFont();
		main_menu = new MainMenuScreen(this);
		
		// Load preferences set by options
		prefs = Gdx.app.getPreferences("My Preferences");
		
		loading_screen = new LoadingScreen(this);
		setScreen(loading_screen);
	}

	@Override
	public void dispose() 
	{
		loading_screen.dispose();
		// Make the changes persist. (ie. saves an XML file for Windows in /Users/<user>/.prefs/
		prefs.flush();
	}

}