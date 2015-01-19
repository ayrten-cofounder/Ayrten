package com.ayrten.scrots.dots;

import java.util.Random;

import com.ayrten.scrots.game.GameMode;
import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.screens.Manager;

public class DotGenerator 
{
	private Random random = new Random();
	// private Sound pop;

	// Width and height of the game window
	private int height;
	private int width;

	private Manager gm;

	public DotGenerator(int width, int height, Manager gm) {

		this.width = width;
		if (gm.get_game_mode() == GameMode.MAIN_MENU_BACKGROUND_MODE) {
			this.height = height;
		} else {
			this.height = Assets.game_height;
		}
		this.gm = gm;
	}

	public RegDot1 genRegDot1() {
		RegDot1 regDot = new RegDot1(Assets.regDot_1, gm, Assets.reg_pop_1);
		setRandPositions(regDot);
		return regDot;
	}
	
	public RegDot2 genRegDot2() {
		RegDot2 regDot = new RegDot2(Assets.regDot_2, gm, Assets.reg_pop_2);
		setRandPositions(regDot);
		return regDot;
	}

	public PenDot1 genPenDot1() {
		PenDot1 penDot = new PenDot1(Assets.penDot_1, gm, Assets.button_pop);
		setRandPositions(penDot);
		return penDot;
	}

	public PenDot2 genPenDot2() {
		PenDot2 penDot = new PenDot2(Assets.penDot_2, gm, Assets.pen_pop_1);
		setRandPositions(penDot);
		return penDot;
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
