package com.chrisbarklow.tyrianclone.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.chrisbarklow.tyrianclone.PlayerShip;
import com.chrisbarklow.tyrianclone.TyrianClone;

public class Bullet {
	
	public TextureRegion textureRegion;
	public Vector2 position;
	public Vector2 heading;
	public float speed;
	public Rectangle boundingRect;
	public float width;
	public float height;
	private PlayerShip playerShip;
	
	public Bullet(Vector2 startPosition, Vector2 heading, PlayerShip playerShip){
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("image-atlas/bullet.txt"));
		TextureRegion bulletRegion = atlas.findRegion("bullet");
		this.width = (1/32f) * bulletRegion.getRegionWidth();
		this.height = (1/32f) * bulletRegion.getRegionHeight();
		
		this.textureRegion = bulletRegion;
		this.position = startPosition;
		this.heading = heading;
		boundingRect = new Rectangle(this.position.x, this.position.y, width, height);
		this.playerShip = playerShip;
	}
	
	public void update(float delta){
		this.position.add(this.heading);
		this.boundingRect.x = this.position.x;
		this.boundingRect.y = this.position.y;
		
		checkPlayerCollision();
	}
	
	private void checkPlayerCollision(){
		if(this.boundingRect.overlaps(playerShip.boundingRect)){
			Gdx.app.log(TyrianClone.LOG, "Bullet is intersecting player");
			playerShip.killPlayer();
		}
	}
	
	public void draw(Batch batch){
		batch.draw(this.textureRegion, this.position.x, this.position.y, this.width, this.height);
	}

}
