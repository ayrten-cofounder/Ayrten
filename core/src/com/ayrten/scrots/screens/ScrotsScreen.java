package com.ayrten.scrots.screens;

import java.util.ArrayList;
import java.util.LinkedList;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ScrotsScreen implements Screen {

	protected Stage stage;
	protected Screen backScreen;
	protected ArrayList<Actor> actors;

	// Widgets
	protected Label back;

	// Nav Bar
	// Navagation bar should probably be a different class...maybe
	protected Table navigation_bar;
	protected LinkedList<Actor> navBarItems = new LinkedList<Actor>();

	// Table Screen
	// The table for the screen
	protected Table table;

	// Background
	protected Table bkg;

	protected boolean createBack;
	protected boolean backStage;

	public ScrotsScreen() {
	}

	public ScrotsScreen(Screen bscreen, boolean createBack) {
		backScreen = bscreen;
		actors = new ArrayList<Actor>();
		this.createBack = createBack;
		backStage = true;
		stage = new Stage() {
			public boolean keyDown(int keyCode) {
				if (keyCode == Keys.BACK && backStage && backScreen != null) {
					backStage = false;
					if (Assets.prefs.getBoolean("sound_effs"))
						Assets.button_pop.play();
					stage.addAction(Actions.parallel(
							Actions.run(new Runnable() {
								public void run() {
									setActorsTouchable(Touchable.disabled);
								}
							}),
							Actions.sequence(Actions.alpha(1),
									Actions.fadeOut(0.35f),
									Actions.run(new Runnable() {
										public void run() {
											Assets.game.setScreen(backScreen);
										}
									}))));
				}
				return super.keyDown(keyCode);
			}
		};
	}

	public void createBackLabelAndInitNavBar() {
		// Init back label
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = Assets.font_64;
		labelStyle.fontColor = Color.WHITE;

		back = new Label("Back", labelStyle);
		back.setBounds(back.getX(), back.getY(), back.getWidth(),
				back.getHeight());
		back.setAlignment(Align.left);
		back.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if (Assets.prefs.getBoolean("sound_effs"))
					Assets.button_pop.play();
				stage.addAction(Actions.parallel(
						Actions.run(new Runnable() {
							public void run() {
								setActorsTouchable(Touchable.disabled);
							}
						}),
						Actions.sequence(Actions.alpha(1),
								Actions.fadeOut(0.35f),
								Actions.run(new Runnable() {
									public void run() {
										Assets.game.setScreen(backScreen);
									}
								}))));
			}
		});

		initializeNaviBar();
		addToNavBar(back);
	}
	
	public void initializeNaviBar() {
		// Init Nav Bar
		navigation_bar = new Table();
		// navagation_bar.setSize(Assets.width - Assets.PAD, back.getHeight());
		// navagation_bar.setPosition(Assets.PAD / 2, Assets.height
		// - navagation_bar.getHeight() - (Assets.PAD / 2));
		
		navigation_bar.setSize(Assets.width, Assets.font_64.getLineHeight());
		navigation_bar.setPosition(Assets.PAD * 2 / 2, Assets.height
				- navigation_bar.getHeight() - (Assets.PAD / 2));

		navigation_bar.setSkin(Assets.skin);
		navigation_bar.setBackground(Assets.rounded_rectangle_blue);
		navigation_bar.align(Align.left);
	}

	public void createTableScreen() {
		table = new Table(Assets.skin);
		table.setSize(Assets.width - (Assets.PAD * 2),
				Assets.height - back.getHeight() - (Assets.PAD * 2));
		table.setPosition(Assets.PAD, Assets.PAD);
		table.setBackground(Assets.rounded_rectangle_border);

		bkg = new Table();
		bkg.setSize(Assets.width, Assets.height);
		bkg.setFillParent(true);
		bkg.setPosition(0, 0);
		bkg.setSkin(Assets.skin);

		bkg.row();
		bkg.add(new Image(Assets.bkg)).width(Assets.width).height(Assets.height);
	}

	// This must be called near the end of the constructor.
	public void setupStage() {
		if (createBack) {
			createBackLabelAndInitNavBar();
			createTableScreen();
			// back.setPosition(0 + back.getWidth() / 5,
			// Gdx.graphics.getHeight()
			// - back.getHeight());
			// stage.addActor(back);
			stage.addActor(bkg);
			stage.addActor(navigation_bar);
		}

		addActors();
		setActorsTouchable(Touchable.disabled);
	}

	// Call this function AFTER setUpStage() is called
	public void showTableScreen() {
		stage.addActor(table);
	}

	public void addToNavBar(Actor actor) {
		navigation_bar.clear();
		navigation_bar.row();

		navBarItems.add(actor);
		int width = (int) ((navigation_bar.getWidth() - Assets.PAD) / navBarItems.size());
		for (Actor a : navBarItems) {
			navigation_bar.add(a).width(width);
		}
	}

	public void addActors() {
		if (back != null && navigation_bar != null && table != null) {
			actors.add(navigation_bar);
			actors.add(back);
			actors.add(table);
		}
	}

	public void setBackScreen(Screen bscreen) {
		backScreen = bscreen;
	}

	public void setActorsTouchable(Touchable touchable) {
		for (Actor actor : actors) {
			if (actor != null)
				actor.setTouchable(touchable);
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1f),
				Actions.run(new Runnable() {
					public void run() {
						otherShowOptions();
						backStage = true;
						setActorsTouchable(Touchable.enabled);
						Gdx.input.setInputProcessor(stage);
					}
				})));
	}

	public void otherShowOptions() {
	}

	@Override
	public void hide() {
		setActorsTouchable(Touchable.disabled);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
