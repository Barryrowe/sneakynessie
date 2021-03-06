package com.pineapplepiranha.games.scene2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.kasetagen.engine.gdx.scenes.scene2d.KasetagenGroup;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 8/23/14
 * Time: 12:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class GenericGroup extends KasetagenGroup {
    protected TextureRegion textureRegion;
    public Rectangle collider;

    public GenericGroup(float x, float y, float width, float height, TextureRegion textureRegion, Color color){
        super();
        setPosition(x, y);
        setBounds(x, y, width, height);
        setWidth(width);
        setHeight(height);
        setOrigin(x + width/2, y + height/2);
        setColor(color);
        this.textureRegion = textureRegion;
        collider = new Rectangle(x, y, width, height);
    }

    public GenericGroup(float x, float y, float width, float height, Color color){
        super();
        setPosition(x, y);
        setBounds(x, y, width, height);
        setWidth(width);
        setHeight(height);
        setOrigin(x + width/2, y + height/2);
        setColor(color);
        collider = new Rectangle(x, y, width, height);
    }

    protected void adjustCollidingBox(float delta){
        collider.setPosition(getX(), getY());
        collider.setWidth(getWidth());
        collider.setHeight(getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        adjustCollidingBox(delta);
    }

    @Override
    protected void drawBefore(Batch batch, float parentAlpha) {
        if(textureRegion != null){
            batch.draw(textureRegion, getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }

    @Override
    protected void drawFull(Batch batch, float v) {

    }
}
