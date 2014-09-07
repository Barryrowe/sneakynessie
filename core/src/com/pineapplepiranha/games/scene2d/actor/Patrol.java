package com.pineapplepiranha.games.scene2d.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kasetagen.engine.gdx.scenes.scene2d.KasetagenStateUtil;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 8/23/14
 * Time: 10:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class Patrol extends DepthActor{

    public float range;
    public float speed = 100f;
    public Rectangle visionBox;
    TextureRegion flashlight;
    public Animation animation;

    private float minX, maxX;
    private float keyFrameTime = 0f;
    private boolean goingToNessie = false;
    private float targetX = 0f;
    private boolean isGoingLeft = false;

    public Patrol(float x, float y, float width, float height, Animation ani, int depthPos, float range){
        super(x, y, width, height, depthPos);
        setColor(Color.BLUE);

        minX = x + (width/2) - range;
        maxX = x + (width/2) + range;
        velocity.set(speed, 0f);
        isGoingLeft = speed < 0f;
        this.depthPos = depthPos;
        this.range = range;
        this.visionBox = new Rectangle(x-(getVisionBoxWidth()), y-(height/4), getVisionBoxWidth(), height*1.5f);
        this.animation = ani;
        updateDirections(isGoingLeft);
    }

    private float getVisionBoxWidth(){
        return getWidth()*2;
    }

    @Override
    public void act(float delta) {

        super.act(delta);

        if(goingToNessie){
            if((velocity.x > 0f && getX() >= targetX) || (velocity.x < 0f && getX() <= targetX)){
                setX(targetX);
                velocity.x = 0f;
                textureRegion = animation.getKeyFrame(0f);
            }
        }else if(getX() >= maxX){
            setX(maxX - 1);

            //Switch to Going Left
            updateDirections(true);
        }else if(getX() <= minX){
            setX(minX + 1);
            //Switch to going right
            updateDirections(false);
        }




        if(animation != null){
            if(!goingToNessie && getX() != targetX){
                textureRegion = animation.getKeyFrame(keyFrameTime, true);
                keyFrameTime += delta;
            }
        }

        if(isGoingLeft){
            visionBox.setX(getX() - (getVisionBoxWidth()));
        }else{
            visionBox.setX(getX() + getWidth());
        }
    }


    private void updateDirections(boolean goingLeft){
        //Switch to Going Left
        isGoingLeft = goingLeft;
        if(flashlight != null){
            if((isGoingLeft && flashlight.isFlipX()) || (!isGoingLeft && !flashlight.isFlipX())){
                flashlight.flip(true, false);
            }
        }

        if(animation != null){
            for(TextureRegion tr:animation.getKeyFrames()){
                if((isGoingLeft && tr.isFlipX()) || (!isGoingLeft && !tr.isFlipX())){
                    tr.flip(true, false);
                }
            }
        }

        if(velocity.x > 0f && isGoingLeft || velocity.x < 0f && !isGoingLeft){
            velocity.x *= -1;
        }
    }

    public void sendToNessie(float targetX){
        goingToNessie = true;
        this.targetX = targetX;
        if(targetX < getX() || targetX > getX()){
            updateDirections(targetX < getX());
        }
    }

    @Override
    protected void drawFull(Batch batch, float parentAlpha) {
        super.drawFull(batch, parentAlpha);

        if(flashlight != null){
            batch.draw(flashlight, visionBox.x, visionBox.y, visionBox.width, visionBox.height);
        }

        if(KasetagenStateUtil.isDebugMode()){
            batch.end();
            batch.begin();
            Gdx.gl20.glLineWidth(1f);
            //Set the projection matrix, and line shape
            debugRenderer.setProjectionMatrix(getStage().getCamera().combined);
            debugRenderer.begin(ShapeRenderer.ShapeType.Line);

            //Draw the bounds of the actor as a box
            Color c = getColor() != null ? getColor() : Color.WHITE;
            debugRenderer.setColor(c);
            debugRenderer.rect(visionBox.x, visionBox.y, visionBox.width, visionBox.height);

            //End our shapeRenderer, flush the batch, and re-open it for future use as it was open
            // coming in.
            debugRenderer.end();
            batch.end();
            batch.begin();
        }

    }

    public void setFlashlightTextureRegion(TextureRegion tr){
        flashlight = tr;
        updateDirections(isGoingLeft);
    }
}
