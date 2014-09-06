package com.pineapplepiranha.games.scene2d.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 9/6/14
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class Spaceship extends AnimatedActor {

    private TextureRegion speechBubble;
    private Vector2 speechOffset = new Vector2(0f, 0f);
    public Spaceship(float x, float y, float width, float height, Animation ani){
        super(x, y, width, height, ani, 0f);
    }

    public void setSpeechBubble(TextureRegion tr, float x, float y){
        speechBubble = tr;
        speechOffset.x = x;
        speechOffset.y = y;
    }

    public void setSpeechBubble(Texture t, float x, float y){
        speechBubble = new TextureRegion(t);
        speechOffset.x = x;
        speechOffset.y = y;
    }

    @Override
    protected void drawFull(Batch batch, float parentAlpha) {
        super.drawFull(batch, parentAlpha);
        if(speechBubble != null){
                 batch.draw(speechBubble, getX() + speechOffset.x, getY() + speechOffset.y);
        }
    }
}
