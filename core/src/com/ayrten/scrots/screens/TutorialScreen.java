package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TutorialScreen extends ScrotsScreen {
	public TutorialScreen(Screen bscreen) {
		super(bscreen, true);

		setupStage();
		showTableScreen();

		Label intent = new Label("", Assets.style_font_32_white);
		intent.setWrap(true);
		intent.setText("The goal of the game is pop all the greens on screen as fast"
				+ " as you can! When all green dots are popped, you gain a point and"
				+ " the difficulty level increases. If time runs out or you popped a red"
				+ " dot, then GAME OVER! Below are the type of dots you'll be encountering:");

		Image greenDot = new Image(Assets.regDot_1);
		Image redDot = new Image(Assets.penDot_1);
		Image blueDot = new Image(Assets.penDot_2);
		Image babyBlueDot = new Image(Assets.regDot_2);
		Image explosion_dot = new Image(Assets.explosion_dot);
		Image dwd_regDot1 = new Image(Assets.dwdRegDot_1);
		Image dwd_regDot2 = new Image(Assets.dwdRegDot_2);
		Image dwd_penDot1 = new Image(Assets.dwdPenDot_1);
		Image dwd_penDot2 = new Image(Assets.dwdPenDot_2);
		Image powerDot_Invincible = new Image(Assets.invincible_dot);
		Image powerDot_Rainbow = new Image(Assets.rainbow_dot);
		Image powerDot_Magnet = new Image(Assets.magnet_dot);
		Image questionDot = new Image(Assets.question_mark);

		Label greenDesc = new Label("", Assets.style_font_32_white);
		greenDesc.setWrap(true);
		greenDesc.setText("Requires popping in order to advance.");

		Label redDesc = new Label("", Assets.style_font_32_white);

		redDesc.setWrap(true);
		redDesc.setText("Immediate GAME OVER!");

		Label blueDesc = new Label("", Assets.style_font_32_white);
		blueDesc.setWrap(true);
		blueDesc.setText("Decreases time limit by 5 seconds so BE CAREFUL!");

		Label babyBlueDesc = new Label("", Assets.style_font_32_white);
		babyBlueDesc.setWrap(true);
		babyBlueDesc.setText("Increases time limit by 2.5 seconds.");

		Label explosion_dot_desc = new Label("", Assets.style_font_32_white);
		explosion_dot_desc.setWrap(true);
		explosion_dot_desc.setText("Pops all dots around the explosion dot.");

		Label dwd_regDot1_desc = new Label("", Assets.style_font_32_white);
		dwd_regDot1_desc.setWrap(true);
		dwd_regDot1_desc
				.setText("3 to 4 green dots will appear. An explosion dot may appear.");

		Label dwd_regDot2_desc = new Label("", Assets.style_font_32_white);
		dwd_regDot2_desc.setWrap(true);
		dwd_regDot2_desc
				.setText("2 blue dots will appear. Increases time limit by 1 second.");

		Label dwd_penDot1_desc = new Label("", Assets.style_font_32_white);
		dwd_penDot1_desc.setWrap(true);
		dwd_penDot1_desc
				.setText("4 red dots will appear. Decreases time limit by half.");

		Label dwd_penDot2_desc = new Label("", Assets.style_font_32_white);
		dwd_penDot2_desc.setWrap(true);
		dwd_penDot2_desc
				.setText("2 purple dots will appear. Decreases time limit by 3 seconds.");

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

		Label questionDesc = new Label("", Assets.style_font_32_white);
		questionDesc.setWrap(true);
		questionDesc.setText("More to come!");

		Table innerTable = new Table(Assets.skin);
		innerTable.left().top();
		innerTable.add(greenDot).width(greenDot.getWidth())
				.height(greenDot.getHeight());
		innerTable.add(greenDesc)
				.width(Gdx.graphics.getWidth() / 2 - back.getWidth() / 5 * 2)
				.padLeft(back.getWidth() / 5);
		innerTable.row();
		innerTable.add("").height(back.getStyle().font.getLineHeight());
		innerTable.row();
		innerTable.add(redDot).width(redDot.getWidth())
				.height(redDot.getHeight());
		innerTable.add(redDesc)
				.width(Gdx.graphics.getWidth() / 2 - back.getWidth() / 5 * 2)
				.padLeft(back.getWidth() / 5);
		innerTable.row();
		innerTable.add("").height(back.getStyle().font.getLineHeight());
		innerTable.row();
		innerTable.add(blueDot).width(blueDot.getWidth())
				.height(blueDot.getHeight());
		innerTable.add(blueDesc)
				.width(Gdx.graphics.getWidth() / 2 - back.getWidth() / 5 * 2)
				.padLeft(back.getWidth() / 5);
		innerTable.row();
		innerTable.add("").height(back.getStyle().font.getLineHeight());
		innerTable.row();
		innerTable.add(babyBlueDot).width(babyBlueDot.getWidth())
				.height(babyBlueDot.getHeight());
		innerTable.add(babyBlueDesc)
				.width(Gdx.graphics.getWidth() / 2 - back.getWidth() / 5 * 2)
				.padLeft(back.getWidth() / 5);
		innerTable.row();
		innerTable.add("").height(back.getStyle().font.getLineHeight());
		innerTable.row();
		innerTable.add(explosion_dot).width(babyBlueDot.getWidth())
				.height(explosion_dot.getHeight());
		innerTable.add(explosion_dot_desc)
				.width(Gdx.graphics.getWidth() / 2 - back.getWidth() / 5 * 2)
				.padLeft(back.getWidth() / 5);

		// DWD Dots

		innerTable.row();
		innerTable.add("").height(back.getStyle().font.getLineHeight());
		innerTable.row();
		innerTable.add(dwd_regDot1).width(questionDot.getWidth())
				.height(dwd_regDot1.getHeight());
		innerTable.add(dwd_regDot1_desc)
				.width(Gdx.graphics.getWidth() / 2 - back.getWidth() / 5 * 2)
				.padLeft(back.getWidth() / 5);

		innerTable.row();
		innerTable.add("").height(back.getStyle().font.getLineHeight());
		innerTable.row();
		innerTable.add(dwd_regDot2).width(questionDot.getWidth())
				.height(dwd_regDot2.getHeight());
		innerTable.add(dwd_regDot2_desc)
				.width(Gdx.graphics.getWidth() / 2 - back.getWidth() / 5 * 2)
				.padLeft(back.getWidth() / 5);

		innerTable.row();
		innerTable.add("").height(back.getStyle().font.getLineHeight());
		innerTable.row();
		innerTable.add(dwd_penDot1).width(questionDot.getWidth())
				.height(dwd_penDot1.getHeight());
		innerTable.add(dwd_penDot1_desc)
				.width(Gdx.graphics.getWidth() / 2 - back.getWidth() / 5 * 2)
				.padLeft(back.getWidth() / 5);

		innerTable.row();
		innerTable.add("").height(back.getStyle().font.getLineHeight());
		innerTable.row();
		innerTable.add(dwd_penDot2).width(questionDot.getWidth())
				.height(dwd_penDot2.getHeight());
		innerTable.add(dwd_penDot2_desc)
				.width(Gdx.graphics.getWidth() / 2 - back.getWidth() / 5 * 2)
				.padLeft(back.getWidth() / 5);

		// Power Dots

		innerTable.row();
		innerTable.add("").height(back.getStyle().font.getLineHeight());
		innerTable.row();
		innerTable.add(powerDot_Invincible).width(questionDot.getWidth())
				.height(powerDot_Invincible.getHeight());
		innerTable.add(powerDot_Invincible_desc)
				.width(Gdx.graphics.getWidth() / 2 - back.getWidth() / 5 * 2)
				.padLeft(back.getWidth() / 5);

		innerTable.row();
		innerTable.add("").height(back.getStyle().font.getLineHeight());
		innerTable.row();
		innerTable.add(powerDot_Rainbow).width(questionDot.getWidth())
				.height(powerDot_Rainbow.getHeight());
		innerTable.add(powerDot_Rainbow_desc)
				.width(Gdx.graphics.getWidth() / 2 - back.getWidth() / 5 * 2)
				.padLeft(back.getWidth() / 5);

		innerTable.row();
		innerTable.add("").height(back.getStyle().font.getLineHeight());
		innerTable.row();
		innerTable.add(powerDot_Magnet).width(questionDot.getWidth())
				.height(powerDot_Magnet.getHeight());
		innerTable.add(powerDot_Magnet_desc)
				.width(Gdx.graphics.getWidth() / 2 - back.getWidth() / 5 * 2)
				.padLeft(back.getWidth() / 5);

		// Question Dot

		innerTable.row();
		innerTable.add("").height(back.getStyle().font.getLineHeight());
		innerTable.row();
		innerTable.add(questionDot).width(questionDot.getWidth())
				.height(questionDot.getHeight());
		innerTable.add(questionDesc)
				.width(Gdx.graphics.getWidth() / 2 - back.getWidth() / 5 * 2)
				.padLeft(back.getWidth() / 5);

		Table tempt = new Table(Assets.skin);

		tempt.add(intent).width(table.getWidth() - 100);
		tempt.row();
		tempt.add("").height(back.getStyle().font.getLineHeight());
		tempt.row();
		tempt.add(innerTable);
		tempt.row();
		tempt.add("").height(intent.getStyle().font.getLineHeight());

		ScrollPane scroll_view = new ScrollPane(tempt);
		scroll_view.setScrollingDisabled(true, false);

		table.add(scroll_view);
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
