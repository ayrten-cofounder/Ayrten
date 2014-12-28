package com.ayrten.scrots.android;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ayrten.scrots.manager.ButtonInterface;
import com.ayrten.scrots.screens.GameScreen;

public class GameOverDialog extends Dialog {
	private TextView title;
	private Button yes;
	private Button no;

	private TextView highScore;
	private EditText name;
	private View divider;

	public GameOverDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.game_over_dialog);

		title = (TextView) findViewById(R.id.title);
		yes = (Button) findViewById(R.id.yes_button);
		no = (Button) findViewById(R.id.no_button);

		highScore = (TextView) findViewById(R.id.newHighScore);
		name = (EditText) findViewById(R.id.name);
		divider = (View) findViewById(R.id.divider);

		title.setText("Game Over!");
		yes.setText("Replay");
		no.setText("Main Menu");
		this.setCancelable(false);
		this.setCanceledOnTouchOutside(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void setDialog(final ButtonInterface yes_interface,
			final ButtonInterface no_interface) {
		highScore.setVisibility(View.GONE);
		name.setVisibility(View.GONE);
		divider.setVisibility(View.GONE);

		yes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (yes_interface != null) {
					yes_interface.buttonPressed();
				}
				dismiss();
			}
		});

		no.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (no_interface != null) {
					no_interface.buttonPressed();
				}
				dismiss();
			}
		});
	}

	public void setHighScoreDialog(final GameScreen gameScreen,
			final ButtonInterface yes_interface,
			final ButtonInterface no_interface) {
		highScore.setVisibility(View.VISIBLE);
		name.setVisibility(View.VISIBLE);
		divider.setVisibility(View.VISIBLE);

		name.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {

				if (actionId == EditorInfo.IME_ACTION_DONE) {
					v.clearComposingText();
					v.clearFocus();

					gameScreen.setHighScoreName(name.getText().toString());

					name.setText("");
					name.clearComposingText();
					name.clearFocus();

					highScore.setVisibility(View.GONE);
					name.setVisibility(View.GONE);
					divider.setVisibility(View.GONE);

					return true;
				}

				return false;
			}

		});

		yes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (yes_interface != null) {
					yes_interface.buttonPressed();
				}
				dismiss();
			}
		});

		no.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (no_interface != null) {
					no_interface.buttonPressed();
				}
				dismiss();
			}
		});
	}
}
