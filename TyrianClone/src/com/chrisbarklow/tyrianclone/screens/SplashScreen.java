package com.chrisbarklow.tyrianclone.screens;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
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
		AtlasRegion splashRegion = getAtlas().findRegion("splash-screen/splash-image");
		Drawable splashDrawable = new TextureRegionDrawable(splashRegion);
		
		//create the splash image actor and set its size
		splashImage = new Image(splashDrawable, Scaling.stretch);
		splashImage.setFillParent(true);

		//this is needed for the fade-in effect
		splashImage.getColor().a = 0f;
		
		//configure the fade-in and fade-out effect of the splash image		
		splashImage.addAction(Actions.sequence(Actions.fadeIn( 0.75f ), Actions.delay( 1.75f ), Actions.fadeOut( 0.75f ),
	            new Action() {
	                @Override
	                public boolean act(float delta ){
	                    // the last action will move to the next screen
	                    game.setScreen( new MenuScreen( game ) );
	                    return true;
	                }
	            }));
		
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
