package com.ayrten.scrots.manager;

import com.ayrten.scrots.screens.ScrotsGame;
import com.ayrten.scrots.shop.IAP;
import com.ayrten.scrots.statistics.StatisticsManager;
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
import com.badlogic.gdx.utils.Json;

public class Assets {
	public static final String VERSION = "2.00.01";
	public static final String PREFS_NO_ADS = "noAds";

	// Height and Width
	public static float game_height;
	public static float game_width;
	public static float powerdot_thresh;
	public static int height;
	public static int width;
	public static final float PAD = 20;
	public static final int MAX_POINTS = 999999999;
	public static final String GPLAY_FILE = "gplay_status.txt";

	// If in Game
	public static boolean inGameMusic = false;

	// Power Dot Descriptions
	public static final String magnet_dot_description = "Attracts negative dots for 8 seconds.";
	public static final String invincible_dot_description = "Negative dots won't affect you for 5 seconds.";
	public static final String rainbow_dot_description = "Removes negative dots for 5 seconds.";

	// Shop
	public static PowerDotManager power_dot_manager;
	public static PointsManager points_manager;
	public static GPlayManager gplay_manager;
	public static StatisticsManager stats_manager;

	// Drawables
	public static NinePatchDrawable gray_box;
	public static NinePatchDrawable transparent_box;

	public static NinePatchDrawable rounded_rectangle_dark_gray;
	public static NinePatchDrawable rounded_rectangle_gray;
	public static NinePatchDrawable rounded_rectangle_blue;

	public static NinePatchDrawable rounded_rectangle_border;
	public static NinePatchDrawable rounded_rectangle_border_blue;
	public static NinePatchDrawable rounded_rectangle_border_dark_gray;

	// Fonts
	public static BitmapFont font_220;
	public static BitmapFont font_200;
	public static BitmapFont font_120;
	public static BitmapFont font_64;
	public static BitmapFont font_32;
	public static BitmapFont font_16;
	public static BitmapFont font_0;

	public static BitmapFont font_200_border;

	// Style
	public static LabelStyle style_font_64_black;
	public static LabelStyle style_font_64_light_gray;
	public static LabelStyle style_font_64_blue;
	public static LabelStyle style_font_64_red;
	public static LabelStyle style_font_64_orange;
	public static LabelStyle style_font_64_white;

	public static LabelStyle style_font_32_white;
	public static LabelStyle style_font_32_black;
	public static LabelStyle style_font_32_light_gray;
	public static LabelStyle style_font_32_red;
	public static LabelStyle style_font_32_timer_red;
	public static LabelStyle style_font_32_orange;
	public static LabelStyle style_font_32_blue;

	public static LabelStyle style_font_0_white;

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
	public static Texture bkg;
	public static Texture transparent_background;

	public static Texture up_button;
	public static Texture down_button;

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
	public static Texture invincible_dot_gray;
	public static Texture magnet_dot_gray;
	public static Texture rainbow_dot_gray;

	public static Texture question_mark;

	// Game Screen
	public static Texture slot_switch;
	public static Texture pause_button;
	public static Texture play_button;
	public static Texture lvl_bubble;
	public static Texture time_bubble;
	public static Texture timer_ring;

	public static Image powDot1_image;
	public static Image powDot2_image;
	public static Image powDot3_image;

	// Miscellaneous
	public static Preferences prefs;
	public static ScrotsGame game;
	public static final Color ORANGE = Color.valueOf("ff9f38");

	public static enum LeaderboardType {
		TIME, SURVIVAL
	};

	// Objects
	public static Json json;

	public static void load(ScrotsGame sg) {
		int ref_width = 800;
		float font_ratio = Gdx.graphics.getWidth() / ref_width;

		// Objects
		json = new Json();

		// Fonts
		font_220 = generate_BitMapFont(220, font_ratio);
		font_200 = generate_BitMapFont(200, font_ratio);
		font_120 = generate_BitMapFont(120, font_ratio);
		font_64 = generate_BitMapFont(64, font_ratio);
		font_32 = generate_BitMapFont(32, font_ratio);
		font_16 = generate_BitMapFont(16, font_ratio);
		font_0 = generate_BitMapFont(0, font_ratio);

		font_200_border = generate_OutlineBitMapFont(200, font_ratio);

		// Height and Width
		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();
		game_height = height * 0.85f;
		game_width = width - (height - game_height);
		powerdot_thresh = height - game_height;

		// Managers
		power_dot_manager = new PowerDotManager();
		points_manager = new PointsManager();

		Json json = new Json();
		String text = Assets.readFile(GPLAY_FILE);
		if (text.isEmpty())
			gplay_manager = new GPlayManager();
		else {
			gplay_manager = json.fromJson(GPlayManager.class, text);
		}

		stats_manager = new StatisticsManager();

		// Drawable
		gray_box = new NinePatchDrawable(new NinePatch(new Texture(
				Gdx.files.internal("data/gray_box.9.png"))));
		transparent_box = new NinePatchDrawable(new NinePatch(new Texture(
				Gdx.files.internal("data/transparent_box.9.png"))));

		rounded_rectangle_dark_gray = new NinePatchDrawable(
				new NinePatch(new Texture(Gdx.files
						.internal("data/rounded_rectangle_dark_gray.png")), 50,
						50, 50, 50));

		rounded_rectangle_gray = new NinePatchDrawable(new NinePatch(
				new Texture(Gdx.files.internal("data/rounded_rectangle.png")),
				50, 50, 50, 50));
		rounded_rectangle_blue = new NinePatchDrawable(new NinePatch(
				new Texture(
						Gdx.files.internal("data/rounded_rectangle_blue.png")),
				50, 50, 50, 50));

		rounded_rectangle_border = new NinePatchDrawable(
				new NinePatch(new Texture(Gdx.files
						.internal("data/rounded_rectangle_bordered.png")), 50,
						50, 50, 50));

		rounded_rectangle_border_blue = new NinePatchDrawable(
				new NinePatch(new Texture(Gdx.files
						.internal("data/rounded_rectangle_bordered_blue.png")),
						20, 20, 20, 20));

		rounded_rectangle_border_dark_gray = new NinePatchDrawable(
				new NinePatch(
						new Texture(
								Gdx.files
										.internal("data/rounded_rectangle_bordered_dark_gray.png")),
						20, 20, 20, 20));

		// Style
		style_font_64_black = new LabelStyle();
		style_font_64_black.font = font_64;
		style_font_64_black.fontColor = Color.BLACK;

		style_font_64_light_gray = new LabelStyle();
		style_font_64_light_gray.font = font_64;
		style_font_64_light_gray.fontColor = Color.LIGHT_GRAY;

		style_font_64_blue = new LabelStyle();
		style_font_64_blue.font = font_64;
		style_font_64_blue.fontColor = Color.valueOf("7A80E0");

		style_font_64_orange = new LabelStyle();
		style_font_64_orange.font = font_64;
		style_font_64_orange.fontColor = ORANGE;

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

		style_font_32_light_gray = new LabelStyle();
		style_font_32_light_gray.font = font_32;
		style_font_32_light_gray.fontColor = Color.LIGHT_GRAY;

		style_font_32_red = new LabelStyle();
		style_font_32_red.font = font_32;
		style_font_32_red.fontColor = Color.valueOf("e07a80");

		style_font_32_timer_red = new LabelStyle();
		style_font_32_timer_red.font = font_32;
		style_font_32_timer_red.fontColor = Color.RED;

		style_font_32_orange = new LabelStyle();
		style_font_32_orange.font = font_32;
		style_font_32_orange.fontColor = ORANGE;

		style_font_32_blue = new LabelStyle();
		style_font_32_blue.font = font_32;
		style_font_32_blue.fontColor = Color.valueOf("7A80E0");

		style_font_0_white = new LabelStyle();
		style_font_0_white.font = font_0;
		style_font_0_white.fontColor = Color.WHITE;

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
		menu_bgm_black = Gdx.audio.newMusic(Gdx.files.internal("bgm/bgm2.mp3"));
		menu_bgm_white = Gdx.audio.newMusic(Gdx.files.internal("bgm/bgm3.mp3"));

		// Skin
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		skin_window = new Skin(Gdx.files.internal("data/uiskin2.json"));

		question_mark = new Texture(Gdx.files.internal("data/question_dot.png"));

		// Miscellaneous
		prefs = Gdx.app.getPreferences("com.ayrten.scrots-preferences");
		game = sg;

		bkg = new Texture(Gdx.files.internal("data/bkg.png"));
		bkg.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		transparent_background = new Texture(
				Gdx.files.internal("data/transparent_gray.png"));
		transparent_background.setFilter(TextureFilter.Linear,
				TextureFilter.Linear);

		up_button = new Texture(Gdx.files.internal("data/button_up.png"));
		down_button = new Texture(Gdx.files.internal("data/button_down.png"));

		loadDotTextures();
		explosion_dot = new Texture(
				Gdx.files.internal("data/dots/explosion_dot.png"));
		explosion_dot.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		invincible_dot = new Texture(
				Gdx.files.internal("data/dots/invincible_dot.png"));
		invincible_dot.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		magnet_dot = new Texture(Gdx.files.internal("data/dots/magnet_dot.png"));
		magnet_dot.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		rainbow_dot = new Texture(
				Gdx.files.internal("data/dots/rainbow_dot.png"));
		rainbow_dot.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		invincible_dot_gray = new Texture(
				Gdx.files.internal("data/dots/invincible_dot_gray.png"));
		magnet_dot_gray = new Texture(
				Gdx.files.internal("data/dots/magnet_dot_gray.png"));
		rainbow_dot_gray = new Texture(
				Gdx.files.internal("data/dots/rainbow_dot_gray.png"));

		slot_switch = explosion_dot;
		pause_button = new Texture(
				Gdx.files.internal("data/dots/pause_dot.png"));
		pause_button.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		play_button = new Texture(Gdx.files.internal("data/dots/play_dot.png"));
		play_button.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		timer_ring = new Texture(Gdx.files.internal("data/timer_ring.png"));

		lvl_bubble = new Texture(Gdx.files.internal("data/dots/lvl_bubble.png"));
		time_bubble = new Texture(
				Gdx.files.internal("data/dots/time_bubble.png"));

		powDot1_image = new Image(regDot_1);
		powDot2_image = new Image(regDot_1);
		powDot3_image = new Image(regDot_1);

		question_mark = new Texture(Gdx.files.internal("data/question_dot.png"));

	}

	public static void playGameBGM() {
		inGameMusic = true;
		menu_bgm_black.stop();
		menu_bgm_white.stop();

		if (prefs.getBoolean("bkg_music", true) == false)
			return;
		game_bgm.play();
		game_bgm.setLooping(true);
	}

	public static void playMenuBGM() {
		inGameMusic = false;
		game_bgm.stop();

		if (prefs.getBoolean("bkg_music", true) == false)
			return;

		if (prefs.getString("bg_color", "White").equals("White")) {
			if (!menu_bgm_white.isPlaying()) {
				menu_bgm_white.play();
				menu_bgm_white.setLooping(true);
			}
			if (menu_bgm_black.isPlaying())
				menu_bgm_black.stop();
		} else {
			if (!menu_bgm_black.isPlaying()) {
				menu_bgm_black.play();
				menu_bgm_black.setLooping(true);
			}
			if (menu_bgm_white.isPlaying())
				menu_bgm_white.stop();
		}
	}

	public static void startBKGMusic() {
		if (inGameMusic)
			playGameBGM();
		else
			playMenuBGM();
	}

	public static void pauseBKGMusic() {
		if (game_bgm.isPlaying())
			game_bgm.pause();
		if (menu_bgm_white.isPlaying())
			menu_bgm_white.pause();
		if (menu_bgm_black.isPlaying())
			menu_bgm_black.pause();
	}

	// Function used to switch to color blind version textures dynamically.
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
	}

	public static void dispose() {
		// Fonts
		font_200.dispose();
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
		// Windows: /Users/<user>/.prefs/
		// OS X: ~/.prefs/My Preferences
		prefs.flush();
		gplay_manager.dispose();
	}

	public static void removeAds() {
		if (null == prefs) {
			prefs = Gdx.app.getPreferences("com.ayrten.scrots-preferences");
		}

		prefs.putBoolean(IAP.REMOVE_ADS, true);
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

	private static BitmapFont generate_OutlineBitMapFont(int fontSize,
			float font_ratio) {
		// FileHandle tff_file = Gdx.files
		// .internal("fonts/kenvector_future_thin.ttf");
		// FileHandle tff_file = Gdx.files.internal("fonts/code_bold.otf");
		FileHandle tff_file = Gdx.files.internal("fonts/summer_of_love.ttf");
		FreeTypeFontGenerator font_gen = new FreeTypeFontGenerator(tff_file);
		FreeTypeFontParameter params = new FreeTypeFontParameter();
		int adj_size = (int) (fontSize * font_ratio) + 30;
		params.size = adj_size;
		BitmapFont font = font_gen.generateFont(params);
		font_gen.dispose();
		return (font);
	}

	public static void writeFile(String fileName, String s) {
		FileHandle file = Gdx.files.local(fileName);
		file.writeString(com.badlogic.gdx.utils.Base64Coder.encodeString(s),
				false);
	}

	public static String readFile(String fileName) {
		FileHandle file = Gdx.files.local(fileName);
		if (file != null && file.exists()) {
			String s = file.readString();
			if (!s.isEmpty()) {
				return com.badlogic.gdx.utils.Base64Coder.decodeString(s);
			}
		}
		return "";
	}
}
