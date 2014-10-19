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
	protected int velocity_of_dot = 5; // How often it moves
	protected float speed_of_dot = 1f; // How fast it moves
	private static final float MAX_SIZE_RATIO = 15; // No use really...
	private static final float MIN_SIZE_RATIO = 7.5f;
	private float size_ratio = MIN_SIZE_RATIO;

	private Texture dot;
	// private NinePatch dot;

	public Manager gm;
	private Random random;

	private boolean isGoingRight;
	private int velocity_count = 0;

	public Sound pop;

	public Dot(Texture dot, Manager gm, Sound pop) {
		this.dot = dot;
		this.gm = gm;
		this.pop = pop;
		random = new Random(System.nanoTime());
		isGoingRight = random.nextBoolean();
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

				// Remove the actor from the stage.
				event.getTarget().remove();

				dotChange();
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

	public void changePosition() {
		if (gm.get_game_mode() == GameMode.CHALLENGE_MODE) {
			if (velocity_count == velocity_of_dot) {
				velocity_count = 0;
			} else {
				velocity_count++;
				return;
			}

			float x = getX();
			float maxX = Gdx.graphics.getWidth();

			if (isGoingRight) {
				if ((x + speed_of_dot + getWidth()) < maxX) {
					setX(x + 1);
				} else {
					isGoingRight = !isGoingRight;
				}
			} else {
				if (x - speed_of_dot > 0) {
					setX(x - 1);
				} else {
					isGoingRight = !isGoingRight;
				}
			}
		}
	}

	private void dotChange() {
		gm.changeDotSize();
	}

	public void resetRatio() {
		size_ratio = random.nextBoolean() ? MAX_SIZE_RATIO : MIN_SIZE_RATIO;
	}

	// Overridden functions.
	@Override
	public void draw(Batch batch, float alpha) {
		changePosition();
		setBounds(getX(), getY(), getCircleWidth(), getCircleHeight());
		batch.draw(dot, getX(), getY(), getCircleWidth(), getCircleHeight());
	}
}
