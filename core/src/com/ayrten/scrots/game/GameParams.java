package com.ayrten.scrots.game;

import java.util.ArrayList;

import com.ayrten.scrots.common.Assets;
import com.ayrten.scrots.profile.PlayerProfile;
import com.badlogic.gdx.graphics.Texture;

public class GameParams 
{
	public PlayerProfile profile;
	public ArrayList<String> powerdot_types;
	public ArrayList<Texture> textures;
	
	public GameParams(ArrayList<String> powerdot_types, ArrayList<Texture> textures, PlayerProfile profile) {
		this.powerdot_types = powerdot_types;
		this.textures = textures;
		this.profile = profile;
	}
	
	public GameParams() {
		powerdot_types = new ArrayList<String>();
		powerdot_types.add("com.ayrten.scrots.dots.power.PowerDot_Rainbow");
		powerdot_types.add("com.ayrten.scrots.dots.power.PowerDot_Invincible");
		powerdot_types.add("com.ayrten.scrots.dots.power.PowerDot_Magnet");
		
		textures = new ArrayList<Texture>();
		textures.add(Assets.rainbow_dot);
		textures.add(Assets.invincible_dot);
		textures.add(Assets.magnet_dot);
		
		assert powerdot_types.size() == textures.size();
		profile = null;
	}

}
