package com.me.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.me.GameWorld.GameRenderer;
import com.me.GameWorld.GameWorld;
import com.me.JBHelpers.InputHandler;


public class GameScreen implements Screen {
    private GameWorld world;
    private GameRenderer renderer;
    
    private float runTime;
    
	public GameScreen() {
		
		System.out.println("Game Screen Attached");
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		
		runTime = 0;
		float gameWidth = 136;
		float gameHeight = screenHeight / (screenWidth/ gameWidth);
		
		int midPointY = (int) (gameHeight / 2);
		
		world = new GameWorld(midPointY);
		renderer = new GameRenderer(world, (int) gameHeight, midPointY);
		
		Gdx.input.setInputProcessor(new InputHandler(world));
		
	}
	
	@Override
	public void render(float delta) {
		runTime += delta;
		world.update(delta);
		renderer.render(runTime);
       
	}

	@Override
	public void resize(int width, int height) {
		System.out.println("resize called");
		
	}

	@Override
	public void show() {
		System.out.println("show called");
		
	}

	@Override
	public void hide() {
		System.out.println("hide called");
		
	}

	@Override
	public void pause() {
		System.out.println("pause called");
		
	}

	@Override
	public void resume() {
		System.out.println("resume called");
		
	}

	@Override
	public void dispose() {
		System.out.println("dispose called");
		
	}

}
