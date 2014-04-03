package com.ayrten.scrots.dots;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class RandomDotGenerator
{
	private Texture redDot;
	private Texture blueDot;
	private Texture greenDot;
	
	private int height;
	private int width;
	
	public RandomDotGenerator(int height, int width)
	{
		this.height = height;
		this.width = width;
		
		redDot = new Texture(Gdx.files.internal("data/libgdx.png"));
		redDot.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		blueDot = new Texture(Gdx.files.internal("data/libgdx.png"));
		blueDot.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		greenDot = new Texture(Gdx.files.internal("data/green_circle.png"));
		greenDot.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}
	
	private int getRandomHeight()
	{
		return 0;
	}
	
	private int getRandomWidth()
	{
		return 0;
	}
	
	public Dot getRandomDot()
	{
		
		
		return null;
	}
}
