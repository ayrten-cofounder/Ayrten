package com.ayrten.scrots.screens;

import java.util.ArrayList;

import com.ayrten.scrots.common.Assets;
import com.ayrten.scrots.common.Item;
import com.ayrten.scrots.common.SelectionBox;
import com.ayrten.scrots.dots.power.PowerDot_Decelerate;
import com.ayrten.scrots.dots.power.PowerDot_Invincible;
import com.ayrten.scrots.dots.power.PowerDot_Magnet;
import com.ayrten.scrots.dots.power.PowerDot_Rainbow;
import com.ayrten.scrots.manager.Manager.gameState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class GameConfigScreen extends ScrotsScreen {
	public static GameScreen game_screen;

//	protected Label time_mode;
//	protected Label survivalMode;

	protected Table horizontal_table;
	protected ScrollPane horizontal_scroll;

	protected Table vertical_table;
	protected ScrollPane vertical_scroll;

	protected ArrayList<Image> newly_unlocked_dots;

	// Pointer to the currently selected selection box.
	protected SelectionBox curr_selection_box;

	// Pointer to the current selection table. Updated depending on
	// what user is selecting (ie. power dots, profiles, etc).
	protected Table curr_selection_table;

	// Used to keep track what should be highlighted in the dot selection column
	protected Item prev_selected_item;
	protected Item curr_selected_item;
	
	protected Label selected_item_description;

	protected enum screenState { MODE, EQUIPMENT, SELECTION };
	protected screenState state;

	public GameConfigScreen(Screen backScreen) {
		super(backScreen, true);
		setupStage();
		showTableScreen();

		state = screenState.MODE;
		newly_unlocked_dots = new ArrayList<Image>();
		Label intro = new Label("Pick Your Mode of Gameplay",
				Assets.style_font_64_white);

		Label time_mode = new Label("Time Mode", Assets.style_font_64_white);
		time_mode.setBounds(time_mode.getX(), time_mode.getY(),
				time_mode.getWidth(), time_mode.getHeight());
		time_mode.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				state = screenState.EQUIPMENT;
				Assets.playButtonSound();
				// TODO: how to make scroll instantly...?
				horizontal_scroll.scrollTo(horizontal_scroll.getScrollX()
						+ Assets.width, 0, horizontal_scroll.getWidth(),
						horizontal_scroll.getHeight());
			}
		});

		Label survival_mode = new Label("Survival Mode - Coming Soon!",
				Assets.style_font_64_orange);
		survival_mode.setBounds(survival_mode.getX(), survival_mode.getY(),
				survival_mode.getWidth(), survival_mode.getHeight());
		survival_mode.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO: add survival mode
			}
		});

		table.add(intro);
		table.row();
		table.add().height(Assets.style_font_64_white.font.getLineHeight());
		table.row();
		table.add(time_mode);
		table.row();
		table.add(survival_mode);

		initHorizontalScroll(initSelectionTables());

		stage.addActor(horizontal_scroll);
		// Causes the vertical scroll to scroll to initial position.
		vertical_scroll.validate();
		vertical_scroll.setScrollPercentY(100);
	}

	@Override
	public void show() {
		super.show();
		// TODO: fix unlocking power dots
//		if(!newly_unlocked_dots.isEmpty()) {
//			for(Image dot_image : newly_unlocked_dots)
//				createSelectionDot(dot_image, power_dot_selection_table);
//		}

		// Clear settings.
	}

	// TODO: Add item width as parameter
	private void initSelectionDot(final Item item) {
		item.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
//				// Switch selection table
				// TODO: why do we need this here...?
//				if(curr_selection_table != null)
//					curr_selection_table.setVisible(false);
//				curr_selection_table = ref_table;
//				curr_selection_table.setVisible(true);
				
				// Switch selected item
				if (prev_selected_item != null)
					((Image) prev_selected_item.getUserObject())
					.setVisible(false);
				prev_selected_item = (Item) event.getTarget();
				((Image) event.getTarget().getUserObject())
				.setVisible(true);
				curr_selected_item = item;
				selected_item_description.setText(curr_selected_item.getDescription());
			}
		});

//		input_table.stack(selected_bkg, dot_image).width(size).height(size);
	}

	public void addUnlockedDot(Image dot_image) {
		newly_unlocked_dots.add(dot_image);
	}

	private ArrayList<Table> initSelectionTables() {
		// This table is used contain all selection tables. This will be used
		// when creating the SelectionBox objects so they know which tables
		// to reference.
		ArrayList<Table> all_selection_tables = new ArrayList<Table>();
		vertical_table = new Table(Assets.skin);
		vertical_table.setSize(Assets.width, navigation_bar.getY() * 2);
		vertical_table.debugAll();
		
		// This table will have 3 panels:
		// 		1. A panel to show the current selection table
		//		2. A panel to show the description of the selected item.
		//		3. A panel to contain the SELECT button
		// TODO: change to VerticalGroup
		Table main_selection_table = new Table(Assets.skin);
		main_selection_table.setSize(Assets.width, navigation_bar.getY());
		
		// This table will contain panels #2 and #3, which are the description panel
		// and SELECT button.
		// TODO: change to HorizontalGroup
		Table bottom_table = new Table(Assets.skin);
		bottom_table.setSize(Assets.width, navigation_bar.getY()/4);
		// Create the description panel.
		// TODO: change to Container class
		Table desc_panel = new Table(Assets.skin);
		desc_panel.align(Align.topLeft);
		desc_panel.setSize(Assets.width/2, bottom_table.getHeight());
		selected_item_description = new Label("", Assets.style_font_32_white);
		selected_item_description.setWrap(true);
		desc_panel.add(selected_item_description).width(desc_panel.getWidth()).top().left();
		final ScrollPane desc_scroll = new ScrollPane(desc_panel);
		desc_scroll.setTouchable(Touchable.disabled);;
		desc_scroll.setSize(desc_panel.getWidth(), desc_panel.getHeight());
		desc_scroll.addAction(Actions.repeat(RepeatAction.FOREVER, new Action() {
			@Override
			public boolean act(float delta) {
				desc_scroll.fling(1, 0, -25);
				return false;
			}
		}));
		bottom_table.add(desc_scroll).width(Assets.width/2).height(bottom_table.getHeight());
		
		// Create the SELECT button.
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
				if (curr_selected_item != null) {
					vertical_scroll.scrollTo(0, 0, vertical_scroll.getWidth(),
							vertical_scroll.getHeight());

					new Timer().scheduleTask(new Task() {
						@Override
						public void run() {
							curr_selection_box.setIcon(curr_selected_item);
							curr_selection_box.setIconVisible(true);
							// Unhighlight the item after selection is done.
							((Image) curr_selected_item.getUserObject()).setVisible(false);
							curr_selected_item = null;
							selected_item_description.setText("");
							state = screenState.EQUIPMENT;
						}
					}, 0.5f);
				}
			}
		});
		bottom_table.add(select_button).width(Assets.width/2).height(bottom_table.getHeight());
		
		// This table will serve as panel #1, which will have all the selection
		// tables stacked on top of each other.
		Table top_table = new Table(Assets.skin);
		top_table.setSize(Assets.width, navigation_bar.getY() - bottom_table.getHeight());
		byte num_of_rows = 4;
		float item_height = top_table.getHeight() / num_of_rows;
		
		// Create all your selection tables here.
		// TODO: calculate the width
		Table pow_dot_select_table = initPowerDotSelectionTable(item_height, item_height);
		pow_dot_select_table.setSize(top_table.getWidth(), top_table.getHeight());
		// Add all selection tables here to be referenced by SelectionBox later.
		all_selection_tables.add(pow_dot_select_table);
		// Add all your selection tables here.
		Stack selection_table_stack = new Stack();
		selection_table_stack.add(pow_dot_select_table);
		top_table.add(selection_table_stack).width(top_table.getWidth()).height(top_table.getHeight());
		
		// Populate the main table.
		main_selection_table.add(top_table).height(navigation_bar.getY() - bottom_table.getHeight());
		main_selection_table.row();
		main_selection_table.add(bottom_table).height(bottom_table.getHeight());
		
		vertical_table.add(main_selection_table).width(Assets.width).height(navigation_bar.getY());
		vertical_table.row();

		// Scroll from bottom to up.
		vertical_scroll = new ScrollPane(vertical_table);
		vertical_scroll.setSize(Assets.width, navigation_bar.getY());
		vertical_scroll.setFlickScroll(false);
		
		return all_selection_tables;
	}

	private Table initPowerDotSelectionTable(float height, float width) {
		Table power_dot_selection_table = new Table(Assets.skin);
		power_dot_selection_table.align(Align.topLeft);
		// This table will contain all the choices available to pick from.
		ArrayList<Item> dot_list = createUnlockedDotList();
		
		for (int i = 0; i < dot_list.size(); i++) {
			initSelectionDot(dot_list.get(i));
			dot_list.get(i);
			
			Image selected_bkg = new Image(Assets.rounded_rectangle_dark_gray);
			selected_bkg.setVisible(false);
			dot_list.get(i).setUserObject(selected_bkg);
			power_dot_selection_table.stack(selected_bkg, dot_list.get(i)).height(height).width(width);
		}

		return power_dot_selection_table;
	}

	private ArrayList<Item> createUnlockedDotList() {
		ArrayList<Item> list = new ArrayList<Item>();
		Item item;
		
		if(Assets.item_manager.isItemUnlocked(PowerDot_Rainbow.class.getSimpleName())) {
			item = new Item(Assets.rainbow_dot, Assets.rainbow_dot_description);
			list.add(item);
		}
		if(Assets.item_manager.isItemUnlocked(PowerDot_Invincible.class.getSimpleName())) {
			item = new Item(Assets.invincible_dot, Assets.invincible_dot_description);
			list.add(item);
		}
		if(Assets.item_manager.isItemUnlocked(PowerDot_Magnet.class.getSimpleName())) {
			item = new Item(Assets.magnet_dot, Assets.magnet_dot_description);
			list.add(item);
		}
		if(Assets.item_manager.isItemUnlocked(PowerDot_Decelerate.class.getSimpleName())) {
			item = new Item(Assets.decelerate_dot, Assets.decelerate_dot_description);
			list.add(item);
		}

		return list;
	}

	private void initHorizontalScroll(ArrayList<Table> all_columns) {
		horizontal_table = new Table(Assets.skin);
		horizontal_table.setSize(Assets.width * 2, navigation_bar.getY());
		horizontal_table.add(table).width(Assets.width).height(navigation_bar.getY());

		// First row is power dots, second row is profile (which will have
		// powers), third row is exchangeable powers.
		byte num_of_selections = 2;
		// +1 because need bottom row for starting game.
		float row_height = navigation_bar.getY() / (num_of_selections + 1);
		Table equip_table = new Table(Assets.skin);
		equip_table.setSize(Assets.width, navigation_bar.getY());
		equip_table.align(Align.topLeft);
		
		ArrayList<ArrayList<SelectionBox>> rows = new ArrayList<ArrayList<SelectionBox>>();
		rows.add(createPowerDotSelection(all_columns.get(0)));

		ArrayList<String> text_selection = new ArrayList<String>();
		text_selection.add("Powerdot Selection: ");
		
		LabelStyle text_style = Assets.style_font_64_white;
		float icon_size = text_style.font.getLineHeight() / 2 * 3;

		assert rows.size() >= text_selection.size();
		Table selection_table = new  Table(Assets.skin);
		for(int i = 0; i < rows.size(); i++) {
			Table row = new Table(Assets.skin);
			row.align(Align.left);
			// Add the label first.
			Label label = new Label(text_selection.get(i), text_style);
			row.add(label).height(row_height).left();
			// Add empty space between the SelectionBox and the label.
			float empty_space_width = equip_table.getWidth() - text_style.font.getBounds(label.getText()).width - rows.get(i).size() * icon_size;
			row.add().width(empty_space_width);
			// Add the SelectionBoxes
			for(int j = 0; j < rows.get(i).size(); j++)
				row.add(rows.get(i).get(j)).width(icon_size).height(icon_size);
			selection_table.add(row).width(Assets.width).height(row_height).left();
			selection_table.row();
		}
		
		ScrollPane selection_scroll = new ScrollPane(selection_table);
		selection_scroll.setSize(equip_table.getWidth(), equip_table.getHeight() - icon_size);
		
		equip_table.add(selection_scroll).width(selection_scroll.getWidth()).height(selection_scroll.getHeight());
		equip_table.row();

		// Play button to start the game.
		int left = (int) ((int) Assets.width * 0.02);
		int right = left;
		int top = (int) ((int) Assets.height * 0.02);
		int bottom = top;
		
		LabelStyle style = new LabelStyle();
		style.font = text_style.font;
		style.fontColor = Color.WHITE;
		style.background = new NinePatchDrawable(new NinePatch(new Texture(
				Gdx.files.internal("data/rounded_rectangle_pale_orange.png")),
				left, right, top, bottom));
		
		Label play_button = new Label("Let's get started!", style);
		play_button.setBounds(play_button.getX(), play_button.getY(), play_button.getWidth(), play_button.getHeight());
		play_button.setAlignment(Align.center);
		play_button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				
				Assets.playButtonSound();
				if (Assets.prefs.getBoolean("first_time", true))
					loadTutorialScreen();
				else {
					game_screen = new GameScreen();
					Assets.playGameBGM();
					Assets.game.setScreen(game_screen);
				}
				
				horizontal_scroll.scrollTo(horizontal_scroll.getScrollX()
						- Assets.width, 0, horizontal_scroll.getWidth(),
						horizontal_scroll.getHeight());
			}
		});
		equip_table.add(play_button).width(equip_table.getWidth()).height(icon_size);
		vertical_table.add(equip_table).width(Assets.width).height(navigation_bar.getY());

		horizontal_table.add(vertical_scroll).width(Assets.width).height(navigation_bar.getY());
		horizontal_scroll = new ScrollPane(horizontal_table);
		// User cannot scroll via dragging/flinging.
		horizontal_scroll.setFlickScroll(false);
		horizontal_scroll.setSize(Assets.width, navigation_bar.getY());
		// Disable scrolling via mouse scroll.
		horizontal_scroll.addCaptureListener(new InputListener() {
			public boolean scrolled(InputEvent event, float x, float y, int amount) {
				event.cancel();
				return false;
			};
		});
	}

	@Override
	public void addActors() {
		super.addActors();
	}

	private ArrayList<SelectionBox> createPowerDotSelection(Table ref_contents) {
		ArrayList<SelectionBox> list = new ArrayList<SelectionBox>();
		// Number of powerdot slots, hardcoded to 3 for now.
		// TODO: make number of powerdot slots configurable (ie. not hardcoded)
		for(int i = 0; i < 3; i++) {
			final SelectionBox sbox = new SelectionBox(ref_contents, new Image(Assets.rounded_rectangle_border_blue));
			sbox.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					super.clicked(event, x, y);
					if(curr_selection_box != null)
						curr_selection_box.highlight(false);
					curr_selection_box = sbox;
					curr_selection_box.highlight(true);
					
					vertical_scroll.setVisible(true);
					vertical_scroll.scrollTo(0, vertical_scroll.getHeight(),
							vertical_scroll.getWidth(),
							vertical_scroll.getHeight());
					state = screenState.SELECTION;
				}
			});
			list.add(sbox);
		}

		return list;
	}

	@Override
	public void executeBackAction() {
		switch(state) {
			case EQUIPMENT: 
				Assets.playButtonSound();
				state = screenState.MODE;
				horizontal_scroll.scrollTo(horizontal_scroll.getScrollX()
					- Assets.width, 0, horizontal_scroll.getWidth(),
					horizontal_scroll.getHeight());
				break;

			case SELECTION:
				Assets.playButtonSound();
				state = screenState.EQUIPMENT;
				vertical_scroll.scrollTo(0, 0, vertical_scroll.getWidth(),
						vertical_scroll.getHeight());
				break;

			// Mode selection
			default:
				super.executeBackAction();
		}
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
