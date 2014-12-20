package com.ayrten.scrots.android;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.ayrten.scrots.manager.ButtonInterface;

public class YesNoDialog extends Dialog {
	private TextView title;
	private Button yes;
	private Button no;

	public YesNoDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog);

		title = (TextView) findViewById(R.id.title);
		yes = (Button) findViewById(R.id.yes_button);
		no = (Button) findViewById(R.id.no_button);

		yes.setText("Yes");
		no.setText("No");
		this.setCancelable(false);
		this.setCanceledOnTouchOutside(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void setDialog(String title, final ButtonInterface yes_interface,
			final ButtonInterface no_interface) {
		this.title.setText(title);

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

	public void setDialogWithButtonNames(String title, String yes_button, String no_button,
			final ButtonInterface yes_interface, final ButtonInterface no_interface)
	{
		this.title.setText(title);
		this.yes.setText(yes_button);
		this.no.setText(no_button);

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
