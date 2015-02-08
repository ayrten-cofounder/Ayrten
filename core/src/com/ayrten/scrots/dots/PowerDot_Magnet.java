package com.ayrten.scrots.dots;

import java.util.ArrayList;
import java.util.LinkedList;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class PowerDot_Magnet extends PowerDot {
	private static final float DURATION = 2f; // Time in seconds it takes the
												// dots to move to the magnet

	private DotGenerator generator;
	private Dot magnet;

	public PowerDot_Magnet(Texture dot, final Manager gm, Sound pop) {
		super(dot, gm, pop);

		ACTIVE_TIME = 8;
		generator = new DotGenerator(Assets.width, Assets.game_height, gm);
	}

	@Override
	public void beforeAction() {
		super.beforeAction();

		magnet = generator.genPowerDotMagnet();
		magnet.setTouchable(Touchable.disabled);
		gm.getStage().addActor(magnet);
		checkRadius(gm.curr_level.get_all_dots());
	}

	@Override
	public void afterAction() {
		super.afterAction();

		magnet.remove();
		unmagnet();
	}

	private void checkRadius(LinkedList<ArrayList<Dot>> linkedList) {
		for (ArrayList<Dot> dotList : linkedList) {
			for (Dot dot : dotList) {
				if (dot.magneted) {
					dot.setPosition(getX(), getY());
				}

				if (dot.getClass() == PenDot1.class
						|| dot.getClass() == PenDot2.class
						|| dot.getClass() == DWD_PenDot1.class
						|| dot.getClass() == DWD_PenDot2.class) {

					dot.addAction(Actions.moveTo(magnet.getX(),
							magnet.getY(), DURATION,
							Interpolation.circleIn));
					dot.magneted = true;
				}

			}
		}
	}

	private void unmagnet() {
		for (ArrayList<Dot> dotList : gm.curr_level.get_all_dots()) {
			for (Dot dot : dotList) {
				if (dot.getClass() == PenDot1.class
						|| dot.getClass() == PenDot2.class
						|| dot.getClass() == DWD_PenDot1.class
						|| dot.getClass() == DWD_PenDot2.class) {
					dot.magneted = false;
				}
			}
		}
	}

	@Override
	public void draw(Batch batch, float alpha) {
		batch.draw(dot, getX(), getY(), getWidth(), getHeight());
	}
}