package com.chrisbarklow.tyrianclone.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;
import com.chrisbarklow.tyrianclone.TyrianClone;

public class MusicManager implements Disposable{
	
	/**
     * The available music files.
     */
    public enum TyrianCloneMusic{
        MENU( "music/menu.ogg" ),
        LEVEL( "music/level.ogg" );

        private final String fileName;

        private TyrianCloneMusic(String fileName ){
            this.fileName = fileName;
        }

        public String getFileName(){
            return fileName;
        }
    }
    
    private Music musicBeingPlayed;
    private float volume = 1.0f;
    private boolean enabled = true;
    
    public void play(TyrianCloneMusic music ){
        // check if the music is enabled
        if( ! enabled ) return;

        // stop any music being played
        Gdx.app.log( TyrianClone.LOG, "Playing music: " + music.name() );
        stop();

        // start streaming the new music
        FileHandle musicFile = Gdx.files.internal( music.getFileName() );
        musicBeingPlayed = Gdx.audio.newMusic( musicFile );
        musicBeingPlayed.setVolume( volume );
        musicBeingPlayed.setLooping( true );
        musicBeingPlayed.play();
    }
    
    /**
     * Stops and disposes the current music being played, if any.
     */
    public void stop(){
        if( musicBeingPlayed != null ) {
            Gdx.app.log( TyrianClone.LOG, "Stopping current music" );
            musicBeingPlayed.stop();
            musicBeingPlayed.dispose();
        }
    }
    
    /**
     * Sets the music volume which must be inside the range [0,1].
     */
    public void setVolume(float volume ){
        Gdx.app.log( TyrianClone.LOG, "Adjusting music volume to: " + volume );

        // check and set the new volume
        if( volume < 0 || volume > 1f ) {
            throw new IllegalArgumentException( "The volume must be inside the range: [0,1]" );
        }
        this.volume = volume;

        // if there is a music being played, change its volume
        if( musicBeingPlayed != null ) {
            musicBeingPlayed.setVolume( volume );
        }
    }
    
    /**
     * Enables or disabled the music.
     */
    public void setEnabled(boolean enabled ){
        this.enabled = enabled;

        // if the music is being deactivated, stop any music being played
        if( ! enabled ) {
            stop();
        }
    }
    
    /**
     * Disposes the music manager.
     */
    public void dispose(){
        Gdx.app.log( TyrianClone.LOG, "Disposing music manager" );
        stop();
    }

}
