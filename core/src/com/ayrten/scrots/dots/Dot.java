package com.ayrten.scrots.dots;

import java.util.Random;

import com.ayrten.scrots.dot_movement.DotMovement;
import com.ayrten.scrots.dot_movement.DotMovement_MainMenuScreenBackground;
import com.ayrten.scrots.dot_movement.DotMovement_NormalGameMode;
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
	private static final float MAX_SIZE_RATIO = 15; // No use really...
	private static final float MIN_SIZE_RATIO = 7.5f;
	private float size_ratio = MIN_SIZE_RATIO;

	private Texture dot;
	// private NinePatch dot;

	public Manager gm;
	public Random random;
	public DotMovement move;

	public float curr_width;
	public float curr_height;

	public Sound pop;

	public Dot(Texture dot, Manager gm, Sound pop) {
		this.dot = dot;
		this.gm = gm;
		this.pop = pop;
		random = new Random(System.nanoTime());
		setBounds(getX(), getY(), dot.getWidth(), dot.getHeight());

		if (gm.get_game_mode() == GameMode.NORMAL_MODE
				|| gm.get_game_mode() == GameMode.CHALLENGE_MODE) {
			move = new DotMovement_NormalGameMode(this);
		} else if (gm.get_game_mode() == GameMode.MAIN_MENU_BACKGROUND_MODE) {
			move = new DotMovement_MainMenuScreenBackground(this);
		}

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
		if (Assets.prefs.getBoolean("sound_effs", true)) {
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
		move.move();
	}

	private void dotChange() {
		gm.changeDotSize();
	}

	public void setSize() {
		curr_width = getCircleWidth();
		curr_height = getCircleHeight();
		setBounds(getX(), getY(), curr_width, curr_height);
	}

	public void resetRatio() {
		size_ratio = random.nextBoolean() ? MAX_SIZE_RATIO : MIN_SIZE_RATIO;
	}

	// Overridden functions.
	@Override
	public void draw(Batch batch, float alpha) {
		setSize();
		changePosition();
		batch.draw(dot, getX(), getY(), curr_width, curr_height);
	}
}
