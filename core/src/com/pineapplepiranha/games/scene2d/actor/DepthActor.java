package com.pineapplepiranha.games.scene2d.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pineapplepiranha.games.scene2d.GenericActor;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 8/23/14
 * Time: 12:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class DepthActor extends GenericActor{

    public int depthPos = 0;

    public DepthActor(float x, float y, float width, float height, int depthPos){
        super(x, y, width, height, Color.GRAY);
        this.depthPos = depthPos;
    }

    public DepthActor(float x, float y, float width, float height, TextureRegion tr, int depthPos){
        super(x, y, width, height, tr, Color.GRAY);
        this.depthPos = depthPos;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setZIndex(depthPos);
    }
}
