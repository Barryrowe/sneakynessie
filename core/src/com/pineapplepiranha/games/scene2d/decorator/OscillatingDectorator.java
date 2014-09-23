package com.pineapplepiranha.games.scene2d.decorator;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 9/14/14
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class OscillatingDectorator implements ActorDecorator {




    private float minRotation = -5f;
    private float maxRotation = 5f;
    private float rotationSpeed = 1f;


    public OscillatingDectorator(float minRotation, float maxRotation, float rotationSpeed){
        this.minRotation = minRotation;
        this.maxRotation = maxRotation;
        this.rotationSpeed = rotationSpeed;

    }
    @Override
    public void applyAdjustment(Actor a, float delta) {

        boolean isAboveMax = a.getRotation() >= maxRotation;
        boolean isBelowMin = a.getRotation() <= minRotation;

        if(isAboveMax || isBelowMin){
            if(isAboveMax){
                a.setRotation(maxRotation);
            }else{
                a.setRotation(minRotation);
            }
            rotationSpeed *= -1;
        }

        a.setOrigin(a.getWidth()/2, a.getHeight());
        float adjustment = rotationSpeed * delta;
        a.setRotation(a.getRotation()+adjustment);
    }
}
