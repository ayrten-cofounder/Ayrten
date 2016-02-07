package com.ayrten.scrots.common;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

// Any item is a generic class that has an icon and a description
// about the item.
public class Item extends Image {
	protected String desc;
	
	public Item(Texture icon, String desc) {
		super(icon);
//		this.icon = icon;
		this.desc = desc;
	}
	
//	public Image getIcon() { return icon; }
	public String getDescription() { return desc; }
}
