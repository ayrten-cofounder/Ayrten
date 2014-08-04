package com.ayrten.scrots.screens;

import com.ayrten.scrots.game.GameMode;
import com.ayrten.scrots.manager.Manager;
import com.ayrten.scrots.screens.ScrotsGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

// public class Scrots implements ApplicationListener
public class GameScreen implements Screen
{
	private SpriteBatch batch;
	TextureRegion region;

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

		batch = new SpriteBatch();
		gm = new Manager(0); // Starts with 0 points
		gamemode = new GameMode(batch, stage, gm, w, h);
	}
	
	public Stage getStage()
	{
		return stage;
	}

	@Override
	public void dispose()
	{
		batch.dispose();
	}

	@Override
	public void render(float delta)
	{
		// Draws white background?
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}
