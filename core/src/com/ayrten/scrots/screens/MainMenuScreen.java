package com.ayrten.scrots.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class MainMenuScreen implements Screen 
{
	ScrotsGame game;
	
	private TextButton start;
	private TextButton options;
	private TextButton high_score;
	private Stage stage;
	private Skin skin;
	
	// For options and high scores screens
	private TextButton back_TextButton;
	
	private OptionsScreen options_screen;
	private HighScoresScreen high_score_screen;
	private GameScreen game_screen;
	
	public MainMenuScreen(ScrotsGame game)
	{
		this.game  = game;
		
		// Initialize variables
		stage = new Stage();
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		start = new TextButton("Start", skin);
		start.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		start.setBounds(start.getX(), start.getY(), start.getWidth(), start.getHeight());
		start.addListener(new InputListener()
		{
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button)
			{
				return true;
			}
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button)
			{
				System.out.println("IT WAS TOUCHED!");
			}
		});
		
		options = new TextButton("Options", skin);
		high_score = new TextButton("High Scores", skin);
		options_screen = new OptionsScreen(game);
		high_score_screen = new HighScoresScreen(game);
		game_screen = new GameScreen(game);
		
		stage.addActor(start);
		stage.addActor(options);
		stage.addActor(high_score);
		
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
		Table.drawDebug(stage);
		
		if(start.isPressed())
		{
			Gdx.input.setInputProcessor(game_screen.getStage());
			game.setScreen(game_screen);
		}
		
		if(options.isPressed())
		{
			game.setScreen(options_screen);
		}
		
		if(high_score.isPressed())
		{
			game.setScreen(high_score_screen);
		}
	}

	@Override
	public void resize(int width, int height) 
	{

	}

	@Override
	public void show() 
	{

	}

	@Override
	public void hide()
	{
		
	}

	@Override
	public void pause() 
	{
		
	}

	@Override
	public void resume() 
	{
		
	}

	@Override
	public void dispose() 
	{
		// Remove all actors
		stage.clear();
	}

}
