package com.chrisbarklow.tyrianclone.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.chrisbarklow.tyrianclone.TyrianClone;

public class SplashScreen extends AbstractScreen {
	private Texture splashTexture;	
	
	public SplashScreen(TyrianClone game){
		super(game);
	}
	
	@Override
	public void show(){
		super.show();
		
		//load the splash image
		splashTexture = new Texture("splash.png");
		
		//set linear texture filter to improve stretching
		splashTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}
	
	@Override
	public void resize(int width, int height){
		super.resize(width, height);
		
		//let's make sure the stage is clear
		stage.clear();
		
		//get our texture region from our image atlas
		TextureRegion splashRegion = new TextureRegion( splashTexture, 0, 0, 512, 301 );
		
		//create the splash image actor and set its size
		Image splashImage = new Image(splashRegion);
		splashImage.setWidth(width);
		splashImage.setHeight(height);
		
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
	public void dispose(){
		super.dispose();
		splashTexture.dispose();
	}
}
