package com.ayrten.scrots.manager;

import com.ayrten.scrots.screens.ScrotsGame;
import com.ayrten.scrots.shop.PointsManager;
import com.ayrten.scrots.shop.PowerDotManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class Assets {
	// Height and Width
	public static float game_height;
	public static int height;
	public static int width;
	public static final float TIME_ADD = (float) 2.6; // seconds
	public static final float TIME_OFF = (float) -5.0;
	
	// Shop
	public static PowerDotManager power_dot_manager;
	public static PointsManager points_manager;

	// Drawables
	public static NinePatchDrawable gray_box;
	public static NinePatchDrawable transparent_box;

	// Fonts
	public static BitmapFont font_120;
	public static BitmapFont font_64;
	public static BitmapFont font_32;
	public static BitmapFont font_16;

	// Style
	public static LabelStyle style_font_64_black;
	public static LabelStyle style_font_64_blue;
	public static LabelStyle style_font_64_red;
	public static LabelStyle style_font_64_orange;
	public static LabelStyle style_font_64_white;
	
	public static LabelStyle style_font_32_white;
	public static LabelStyle style_font_32_black;
	public static LabelStyle style_font_32_red;
	public static LabelStyle style_font_32_orange;
	public static LabelStyle style_font_32_blue;

	// Sounds
	public static Sound button_pop;
	public static Sound level_clear;

	// Regular dots
	public static Sound reg_pop_1;
	public static Sound reg_pop_2;

	// Penalty dots
	public static Sound pen_pop_1;
	public static Sound pen_pop_2;

	// Music
	public static Music menu_bgm_black;
	public static Music menu_bgm_white;
	public static Music game_bgm;

	// Skin
	public static Skin skin;
	public static Skin skin_window;

	// Textures
	public static Texture regDot_1; // Regular dot (ie. green)
	public static Texture regDot_2; // Regular dot (ie. babay blue)
	public static Texture regDot_3; // Exploding dot

	public static Texture penDot_1; // Penalty dot (ie. red)
	public static Texture penDot_2; // Penalty dot (ie. blue)

	public static Texture dwdPenDot_1;
	public static Texture dwdPenDot_2;
	public static Texture dwdRegDot_1;
	public static Texture dwdRegDot_2;

	public static Texture powDot_1;
	public static Texture powDot_2;

	public static Texture explosion_dot;

	public static Texture invincible_dot;
	public static Texture magnet_dot;
	public static Texture rainbow_dot;

	public static Texture question_mark;

	public static Texture slot_switch;
	public static Texture pause_dot;

	public static Image powDot1_image;
	public static Image powDot2_image;
	public static Image powDot3_image;

	// Miscellaneous
	public static Preferences prefs;
	public static ScrotsGame game;
	public static final Color ORANGE = Color.valueOf("ff9f38");

	public static void load(ScrotsGame sg) {
		int ref_width = 800;
		float font_ratio = Gdx.graphics.getWidth() / ref_width;

		// Fonts
		font_120 = generate_BitMapFont(120, font_ratio);
		font_64 = generate_BitMapFont(64, font_ratio);
		font_32 = generate_BitMapFont(32, font_ratio);
		font_16 = generate_BitMapFont(16, font_ratio);

		// Height and Width
		// game_height = Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 5);
		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();
		game_height = height - font_32.getLineHeight() * 2;
		
		// Shop
		power_dot_manager = new PowerDotManager();
		points_manager = new PointsManager();

		// Drawable
		gray_box = new NinePatchDrawable(new NinePatch(new Texture(
				Gdx.files.internal("data/gray_box.9.png"))));
		transparent_box = new NinePatchDrawable(new NinePatch(new Texture(
				Gdx.files.internal("data/transparent_box.9.png"))));


		// Style
		style_font_64_black = new LabelStyle();
		style_font_64_black.font = font_64;
		style_font_64_black.fontColor = Color.BLACK;

		style_font_64_blue = new LabelStyle();
		style_font_64_blue.font = font_64;
		style_font_64_blue.fontColor = Color.valueOf("7A80E0");

		style_font_64_orange = new LabelStyle();
		style_font_64_orange.font = font_64;
		style_font_64_orange.fontColor = Color.valueOf("ffcd55");

		style_font_64_red = new LabelStyle();
		style_font_64_red.font = font_64;
		style_font_64_red.fontColor = Color.valueOf("e07a80");

		style_font_64_white = new LabelStyle();
		style_font_64_white.font = font_64;
		style_font_64_white.fontColor = Color.WHITE;
		
		style_font_32_white = new LabelStyle();
		style_font_32_white.font = font_32;
		style_font_32_white.fontColor = Color.WHITE;
		
		style_font_32_black = new LabelStyle();
		style_font_32_black.font = font_32;
		style_font_32_black.fontColor = Color.BLACK;

		style_font_32_red = new LabelStyle();
		style_font_32_red.font = font_32;
		style_font_32_red.fontColor = Color.valueOf("e07a80");

		style_font_32_orange = new LabelStyle();
		style_font_32_orange.font = font_32;
		style_font_32_orange.fontColor = ORANGE;

		style_font_32_blue = new LabelStyle();
		style_font_32_blue.font = font_32;
		style_font_32_blue.fontColor = Color.valueOf("7A80E0");

		// Sounds
		button_pop = Gdx.audio.newSound(Gdx.files
				.internal("sounds/button_pop.mp3"));
		reg_pop_1 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/dot_pop1.mp3"));
		pen_pop_2 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/dot_pop2.wav"));
		reg_pop_2 = Gdx.audio.newSound(Gdx.files
				.internal("sounds/dot_pop3.wav"));
		level_clear = Gdx.audio.newSound(Gdx.files
				.internal("sounds/level_clear.wav"));

		// Music
		game_bgm = Gdx.audio.newMusic(Gdx.files.internal("bgm/bgm1.mp3"));
		game_bgm.setLooping(true);
		menu_bgm_black = Gdx.audio.newMusic(Gdx.files.internal("bgm/bgm2.mp3"));
		menu_bgm_black.setLooping(true);
		menu_bgm_white = Gdx.audio.newMusic(Gdx.files.internal("bgm/bgm3.wav"));
		menu_bgm_white.setLooping(true);

		game_bgm.setLooping(true);
		menu_bgm_black.setLooping(true);
		menu_bgm_white.setLooping(true);

		// Skin
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		skin_window = new Skin(Gdx.files.internal("data/uiskin2.json"));

		question_mark = new Texture(Gdx.files.internal("data/question_dot.png"));

		// Miscellaneous
		prefs = Gdx.app.getPreferences("com.ayrten.scrots-preferences");
		game = sg;

		loadDotTextures();
		slot_switch = explosion_dot;

		powDot1_image = new Image(regDot_1);
		powDot2_image = new Image(regDot_1);
		powDot3_image = new Image(regDot_1);

		question_mark = new Texture(Gdx.files.internal("data/question_dot.png"));
	}

	public static void playGameBGM() {
		if (menu_bgm_black.isPlaying())
			menu_bgm_black.stop();
		else
			menu_bgm_white.stop();
		game_bgm.play();
	}

	public static void playMenuBGM() {
		if (game_bgm.isPlaying())
			game_bgm.stop();
		if (prefs.getString("bg_color", "White").equals("White")) {
			if (!menu_bgm_white.isPlaying())
				menu_bgm_white.play();
			if (menu_bgm_black.isPlaying())
				menu_bgm_black.stop();
		} else {
			if (!menu_bgm_black.isPlaying())
				menu_bgm_black.play();
			if (menu_bgm_white.isPlaying())
				menu_bgm_white.stop();
		}
	}

	public static void loadDotTextures() {
		if (!prefs.getBoolean("color_blind", false))
			penDot_1 = new Texture(Gdx.files.internal("data/red_dot.png"));
		else
			penDot_1 = new Texture(
					Gdx.files.internal("data/dots/red_dot_cb.png"));
		penDot_1.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		if (!prefs.getBoolean("color_blind", false))
			penDot_2 = new Texture(
					Gdx.files.internal("data/dots/purple_dot.png"));
		else
			penDot_2 = new Texture(
					Gdx.files.internal("data/dots/purple_dot_cb.png"));

		penDot_2.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		if (!prefs.getBoolean("color_blind", false))
			regDot_1 = new Texture(Gdx.files.internal("data/green_dot.png"));
		else
			regDot_1 = new Texture(
					Gdx.files.internal("data/dots/green_dot_cb.png"));
		regDot_1.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		if (!prefs.getBoolean("color_blind", false))
			regDot_2 = new Texture(Gdx.files.internal("data/baby_blue_dot.png"));
		else
			regDot_2 = new Texture(
					Gdx.files.internal("data/dots/baby_blue_dot_cb.png"));
		regDot_2.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		regDot_3 = new Texture(Gdx.files.internal("data/dots/brown_dot.png"));
		regDot_3.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		dwdPenDot_1 = new Texture(Gdx.files.internal("data/dots/brown_dot.png"));
		dwdPenDot_1.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		dwdPenDot_2 = new Texture(Gdx.files.internal("data/dots/pink_dot.png"));
		dwdPenDot_2.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		dwdRegDot_1 = new Texture(
				Gdx.files.internal("data/dots/yellow_dot.png"));
		dwdRegDot_1.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		dwdRegDot_2 = new Texture(
				Gdx.files.internal("data/dots/orange_dot.png"));
		dwdRegDot_2.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		explosion_dot = new Texture(
				Gdx.files.internal("data/dots/explosion_dot.png"));
		explosion_dot.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		invincible_dot = new Texture(
				Gdx.files.internal("data/dots/invincible_dot.png"));
		invincible_dot.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		magnet_dot = new Texture(
				Gdx.files.internal("data/dots/magnet_dot.png"));
		magnet_dot.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		rainbow_dot = new Texture(
				Gdx.files.internal("data/dots/rainbow_dot.png"));
		rainbow_dot.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		pause_dot = new Texture(
				Gdx.files.internal("data/dots/pause_dot.png"));
		pause_dot.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}

	public static void dispose() {
		// Fonts
		font_120.dispose();
		font_64.dispose();
		font_32.dispose();
		font_16.dispose();

		// Sounds
		button_pop.dispose();
		reg_pop_1.dispose();
		// red_pop.dispose();
		pen_pop_2.dispose();
		reg_pop_2.dispose();

		// Music
		menu_bgm_black.dispose();
		menu_bgm_white.dispose();
		game_bgm.dispose();

		// Skin
		skin.dispose();

		// Textures
		penDot_1.dispose();
		penDot_2.dispose();
		regDot_1.dispose();
		regDot_2.dispose();
		question_mark.dispose();

		// Miscellaneous
		// Make the changes persist. (ie. saves an XML file for Windows in
		// /Users/<user>/.prefs/
		prefs.flush();
	}

	private static BitmapFont generate_BitMapFont(int fontSize, float font_ratio) {
		// FileHandle tff_file = Gdx.files
		// .internal("fonts/kenvector_future_thin.ttf");
		// FileHandle tff_file = Gdx.files.internal("fonts/code_bold.otf");
		FileHandle tff_file = Gdx.files.internal("fonts/summer_of_love.ttf");
		FreeTypeFontGenerator font_gen = new FreeTypeFontGenerator(tff_file);
		FreeTypeFontParameter params = new FreeTypeFontParameter();
		int adj_size = (int) (fontSize * font_ratio);
		params.size = adj_size;
		BitmapFont font = font_gen.generateFont(params);
		font_gen.dispose();
		return (font);
	}
}
