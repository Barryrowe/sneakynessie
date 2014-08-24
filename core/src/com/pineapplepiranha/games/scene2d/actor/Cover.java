package com.pineapplepiranha.games.scene2d.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 8/23/14
 * Time: 10:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class Cover extends DepthActor {


    public Cover(float x, float y, float width, float height, TextureRegion tr, int depthPos){
        super(x, y, width, height, tr, depthPos);
        setColor(Color.GREEN);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        setZIndex(3);
    }
}
