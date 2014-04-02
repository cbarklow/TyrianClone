package com.chrisbarklow.tyrianclone.screens.scene2d;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.chrisbarklow.tyrianclone.TyrianClone;
import com.chrisbarklow.tyrianclone.domain.Ship;
import com.chrisbarklow.tyrianclone.screens.AbstractScreen;

public class Ship2D extends Image {
		
	private static final float MAX_HORIZONTAL_SPEED = ( AbstractScreen.GAME_VIEWPORT_WIDTH / 1.6f );
	private static final float MAX_VERTICAL_SPEED = ( AbstractScreen.GAME_VIEWPORT_HEIGHT / 0.96f );
	private static final float MAX_ACCELERATION = 8; // unit: px/s^2
	
	private Vector2 position; //unitless
	private Vector2 velocity; // unit: px/s^2
	private Vector2 acceleration; // unit: px/s^2
	
	private final Animation tiltAnimation;
	
	private float tiltAnimationStateTime;
	
	/**
     * Creates a new ship2D.
     */
    private Ship2D(Ship ship, Array<AtlasRegion> tiltAnimationFrames ){
        // the super constructor does a lot of work
        super( tiltAnimationFrames.get( 0 ) );
        
        this.position = new Vector2();
        this.velocity = new Vector2();
        this.acceleration = new Vector2();
        
        // create the tilt animation (each frame will be shown for 0.15
        // seconds when the animation is active)
        this.tiltAnimation = new Animation(0.15f, tiltAnimationFrames);

        // set some basic attributes
        this.setTouchable(Touchable.disabled);
    }
    
    /**
     * Factory method to create a ship2D.
     */
    public static Ship2D create(Ship ship, TextureAtlas textureAtlas ){
    	// load all the regions of our ship in the image atlas
    	Array<AtlasRegion> regions = textureAtlas.findRegions( ship.getShipModel().getPreviewImageName());
        
    	// we just want the regions that make up an animation, so we should
        // ignore the regions that have a negative index (hence are not part of
        // an animation);
        // this is necessary because we use a static ship image in the start
        // game screen, and that image's name is reused for the images
        // that compose the ship tilt animation
//        Iterator<AtlasRegion> regionIterator = regions.iterator();
//        while( regionIterator.hasNext() ) {
//            if( regionIterator.next().index < 0 ) {
//                regionIterator.remove();
//            }
//        }

        // finally, create the ship
        Ship2D ship2d = new Ship2D( ship, regions );
        return ship2d;
    }
    
    public void moveShip(float delta){
    	position.set(this.getX(), this.getY());
    	
    	if(Gdx.input.isPeripheralAvailable( Peripheral.Accelerometer )){
    		Gdx.input.getAccelerometerX(); // points to the right (when in portrait orientation)
    		Gdx.input.getAccelerometerY(); // points upwards (when in portrait orientation)
    		Gdx.input.getAccelerometerZ(); // points to the front of the display (coming out of the screen)
    		
    		if( TyrianClone.debug_mode ) {
    		    Gdx.app.debug( TyrianClone.LOG,
    		        Gdx.input.getAccelerometerX() + "," +
    		        Gdx.input.getAccelerometerY() + "," +
    		        Gdx.input.getAccelerometerZ() );
    		}
    		
    		// output the accelerometer axis
            if(TyrianClone.debug_mode) {
                Gdx.app.debug( TyrianClone.LOG,
                    Gdx.input.getAccelerometerX() + "," + Gdx.input.getAccelerometerY() + ","
                        + Gdx.input.getAccelerometerZ() );
            }

            // x: 4 (back), 2 (still), 0 (forward)
            // I'll translate the above values to (-2,0,2) so that my next
            // calculations are simpler
            float accelerationX = ( Gdx.input.getAccelerometerX() - 2f );
            if( accelerationX < - 2f ) accelerationX = - 2f;
            else if( accelerationX > 2f ) accelerationX = 2f;

            // y: -2 (left), 0 (still), 2 (right)
            float accelerationY = Gdx.input.getAccelerometerY();
            if( accelerationY < - 2f ) accelerationY = - 2f;
            else if( accelerationY > 2f ) accelerationY = 2f;

            // 2 means 100% of acceleration, so let's adjust the values
            accelerationX /= 2;
            accelerationY /= 2;

            // notice the inverted axis because the game is displayed in
            // landscape mode
            acceleration.x = ( accelerationY * MAX_ACCELERATION );
            acceleration.y = ( - accelerationX * MAX_ACCELERATION );    		
    	} else {
    		// calculate the horizontal and vertical acceleration
            acceleration.x = ( Gdx.input.isKeyPressed( Input.Keys.LEFT ) ? - MAX_ACCELERATION
                : ( Gdx.input.isKeyPressed( Input.Keys.RIGHT ) ? MAX_ACCELERATION : 0 ) );
            acceleration.y = ( Gdx.input.isKeyPressed( Input.Keys.UP ) ? MAX_ACCELERATION
                : ( Gdx.input.isKeyPressed( Input.Keys.DOWN ) ? - MAX_ACCELERATION : 0 ) );

            // note that when the keys are not pressed, the acceleration will be
            // zero, so the ship's velocity won't be affected

    	}
    	
    	// apply the delta on the acceleration
        acceleration.scl(delta);

        // modify the ship's velocity
        velocity.add(acceleration);

        // check the max speed
        if( velocity.x < 0 ) {
            velocity.x = Math.max( velocity.x, - MAX_HORIZONTAL_SPEED * delta );
        } else if( velocity.x > 0 ) {
            velocity.x = Math.min( velocity.x, MAX_HORIZONTAL_SPEED * delta );
        }
        if( velocity.y < 0 ) {
            velocity.y = Math.max( velocity.y, - MAX_VERTICAL_SPEED * delta );
        } else if( velocity.y > 0 ) {
            velocity.y = Math.min( velocity.y, MAX_VERTICAL_SPEED * delta );
        }

        // update the ship's position
        position.add( velocity );

        // make sure the ship is inside the stage
        if( position.x < 0 ) {
            position.x = 0;
            velocity.x = 0;
        } else if( position.x > this.getStage().getWidth() - this.getWidth()) {
            position.x = this.getStage().getWidth() - this.getWidth();
            velocity.x = 0;
        }
        if( position.y < 0 ) {
            position.y = 0;
            velocity.y = 0;
        } else if( position.y > this.getStage().getHeight() - this.getHeight()) {
            position.y = this.getStage().getHeight() - this.getHeight();
            velocity.y = 0;
        }

    	
    	this.setX(position.x);
    	this.setY(position.y);
    }
    
    private void tiltShip(float delta){
    	// the animation's frame to be shown
        TextureRegion frame;
     
        // find the appropriate frame of the tilt animation to be drawn
        if( velocity.x < 0 ) {
            frame = tiltAnimation.getKeyFrame( tiltAnimationStateTime += delta, false );
            if( frame.getRegionWidth() < 0 ) frame.flip( true, false );
        } else if( velocity.x > 0 ) {
            frame = tiltAnimation.getKeyFrame( tiltAnimationStateTime += delta, false );
            if( frame.getRegionWidth() > 0 ) frame.flip( true, false );
        } else {
            tiltAnimationStateTime = 0;
            frame = tiltAnimation.getKeyFrame( 0, false );
        }
     
        // there is no performance issues when setting the same frame multiple
        // times as the current region (the call will be ignored in this case)
        setDrawable(new TextureRegionDrawable(frame));
    }
    
    @Override
    public void act(float delta){
    	super.act(delta);
    	moveShip(delta);
    	tiltShip(delta);
    }

}
