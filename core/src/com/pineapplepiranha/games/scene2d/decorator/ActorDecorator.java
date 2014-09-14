package com.pineapplepiranha.games.scene2d.decorator;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 9/14/14
 * Time: 3:17 PM
 * To change this template use File | Settings | File Templates.
 */
public interface  ActorDecorator {

    public void applyAdjustment(Actor a, float delta);
}
