package com.pineapplepiranha.games;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.kasetagen.engine.gdx.scenes.scene2d.KasetagenStateUtil;
import com.pineapplepiranha.games.data.IDataSaver;
import com.pineapplepiranha.games.delegate.IGameProcessor;
import com.pineapplepiranha.games.screen.CityMenuScreen;
import com.pineapplepiranha.games.util.AssetsUtil;

public class CityLightsGame extends Game implements IGameProcessor {

    private static final String CITY_LIGHTS_DATA_NAME = "CityLightsGame";

    public static final String MENU = "menu";
    public static final String OPTIONS = "options";
    public static final String GAME = "game";

    boolean isInitialized = false;

    protected AssetManager assetManager;

    private CityMenuScreen menu;
    //private CityPuzzleScreen gameScreen;

    @Override
    public void create () {
        assetManager = new AssetManager();
        loadAssets();
    }

    @Override
    public void render () {
        if(assetManager.update()){

            if(!isInitialized){
                KasetagenStateUtil.setDebugMode(false);
                changeToScreen(MENU);
                isInitialized = true;
            }

            Gdx.gl.glClearColor(.16f, .14f, .13f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            super.render();
        }
    }

    public void loadAssets(){
        assetManager.load(AssetsUtil.TITLE_SCREEN, AssetsUtil.TEXTURE);
        assetManager.load(AssetsUtil.COURIER_FONT_12, AssetsUtil.BITMAP_FONT);
        assetManager.load(AssetsUtil.COURIER_FONT_18, AssetsUtil.BITMAP_FONT);
        assetManager.load(AssetsUtil.COURIER_FONT_32, AssetsUtil.BITMAP_FONT);
    }

    //IGameProcessor
    @Override
    public AssetManager getAssetManager() {
        return assetManager;
    }

    @Override
    public void changeToScreen(String screenName) {
        if(MENU.equalsIgnoreCase(screenName)){
            if(menu == null){
                menu = new CityMenuScreen(this);
            }

            setScreen(menu);
            Gdx.input.setInputProcessor(menu.getStage());

        }else if(GAME.equalsIgnoreCase(screenName)){
            //Load the Game Screen!!
//            if(gameScreen == null){
//                gameScreen = new CityPuzzleScreen(this);
//            }
//
//            setScreen(gameScreen);
//            Gdx.input.setInputProcessor(gameScreen.getStage());
        }
    }

    @Override
    public String getStoredString(String key) {
        Preferences preferences = Gdx.app.getPreferences(CITY_LIGHTS_DATA_NAME);
        String value = "";
        if(preferences.contains(key)){
            value = preferences.getString(key);
        }
        return value;
    }

    @Override
    public int getStoredInt(String key) {
        Preferences preferences = Gdx.app.getPreferences(CITY_LIGHTS_DATA_NAME);
        int value = -1;
        if(preferences.contains(key)){
            value = preferences.getInteger(key);
        }
        return value;
    }

    @Override
    public float getStoredFloat(String key) {
        Preferences preferences = Gdx.app.getPreferences(CITY_LIGHTS_DATA_NAME);
        float value = -1f;
        if(preferences.contains(key)){
            value = preferences.getFloat(key);
        }
        return value;
    }

    @Override
    public void saveGameData(IDataSaver saver) {
        Preferences preferences = Gdx.app.getPreferences(CITY_LIGHTS_DATA_NAME);
        saver.updatePreferences(preferences);
        preferences.flush();
    }
}
