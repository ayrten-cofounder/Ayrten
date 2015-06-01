package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Dot extends Actor {
	protected Texture dot;
	public Manager gm;

	protected Sound pop;
	protected InputListener listener;

	public Dot(Texture dot, Manager gm, Sound pop) {
		this.dot = dot;
		this.gm = gm;
		this.pop = pop;
		initializeListener();
	}
	
	protected void initializeListener() {
		Gdx.app.exit();
	}

	// This class shall be overriddent by the real dots
	public void touchedByAnAngel(InputEvent event) {
		if (Assets.prefs.getBoolean("sound_effs", true))
			pop.play();
	}
	
	public void setTexture(Texture dot) {
		this.dot = dot;
	}

	public Texture getTexture() {
		return dot;
	}
}
