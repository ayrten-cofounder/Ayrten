package com.ayrten.scrots.screens;

import java.util.ArrayList;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.ButtonInterface;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class MessageScreen {
	private Stage stage;
	
	private Table table;

	private Image background_overlay;
	private Label message;
	private Label negative_button;
	private Label positive_button;

	public MessageScreen(Stage stage) {
		this.stage = stage;
		
		table = new Table();
		table.setFillParent(true);
		table.setSkin(Assets.skin);

		background_overlay = new Image(Assets.transparent_background);
		background_overlay.setBounds(0, 0, Assets.width, Assets.height);
	}

	public void makeWindow(String title, String positive_title, String negative_title,
			final ButtonInterface yes_interface,
			final ButtonInterface no_interface) {
		message = new Label(title, Assets.style_font_64_white);
		message.setPosition((Assets.width / 2) - (message.getWidth() / 2),
				Assets.height / 2);
		message.setAlignment(Align.center);
		message.setWrap(true);

		negative_button = new Label(negative_title, Assets.style_font_64_red);
		negative_button.setPosition(Assets.width / 5, (Assets.height / 2)
				- message.getHeight());
		negative_button.setAlignment(Align.center);

		positive_button = new Label(positive_title, Assets.style_font_64_orange);
		positive_button.setPosition(Assets.width / 5 * 4, (Assets.height / 2)
				- message.getHeight());
		positive_button.setAlignment(Align.center);

		negative_button.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				removeActors();
				no_interface.buttonPressed();
			}
		});
		
		positive_button.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				removeActors();
				yes_interface.buttonPressed();
			}
		});
		
		Table newTable = new Table(Assets.skin);
		ArrayList<Actor> actor_list = new ArrayList<Actor>();
		actor_list.add(positive_button);
		actor_list.add(negative_button);
		
		for(int i = 0; i < actor_list.size(); i++)
		{
			Table temp = new Table(Assets.skin);
			temp.add(actor_list.get(i));
			newTable.add(temp).width(Assets.width/2);
		}
		
		table.add(message).width(Assets.game_height);
		table.row();
		table.add(newTable);

		stage.addActor(background_overlay);
		stage.addActor(table);
//		table.debug();
	}
	
	private void removeActors()
	{
		background_overlay.remove();
		table.remove();
	}
}
