package com.me.JumpyBro;

import com.badlogic.gdx.Game;
import com.me.JBHelpers.AssetLoader;
import com.me.Screens.GameScreen;

public class JBGame extends Game {
	
   @Override
   public void create() {
	   System.out.println("JB game created!");
	   AssetLoader.load();
	   setScreen(new GameScreen());
   }
   
   @Override
   public void dispose() {
	   super.dispose();
	   AssetLoader.dispose();
   }

}
