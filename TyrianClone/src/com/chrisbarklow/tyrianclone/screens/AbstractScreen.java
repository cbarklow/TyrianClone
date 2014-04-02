package com.chrisbarklow.tyrianclone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.chrisbarklow.tyrianclone.TyrianClone;

public class AbstractScreen implements Screen {
	
	// the fixed viewport dimensions (ratio: 1.6)
    public static final int GAME_VIEWPORT_WIDTH = 400, GAME_VIEWPORT_HEIGHT = 240;
//    public static final int MENU_VIEWPORT_WIDTH = 800, MENU_VIEWPORT_HEIGHT = 480;
	
	protected final TyrianClone game;
	protected final SpriteBatch batch;	
	protected final Stage stage;
	
	
	private TextureAtlas atlas;
	private Table table;
	
	private Skin skin;
	
	public AbstractScreen(TyrianClone game){
		this.game = game;
//		int width = ( isGameScreen() ? GAME_VIEWPORT_WIDTH : MENU_VIEWPORT_WIDTH );
//        int height = ( isGameScreen() ? GAME_VIEWPORT_HEIGHT : MENU_VIEWPORT_HEIGHT );
		this.stage = new Stage();
		
		//stage.setViewport(new StretchViewport(width, height, stage.getCamera()));
		//stage.setViewport(new FillViewport(width, height, stage.getCamera()));
		//stage.setViewport(new FitViewport(width, height, stage.getCamera()));
		//stage.setViewport(new ExtendViewport(width, height, stage.getCamera()));
		ScreenViewport svp = new ScreenViewport();
		//svp.update(width, height, true);
		stage.setViewport(svp);
		
		this.batch = new SpriteBatch();		
	}
	
	protected String getName(){
        return getClass().getSimpleName();
    }
	
	protected boolean isGameScreen()
    {
        return false;
    }
	
	protected Skin getSkin(){
		if(skin == null){
			TextureAtlas atlas = new TextureAtlas(Gdx.files.internal( "skin/uiskin.atlas" ));
			skin = new Skin(Gdx.files.internal( "skin/uiskin.json" ), atlas);			
		}
		
		return skin;
	}
	
	public TextureAtlas getAtlas(){
		if(atlas == null){
			atlas = new TextureAtlas(Gdx.files.internal("image-atlas/texturepacker.txt"));
		}
		return atlas;
	}
	
	protected Table getTable(){
        if( table == null ) {
            table = new Table( getSkin() );
            table.setFillParent( true );
            if( TyrianClone.debug_mode ) {
                table.debug();
            }
            stage.addActor( table );
        }
        return table;
    }
	 	
	@Override
	public void render(float delta) {
		//update and draw stage actors
		stage.act(delta);
		
		//the following code clears the screen with the given RGB color
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
		stage.draw();
		
		// draw the table debug lines
        //Table.drawDebug( stage );
	}

	@Override
	public void resize(int width, int height) {
		Gdx.app.log(TyrianClone.LOG, "Resizing screen: " + getName() + " to " + width + " x " + height);
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void show() { 
		Gdx.app.log(TyrianClone.LOG, "Showing screen: " + getName());
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		Gdx.app.log(TyrianClone.LOG, "Dipsosing screen: " + getName());
		//dispose of the collaborators
		stage.dispose();
		batch.dispose();

	}

}
