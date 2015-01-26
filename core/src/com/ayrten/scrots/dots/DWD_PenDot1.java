package com.ayrten.scrots.dots;

import com.ayrten.scrots.screens.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class DWD_PenDot1 extends DWD {
	public DWD_PenDot1(Texture dot, Manager gm, Sound pop) {
		super(dot, gm, pop);

		MAX_DOTS = 4;
		for (int i = 0; i < MAX_DOTS; i++) {
			Dot newDot = generator.genPenDot1();
			newDot.setPosition(getX(), getY());
			dots_inside.add(newDot);
		}
	}

	@Override
	public void touchedByAnAngel() {
		super.touchedByAnAngel();

	}
}
