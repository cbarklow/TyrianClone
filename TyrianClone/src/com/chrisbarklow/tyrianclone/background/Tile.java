package com.chrisbarklow.tyrianclone.background;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;

public class Tile extends Image {
	
	private Image tileImage;
	
	public Tile(float x, float y, Drawable tileDrawable){
		this.setX(x);
		this.setY(y);
		tileImage = new Image(tileDrawable, Scaling.stretch);
	}
}
