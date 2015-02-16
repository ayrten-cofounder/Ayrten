package com.ayrten.scrots.screens;

import java.util.ArrayList;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ScrotsScreen implements Screen {

	protected Stage stage;
	protected Screen backScreen;
	protected ArrayList<Actor> actors;

	// Widgets
	protected Label back;

	protected boolean createBack;
	protected boolean backStage;
	
	public ScrotsScreen() {}

	public ScrotsScreen(Screen bscreen, boolean createBack)
	{
		backScreen = bscreen;
		actors = new ArrayList<Actor>();
		this.createBack = createBack;
		backStage = true;
		stage = new Stage() {
			public boolean keyDown(int keyCode) {
				if(keyCode == Keys.BACK && backStage && backScreen != null)
				{
					backStage = false;
					if(Assets.prefs.getBoolean("sound_effs"))
						Assets.button_pop.play();
					stage.addAction(Actions.parallel(Actions.run(new Runnable() {
						public void run() {
							setActorsTouchable(Touchable.disabled);
						}
					}), Actions.sequence(Actions.alpha(1), Actions.fadeOut(0.35f), Actions.run(new Runnable() {
						public void run() {
							Assets.game.setScreen(backScreen);
						}
					}))));
				}
				return super.keyDown(keyCode);
			}
		};
	}
	
	public void createBackLabel()
	{
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = Assets.font_64;
		labelStyle.fontColor = Assets.ORANGE;

		back = new Label("Back", labelStyle);
		back.setBounds(back.getX(), back.getY(), back.getWidth(), back.getHeight());
		back.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if(Assets.prefs.getBoolean("sound_effs"))
					Assets.button_pop.play();
				stage.addAction(Actions.parallel(Actions.run(new Runnable() {
					public void run() {
						setActorsTouchable(Touchable.disabled);
					}
				}), Actions.sequence(Actions.alpha(1), Actions.fadeOut(0.35f), Actions.run(new Runnable() {
					public void run() {
						Assets.game.setScreen(backScreen);
					}
				}))));
			}
		});
	}
	
	// This must be called near the end of the constructor.
	public void setupStage()
	{
		if(createBack)
		{
			createBackLabel();
			back.setPosition(0 + back.getWidth()/5, Gdx.graphics.getHeight() - back.getHeight());
			stage.addActor(back);
		}

		addActors();
		setActorsTouchable(Touchable.disabled);
	}

	public void addActors()
	{
		if(back != null)
			actors.add(back);
	}
	
	public void setBackScreen(Screen bscreen)
	{
		backScreen = bscreen;
	}

	public void setActorsTouchable(Touchable touchable)
	{
		for(Actor actor : actors)
			actor.setTouchable(touchable);
	}

	
	@Override
	public void render(float delta) {
		if (Assets.prefs.getString("bg_color").equals("Black")) {
			Gdx.gl.glClearColor(0, 0, 0, 0);
		} else {
			Gdx.gl.glClearColor(1, 1, 1, 1);
		}
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1f), Actions.run(new Runnable() {
			public void run() {
				otherShowOptions();
				backStage = true;
				setActorsTouchable(Touchable.enabled);
				Gdx.input.setInputProcessor(stage);
			}
		})));
	}

	public void otherShowOptions() {}

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
