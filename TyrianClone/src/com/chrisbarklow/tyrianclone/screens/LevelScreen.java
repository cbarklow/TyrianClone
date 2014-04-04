package com.chrisbarklow.tyrianclone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Scaling;
import com.chrisbarklow.tyrianclone.TyrianClone;
import com.chrisbarklow.tyrianclone.domain.Level;
import com.chrisbarklow.tyrianclone.domain.Profile;
import com.chrisbarklow.tyrianclone.managers.MusicManager.TyrianCloneMusic;
import com.chrisbarklow.tyrianclone.screens.scene2d.Ship2D;
import com.chrisbarklow.tyrianclone.utils.BackgroundParser;

public class LevelScreen extends AbstractScreen {
	
	private final Profile profile;
    private final Level level;
	
	//private BackgroundParser bgParser;

    private Ship2D ship2D;

    public LevelScreen(TyrianClone game, String levelName ){
        super( game );

        // set the basic attributes
        profile = game.getProfileManager().retrieveProfile();
        level = new Level(levelName);
        //bgParser = new BackgroundParser(stage);
    }

    @Override
    public void show()
    {
        super.show();

        // play the level music
        game.getMusicManager().play( TyrianCloneMusic.LEVEL );
        level.load(stage);

        // create the ship and add it to the stage
        ship2D = Ship2D.create( profile.getShip(), getAtlas() );
//        ship2D.setWidth( .1f * Gdx.graphics.getWidth());
//        ship2D.setHeight(.1f * Gdx.graphics.getHeight());
//        ship2D.setScaling(Scaling.fill);

        // center the ship horizontally
        ship2D.setInitialPosition( ( Gdx.graphics.getWidth() / 2 - ship2D.getWidth() / 2 ),
            ship2D.getHeight() );

        // add the ship to the stage
        stage.addActor( ship2D );

        // add a fade-in effect to the whole stage
        stage.getRoot().getColor().a = 0f;
        stage.getRoot().addAction(Actions.fadeIn( 0.5f ) );        
    }
    
    @Override
    public boolean isGameScreen(){
    	return true;
    }
    
    @Override
    public void resize(int width, int height){
    	super.resize(width, height);
    }
    
//    @Override
//    public void render(float delta){
//    	super.render(delta);
//    	ship2D.moveShip(delta);
//    }
}
