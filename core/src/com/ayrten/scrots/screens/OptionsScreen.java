package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class OptionsScreen extends ScrotsScreen {
	// Actors
	private SelectBox<String> mode;
	private SelectBox<String> bg_color;
	private CheckBox sound_effs;
	private CheckBox color_blind;

	private float label_pad_left = (float) 5.5; // Lower # = more left

	public OptionsScreen(Screen bscreen) {
		super(bscreen, true);

		Table table = new Table();
		table.setFillParent(true);
		table.setSkin(Assets.skin);

		LabelStyle style = new LabelStyle();
		style.font = Assets.font_32;
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

		sound_effs = new CheckBox("", Assets.skin); 
		sound_effs.setChecked(true);
		sound_effs.getCells().get(0).size(Assets.font_32.getLineHeight() / 2,
				Assets.font_32.getLineHeight() / 2);
		if (Assets.prefs.getBoolean("sound_effs", true) == false)
			sound_effs.setChecked(false);

		color_blind = new CheckBox("", Assets.skin);
		color_blind.setChecked(false);
		color_blind.getCells().get(0).size(Assets.font_32.getLineHeight() / 2,
				Assets.font_32.getLineHeight() / 2);
		if(Assets.prefs.getBoolean("color_blind", false) == true)
			color_blind.setChecked(true);

		bg_color = new SelectBox<String>(Assets.skin);
		bg_color.setItems("White", "Black");
		bg_color.getStyle().fontColor = Assets.ORANGE;
		bg_color.getStyle().listStyle.fontColorSelected = Assets.ORANGE;
		bg_color.getStyle().listStyle.fontColorUnselected = Assets.ORANGE;
		if(Assets.prefs.getString("bg_color", "White").equals("Black")) {
			Gdx.gl.glClearColor(0, 0, 0, 0);
			bg_color.setSelected("Black");
		} else {
			Gdx.gl.glClearColor(1, 1, 1, 1);
			bg_color.setSelected("White");
		}


		table.row();
		table.add("").height(Gdx.graphics.getHeight() / 5 * 2);
		// table.row();
		// table.add(new Label("Game Mode: ", style)).left().padLeft((float)
		// (Gdx.graphics.getWidth()/label_pad_left));
		// table.add(mode).center().padLeft(Gdx.graphics.getWidth()/6).height(style.font.getLineHeight());
		// table.row();
		// table.add("").height(Gdx.graphics.getHeight()/50);
		table.row();
		table.add(new Label("Background: ", Assets.style_font_32_orange)).left()
		.padLeft((float) (Gdx.graphics.getWidth() / label_pad_left));
		table.add(bg_color).center().padLeft(Gdx.graphics.getWidth() / 6);
		table.row();
		table.add("").height(Gdx.graphics.getHeight() / 50);
		table.row();
		table.add(new Label("Sound Effects: ", Assets.style_font_32_orange)).left()
		.padLeft((float) (Gdx.graphics.getWidth() / label_pad_left));
		table.add(sound_effs).center().padLeft(Gdx.graphics.getWidth() / 6);
		table.row();
		table.add("").height(Gdx.graphics.getHeight() / 50);
		table.row();
		table.add(new Label("Color Blind Mode: ", Assets.style_font_32_orange)).left()
		.padLeft((float) (Gdx.graphics.getWidth() / label_pad_left));;
		table.add(color_blind).center().padLeft(Gdx.graphics.getWidth() / 6);

		table.left().top();

		setupStage();
		stage.addActor(table);
		// When user initially touch drop-down list, it shows a scroll instead
		// of
		// expanding fully. This expands the list beforehand so when user
		// interacts
		// with drop-down list for first time, it will expand fully.
		// mode.showList();
	}

	@Override
	public void render(float delta) {
		if (bg_color.getSelected().equals("White")) {
			Gdx.gl.glClearColor(1, 1, 1, 1);
		} else {
			Gdx.gl.glClearColor(0, 0, 0, 0);
		}

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();

		Assets.prefs.putBoolean("sound_effs", sound_effs.isChecked());
	}

	public void hide() {
		super.hide();
		Assets.prefs.putString("mode", mode.getSelected());
		Assets.prefs.putString("bg_color", bg_color.getSelected());
		Assets.prefs.putBoolean("sound_effs", sound_effs.isChecked());
		if(Assets.prefs.getBoolean("color_blind", false) != color_blind.isChecked()) {
			Assets.prefs.putBoolean("color_blind", color_blind.isChecked());
			Assets.loadDotTextures();
		} else
			Assets.prefs.putBoolean("color_blind", color_blind.isChecked());
		
		Assets.prefs.flush();
		if(backScreen.getClass() == MainMenuScreen.class)
		  Assets.playMenuBGM();
	}

	@Override
	public void dispose() {
		super.dispose();
		Assets.prefs.putString("mode", mode.getSelected());
		Assets.prefs.putString("bg_color", bg_color.getSelected());
		Assets.prefs.putBoolean("sound_effs", sound_effs.isChecked());
		Assets.prefs.putBoolean("color_blind", color_blind.isChecked());
	}

	public void setActorsColor(Color color) {
		for (Actor actor : stage.getActors()) {
			if (actor.toString().equals("Table")) {
				for (Actor table_actor : ((Table) actor).getChildren()) {
					if (table_actor.toString().equals("Label"))
						table_actor.setColor(color);
				}
			}
		}
	}
}
