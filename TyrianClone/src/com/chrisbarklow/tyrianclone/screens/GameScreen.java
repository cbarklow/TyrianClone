package com.chrisbarklow.tyrianclone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.chrisbarklow.tyrianclone.PlayerShip;
import com.chrisbarklow.tyrianclone.TyrianClone;
import com.chrisbarklow.tyrianclone.domain.Enemy;
import com.chrisbarklow.tyrianclone.managers.MusicManager.TyrianCloneMusic;
import com.chrisbarklow.tyrianclone.utils.StopWatch;

public class GameScreen implements Screen {
	
	OrthographicCamera camera;
	SpriteBatch batch;
	TiledMap map;
	OrthogonalTiledMapRenderer renderer;	
	TyrianClone game;	
	TextureAtlas atlas;
	PlayerShip ship;
	BitmapFont font;
	StopWatch stopWatch;	
	Vector2 cameraOffset;
	Rectangle viewport;
	Enemy enemy1;
	Enemy enemy2;
	Enemy enemy3;
	Enemy enemy4;
	
	
	public GameScreen(TyrianClone game){
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(16, 10);
		
		//set how many tile units to be displayed in the game. This setup would display 16x10
		camera.setToOrtho(false, (w/h) * 10, 10);		
		
		font = new BitmapFont();
		batch = new SpriteBatch();
		
		initPlayer();
		initEnemies();
		
		//load the map, set the scale to 1/16 (1 unit = 16 pixels)
		//the bigger the fraction, the more zoomed in the game feels
		map = new TmxMapLoader().load("tyrian-clone1.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1/32f);	      		
		
		viewport = new Rectangle(0, 0, camera.viewportWidth, camera.viewportHeight);
		
		stopWatch = new StopWatch();
		stopWatch.start();
		
		this.game = game;
	}
	
	private void initPlayer(){
		ship = new PlayerShip();				
	}
	
	private void initEnemies(){
		enemy1 = new Enemy(ship);
		enemy1.setPositionX(1);
		enemy1.setPositionY(1);
		
		enemy2 = new Enemy(ship);
		enemy2.setPositionX(1);
		enemy2.setPositionY(9);
		
		enemy3 = new Enemy(ship);
		enemy3.setPositionX(15);
		enemy3.setPositionY(9);
		
		enemy4 = new Enemy(ship);
		enemy4.setPositionX(15);
		enemy4.setPositionY(1);
	}

	@Override
	public void render(float delta) {
		//the following code clears the screen with the given RGB color
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(!ship.isAlive){		
			this.dispose();
			return;
		}
		
		camera.update();
		
		renderer.setView(camera);
		renderer.render();
		
		ship.updateShip(delta, camera.viewportWidth, camera.viewportHeight, viewport);
		ship.tiltShip(delta);
		
		enemy1.update(delta);
		enemy2.update(delta);
		enemy3.update(delta);
		enemy4.update(delta);
		
		Batch renderBatch = renderer.getSpriteBatch();
		renderBatch.begin();		
		enemy1.draw(renderBatch);
		enemy2.draw(renderBatch);
		enemy3.draw(renderBatch);
		enemy4.draw(renderBatch);
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
		
	}

	@Override
	public void pause() {
		stopWatch.stop();

	}

	@Override
	public void resume() {
		stopWatch.start();
	}

	@Override
	public void dispose() {
		Gdx.app.log("GameScreen:", "dispose() called");		
		batch.dispose();
		map.dispose();
				
		long bestTime = game.getPreferenceManager().getBestScore();
		
		if(stopWatch.getTotalElapsedMilli() > bestTime){
			game.getPreferenceManager().setBestScore("" + stopWatch.getTotalElapsedMilli());
		}
		
		game.setScreen(new StartScreen(game));
	}

}
