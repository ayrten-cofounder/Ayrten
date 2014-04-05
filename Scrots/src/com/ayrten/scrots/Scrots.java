package com.ayrten.scrots;

import java.util.ArrayList;

import com.ayrten.scrots.dots.Dot;
import com.ayrten.scrots.dots.RandomDotGenerator;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Scrots implements ApplicationListener
{
	private RandomDotGenerator	generator;
	
	private SpriteBatch			batch;
	private Texture				texture;
	TextureRegion				region;
	Vector2						position;
	
	ArrayList<Dot> dot_array = new ArrayList<Dot>();
	
	private int					w;
	private int					h;
	
	@Override
	public void create()
	{
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		
		generator = new RandomDotGenerator(w, h);
		batch = new SpriteBatch();
		
		position = new Vector2(50, 50);
	}
	
	@Override
	public void dispose()
	{
		batch.dispose();
		texture.dispose();
	}
	
	@Override
	public void render()
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (Gdx.input.isTouched())
		{
			dot_array.add(generator.getRandomDot());
		}
		
		batch.begin();

		for(Dot dot: dot_array)
		{
			batch.draw(dot.getTexture(), dot.getX(), dot.getY());
		}
		
		batch.end();
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
