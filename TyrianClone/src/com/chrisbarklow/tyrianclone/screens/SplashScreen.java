package com.chrisbarklow.tyrianclone.screens;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.chrisbarklow.tyrianclone.TyrianClone;
import com.chrisbarklow.tyrianclone.managers.MusicManager.TyrianCloneMusic;

public class SplashScreen extends AbstractScreen {
	private Image splashImage;
	
	public SplashScreen(TyrianClone game){
		super(game);
	}
	
	@Override
	public void show(){
		super.show();
		
		// start playing the menu music
        game.getMusicManager().play( TyrianCloneMusic.MENU );
		
		//get our texture region from our image atlas
		AtlasRegion splashRegion = getAtlas().findRegion("splash-image");
		
		//create the splash image actor and set its size
		splashImage = new Image(splashRegion);
		splashImage.setWidth(stage.getWidth());
		splashImage.setHeight(stage.getHeight());

		//this is needed for the fade-in effect
		splashImage.getColor().a = 0f;
		
		//configure the fade-in and fade-out effect of the splash image
		splashImage.addAction(Actions.sequence(Actions.fadeIn(0.75f), 
				Actions.delay(0.75f),
				Actions.fadeOut(1.75f), Actions.run(new Runnable(){
					public void run(){
						// when the image is faded out, move on to the next screen
		                game.setScreen( game.getMenuScreen() );
					}
				})));
		
		stage.addActor(splashImage);
	}
	
	@Override
	public void resize(int width, int height){
		super.resize(width, height);
		splashImage.setWidth(width);
		splashImage.setHeight(height);
		
		splashImage.invalidateHierarchy();
	}
}
