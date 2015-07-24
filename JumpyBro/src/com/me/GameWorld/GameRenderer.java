package com.me.GameWorld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.me.GameObjects.BackGround;
import com.me.GameObjects.Bro;
import com.me.GameObjects.Cloud;
import com.me.GameObjects.FireBall;
import com.me.GameObjects.Grass;
import com.me.GameObjects.Pipe;
import com.me.GameObjects.ScrollHandler;
import com.me.JBHelpers.AssetLoader;

public class GameRenderer {
	private GameWorld myWorld;
	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batcher;
	
	private Bro bro;
	private FireBall fireBall;
	private TextureRegion bg, grass, cloud;
	private Animation broAnimation, fireBallAnimation;
	private TextureRegion broDead, broJump;
	private TextureRegion skullDown, bar;
	private int gameHeight;
	private int midPointY;
	
	private ScrollHandler scroller;
	private Grass frontGrass, backGrass;
	private BackGround bg1, bg2;
	private Cloud cloud1, cloud2;
	private Pipe pipe1, pipe2, pipe3;
	
	public GameRenderer(GameWorld world , int height, int midpoint ) {
		myWorld = world;
		
		this.gameHeight = height;
		this.midPointY = midpoint;
		
		cam = new OrthographicCamera();
		cam.setToOrtho(true, 136, gameHeight);
		
		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);
		
		shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);
        
        initGameObjects();
        initAssets();
	}
	
	public void render(float runTime) {
		
		//Bro bro = myWorld.getBro();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
     // Begin ShapeRenderer
        shapeRenderer.begin(ShapeType.Filled);
        
        // Draw Background color
        shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 136, midPointY + 66);

        // Draw Grass
        shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 66, 136, 11);

        // Draw Dirt
        shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 77, 136, 52);
        
        // End ShapeRenderer
        shapeRenderer.end();

        // Begin SpriteBatch
        batcher.begin();
        // Disable transparency 
        // This is good for performance when drawing images that do not require
        // transparency.
        drawClouds();
        drawBackGround();
        batcher.disableBlending();
        //batcher.draw(AssetLoader.bg, 0, midPointY + 23, 136, 43);
        
        drawGrass();
        drawPipes();
        

        // The bro needs transparency, so we enable that again.
        batcher.enableBlending();
        
        drawSkulls();
        
        
        batcher.draw(fireBallAnimation.getKeyFrame(runTime),
        		fireBall.getX(), fireBall.getY(), fireBall.getWidth() / 2.0f, fireBall.getHeight() / 2.0f,
        		fireBall.getWidth(),fireBall.getHeight(),1,1,0);
        
        // Draw bro at its coordinates. Retrieve the Animation object from AssetLoader
        // Pass in the runTime variable to get the current frame.
        if (bro.isAlive()) {
        	if (bro.isJumping()) {
        		batcher.draw(broJump, bro.getX(),bro.getY(), bro.getWidth() / 2.0f, bro.getHeight() / 2.0f,
        				bro.getWidth(), bro.getHeight(),1,1,bro.getRotation());
        	} else {
               batcher.draw(broAnimation.getKeyFrame(runTime),
                   bro.getX(), bro.getY(), bro.getWidth() / 2.0f, bro.getHeight() / 2.0f, 
                   bro.getWidth(), bro.getHeight(),1,1,bro.getRotation());
        	}
        } else {
        	batcher.draw(broDead,
                    bro.getX(), bro.getY(), bro.getWidth() / 2.0f, bro.getHeight() / 2.0f, 
                    bro.getWidth(), bro.getHeight(),1,1,bro.getRotation());
        }
        
        if (myWorld.isReady()) {
            // Draw shadow first
            AssetLoader.shadow.draw(batcher, "Touch to", (136 / 2)
                    - (42), 56);
            // Draw text
            AssetLoader.font.draw(batcher, "Touch to", (136 / 2)
                    - (42 - 1), 55);
            AssetLoader.shadow.draw(batcher, "start", (136 / 2)
                    - (29), 76);
            // Draw text
            AssetLoader.font.draw(batcher, "start", (136 / 2)
                    - (29 - 1), 75);
        } else {

            if (myWorld.isGameOver()) {
                AssetLoader.shadow.draw(batcher, "Game Over", 25, 56);
                AssetLoader.font.draw(batcher, "Game Over", 24, 55);
                
                AssetLoader.shadow.draw(batcher, "Try again?", 23, 86);
                AssetLoader.font.draw(batcher, "Try again?", 24, 85);
                
                AssetLoader.shadow.draw(batcher, "High Score " + AssetLoader.getHighScore(), 23, 116);
                AssetLoader.font.draw(batcher, "High Score " + AssetLoader.getHighScore(), 24, 115);
                
                
                
            }
            
            // Convert integer into String
            String score = myWorld.getScore() + "";

            // Draw shadow first
            AssetLoader.shadow.draw(batcher, "" + myWorld.getScore(), (136 / 2)
                    - (3 * score.length()), 12);
            // Draw text
            AssetLoader.font.draw(batcher, "" + myWorld.getScore(), (136 / 2)
                    - (3 * score.length() - 1), 11);
        }
        
        
        // End SpriteBatch
        batcher.end();
	}
	
	private void initGameObjects() {
		bro = myWorld.getBro();
		scroller = myWorld.getScroller();
        frontGrass = scroller.getFrontGrass();
        backGrass = scroller.getBackGrass();
        pipe1 = scroller.getPipe1();
        pipe2 = scroller.getPipe2();
        pipe3 = scroller.getPipe3(); 
        fireBall = scroller.getFireBall();
        bg1 = scroller.getBackGround1();
        bg2 = scroller.getBackGround2();
        cloud1 = scroller.getCloud1();
        cloud2 = scroller.getCloud2();
        		
	}
	
	private void initAssets() {
	//	fireBallAsset = AssetLoader.fireBall;
		fireBallAnimation = AssetLoader.fireBallAnimation;
		bg = AssetLoader.bg;
        grass = AssetLoader.grass;
        broAnimation = AssetLoader.broAnimation;
      //  broMid = AssetLoader.bro;
        //broDown = AssetLoader.broDown;
        broDead = AssetLoader.broUp;
       // skullUp = AssetLoader.skullUp;
        skullDown = AssetLoader.skullDown;
        bar = AssetLoader.bar;
        cloud = AssetLoader.cloud;
        broJump = AssetLoader.broJump;
		
	}
	
	private void drawGrass() {
        // Draw the grass
        batcher.draw(grass, frontGrass.getX(), frontGrass.getY(),
                frontGrass.getWidth(), frontGrass.getHeight());
        batcher.draw(grass, backGrass.getX(), backGrass.getY(),
                backGrass.getWidth(), backGrass.getHeight());
    } 
	
	private void drawBackGround() {
		batcher.draw(bg, bg1.getX(), bg1.getY(),
                bg1.getWidth(), bg1.getHeight());
        batcher.draw(bg, bg2.getX(), bg2.getY(),
                bg2.getWidth(), bg2.getHeight());
	}
	
	private void drawClouds() {
		batcher.draw(cloud, cloud1.getX(), cloud1.getY(),
                cloud1.getWidth(), cloud1.getHeight());
        batcher.draw(cloud, cloud2.getX(), cloud2.getY(),
                cloud2.getWidth(), cloud2.getHeight());
	}
	
	private void drawPipes() {
		//batcher.draw(bar, pipe1.getX(), pipe1.getY(), pipe1.getWidth(),
              //  pipe1.getHeight());
        batcher.draw(bar, pipe1.getX(), pipe1.getY() + pipe1.getHeight() + 45,
                pipe1.getWidth(), midPointY + 66 - (pipe1.getHeight() + 45));

      //  batcher.draw(bar, pipe2.getX(), pipe2.getY(), pipe2.getWidth(),
              // pipe2.getHeight());
        batcher.draw(bar, pipe2.getX(), pipe2.getY() + pipe2.getHeight() + 45,
                pipe2.getWidth(), midPointY + 66 - (pipe2.getHeight() + 45));

       // batcher.draw(bar, pipe3.getX(), pipe3.getY(), pipe3.getWidth(),
              //  pipe3.getHeight());
        batcher.draw(bar, pipe3.getX(), pipe3.getY() + pipe3.getHeight() + 45,
                pipe3.getWidth(), midPointY + 66 - (pipe3.getHeight() + 45));
		
	}
	
	private void drawSkulls() {
		//batcher.draw(skullUp, pipe1.getX() - 1,
              //  pipe1.getY() + pipe1.getHeight() - 14, 24, 14);
        batcher.draw(skullDown, pipe1.getX() - 1,
                pipe1.getY() + pipe1.getHeight() + 45, 24, 14);

        //batcher.draw(skullUp, pipe2.getX() - 1,
             //   pipe2.getY() + pipe2.getHeight() - 14, 24, 14);
        batcher.draw(skullDown, pipe2.getX() - 1,
                pipe2.getY() + pipe2.getHeight() + 45, 24, 14);

        //batcher.draw(skullUp, pipe3.getX() - 1,
             //   pipe3.getY() + pipe3.getHeight() - 14, 24, 14);
       batcher.draw(skullDown, pipe3.getX() - 1,
                pipe3.getY() + pipe3.getHeight() + 45, 24, 14);
	}


}
