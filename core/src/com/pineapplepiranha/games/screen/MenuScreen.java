package com.pineapplepiranha.games.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pineapplepiranha.games.SneakyNessieGame;
import com.pineapplepiranha.games.data.IDataSaver;
import com.pineapplepiranha.games.delegate.IGameProcessor;
import com.pineapplepiranha.games.delegate.IStageManager;
import com.pineapplepiranha.games.scene2d.stage.BaseStage;
import com.pineapplepiranha.games.scene2d.GenericActor;
import com.pineapplepiranha.games.util.AssetsUtil;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 8/23/14
 * Time: 12:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class MenuScreen extends ApplicationAdapter implements Screen, InputProcessor, IStageManager {

        protected IGameProcessor gameProcessor;

        private Stage stage;
        private TextButton startGameButton;
        private TextButton optionsButton;
        private TextureRegion bgTextureRegion;

        private Music menuMusic;


        public MenuScreen(IGameProcessor delegate){
            this.gameProcessor = delegate;
            stage = new BaseStage();
            menuMusic = delegate.getAssetManager().get(AssetsUtil.MENU_MUSIC, AssetsUtil.MUSIC);


            ClickListener listener = new ClickListener()
            {
                @Override
                public void clicked(InputEvent event, float x, float y)
                {
                    Actor btn = event.getListenerActor();

                    if(btn == startGameButton){
                        gameProcessor.changeToScreen(SneakyNessieGame.GAME);

                    }
                    else if (btn == optionsButton){
                        gameProcessor.changeToScreen(SneakyNessieGame.OPTIONS);
                    }
                }
            };

            bgTextureRegion = new TextureRegion(gameProcessor.getAssetManager().get(AssetsUtil.TITLE_SCREEN, AssetsUtil.TEXTURE));
            stage.addActor(new GenericActor(0, 0, stage.getWidth(), stage.getHeight(), bgTextureRegion, Color.DARK_GRAY));

            TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
            style.font = gameProcessor.getAssetManager().get(AssetsUtil.COURIER_FONT_32, AssetsUtil.BITMAP_FONT);
            style.fontColor =  Color.WHITE;
            style.overFontColor = Color.TEAL;
            style.downFontColor = Color.WHITE;
            float fontScale = 2f;
            style.font.setScale(fontScale);

            startGameButton = new TextButton("Start Sneakin'", style);
            startGameButton.addListener(listener);
            startGameButton.setPosition(((stage.getWidth()/4) * 3) - startGameButton.getWidth()/2, -5f);

            stage.addActor(startGameButton);

//            optionsButton = new TextButton("Options", style);
//            optionsButton.setPosition(((stage.getWidth() / 4) * 3) - startGameButton.getWidth() / 2, (stage.getHeight() / 8));
//            optionsButton.addListener(listener);
//
//            stage.addActor(optionsButton);
        }

        @Override
        public void render(float delta) {
            stage.act(delta);
            stage.draw();
        }

        @Override
        public void resize(int width, int height) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void show() {
            menuMusic.play();
            menuMusic.setLooping(true);
            menuMusic.setVolume(0.5f);
        }

        @Override
        public void hide() {
            menuMusic.stop();
        }

        @Override
        public void pause() {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void resume() {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void dispose() {
            //To change body of implemented methods use File | Settings | File Templates.
        }


        //
//Input Processor
//
        @Override
        public boolean keyDown(int keycode) {

            if(keycode == Input.Keys.SPACE){
                gameProcessor.changeToScreen(SneakyNessieGame.GAME);
            }

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

        //
//IStageManager
//
        public Stage getStage(){
            return stage;
        }
}
