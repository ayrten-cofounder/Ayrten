package com.ayrten.scrots.screens;

import java.util.ArrayList;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class GameConfigScreen extends ScrotsScreen {
	public static GameScreen game_screen;

	protected Label timeMode;
	protected Label survivalMode;

	protected Table horizontal_table;
	protected ScrollPane horizontal_scroll;

	protected Table vertical_table;
	protected ScrollPane vertical_scroll;

	protected Actor selected_powerdot_box;

	protected Image previous_selected_dot;
	protected Image current_selected_dot;

	public GameConfigScreen(Screen backScreen) {
		super(backScreen, true);
		setupStage();
		showTableScreen();

		Label intro = new Label("Pick Your Mode of Gameplay",
				Assets.style_font_64_white);

		timeMode = new Label("Time Mode", Assets.style_font_64_white);
		timeMode.setBounds(timeMode.getX(), timeMode.getY(),
				timeMode.getWidth(), timeMode.getHeight());
		timeMode.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (Assets.prefs.getBoolean("sound_effs", true))
					Assets.button_pop.play();
				// if (Assets.prefs.getBoolean("first_time", true))
				// loadTutorialScreen();
				// else {
				// game_screen = new GameScreen();
				// Assets.playGameBGM();
				// Assets.game.setScreen(game_screen);
				// }
				horizontal_scroll.scrollTo(horizontal_scroll.getScrollX()
						+ Assets.width, 0, horizontal_scroll.getWidth(),
						horizontal_scroll.getHeight());
			}
		});

		survivalMode = new Label("Survival Mode - Coming Soon!",
				Assets.style_font_64_orange);
		survivalMode.setBounds(survivalMode.getX(), survivalMode.getY(),
				survivalMode.getWidth(), survivalMode.getHeight());
		survivalMode.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO: add survival mode
			}
		});

		table.add(intro);
		table.row();
		table.add().height(Assets.style_font_64_white.font.getLineHeight());
		table.row();
		table.add(timeMode);
		table.row();
		table.add(survivalMode);

		initializeHorizontalScroll();
		initializeVerticalScroll();

		stage.addActor(vertical_scroll);
		stage.addActor(horizontal_scroll);
	}

	private void initializeVerticalScroll() {
		vertical_table = new Table(Assets.skin);
		vertical_table.setSize(Assets.width, navigation_bar.getY() * 2);

		ArrayList<Image> dots = new ArrayList<Image>();
		dots.add(new Image(Assets.rainbow_dot));
		dots.add(new Image(Assets.invincible_dot));
		dots.add(new Image(Assets.magnet_dot));

		float size = Assets.width - Assets.game_width;
		Table dot_column = new Table(Assets.skin);

		for (int i = 0; i < 6; i++) {
			Image selected_bkg = new Image(Assets.rounded_rectangle_dark_gray);
			selected_bkg.setVisible(false);
			Image image = new Image(Assets.rainbow_dot);
			image.setUserObject(selected_bkg);
			image.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					super.clicked(event, x, y);
					if (previous_selected_dot != null)
						((Image) previous_selected_dot.getUserObject())
								.setVisible(false);
					previous_selected_dot = (Image) event.getTarget();
					((Image) event.getTarget().getUserObject())
							.setVisible(true);
					current_selected_dot = (Image) event.getTarget();
				}
			});
			dot_column.stack(selected_bkg, image).width(size).height(size);
			// if(i != dots.size() - 1)
			dot_column.row();
		}

		ScrollPane dot_column_scroll = new ScrollPane(dot_column);
		Table bkg_column_table = new Table(Assets.skin);
		bkg_column_table.setBackground(Assets.rounded_rectangle_blue);

		int left = (int) ((int) Assets.width * 0.02);
		int right = left;
		int top = (int) ((int) Assets.height * 0.02);
		int bottom = top;

		LabelStyle style = new LabelStyle();
		style.font = Assets.font_64;
		style.fontColor = Color.WHITE;
		style.background = new NinePatchDrawable(new NinePatch(new Texture(
				Gdx.files.internal("data/rounded_rectangle_pale_orange.png")),
				left, right, top, bottom));
		;

		Label select_button = new Label("Select", style);
		select_button.setBounds(select_button.getX(), select_button.getY(),
				select_button.getWidth(), select_button.getHeight());
		select_button.setAlignment(Align.center);
		select_button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);

				((Image) selected_powerdot_box.getUserObject())
						.setDrawable(current_selected_dot.getDrawable());
				((Image) selected_powerdot_box.getUserObject())
						.setVisible(true);
			}
		});

		vertical_table.add().width(Assets.game_width / 2);
		vertical_table.add(select_button).width(Assets.game_width / 2);
		vertical_table.stack(bkg_column_table, dot_column_scroll)
				.height(navigation_bar.getY()).width(size);
		vertical_table.row();
		vertical_table.add().width(Assets.width).height(navigation_bar.getY());

		// Scroll from bottom to up.
		vertical_scroll = new ScrollPane(vertical_table);
		vertical_scroll.setFlickScroll(false);
		vertical_scroll.setSize(Assets.width, navigation_bar.getY());
//		vertical_scroll.setVisible(false);
	}

	private void initializeHorizontalScroll() {
		horizontal_table = new Table(Assets.skin);
		horizontal_table.setSize(Assets.width * 2, navigation_bar.getY());
		horizontal_table.add(table).width(Assets.width);

		Table power_selection_table = new Table(Assets.skin);
		power_selection_table.setSize(Assets.width, navigation_bar.getY());

		// First row is power dots, second row is profile (which will have
		// powers), third row is exchangeable powers.
		float row_height = navigation_bar.getY() / 3;
		Table powerdot_selection_row = new Table(Assets.skin);

		for (int i = 0; i < 3; i++) {
			Image box = new Image(Assets.rounded_rectangle_border_blue);
			box.setBounds(box.getX(), box.getY(), box.getWidth(),
					box.getHeight());
			box.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					super.clicked(event, x, y);
					selected_powerdot_box = event.getTarget();
//					vertical_scroll.setVisible(true);
					((Table) vertical_scroll.getWidget()).layout();
					vertical_scroll.scrollTo(0, navigation_bar.getY(),
							vertical_scroll.getWidth(),
							vertical_scroll.getHeight());
				}
			});

			Image selected_dot = new Image();
			selected_dot.setVisible(false);
			box.setUserObject(selected_dot);
			powerdot_selection_row.stack(box, selected_dot).height(row_height)
					.width(Assets.width / 3);
		}

		horizontal_table.add(powerdot_selection_row).width(Assets.width);

		horizontal_scroll = new ScrollPane(horizontal_table);
		horizontal_scroll.setFlickScroll(false);
		horizontal_scroll.setSize(Assets.width, navigation_bar.getY());
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
