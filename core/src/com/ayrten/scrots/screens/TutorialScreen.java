package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TutorialScreen extends ScrotsScreen {
	public TutorialScreen(Screen bscreen) {
		super(bscreen, true);

		setupStage();
		showTableScreen();
		
		Table overview_table = new Table(Assets.skin);
		// Declare your overviews here.
		final Label time_mode_overview = new Label("", Assets.style_font_32_white);
		time_mode_overview.setWrap(true);
		time_mode_overview.setText("The goal of the game is pop all the greens on screen as fast"
				+ " as you can! When all green dots are popped, you gain a point and"
				+ " the difficulty level increases. If time runs out or you popped a red"
				+ " dot, then GAME OVER! Below are the type of dots you'll be encountering:");
		
		float overview_height = time_mode_overview.getStyle().font
				.getWrappedBounds(time_mode_overview.getText(),
						table.getWidth()).height
				+ Assets.PAD * 2;
		overview_table.stack(time_mode_overview).width(table.getWidth() - Assets.PAD * 2).padLeft(Assets.PAD).padRight(Assets.PAD);

		LabelStyle tab_style = new LabelStyle();
		tab_style.font = Assets.font_64;
		tab_style.fontColor = Color.WHITE;
		
		final Image time_mode_tab = new Image(Assets.transparent_background);
		Label time_mode = new Label("Time Mode", tab_style);
		time_mode.setBounds(time_mode.getX(), time_mode.getY(), time_mode.getWidth(), time_mode.getHeight());
		time_mode.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				time_mode_tab.setVisible(true);
				time_mode_overview.setVisible(true);
			}
		});
		time_mode.setAlignment(Align.center);
		
		Table tab_table = new Table(Assets.skin);
		tab_table.stack(time_mode_tab, time_mode);
		addToNavBar(tab_table);
		
		Table scroll_table = new Table(Assets.skin);
		scroll_table.setWidth(table.getWidth());
		Table left_scroll_table = new Table(Assets.skin);
		left_scroll_table.setBackground(Assets.rounded_rectangle_border);
		Table right_scroll_table = new Table(Assets.skin);
		right_scroll_table.setBackground(Assets.rounded_rectangle_border);
		
		Label power_type = new Label("Power", Assets.style_font_32_white);
		power_type.setBounds(power_type.getX(), power_type.getY(), power_type.getWidth(), power_type.getHeight());
		Label penalty_type = new Label("Penalty", Assets.style_font_32_white);
		penalty_type.setBounds(penalty_type.getX(), penalty_type.getY(), penalty_type.getWidth(), penalty_type.getHeight());
		Label regular_type = new Label("Regular", Assets.style_font_32_white);
		regular_type.setBounds(regular_type.getX(), regular_type.getX(), regular_type.getWidth(), regular_type.getHeight());
		float tab_width = Math.max(regular_type.getWidth(), Math.max(penalty_type.getWidth(), power_type.getWidth()));

		// Right scroll table.
		// Regular dots
		Image greenDot = new Image(Assets.regDot_1);
		Image babyBlueDot = new Image(Assets.regDot_2);
		Image explosion_dot = new Image(Assets.explosion_dot);
		Image dwd_regDot1 = new Image(Assets.dwdRegDot_1);
		Image dwd_regDot2 = new Image(Assets.dwdRegDot_2);
		Label greenDesc = new Label("", Assets.style_font_32_white);
		greenDesc.setWrap(true);
		greenDesc.setText("Requires popping in order to advance.");
		
		Label babyBlueDesc = new Label("", Assets.style_font_32_white);
		babyBlueDesc.setWrap(true);
		babyBlueDesc.setText("Increases time limit by 2.5 seconds.");
		
		Label dwd_regDot1_desc = new Label("", Assets.style_font_32_white);
		dwd_regDot1_desc.setWrap(true);
		dwd_regDot1_desc
				.setText("3 to 4 green dots will appear. An explosion dot may appear.");
		
		Label explosion_dot_desc = new Label("", Assets.style_font_32_white);
		explosion_dot_desc.setWrap(true);
		explosion_dot_desc.setText("Pops all dots around the explosion dot.");

		Label dwd_regDot2_desc = new Label("", Assets.style_font_32_white);
		dwd_regDot2_desc.setWrap(true);
		dwd_regDot2_desc
				.setText("2 blue dots will appear. Increases time limit by 1 second.");
		
		float dot_size = Assets.style_font_32_white.font.getLineHeight() * 2;
		float pad_left = Assets.style_font_32_white.font.getSpaceWidth();
		float desc_size = table.getWidth() - tab_width - dot_size - pad_left - Assets.rounded_rectangle_border.getLeftWidth() - Assets.rounded_rectangle_border.getRightWidth();
		
		Table regular_table = new Table(Assets.skin);
		regular_table.left().top();
		regular_table.add(greenDot).width(dot_size).height(dot_size).top();
		regular_table.add(greenDesc).width(desc_size).padLeft(pad_left);
		regular_table.row();
		regular_table.add().height(Assets.style_font_32_white.font.getLineHeight());
		regular_table.row();
		regular_table.add(babyBlueDot).width(dot_size).height(dot_size);
		regular_table.add(babyBlueDesc).width(desc_size).padLeft(pad_left);
		regular_table.row();
		regular_table.add().height(Assets.style_font_32_white.font.getLineHeight());
		regular_table.row();
		regular_table.add(dwd_regDot1).width(dot_size).height(dot_size);
		regular_table.add(dwd_regDot1_desc).width(desc_size).padLeft(pad_left);
		regular_table.row();
		regular_table.add().height(Assets.style_font_32_white.font.getLineHeight());
		regular_table.row();
		regular_table.add(explosion_dot).width(dot_size).height(dot_size);
		regular_table.add(explosion_dot_desc).width(desc_size).padLeft(pad_left);
		regular_table.row();
		regular_table.add().height(Assets.style_font_32_white.font.getLineHeight());
		regular_table.row();
		regular_table.add(dwd_regDot2).width(dot_size).height(dot_size);
		regular_table.add(dwd_regDot2_desc).width(desc_size).padLeft(pad_left);
		final ScrollPane regular_scroll = new ScrollPane(regular_table);
		regular_scroll.setScrollingDisabled(true, false);
		
		// Penalty dots
		Image redDot = new Image(Assets.penDot_1);
		Image purpleDot = new Image(Assets.penDot_2);
		Image dwd_penDot1 = new Image(Assets.dwdPenDot_1);
		Image dwd_penDot2 = new Image(Assets.dwdPenDot_2);
		
		Label redDesc = new Label("", Assets.style_font_32_white);
		redDesc.setWrap(true);
		redDesc.setText("Immediate GAME OVER!");

		Label purpleDesc = new Label("", Assets.style_font_32_white);
		purpleDesc.setWrap(true);
		purpleDesc.setText("Decreases time limit by 5 seconds so BE CAREFUL!");
		
		Label dwd_penDot1_desc = new Label("", Assets.style_font_32_white);
		dwd_penDot1_desc.setWrap(true);
		dwd_penDot1_desc
				.setText("4 red dots will appear. Decreases time limit by half.");

		Label dwd_penDot2_desc = new Label("", Assets.style_font_32_white);
		dwd_penDot2_desc.setWrap(true);
		dwd_penDot2_desc
				.setText("2 purple dots will appear. Decreases time limit by 3 seconds.");
		
		Table penalty_table = new Table(Assets.skin);
		penalty_table.left().top();
		penalty_table.add(redDot).width(dot_size).height(dot_size);
		penalty_table.add(redDesc).width(desc_size).padLeft(pad_left);
		penalty_table.row();
		penalty_table.add().height(Assets.style_font_32_white.font.getLineHeight());
		penalty_table.row();
		penalty_table.add(purpleDot).width(dot_size).height(dot_size);
		penalty_table.add(purpleDesc).width(desc_size).padLeft(pad_left);
		penalty_table.row();
		penalty_table.add().height(Assets.style_font_32_white.font.getLineHeight());
		penalty_table.row();
		penalty_table.add(dwd_penDot1).width(dot_size).height(dot_size);
		penalty_table.add(dwd_penDot1_desc).width(desc_size).padLeft(pad_left);
		penalty_table.row();
		penalty_table.add().height(Assets.style_font_32_white.font.getLineHeight());
		penalty_table.row();
		penalty_table.add(dwd_penDot2).width(dot_size).height(dot_size);
		penalty_table.add(dwd_penDot2_desc).width(desc_size).padLeft(pad_left);
		final ScrollPane penalty_scroll = new ScrollPane(penalty_table);
		penalty_scroll.setScrollingDisabled(true, false);
		penalty_scroll.setVisible(false);
		
		// Power dots
		Image powerDot_Invincible = new Image(Assets.invincible_dot);
		Image powerDot_Rainbow = new Image(Assets.rainbow_dot);
		Image powerDot_Magnet = new Image(Assets.magnet_dot);

		Label powerDot_Invincible_desc = new Label("",
				Assets.style_font_32_white);
		powerDot_Invincible_desc.setWrap(true);
		powerDot_Invincible_desc
				.setText("Penality dots will have no effect for 5 seconds.");

		Label powerDot_Rainbow_desc = new Label("", Assets.style_font_32_white);
		powerDot_Rainbow_desc.setWrap(true);
		powerDot_Rainbow_desc
				.setText("Removes penality dots from the game for 5 seconds");

		Label powerDot_Magnet_desc = new Label("", Assets.style_font_32_white);
		powerDot_Magnet_desc.setWrap(true);
		powerDot_Magnet_desc
				.setText("Attracts all penality dots to the magnet for 8 seconds.");
		
		Table power_table = new Table(Assets.skin);
		power_table.left().top();
		power_table.add(powerDot_Invincible).width(dot_size).height(dot_size);
		power_table.add(powerDot_Invincible_desc).width(desc_size).padLeft(pad_left);
		power_table.row();
		power_table.add().height(Assets.style_font_32_white.font.getLineHeight());
		power_table.row();
		power_table.add(powerDot_Rainbow).width(dot_size).height(dot_size);
		power_table.add(powerDot_Rainbow_desc).width(desc_size).padLeft(pad_left);
		power_table.row();
		power_table.add().height(Assets.style_font_32_white.font.getLineHeight());
		power_table.row();
		power_table.add(powerDot_Magnet).width(dot_size).height(dot_size);
		power_table.add(powerDot_Magnet_desc).width(desc_size).padLeft(pad_left);
		final ScrollPane power_scroll = new ScrollPane(power_table);
		power_scroll.setScrollingDisabled(true, false);
		power_scroll.setVisible(false);
		
		right_scroll_table.stack(regular_scroll, penalty_scroll, power_scroll);
		
		scroll_table.add(left_scroll_table).width(tab_width + Assets.PAD).top().height(table.getHeight() - overview_height);
		scroll_table.add(right_scroll_table).width(scroll_table.getWidth() - tab_width - Assets.PAD);
		
		// Declare the tab indicators here.
		final Image regular_tab = new Image(Assets.transparent_background);
		final Image penalty_tab = new Image(Assets.transparent_background);
		penalty_tab.setVisible(false);
		final Image power_tab = new Image(Assets.transparent_background);
		power_tab.setVisible(false);

		// Set left tab listener.
		regular_type.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Tabs
				regular_tab.setVisible(true);
				penalty_tab.setVisible(false);
				power_tab.setVisible(false);

				// Descriptions
				regular_scroll.setVisible(true);
				penalty_scroll.setVisible(false);
				power_scroll.setVisible(false);
			}
		});


		penalty_type.addCaptureListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Tabs
				regular_tab.setVisible(false);
				penalty_tab.setVisible(true);
				power_tab.setVisible(false);
				
				// Descriptions
				regular_scroll.setVisible(false);
				penalty_scroll.setVisible(true);
				power_scroll.setVisible(false);
			}
		});


		power_type.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				regular_tab.setVisible(false);
				penalty_tab.setVisible(false);
				power_tab.setVisible(true);
				
				// Descriptions
				regular_scroll.setVisible(false);
				penalty_scroll.setVisible(false);
				power_scroll.setVisible(true);
			}
		});

		left_scroll_table.stack(regular_tab, regular_type).width(tab_width).height(Assets.style_font_32_white.font.getLineHeight()).top();
		left_scroll_table.row();
		left_scroll_table.stack(penalty_tab, penalty_type).width(tab_width).height(Assets.style_font_32_white.font.getLineHeight());
		left_scroll_table.row();
		left_scroll_table.stack(power_tab, power_type).width(tab_width).height(Assets.style_font_32_white.font.getLineHeight());

		table.add(overview_table).height(overview_height);
		table.row();
		table.add(scroll_table);
	}

	@Override
	public void hide() {
		super.hide();
		if (Assets.prefs.getBoolean("first_time", true)) {
			Assets.prefs.putBoolean("first_time", false);
			Assets.prefs.flush();
		}
	}
}
