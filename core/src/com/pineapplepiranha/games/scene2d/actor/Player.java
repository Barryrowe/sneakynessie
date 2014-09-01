package com.pineapplepiranha.games.scene2d.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kasetagen.engine.gdx.scenes.scene2d.KasetagenStateUtil;
import com.pineapplepiranha.games.scene2d.GenericActor;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 8/23/14
 * Time: 11:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class Player extends DepthActor{

    //THIS IS GROSS!!!
    private static float BUFFER = 20f;

    public float speed = 180f;

    private float keyFrameTotal = 0f;
    public Animation animation = null;
    public Animation sadAnimation = null;
    public Animation runningAnimation = null;

    public TextureRegion hidingTexture;
    public TextureRegion normalTexture;
    public TextureRegion disguisedTexture;

    public boolean isHiding = false;
    public boolean isDisguised = false;
    protected boolean isFound = false;
    public boolean isRunning = false;

    public Player(float x, float y, float width, float height, TextureRegion tr){
        super(x+BUFFER, y+BUFFER, width-BUFFER*2, height-(BUFFER*2), tr, 0);
        setColor(Color.YELLOW);
        normalTexture = tr;
        animation = null;
        collider.set(x+(width/4), y, width/2, height/2);
    }

    public Player(float x, float y, float width, float height, Animation anim){
        super(x, y, width, height, 0);
        setColor(Color.YELLOW);
        animation = anim;
        normalTexture = animation.getKeyFrame(0f);
    }

    @Override
    protected void adjustCollidingBox(float delta) {
        if(isRunning){
            collider.set(getX()+(getWidth()/8), getY(), (getWidth()/1.5f), (getHeight()/1.5f));
        }   else{
            collider.set(getX()+(getWidth()/4), getY(), getWidth()/2, getHeight()/2);
        }
    }

    @Override
    public void adjustPosition(float delta) {
        if(!isDisguised && !isFound){
            super.adjustPosition(delta);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(isFound){
            if(sadAnimation != null){
              textureRegion = sadAnimation.getKeyFrame(keyFrameTotal, false);
              keyFrameTotal += delta;
            }
        }else if(isDisguised){
            //Draw Disguised and return

            if(disguisedTexture != null){
                textureRegion = disguisedTexture;
            }
            return;
        }else{
            if(velocity.x == 0.0f && velocity.y == 0.0f){
                //
                if(isHiding){
                    textureRegion = hidingTexture;
                }else {
                    textureRegion = normalTexture;
                }
                keyFrameTotal = 0f;
            }else if(animation != null){
                textureRegion = isRunning ? runningAnimation.getKeyFrame(keyFrameTotal, true) : animation.getKeyFrame(keyFrameTotal, true);
                if(velocity.x < 0f){
                    textureRegion.flip(true, false);

                }
                keyFrameTotal += delta;
            }

            if(velocity.x > 0.0f){
                if(!textureRegion.isFlipX()){
                    textureRegion.flip(true, false);
                }

                if(!hidingTexture.isFlipX()){
                    hidingTexture.flip(true, false);
                }

                if(!normalTexture.isFlipX()){
                    normalTexture.flip(true, false);
                }
            }else if(velocity.x < 0.0f){
                if(textureRegion.isFlipX()){
                    textureRegion.flip(true, false);
                }

                if(hidingTexture.isFlipX()){
                    hidingTexture.flip(true, false);
                }

                if(normalTexture.isFlipX()){
                    normalTexture.flip(true, false);
                }
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

    public void setNormalTexture(TextureRegion tr){
        normalTexture = tr;
    }

    public void setIsFound(boolean found){
        isFound = found;
        keyFrameTotal = 0f;
    }

    public boolean isFound(){
        return isFound;
    }

    @Override
    protected void drawFull(Batch batch, float parentAlpha) {
        //super.drawFull(batch, parentAlpha);
        if(isRunning && velocity.x != 0f && velocity.y != 0f){
            batch.draw(textureRegion, getX()-(BUFFER/2), getY()-(BUFFER/2), getWidth()+(BUFFER), getHeight()+(BUFFER));
        }else{
            batch.draw(textureRegion, getX()-BUFFER, getY()-BUFFER, getWidth()+(BUFFER*2), getHeight()+(BUFFER*2));
        }
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
