package com.chrisbarklow.tyrianclone.screens;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.chrisbarklow.tyrianclone.TyrianClone;
import com.chrisbarklow.tyrianclone.domain.Profile;
import com.chrisbarklow.tyrianclone.managers.MusicManager.TyrianCloneMusic;
import com.chrisbarklow.tyrianclone.screens.scene2d.Ship2D;

public class LevelScreen extends AbstractScreen {
	
	private final Profile profile;
    //private final Level level;

    private Ship2D ship2D;

    public LevelScreen(TyrianClone game, int targetLevelId ){
        super( game );

        // set the basic attributes
        profile = game.getProfileManager().retrieveProfile();
        //level = game.getLevelManager().findLevelById( targetLevelId );
    }

    @Override
    public void show()
    {
        super.show();

        // play the level music
        game.getMusicManager().play( TyrianCloneMusic.LEVEL );

        // create the ship and add it to the stage
        ship2D = Ship2D.create( profile.getShip(), getAtlas() );

        // center the ship horizontally
        ship2D.setX(stage.getWidth()/2 - ship2D.getWidth()/2);
        ship2D.setY(ship2D.getHeight());

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
