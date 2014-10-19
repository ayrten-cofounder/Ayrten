package com.ayrten.scrots.dots;

import java.util.Random;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;

public class DotGenerator {
	private int dots = 3; // For the random generator: to
							// get a random number between 0
							// and 2. Because we have 3
							// types of dots

	private Random random = new Random();

//	private Texture redDot;
//	private Texture blueDot;
//	private Texture greenDot;
//	private Texture babyBlueDot;

//	private Sound pop;

	// Width and height of the game window
	private int height;
	private int width;

	private Manager gm;

	public DotGenerator(int width, int height, Manager gm) {

		this.width = width;
		this.height = height - (height / 5);
		this.gm = gm;
	}

	public GreenDot genGreenDot() {
		GreenDot gDot = new GreenDot(Assets.greenDot, gm, Assets.pop);
		setRandPositions(gDot);
		return gDot;
	}

	public RedDot genRedDot() {
		RedDot rDot = new RedDot(Assets.redDot, gm, Assets.pop);
		setRandPositions(rDot);
		return rDot;
	}

	public BlueDot genBlueDot() {
		BlueDot bDot = new BlueDot(Assets.blueDot, gm, Assets.pop);
		setRandPositions(bDot);
		return bDot;
	}

	public BabyBlueDot genBabyBlueDot() {
		BabyBlueDot bbDot = new BabyBlueDot(Assets.babyBlueDot, gm, Assets.pop);
		setRandPositions(bbDot);
		return bbDot;
	}

	// Gets random dot type
	// Then gets random position
	public Dot genRandDot() {
		Dot randomDot = null;

		int dotType = random.nextInt(dots);

		switch (dotType) {
		case 0:
			randomDot = new GreenDot(Assets.greenDot, gm, Assets.pop);
			break;
		case 1:
			randomDot = new BlueDot(Assets.blueDot, gm, Assets.pop);
			break;
		case 2:
			randomDot = new RedDot(Assets.redDot, gm, Assets.pop);
			break;
		case 3:
			randomDot = new BabyBlueDot(Assets.babyBlueDot, gm, Assets.pop);
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

		if (w == 0) {
			w += target.getTexture().getWidth();
		} else if (w + target.getTexture().getWidth() > width) {
			w = (int) (width - target.getTexture().getWidth());
		}

		if (h == 0) {
			h += target.getTexture().getWidth();
		} else if (h + target.getTexture().getHeight() > height) {
			h = (int) (height - target.getTexture().getHeight());
		}

		target.setPosition(w, h);
	}
}
