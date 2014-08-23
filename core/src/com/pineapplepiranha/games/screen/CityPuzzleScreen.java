package com.pineapplepiranha.games.screen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pineapplepiranha.games.delegate.IGameProcessor;
import com.pineapplepiranha.games.delegate.IStageManager;
import com.pineapplepiranha.games.scene2d.BaseStage;
import com.pineapplepiranha.games.scene2d.GenericActor;
import com.pineapplepiranha.games.util.AssetsUtil;
import com.pineapplepiranha.games.util.ViewportUtil;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 8/23/14
 * Time: 1:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class CityPuzzleScreen extends ApplicationAdapter implements Screen, InputProcessor, IStageManager {

    protected IGameProcessor gameProcessor;

    private Stage stage;
    private TextureRegion bgTextureRegion;


    public CityPuzzleScreen(IGameProcessor delegate){
        gameProcessor = delegate;
        //TODO: Replace with Stage Implementation!!
        stage = new BaseStage();

        bgTextureRegion = new TextureRegion(gameProcessor.getAssetManager().get(AssetsUtil.CITYSCAPE, AssetsUtil.TEXTURE));
        stage.addActor(new GenericActor(0f, ViewportUtil.VP_HEIGHT/2, ViewportUtil.VP_WIDTH, ViewportUtil.VP_HEIGHT/2, bgTextureRegion, Color.YELLOW));

    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean keyTyped(char character) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean scrolled(int amount) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void hide() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
