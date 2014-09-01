package com.pineapplepiranha.games.scene2d.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.pineapplepiranha.games.scene2d.GenericActor;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 8/31/14
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class BlockingActor extends GenericActor {

    //TODO: Implement Left/Right/Top/Bottom blocking detection
    //      This is imcomplete. Abandoning for now.
    public BlockingActor(float x, float y, float width, float height){
        super(x, y, width, height, Color.RED);

        Gdx.app.log("NEW BLOCKING COLLIDER", "COLLIDER:" + collider.x + " - " + collider.y + " [ " + collider.width + " ] [ " + collider.height + " ]");
    }

    public Vector2 getCollisionAdjustments(GenericActor intruder){
        Vector2 intruderPosition = new Vector2(intruder.collider.x, intruder.collider.y);

        Vector2 intruderAdjusts = new Vector2(0f, 0f);

        if(intruder.collider.overlaps(collider)){

            float intruderLeft =intruder.collider.x;
            float intruderRight = intruder.collider.x + intruder.collider.width;
            float intruderBottom = intruder.collider.y;
            float intruderTop = intruder.collider.y + intruder.collider.height;

            float distToBottom = Math.abs(getY() - intruderBottom);
            float distToTop = Math.abs((getY() + getHeight()) - intruderBottom);
            float distToLeft = Math.abs(getX() - intruderLeft);
            float distToRight = Math.abs((getX() + getWidth()) - intruderLeft);


            float yAdjust = distToBottom <= distToTop ? distToBottom : distToTop;
            float xAdjust = distToLeft <= distToLeft ? distToLeft : distToRight;

            if(yAdjust < xAdjust){
                //Adjust y
                if(intruderBottom < getY()){
                    intruderPosition.y -= (intruderTop-getY());
                }else if(intruderTop > getY()){
                    intruderPosition.y += (getY() - intruderBottom);
                }else if(intruderBottom >= getY() && intruderTop <= (getY() + getHeight())){
                    intruderAdjusts.y -= (intruderBottom-getY());
                }
            }else{
                //Adjust x
                if(intruderLeft <= getX()){
                    intruderAdjusts.x -= (intruderRight - getX());
                }else if(intruderRight >= (getX() + getWidth())){
                    intruderAdjusts.x += (getX() - intruderLeft);
                }else if(intruderRight <= (getX() + getWidth()) && intruderLeft >= getX()){
                    intruderAdjusts.x -=  (intruderLeft-getX());
                }
            }
        }

        Gdx.app.log("COLLIDER", "Intruder is Overlapping!" + " Adjust X: " + intruderAdjusts.x + " Adjust Y: " + intruderAdjusts.y);
        return intruderAdjusts;
    }

}
