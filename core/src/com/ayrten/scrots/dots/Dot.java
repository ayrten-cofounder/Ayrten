package com.ayrten.scrots.dots;

import java.util.Random;

import com.ayrten.scrots.dotGraphics.DotGraphics;
import com.ayrten.scrots.dotGraphics.DotGraphics_MainMenuScreenBackground;
import com.ayrten.scrots.dotGraphics.DotGraphics_NormalGameMode;
import com.ayrten.scrots.game.GameMode;
import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.screens.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Dot extends Actor {
	public Texture dot;

	public Manager gm;
	public Random random;
	public DotGraphics graphics;

	public Sound pop;

	public Dot(Texture dot, Manager gm, Sound pop) {
		this.dot = dot;
		this.gm = gm;
		this.pop = pop;
		random = new Random(System.nanoTime());
		setBounds(getX(), getY(), dot.getWidth(), dot.getHeight());

		if (gm.get_game_mode() == GameMode.NORMAL_MODE
				|| gm.get_game_mode() == GameMode.CHALLENGE_MODE) {
			graphics = new DotGraphics_NormalGameMode(this);
		} else if (gm.get_game_mode() == GameMode.MAIN_MENU_BACKGROUND_MODE) {
			graphics = new DotGraphics_MainMenuScreenBackground(this);
		}

		// An InputListener is a subclass of EventListener
		addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {

				// Gotta get touched by an angel
				touchedByAnAngel();

				// Remove the actor from the stage.
				event.getTarget().remove();

				dotChange();
			}
		});
	}

	// This class shall be overriddent by the blue, green, red dots
	public void touchedByAnAngel() {
		if (Assets.prefs.getBoolean("sound_effs", true)) {
			pop.play();
		}
	}

	public void setTexture(Texture dot) {
		this.dot = dot;
	}

	public Texture getTexture() {
		return dot;
	}

	public void setPosition(float x, float y) {
		setX(x);
		setY(y);
	}

	public void changePosition() {
		graphics.move();
	}

	private void dotChange() {
		gm.changeDotSize();
	}

	public void resetRatio() {
		graphics.resetRatio();
	}

	// Overridden functions.
	@Override
	public void draw(Batch batch, float alpha) {
		graphics.draw(batch, alpha);
	}
}
