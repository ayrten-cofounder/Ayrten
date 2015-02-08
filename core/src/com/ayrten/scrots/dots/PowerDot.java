package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class PowerDot extends Dot 
{
	private float DELAY = (float) 0.05; // seconds
	private static final float ADDER = (float) 0.005; // seconds
	protected float ACTIVE_TIME = 0; // seconds

	private Label num_label;
	private Label time_label;
	private Timer timer;
	public float time;
	
	protected int num = 0; // The amount of power dots the user has

	protected InputListener powerdot_listener;

	public PowerDot(Texture dot, Manager gm, Sound pop) {
		super(dot, gm, pop);

		timer = new Timer();
		powerdot_listener = new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				touchedByAnAngel();
			}
		};

		removeListener(listener);
		addListener(powerdot_listener);
	}

	public void setTimeLabel(Label label) {
		time_label = label;
		time_label.setVisible(false);
	}

	public void setNumLabel(Label label) {
		num_label = label;
		num_label.setText(String.valueOf(num));
	}

	public void updateNumLabel() {
		num_label.setText(String.valueOf(num));
	}
	
	@Override
	public void touchedByAnAngel() {
		super.touchedByAnAngel();

		beforeAction();

		time = ACTIVE_TIME;
		setTime();
		time_label.setVisible(true);
		startTime();
	}

	// Action to do before timer starts.
	public void beforeAction() {
	
	}

	// Action to do during the timer
	public void duringAction() {

	}

	// Action to do after timer ends
	public void afterAction() {

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
					System.out.println("TIMER REACHED ZERO FOR : " + getClass());
					return;
				}

				startTime();
			}
		}, DELAY);
	}
	
	public void pauseTime()
	{
		timer.stop();
	}
	
	public void resumeTime()
	{
		timer.start();
	}

	private void setTime() {
		if (time < 10) {
			time_label.setText(String.valueOf(time).substring(0, 3));
		} else {
			time_label.setText(String.valueOf(time).substring(0, 2));
		}
	}
}
