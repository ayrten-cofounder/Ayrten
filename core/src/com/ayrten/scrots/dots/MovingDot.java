package com.ayrten.scrots.dots;

import com.ayrten.scrots.common.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class MovingDot extends Dot {
	public boolean isComboDot;
	public boolean magneted = false;
	
	public float slopeX, slopeY, sizeRatio;
	public float prevSlopeX, prevSlopeY;
	public float speedX = 1, speedY = 1;

	public MovingDot(Texture dot, Manager gm, Sound pop) {
		super(dot, gm, pop);
		
		isComboDot = false;
		
		// Set initial movement.
		gm.animation.randSlopeV3(this);
		resetRatio();
		// Set initial size based on ratio.
		gm.animation.setSize(this);
	}
	
	@Override
	protected void initializeListener() {
		listener = new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// Gotta get touched by an angel
				touchedByAnAngel(event);
			}
		};
		this.addListener(listener);
	}
	
	@Override
	public void touchedByAnAngel(InputEvent event) {
		removeFromStage(event);
	}
	
	protected void removeFromStage(InputEvent event) {
		event.getTarget().remove();
		dotChange();
		if (Assets.prefs.getBoolean("sound_effs", true))
			pop.play();
		gm.curr_level.getDotList().remove(this);
	}
	
	public void resetSpeed() {
		speedX = 1;
		speedY = 1;
		slopeX = prevSlopeX;
		slopeY = prevSlopeY;
	}
	
	private void setSpeed(float spdX, float spdY) {
		speedX = spdX;
		speedY = spdY;
		prevSlopeX = slopeX;
		prevSlopeY = slopeY;
		slopeX *= speedX;
		slopeY *= speedY;
	}
	
	public void slowDown(int slowBy) {
		resetSpeed();
		setSpeed((speedX / slowBy), (speedY / slowBy));
	}
	
	public void speedUp(int speedBy) {
		resetSpeed();
		setSpeed((speedX * speedBy), (speedY * speedBy));
	}
	
	public void setComboDot() {
		isComboDot = true;
		setTexture(Assets.combo_dot);
	}
	
	private void dotChange() {
		gm.changeDotSize();
	}

	public void resetRatio() {
		gm.animation.resetRatio(this);
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		gm.animation.animateV1(this, batch, alpha);
	}
}
