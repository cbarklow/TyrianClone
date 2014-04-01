package com.chrisbarklow.tyrianclone.screens;

import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.chrisbarklow.tyrianclone.TyrianClone;

public class OptionsScreen extends AbstractScreen {
	
	private Table table;
	private Label volumeValue;
	
	public OptionsScreen(TyrianClone game){
		super(game);
	}
	
	@Override
	public void show(){
		super.show();
		Skin skin = super.getSkin();
		
		table = new Table();
		table.center();
		table.pad(8f);
		
		Label options = new Label("Options", skin);
		table.add(options).colspan(3).spaceBottom(30f);
		table.row();
		
		Label soundEffects = new Label("Sound Effects: ", skin);
		table.add(soundEffects);
		final CheckBox soundEffectsCheckBox = new CheckBox("", skin);
		soundEffectsCheckBox.setChecked(game.getPreferences().isSoundEffectsEnabled());
		soundEffectsCheckBox.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				Gdx.app.log(TyrianClone.LOG, "Sound Effects box has been clicked");
				boolean enabled = soundEffectsCheckBox.isChecked();
				game.getPreferences().setSoundEffectsEnabled(enabled);
			}
		});
		table.add(soundEffectsCheckBox).colspan(2).align(Align.left);
		table.row();
		
		Label music = new Label("Music: ", skin);
		table.add(music);
		final CheckBox musicCheckBox = new CheckBox("", skin);
		musicCheckBox.setChecked(game.getPreferences().isMusicEnabled());
		musicCheckBox.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				Gdx.app.log(TyrianClone.LOG, "Music box has been clicked");
				boolean enabled = musicCheckBox.isChecked();
				game.getPreferences().setMusicEnabled(enabled);
			}
		});
		table.add(musicCheckBox).colspan(2).align(Align.left);
		table.row();
		
		Label slider = new Label("Volume", skin);
		table.add(slider);
		final Slider volumeSlider = new Slider(0f, 1.0f, 0.1f, false, skin);
		volumeSlider.setValue(game.getPreferences().getVolume());
		volumeSlider.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				Gdx.app.log(TyrianClone.LOG, "volumeSlider: " + volumeSlider.getValue());
				game.getPreferences().setVolume(volumeSlider.getValue());
				updateVolumeLabel();
			}
		});
		table.add(volumeSlider).fill();
		volumeValue = new Label("",skin);
		table.add(volumeValue).width(40f);
		updateVolumeLabel();
		table.row();
		
		//register the back button
		TextButton backButton = new TextButton("Back to Main Menu", skin);
		backButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				game.setScreen(game.getMenuScreen());
			}
		});
		table.add(backButton).colspan(3).width(300f).height(60f).spaceTop(30f);
		
		stage.addActor(table);
		
	}
	
	@Override
	public void resize(int width, int height){
		super.resize(width, height);
		
		table.setWidth(width);
		table.setHeight(height);
		
		table.invalidateHierarchy();
	}

	/**
     * Updates the volume label next to the slider.
     */
    private void updateVolumeLabel()
    {
        float volume = ( game.getPreferences().getVolume() * 100 );
        volumeValue.setText( String.format( Locale.US, "%1.0f%%", volume ) );
    }
}
