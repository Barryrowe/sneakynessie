package com.pineapplepiranha.games.scene2d.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pineapplepiranha.games.scene2d.GenericActor;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 8/23/14
 * Time: 11:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class Player extends DepthActor{

    public float speed = 250f;

    private float keyFrameTotal = 0f;
    public Animation animation = null;

    public TextureRegion hidingTexture;
    public TextureRegion normalTexture;

    public boolean isHiding = false;

    public Player(float x, float y, float width, float height, TextureRegion tr){
        super(x, y, width, height, tr, 0);
        setColor(Color.YELLOW);
        normalTexture = tr;
        animation = null;
    }

    public Player(float x, float y, float width, float height, Animation anim){
        super(x, y, width, height, 0);
        setColor(Color.YELLOW);
        animation = anim;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(animation != null){
            keyFrameTotal += delta;

            textureRegion = animation.getKeyFrame(keyFrameTotal);
            if(velocity.x < 0f){
                textureRegion.flip(true, false);
                Gdx.app.log("NESSIE", "ANIMATION FLIP!");
            }
        }

        if(isHiding){
            textureRegion = hidingTexture;
        }else{
            textureRegion = normalTexture;
        }


        if(velocity.x > 0.0f){
            if(!textureRegion.isFlipX()){
                textureRegion.flip(true, false);
            }
        }else if(velocity.x < 0.0f){
            if(textureRegion.isFlipX()){
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

    public void setHidingTexture(TextureRegion tr){
        hidingTexture = tr;
    }
}
