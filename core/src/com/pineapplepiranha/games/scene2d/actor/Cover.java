package com.pineapplepiranha.games.scene2d.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pineapplepiranha.games.scene2d.GenericActor;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 8/23/14
 * Time: 10:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class Cover extends GenericActor {

    public int depthPos;

    public Cover(float x, float y, float width, float height, TextureRegion tr, int depthPos){
        super(x, y, width, height, tr, Color.GREEN);
        this.depthPos = depthPos;
    }


}
