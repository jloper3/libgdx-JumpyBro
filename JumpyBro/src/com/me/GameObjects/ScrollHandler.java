package com.me.GameObjects;

import java.util.Random;

import com.me.GameWorld.GameWorld;
import com.me.JBHelpers.AssetLoader;

public class ScrollHandler {
	
	private Grass frontGrass, backGrass;
	private Pipe pipe1, pipe2, pipe3;
	private FireBall fireBall;
	private BackGround bg1,bg2;
	private Cloud cloud1, cloud2;
	
	// ScrollHandler will use the2 constants below to determine
    // how fast we need to scroll and also determine
    // the size of the gap between each pair of pipes.

    // Capital letters are used by convention when naming constants.
    public static final int SCROLL_SPEED = -59;
    public static final int BACKGROUND_SPEED = -15;
    public static final int PIPE_GAP = 200;
    private Random r;
    private GameWorld myWorld;

    // Constructor receives a float that tells us where we need to create our 
    // Grass and Pipe objects.
    public ScrollHandler(GameWorld world, float yPos) {
    	this.myWorld = world;
    	frontGrass = new Grass(0, yPos, 143, 11, SCROLL_SPEED);
        backGrass = new Grass(frontGrass.getTailX(), yPos, 143, 11,
                SCROLL_SPEED); 
        cloud1 = new Cloud(0,yPos - 60,143,56,BACKGROUND_SPEED / 2);
        cloud2 = new Cloud(cloud1.getTailX(),yPos - 60,143,56,BACKGROUND_SPEED / 2 );
        bg1 = new BackGround(0,yPos - 50,143,56,BACKGROUND_SPEED);
        bg2 = new BackGround(bg1.getTailX(),yPos - 50 ,143,56,BACKGROUND_SPEED);
        
        r = new Random();
        pipe1 = new Pipe(150 + r.nextInt(PIPE_GAP), 0, 22, 60, SCROLL_SPEED, yPos);
        pipe2 = new Pipe(pipe1.getTailX() + r.nextInt(PIPE_GAP), 0, 22, 70, SCROLL_SPEED, yPos);
        pipe3 = new Pipe(pipe2.getTailX() + r.nextInt(PIPE_GAP), 0, 22, 60, SCROLL_SPEED, yPos);
        
        fireBall = new FireBall(210,r.nextInt(136),7,7,SCROLL_SPEED - r.nextInt(20));
        fireBall.velocity.y = r.nextInt(100);
        
    }
    
    public void update(float delta) {
        // Update our objects
        frontGrass.update(delta);
        backGrass.update(delta);
        pipe1.update(delta);
        pipe2.update(delta);
        pipe3.update(delta);
        fireBall.update(delta);
        bg1.update(delta);
        bg2.update(delta);
        cloud1.update(delta);
        cloud2.update(delta);

        // Check if any of the pipes are scrolled left,
        // and reset accordingly
        if (pipe1.isScrolledLeft()) {
            pipe1.reset(pipe3.getTailX() + r.nextInt(150) * 3);
        } else if (pipe2.isScrolledLeft()) {
            pipe2.reset(pipe1.getTailX() + r.nextInt(150) * 3);

        } else if (pipe3.isScrolledLeft()) {
            pipe3.reset(pipe2.getTailX() + r.nextInt(150) * 3);
        }
        if (bg1.isScrolledLeft()) {
        	bg1.reset(bg2.getTailX());
        }
        if (bg2.isScrolledLeft()) {
        	bg2.reset(bg1.getTailX());
        }
        if (cloud1.isScrolledLeft()) {
        	cloud1.reset(cloud2.getTailX());
        }
        if (cloud2.isScrolledLeft()) {
        	cloud2.reset(cloud1.getTailX());
        }
        if (fireBall.isScrolledLeft()) {
        	fireBall.reset(210);
        }

        // Same with grass
        if (frontGrass.isScrolledLeft()) {
            frontGrass.reset(backGrass.getTailX());

        } else if (backGrass.isScrolledLeft()) {
            backGrass.reset(frontGrass.getTailX());

        }
    }

    
    public void stop() {
        frontGrass.stop();
        backGrass.stop();
        pipe1.stop();
        pipe2.stop();
        pipe3.stop();
        fireBall.stop();
        bg1.stop();
        bg2.stop();
        cloud1.stop();
        cloud2.stop();
    }
    
 // Return true if ANY pipe hits the bird.
    public boolean collides(Bro bro) {
       
    	if (!pipe1.isScored()
                && pipe1.getX() + (pipe1.getWidth() / 2) < bro.getX()
                        + bro.getWidth()) {
            addScore(1);
            pipe1.setScored(true);
            AssetLoader.coin.play();
    	}
    	
    	if (!pipe2.isScored()
                && pipe2.getX() + (pipe2.getWidth() / 2) < bro.getX()
                        + bro.getWidth()) {
            addScore(1);
            pipe2.setScored(true);
            AssetLoader.coin.play();
    	}
    	if (!pipe3.isScored()
                && pipe3.getX() + (pipe3.getWidth() / 2) < bro.getX()
                        + bro.getWidth()) {
            addScore(1);
            pipe3.setScored(true);
            AssetLoader.coin.play();
    	}
       
       return (pipe1.collides(bro) || 
    		   pipe2.collides(bro) || 
    		   pipe3.collides(bro) || 
    		   fireBall.collides(bro));
    }
    
    public void onRestart() {
        frontGrass.onRestart(0, SCROLL_SPEED);
        backGrass.onRestart(frontGrass.getTailX(), SCROLL_SPEED);
        pipe1.onRestart(210, SCROLL_SPEED);
        pipe2.onRestart(pipe1.getTailX() + PIPE_GAP, SCROLL_SPEED);
        pipe3.onRestart(pipe2.getTailX() + PIPE_GAP, SCROLL_SPEED);
        fireBall.onRestart(210);
        cloud1.onRestart(0, BACKGROUND_SPEED / 2);
        cloud2.onRestart(cloud1.getTailX(), BACKGROUND_SPEED / 2);
        bg1.onRestart(0,BACKGROUND_SPEED);
        bg2.onRestart(bg1.getTailX(), BACKGROUND_SPEED);
        
        
    }
    
    private void addScore(int increment) {
        myWorld.addScore(increment);
    }
    
 // The getters for our five instance variables
    public Grass getFrontGrass() {
        return frontGrass;
    }

    public Grass getBackGrass() {
        return backGrass;
    }

    public Pipe getPipe1() {
        return pipe1;
    }

    public Pipe getPipe2() {
        return pipe2;
    }

    public Pipe getPipe3() {
        return pipe3;
    }
    public FireBall getFireBall() {
    	return fireBall;
    }
    
    public BackGround getBackGround1() {
    	return bg1;
    }
    
    public BackGround getBackGround2() {
    	return bg2;
    }
    
    public Cloud getCloud1() {
        return cloud1;
    }

    public Cloud getCloud2() {
        return cloud2;
    }

}
