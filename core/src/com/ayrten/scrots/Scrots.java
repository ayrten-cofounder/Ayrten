package com.ayrten.scrots;

import com.ayrten.scrots.game.Game;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Scrots implements ApplicationListener
{
	private SpriteBatch batch;
	TextureRegion region;

	private Game game;
	private Manager gm;
	private Stage stage;

	private int w;
	private int h;

	@Override
	public void create()
	{
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		System.out.println("Width of screen: " + w);
		System.out.println("Height of scrren: " + h);
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		batch = new SpriteBatch();
		gm = new Manager(0); // Starts with 0 points
		game = new Game(batch, stage, gm, w, h);
	}

	@Override
	public void dispose()
	{
		batch.dispose();
	}

	@Override
	public void render()
	{
		// Draws white background?
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.render();
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
}
