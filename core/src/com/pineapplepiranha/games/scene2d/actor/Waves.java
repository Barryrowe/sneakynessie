package com.pineapplepiranha.games.scene2d.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pineapplepiranha.games.scene2d.GenericActor;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 8/23/14
 * Time: 10:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class Waves extends GenericActor{

    private float maxX;
    private float maxY;
    private float minX;
    private float minY;

    public Waves(float x, float y, TextureRegion tr, float maxX, float maxY){
        super(x, y, tr.getRegionWidth(), tr.getRegionHeight(), tr, Color.CYAN);
        this.minX = x;
        this.maxX = maxX;
        this.minY = y;
        this.maxY = maxY;
        this.velocity.x = 20f;
        this.velocity.y = 20f;
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        float x = getX();
        if(x >= maxX && velocity.x > 0f){
            velocity.x *= -1;
        }else if(x <= minX && velocity.x < 0f){
            velocity.x *= -1;
        }

        float y = getY();
        if(y >= maxY && velocity.y > 0f){
            velocity.y *= -1;
        }else if(y <= minY && velocity.y < 0f){
            velocity.y *= -1;
        }
    }
}
