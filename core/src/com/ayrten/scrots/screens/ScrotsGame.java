package com.ayrten.scrots.screens;

// import com.ayrten.scrots.BitmapFontWriter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

// Game is an ApplicationListener just like initial setup of Scrots, but with more functionality.
public class ScrotsGame extends Game 
{
	public Preferences prefs;
    public Sound pop;
    public Skin skin;
    public Music bg;
    
    // Fonts
    public BitmapFont font_120;
    public BitmapFont font_64;
    public BitmapFont font_32;
    public BitmapFont font_16;
    
    // Going to create the main screens here since we don't want to create them 
    // on the fly, compared to the loading screen.
    // Note: if it takes up too much phone resources, we can always create them on the fly later
    public MainMenuScreen main_menu;
    
    @Override
	public void create() 
	{
		
		// Initialize variables
		prefs = Gdx.app.getPreferences("com.ayrten.scrots-preferences");
		pop = Gdx.audio.newSound(Gdx.files.internal("sounds/pop.mp3"));
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		bg = Gdx.audio.newMusic(Gdx.files.internal("bgm/Cycles.mp3"));
		
		int displayWidth  = prefs.getInteger("display-width", 0);
		int displayHeight = prefs.getInteger("display-height", 0);
		boolean loaded = false;
		// Generate font if the screen size changed.
		if(displayWidth != Gdx.graphics.getWidth() || displayHeight != Gdx.graphics.getHeight())
		{
			
		}
		else
		{
			// Load previously generate fonts.
			// loaded = true;
			// font_120
		}
		
		if(!loaded)
		{
			int ref_width = 640;
			float ratio = Gdx.graphics.getWidth()/ref_width;
			
			font_120 = generate_BitMapFont(120, ratio);
			font_64  = generate_BitMapFont( 64, ratio);
			font_32  = generate_BitMapFont( 32, ratio);
			font_16  = generate_BitMapFont( 16, ratio);
			
			prefs.putInteger("display-width",  Gdx.graphics.getWidth());
			prefs.putInteger("display-height", Gdx.graphics.getHeight());
		}
		
		// Screens
		main_menu = new MainMenuScreen(this);		
		LoadingScreen loading_screen = new LoadingScreen(this);
		
		bg.setLooping(true);
		bg.play();
		setScreen(loading_screen);
		
		// Catches when the user presses the back button. Has no effects on desktop.
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void dispose() 
	{
		// Make the changes persist. (ie. saves an XML file for Windows in /Users/<user>/.prefs/
		prefs.flush();
		pop.dispose();
		skin.dispose();
		bg.dispose();
		font_64.dispose();
		font_32.dispose();
	}
	
	public BitmapFont generate_BitMapFont(int fontSize, float ratio)
	{
		FileHandle tff_file = Gdx.files.internal("fonts/kenvector_future_thin.ttf");
		FreeTypeFontGenerator font_gen = new FreeTypeFontGenerator(tff_file);
		FreeTypeFontParameter params = new FreeTypeFontParameter();
		PixmapPacker packer = new PixmapPacker(512, 512, Pixmap.Format.RGB888, 2, false);
		int adj_size = (int) (fontSize * ratio);
		params.size = adj_size;
		BitmapFont font = font_gen.generateFont(params);
		// Can't get it to work... commenting out for now
		// saveFontToFile(font, fontSize, tff_file.nameWithoutExtension(), packer);
		font_gen.dispose();
		packer.dispose(); 
		return (font);
	}
	
	/*
	private void saveFontToFile(BitmapFont font, int fontSize, String fontName, PixmapPacker packer) {
		// Cannot use "internal" because it's read only.
		FileHandle fontFile = Gdx.files.local(fontName + fontSize + ".fnt"); // .fnt path
		FileHandle pixmapDir = Gdx.files.local("generated-fonts" + fontName + font + fontSize); // png dir path
		BitmapFontWriter.setOutputFormat(BitmapFontWriter.OutputFormat.Text);

		String[] pageRefs = BitmapFontWriter.writePixmaps(packer.getPages(), pixmapDir, fontName);
		// Gdx.app.debug(TAG, String.format("Saving font [%s]: fontfile: %s, pixmapDir: %s\n", fontName, fontFile, pixmapDir));
		System.out.println(String.format("Saving font [%s]: fontfile: %s, pixmapDir: %s\n", fontName, fontFile, pixmapDir));
		for (int i = 0; i < pageRefs.length; i++) {
			pageRefs[i] = fontName + "/" + pageRefs[i];
		}
		BitmapFontWriter.writeFont(font.getData(), pageRefs, fontFile, new BitmapFontWriter.FontInfo(fontName, fontSize), 1, 1);
	}
	*/
	

}