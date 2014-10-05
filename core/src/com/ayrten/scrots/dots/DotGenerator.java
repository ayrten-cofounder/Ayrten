package com.ayrten.scrots.dots;

import java.util.Random;

import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.NinePatch;

//import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class DotGenerator
{
	private int dots = 3; // For the random generator: to
							// get a random number between 0
							// and 2. Because we have 3
							// types of dots

	private Random random = new Random();

	private Texture redDot;
	private Texture blueDot;
	private Texture greenDot;
	private Texture babyBlueDot;

	private Sound pop;

	// Width and height of the game window
	private int height;
	private int width;

	private Manager gm;

	public DotGenerator(int width, int height, Manager gm)
	{
	}

	public DotGenerator(int width, int height, Manager gm, Sound pop)
	{

		this.width = width;
		this.height = height;
		this.gm = gm;
		this.pop = pop;

		// Move these textures up to GameMode? - Tony
//		redDot = new NinePatch(new Texture(Gdx.files.internal("data/red.9.png")));
		redDot = new Texture(Gdx.files.internal("data/red_dot.png"));
		// redDot.setFilter(TextureFilter.Linear, TextureFilter.Linear);

//		blueDot = new NinePatch(new Texture(Gdx.files.internal("data/blue.9.png")));
		blueDot = new Texture(Gdx.files.internal("data/blue_dot.png"));
		// blueDot.setFilter(TextureFilter.Linear, TextureFilter.Linear);

//		greenDot = new NinePatch(new Texture(Gdx.files.internal("data/green.9.png")));
		greenDot = new Texture(Gdx.files.internal("data/green_dot.png"));
		// greenDot.setFilter(TextureFilter.Linear, TextureFilter.Linear);

//		babyBlueDot = new NinePatch(new Texture(Gdx.files.internal("data/baby_blue.9.png")));
		babyBlueDot = new Texture(Gdx.files.internal("data/baby_blue_dot.png"));
		// babyBlueDot.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		pop = Gdx.audio.newSound(Gdx.files.internal("sounds/pop.mp3"));

	}

	public GreenDot genGreenDot()
	{
		GreenDot gDot = new GreenDot(greenDot, gm, pop);
		setRandPositions(gDot);
		return gDot;
	}

	public RedDot genRedDot()
	{
		RedDot rDot = new RedDot(redDot, gm, pop);
		setRandPositions(rDot);
		return rDot;
	}

	public BlueDot genBlueDot()
	{
		BlueDot bDot = new BlueDot(blueDot, gm, pop);
		setRandPositions(bDot);
		return bDot;
	}

	public BabyBlueDot genBabyBlueDot()
	{
		BabyBlueDot bbDot = new BabyBlueDot(babyBlueDot, gm, pop);
		setRandPositions(bbDot);
		return bbDot;
	}

	// Gets random dot type
	// Then gets random position
	public Dot genRandDot()
	{
		Dot randomDot = null;

		int dotType = random.nextInt(dots);

		switch (dotType)
		{
			case 0:
				randomDot = new GreenDot(greenDot, gm, pop);
				break;
			case 1:
				randomDot = new BlueDot(blueDot, gm, pop);
				break;
			case 2:
				randomDot = new RedDot(redDot, gm, pop);
				break;
			case 3:
				randomDot = new BabyBlueDot(babyBlueDot, gm, pop);
				break;
			default:
				break;
		}

		// Gets a random position based on the width an height of the window
		setRandPositions(randomDot);
		return randomDot;
	}

	public void setRandPositions(Dot target)
	{
		int w = random.nextInt(width);
		int h = random.nextInt(height);

		if (w == 0)
		{
			w += target.getTexture().getWidth();
		}
		else if (w + target.getTexture().getWidth() > width)
		{
			w = (int) (width - target.getTexture().getWidth());
		}

		if (h == 0)
		{
			h += target.getTexture().getWidth();
		}
		else if (h + target.getTexture().getHeight() > height)
		{
			h = (int) (height - target.getTexture().getHeight());
		}

		target.setPosition(w, h);
	}
}
