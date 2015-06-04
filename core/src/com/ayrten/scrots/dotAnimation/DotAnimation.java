package com.ayrten.scrots.dotAnimation;

import java.util.Random;

import com.ayrten.scrots.dots.MovingDot;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

public class DotAnimation {
	protected static final float MAX_SIZE_RATIO = 15;
	protected static final float MIN_SIZE_RATIO = 7.5f;
	protected static final int SLOPE_RAND_X = 9;
	protected static final int SLOPE_RAND_Y = 9;
	protected static final int SLOPE_DIFF_X = SLOPE_RAND_X / 2;
	protected static final int SLOPE_DIFF_Y = SLOPE_RAND_Y / 2;

	protected Random random;

	public DotAnimation() {
		random = new Random();
	}

	public void move(MovingDot dot) {
	}

	// Left/Right
	protected void move_v1() {
	}

	// Up/Down/left/right
	protected void move_v2() {
	}

	// Diagional
	protected void move_v3(MovingDot dot) {
		float x = dot.getX();
		float y = dot.getY();
		
		float minX = dot.gm.min_width;
		float maxX = dot.gm.max_width;
		float maxY = dot.gm.max_height;

		if (dot.slopeX >= 0f && dot.slopeY >= 0f) {
			if (dot.slopeX + x + dot.getWidth() < maxX
					&& dot.slopeY + y + dot.getHeight() < maxY) {
				dot.setPosition(x + dot.slopeX, y + dot.slopeY);
			} else {
				randSlopeV3(dot);
			}
		} else if (dot.slopeX <= 0f && dot.slopeY <= 0f) {
			if (dot.slopeX + x > minX && dot.slopeY + y > 0) {
				dot.setPosition(x + dot.slopeX, y + dot.slopeY);
			} else {
				randSlopeV3(dot);
			}
		} else if (dot.slopeX <= 0f && dot.slopeY >= 0f) {
			if (dot.slopeX + x > minX && dot.slopeY + y + dot.getHeight() < maxY) {
				dot.setPosition(x + dot.slopeX, y + dot.slopeY);
			} else {
				randSlopeV3(dot);
			}
		} else if (dot.slopeX >= 0f && dot.slopeY <= 0f) {
			if (dot.slopeX + x + dot.getWidth() < maxX && dot.slopeY + y > 0) {
				dot.setPosition(x + dot.slopeX, y + dot.slopeY);
			} else {
				randSlopeV3(dot);
			}
		} else {
			dot.slopeX = 1;
			dot.slopeY = 1;
		}
	}

	protected void randSlopeV2() {
	}

	public void randSlopeV3(MovingDot dot) {
		int x = random.nextInt(SLOPE_RAND_X);
		int y = random.nextInt(SLOPE_RAND_Y);
		
		dot.slopeX = (x - SLOPE_DIFF_X);
		dot.slopeY = (y - SLOPE_DIFF_Y);
		
		dot.prevSlopeX = dot.slopeX;
		dot.prevSlopeY = dot.slopeY;
		
		dot.slopeX *= dot.speedX;
		dot.slopeY *= dot.speedY;

		if (dot.slopeX == 0 && dot.slopeY == 0) {
			randSlopeV3(dot);
		}
	}
	
	public void resetRatio(MovingDot dot) {
		dot.sizeRatio = random.nextBoolean() ? MAX_SIZE_RATIO : MIN_SIZE_RATIO;
	}

	public void setSize(MovingDot dot) {
		dot.setWidth(getCircleRadius(dot));
		dot.setHeight(dot.getWidth());
		dot.setBounds(dot.getX(), dot.getY(), dot.getWidth(), dot.getHeight());
	}

	private float getCircleRadius(MovingDot dot) {
		return (float) (Gdx.graphics.getWidth() / dot.sizeRatio);
	}

	public void changePosition(MovingDot dot) {
		move_v3(dot);
	}
	
	public void animateV1(MovingDot dot, Batch batch, float alpha) {
		changePositionAndSize(dot, batch, alpha);
		batch.draw(dot.getTexture(), dot.getX(), dot.getY(), dot.getWidth(), dot.getHeight());
	}
	
	public void animateV2(MovingDot dot, Batch batch, float alpha, int srcX, int srcY,int srcWidth, int srcHeight) {
		changePositionAndSize(dot, batch, alpha);
		batch.draw(dot.getTexture(), dot.getX(), dot.getY(), dot.getOriginX(), dot.getOriginY(), 
				dot.getWidth(), dot.getHeight(), dot.getScaleX(), dot.getScaleY(), dot.getRotation(), 
				srcX, srcY, srcWidth, srcHeight, false, false);
	}
	
	private void changePositionAndSize(MovingDot dot, Batch batch, float alpha) {
		if (!dot.magneted) {
			changePosition(dot);
		}
		
		setSize(dot);
	}
}
