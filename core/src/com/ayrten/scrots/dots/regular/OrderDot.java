package com.ayrten.scrots.dots.regular;

import com.ayrten.scrots.common.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class OrderDot extends RegDot1 {
	protected Label num_label;

	// Pointer to the next dot. If it's null, then this dot is the last dot.
	protected OrderDot nextDot;
	protected OrderDot lastDot;
	protected OrderDot firstDot;
	protected boolean isCurrOrder;

	public OrderDot(Texture dot, Manager gm, Sound pop, int number) {
		super(dot, gm, pop);
		num_label = new Label(String.valueOf(number), Assets.style_font_64_white);
		isCurrOrder = false;
	}

	@Override
	protected void incrementCombo() {
		if(isCurrOrder) {
			Assets.points_manager.addPoints(1);
			nextDot.setOrder();
		} else
			gm.resetCombo();
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		super.draw(batch, alpha);
		num_label.setPosition(getX(Align.center), getY(Align.center), Align.center);
		num_label.draw(batch, alpha);
	}

	@Override
	protected void removeFromStage(InputEvent event) {
		if(isCurrOrder)
			super.removeFromStage(event);
	}

	// This is called by Manager to start the sequence.
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
