package com.ayrten.scrots.dots;

import java.util.Random;

import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

//import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class DotGenerator {
	private static final int DOTS = 3; // For the random generator: to
										// get a random number between 0
										// and 2. Because we have 3
										// types of dots

	private Random random = new Random();

	private Texture redDot;
	private Texture blueDot;
	private Texture greenDot;

	private Sound pop;

	// Width and height of the game window
	private int height;
	private int width;

	private Manager gm;

	public DotGenerator(int width, int height, Manager gm) {
		this.width = width;
		this.height = height;
		this.gm = gm;

		// Move these textures up to GameMode? - Tony
		redDot = new Texture(Gdx.files.internal("data/red_dot.png"));
		// redDot.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		blueDot = new Texture(Gdx.files.internal("data/blue_dot.png"));
		// blueDot.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		greenDot = new Texture(Gdx.files.internal("data/green_dot.png"));
		// greenDot.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		pop = Gdx.audio.newSound(Gdx.files.internal("sounds/pop.mp3"));
	}

	public GreenDot genGreenDot() {
		GreenDot gDot = new GreenDot(greenDot, gm, pop);
		setRandPositions(gDot);
		return gDot;
	}

	public RedDot genRedDot() {
		RedDot rDot = new RedDot(redDot, gm, pop);
		setRandPositions(rDot);
		return rDot;
	}

	public BlueDot genBlueDot() {
		BlueDot bDot = new BlueDot(blueDot, gm, pop);
		setRandPositions(bDot);
		return bDot;
	}

	// Gets random dot type
	// Then gets random position
	public Dot genRandDot() {
		Dot randomDot = null;

		int dotType = random.nextInt(DOTS);

		switch (dotType) {
		case 0:
			randomDot = new GreenDot(greenDot, gm, pop);
			break;
		case 1:
			randomDot = new BlueDot(blueDot, gm, pop);
			break;
		case 2:
			randomDot = new RedDot(redDot, gm, pop);
			break;
		default:
			break;
		}

		// Gets a random position based on the width an height of the window
		setRandPositions(randomDot);
		return randomDot;
	}

	public void setRandPositions(Dot target) {
		int w = random.nextInt(width);
		int h = random.nextInt(height);
		if (w == 0)
			w += target.getTexture().getWidth();
		else if (w + target.getTexture().getWidth() > width)
			w = width - target.getTexture().getWidth();
		if (h == 0)
			h += target.getTexture().getWidth();
		else if (h + target.getTexture().getHeight() > height)
			h = height - target.getTexture().getHeight();
		target.setPosition(w, h);
	}
}
