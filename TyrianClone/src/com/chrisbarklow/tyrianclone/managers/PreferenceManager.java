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
	
	public String getBestScore(){
		String defaultTime = "0:00:00";
		if(!getPrefs().getString(BEST_TIME).equals(""))
			return getPrefs().getString(BEST_TIME);
		return defaultTime;
	}
	
	public void setBestScore(String newBestTime ){
		getPrefs().putString(BEST_TIME, newBestTime);
		getPrefs().flush();
	}
}
