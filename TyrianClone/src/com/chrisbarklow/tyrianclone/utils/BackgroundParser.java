package com.chrisbarklow.tyrianclone.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Json;
import com.chrisbarklow.tyrianclone.background.Background;
import com.chrisbarklow.tyrianclone.background.Layer;
import com.chrisbarklow.tyrianclone.background.Tileset;

public class BackgroundParser {
	//private static final String backgroundFile = "image-atlas/test-level.json";
	private static final String TAG = "Background Parser";
	
	private Stage stage;
	private String levelName;
	
	
	public BackgroundParser(Stage stage, String levelName){
		this.stage = stage;
		this.levelName = levelName;
	}
	
	public void parseBackground(){
		FileHandle handle = Gdx.files.internal("image-atlas/"+levelName+".json");
		String fileContent = handle.readString();	
		
		Json json = new Json();	
		json.setElementType(Background.class, "layers", Layer.class);
		json.setElementType(Background.class, "tilesets", Tileset.class);
		json.setIgnoreUnknownFields(true);
		Background bg = json.fromJson(Background.class, fileContent);
		bg.setStage(stage);
		bg.setLevelName(levelName);
		bg.createTiles();
		
		Gdx.app.log(TAG, "Background width = " + bg.width);
	}

}
