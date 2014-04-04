package com.chrisbarklow.tyrianclone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.chrisbarklow.tyrianclone.TyrianClone;
import com.chrisbarklow.tyrianclone.managers.MusicManager.TyrianCloneMusic;
import com.chrisbarklow.tyrianclone.utils.VectorUtils;

public class GameScreen implements Screen {
	
	static class Ship{
		static float WIDTH;
		static float HEIGHT;
		private static final float MAX_SPEED = 24;
	    private static final float MAX_ACCELERATION = 2;
	    private static final float MAX_DECELERATION = MAX_ACCELERATION / 2;

		final Vector2 position = new Vector2();
		final Vector2 velocity = new Vector2();
		final Vector2 acceleration = new Vector2();
	}
	
	OrthographicCamera camera;
	SpriteBatch batch;
	TiledMap map;
	OrthogonalTiledMapRenderer renderer;
	TyrianClone game;
	TextureRegion shipTexture;
	TextureAtlas atlas;
	Ship ship;
	BitmapFont font;
	int timeInSeconds = 0;

	
	public GameScreen(TyrianClone game){
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, (w/h) * 10, 10);
		camera.update();
		
		font = new BitmapFont();
		batch = new SpriteBatch();
		
		map = new TmxMapLoader().load("tyrian-clone1.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1/32f);	      
		
		atlas = new TextureAtlas(Gdx.files.internal("image-atlas/pages.txt"));
		shipTexture = atlas.findRegion("level-screen/ship-model-gencore-phoenix", 0);  
		ship.WIDTH = (1/16f) * shipTexture.getRegionWidth();
		ship.HEIGHT = (1/16f) * shipTexture.getRegionHeight();
		ship = new Ship();
		this.game = game;    
	}

	@Override
	public void render(float delta) {
		//the following code clears the screen with the given RGB color
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();			
		renderer.setView(camera);
		renderer.render();
		
		updateShip(delta);
		
		Batch renderBatch = renderer.getSpriteBatch();
		renderBatch.begin();
		renderBatch.draw(shipTexture, ship.position.x, ship.position.y,ship.WIDTH, ship.HEIGHT);
		renderBatch.end();
		
		batch.begin();				
		font.draw(batch, "Time: " + System.currentTimeMillis(), Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight() - font.getCapHeight());
		batch.end();
	}
	
	@SuppressWarnings("static-access")
	private void updateShip(float delta){
		//position.set(this.getX(), this.getY());
    	
    	if(Gdx.input.isPeripheralAvailable( Peripheral.Accelerometer )){
    		
    		if( TyrianClone.debug_mode ) {
    		    Gdx.app.debug( TyrianClone.LOG,
    		        Gdx.input.getAccelerometerX() + "," +
    		        Gdx.input.getAccelerometerY() + "," +
    		        Gdx.input.getAccelerometerZ() );
    		}
    	
            ship.acceleration.set(Gdx.input.getAccelerometerY(), Gdx.input.getAccelerometerX());
            
            VectorUtils.adjustByRange(ship.acceleration, -2, 2);

         // set the input deadzone
            if( ! VectorUtils.adjustDeadzone( ship.acceleration, 1f, 0f ) ) {
                // we're out of the deadzone, so let's adjust the acceleration
                // (2 is 100% of the max acceleration)
                ship.acceleration.x = ( ship.acceleration.x / 2 * ship.MAX_ACCELERATION );
                ship.acceleration.y = ( - ship.acceleration.y / 2 * ship.MAX_ACCELERATION );
            } 		
    	} else {
    		// calculate the horizontal and vertical acceleration
            ship.acceleration.x = ( Gdx.input.isKeyPressed( Input.Keys.LEFT ) ? - ship.MAX_ACCELERATION
                : ( Gdx.input.isKeyPressed( Input.Keys.RIGHT ) ? ship.MAX_ACCELERATION : 0 ) );
            ship.acceleration.y = ( Gdx.input.isKeyPressed( Input.Keys.UP ) ? ship.MAX_ACCELERATION
                : ( Gdx.input.isKeyPressed( Input.Keys.DOWN ) ? - ship.MAX_ACCELERATION : 0 ) );

            // note that when the keys are not pressed, the acceleration will be
            // zero, so the ship's velocity won't be affected

    	}
    	
    	// if there is no acceleration and the ship is moving, let's calculate
        // an appropriate deceleration
        if( ship.acceleration.len() == 0f && ship.velocity.len() > 0f ) {

            // horizontal deceleration
            if( ship.velocity.x > 0 ) {
            	ship.acceleration.x = - ship.MAX_DECELERATION;
                if( ship.velocity.x - ship.acceleration.x < 0 ) {
                	ship.acceleration.x = - ( ship.acceleration.x - ship.velocity.x );
                }
            } else if( ship.velocity.x < 0 ) {
            	ship.acceleration.x = ship.MAX_DECELERATION;
                if( ship.velocity.x + ship.acceleration.x > 0 ) {
                	ship.acceleration.x = ( ship.acceleration.x - ship.velocity.x );
                }
            }

            // vertical deceleration
            if( ship.velocity.y > 0 ) {
            	ship.acceleration.y = - ship.MAX_DECELERATION;
                if( ship.velocity.y - ship.acceleration.y < 0 ) {
                	ship.acceleration.y = - ( ship.acceleration.y - ship.velocity.y );
                }
            } else if( ship.velocity.y < 0 ) {
            	ship.acceleration.y = ship.MAX_DECELERATION;
                if( ship.velocity.y + ship.acceleration.y > 0 ) {
                	ship.acceleration.y = (ship.acceleration.y - ship.velocity.y );
                }
            }

        }
        
     // modify and check the ship's velocity
        ship.velocity.add(ship.acceleration );
        VectorUtils.adjustByRange( ship.velocity, - ship.MAX_SPEED, ship.MAX_SPEED );

        // modify and check the ship's position, applying the delta parameter
        ship.position.add( ship.velocity.x * delta, ship.velocity.y * delta );

        // we can't let the ship go off the screen, so here we check the new
        // ship's position against the stage's dimensions, correcting it if
        // needed and zeroing the velocity, so that the ship stops flying in the
        // current direction
        if( VectorUtils.adjustByRangeX( ship.position, 0, ( camera.viewportWidth - ship.WIDTH ) ) )
        	ship.velocity.x = 0;
        if( VectorUtils.adjustByRangeY( ship.position, 0, ( camera.viewportHeight - ship.HEIGHT ) ) )
        	ship.velocity.y = 0;
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
