package com.ayrten.scrots.screens;

import java.util.ArrayList;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.ButtonInterface;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MessageScreen extends ScrotsScreen {
	private Table table;

	private Image background_overlay;
	private Label message;
	private Label negative_button;
	private Label positive_button;

	// Used as an overlay window.
	public MessageScreen(Stage stage) {
		this.stage = stage;

		table = new Table();
		table.setFillParent(true);
		table.setSkin(Assets.skin);

		background_overlay = new Image(Assets.transparent_background);
		background_overlay.setBounds(0, 0, Assets.width, Assets.height);
	}

	// Used as a generic message screen. Actors must be initialized beforehand.
	public MessageScreen(ArrayList<Actor> actor_list) {
		stage = new Stage();
		actors = actor_list;
		for (Actor actor : actor_list)
			stage.addActor(actor);
	}

	// This type of message screen is formatted as a slideshow.
	public MessageScreen(Table top_table, int pages) {
		stage = new Stage();
		actors = new ArrayList<Actor>();

		final ScrollPane message_scroll = new ScrollPane(top_table);
		message_scroll.setFlickScroll(false);

		Label previous = new Label("Prev", Assets.style_font_64_orange);
		previous.setBounds(previous.getX(), previous.getY(),
				previous.getWidth(), previous.getHeight());
		previous.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (message_scroll.getScrollX() != 0)
					message_scroll.scrollTo(message_scroll.getScrollX()
							- Assets.width, 0, message_scroll.getWidth(),
							message_scroll.getHeight());
			}
		});

		Label next = new Label("Next", Assets.style_font_64_orange);
		next.setBounds(next.getX(), next.getY(), next.getWidth(),
				next.getHeight());
		next.addListener(new ClickListener() {
			private int max_x = Assets.width * 2;

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (message_scroll.getScrollX() != max_x)
					message_scroll.scrollTo(message_scroll.getScrollX()
							+ Assets.width, 0, message_scroll.getWidth(),
							message_scroll.getHeight());
				else
					((MessageScreen) Assets.game.getScreen()).transition();
			}
		});

		table = new Table(Assets.skin);
		table.setFillParent(true);

		table.add(message_scroll).height(
				Assets.height - next.getStyle().font.getLineHeight());
		table.row();

		Table temp = new Table(Assets.skin);
		temp.add(previous);
		table.add(temp).width(Assets.width / 2);

		temp = new Table(Assets.skin);
		temp.add(next);
		table.add(temp).width(Assets.width / 2);

		stage.addActor(table);
	}

	// Needs to be overridden, should be called when transitioning from
	// MessageScreen
	// to another screen.
	public void transition() {
	}

	public void makeWindow(String title, String positive_title,
			String negative_title, final ButtonInterface yes_interface,
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

		for (int i = 0; i < actor_list.size(); i++) {
			Table temp = new Table(Assets.skin);
			temp.add(actor_list.get(i));
			newTable.add(temp).width(Assets.width / 2);
		}

		table.add(message).width(Assets.game_height);
		table.row();
		table.add(newTable);

		stage.addActor(background_overlay);
		stage.addActor(table);
		Assets.game.apk_intf.shouldShowAd(true);
	}

	public void makeGameOverWithHighScore(final GameScreen gameScreen,
			String title, String positive_title, String negative_title,
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

		final Label name = new Label("Enter Name", Assets.style_font_64_black);
		name.setWidth(name.getStyle().font.getBounds("wwwwwwwwww").width);
		name.setAlignment(Align.center);
		name.setTouchable(Touchable.disabled);

		final TextField textField = new TextField("", Assets.skin);
		textField.getStyle().font = Assets.font_0;
		textField.getStyle().cursor = null;
		textField.setSize(0, 0);
		textField.setBounds(name.getX(), name.getY(), name.getWidth(),
				name.getHeight());
		textField.getStyle().background = Assets.gray_box;
		textField.setMaxLength(10);
		textField.setMessageText("Enter Name");
		textField.layout();

		textField.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				name.setText("");
			}
		});

		textField.setTextFieldListener(new TextFieldListener() {

			@Override
			public void keyTyped(TextField textField, char key) {
				name.setText(textField.getText());
			}
		});
		
		final Label submit = new Label("Submit", Assets.style_font_64_blue);
		submit.setAlignment(Align.center);

		submit.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				gameScreen.setHighScoreName(name.getText().toString());
				name.remove();
				textField.remove();
				submit.remove();
			}
		});

		Table newTable = new Table(Assets.skin);
		ArrayList<Actor> actor_list = new ArrayList<Actor>();
		actor_list.add(positive_button);
		actor_list.add(negative_button);

		for (int i = 0; i < actor_list.size(); i++) {
			Table temp = new Table(Assets.skin);
			temp.add(actor_list.get(i));
			newTable.add(temp).width(Assets.width / 2);
		}

		table.add(message).width(Assets.game_height);
		table.row();
		table.stack(textField, name).width(name.getWidth())
				.height(name.getHeight()).center();
		table.row();
		table.add(submit).width(submit.getWidth());
		table.row();
		table.add(newTable);

		textField.setBounds(name.getX(), name.getY(), name.getWidth(),
				name.getHeight());

		Assets.game.apk_intf.shouldShowAd(true);
		stage.addActor(background_overlay);
		stage.addActor(table);
	}

	private void removeActors() {
		Assets.game.apk_intf.shouldShowAd(false);
		background_overlay.remove();
		table.remove();
	}
}
