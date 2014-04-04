package com.chrisbarklow.tyrianclone.background;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

public class Background {

	public int height;
	public ArrayList<Layer> layers;
	public int tileheight;
	public ArrayList<Tileset> tilesets;	
	public int tilewidth;
	public int version;
	public int width;

	private Stage stage;
	private String levelName;
	
	public void setStage(Stage stage){
		this.stage = stage;
	}
	
	public void setLevelName(String levelName){
		this.levelName = levelName;
	}

	public void createTiles(){
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("image-atlas/"+levelName+".txt"));
		
		for (Layer layer : layers) {
			ArrayList<Integer> tileIds = layer.data;
			int x = 0;
			int y = (height-1) * tileheight;
			for(int tileId : tileIds){
				AtlasRegion tileRegion = atlas.findRegion(levelName + "-" +tileId);
				Drawable tileDrawable = new TextureRegionDrawable(tileRegion);
				Image tileImage = new Image(tileDrawable);
				tileImage.setX(x);
				tileImage.setY(y);
//				tileImage.setScaling(Scaling.fill);
				stage.addActor(tileImage);
				if(x < ((width-1) * tilewidth)){
					x += tilewidth;
				} else {
					x = 0;
					y -= tileheight;
				}
			}
		}
	}

}
