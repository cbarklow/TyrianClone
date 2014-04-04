package com.chrisbarklow.tyrianclone.domain;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.chrisbarklow.tyrianclone.utils.BackgroundParser;

public class Level {
	private final int id;
    private String name;
    BackgroundParser bgParser;

    public Level(String name ){
        this.name = name;
        id = 0;
    }

    /**
     * Retrieves the ID of this object.
     */
    public int getId(){
        return id;
    }

    /**
     * Retrieves the level's name.
     */
    public String getName(){
        return name;
    }

    /**
     * Sets the level's name.
     */
    public void setName(String name ){
        this.name = name;
    }
    
    public void load(Stage stage){
    	bgParser = new BackgroundParser(stage, this.name);
    	bgParser.parseBackground();
    }
}
