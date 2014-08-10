package com.ayrten.scrots.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MainMenuScreen implements Screen 
{
	private TextButton start;
	private TextButton options;
	private TextButton high_score;
	private Stage stage;
	private Skin skin;
	
	// For options and high scores screens
	private TextButton back_button;
	
	private OptionsScreen options_screen;
	private HighScoresScreen high_score_screen;
	private GameScreen game_screen;
	
	public MainMenuScreen()
	{	
		// Initialize variables
		stage = new Stage();
		
		Table table = new Table(); 
//		table.setFillParent(true);
		table.setHeight(Gdx.graphics.getHeight()/4);
		table.setCenterPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/3);

		stage.addActor(table);
		skin = ((ScrotsGame) Gdx.app.getApplicationListener()).skin;
		
		options_screen = new OptionsScreen((ScrotsGame) Gdx.app.getApplicationListener());
		high_score_screen = new HighScoresScreen((ScrotsGame) Gdx.app.getApplicationListener());
		game_screen = new GameScreen((ScrotsGame) Gdx.app.getApplicationListener());
		
		start = new TextButton("Start", skin);
		start.setBounds(start.getX(), start.getY(), start.getWidth(), start.getHeight());
		start.addListener(new InputListener()
		{
			// Need this or else it won't recognize the touch down event.
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y,	int pointer, int button)
			{
				if(((ScrotsGame) Gdx.app.getApplicationListener()).prefs.getBoolean("sound_effs"))
				  ((ScrotsGame) Gdx.app.getApplicationListener()).pop.play();
				((ScrotsGame) Gdx.app.getApplicationListener()).setScreen(game_screen);
			}
		});
		
		options = new TextButton("Options", skin);
		options.setBounds(options.getX(), options.getY(), options.getWidth(), options.getHeight());
		options.addListener(new InputListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y,	int pointer, int button)
			{
				if(((ScrotsGame) Gdx.app.getApplicationListener()).prefs.getBoolean("sound_effs"))
					  ((ScrotsGame) Gdx.app.getApplicationListener()).pop.play();
				((ScrotsGame) Gdx.app.getApplicationListener()).setScreen(options_screen);
			}
		});
		
		high_score = new TextButton("High Scores", skin);
		high_score.setBounds(high_score.getX(), high_score.getY(), high_score.getWidth(), high_score.getHeight());
		high_score.addListener(new InputListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y,	int pointer, int button)
			{
				if(((ScrotsGame) Gdx.app.getApplicationListener()).prefs.getBoolean("sound_effs"))
				  ((ScrotsGame) Gdx.app.getApplicationListener()).pop.play();
				((ScrotsGame) Gdx.app.getApplicationListener()).setScreen(high_score_screen);
			}
		});

		table.add(start);
		table.row();
		table.add().expand().fill();
		table.row();
		table.add(options);
		table.row();
		table.add().expand().fill();
		table.row();
		table.add(high_score);
	}

	@Override
	public void render(float delta) 
	{
		if(((ScrotsGame) Gdx.app.getApplicationListener()).prefs.getString("bg_color").equals("White"))
			Gdx.gl.glClearColor(1, 1, 1, 1);
		else
			Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
	}

	@Override
	public void dispose() 
	{
		// Remove all actors
		stage.dispose();
	}
	
	@Override
	public void resize(int width, int height) 
	{

	}

	@Override
	public void show() 
	{
		Gdx.input.setInputProcessor(stage);
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
}
