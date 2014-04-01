package com.chrisbarklow.tyrianclone;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.chrisbarklow.tyrianclone.screens.HighScoresScreen;
import com.chrisbarklow.tyrianclone.screens.MenuScreen;
import com.chrisbarklow.tyrianclone.screens.OptionsScreen;
import com.chrisbarklow.tyrianclone.screens.SplashScreen;
import com.chrisbarklow.tyrianclone.services.ProfileService;


public class TyrianClone extends Game {
	//constant useful for logging
	public static final String LOG = TyrianClone.class.getSimpleName();
	
	//a libgdx helper class that logs the current FPS
	private FPSLogger fpsLogger;
	
	//services
	private final ProfileService profileService;
	private final TyrianClonePreferences preferences;
	
	public static boolean debug_mode = true;
	
	public TyrianClone(){
		profileService = new ProfileService();
		preferences = new TyrianClonePreferences();
	}
	
	public ProfileService getProfileService(){
		return profileService;
	}
	
	public TyrianClonePreferences getPreferences(){
		return preferences;
	}
	
	public SplashScreen getSplashScreen(){
		return new SplashScreen(this);
	}
	
	public MenuScreen getMenuScreen(){
		return new MenuScreen(this);
	}
	
	public HighScoresScreen getHighScoresScreen(){
		return new HighScoresScreen(this);
	}
	
	public OptionsScreen getOptionsScreen(){
		return new OptionsScreen(this);
	}
	
	@Override
	public void create(){
		Gdx.app.log(TyrianClone.LOG, "Creating game");
		fpsLogger = new FPSLogger();
		setScreen(getSplashScreen());
	}
	
	@Override
	public void resize(int width, int height){
		Gdx.app.log(TyrianClone.LOG, "Resizing game to: " + width + " x " + height);
	}
	
	@Override
	public void render(){
		super.render();
		//output the current FPS
		fpsLogger.log();
	}
	
	@Override
	public void pause(){
		Gdx.app.log(TyrianClone.LOG, "Pausing the game.");
		profileService.persist();
	}
	
	@Override
	public void resume(){
		Gdx.app.log(TyrianClone.LOG, "Resuming the game.");
	}
	
	@Override
	public void dispose(){
		Gdx.app.log(TyrianClone.LOG, "Disposing the game.");
	}
}
