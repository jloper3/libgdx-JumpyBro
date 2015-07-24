package com.me.GameObjects;


import java.util.Random;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;

public class FireBall extends Scrollable {
	
	private Random r;
	
	private Circle boundingCircle;

	public FireBall(float x, float y, int width, int height, float scrollSpeed) {
		//r = new Random();
		super(x, y, width, height, scrollSpeed);
		r = new Random();
		//position.y = r.nextInt(90) + 15;
		boundingCircle = new Circle();    
	}
	
	@Override
	public void update(float delta) {
		// Call the update method in the superclass (Scrollable)
        super.update(delta);
		// Set the circle's center to be (9, 6) with respect to the bird.
        // Set the circle's radius to be 6.5f;
		boundingCircle.set(position.x + 9, position.y + 6, 6.5f);
	}
	
	
	public boolean collides(Bro bro) {
        if (position.x < bro.getX() + bro.getWidth()) {
            return (Intersector.overlaps(bro.getBoundingCircle(), boundingCircle)
                    || Intersector.overlaps(bro.getBoundingCircle(), boundingCircle)
                    || Intersector.overlaps(bro.getBoundingCircle(), boundingCircle) 
                    || Intersector.overlaps(bro.getBoundingCircle(), boundingCircle));
        }
        return false;
    }
	
	@Override
    public void reset(float newX) {
        // Call the reset method in the superclass (Scrollable)
        super.reset(newX);
        // Change the height to a random number
        position.y = r.nextInt(300);
        velocity.x = -80 - (r.nextInt(80));
        velocity.y = r.nextInt(80);
        
    }
	
	public void onRestart(float x){
		reset(x);
		
	}

}