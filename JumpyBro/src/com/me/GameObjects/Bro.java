package com.me.GameObjects;


import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.me.JBHelpers.AssetLoader;

public class Bro {

	private Vector2 position;
	private Vector2 acceleration;
	private Vector2 velocity;
	
	private float rotation;
	private int height;
	private int width;
	
	private boolean isAlive = true;
	private boolean isJumping;
	private int jumpsLeft;
	private Circle boundingCircle;
	
	public Bro (float x, float y, int width, int height) {
		this.width = width;
		this.height = height;
		
		position = new Vector2(x, y);
		acceleration = new Vector2(0, 460);
		velocity = new Vector2(0,0);
		
		boundingCircle = new Circle();
		this.jumpsLeft = 6;
		
	}
	
	public void update(float delta) {
		
		velocity.add(acceleration.cpy().scl(delta));
		
		if (velocity.y > 200) {
			velocity.y = 100;
		}
		// Rotate counterclockwise
        if (velocity.y < 0) {
            rotation -= 600 * delta;

            if (rotation < -20) {
                rotation = -20;
            }
        }
        
        
        // Set the circle's center to be (9, 6) with respect to the bird.
        // Set the circle's radius to be 6.5f;
        boundingCircle.set(position.x + 9, position.y + 6, 6.5f);
        
		position.add(velocity.cpy().scl(delta));
		
		// Rotate clockwise
        if (isFalling()) {
            rotation += 480 * delta;
            if (rotation > 90) {
                rotation = 90;
            }

        }
        
       // if (!isAlive) {
        	//rotation = -180;
     //   }
		
	}
	public void die() {
		isAlive = false;
		velocity.y = 0;
		
	}
	
	public void decelerate() {
	    // We want the bird to stop accelerating downwards once it is dead.
	    acceleration.y = 0;
	}
	
	public void land(int y) {
		decelerate();
		velocity.y = 0;
		position.y = y;
		rotation = 0;
	    this.jumpsLeft = 6;
	    isJumping = false;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public boolean isFalling() {
		return velocity.y > 110;
	}
	
	public boolean isJumping(){
		return isJumping;
	}
	
	public boolean shouldntFlap() {
		return velocity.y > 70;
	}
	
	public void onClick() {
		
		if (isAlive && this.jumpsLeft > 0) { 
		       AssetLoader.jump.play();
		       velocity.y = -210;
		       acceleration.y = 450;
		       this.jumpsLeft += -1; 
		       isJumping = true;
		   }
		
	}
	
	public void onRestart(int y) {
		rotation = 0;
        position.y = y;
        velocity.x = 0;
        velocity.y = 0;
        acceleration.x = 0;
        acceleration.y = 450;
        isAlive = true;
	}
	
	public float getX() {
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	 
	public float getRotation() {
		return rotation;
	}
	
	public Circle getBoundingCircle() {
        return boundingCircle;
    }

}
