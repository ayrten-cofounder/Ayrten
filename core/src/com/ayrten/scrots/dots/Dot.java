package com.ayrten.scrots.dots;

import java.util.Random;

import com.ayrten.scrots.game.GameMode;
import com.ayrten.scrots.manager.Manager;
import com.ayrten.scrots.screens.ScrotsGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Dot extends Actor {
	private static final int BUMP_IT_UP = 15; // min
	private static final float MAX_SIZE_RATIO = 15; // No use really...
	private float size_ratio = MAX_SIZE_RATIO / 2; // arbituary
													// buffer...jk

	private Texture dot;
	// private NinePatch dot;

	public Manager gm;
	private Random random;

	public Sound pop;

	public Dot(Texture dot, Manager gm, Sound pop) {
		this.dot = dot;
		this.gm = gm;
		this.pop = pop;
		random = new Random(System.nanoTime());
		setBounds(getX(), getY(), dot.getWidth(), dot.getHeight());
		// An InputListener is a subclass of EventListener
		addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {

				// Gotta get touched by an angel ;
				touchedByAnAngel();
				
				dotChange();

				// Remove the actor from the stage.
				event.getTarget().remove();
			}
		});
	}

	private float getCircleWidth() {
		return (float) (gm.w / size_ratio);
	}

	private float getCircleHeight() {
		return (float) ((getCircleWidth() * dot.getHeight()) / dot.getWidth());
	}

	// This class shall be overriddent by the blue, green, red dots
	public void touchedByAnAngel() {
		if (((ScrotsGame) Gdx.app.getApplicationListener()).prefs.getBoolean(
				"sound_effs", true)) {
			pop.play();
		}
	}

	public void setTexture(Texture dot) {
		this.dot = dot;
	}

	public Texture getTexture() {
		return dot;
	}

	public void setPosition(float x, float y) {
		setX(x);
		setY(y);
	}

	private void dotChange() {
		if (gm.get_game_mode() == GameMode.CHALLENGE_MODE) {
			gm.changeDotSize();
		}
	}

	public void resetRatio() {
		if (random.nextInt(2) == 1) {
			size_ratio = 7.5f;
		} else {
			size_ratio = 15f;
		}
		// size_ratio = random.nextInt(BUMP_IT_UP) + BUMP_IT_UP;
	}

	// Overridden functions.
	@Override
	public void draw(Batch batch, float alpha) {
		// batch.draw(dot, getX(), getY(), getCircleWidth(), getCircleHeight(),
		// 0,
		// 0, dot.getWidth(), dot.getHeight(), false, false);
		batch.draw(dot, getX(), getY(), getCircleWidth(), getCircleHeight());
		// dot.draw(batch, getX(), getY(), getCircleWidth(), getCircleHeight());
	}
}
