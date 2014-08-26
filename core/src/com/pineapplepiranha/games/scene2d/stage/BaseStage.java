package com.pineapplepiranha.games.scene2d.stage;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kasetagen.engine.gdx.scenes.scene2d.KasetagenStateUtil;
import com.pineapplepiranha.games.delegate.IGameProcessor;
import com.pineapplepiranha.games.util.ViewportUtil;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 8/23/14
 * Time: 12:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaseStage extends Stage {

    protected IGameProcessor gameProcessor;

    public BaseStage(){
        super(new StretchViewport(ViewportUtil.VP_WIDTH, ViewportUtil.VP_HEIGHT));
    }

    public BaseStage(IGameProcessor gameProcessor){
        super(new StretchViewport(ViewportUtil.VP_WIDTH, ViewportUtil.VP_HEIGHT));
        this.gameProcessor = gameProcessor;
    }

    @Override
    public boolean keyDown(int keyCode) {
        boolean result = super.keyDown(keyCode);

//        if(Input.Keys.TAB == keyCode){
//            KasetagenStateUtil.setDebugMode(!KasetagenStateUtil.isDebugMode());
//        }

        return result;
    }
}
