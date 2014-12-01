package com.ayrten.scrots.screens;

import com.ayrten.scrots.dots.Dot;
import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class TutorialScreen extends ScrotsScreen
{	
	public TutorialScreen(Screen bscreen)
	{
		super(bscreen, false);
		createBackLabel();
		
		Table table = new Table(Assets.skin);
		table.setFillParent(true);
		table.left().top();
		table.padLeft(back.getWidth()/5).padRight(back.getWidth()/5);
		
		Label intent = new Label("", Assets.style_font_32_orange);
		intent.setWrap(true);
		intent.setText("The goal of the game is pop all the greens on screen as fast"
				+ " as possible! When all green dots are popped, you gain a point and"
				+ " the difficulty level increases. Below are the type of dots you'll"
				+ " be encountering:");
		
		Dot greenDot = new Dot(Assets.greenDot);
		Dot redDot = new Dot(Assets.redDot);
		Dot blueDot = new Dot(Assets.blueDot);
		Dot babyBlueDot = new Dot(Assets.babyBlueDot);
		
		Table innerTable = new Table(Assets.skin);
		innerTable.add(intent);
//		innerTable.add(greenDot);
//		innerTable.row();
//		innerTable.add(redDot);
//		innerTable.row();
//		innerTable.add(blueDot);
//		innerTable.row();
//		innerTable.add(babyBlueDot);
		
		table.add(back).left();
		table.row();
		table.add(intent).width(Gdx.graphics.getWidth() - back.getWidth()/5);
		table.row();
		table.add("").height(back.getStyle().font.getLineHeight());
		table.row();
		table.add(innerTable);
		
		setupStage();
		stage.addActor(table);
	}
}
