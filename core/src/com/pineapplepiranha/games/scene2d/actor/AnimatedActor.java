package com.pineapplepiranha.games.scene2d.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.pineapplepiranha.games.scene2d.GenericActor;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 8/24/14
 * Time: 12:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class AnimatedActor extends GenericActor{

    public Animation animation;

    private float keyFrameTime = 0f;

    public AnimatedActor(float x, float y, float width, float height, Animation animation, float startKeyframe){
        super(x, y, width, height, Color.GREEN);
        keyFrameTime = startKeyframe;
        this.animation = animation;

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(animation != null){
            textureRegion = animation.getKeyFrame(keyFrameTime, true);
            keyFrameTime += delta;
        }
    }

}
