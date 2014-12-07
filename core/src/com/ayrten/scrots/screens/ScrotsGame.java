package com.ayrten.scrots.screens;

// import com.ayrten.scrots.BitmapFontWriter;
import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

// Game is an ApplicationListener just like initial setup of Scrots, but with more functionality.
public class ScrotsGame extends Game 
{
	
    // Going to create the main screens here since we don't want to create them 
    // on the fly, compared to the loading screen.
    // Note: if it takes up too much phone resources, we can always create them on the fly later
    public MainMenuScreen main_menu;
    public AndroidInterface apk_intf;
    
    public ScrotsGame(AndroidInterface apk_intf) {
    	this.apk_intf = apk_intf;
	}
    
	@Override
	public void create() 
	{		
		Assets.load(this);
		
		// Screens
		main_menu = new MainMenuScreen();		
		LoadingScreen loading_screen = new LoadingScreen();
		
		Assets.bg.setLooping(true);
		Assets.bg.play();
		setScreen(loading_screen);
		
		// Catches when the user presses the back button. Has no effects on desktop.
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void dispose() 
	{
		Assets.dispose();
	}
}
