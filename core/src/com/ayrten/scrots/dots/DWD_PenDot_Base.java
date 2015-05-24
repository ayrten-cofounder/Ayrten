package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class DWD_PenDot_Base extends DWD {

	public DWD_PenDot_Base(Texture dot, Manager gm, Sound pop) {
		super(dot, gm, pop);
	}
	
	@Override
	public void touchedByAnAngel() {
		super.touchedByAnAngel();
		gm.resetCombo();
		if(gm.isInvincible())
			return;
		executePenalty();
	}
	
	public void executePenalty() {}

}
