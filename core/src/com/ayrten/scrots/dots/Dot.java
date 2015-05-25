package com.ayrten.scrots.dots;

import com.ayrten.scrots.dotAnimation.DotAnimation;
import com.ayrten.scrots.dotAnimation.DotAnimation_TimeMode;
import com.ayrten.scrots.game.GameMode;
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
	//protected Random random;
	protected DotAnimation animation;

	protected Sound pop;

	protected InputListener listener;
	public boolean isComboDot;
	public boolean magneted = false;

	public Dot(Texture dot, Manager gm, Sound pop) {
		this.dot = dot;
		this.gm = gm;
		this.pop = pop;
		isComboDot = false;
		setBounds(getX(), getY(), dot.getWidth(), dot.getHeight());
		if (gm.get_game_mode() == GameMode.NORMAL_MODE
				|| gm.get_game_mode() == GameMode.CHALLENGE_MODE) {
			animation = new DotAnimation_TimeMode(this);
		} else 
			animation = new DotAnimation(this);
		
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
		animation.resetRatio();
	}

	// Overridden functions.
	@Override
	public void draw(Batch batch, float alpha) {
		animation.draw(batch, alpha, magneted);
	}
}
