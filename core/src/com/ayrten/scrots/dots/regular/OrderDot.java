package com.ayrten.scrots.dots.regular;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class OrderDot extends RegDot1 {
	protected int num;

	// Pointer to the next dot. If it's null, then this dot is the last dot.
	protected OrderDot nextDot;
	protected OrderDot lastDot;
	protected OrderDot firstDot;
	protected boolean isCurrOrder;

	public OrderDot(Texture dot, Manager gm, Sound pop, int number) {
		super(dot, gm, pop);
		num = number;
		isCurrOrder = false;
		setOrigin(Align.center);
	}

	@Override
	protected void incrementCombo() {
		if(isCurrOrder) {
			if(this.equals(lastDot) && isComboDot)
				gm.incrementCombo();
			else {
				Assets.points_manager.addPoints(1);
				nextDot.setOrder();
			}
		} else
			gm.resetCombo();
	}

	@Override
	public void draw(Batch batch, float alpha) {
		if(isComboDot) {
			gm.animation.draw(this, batch, alpha, magneted);
			setRotation(getRotation() + 7);
			batch.draw(getTexture(), getX(), getY(), getOriginX(), getOriginY(), 
					getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation(), 
					0, 0, 1024, 1024, false, false);
		} else
			super.draw(batch, alpha);
	}

	@Override
	protected void removeFromStage(InputEvent event) {
		if(isCurrOrder)
			super.removeFromStage(event);
	}

	@Override
	public void setComboDot() {
		lastDot.isComboDot = true;
		lastDot.setTexture(Assets.combo_dot);
		firstDot.setOrder();
	}

	public OrderDot getNextDot() {
		return nextDot;
	}

	public OrderDot getFirstDot() {
		return firstDot;
	}

	public OrderDot getLastDot() {
		return lastDot;
	}

	public void setNextDot(OrderDot next) {
		nextDot = next;
	}

	public void setFirstDot(OrderDot first) {
		firstDot = first;
	}

	public void setLastDot(OrderDot last) {
		lastDot = last;
	}

	public void setOrder() {
		isCurrOrder = true;
	}
}
