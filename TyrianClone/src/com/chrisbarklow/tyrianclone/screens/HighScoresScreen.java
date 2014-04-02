package com.chrisbarklow.tyrianclone.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.chrisbarklow.tyrianclone.TyrianClone;
import com.chrisbarklow.tyrianclone.domain.Profile;
import com.chrisbarklow.tyrianclone.managers.SoundManager.TyrianCloneSound;

public class HighScoresScreen extends AbstractScreen {
	
	public HighScoresScreen(TyrianClone game){
		super(game);
	}
	
	@Override
	public void show(){
		super. show();
		
		//retrieve custom skin for 2d widgets
		Skin skin = super.getSkin();
		
		Table table = super.getTable();
		table.defaults().spaceBottom( 30 );
		
		Profile profile = game.getProfileManager().retrieveProfile();
        
        //create the label widgets
		table.add("High Scores").colspan(2);
		table.row();
		        
        table.add("Episode 1");
		String episode1HighScore = String.valueOf(profile.getHighScore(0));
		Label level1HighScore = new Label(episode1HighScore, skin);
		table.add(level1HighScore);
		table.row();
				
        table.add("Episode 2");
		String episode2HighScore = String.valueOf(profile.getHighScore(0));
		Label level2HighScore = new Label(episode2HighScore, skin);
		table.add(level2HighScore);
		table.row();
				
        table.add("Episode 3");
		String episode3HighScore = String.valueOf(profile.getHighScore(0));
		Label level3HighScore = new Label(episode3HighScore, skin);
		table.add(level3HighScore);
		table.row();
		
		//register the back button
		TextButton backButton = new TextButton("Back to Main Menu", skin);
		backButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				game.getSoundManager().play( TyrianCloneSound.CLICK );
				game.setScreen(game.getMenuScreen());
			}
		});
		table.add(backButton).size(250, 60).colspan(2);
		
		stage.addActor(table);
	}
	
//	@Override
//	public void resize(int width, int height){
//		super.resize(width, height);
//		
//		table.setWidth(width);
//		table.setHeight(height);
//		
//		//we need a complete redraw
//		table.invalidateHierarchy();
//	}

}
