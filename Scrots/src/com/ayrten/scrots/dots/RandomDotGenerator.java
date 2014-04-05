package com.ayrten.scrots.dots;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class RandomDotGenerator
{
	private static final int	DOTS	= 3;	// For the random generator: to
												// get a random number between 0
												// and 2. Because we have 3
												// types of dots
									
	private Random random = new Random();
	
	private Texture				redDot;
	private Texture				blueDot;
	private Texture				greenDot;
	
	// Width and height of the game window
	private int					height;
	private int					width;
	
	public RandomDotGenerator(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		redDot = new Texture(Gdx.files.internal("data/red_dot.png"));
		redDot.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		blueDot = new Texture(Gdx.files.internal("data/blue_dot.png"));
		blueDot.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		greenDot = new Texture(Gdx.files.internal("data/green_dot.png"));
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
		Dot randomDot = null;
		
		int dotType = random.nextInt(DOTS);
		
		switch (dotType)
		{
			case 0:
				randomDot = new GreenDot(greenDot);
				break;
			case 1:
				randomDot = new BlueDot(blueDot);
				break;
			case 2:
				randomDot = new RedDot(redDot);
				break;
			default:
				break;
		}
		;
		
		// Gets a random position based on the width an height of the window
		try
		{
			randomDot
					.setPosition(random.nextInt(width), random.nextInt(height));
		}
		catch (Exception ex)
		{
			randomDot = new GreenDot(greenDot);
			randomDot.setPosition(230, 190);
		}
		
		return randomDot;
	}
}
