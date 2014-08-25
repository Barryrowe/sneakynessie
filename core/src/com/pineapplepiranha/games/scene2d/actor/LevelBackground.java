package com.pineapplepiranha.games.scene2d.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.pineapplepiranha.games.scene2d.GenericActor;
import com.pineapplepiranha.games.scene2d.GenericGroup;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 8/23/14
 * Time: 10:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class LevelBackground extends GenericGroup {

    Texture bgTexture;

    public Vector2 velocity = new Vector2(0f, 0f);

    public LevelBackground(float x, float y, float width, float height, Array<TextureAtlas.AtlasRegion> regions){
        super(x, y, width, height, Color.BLACK);
        float nextX = 0f;
        Gdx.app.log("LVLBG", "Regions:" + regions.size);
        for(TextureAtlas.AtlasRegion r:regions){
            Gdx.app.log("LVLBG", "Region Name: " + r.name + " Nextx: " + nextX);
            addActor(new GenericActor(nextX, 0f, r.getRegionWidth(), r.getRegionHeight(), r, Color.BLUE));
            nextX += r.getRegionWidth();
        }
    }

    public LevelBackground(float x, float y, float width, float height, Texture bgTexture){
        super(x, y, width,height, Color.BLACK);
        this.bgTexture = bgTexture;
        if(bgTexture != null){
            textureRegion = new TextureRegion(bgTexture);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        float curX = getX();
        float curY = getY();
        float newX = curX + (velocity.x * delta);
        float newY = curY + (velocity.y * delta);
        setPosition(newX, newY);

    }

    @Override
    protected void drawBefore(Batch batch, float parentAlpha) {
        super.drawBefore(batch, parentAlpha);
        if(textureRegion != null){
            batch.draw(textureRegion, getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }

    @Override
    protected void drawFull(Batch batch, float parentAlpha) {
        //do nothing
    }
}
