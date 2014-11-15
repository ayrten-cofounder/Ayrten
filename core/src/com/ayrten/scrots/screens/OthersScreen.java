package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class OthersScreen implements Screen 
{
	protected Table table;
	protected Stage stage;
	
	// Labels
	protected Label options;
	protected Label tutorial;
	protected Label credits;
	protected Label back;
	
	// Screens
	protected OptionsScreen 	options_screen;
	protected TutorialScreen 	tutorial_screen;
	protected CreditsScreen		credits_screen;
	
	public OthersScreen() 
	{
		stage = new Stage();
		table = new Table();
		table.setSkin(Assets.skin);
		table.setFillParent(true);
		
		options_screen  = new OptionsScreen(this);
		tutorial_screen = new TutorialScreen(this);
		credits_screen  = new CreditsScreen();
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = Assets.font_64;
		labelStyle.fontColor = Assets.ORANGE;
		
		options = new Label("Options", labelStyle);
		options.setBounds(options.getX(), options.getY(), options.getWidth(), options.getHeight());
		options.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.game.setScreen(options_screen);
			}
		});
		
		tutorial = new Label("Tutorial", labelStyle);
		tutorial.setBounds(tutorial.getX(), tutorial.getY(), tutorial.getWidth(), tutorial.getHeight());
		tutorial.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.game.setScreen(tutorial_screen);
			}
		});
		
		credits = new Label("Credits", labelStyle);
		credits.setBounds(credits.getX(), credits.getY(), credits.getWidth(), credits.getHeight());
		credits.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.game.setScreen(credits_screen);
			}
		});
		
		back = new Label("Back", labelStyle);
		back.setBounds(back.getX(), back.getY(), back.getWidth(), back.getHeight());
		back.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Assets.prefs.getBoolean("sound_effs"))
					Assets.pop.play();
				stage.addAction(Actions.sequence(Actions.alpha(1), Actions.fadeOut(0.35f), Actions.run(new Runnable() {
					@Override
					public void run() {
						Assets.game.setScreen(Assets.game.main_menu);
					}
				})));
			}
		});
		back.setPosition(0, Gdx.graphics.getHeight() - back.getHeight());
		
		table.add(options);
		table.row();
		table.add(tutorial);
		table.row();
		table.add(credits);
		
		stage.addActor(table);
		stage.addActor(back);
	}

	@Override
	public void render(float delta) 
	{
		if (Assets.prefs.getString("bg_color").equals("Black"))
			Gdx.gl.glClearColor(0, 0, 0, 0);
		else
			Gdx.gl.glClearColor(1, 1, 1, 1);
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1f)));
	}

	@Override
	public void hide() {
		
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
