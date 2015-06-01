package com.ayrten.scrots.dots.penalty;

import com.ayrten.scrots.dots.MovingDot;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class PenDot_Base extends MovingDot {

	public PenDot_Base(Texture dot, Manager gm, Sound pop) {
		super(dot, gm, pop);
	}
	
	@Override
	public void touchedByAnAngel(InputEvent event) {
		super.touchedByAnAngel(event);
		gm.resetCombo();
		if(gm.isInvincible())
			return;
	    executePenalty();
	}
	
	public void executePenalty() {}
}
