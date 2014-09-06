package com.pineapplepiranha.games.scene2d.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kasetagen.engine.gdx.scenes.scene2d.KasetagenStateUtil;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 9/6/14
 * Time: 4:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class LandingPad extends AnimatedActor {

    public LandingPad(float x, float y, float width, float height, Animation ani, float keyFrameRate){
        super(x, y, width, height, ani, keyFrameRate);

        //this.collider.set(x + (width/8f), y + height/8f, width*(3f/4f), height*(3f/4f));
    }

    @Override
    protected void adjustCollidingBox(float delta) {
        collider.setPosition(getX() + (getWidth()/8f), getY() + (getHeight()/8f));
        collider.setWidth(getWidth()*(3f/4f));
        collider.setHeight(getHeight()*(3f/4f));
    }

    @Override
    protected void drawFull(Batch batch, float parentAlpha) {
        super.drawFull(batch, parentAlpha);

        if(KasetagenStateUtil.isDebugMode()){
            batch.end();
            batch.begin();
            Gdx.gl20.glLineWidth(1f);
            //Set the projection matrix, and line shape
            debugRenderer.setProjectionMatrix(getStage().getCamera().combined);
            debugRenderer.begin(ShapeRenderer.ShapeType.Line);

            Color c = getColor() != null ? getColor() : Color.WHITE;
            debugRenderer.setColor(Color.PINK);
            debugRenderer.rect(collider.x, collider.y, collider.width, collider.height);

            //End our shapeRenderer, flush the batch, and re-open it for future use as it was open
            // coming in.
            debugRenderer.end();
            batch.end();
            batch.begin();
        }
    }
}
