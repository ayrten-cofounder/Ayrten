package com.ayrten.scrots.dots.power;

import java.util.LinkedList;

import com.ayrten.scrots.dots.MovingDot;
import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class PowerDot_Magnet extends PowerDot {
	private static final float DURATION = 2f; // Time in seconds it takes the
												// dots to move to the magnet

	private PowerDot_Magnet magnet;

	public PowerDot_Magnet(Texture dot, final Manager gm, Sound pop) {
		super(dot, gm, pop);

		ACTIVE_TIME = 8;
	}
	
	@Override
	protected void initializeAssets() {
		num = Assets.power_dot_manager.getMagnetDots();
		gray_dot_image = new Image(Assets.magnet_dot_gray);
	}
	
	@Override
	public void beforeAction() {
		super.beforeAction();

		Assets.power_dot_manager.setMagnetDotAmount(--num);
		updateNumLabel();

		magnet = gm.generator.genPowerDotMagnet();
		magnet.setTouchable(Touchable.disabled);
		gm.addPersistentDot(magnet);
		gm.setMagnetState(true);
		gm.getStage().addActor(magnet);
		magnet();
		Assets.stats_manager.getPlayerStats().power_dot_magnet.popped++;
	}

	@Override
	public void afterAction() {
		super.afterAction();

		gm.removePersistentDot(magnet);
		magnet.remove();
		gm.setMagnetState(false);
		unmagnet();
	}

	public void magnet() {
		checkRadius(gm.curr_level.getDotList());
	}

	private void checkRadius(LinkedList<MovingDot> linkedList) {
		for (MovingDot dot : linkedList) {
			if (dot.magneted) {
				dot.setPosition(getX(), getY());
			}

			if (gm.isPenDot(dot)) {
				dot.addAction(Actions.moveTo(magnet.getX(), magnet.getY(),
						DURATION, Interpolation.circleIn));
				dot.magneted = true;
			}
		}
	}

	private void unmagnet() {
		for (MovingDot dot : gm.curr_level.getDotList()) {			
			if (gm.isPenDot(dot))
				dot.magneted = false;
		}
	}

	@Override
	public boolean isUnlocked() {
		return (Assets.power_dot_manager.isMagnetDotUnlocked());
	}
}