package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class PenDot_Base extends Dot {

	public PenDot_Base(Texture dot, Manager gm, Sound pop) {
		super(dot, gm, pop);
	}
	
	@Override
	public void touchedByAnAngel() {
		super.touchedByAnAngel();
		if(gm.isInvincible())
			return;
	    executePenalty();
	}
	
	public void executePenalty() {}
}
