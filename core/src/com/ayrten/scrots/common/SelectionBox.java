package com.ayrten.scrots.common;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
// SelectionBox is like a SectionTab, except the icon is initially not defined
// SelectionBox is used in GameConfig screen, in which user would choose their 
// equipment before playing.
public class SelectionBox extends SectionTab {

	public SelectionBox(Table contents, Image highlight) {
		super(null, contents, highlight);
		highlight.setVisible(true);
	}
	
	@Override
	public void highlight(boolean opt) {
		contents.setVisible(opt);
	}
}
