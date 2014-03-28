/**
 * Created by Andy Yeung & Tony Doan
 */

package com.ayrten.scrots;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuScreen extends Activity
{
	private Button	play;
	private Button	options;
	private Button	highscore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_screen);
		
		play = (Button) findViewById(R.id.play);
		options = (Button) findViewById(R.id.options);
		highscore = (Button) findViewById(R.id.highscore);
		
		setPlayButton();
	}
	
	private void setPlayButton()
	{
		play.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(MenuScreen.this, Game.class));
			}
		});
	}
}
