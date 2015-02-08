package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class TutorialScreen extends ScrotsScreen
{	
	public TutorialScreen(Screen bscreen)
	{
		super(bscreen, false);
		createBackLabel();
		
		Table table = new Table(Assets.skin);
		table.padLeft(back.getWidth()/5).padRight(back.getWidth()/5);
		
		Label intent = new Label("", Assets.style_font_32_orange);
		intent.setWrap(true);
		intent.setText("The goal of the game is pop all the greens on screen as fast"
				+ " as you can! When all green dots are popped, you gain a point and"
				+ " the difficulty level increases. If time runs out or you popped a red"
				+ " dot, then GAME OVER! Below are the type of dots you'll be encountering:");
				
		Image greenDot = new Image(Assets.regDot_1);
		Image redDot = new Image(Assets.penDot_1);
		Image blueDot = new Image(Assets.penDot_2);
		Image babyBlueDot = new Image(Assets.regDot_2);
		Image questionDot = new Image(Assets.question_mark);
		
		Label greenDesc = new Label("", Assets.style_font_32_orange);
		greenDesc.setWrap(true);
		greenDesc.setText("Requires popping in order to advance");
		
		Label redDesc = new Label("", Assets.style_font_32_orange);
		redDesc.setWrap(true);
		redDesc.setText("Immediate GAME OVER!");
		
		Label blueDesc = new Label("", Assets.style_font_32_orange);
		blueDesc.setWrap(true);
		blueDesc.setText("Decreases time limit by 5 seconds so BE CAREFUL!");
		
		Label babyBlueDesc = new Label("", Assets.style_font_32_orange);
		babyBlueDesc.setWrap(true);
		babyBlueDesc.setText("Increased time limit by 2.5 seconds");
		
		Label questionDesc = new Label("", Assets.style_font_32_orange);
		questionDesc.setWrap(true);
		questionDesc.setText("More to come!");
		
		Table innerTable = new Table(Assets.skin);
		innerTable.left().top();
		innerTable.add(greenDot).width(greenDot.getWidth()).height(greenDot.getHeight());
		innerTable.add(greenDesc).width(Gdx.graphics.getWidth()/2 - back.getWidth()/5 * 2).padLeft(back.getWidth()/5);
		innerTable.row();
		innerTable.add("").height(back.getStyle().font.getLineHeight());
		innerTable.row();
		innerTable.add(redDot).width(redDot.getWidth()).height(redDot.getHeight());
		innerTable.add(redDesc).width(Gdx.graphics.getWidth()/2 - back.getWidth()/5 * 2).padLeft(back.getWidth()/5);
		innerTable.row();
		innerTable.add("").height(back.getStyle().font.getLineHeight());
		innerTable.row();
		innerTable.add(blueDot).width(blueDot.getWidth()).height(blueDot.getHeight());
		innerTable.add(blueDesc).width(Gdx.graphics.getWidth()/2 - back.getWidth()/5 * 2).padLeft(back.getWidth()/5);
		innerTable.row();
		innerTable.add("").height(back.getStyle().font.getLineHeight());
		innerTable.row();
		innerTable.add(babyBlueDot).width(babyBlueDot.getWidth()).height(babyBlueDot.getHeight());
		innerTable.add(babyBlueDesc).width(Gdx.graphics.getWidth()/2 - back.getWidth()/5 * 2).padLeft(back.getWidth()/5);
		innerTable.row();
		innerTable.add("").height(back.getStyle().font.getLineHeight());
		innerTable.row();
		innerTable.add(questionDot).width(questionDot.getWidth()).height(questionDot.getHeight());
		innerTable.add(questionDesc).width(Gdx.graphics.getWidth()/2 - back.getWidth()/5 * 2).padLeft(back.getWidth()/5);
		
		table.add(back).left();
		table.row();
		table.add(intent).width(Gdx.graphics.getWidth() - back.getWidth()/5);
		table.row();
		table.add("").height(back.getStyle().font.getLineHeight());
		table.row();
		table.add(innerTable);
		table.row();
		table.add("").height(intent.getStyle().font.getLineHeight());
		
		ScrollPane scroll_view = new ScrollPane(table);
		scroll_view.setFillParent(true);
		scroll_view.setScrollingDisabled(true, false);
		
		setupStage();
		stage.addActor(scroll_view);
	}
	
	@Override
	public void hide() {
		super.hide();
		if(Assets.prefs.getBoolean("first_time", true)) {
			Assets.prefs.putBoolean("first_time", false);
			Assets.prefs.flush();
		}
	}
}
