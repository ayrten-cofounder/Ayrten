package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class OptionsScreen extends ScrotsScreen {
	// Actors
	protected SelectBox<String> mode;
	protected SelectBox<String> bg_color;
	protected CheckBox sound_effs;
	protected CheckBox auto_gplay_signin;
//	protected CheckBox color_blind;
	protected CheckBox bkg_music;
	protected TextField default_winner;

	protected Table non_game_options;

	public OptionsScreen(Screen bscreen) {
		super(bscreen, true);

		setupStage();
		showTableScreen();

		mode = new SelectBox<String>(Assets.skin);
		mode.getStyle().font = Assets.font_32;
		mode.getList().getStyle().font = Assets.font_32;
		mode.setItems("Normal", "Challenge");
		if (!Assets.prefs.getString("mode", "").equals(""))
			mode.setSelected(Assets.prefs.getString("mode"));
		// Set the font size of the current shown item.
		mode.getStyle().font = Assets.font_32;
		// Set the font size of all the items in the list.
		mode.getList().getStyle().font = Assets.font_32;

		bkg_music = new CheckBox("", Assets.skin);
		bkg_music.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (Assets.prefs.getBoolean("sound_effs"))
					Assets.button_pop.play();
				Assets.prefs.putBoolean("bkg_music", bkg_music.isChecked());
				Assets.prefs.flush();
				if (bkg_music.isChecked()) {
					Assets.prefs.putBoolean("bkg_music", true);
					Assets.startBKGMusic();
				} else {
					Assets.prefs.putBoolean("bkg_music", false);
					Assets.pauseBKGMusic();
				}
			}
		});
		bkg_music.setChecked(true);
		bkg_music
				.getCells()
				.get(0)
				.size(Assets.font_32.getLineHeight() / 2,
						Assets.font_32.getLineHeight() / 2);
		if (Assets.prefs.getBoolean("bkg_music", true) == false)
			bkg_music.setChecked(false);

		sound_effs = new CheckBox("", Assets.skin);
		sound_effs.setChecked(true);
		sound_effs
				.getCells()
				.get(0)
				.size(Assets.font_32.getLineHeight() / 2,
						Assets.font_32.getLineHeight() / 2);
		sound_effs.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.prefs.putBoolean("sound_effs", sound_effs.isChecked());
				Assets.prefs.flush();
				if (sound_effs.isChecked())
					Assets.button_pop.play();
			}
		});
		if (Assets.prefs.getBoolean("sound_effs", true) == false)
			sound_effs.setChecked(false);

		auto_gplay_signin = new CheckBox("", Assets.skin);
		auto_gplay_signin.setChecked(true);
		auto_gplay_signin
				.getCells()
				.get(0)
				.size(Assets.font_32.getLineHeight() / 2,
						Assets.font_32.getLineHeight() / 2);
		if (Assets.prefs.getBoolean("auto_gplay_signin", true) == false)
			auto_gplay_signin.setChecked(false);
		auto_gplay_signin.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (Assets.prefs.getBoolean("sound_effs"))
					Assets.button_pop.play();
			}
		});

//		color_blind = new CheckBox("", Assets.skin);
//		color_blind.setChecked(false);
//		color_blind
//				.getCells()
//				.get(0)
//				.size(Assets.font_32.getLineHeight() / 2,
//						Assets.font_32.getLineHeight() / 2);
//		if (Assets.prefs.getBoolean("color_blind", false) == true)
//			color_blind.setChecked(true);
//		color_blind.addListener(new ClickListener() {
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				if (Assets.prefs.getBoolean("sound_effs"))
//					Assets.button_pop.play();
//			}
//		});

		bg_color = new SelectBox<String>(Assets.skin);
		bg_color.setItems("White", "Black");
		bg_color.getStyle().fontColor = Color.WHITE;
		bg_color.getStyle().listStyle.fontColorSelected = Color.WHITE;
		bg_color.getStyle().listStyle.fontColorUnselected = Color.WHITE;
		if (Assets.prefs.getString("bg_color", "White").equals("Black")) {
			Gdx.gl.glClearColor(0, 0, 0, 0);
			bg_color.setSelected("Black");
		} else {
			Gdx.gl.glClearColor(1, 1, 1, 1);
			bg_color.setSelected("White");
		}
		
		default_winner = new TextField(Assets.prefs.getString("default_winner", ""), Assets.skin);
		default_winner.setMaxLength(10);
		default_winner.setSize(default_winner.getStyle().font.getLineHeight(), default_winner.getMaxLength());
		default_winner.setAlignment(Align.center);
		default_winner.setBounds(default_winner.getX(), default_winner.getY(), default_winner.getWidth(), default_winner.getHeight());
		default_winner.addListener(new InputListener(){
			@Override
			public boolean keyUp(InputEvent event, int keycode) {
				if(keycode == '\r' || keycode == '\n' || keycode == Keys.ENTER)
					Assets.prefs.putString("default_winner", ((TextField)event.getTarget()).getText());

				return super.keyUp(event, keycode);
			}
		});
		
		float CHECKBOX_SIZE = Assets.font_32.getLineHeight();
		// Set size of checkboxes
		bkg_music.getCells().get(0).size(CHECKBOX_SIZE, CHECKBOX_SIZE);
		bkg_music.setHeight(CHECKBOX_SIZE);
		
		sound_effs.getCells().get(0).size(CHECKBOX_SIZE, CHECKBOX_SIZE);
		sound_effs.setHeight(CHECKBOX_SIZE);
		
		auto_gplay_signin.getCells().get(0).size(CHECKBOX_SIZE, CHECKBOX_SIZE);
		auto_gplay_signin.setHeight(CHECKBOX_SIZE);
		
//		color_blind.getCells().get(0).size(CHECKBOX_SIZE, CHECKBOX_SIZE);
//		color_blind.setHeight(CHECKBOX_SIZE);
		
		non_game_options = new Table(Assets.skin);
		non_game_options.setWidth(table.getWidth());

		Table game_options = new Table(Assets.skin);
		game_options.setWidth(table.getWidth());
		float width = table.getWidth() / 3;
		
		Table temp = new Table(Assets.skin);
		temp.add(bg_color);

		// When adding options, keep non-game options at the bottom.
		game_options
				.add(new Label("Background: ", Assets.style_font_32_white))
				.width(width).left();
		game_options.add(temp).width(width).right();
		game_options.row();
		game_options.add("").height(Gdx.graphics.getHeight() / 50);
		game_options.row();
		game_options
				.add(new Label("Sound Effects: ", Assets.style_font_32_white))
				.width(width).left();
		game_options.add(sound_effs).width(width).right();
		game_options.row();
		game_options.add().height(Gdx.graphics.getHeight() / 50);
		game_options.row();
		game_options
				.add(new Label("Background Music: ", Assets.style_font_32_white))
				.width(width).left();
		game_options.add(bkg_music).width(width).right();
		game_options.row();
		game_options.add(new Label("Default Winner: ", Assets.style_font_32_white)).width(width).left();
		game_options.add(default_winner).width(width).height(default_winner.getStyle().font.getLineHeight()).left();


		non_game_options
				.add(new Label("Auto Google Signin: ",
						Assets.style_font_32_white)).width(width).left();
		non_game_options.add(auto_gplay_signin).width(width).right();
//		game_options.row();
//		game_options
//				.add(new Label("Color Blind Mode: ", Assets.style_font_32_white))
//				.width(width).left();
//		game_options.add(color_blind).width(width).right();

		table.add(game_options).left();
		table.row();
		table.add("").height(Gdx.graphics.getHeight() / 50);
		table.row();
		table.add(non_game_options).left();
	}

	public void enableNonGameOptions(boolean enable) {
		non_game_options.setVisible(enable);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		
		if (bg_color.getSelected().equals("White")) {
			Gdx.gl.glClearColor(1, 1, 1, 1);
		} else {
			Gdx.gl.glClearColor(0, 0, 0, 0);
		}
	}

	public void hide() {
		super.hide();
		Assets.prefs.putString("mode", mode.getSelected());
		Assets.prefs.putString("bg_color", bg_color.getSelected());
		Assets.prefs.putBoolean("sound_effs", sound_effs.isChecked());
//		if (Assets.prefs.getBoolean("color_blind", false) != color_blind
//				.isChecked()) {
//			Assets.prefs.putBoolean("color_blind", color_blind.isChecked());
//			Assets.loadDotTextures();
//		} else
//			Assets.prefs.putBoolean("color_blind", color_blind.isChecked());
		Assets.prefs.putBoolean("auto_gplay_signin", auto_gplay_signin.isChecked());
		Assets.prefs.flush();
		if (backScreen.getClass() == MainMenuScreen.class)
			Assets.playMenuBGM();
		enableNonGameOptions(true);
	}

	@Override
	public void dispose() {
		super.dispose();
		Assets.prefs.putString("mode", mode.getSelected());
		Assets.prefs.putString("bg_color", bg_color.getSelected());
		Assets.prefs.putBoolean("sound_effs", sound_effs.isChecked());
//		Assets.prefs.putBoolean("color_blind", color_blind.isChecked());
		Assets.prefs.putBoolean("auto_gplay_signin",
				auto_gplay_signin.isChecked());
	}
}
