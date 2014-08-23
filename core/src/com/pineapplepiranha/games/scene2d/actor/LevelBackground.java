package com.pineapplepiranha.games.scene2d.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pineapplepiranha.games.scene2d.GenericGroup;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 8/23/14
 * Time: 10:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class LevelBackground extends GenericGroup {

    Texture bgTexture;

    public LevelBackground(float x, float y, float width, float height, Texture bgTexture){
        super(x, y, width,height, Color.BLACK);
        this.bgTexture = bgTexture;
        textureRegion = new TextureRegion(bgTexture);
    }


}
