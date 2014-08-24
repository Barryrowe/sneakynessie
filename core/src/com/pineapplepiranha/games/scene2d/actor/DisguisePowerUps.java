package com.pineapplepiranha.games.scene2d.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.pineapplepiranha.games.scene2d.GenericGroup;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 8/23/14
 * Time: 9:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class DisguisePowerUps extends GenericGroup{

    private float INDICATOR_WIDTH = 50f;
    private float BUFFER = 10f;
    private TextureRegion indicatorRegion;

    private Array<DisguiseIndicator> indicators;

    public DisguisePowerUps(float x, float y, float width, float height, TextureRegion tr){
        super(x, y, width, height, Color.MAROON);

        indicatorRegion = tr;
        indicators = new Array<DisguiseIndicator>();
    }

    public void addIndicator(String type, TextureRegion tr){
        float x = indicators.size * (INDICATOR_WIDTH + BUFFER);
        float y = 0f;
        float width = INDICATOR_WIDTH;
        float height = INDICATOR_WIDTH;
        DisguiseIndicator indicator = new DisguiseIndicator(x, y, width, height, tr);
        indicator.disguiseType = type;
        this.addActor(indicator);
        indicators.add(indicator);

    }

    public DisguiseIndicator popIndicator(){
       DisguiseIndicator i = indicators.pop();
        i.remove();
        return i;
    }

    public boolean hasDisguise() {
        return indicators.size > 0;
    }
}
