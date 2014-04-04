package com.chrisbarklow.tyrianclone;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.chrisbarklow.tyrianclone.managers.LevelManager;
import com.chrisbarklow.tyrianclone.managers.MusicManager;
import com.chrisbarklow.tyrianclone.managers.PreferencesManager;
import com.chrisbarklow.tyrianclone.managers.ProfileManager;
import com.chrisbarklow.tyrianclone.managers.SoundManager;
import com.chrisbarklow.tyrianclone.screens.HighScoresScreen;
import com.chrisbarklow.tyrianclone.screens.LevelScreen;
import com.chrisbarklow.tyrianclone.screens.MenuScreen;
import com.chrisbarklow.tyrianclone.screens.OptionsScreen;
import com.chrisbarklow.tyrianclone.screens.SplashScreen;
import com.chrisbarklow.tyrianclone.screens.StartGameScreen;


public class TyrianClone extends Game {
	//constant useful for logging
	public static final String LOG = TyrianClone.class.getSimpleName();
	
	//a libgdx helper class that logs the current FPS
	private FPSLogger fpsLogger;
	
	//services
	private final ProfileManager profileService;
	private final PreferencesManager preferencesManager;
	private MusicManager musicManager;
	private SoundManager soundManager;
	private LevelManager levelManager;
	
	public static boolean debug_mode = true;
	
	public TyrianClone(){
		profileService = new ProfileManager();
		preferencesManager = new PreferencesManager();
	}
	
	public ProfileManager getProfileManager(){
		return profileService;
	}
	
	public PreferencesManager getPreferencesManager(){
		return preferencesManager;
	}
	
	public SoundManager getSoundManager(){
		return soundManager;
	}
	
	public MusicManager getMusicManager(){
		return musicManager;
	}
	
	public LevelManager getLevelManager(){
		return levelManager;
	}
	
	public SplashScreen getSplashScreen(){
		return new SplashScreen(this);
	}
	
	public MenuScreen getMenuScreen(){
		return new MenuScreen(this);
	}
	
	public StartGameScreen getStartGameScreen(){
		return new StartGameScreen(this);
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
		profileService.retrieveProfile();
		
		// create the music manager service
        musicManager = new MusicManager();
        musicManager.setVolume(preferencesManager.getVolume());
        musicManager.setEnabled(preferencesManager.isMusicEnabled());

        // create the sound manager service
        soundManager = new SoundManager();
        soundManager.setVolume(preferencesManager.getVolume());
        soundManager.setEnabled(preferencesManager.isSoundEnabled());
        
        levelManager = new LevelManager();
		
        //setScreen(getSplashScreen());
        setScreen(new LevelScreen(this, "test-level"));
	}
	
	@Override
	public void resize(int width, int height){
		super.resize(width, height);
		Gdx.app.log(TyrianClone.LOG, "Resizing game to: " + width + " x " + height);
	}
	
	@Override
	public void render(){
		super.render();
		//output the current FPS
		if(debug_mode) fpsLogger.log();
	}
	
	@Override
	public void pause(){
		super.pause();
		Gdx.app.log(TyrianClone.LOG, "Pausing the game.");
		profileService.persist();
	}
	
	@Override
	public void resume(){
		super.resume();
		Gdx.app.log(TyrianClone.LOG, "Resuming the game.");
	}
	
	@Override
    public void setScreen(
        Screen screen )
    {
        super.setScreen( screen );
        Gdx.app.log( TyrianClone.LOG, "Setting screen: " + screen.getClass().getSimpleName() );
    }
	
	@Override
	public void dispose(){
		super.dispose();
		Gdx.app.log(TyrianClone.LOG, "Disposing the game.");
	}
}
