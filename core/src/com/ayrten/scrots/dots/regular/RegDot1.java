package com.ayrten.scrots.dots.regular;

import com.ayrten.scrots.dots.Dot;
import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class RegDot1 extends Dot {
	public RegDot1(Texture dot, Manager gm, Sound pop) {
		super(dot, gm, pop);
	}

	@Override
	public void touchedByAnAngel() {
		super.touchedByAnAngel();

		// Then Adds a point
		gm.minusGreenDot();
		Assets.points_manager.addPoints(1);
		Assets.stats_manager.getPlayerStats().reg_dot_1.popped++;
		Assets.gplay_manager.unlockDotAchievement(Assets.stats_manager
				.getPlayerStats().reg_dot_1.popped);
		gm.curr_level.getRegDotList().remove(this);
		if(isComboDot)
			gm.incrementCombo();
		else
			gm.resetCombo();
	}
}
