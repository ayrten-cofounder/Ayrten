package com.ayrten.scrots.screens;

import java.util.ArrayList;

import com.ayrten.scrots.common.Assets;
import com.ayrten.scrots.common.SelectionBox;
import com.ayrten.scrots.dots.power.PowerDot_Decelerate;
import com.ayrten.scrots.dots.power.PowerDot_Invincible;
import com.ayrten.scrots.dots.power.PowerDot_Magnet;
import com.ayrten.scrots.dots.power.PowerDot_Rainbow;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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
	
	protected ArrayList<Image> newly_unlocked_dots;
	protected Table dot_column;

	protected SelectionBox selected_powerdot_box;
	
	protected Image prev_selected_dot;
	protected Image curr_selected_dot;

	public GameConfigScreen(Screen backScreen) {
		super(backScreen, true);
		setupStage();
		showTableScreen();

		newly_unlocked_dots = new ArrayList<Image>();
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
				 if (Assets.prefs.getBoolean("first_time", true))
				 loadTutorialScreen();
				 else {
				 game_screen = new GameScreen();
				 Assets.playGameBGM();
				 Assets.game.setScreen(game_screen);
				 }
//				horizontal_scroll.scrollTo(horizontal_scroll.getScrollX()
//						+ Assets.width, 0, horizontal_scroll.getWidth(),
//						horizontal_scroll.getHeight());
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

		initializeHorizontalScroll(initializeVerticalScroll());

		stage.addActor(horizontal_scroll);
		stage.addActor(vertical_scroll);
	}
	
	@Override
	public void show() {
		super.show();
		if(!newly_unlocked_dots.isEmpty()) {
			for(Image dot_image : newly_unlocked_dots)
				createSelectionDot(dot_image);
		}
	}
	
	private void createSelectionDot(final Image dot_image) {
		float size = Assets.width - Assets.game_width;
		Image selected_bkg = new Image(Assets.rounded_rectangle_dark_gray);
		selected_bkg.setVisible(false);
//		final Image image = new Image(dot_texture);
		dot_image.setUserObject(selected_bkg);
		dot_image.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				if (prev_selected_dot != null)
					((Image) prev_selected_dot.getUserObject())
							.setVisible(false);
				prev_selected_dot = (Image) event.getTarget();
				((Image) event.getTarget().getUserObject())
						.setVisible(true);
				curr_selected_dot = dot_image;
			}
		});
		
		dot_column.stack(selected_bkg, dot_image).width(size).height(size);
		dot_column.row();
	}
	
	public void addUnlockedDot(Image dot_image) {
		newly_unlocked_dots.add(dot_image);
	}

	private ArrayList<Table> initializeVerticalScroll() {
		ArrayList<Table> all_columns = new ArrayList<Table>();
		vertical_table = new Table(Assets.skin);
		vertical_table.setSize(Assets.width, navigation_bar.getY() * 2);

		ArrayList<Image> dots = new ArrayList<Image>();
		dots.add(new Image(Assets.rainbow_dot));
		dots.add(new Image(Assets.invincible_dot));
		dots.add(new Image(Assets.magnet_dot));

		float size = Assets.width - Assets.game_width;
		dot_column = new Table(Assets.skin);
		ArrayList<Texture> dot_list = new ArrayList<Texture>();
		
		if(Assets.item_manager.isItemUnlocked(PowerDot_Rainbow.class.getSimpleName()));
			dot_list.add(Assets.rainbow_dot);
		if(Assets.item_manager.isItemUnlocked(PowerDot_Invincible.class.getSimpleName()))
			dot_list.add(Assets.invincible_dot);
		if(Assets.item_manager.isItemUnlocked(PowerDot_Magnet.class.getSimpleName()))
			dot_list.add(Assets.magnet_dot);
		if(Assets.item_manager.isItemUnlocked(PowerDot_Decelerate.class.getSimpleName()))
			dot_list.add(Assets.decelerate_dot);

		for (int i = 0; i < dot_list.size(); i++) {
//			Image selected_bkg = new Image(Assets.rounded_rectangle_dark_gray);
//			selected_bkg.setVisible(false);
//			final Image image = new Image(dot_list.get(i));
//			image.setUserObject(selected_bkg);
//			image.addListener(new ClickListener() {
//				@Override
//				public void clicked(InputEvent event, float x, float y) {
//					super.clicked(event, x, y);
//					if (prev_selected_dot != null)
//						((Image) prev_selected_dot.getUserObject())
//								.setVisible(false);
//					prev_selected_dot = (Image) event.getTarget();
//					((Image) event.getTarget().getUserObject())
//							.setVisible(true);
//					curr_selected_dot = image;
//				}
//			});
//			dot_column.stack(selected_bkg, image).width(size).height(size);
//			// if(i != dots.size() - 1)
//			dot_column.row();
			Image image = new Image(dot_list.get(i));
			createSelectionDot(image);
		}

		ScrollPane dot_column_scroll = new ScrollPane(dot_column);
		Table bkg_column_table = new Table(Assets.skin);
		bkg_column_table.setBackground(Assets.rounded_rectangle_blue);
		
		all_columns.add(dot_column);

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

		Label select_button = new Label("Select", style);
		select_button.setBounds(select_button.getX(), select_button.getY(),
				select_button.getWidth(), select_button.getHeight());
		select_button.setAlignment(Align.center);
		select_button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				if (curr_selected_dot != null) {
					vertical_scroll.scrollTo(0, 0, vertical_scroll.getWidth(),
							vertical_scroll.getHeight());

					new Timer().scheduleTask(new Task() {
						@Override
						public void run() {
							vertical_scroll.setVisible(false);
							selected_powerdot_box.setIcon(curr_selected_dot);
							selected_powerdot_box.setIconVisible(true);
//							((Image) selected_powerdot_box.getUserObject())
//									.setDrawable(curr_selected_dot
//											.getDrawable());
//							((Image) selected_powerdot_box.getUserObject())
//									.setVisible(true);
						}
					}, 0.5f);
				}
			}
		});
		
		Table top_table = new Table(Assets.skin);
		top_table.setSize(Assets.width, navigation_bar.getY());
		top_table.add(select_button).width(size);
		top_table.stack(bkg_column_table, dot_column_scroll).width(size);

		vertical_table.add(top_table).width(Assets.width)
				.height(navigation_bar.getY());
		vertical_table.row();
		vertical_table.add().height(navigation_bar.getY());
		
		// Scroll from bottom to up.
		vertical_scroll = new ScrollPane(vertical_table);
		vertical_scroll.setSize(Assets.width, navigation_bar.getY());
		vertical_scroll.setSize(Assets.width, navigation_bar.getY());
		vertical_scroll.setVisible(false);
		vertical_scroll.setFlickScroll(false);
		vertical_scroll.setScrollPercentY(100);
		vertical_scroll.layout();
		vertical_scroll.scrollTo(0, 0, vertical_scroll.getWidth(), vertical_scroll.getHeight());
	
		return all_columns;
	}

	private void initializeHorizontalScroll(ArrayList<Table> all_columns) {
		horizontal_table = new Table(Assets.skin);
		horizontal_table.setSize(Assets.width * 2, navigation_bar.getY());
		horizontal_table.add(table).width(Assets.width);

//		Table power_selection_table = new Table(Assets.skin);
//		power_selection_table.setSize(Assets.width, navigation_bar.getY());

		// First row is power dots, second row is profile (which will have
		// powers), third row is exchangeable powers.
		byte num_of_selections = 2;
		// +1 because need bottom row for starting game.
		float row_height = navigation_bar.getY() / (num_of_selections + 1);
		Table selection_table = new Table(Assets.skin);
		
		ArrayList<ArrayList<SelectionBox>> rows = new ArrayList<ArrayList<SelectionBox>>();
		rows.add(createPowerDotSelection(all_columns.get(0)));
		
		ArrayList<String> text_selection = new ArrayList<String>();
		text_selection.add("Powerdot Selection: ");
		
		assert rows.size() == text_selection.size();
		for(int i = 0; i < rows.size(); i++) {
			Table row = new Table(Assets.skin);
			Label label = new Label(text_selection.get(i), Assets.style_font_32_white);
			row.add(label).height(row_height);
			for(int j = 0; j < rows.get(i).size(); j++)
				row.add(rows.get(i).get(j)).height(row_height);
			selection_table.add(row).width(Assets.width).height(row_height);
			selection_table.row();
		}

//		for (int i = 0; i < 3; i++) {
//			final Image box = new Image(Assets.rounded_rectangle_border_blue);
//			box.setBounds(box.getX(), box.getY(), box.getWidth(),
//					box.getHeight());
//
//			Image selected_dot = new Image();
//			selected_dot.setVisible(false);
//			box.setUserObject(selected_dot);
//			
//			Stack stack = new Stack();
//			stack.addListener(new ClickListener() {
//				@Override
//				public void clicked(InputEvent event, float x, float y) {
//					super.clicked(event, x, y);
//					selected_powerdot_box = box;					
//					vertical_scroll.setVisible(true);
//					vertical_scroll.scrollTo(0, navigation_bar.getY(),
//							vertical_scroll.getWidth(),
//							vertical_scroll.getHeight());
//				}
//			});
//			stack.add(box);
//			stack.add(selected_dot);
//			
//			powerdot_selection_row.add(stack).height(row_height)
//					.width(Assets.width / 3);
//		}

		horizontal_table.add(selection_table).width(Assets.width);

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
	
	private ArrayList<SelectionBox> createPowerDotSelection(Table ref_contents) {
		ArrayList<SelectionBox> list = new ArrayList<SelectionBox>();
		// Number of powerdot slots, hardcoded to 3 for now.
		for(int i = 0; i < 3; i++) {
			final SelectionBox sbox = new SelectionBox(ref_contents, new Image(Assets.rounded_rectangle_border_blue));
			sbox.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					super.clicked(event, x, y);
					selected_powerdot_box = sbox;					
					vertical_scroll.setVisible(true);
					vertical_scroll.scrollTo(0, navigation_bar.getY(),
							vertical_scroll.getWidth(),
							vertical_scroll.getHeight());
				}
			});
			list.add(sbox);
		}
		
		return list;
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
