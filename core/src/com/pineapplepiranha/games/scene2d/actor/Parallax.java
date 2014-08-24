package com.pineapplepiranha.games.scene2d.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pineapplepiranha.games.scene2d.GenericActor;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 8/23/14
 * Time: 10:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class Parallax extends GenericActor{

    public Parallax(float x, float y, TextureRegion tr){
        super(x, y, tr.getRegionWidth(), tr.getRegionHeight(), tr, Color.RED);
    }

}
