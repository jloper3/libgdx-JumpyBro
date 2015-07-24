package com.me.JBHelpers;

import com.badlogic.gdx.InputProcessor;
import com.me.GameObjects.Bro;
import com.me.GameWorld.GameWorld;

public class InputHandler implements InputProcessor {
    
	private Bro myBro;
	private GameWorld myWorld;
	
	public InputHandler (GameWorld world) {
		myWorld = world;
		myBro = myWorld.getBro();
	}
	
	@Override
	public boolean keyDown(int keycode) {
		myBro.onClick();
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		if (myWorld.isReady()) {
            myWorld.start();
        }
		
		myBro.onClick();
		
		if (myWorld.isGameOver()) {
            // Reset all variables, go to GameState.READY
            myWorld.restart();
        }

        return true;

	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
