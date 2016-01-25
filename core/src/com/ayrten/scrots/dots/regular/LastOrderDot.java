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
		if(isCurrOrder && isComboDot) {
			gm.incrementCombo();
		} else
			gm.resetCombo();
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		setOrigin(Align.center);
		setRotation(getRotation() + 7);
		gm.animation.animateV2(this, batch, alpha, 0, 0, 160, 160);
		// When the LastOrderedDot is a ComboDot, then the texture is replaced by a different texture
		// and doesn't need the number to show up. Instead, the dot will be rotating to show that
		// the player is currently popping a sequence of dots and this is the last dot in the
		// sequence.
		if(!isComboDot) {
			num_label.setPosition(getX(Align.center), getY(Align.center), Align.center);
			num_label.draw(batch, alpha);
		}
	}
}
