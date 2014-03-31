package com.chrisbarklow.tyrianclone.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.chrisbarklow.tyrianclone.TyrianClone;

public class MenuScreen extends AbstractScreen {
	// setup the dimensions of the menu buttons
    private static final float BUTTON_WIDTH = 300f;
    private static final float BUTTON_HEIGHT = 60f;
    private static final float BUTTON_SPACING = 10f;
    
    public MenuScreen(TyrianClone game){
    	super(game);
    }
    
    @Override
    public void resize(int width, int height){
    	super.resize(width, height);
    	final float buttonX = ( width - BUTTON_WIDTH ) / 2;
        float currentY = 280f;
    	
    	// label "welcome"
        Label welcomeLabel = new Label( "Welcome to Tyrian for Android!", getSkin() );
        welcomeLabel.setX( ( width - welcomeLabel.getWidth() ) / 2 );
        welcomeLabel.setY( currentY + 100 );
        stage.addActor( welcomeLabel );
 
        // button "start game"
        TextButton startGameButton = new TextButton( "Start game", getSkin() );
        startGameButton.setX(buttonX);
        startGameButton.setY(currentY);
        startGameButton.setWidth(BUTTON_WIDTH);
        startGameButton.setHeight(BUTTON_HEIGHT);
        stage.addActor( startGameButton );
 
        // button "options"
        TextButton optionsButton = new TextButton( "Options", getSkin() );
        optionsButton.setX(buttonX);
        optionsButton.setY( currentY -= BUTTON_HEIGHT + BUTTON_SPACING );
        optionsButton.setWidth(BUTTON_WIDTH);
        optionsButton.setHeight(BUTTON_HEIGHT);
        stage.addActor( optionsButton );
 
        // button "hall of fame"
        TextButton hallOfFameButton = new TextButton( "Hall of Fame", getSkin() );
        hallOfFameButton.setX(buttonX);
        hallOfFameButton.setY( currentY -= BUTTON_HEIGHT + BUTTON_SPACING );
        hallOfFameButton.setWidth(BUTTON_WIDTH);
        hallOfFameButton.setHeight(BUTTON_HEIGHT);
        stage.addActor( hallOfFameButton );
    }

}
