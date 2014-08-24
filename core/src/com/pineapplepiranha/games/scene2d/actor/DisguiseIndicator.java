package com.pineapplepiranha.games.scene2d.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pineapplepiranha.games.scene2d.GenericActor;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 8/23/14
 * Time: 6:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class DisguiseIndicator extends GenericActor {

    public String disguiseType;
    public boolean hasDisguise = true;

    public DisguiseIndicator(float x, float y, float width, float height, TextureRegion tr){
        super(x, y, width, height, tr, Color.MAGENTA);
        disguiseType = "NOSE";
    }

    @Override
    protected void drawFull(Batch batch, float parentAlpha) {
        if(hasDisguise){
            super.drawFull(batch, parentAlpha);
        }
    }
}
