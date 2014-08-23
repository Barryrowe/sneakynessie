package com.pineapplepiranha.games.scene2d.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pineapplepiranha.games.scene2d.GenericActor;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 8/23/14
 * Time: 10:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class Patroler extends DepthActor{

    public float range;
    public Vector2 velocity;
    public float speed = 100f;

    public Rectangle visionBox;


    private Vector2 initialPos;
    private float minX, maxX;

    TextureRegion flashlight;

    public Patroler(float x, float y, float width, float height, TextureRegion tr, int depthPos, float range){
        super(x, y, width, height, tr, depthPos);
        setColor(Color.BLUE);
        initialPos = new Vector2(x, y);
        minX = x + (width/2) - range;
        maxX = x + (width/2) + range;
        velocity = new Vector2(speed, 0f);
        this.depthPos = depthPos;
        this.range = range;
        this.visionBox = new Rectangle(x-(getVisionBoxWidth()), y-(height/2), getVisionBoxWidth(), height*2);
    }

    private float getVisionBoxWidth(){
        return getWidth()*3;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        //Adjust visionBox

        if(getX() >= maxX || getX() <= minX){
            velocity.x *= -1;
        }
        setPosition(getX() + (velocity.x * delta), getY());
        if(velocity.x >= 0){
            visionBox.setX(getX()+getWidth());
            if(flashlight != null && !flashlight.isFlipX()){
                flashlight.flip(true, false);
            }
        }else{
            visionBox.setX(getX() - (getVisionBoxWidth()));
            if(flashlight != null && flashlight.isFlipX()){
                flashlight.flip(true, false);
            }
        }
    }

    @Override
    protected void drawFull(Batch batch, float parentAlpha) {
        super.drawFull(batch, parentAlpha);

        if(flashlight != null){
            batch.draw(flashlight, visionBox.x, visionBox.y, visionBox.width, visionBox.height);
        }

//        batch.end();
//        batch.begin();
//        Gdx.gl20.glLineWidth(1f);
//        //Set the projection matrix, and line shape
//        debugRenderer.setProjectionMatrix(getStage().getCamera().combined);
//        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
//
//        Color c = getColor() != null ? getColor() : Color.WHITE;
//        debugRenderer.setColor(c);
//        debugRenderer.rect(visionBox.x, visionBox.y, visionBox.width, visionBox.height);
//
//        //End our shapeRenderer, flush the batch, and re-open it for future use as it was open
//        // coming in.
//        debugRenderer.end();
//        batch.end();
//        batch.begin();
    }

    public void setFlashlightTextureRegion(TextureRegion tr){
        flashlight = tr;
    }
}
