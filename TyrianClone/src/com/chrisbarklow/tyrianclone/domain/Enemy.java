package com.chrisbarklow.tyrianclone.domain;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.chrisbarklow.tyrianclone.PlayerShip;

public class Enemy {
	
	static final float WAIT_TIME = 5f;
	
	PlayerShip playerShip;
	TextureAtlas atlas;
	public TextureRegion enemyTexture;
	Vector2 position;
	public float width;
	public float height;
	private float shootTimer;
	ArrayList<Bullet> bullets;
	ArrayList<Bullet> removeBulletsList;
	
	public Enemy(PlayerShip playerShip){
		this.playerShip = playerShip;
		atlas = new TextureAtlas(Gdx.files.internal("image-atlas/enemy.txt"));
		enemyTexture = atlas.findRegion("enemy");
		position = new Vector2();
		bullets = new ArrayList<Bullet>();
		removeBulletsList = new ArrayList<Bullet>();
		
		this.width = (1/32f) * this.enemyTexture.getRegionWidth();
		this.height = (1/32f) * this.enemyTexture.getRegionHeight();
	}
	
	public void setPositionX(float x){
		position.x = x;		
	}
	
	public void setPositionY(float y){
		position.y = y;
	}
	
	public void update(float delta){	
		shootTimer += delta;
		
		if(shootTimer >= WAIT_TIME){
			Random random = new Random();
			int value = random.nextInt(2);
			
			if(value == 0)
				fireHomingBullet();
			else
				fireBulletBurst();
			
			shootTimer -= WAIT_TIME;
		}
		
		for(Bullet b : bullets){
			b.update(delta);
		}
		
		checkBulletsOffScreen();
	}
	
	private void checkBulletsOffScreen(){
		for(Bullet b : bullets){
			if (b.position.x < 0 - b.width || b.position.x > 16
					|| b.position.y < 0 - b.height || b.position.y > 10){
				removeBulletsList.add(b);
			}
		}
		
		for(Bullet b : removeBulletsList){
			if(bullets.contains(b)){
				bullets.remove(b);
			}
		}
		
		removeBulletsList.clear();
	}
	
	private void fireBulletBurst(){
		
		Bullet b1 = new Bullet(new Vector2(this.position.x + .5f, this.position.y + .5f)
				, new Vector2(.01f,.01f)
				, this.playerShip);
		bullets.add(b1);
		
		Bullet b2 = new Bullet(new Vector2(this.position.x + .5f, this.position.y + .5f)
				, new Vector2(.01f, 0f)
				, this.playerShip);		
		bullets.add(b2);
		
		Bullet b3 = new Bullet(new Vector2(this.position.x + .5f, this.position.y + .5f)
				, new Vector2(.01f, -.01f)
				, this.playerShip);		
		bullets.add(b3);
		
		Bullet b4 = new Bullet(new Vector2(this.position.x + .5f, this.position.y + .5f)
				, new Vector2(0f,-.01f)
				, this.playerShip);		
		bullets.add(b4);
		
		Bullet b5 = new Bullet(new Vector2(this.position.x + .5f, this.position.y + .5f)
				, new Vector2( -.01f,-.01f)
				, this.playerShip);		
		bullets.add(b5);
		
		Bullet b6 = new Bullet(new Vector2(this.position.x + .5f, this.position.y + .5f)
				, new Vector2(-.01f, 0f)
				, this.playerShip);		
		bullets.add(b6);
		
		Bullet b7 = new Bullet(new Vector2(this.position.x + .5f, this.position.y + .5f)
				, new Vector2(-.01f, .01f)
				, this.playerShip);
		bullets.add(b7);
		
		Bullet b8 = new Bullet(new Vector2(this.position.x + .5f, this.position.y + .5f)
				, new Vector2(0f, .01f)
				, this.playerShip);		
		bullets.add(b8);
	}
	
	private void fireHomingBullet(){
	    Vector2 playerShipPosition = new Vector2(playerShip.position.x, playerShip.position.y);
		Vector2 homingVector = new Vector2(playerShipPosition.sub(this.position));
		
		homingVector.scl(.01f);
		
		Bullet b = new Bullet(new Vector2(this.position.x + .5f, this.position.y + .5f)
				, homingVector
				, this.playerShip);
		bullets.add(b);
			
	}
	
	private void fireShot(){		
		Bullet b = new Bullet(new Vector2(this.position.x + .5f, this.position.y + .5f)
				, new Vector2(.01f,.01f)
				, this.playerShip);
		b.width = (1/32f) * b.textureRegion.getRegionWidth();
		b.height = (1/32f) * b.textureRegion.getRegionHeight();
		bullets.add(b);
	}
	
	public void draw(Batch batch){
		batch.draw(enemyTexture, position.x, position.y, width, height);
		
		for(Bullet b: bullets){
			b.draw(batch);
		}
	}

}
