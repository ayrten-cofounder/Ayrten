package com.ayrten.scrots.screens;

import com.ayrten.scrots.game.ChallengeGameMode;
import com.ayrten.scrots.game.GameMode;
import com.ayrten.scrots.game.NormalGameMode;
import com.ayrten.scrots.manager.Manager;
import com.ayrten.scrots.scoreboard.ChallengeScoreboard;
import com.ayrten.scrots.scoreboard.NormalScoreboard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

// public class Scrots implements ApplicationListener
public class GameScreen implements Screen
{
	private GameMode gamemode;
	private Manager gm;
	private Stage stage;
	private ScrotsGame game;

	private int w;
	private int h;

	public GameScreen(ScrotsGame game)
	{
		this.game =  game;
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		gm = new Manager(0, w, h); // Starts with 0 points
		if(game.prefs.getString("mode").equals("Normal")) 
		{
			gamemode = new NormalGameMode(game, stage, gm, w, h);
			NormalScoreboard nsb = new NormalScoreboard();
			gm.setScoreboard(nsb);
		}
		else {
			gamemode = new ChallengeGameMode(game, stage, gm, w, h);
			ChallengeScoreboard csb = new ChallengeScoreboard();
			gm.setScoreboard(csb);
		}
	}

	@Override
	public void dispose()
	{
		gamemode.dispose();
	}

	@Override
	public void render(float delta)
	{
		if(game.prefs.getString("bg_color").equals("Black"))
			Gdx.gl.glClearColor(0, 0, 0, 0);
		else
			Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gamemode.render();
	}

	@Override
	public void resize(int width, int height)
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
	public void show() 
	{
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}
