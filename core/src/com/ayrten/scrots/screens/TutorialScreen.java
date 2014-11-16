package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class TutorialScreen extends ScrotsScreen
{	
	public TutorialScreen(Screen bscreen)
	{
		super(bscreen, true);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = Assets.font_64;
		labelStyle.fontColor = Assets.ORANGE;
		
		setupStage();
	}
}
