package com.ayrten.scrots.screens;

import java.util.ArrayList;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.ButtonInterface;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class MessageScreen extends ScrotsScreen {
	protected Image background_overlay;
	protected Label message;
	protected Label negative_button;
	protected Label positive_button;
	
	private boolean clicked = false;
	
	public static float WINDOW_DISPLAY_HEIGHT = Assets.height - Assets.font_64.getLineHeight();

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
	public MessageScreen(Table top_table, final int pages) {
		stage = new Stage();
		actors = new ArrayList<Actor>();

		final ScrollPane message_scroll = new ScrollPane(top_table);
		message_scroll.setFlickScroll(false);

		LabelStyle green_style = new LabelStyle();
		green_style.font = Assets.font_32;
		green_style.fontColor = Color.WHITE;
		
		int left = (int) ((int) Assets.width * 0.02);
		int right = left;
		int top = (int) ((int) Assets.height * 0.02);
		int bottom = top;
		
		NinePatchDrawable rounded_rectangle_green = new NinePatchDrawable(
				new NinePatch(new Texture(Gdx.files
						.internal("data/rounded_rectangle_green.png")), left,
						right, top, bottom));
		
		green_style.background = rounded_rectangle_green;
		
		LabelStyle orange_style = new LabelStyle();
		orange_style.font = Assets.font_32;
		orange_style.fontColor = Color.WHITE;
		
		NinePatchDrawable rounded_rectangle_orange = new NinePatchDrawable(
				new NinePatch(new Texture(Gdx.files
						.internal("data/rounded_rectangle_pale_orange.png")), left,
						right, top, bottom));
		
		green_style.background = rounded_rectangle_green;
		orange_style.background = rounded_rectangle_orange;
		
		final Label next = new Label("Next", green_style);
		if(pages == 1)
			next.setText("Done");
		final Label previous = new Label("Prev", orange_style);
		
		next.setBounds(next.getX(), next.getY(), next.getWidth(),
				next.getHeight());
		next.addListener(new ClickListener() {
			private int max_x = Assets.width * (pages - 1);

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (message_scroll.getScrollX() != max_x) {
					if (message_scroll.getScrollX() + Assets.width == max_x) {
						next.setText("Done");
					}
					
					if(Assets.prefs.getBoolean("sound_effs"))
						Assets.button_pop.play();

					message_scroll.scrollTo(message_scroll.getScrollX()
							+ Assets.width, 0, message_scroll.getWidth(),
							message_scroll.getHeight());
				} else {
					next.setTouchable(Touchable.disabled);
					previous.setTouchable(Touchable.disabled);
					message_scroll.setTouchable(Touchable.disabled);
					transition();
					
					if(Assets.prefs.getBoolean("sound_effs"))
						Assets.button_pop.play();
				}
				
				if (message_scroll.getScrollX() > 0){
					previous.setVisible(true);
				}
			}
		});

		previous.setBounds(previous.getX(), previous.getY(),
				previous.getWidth(), previous.getHeight());
		previous.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (message_scroll.getScrollX() != 0) {
					next.setText("Next");
					
					if(Assets.prefs.getBoolean("sound_effs"))
						Assets.button_pop.play();

					message_scroll.scrollTo(message_scroll.getScrollX()
							- Assets.width, 0, message_scroll.getWidth(),
							message_scroll.getHeight());
				}
				
				if (message_scroll.getScrollX() == 0) {
					previous.setVisible(false);
				}
			}
		});
		previous.setVisible(false);

		table = new Table(Assets.skin);
		table.setFillParent(true);
		table.setBackground(Assets.red_backgroud);

		table.add(message_scroll).height(
				Assets.height - next.getHeight() - bottom);
		table.row();
		
		Table bottom_table = new Table(Assets.skin);
		bottom_table.setWidth(Assets.width);

		Table temp = new Table(Assets.skin);
		if(pages > 1) {
		temp.add(previous);
		bottom_table.add(temp).width(Assets.width / 2);
		}

		temp = new Table(Assets.skin);
		temp.add(next);
		bottom_table.add(temp).width(Assets.width / 2);
		
		table.add(bottom_table);
		stage.addActor(table);
	}

	// Needs to be overridden, should be called when transitioning from
	// MessageScreen to another screen.
	public void transition() {}

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

	public void makeSingleButtonWindow(String title, String positive_title,
			final ButtonInterface yes_interface) {
		message = new Label(title, Assets.style_font_64_white);
		message.setPosition((Assets.width / 2) - (message.getWidth() / 2),
				Assets.height / 2);
		message.setAlignment(Align.center);
		message.setWrap(true);

		positive_button = new Label(positive_title, Assets.style_font_64_orange);
		positive_button.setPosition(Assets.width / 5 * 4, (Assets.height / 2)
				- message.getHeight());
		positive_button.setAlignment(Align.center);

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

		for (int i = 0; i < actor_list.size(); i++) {
			Table temp = new Table(Assets.skin);
			temp.add(actor_list.get(i));
			newTable.add(temp).width(Assets.width);
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
				clicked = true;
			}
		});

		textField.setTextFieldListener(new TextFieldListener() {

			@Override
			public void keyTyped(TextField textField, char key) {
				name.setText(textField.getText());
			}
		});

		final Label submit = new Label("Submit", Assets.style_font_64_blue);
		submit.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {

				if (clicked) {
					gameScreen.setHighScoreName(name.getText().toString());
					name.remove();
					textField.remove();
					submit.remove();
				}
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

		Table submitTable = new Table(Assets.skin);
		submitTable.stack(textField, name).width(name.getWidth())
				.height(name.getHeight());
		submitTable.row();
		submitTable.add(submit).width(submit.getWidth());

		table.add(message).width(Assets.game_height);
		table.row();
		table.add(submitTable);
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
