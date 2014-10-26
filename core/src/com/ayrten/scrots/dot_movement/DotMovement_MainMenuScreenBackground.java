package com.ayrten.scrots.dot_movement;

import com.ayrten.scrots.dots.Dot;
import com.badlogic.gdx.Gdx;

public class DotMovement_MainMenuScreenBackground extends DotMovement{

	public DotMovement_MainMenuScreenBackground(Dot dot) {
		super(dot);
		randSlopeV3();
	}

	@Override
	public void move() {
		move_main_menu_screen_background();
	}
	
	public void move_main_menu_screen_background()
	{
		if (velocity_count == velocity_of_dot) {
			velocity_count = 0;
		} else {
			velocity_count++;
			return;
		}

		float x = dot.getX();
		float y = dot.getY();
		float maxX = Gdx.graphics.getWidth();
		float maxY = Gdx.graphics.getHeight();

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
}
