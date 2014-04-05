package com.chrisbarklow.tyrianclone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.chrisbarklow.tyrianclone.utils.VectorUtils;

public class PlayerShip {
	
	public float WIDTH;
	public float HEIGHT;
	public static final float MAX_SPEED = 24;
    public static final float MAX_ACCELERATION = 2;
    public static final float MAX_DECELERATION = MAX_ACCELERATION / 2;

	public final Vector2 position = new Vector2();
	public final Vector2 velocity = new Vector2();
	public final Vector2 acceleration = new Vector2();
	
	private final Animation tiltAnimation;
	private float tiltAnimationStateTime;
    private TextureRegion frame;    
	
	public PlayerShip(){
		TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("image-atlas/pages.txt"));
		// load all the regions of our ship in the image atlas
    	Array<AtlasRegion> regions = textureAtlas.findRegions("level-screen/ship-model-gencore-phoenix");
    	
    	frame = textureAtlas.findRegion("level-screen/ship-model-gencore-phoenix", 0);
		
		// create the tilt animation (each frame will be shown for 0.15
        // seconds when the animation is active)
        this.tiltAnimation = new Animation(0.15f, regions);
	}
	
	@SuppressWarnings("static-access")
	public void updateShip(float delta, float viewportWidth, float viewportHeight){
		//position.set(this.getX(), this.getY());
    	
    	if(Gdx.input.isPeripheralAvailable( Peripheral.Accelerometer )){
    		
    		if( TyrianClone.debug_mode ) {
    		    Gdx.app.debug( TyrianClone.LOG,
    		        Gdx.input.getAccelerometerX() + "," +
    		        Gdx.input.getAccelerometerY() + "," +
    		        Gdx.input.getAccelerometerZ() );
    		}
    	
            this.acceleration.set(Gdx.input.getAccelerometerY(), Gdx.input.getAccelerometerX());
            
            VectorUtils.adjustByRange(this.acceleration, -2, 2);

         // set the input deadzone
            if( ! VectorUtils.adjustDeadzone( this.acceleration, 1f, 0f ) ) {
                // we're out of the deadzone, so let's adjust the acceleration
                // (2 is 100% of the max acceleration)
                this.acceleration.x = ( this.acceleration.x / 2 * this.MAX_ACCELERATION );
                this.acceleration.y = ( - this.acceleration.y / 2 * this.MAX_ACCELERATION );
            } 		
    	} else {
    		// calculate the horizontal and vertical acceleration
            this.acceleration.x = ( Gdx.input.isKeyPressed( Input.Keys.LEFT ) ? - this.MAX_ACCELERATION
                : ( Gdx.input.isKeyPressed( Input.Keys.RIGHT ) ? this.MAX_ACCELERATION : 0 ) );
            this.acceleration.y = ( Gdx.input.isKeyPressed( Input.Keys.UP ) ? this.MAX_ACCELERATION
                : ( Gdx.input.isKeyPressed( Input.Keys.DOWN ) ? - this.MAX_ACCELERATION : 0 ) );

            // note that when the keys are not pressed, the acceleration will be
            // zero, so the ship's velocity won't be affected

    	}
    	
    	// if there is no acceleration and the ship is moving, let's calculate
        // an appropriate deceleration
        if( this.acceleration.len() == 0f && this.velocity.len() > 0f ) {

            // horizontal deceleration
            if( this.velocity.x > 0 ) {
            	this.acceleration.x = - this.MAX_DECELERATION;
                if( this.velocity.x - this.acceleration.x < 0 ) {
                	this.acceleration.x = - ( this.acceleration.x - this.velocity.x );
                }
            } else if( this.velocity.x < 0 ) {
            	this.acceleration.x = this.MAX_DECELERATION;
                if( this.velocity.x + this.acceleration.x > 0 ) {
                	this.acceleration.x = ( this.acceleration.x - this.velocity.x );
                }
            }

            // vertical deceleration
            if( this.velocity.y > 0 ) {
            	this.acceleration.y = - this.MAX_DECELERATION;
                if( this.velocity.y - this.acceleration.y < 0 ) {
                	this.acceleration.y = - ( this.acceleration.y - this.velocity.y );
                }
            } else if( this.velocity.y < 0 ) {
            	this.acceleration.y = this.MAX_DECELERATION;
                if( this.velocity.y + this.acceleration.y > 0 ) {
                	this.acceleration.y = (this.acceleration.y - this.velocity.y );
                }
            }

        }
        
     // modify and check the ship's velocity
        this.velocity.add(this.acceleration );
        VectorUtils.adjustByRange( this.velocity, - this.MAX_SPEED, this.MAX_SPEED );

        // modify and check the ship's position, applying the delta parameter
        this.position.add( this.velocity.x * delta, this.velocity.y * delta );

        // we can't let the ship go off the screen, so here we check the new
        // ship's position against the stage's dimensions, correcting it if
        // needed and zeroing the velocity, so that the ship stops flying in the
        // current direction
        if( VectorUtils.adjustByRangeX( this.position, 0, ( viewportWidth - this.WIDTH ) ) )
        	this.velocity.x = 0;
        if( VectorUtils.adjustByRangeY( this.position, 0, ( viewportHeight - this.HEIGHT ) ) )
        	this.velocity.y = 0;
	}
	
	public void tiltShip(float delta){
    	// the animation's frame to be shown

     
        // find the appropriate frame of the tilt animation to be drawn
        if( velocity.x < 0 ) {
        	frame = tiltAnimation.getKeyFrame( tiltAnimationStateTime += delta, false );
            if(frame.isFlipX()) frame.flip(true, false);
        } else if( velocity.x > 0 ) {
            frame = tiltAnimation.getKeyFrame( tiltAnimationStateTime += delta, false );
            if(!frame.isFlipX()) frame.flip( true, false );            
        } else {
            tiltAnimationStateTime = 0;
            frame = tiltAnimation.getKeyFrame( 0, false );
        }
        	
     
        // there is no performance issues when setting the same frame multiple
        // times as the current region (the call will be ignored in this case)
        //setDrawable(tiltAnimationDrawables.get(frame));
    }
	
	public void draw(Batch batch){
		batch.draw(frame, this.position.x, this.position.y, this.WIDTH, this.HEIGHT);
	}
}
