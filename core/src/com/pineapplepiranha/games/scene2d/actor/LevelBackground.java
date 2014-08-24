package com.pineapplepiranha.games.scene2d.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
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
        if(bgTexture != null){
            textureRegion = new TextureRegion(bgTexture);
        }
    }

    @Override
    protected void drawBefore(Batch batch, float parentAlpha) {
        super.drawBefore(batch, parentAlpha);
        if(textureRegion != null){
            batch.draw(textureRegion, getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }

    @Override
    protected void drawFull(Batch batch, float parentAlpha) {
        //do nothing
    }
}
