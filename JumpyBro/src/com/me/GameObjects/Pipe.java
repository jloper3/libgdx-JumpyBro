package com.me.GameObjects;

import java.util.Random;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Pipe extends Scrollable {
	
	private Random r;
	
	private Rectangle skullUp, skullDown, barUp, barDown;

    public static final int VERTICAL_GAP = 45;
    public static final int SKULL_WIDTH = 24;
    public static final int SKULL_HEIGHT = 11;
    private float groundY;
    
    private boolean isScored = false;

	public Pipe(float x, float y, int width, int height, float scrollSpeed, float groundY) {
		super(x, y, width, height, scrollSpeed);
		r = new Random();
		
		// Hit boxes
		//skullUp = new Rectangle();
        skullDown = new Rectangle();
       // barUp = new Rectangle();
        barDown = new Rectangle();

        this.groundY = groundY;
	}
	
	public void onRestart(float x, float scrollSpeed) {
        velocity.x = scrollSpeed;
        reset(x);
        
    }
	
	@Override
    public void update(float delta) {
        // Call the update method in the superclass (Scrollable)
        super.update(delta);

        // The set() method allows you to set the top left corner's x, y
        // coordinates,
        // along with the width and height of the rectangle

        //barUp.set(position.x, position.y, width, height);
        barDown.set(position.x, position.y + height + VERTICAL_GAP, width,
               groundY - (position.y + height + VERTICAL_GAP));

        // Our skull width is 24. The bar is only 22 pixels wide. So the skull
        // must be shifted by 1 pixel to the left (so that the skull is centered
        // with respect to its bar).
        
        // This shift is equivalent to: (SKULL_WIDTH - width) / 2
        //skullUp.set(position.x - (SKULL_WIDTH - width) / 2, position.y + height
               // - SKULL_HEIGHT, SKULL_WIDTH, SKULL_HEIGHT);
       skullDown.set(position.x - (SKULL_WIDTH - width) / 2, barDown.y,
               SKULL_WIDTH, SKULL_HEIGHT);

    }
	
	@Override
    public void reset(float newX) {
        // Call the reset method in the superclass (Scrollable)
        super.reset(newX);
        // Change the height to a random number
        height = r.nextInt(100) + 15;
        isScored = false;
    }
	
	public boolean collides(Bro bro) {
        if (position.x < bro.getX() + bro.getWidth()) {
            return (//Intersector.overlaps(bro.getBoundingCircle(), barUp)
                     Intersector.overlaps(bro.getBoundingCircle(), barDown)
                   // || Intersector.overlaps(bro.getBoundingCircle(), skullUp) 
                      || Intersector.overlaps(bro.getBoundingCircle(), skullDown));
        }
        return false;
    }
	
	public Rectangle getBarDown() {
		return barDown;
	}
	
	public Rectangle getBarUp() {
		return barUp;
	}
	
	public Rectangle getSkullUp() {
		return skullUp;
	}
	
	public Rectangle getSkullDown() {
		return skullDown;
	}
	
	public boolean isScored() {
        return isScored;
    }

    public void setScored(boolean b) {
        isScored = b;
    }

}
