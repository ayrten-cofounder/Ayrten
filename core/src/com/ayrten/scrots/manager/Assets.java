package com.ayrten.scrots.manager;

import com.ayrten.scrots.screens.ScrotsGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets 
{
	// Fonts
    public static BitmapFont font_120;
    public static BitmapFont font_64;
    public static BitmapFont font_32;
    public static BitmapFont font_16;
    
    public static ScrotsGame game;
    
    // Sounds
    public static Sound pop;
    public static Sound level_clear;
    
    // Music
    public static Music bg;
    
    // Skin
    public static Skin skin;
    
    // Textures
	public static Texture redDot;
	public static Texture blueDot;
	public static Texture greenDot;
	public static Texture babyBlueDot;
    
    // Miscellaneous
    public static Preferences prefs;
    
	public static void load(ScrotsGame sg)
	{
		game = sg;
		
		int ref_width = 640;
		float font_ratio = Gdx.graphics.getWidth()/ref_width;
		
		// Fonts
		font_120 = generate_BitMapFont(120, font_ratio);
		font_64  = generate_BitMapFont( 64, font_ratio);
		font_32  = generate_BitMapFont( 32, font_ratio);
		font_16  = generate_BitMapFont( 16, font_ratio);
		
		// Sounds
		pop = Gdx.audio.newSound(Gdx.files.internal("sounds/pop.mp3"));
		level_clear = Gdx.audio.newSound(Gdx.files.internal("sounds/level_complete.wav"));
		
		// Music
		bg = Gdx.audio.newMusic(Gdx.files.internal("bgm/Transition.mp3"));
		
		// Skin
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		// Textures
		redDot = new Texture(Gdx.files.internal("data/red_dot.png"));
		redDot.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		blueDot = new Texture(Gdx.files.internal("data/blue_dot.png"));
		blueDot.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		greenDot = new Texture(Gdx.files.internal("data/green_dot.png"));
		greenDot.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		babyBlueDot = new Texture(Gdx.files.internal("data/baby_blue_dot.png"));
		babyBlueDot.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		// Miscellaneous
		prefs = Gdx.app.getPreferences("com.ayrten.scrots-preferences");
	}
	
	public static void dispose()
	{
		// Fonts
		font_120.dispose();
		font_64.dispose();
		font_32.dispose();
		font_16.dispose();
		
		// Sounds
		pop.dispose();
		
		// Music
		bg.dispose();
		
		// Skin
		skin.dispose();
		
		// Textures
		redDot.dispose();
		blueDot.dispose();
		greenDot.dispose();
		babyBlueDot.dispose();
		
		// Miscellaneous
		// Make the changes persist. (ie. saves an XML file for Windows in /Users/<user>/.prefs/
		prefs.flush();
	}
	
	private static BitmapFont generate_BitMapFont(int fontSize, float font_ratio)
	{
		FileHandle tff_file = Gdx.files.internal("fonts/kenvector_future_thin.ttf");
		FreeTypeFontGenerator font_gen = new FreeTypeFontGenerator(tff_file);
		FreeTypeFontParameter params = new FreeTypeFontParameter();
		PixmapPacker packer = new PixmapPacker(512, 512, Pixmap.Format.RGB888, 2, false);
		int adj_size = (int) (fontSize * font_ratio);
		params.size = adj_size;
		BitmapFont font = font_gen.generateFont(params);
		font_gen.dispose();
		packer.dispose(); 
		return (font);
	}
}