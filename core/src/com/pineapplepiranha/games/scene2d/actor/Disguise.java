package com.pineapplepiranha.games.scene2d.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pineapplepiranha.games.scene2d.GenericActor;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 8/23/14
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class Disguise extends GenericActor {

    public String disguiseType;
    public Disguise(float x, float y, float width, float height, Texture texture, String disguiseType){
        super(x, y, width, height, new TextureRegion(texture), Color.ORANGE);
        this.disguiseType = disguiseType;
    }
}
