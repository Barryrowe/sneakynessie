package com.pineapplepiranha.games.scene2d.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pineapplepiranha.games.scene2d.GenericActor;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 8/23/14
 * Time: 10:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class Patroler extends GenericActor{

    public int depthPos;
    public float range;

    public Patroler(float x, float y, float width, float height, TextureRegion tr, int depthPos, float range){
        super(x, y, width, height, tr, Color.BLUE);
        this.depthPos = depthPos;
        this.range = range;
    }
}
