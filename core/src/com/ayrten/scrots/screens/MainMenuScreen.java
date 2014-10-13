package com.ayrten.scrots.screens;

import com.ayrten.scrots.scoreboard.ChallengeScoreboard;
import com.ayrten.scrots.scoreboard.NormalScoreboard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MainMenuScreen implements Screen 
{
	private ScrotsGame game;
	private TextButton start;
	private TextButton options;
	private TextButton high_score;
	private Stage stage;
	
	// For options and high scores screens
	private OptionsScreen options_screen;
	private HighScoresScreen high_score_screen;
	public GameScreen game_screen;
	
	public NormalScoreboard nsb;
	public ChallengeScoreboard csb;
	
	public MainMenuScreen(ScrotsGame game)
	{	
		// Initialize variables
		this.game = game;
		stage = new Stage();
		nsb = new NormalScoreboard();
		csb = new ChallengeScoreboard();
		
		Table table = new Table(); 
		table.setSkin(game.skin);
		table.setCenterPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/4);
		
		options_screen = new OptionsScreen((ScrotsGame) Gdx.app.getApplicationListener());
		high_score_screen = new HighScoresScreen((ScrotsGame) Gdx.app.getApplicationListener());
		// game_screen = new GameScreen((ScrotsGame) Gdx.app.getApplicationListener());
		
		start = new TextButton("", game.skin);
		LabelStyle style = new LabelStyle();
		style.font = game.font_32;
		start.add(new Label("Start", style));
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
				if(((ScrotsGame) Gdx.app.getApplicationListener()).prefs.getBoolean("sound_effs", true))
				  ((ScrotsGame) Gdx.app.getApplicationListener()).pop.play();
				game_screen = new GameScreen((ScrotsGame) Gdx.app.getApplicationListener());
				((ScrotsGame) Gdx.app.getApplicationListener()).setScreen(game_screen);
			}
		});
		
		options = new TextButton("", game.skin);
		// Based on what background it is, set the color of the font? white = > black? ugly?
		options.add(new Label("Options", style));
		options.setBounds(options.getX(), options.getY(), options.getWidth(), options.getHeight());
		options.addListener(new InputListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y,	int pointer, int button)
			{
				if(((ScrotsGame) Gdx.app.getApplicationListener()).prefs.getBoolean("sound_effs", true))
					((ScrotsGame) Gdx.app.getApplicationListener()).pop.play();
				((ScrotsGame) Gdx.app.getApplicationListener()).setScreen(options_screen);
			}
		});
		
		high_score = new TextButton("", game.skin);
		high_score.add(new Label("High Scores", style));
		high_score.setBounds(high_score.getX(), high_score.getY(), high_score.getWidth(), high_score.getHeight());
		high_score.addListener(new InputListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y,	int pointer, int button)
			{
				if(((ScrotsGame) Gdx.app.getApplicationListener()).prefs.getBoolean("sound_effs", true))
				  ((ScrotsGame) Gdx.app.getApplicationListener()).pop.play();
//				if(((ScrotsGame) Gdx.app.getApplicationListener()).main_menu.high_score_screen == null)
//					((ScrotsGame) Gdx.app.getApplicationListener()).main_menu.high_score_screen = new HighScoresScreen((ScrotsGame) Gdx.app.getApplicationListener());
//				high_score_screen = new HighScoresScreen((ScrotsGame) Gdx.app.getApplicationListener());
				((ScrotsGame) Gdx.app.getApplicationListener()).setScreen(high_score_screen);
			}
		});

		table.add(start);
		table.row();
		table.add("").height(Gdx.graphics.getHeight()/50);
		table.row();
		table.add(options);
		table.row();
		table.add("").height(Gdx.graphics.getHeight()/50);
		table.row();
		table.add(high_score);
		
		stage.addActor(table);
	}

	@Override
	public void render(float delta) 
	{
		if(game.prefs.getString("bg_color").equals("Black"))
			Gdx.gl.glClearColor(0, 0, 0, 0);
		else
			Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
	}
	
	public OptionsScreen get_options_screen()
	{
		return options_screen;
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