package com.pineapplepiranha.games.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pineapplepiranha.games.data.IDataSaver;
import com.pineapplepiranha.games.delegate.IGameProcessor;
import com.pineapplepiranha.games.delegate.IStageManager;
import com.pineapplepiranha.games.scene2d.stage.BaseStage;
import com.pineapplepiranha.games.scene2d.GenericActor;
import com.pineapplepiranha.games.scene2d.stage.StealthNessieStage;
import com.pineapplepiranha.games.util.AssetsUtil;
import com.pineapplepiranha.games.util.ViewportUtil;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 8/23/14
 * Time: 1:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameScreen extends ApplicationAdapter implements Screen, InputProcessor, IStageManager {

    protected IGameProcessor gameProcessor;

    private StealthNessieStage stage;


    public GameScreen(IGameProcessor delegate){
        gameProcessor = delegate;

        stage = new StealthNessieStage(gameProcessor);
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public boolean keyDown(int keycode) {

        return false;
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

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.resize(width, height);
    }


}
