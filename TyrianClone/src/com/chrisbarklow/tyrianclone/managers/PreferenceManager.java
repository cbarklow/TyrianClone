package com.chrisbarklow.tyrianclone.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class PreferenceManager {
	private static final String BEST_TIME = "bestTime";
	private static final String PREFS_NAME = "tyrianClonePreferences";
	
	protected Preferences getPrefs()
    {
        return Gdx.app.getPreferences( PREFS_NAME );
    }
	
	public long getBestScore(){
		long defaultTime = 0;
		if(getPrefs().getLong(BEST_TIME) != 0)
			return getPrefs().getLong(BEST_TIME);
		return defaultTime;
	}
	
	public void setBestScore(long newBestTime ){
		Preferences prefs = getPrefs();
		prefs.putLong(BEST_TIME, newBestTime);
		prefs.flush();
	}
}
