package com.ayrten.scrots.dots;

import java.util.Random;

import com.ayrten.scrots.dotGraphics.DotGraphics;
import com.ayrten.scrots.dotGraphics.DotGraphics_NormalGameMode;
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
	protected static boolean INVINCIBLE = false;
	
	public Texture dot;
	public Manager gm;
	public Random random;
	public DotGraphics graphics;

	public Sound pop;

	protected InputListener listener;
	public boolean magneted = false;

	public Dot(Texture dot, Manager gm, Sound pop) {
		this.dot = dot;
		this.gm = gm;
		this.pop = pop;
		random = new Random(System.nanoTime());
		setBounds(getX(), getY(), dot.getWidth(), dot.getHeight());
		if (gm.get_game_mode() == GameMode.NORMAL_MODE
				|| gm.get_game_mode() == GameMode.CHALLENGE_MODE) {
			graphics = new DotGraphics_NormalGameMode(this);
		} else 
			graphics = new DotGraphics(this);
		
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
		Assets.gplay_manager.increment_dot_count();
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
		graphics.resetRatio();
	}

	// Overridden functions.
	@Override
	public void draw(Batch batch, float alpha) {
		graphics.draw(batch, alpha, magneted);
	}
}
