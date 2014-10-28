package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
// import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class OptionsScreen implements Screen {
	private Stage stage;

	// Actors
	private SelectBox<String> mode;
	private SelectBox<String> bg_color;
	private CheckBox sound_effs;
	private Label back;
	
	private float label_pad_left = (float) 5.5; // Lower # = more left

	private boolean should_add_action;

	public OptionsScreen() {
		stage = new Stage();
		should_add_action = true;

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
		sound_effs
				.getCells()
				.get(0)
				.size(Assets.font_32.getLineHeight() / 2,
						Assets.font_32.getLineHeight() / 2);
		if (Assets.prefs.getBoolean("sound_effs", true) == false)
			sound_effs.setChecked(false);

		bg_color = new SelectBox<String>(Assets.skin);
		bg_color.setItems("White", "Black");
//		bg_color.getStyle().background = Assets.gray_box;
		// bg_color.setBounds(bg_color.getX(), bg_color.getY(),
		// bg_color.getWidth(), bg_color.getHeight());
		// bg_color.setPosition(0, Gdx.graphics.getHeight() -
		// bg_color.getHeight());
		if (!Assets.prefs.getString("bg_color", "").equals(""))
			bg_color.setSelected(Assets.prefs.getString("bg_color"));

		back = new Label("Back", style);
		back.setBounds(back.getX(), back.getY(), back.getWidth(),
				back.getHeight());
		back.setPosition(0, Gdx.graphics.getHeight() - back.getHeight());
		back.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if (((ScrotsGame) Gdx.app.getApplicationListener()).main_menu
						.get_options_screen().sound_effs.isChecked())
					Assets.pop.play();
				event.getListenerActor().setTouchable(Touchable.disabled);
				stage.addAction(Actions.sequence(Actions.alpha(1),
						Actions.fadeOut(0.35f), Actions.run(new Runnable() {

							@Override
							public void run() {
								Assets.game.setScreen(Assets.game.main_menu);
							}
						})));
			}
		});

		table.add(back).left().top();
		table.row();
		table.add("").height(Gdx.graphics.getHeight() / 5 * 2);
		// table.row();
		// table.add(new Label("Game Mode: ", style)).left().padLeft((float)
		// (Gdx.graphics.getWidth()/label_pad_left));
		// table.add(mode).center().padLeft(Gdx.graphics.getWidth()/6).height(style.font.getLineHeight());
		// table.row();
		// table.add("").height(Gdx.graphics.getHeight()/50);
		table.row();
		table.add(new Label("Background: ", style)).left()
				.padLeft((float) (Gdx.graphics.getWidth() / label_pad_left));
		table.add(bg_color).center().padLeft(Gdx.graphics.getWidth() / 6);
		table.row();
		table.add("").height(Gdx.graphics.getHeight() / 50);
		table.row();
		table.add(new Label("Sound Effects: ", style)).left()
				.padLeft((float) (Gdx.graphics.getWidth() / label_pad_left));
		table.add(sound_effs).center().padLeft(Gdx.graphics.getWidth() / 6);

		table.left().top();
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
			setOptsColor(stage, Color.BLACK);
			Gdx.gl.glClearColor(1, 1, 1, 1);
		} else {
			setOptsColor(stage, Color.WHITE);
			Gdx.gl.glClearColor(0, 0, 0, 0);
		}

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();

		Assets.prefs.putBoolean("sound_effs", sound_effs.isChecked());

		if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE) && should_add_action) {
			should_add_action = false;
			stage.addAction(Actions.sequence(Actions.alpha(1),
					Actions.fadeOut(0.5f), Actions.run(new Runnable() {

						@Override
						public void run() {
							((ScrotsGame) Gdx.app.getApplicationListener()).setScreen(((ScrotsGame) Gdx.app
									.getApplicationListener()).main_menu);
						}
					})));
		}
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1f)));
		should_add_action = true;
		back.setTouchable(Touchable.enabled);
	}

	@Override
	public void hide() {
		Assets.prefs.putString("mode", mode.getSelected());
		Assets.prefs.putString("bg_color", bg_color.getSelected());
		Assets.prefs.putBoolean("sound_effs", sound_effs.isChecked());
		Assets.prefs.flush();
	}

	@Override
	public void dispose() {
		Assets.prefs.putString("mode", mode.getSelected());
		Assets.prefs.putString("bg_color", bg_color.getSelected());
		Assets.prefs.putBoolean("sound_effs", sound_effs.isChecked());
		stage.dispose();
	}

	public void setOptsColor(Stage stage, Color color) {
		for (Actor actor : stage.getActors()) {
			if (actor.toString().equals("Table")) {
				for (Actor table_actor : ((Table) actor).getChildren()) {
					if (table_actor.toString().equals("Label"))
						table_actor.setColor(color);
				}
			}
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}
}
