package com.ayrten.scrots.dots;

import java.util.Random;

import com.ayrten.scrots.game.GameMode;
import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Dot extends Actor {
	protected int slope_rand_x = 9;
	protected int slope_rand_y = 9;
	protected int velocity_of_dot = 5; // How often it moves
	protected float speed_of_dot = 1f; // How fast it moves
	private static final float MAX_SIZE_RATIO = 15; // No use really...
	private static final float MIN_SIZE_RATIO = 7.5f;
	private float size_ratio = MIN_SIZE_RATIO;

	private Texture dot;
	// private NinePatch dot;

	public Manager gm;
	private Random random;

	private float curr_width;
	private float curr_height;

	private boolean isGoingRight;
	private int velocity_count = 0;
	private float slopeX = 1; // slopeX and slopeY makes the slope
	private float slopeY = 1;

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

		randSlope();
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
		if (gm.get_game_mode() == GameMode.CHALLENGE_MODE) {
			move_v1();
		} else if (gm.get_game_mode() == GameMode.MAIN_MENU_BACKGROUND_MODE) {
			velocity_of_dot = 0;
			move_v3();
		}
	}

	// Left/Right
	private void move_v1() {
		if (velocity_count == velocity_of_dot) {
			velocity_count = 0;
		} else {
			velocity_count++;
			return;
		}

		float x = getX();
		float maxX = Gdx.graphics.getWidth();

		if (isGoingRight) {
			if ((x + speed_of_dot + curr_width) < maxX) {
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

	// Left/Right/Up/Down
	private void move_v2() {

	}

	// Diagional
	private void move_v3() {
		if (velocity_count == velocity_of_dot) {
			velocity_count = 0;
		} else {
			velocity_count++;
			return;
		}

		float x = getX();
		float y = getY();
		float maxX = Gdx.graphics.getWidth();
		float maxY = Gdx.graphics.getHeight();

//		 System.out.println(slopeX + ", " + slopeY);

		if (slopeX >= 0f && slopeY >= 0f) {
			if (slopeX + x + curr_width < maxX
					&& slopeY + y + curr_height < maxY) {
				setPosition(x + slopeX, y + slopeY);
			} else {
				resetSlope();
			}
		} else if (slopeX <= 0f && slopeY <= 0f) {
			if (slopeX + x > 0 && slopeY + y > 0) {
				setPosition(x + slopeX, y + slopeY);
			} else {
				resetSlope();
			}
		} else if (slopeX <= 0f && slopeY >= 0f) {
			if (slopeX + x > 0 && slopeY + y + curr_height < maxY) {
				setPosition(x + slopeX, y + slopeY);
			} else {
				resetSlope();
			}
		} else if (slopeX >= 0f && slopeY <= 0f) {
			if (slopeX + x + curr_width < maxX && slopeY + y > 0) {
				setPosition(x + slopeX, y + slopeY);
			} else {
				resetSlope();
			}
		} else {
			slopeX = 1;
			slopeY = 1;
		}
	}

	private void dotChange() {
		gm.changeDotSize();
	}

	private void randSlope() {
		int x = random.nextInt(slope_rand_x);
		int y = random.nextInt(slope_rand_y);

		slopeX = x - 4;
		slopeY = y - 4;

		if (slopeX == 0 && slopeY == 0) {
			randSlope();
		}
	}

	private void resetSlope() {
		randSlope();
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
