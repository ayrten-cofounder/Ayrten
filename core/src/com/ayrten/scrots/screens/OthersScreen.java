package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class OthersScreen extends ScrotsScreen
{
	// Screens
	protected TutorialScreen 	tutorial_screen;
	protected CreditsScreen		credits_screen;
	
	public OthersScreen(Screen bscreen)
	{
		super(bscreen, true);
		
		tutorial_screen = new TutorialScreen(this);
		credits_screen  = new CreditsScreen(this);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = Assets.font_64;
		labelStyle.fontColor = Assets.ORANGE;
		
		Label achievements = new Label("Achievements", labelStyle);
		achievements.setBounds(achievements.getX(), achievements.getY(), achievements.getWidth(), achievements.getHeight());
		achievements.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.game.apk_intf.showAchievements();
				if(Assets.prefs.getBoolean("sound_effs", true))
					Assets.button_pop.play();
			}
		});
		
//		Label leaderboard = new Label("Leaderboard", labelStyle);
//		leaderboard.setBounds(leaderboard.getX(), leaderboard.getY(), leaderboard.getWidth(), leaderboard.getHeight());
//		leaderboard.addListener(new ClickListener(){
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				Assets.game.apk_intf.showLeadershipBoard();
//				if(Assets.prefs.getBoolean("sound_effs", true))
//					Assets.button_pop.play();
//			}
//		});
		
		Label tutorial = new Label("Tutorial", labelStyle);
		tutorial.setBounds(tutorial.getX(), tutorial.getY(), tutorial.getWidth(), tutorial.getHeight());
		tutorial.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setActorsTouchable(Touchable.disabled);
				if(Assets.prefs.getBoolean("sound_effs", true))
					Assets.button_pop.play();
				tutorial_screen.setBackScreen(Assets.game.getScreen());
				Assets.game.setScreen(tutorial_screen);
			}
		});
		
		Label credits = new Label("Credits", labelStyle);
		credits.setBounds(credits.getX(), credits.getY(), credits.getWidth(), credits.getHeight());
		credits.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setActorsTouchable(Touchable.disabled);
				if(Assets.prefs.getBoolean("sound_effs", true))
					Assets.button_pop.play();
				Assets.game.setScreen(credits_screen);
			}
		});
		
		Table table = new Table(Assets.skin);
		table.setFillParent(true);
		table.add(achievements);
		table.row();
//		table.add(leaderboard);
//		table.row();
		table.add(tutorial);
		table.row();
		table.add(credits);
		
		setupStage();
		stage.addActor(table);
	}
}
