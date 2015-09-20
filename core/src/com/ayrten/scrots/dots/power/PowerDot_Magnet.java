package com.ayrten.scrots.dots.power;

import com.ayrten.scrots.common.Assets;
import com.ayrten.scrots.dots.MovingDot;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class PowerDot_Magnet extends PowerDot {
	private static final float DURATION = 2f; // Time in seconds it takes the
												// dots to move to the magnet

	public PowerDot_Magnet(Texture dot, final Manager gm, Sound pop) {
		super(dot, gm, pop);

		ACTIVE_TIME = 8;
	}
	
	@Override
	protected void initializeAssets() {
		num = Assets.power_dot_manager.getDotCount(this.getClass());
		gray_dot_image = new Image(Assets.magnet_dot_gray);
	}
	
	@Override
	public void beforeAction() {
		super.beforeAction();
		Assets.power_dot_manager.setDotCount(this.getClass(), (--num));
		updateNumLabel();

		gm.generator.setRandPositions(gm.getMagnet());
		gm.getMagnet().setVisible(true);
		gm.setMagnetState(true);
		magnetize();
		Assets.stats_manager.getPlayerStats().power_dot_magnet.popped++;
	}

	@Override
	public void afterAction() {
		super.afterAction();
		gm.getMagnet().setVisible(false);
		gm.setMagnetState(false);
		unmagnetize();
	}

	public void magnetize() {
		for (MovingDot dot : gm.curr_level.getDotList()) {
			if (dot.magneted) {
				dot.setPosition(getX(), getY());
			}

			if (gm.isPenDot(dot)) {
				dot.addAction(Actions.moveTo(gm.getMagnet().getX(), gm.getMagnet().getY(),
						DURATION, Interpolation.circleIn));
				dot.magneted = true;
			}
		}
	}

	private void unmagnetize() {
		for (MovingDot dot : gm.curr_level.getDotList()) {			
			if (gm.isPenDot(dot))
				dot.magneted = false;
		}
	}

	@Override
	public boolean isUnlocked() {
		return (Assets.power_dot_manager.isDotUnlocked(this.getClass()));
	}
	
}