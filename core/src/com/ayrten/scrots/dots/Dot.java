package com.ayrten.scrots.dots;

import com.ayrten.scrots.common.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;

public class Dot extends Stack {

	protected Texture dot_texture;
	public Manager gm;

	protected Sound pop;
	protected InputListener listener;

	public Dot(Texture dot, Manager gm, Sound pop) {
		this.dot_texture = dot;
		this.gm = gm;
		this.pop = pop;
		this.setTouchable(Touchable.enabled);
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
	
	public void setTexture(Texture dot_texture) {
		this.dot_texture = dot_texture;
	}

	public Texture getTexture() {
		return dot_texture;
	}
}
