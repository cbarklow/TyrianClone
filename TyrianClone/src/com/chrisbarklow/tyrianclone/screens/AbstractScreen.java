package com.chrisbarklow.tyrianclone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.chrisbarklow.tyrianclone.TyrianClone;

public class AbstractScreen implements Screen {
	
	protected final TyrianClone game;
	protected final SpriteBatch batch;	
	protected final Stage stage;	
	
	private Skin skin;
	
	public AbstractScreen(TyrianClone game){
		this.game = game;
		this.batch = new SpriteBatch();		
		this.stage = new Stage(new ScreenViewport());
	}
	
	protected String getName(){
        return getClass().getSimpleName();
    }
	
	protected Skin getSkin(){
		if(skin == null){
			TextureAtlas atlas = new TextureAtlas(Gdx.files.internal( "uiskin.atlas" ));
			skin = new Skin(Gdx.files.internal( "uiskin.json" ), atlas);			
		}
		
		return skin;
	}
	 
	
	
	@Override
	public void render(float delta) {
		//the following code clears the screen with the given RGB color
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//update and draw stage actors
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		Gdx.app.log(TyrianClone.LOG, "Resizing screen: " + getName() + " to " + width + " x " + height);
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void show() { 
		Gdx.app.log(TyrianClone.LOG, "Showing screen: " + getName());
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
		Gdx.app.log(TyrianClone.LOG, "Dipsosing screen: " + getName());
		//dispose of the collaborators
		stage.dispose();
		batch.dispose();

	}

}
