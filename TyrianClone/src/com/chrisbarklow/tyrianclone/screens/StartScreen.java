package com.chrisbarklow.tyrianclone.screens;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.chrisbarklow.tyrianclone.TyrianClone;
import com.chrisbarklow.tyrianclone.managers.MusicManager.TyrianCloneMusic;
import com.chrisbarklow.tyrianclone.managers.SoundManager.TyrianCloneSound;
import com.chrisbarklow.tyrianclone.utils.StopWatch;

public class StartScreen implements Screen {
	
	final float WORLD_WIDTH = 800;
	final float WORLD_HEIGHT = 480;
	
	Stage stage;
	Table table;
	TyrianClone game;
	Skin skin;
	
	public StartScreen(TyrianClone game){
		this.game = game;
		//ScreenViewport svp = new ScreenViewport();
		StretchViewport svp = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT);
		this.stage = new Stage(svp);
		
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal( "skin/uiskin.atlas" ));
		skin = new Skin(Gdx.files.internal( "skin/uiskin.json" ), atlas);
		
	}

	@Override
	public void render(float delta) {
		stage.act(delta);
		
		//the following code clears the screen with the given RGB color
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		game.getMusicManager().play(TyrianCloneMusic.MENU);		
		
		table = new Table(skin);
		table.setFillParent(true);
		table.add("Tyrian Dodger").spaceBottom(50f).row();
				
		table.setFillParent(true);
		table.add("Tilt your phone to move the ship. Dodge enemy bullets!").spaceBottom(50f).row();
	
		// start game button
        TextButton playButton = new TextButton( "Press to Play", skin);
        playButton.addListener(new ChangeListener(){
        	@Override
			public void changed(ChangeEvent event, Actor actor){
        		Gdx.app.log(TyrianClone.LOG, "Play game button clicked");
        		game.getSoundManager().play( TyrianCloneSound.CLICK );
				game.setScreen(new GameScreen(game));
			}
        });
        table.add(playButton).uniform().fill().spaceBottom(50f);
        table.row();
        
        String bestTime = StopWatch.convertToString(game.getPreferenceManager().getBestScore());
        
        table.add("Best Time: " + bestTime);
		
		stage.addActor(table);

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
