package com.ayrten.scrots.dots.regular;

import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class LastOrderDot extends OrderDot {

	public LastOrderDot(Texture dot, Manager gm, Sound pop, int number) {
		super(dot, gm, pop, number);
	}
	
	@Override
	protected void incrementCombo() {
		if(isCurrOrder) {
			gm.incrementCombo();
		} else
			gm.resetCombo();
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		setOrigin(Align.center);
		setRotation(getRotation() + 7);
		gm.animation.animateV2(this, batch, alpha, 0, 0, 1024, 1024);
	}
}
