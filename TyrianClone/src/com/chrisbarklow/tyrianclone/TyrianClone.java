package com.chrisbarklow.tyrianclone;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.chrisbarklow.tyrianclone.managers.MusicManager;
import com.chrisbarklow.tyrianclone.managers.PreferenceManager;
import com.chrisbarklow.tyrianclone.managers.SoundManager;
import com.chrisbarklow.tyrianclone.screens.StartScreen;


public class TyrianClone extends Game {
	//constant useful for logging
	public static final String LOG = TyrianClone.class.getSimpleName();
	
	//a libgdx helper class that logs the current FPS
	private FPSLogger fpsLogger;
	
	//manager
	private MusicManager musicManager;
	private SoundManager soundManager;	
	private PreferenceManager preferenceManager;
	
	public static boolean debug_mode = true;
	
	public TyrianClone(){
		preferenceManager = new PreferenceManager();	
	}
	
	public PreferenceManager getPreferenceManager(){
		return preferenceManager;
	}
	
	public SoundManager getSoundManager(){
		return soundManager;
	}
	
	public MusicManager getMusicManager(){
		return musicManager;
	}
	
	@Override
	public void create(){
		Gdx.app.log(TyrianClone.LOG, "Creating game");
		fpsLogger = new FPSLogger();
		
		// create the music manager service
        musicManager = new MusicManager();

        // create the sound manager service
        soundManager = new SoundManager();
        
        setScreen(new StartScreen(this));
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
		//profileService.persist();
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
