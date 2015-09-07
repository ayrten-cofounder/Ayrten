package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameModeScreen extends ScrotsScreen 
{
	public static GameScreen game_screen;

	protected Label timeMode;
	protected Label survivalMode;
	
	public GameModeScreen (Screen backScreen) {
		super(backScreen, true);
		setupStage();
		showTableScreen();
		
		Label intro = new Label("Pick Your Mode of Gameplay", Assets.style_font_64_white);
		
		timeMode = new Label("Time Mode", Assets.style_font_64_white);
		timeMode.setBounds(timeMode.getX(), timeMode.getY(), timeMode.getWidth(), timeMode.getHeight());
		timeMode.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (Assets.prefs.getBoolean("sound_effs", true))
					Assets.button_pop.play();
				if (Assets.prefs.getBoolean("first_time", true))
					loadTutorialScreen();
				else {
					game_screen = new GameScreen();
					Assets.playGameBGM();
					Assets.game.setScreen(game_screen);
				}
			}
		});
		
		survivalMode = new Label("Survival Mode - Coming Soon!", Assets.style_font_64_orange);
		survivalMode.setBounds(survivalMode.getX(), survivalMode.getY(), survivalMode.getWidth(), survivalMode.getHeight());
		survivalMode.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
			}
		});
		
		table.add(intro);
		table.row();
		table.add().height(Assets.style_font_64_white.font.getLineHeight());
		table.row();
		table.add(timeMode);
		table.row();
		table.add(survivalMode);
	}
	
	@Override
	public void addActors() {
		super.addActors();
		actors.add(timeMode);
		actors.add(survivalMode);
		
	}
	
	private void loadTutorialScreen() {
		Image tutorial_1 = new Image(new Texture(
				Gdx.files.internal("data/tutorial_1.png")));
		Image tutorial_2 = new Image(new Texture(
				Gdx.files.internal("data/tutorial_2.png")));

		Table top_table = new Table(Assets.skin);
		top_table.setWidth(Assets.width * 2);

		Table page_one = new Table(Assets.skin);
		page_one.setHeight(MessageScreen.WINDOW_DISPLAY_HEIGHT);
		page_one.setWidth(Assets.width);
		page_one.add(tutorial_1).width(Assets.width)
				.height(MessageScreen.WINDOW_DISPLAY_HEIGHT);

		Table page_two = new Table(Assets.skin);
		page_two.setHeight(MessageScreen.WINDOW_DISPLAY_HEIGHT);
		page_two.setWidth(Assets.width);
		page_two.add(tutorial_2).width(Assets.width)
				.height(MessageScreen.WINDOW_DISPLAY_HEIGHT);

		top_table.add(page_one).width(Assets.width)
				.height(MessageScreen.WINDOW_DISPLAY_HEIGHT);
		top_table.add(page_two).width(Assets.width)
				.height(MessageScreen.WINDOW_DISPLAY_HEIGHT);

		// Use the slideshow type MessageScreen.
		MessageScreen tutorial_screen = new MessageScreen(top_table, 2) {
			@Override
			public void transition() {
				super.transition();
				Assets.prefs.putBoolean("first_time", false);
				Assets.prefs.flush();
				game_screen = new GameScreen();
				Assets.playGameBGM();
				Assets.game.setScreen(game_screen);
			}
		};
		tutorial_screen.setBackgroundColor(150, 66, 66);
		Assets.game.setScreen(tutorial_screen);
	}

}
