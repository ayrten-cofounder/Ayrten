package com.ayrten.scrots.dotAnimation;

import java.util.Random;

import com.ayrten.scrots.dots.Dot;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

public class DotAnimation {
	private static final float MAX_SIZE_RATIO = 15; // No use really...
	private static final float MIN_SIZE_RATIO = 7.5f;
	protected static final int SLOPE_RAND_X = 9;
	protected static final int SLOPE_RAND_Y = 9;
	protected static final int SLOPE_DIFF_X = SLOPE_RAND_X / 2;
	protected static final int SLOPE_DIFF_Y = SLOPE_RAND_Y / 2;

	protected float size_ratio = MIN_SIZE_RATIO;
	protected float slopeX = 1; // slopeX and slopeY makes the slope
	protected float slopeY = 1;
	protected float curr_width;
	protected float curr_height;

	protected Dot dot;
	protected Random random;

	public DotAnimation(Dot dot) {
		this.dot = dot;
		random = new Random(System.nanoTime());
		randSlopeV3();
	}

	public void move() {
	}

	// Left/Right
	protected void move_v1() {
	}

	// Up/Down/left/right
	protected void move_v2() {
	}

	// Diagional
	protected void move_v3() {
		float x = dot.getX();
		float y = dot.getY();
		
		float minX = dot.gm.min_width;
		float maxX = dot.gm.max_width;
		float maxY = dot.gm.max_height;

		if (slopeX >= 0f && slopeY >= 0f) {
			if (slopeX + x + curr_width < maxX
					&& slopeY + y + curr_height < maxY) {
				dot.setPosition(x + slopeX, y + slopeY);
			} else {
				randSlopeV3();
			}
		} else if (slopeX <= 0f && slopeY <= 0f) {
			if (slopeX + x > minX && slopeY + y > 0) {
				dot.setPosition(x + slopeX, y + slopeY);
			} else {
				randSlopeV3();
			}
		} else if (slopeX <= 0f && slopeY >= 0f) {
			if (slopeX + x > minX && slopeY + y + curr_height < maxY) {
				dot.setPosition(x + slopeX, y + slopeY);
			} else {
				randSlopeV3();
			}
		} else if (slopeX >= 0f && slopeY <= 0f) {
			if (slopeX + x + curr_width < maxX && slopeY + y > 0) {
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
	}

	protected void randSlopeV3() {
		int x = random.nextInt(SLOPE_RAND_X);
		int y = random.nextInt(SLOPE_RAND_Y);

		slopeX = x - SLOPE_DIFF_X;
		slopeY = y - SLOPE_DIFF_Y;

		if (slopeX == 0 && slopeY == 0) {
			randSlopeV3();
		}
	}

	public void resetRatio() {
		size_ratio = random.nextBoolean() ? MAX_SIZE_RATIO : MIN_SIZE_RATIO;
	}

	public void setSize() {
		curr_width = getCircleWidth();
		curr_height = getCircleHeight();
	}

	private float getCircleWidth() {
		return (float) (Gdx.graphics.getWidth() / size_ratio);
	}

	private float getCircleHeight() {
		return (float) ((getCircleWidth() * dot.getHeight()) / dot.getWidth());
	}

	public void changePosition() {
		move_v3();
	}
	
	public void draw(Batch batch, float alpha, boolean magnetized) {
		if (!magnetized) {
			changePosition();
		}
		
		setSize();
		dot.setBounds(dot.getX(), dot.getY(), curr_width, curr_height);
		batch.draw(dot.getTexture(), dot.getX(), dot.getY(), curr_width, curr_height);
	}
}
