package com.ayrten.scrots.dot_movement;

import com.ayrten.scrots.dots.Dot;
import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;

public class DotMovement {
	public int slope_rand_x = 9;
	public int slope_rand_y = 9;
	public int velocity_of_dot = 0; // How often it moves
	public float speed_of_dot = 1f; // How fast it moves

	protected boolean isGoingRight;
	protected boolean isGoingUp;
	protected int velocity_count = 0;
	protected float slopeX = 1; // slopeX and slopeY makes the slope
	protected float slopeY = 1;

	protected Dot dot;

	public DotMovement(Dot dot) {
		this.dot = dot;
		this.isGoingRight = dot.random.nextBoolean();
		this.isGoingUp = dot.random.nextBoolean();
	}

	public void move() {
	}

	// Left/Right
	protected void move_v1() {
		float x = dot.getX();
		float maxX = Gdx.graphics.getWidth();

		if (isGoingRight) {
			if ((x + speed_of_dot + dot.curr_width) < maxX) {
				dot.setX(x + 1);
			} else {
				isGoingRight = !isGoingRight;
			}
		} else {
			if (x - speed_of_dot > 0) {
				dot.setX(x - 1);
			} else {
				isGoingRight = !isGoingRight;
			}
		}
	}

	// Up/Down/left/right
	protected void move_v2() {
		float x = dot.getX();
		float y = dot.getY();
		float maxX = Gdx.graphics.getWidth();
		float maxY = Assets.game_height;

		// Up/Down
		if (slopeX == 0) {
			// Up
			if (slopeY > 0) {
				if ((y + slopeY + dot.curr_width) < maxY) {
					dot.setY(y + slopeY);
				} else {
					slopeY = slopeY * (-1);
				}
			}
			// Down
			else {
				if (y - slopeY > 0) {
					dot.setY(y - slopeY);
				} else {
					slopeY = slopeY * (-1);
				}
			}
		}
		// Left/Right
		else {
			// Right
			if (slopeX > 0) {
				if ((x + slopeX + dot.curr_width) < maxX) {
					dot.setX(x + slopeX);
				} else {
					slopeX = slopeX * (-1);
				}
			}
			// Left
			else {
				if (x - slopeX > 0) {
					dot.setX(x - slopeX);
				} else {
					slopeX = slopeX * (-1);
				}
			}
		}
	}

	// Diagional
	protected void move_v3() {
		float x = dot.getX();
		float y = dot.getY();
		float maxX = Gdx.graphics.getWidth();
		float maxY = Assets.game_height;

		if (slopeX >= 0f && slopeY >= 0f) {
			if (slopeX + x + dot.curr_width < maxX
					&& slopeY + y + dot.curr_height < maxY) {
				dot.setPosition(x + slopeX, y + slopeY);
			} else {
				randSlopeV3();
			}
		} else if (slopeX <= 0f && slopeY <= 0f) {
			if (slopeX + x > 0 && slopeY + y > 0) {
				dot.setPosition(x + slopeX, y + slopeY);
			} else {
				randSlopeV3();
			}
		} else if (slopeX <= 0f && slopeY >= 0f) {
			if (slopeX + x > 0 && slopeY + y + dot.curr_height < maxY) {
				dot.setPosition(x + slopeX, y + slopeY);
			} else {
				randSlopeV3();
			}
		} else if (slopeX >= 0f && slopeY <= 0f) {
			if (slopeX + x + dot.curr_width < maxX && slopeY + y > 0) {
				dot.setPosition(x + slopeX, y + slopeY);
			} else {
				randSlopeV3();
			}
		} else {
			slopeX = 1;
			slopeY = 1;
		}
	}

	protected void randSlopeV2() {
		if (dot.random.nextBoolean()) {
			slopeX = dot.random.nextInt(2) - 1;
		} else {
			slopeY = dot.random.nextInt(2) - 1;
		}
	}

	protected void randSlopeV3() {
		int x = dot.random.nextInt(slope_rand_x);
		int y = dot.random.nextInt(slope_rand_y);

		slopeX = x - 4;
		slopeY = y - 4;

		if (slopeX == 0 && slopeY == 0) {
			randSlopeV3();
		}
	}
}
