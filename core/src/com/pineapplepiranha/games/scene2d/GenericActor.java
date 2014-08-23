package com.pineapplepiranha.games.scene2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kasetagen.engine.gdx.scenes.scene2d.KasetagenActor;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 8/23/14
 * Time: 12:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class GenericActor extends KasetagenActor {

    protected TextureRegion textureRegion;
    public Rectangle collider;

    public Vector2 velocity = new Vector2(0f, 0f);

    protected float rotationSpeed = 0f;

    public GenericActor(float x, float y, float width, float height, TextureRegion textureRegion, Color color){
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

    public GenericActor(float x, float y, float width, float height, Color color){
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

    protected void adjustRotation(float delta){
        setRotation(getRotation() + (rotationSpeed * delta));
    }

    public void setRotationSpeed(float newSpeed){
        rotationSpeed = newSpeed;
    }

    public void adjustPosition(float delta){
        float curX = getX();
        float curY = getY();
        float newX = curX + (velocity.x * delta);
        float newY = curY + (velocity.y * delta);
        setPosition(newX, newY);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        adjustCollidingBox(delta);
        adjustRotation(delta);
        adjustPosition(delta);
    }

    @Override
    protected void drawFull(Batch batch, float parentAlpha) {
        if(textureRegion != null){
            batch.draw(textureRegion, getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }
}
