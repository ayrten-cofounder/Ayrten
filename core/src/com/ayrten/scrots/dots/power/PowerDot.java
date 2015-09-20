package com.ayrten.scrots.dots.power;

import com.ayrten.scrots.common.Assets;
import com.ayrten.scrots.dots.Dot;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class PowerDot extends Dot {
	private float DELAY = (float) 0.05; // seconds
	private static final float ADDER = (float) 0.005; // seconds
	protected float ACTIVE_TIME = 0; // seconds

	private Label num_label;
	private Label time_label;
	private Timer timer;
	public float time;

	protected int num = 0; // The amount of power dots the user has
	protected float origX, origY;

	protected Image rs_image;
	protected SpriteBatch batch;
	protected float angle;
	protected Image gray_dot_image;
	// Level of the power dot.
	protected int level;

	public PowerDot(Texture dot, Manager gm, Sound pop) {
		super(dot, gm, pop);
		timer = new Timer();
		angle = 0;
		batch = new SpriteBatch();
		
		LabelStyle dot_count_style = new LabelStyle();
		dot_count_style.font = Assets.font_20;
		dot_count_style.fontColor = Color.WHITE;
		num_label = new Label("", dot_count_style);
		
		initializeAssets();
		updateNumLabel();
		
		TextureRegion tr = new TextureRegion(Assets.timer_ring);
		RadialSprite rs = new RadialSprite(tr);
		rs_image = new Image(rs);
		rs_image.setVisible(false);
		if(isUnlocked())
			gray_dot_image.setVisible(false);
		else
			setVisible(false);
		time_label = new Label("0", Assets.style_font_32_white);
		time_label.setVisible(false);
	}
	
	protected void initializeAssets() {}
	
	public Image getRadialTimer() {
		return rs_image;
	}
	
	public Label getTimeLabel() {
		return time_label;
	}
	
	public Label getNumLabel() {
		return num_label;
	}
	
	public Image getGrayImage() {
		return gray_dot_image;
	}
	
	@Override
	protected void initializeListener() {
		listener = new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				origX = event.getTarget().getX(Align.center);
				origY = event.getTarget().getY(Align.center);
				if(Gdx.input.getX(pointer) > origX && num > 0)
				  event.getTarget().setPosition(Gdx.input.getX(pointer), event.getTarget().getY(Align.center), Align.center);
				return true;
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer) {
				if(Gdx.input.getX(pointer) > origX && num > 0)
					event.getTarget().setPosition(Gdx.input.getX(pointer), event.getTarget().getY(Align.center), Align.center);
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				// If x position exceeds a threshold, then activate the effect.
				if (Math.abs(origX - event.getTarget().getX(Align.center)) > Assets.powerdot_thresh) 
					touchedByAnAngel(event);
				event.getTarget().setPosition(origX, origY, Align.center);
			}
		};

		addListener(listener);
	}

	public void updateNumLabel() {
		num_label.setText("x" + String.valueOf(num));
	}
	
	public boolean isUnlocked() {
		Gdx.app.error("POWERDOT", this.getClass().toString() + " did not override isUnlocked() function!");
		Gdx.app.exit();
		return false;
	}

	@Override
	public void touchedByAnAngel(InputEvent event) {
		super.touchedByAnAngel(event);

		beforeAction();

		time = ACTIVE_TIME;
		setTime();
		time_label.setVisible(true);
		startTime();
	}

	// Action to do before timer starts.
	public void beforeAction() {
		rs_image.setVisible(true);
		gray_dot_image.setVisible(true);
		setVisible(false);
	}

	// Action to do during the timer
	public void duringAction() {
		angle = 360 * (1 - time/ACTIVE_TIME);
		batch.begin();
		((RadialSprite) rs_image.getDrawable()).draw(batch, rs_image.getX(), rs_image.getY(), angle);
		batch.end();
	}

	// Action to do after timer ends
	public void afterAction() {
		angle = 0;
		rs_image.setVisible(false);
		gray_dot_image.setVisible(false);
		setVisible(true);
	}

	public void startTime() {
		timer.scheduleTask(new Task() {
			@Override
			public void run() {
				float subtract = DELAY + ADDER;
				time = time - subtract;
				setTime();
				duringAction();

				if (time <= 0) {
					time_label.setVisible(false);
					afterAction();
					return;
				}

				startTime();
			}
		}, DELAY);
	}

	public void pauseTime() {
		timer.stop();
	}

	public void resumeTime() {
		timer.start();
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		batch.draw(this.getTexture(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}

	private void setTime() {
		if (time < 10) {
			time_label.setText(String.valueOf(time).substring(0, 3));
		} else {
			time_label.setText(String.valueOf(time).substring(0, 2));
		}
	}
}
