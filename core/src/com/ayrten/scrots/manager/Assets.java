package com.ayrten.scrots.manager;

import com.ayrten.scrots.screens.ScrotsGame;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class Assets {
	// Height and Width
	public static int game_height;
	public static int height;
	public static int width;

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
	public static LabelStyle style_font_32_red;
	public static LabelStyle style_font_32_orange;
	public static LabelStyle style_font_32_blue;

	// Sounds
	public static Sound button_pop;
	public static Sound grn_pop;
	public static Sound blue_pop;
	public static Sound red_pop;
	public static Sound baby_blue_pop;
	public static Sound level_clear;

	// Music
	public static Music menu_bgm_black;
	public static Music menu_bgm_white;
	public static Music game_bgm;

	// Skin
	public static Skin skin;
	public static Skin skin_window;

	// Textures
	public static Texture redDot;
	public static Texture blueDot;
	public static Texture greenDot;
	public static Texture babyBlueDot;
	public static Texture question_mark;

	// Miscellaneous
	public static Preferences prefs;
	public static ScrotsGame game;
	public static final Color ORANGE = Color.valueOf("ff9f38");

	public static void load(ScrotsGame sg) {
		int ref_width = 800;
		float font_ratio = Gdx.graphics.getWidth() / ref_width;
		
		// Height and Width
		game_height = Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 5);
		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();

		// Drawable
		gray_box = new NinePatchDrawable(new NinePatch(new Texture(
				Gdx.files.internal("data/gray_box.9.png"))));
		transparent_box = new NinePatchDrawable(new NinePatch(new Texture(
				Gdx.files.internal("data/transparent_box.9.png"))));

		// Fonts
		font_120 = generate_BitMapFont(120, font_ratio);
		font_64 = generate_BitMapFont(64, font_ratio);
		font_32 = generate_BitMapFont(32, font_ratio);
		font_16 = generate_BitMapFont(16, font_ratio);

		// Style
		style_font_64_black = new LabelStyle();
		style_font_64_black.font = font_64;
		style_font_64_black.fontColor = Color.valueOf("1c1c1c");

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
		button_pop = Gdx.audio.newSound(Gdx.files.internal("sounds/button_pop.mp3"));
		grn_pop = Gdx.audio.newSound(Gdx.files.internal("sounds/dot_pop1.mp3"));
		blue_pop = Gdx.audio.newSound(Gdx.files.internal("sounds/dot_pop2.wav"));
		baby_blue_pop = Gdx.audio.newSound(Gdx.files.internal("sounds/dot_pop3.wav"));
		level_clear = Gdx.audio.newSound(Gdx.files.internal("sounds/level_clear.wav"));

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

		// Textures
		redDot = new Texture(Gdx.files.internal("data/red_dot.png"));
		redDot.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		blueDot = new Texture(Gdx.files.internal("data/blue_dot.png"));
		blueDot.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		greenDot = new Texture(Gdx.files.internal("data/green_dot.png"));
		greenDot.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		babyBlueDot = new Texture(Gdx.files.internal("data/baby_blue_dot.png"));
		babyBlueDot.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		question_mark = new Texture(Gdx.files.internal("data/question_dot.png"));

		// Miscellaneous
		prefs = Gdx.app.getPreferences("com.ayrten.scrots-preferences");
		game = sg;
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
			menu_bgm_white.play();
			if (menu_bgm_black.isPlaying())
				menu_bgm_black.stop();
		} else {
			menu_bgm_black.play();
			if (menu_bgm_white.isPlaying())
				menu_bgm_white.stop();
		}
	}

	public static void dispose() {
		// Fonts
		font_120.dispose();
		font_64.dispose();
		font_32.dispose();
		font_16.dispose();

		// Sounds
		button_pop.dispose();
		grn_pop.dispose();
		// red_pop.dispose();
		blue_pop.dispose();
		baby_blue_pop.dispose();

		// Music
		menu_bgm_black.dispose();
		menu_bgm_white.dispose();
		game_bgm.dispose();

		// Skin
		skin.dispose();

		// Textures
		redDot.dispose();
		blueDot.dispose();
		greenDot.dispose();
		babyBlueDot.dispose();
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
