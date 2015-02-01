package com.ayrten.scrots.dots;

import java.util.ArrayList;
import java.util.LinkedList;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class PowerDot_Magnet extends PowerDot {
	private Circle circle;

	private boolean flag = false;

	public PowerDot_Magnet(Texture dot, final Manager gm, Sound pop) {
		super(dot, gm, pop);

		ACTIVE_TIME = 15;
		circle = new Circle();

		removeListener(powerdot_listener);

		powerdot_listener = new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				touchedByAnAngel();
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				time = 0;
				remove();
				unmagnet();
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				setCenterPosition(Gdx.input.getX(pointer), Assets.height
						- Gdx.input.getY(pointer));
			}
		};
		addListener(powerdot_listener);
	}

	@Override
	public void beforeAction() {
		super.beforeAction();

		circle.set(getCenterX(), getCenterY(), getWidth());
		checkRadius(gm.curr_level.get_all_dots());
	}

	@Override
	public void duringAction() {
		super.duringAction();
		checkRadius(gm.curr_level.get_all_dots());
	}

	@Override
	public void afterAction() {
		super.afterAction();

		remove();
		unmagnet();
	}

	private void checkRadius(LinkedList<ArrayList<Dot>> linkedList) {
		if (flag) {
			return;
		}

		flag = true;
		for (ArrayList<Dot> dotList : linkedList) {
			for (Dot dot : dotList) {
				if(dot.magneted)
				{
					dot.setPosition(getX(), getY());
				}
				
				if (circle.contains(dot.getCenterX() + dot.getWidth(),
						dot.getCenterY())
						|| circle.contains(dot.getCenterX() - dot.getWidth(),
								dot.getCenterY())
						|| circle.contains(dot.getCenterX(), dot.getCenterY()
								+ dot.getWidth())
						|| circle.contains(dot.getCenterX(), dot.getCenterY()
								- dot.getWidth())
						|| circle.contains(dot.getCenterX() + 2.414f,
								dot.getCenterY() + 2.414f)
						|| circle.contains(dot.getCenterX() - 2.414f,
								dot.getCenterY() + 2.414f)
						|| circle.contains(dot.getCenterX() + 2.414f,
								dot.getCenterY() - 2.414f)
						|| circle.contains(dot.getCenterX() - 2.414f,
								dot.getCenterY() - 2.414f)) {

					if (dot.getClass() == PenDot1.class
							|| dot.getClass() == PenDot2.class
							|| dot.getClass() == DWD_PenDot1.class
							|| dot.getClass() == DWD_PenDot2.class) {
						dot.setPosition(getX(), getY());
						dot.magneted = true;
					}
				}
			}
		}
		flag = false;
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