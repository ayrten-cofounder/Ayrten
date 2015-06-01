package com.ayrten.scrots.dots.penalty;

import com.ayrten.scrots.dots.DWD;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class DWD_PenDot_Base extends DWD {

	public DWD_PenDot_Base(Texture dot, Manager gm, Sound pop) {
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
