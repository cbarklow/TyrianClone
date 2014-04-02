package com.chrisbarklow.tyrianclone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.chrisbarklow.tyrianclone.TyrianClone;
import com.chrisbarklow.tyrianclone.managers.SoundManager.TyrianCloneSound;

public class MenuScreen extends AbstractScreen {
	// setup the dimensions of the menu buttons
    private static final float BUTTON_WIDTH = 300f;
    private static final float BUTTON_HEIGHT = 60f;
    private static final float BUTTON_SPACING = 10f;
    
    public MenuScreen(TyrianClone game){
    	super(game);
    }
    
    @Override
    public void show(){
    	super.show();
    	
    	//retrieve the skin
    	Skin skin = super.getSkin();
    	
        Table table = super.getTable();
        
    	// label "welcome"
        //Label welcomeLabel = new Label( "Welcome to Tyrian for Android!", skin );
        table.add("Welcome to Tyrian for Android !").spaceBottom(50f);
        table.row();
        
        // button "start game"
        TextButton startGameButton = new TextButton( "Start game", skin);
        startGameButton.addListener(new ChangeListener(){
        	@Override
        	public void changed(ChangeEvent event, Actor actor){        		
        		Gdx.app.log(TyrianClone.LOG, "Options button clicked");
        		game.getSoundManager().play( TyrianCloneSound.CLICK );
        		game.setScreen(game.getStartGameScreen());
        	}
        });
        table.add(startGameButton).size(BUTTON_WIDTH, BUTTON_HEIGHT).uniform().spaceBottom(BUTTON_SPACING);
        table.row();
 
        // button "options"
        TextButton optionsButton = new TextButton( "Options", skin);
        optionsButton.addListener(new ChangeListener(){
        	@Override
        	public void changed(ChangeEvent event, Actor actor){
        		Gdx.app.log(TyrianClone.LOG, "Options button clicked");
        		game.getSoundManager().play( TyrianCloneSound.CLICK );
        		game.setScreen(game.getOptionsScreen());
        	}
        });
        table.add(optionsButton).uniform().fill().spaceBottom(BUTTON_SPACING);
        table.row();
 
        // button "hall of fame"
        TextButton highScoresButton = new TextButton( "High Scores", skin);
        highScoresButton.addListener(new ChangeListener(){
        	@Override
			public void changed(ChangeEvent event, Actor actor){
        		Gdx.app.log(TyrianClone.LOG, "High Scores button clicked");
        		game.getSoundManager().play( TyrianCloneSound.CLICK );
				game.setScreen(game.getHighScoresScreen());
			}
        });
        table.add(highScoresButton).uniform().fill().spaceBottom(BUTTON_SPACING);
        table.row();
        
        stage.addActor(table);
    }
    
//	@Override
//	public void resize(int width, int height){
//		super.resize(width, height);
//		
//		table.setWidth(width);
//		table.setHeight(height);
//		
//		//we need a complete redraw
//		table.invalidateHierarchy();
//	}
}
