package com.ayrten.scrots.dots.regular;

import com.ayrten.scrots.common.Assets;
import com.ayrten.scrots.dots.MovingDot;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class RegDot1 extends MovingDot {
	public RegDot1(Texture dot, Manager gm, Sound pop) {
		super(dot, gm, pop);
	}
	
	@Override
	public void touchedByAnAngel(InputEvent event) {
		super.touchedByAnAngel(event);
		gm.minusGreenDot();
		Assets.stats_manager.getPlayerStats().reg_dot_1.popped++;
		Assets.gplay_manager.unlockDotAchievement(Assets.stats_manager
				.getPlayerStats().reg_dot_1.popped);
		gm.curr_level.getRegDotList().remove(this);
		incrementCombo();
	}
	
	protected void incrementCombo() {
		if(isComboDot)
			gm.incrementCombo();
		else {
			Assets.points_manager.addPoints(1);
			gm.resetCombo();
		}
	}
}
