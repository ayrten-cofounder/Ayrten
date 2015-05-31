package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Dot extends Actor {
	protected Texture dot;
	public Manager gm;

	protected Sound pop;

	protected InputListener listener;
	public boolean isComboDot;
	public boolean magneted = false;
	
	public float slopeX, slopeY, sizeRatio;
	public float prevSlopeX, prevSlopeY;
	
	public float speedX = 1, speedY = 1;

	public Dot(Texture dot, Manager gm, Sound pop) {
		this.dot = dot;
		this.gm = gm;
		this.pop = pop;
		isComboDot = false;
		
		gm.animation.randSlopeV3(this);
		resetRatio();
		// Set initial size based on ratio.
		gm.animation.setSize(this);
		
		// An InputListener is a subclass of EventListener
		listener = new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// Gotta get touched by an angel
				touchedByAnAngel();

				// Remove the actor from the stage.
				event.getTarget().remove();

				dotChange();
			}
		};
		addListener(listener);
	}

	// This class shall be overriddent by the real dots
	public void touchedByAnAngel() {
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

	public void setTexture(Texture dot) {
		this.dot = dot;
	}

	public Texture getTexture() {
		return dot;
	}

	private void dotChange() {
		gm.changeDotSize();
	}

	public void resetRatio() {
		gm.animation.resetRatio(this);
	}

	// Overridden functions.
	@Override
	public void draw(Batch batch, float alpha) {
		gm.animation.draw(this, batch, alpha, magneted);
	}
}
