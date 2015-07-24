package com.me.GameWorld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.me.GameObjects.Bro;
import com.me.GameObjects.ScrollHandler;
import com.me.JBHelpers.AssetLoader;

public class GameWorld {
	
	private Bro bro;
	private ScrollHandler scroller;
	private Rectangle ground;
	private int midPointY;

	private GameState currentState;
	private int score = 0;
	
	
	
	public GameWorld(int midPointY) {
		currentState = GameState.READY;
		this.midPointY = midPointY;
		bro = new Bro(20, midPointY - 5, 17, 12);
		scroller = new ScrollHandler(this, midPointY + 66);
		ground = new Rectangle(0, midPointY + 66, 136, 11);
	}
	
	public enum GameState {
	    READY, RUNNING, GAMEOVER, HIGHSCORE
	}
	
	public void update(float delta) {
		switch (currentState) {
		case READY:
			updateReady(delta);
			break;
		case RUNNING:
		default:
		   updateRunning(delta);
		   break;
		}
	} 
	
	public void updateReady(float delta){
		
	}
	
	public void start() {
		currentState = GameState.RUNNING;
	}
	
	public void restart() {
		currentState = GameState.READY;
		score = 0;
		bro.onRestart(midPointY - 5);
		scroller.onRestart();
		currentState = GameState.READY;
	}
	
	
	public void updateRunning(float delta){ 
		if (delta > .15f) {
            delta = .15f;
        }

        bro.update(delta);
        scroller.update(delta);
        
        if (score > AssetLoader.getHighScore()) {
        	AssetLoader.setHighScore(score);
        }

        if (scroller.collides(bro) && bro.isAlive()) {
            scroller.stop();
            bro.die();
            bro.decelerate();
            AssetLoader.dead.play();
            currentState = GameState.GAMEOVER;
            
        }

      // if (Intersector.overlaps(bro.getBoundingCircle(), ground)) {
       if (bro.getY() > midPointY + 54 && bro.isFalling()) {
            bro.land(midPointY + 54);
        }
	}
	
	public Bro getBro() {
		return bro;
	}
        
	
	public ScrollHandler getScroller() {
		return scroller;
	}
	
	public int getScore() {
		return score;
	}
	
	public void addScore(int increment) {
		score += increment;
	}
	
	public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }
    
	public boolean isReady() {
		return currentState == GameState.READY;
	}
	
	public boolean isRunning() {
		return currentState == GameState.RUNNING;
	}
}
