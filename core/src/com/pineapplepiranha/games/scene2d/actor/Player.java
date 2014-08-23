package com.pineapplepiranha.games.scene2d.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.pineapplepiranha.games.scene2d.GenericActor;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 8/23/14
 * Time: 11:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class Player extends GenericActor{

    public float speed = 20f;

    private float keyFrameTotal = 0f;
    public Animation animation;

    public Player(float x, float y, float width, float height, Animation anim){
        super(x, y, width, height, Color.YELLOW);
        animation = anim;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(animation != null){
            keyFrameTotal += delta;

            textureRegion = animation.getKeyFrame(keyFrameTotal);
            if(velocity.x < 0){
                textureRegion.flip(true, false);
            }
        }
    }

    public void setXVelocity(float vel){
        velocity.x = vel;
    }

    public void setYVelocity(float vel){
        velocity.y = vel;
    }

    public void setVelocity(float x, float y){
        velocity.set(x, y);
    }




}
