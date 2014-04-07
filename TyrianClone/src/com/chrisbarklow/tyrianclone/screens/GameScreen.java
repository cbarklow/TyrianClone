package com.chrisbarklow.tyrianclone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.chrisbarklow.tyrianclone.PlayerShip;
import com.chrisbarklow.tyrianclone.TyrianClone;
import com.chrisbarklow.tyrianclone.managers.MusicManager.TyrianCloneMusic;
import com.chrisbarklow.tyrianclone.utils.StopWatch;

public class GameScreen implements Screen {
	
	OrthographicCamera camera;
	SpriteBatch batch;
	TiledMap map;
	OrthogonalTiledMapRenderer renderer;
	TyrianClone game;
	TextureRegion shipTexture;
	TextureAtlas atlas;
	PlayerShip ship;
	BitmapFont font;
	StopWatch stopWatch;
	float cameraYMove;
	Vector2 cameraOffset;
	Rectangle viewport;
	
	public GameScreen(TyrianClone game){
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera();
		
		//set how many tile units to be displayed in the game. This setup would display 16x10
		camera.setToOrtho(false, (w/h) * 10, 10);		
		
		font = new BitmapFont();
		batch = new SpriteBatch();
		
		//load the map, set the scale to 1/32 (1 unit = 32 pixels)
		//the bigger the fraction, the more zoomed in the game feels
		map = new TmxMapLoader().load("tyrian-clone1.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1/16f);	      
		
		atlas = new TextureAtlas(Gdx.files.internal("image-atlas/pages.txt"));
		shipTexture = atlas.findRegion("level-screen/ship-model-gencore-phoenix", 0);
		ship = new PlayerShip();
		ship.WIDTH = (1/16f) * shipTexture.getRegionWidth();
		ship.HEIGHT = (1/16f) * shipTexture.getRegionHeight();		
		this.game = game;
		
		viewport = new Rectangle(0, 0, camera.viewportWidth, camera.viewportHeight);
		
		stopWatch = new StopWatch();
		stopWatch.start();
	}

	@Override
	public void render(float delta) {
		//the following code clears the screen with the given RGB color
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
		viewport.y += .01f;
		
		camera.translate(0, .01f, 0);
		
		camera.update();
		
		renderer.setView(camera);
		renderer.render();
		
		//float cameraY = camera.position.y - 5;
		ship.updateShip(delta, camera.viewportWidth, camera.viewportHeight, viewport);
		ship.tiltShip(delta);
		
		Batch renderBatch = renderer.getSpriteBatch();
		renderBatch.begin();
		ship.draw(renderBatch);
		renderBatch.end();
		
		batch.begin();				
		font.draw(batch
				, "Time: " + stopWatch.getElapsedTimeMin() + ":" + stopWatch.getElapsedTimeSecs() + ":" + stopWatch.getElapsedTimeMilli()
				, Gdx.graphics.getWidth()/2
				, Gdx.graphics.getHeight() - font.getCapHeight());
		batch.end();
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// play the level music
        game.getMusicManager().play( TyrianCloneMusic.LEVEL );

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
		Gdx.app.log("GameScreen:", "dispose() called");		
		batch.dispose();
		map.dispose();		
	}

}
