package com.me.JBHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
	
	public static Texture texture;
    public static TextureRegion bg, grass, cloud;

    public static Animation broAnimation, fireBallAnimation;
    public static TextureRegion bro, broDown, broUp, broJump;
    public static TextureRegion fireBall, fireBall1,fireBall2, fireBall3;
    public static TextureRegion skullUp, skullDown, bar;
    
    public static Sound dead;
    public static Sound jump; 
    public static Sound coin;
    
    public static Preferences prefs;
    
    public static BitmapFont font, shadow;
    
    public static void load() {
    	prefs = Gdx.app.getPreferences("JumpyBro");

    	// Provide default high score of 0
    	if (!prefs.contains("highScore")) {
    	    prefs.putInteger("highScore", 0);
    	}
    	texture = new Texture(Gdx.files.internal("data/jumpybro.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        bg = new TextureRegion(texture, 0, 0, 136, 43);
        bg.flip(false, true);
        cloud = new TextureRegion(texture, 87,103,136,20);
        cloud.flip(false, true);
        
        fireBall = new TextureRegion(texture, 177, 47, 7, 7);
        fireBall1 = new TextureRegion(texture, 187, 47, 7, 7);
        fireBall2 = new TextureRegion(texture, 196, 47, 7, 7);
        fireBall3 = new TextureRegion(texture, 206, 47, 7, 7);
        
        
        grass = new TextureRegion(texture, 0, 43, 143, 11);
        grass.flip(false, true);

        broDown = new TextureRegion(texture, 136, 0, 14, 16);
        broDown.flip(false, true);
        broJump = new TextureRegion(texture, 189, 26, 16, 16);
        broJump.flip(false, true);
        
        bro = new TextureRegion(texture, 153, 0, 14, 16);
        bro.flip(false, true);

        broUp = new TextureRegion(texture, 170, 0, 14, 16);
        broUp.flip(false, true);

        TextureRegion[] bros = { broDown, bro};
        broAnimation = new Animation(0.1f, bros);
        broAnimation.setPlayMode(Animation.LOOP_PINGPONG);
        
        TextureRegion[] fireballs = { fireBall, fireBall1, fireBall2, fireBall3 };
        fireBallAnimation = new Animation(0.1f, fireballs);
        fireBallAnimation.setPlayMode(Animation.LOOP);

        skullUp = new TextureRegion(texture, 192, 0, 24, 14);
        // Create by flipping existing skullUp
        skullDown = new TextureRegion(skullUp);
        skullDown.flip(false, true);

        bar = new TextureRegion(texture, 136, 16, 22, 3);
        bar.flip(false, true);
        
        // Sounds
        dead = Gdx.audio.newSound(Gdx.files.internal("data/die.wav"));
        jump = Gdx.audio.newSound(Gdx.files.internal("data/sfx.wav"));
        coin = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));
        
        //Fonts
        font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
        font.setScale(.25f, -.25f);
        shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
        shadow.setScale(.25f, -.25f);
    }
    
    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }

    // Retrieves the current high score
    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }
    
    public static void dispose() {
    	texture.dispose();
    	dead.dispose();
    	jump.dispose();
    	coin.dispose();
    	font.dispose();
    	shadow.dispose();
    }
    

}
