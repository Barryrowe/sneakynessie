package com.pineapplepiranha.games;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.kasetagen.engine.gdx.scenes.scene2d.KasetagenStateUtil;
import com.pineapplepiranha.games.data.IDataSaver;
import com.pineapplepiranha.games.delegate.IGameProcessor;
import com.pineapplepiranha.games.screen.GameScreen;
import com.pineapplepiranha.games.screen.MenuScreen;
import com.pineapplepiranha.games.util.AssetsUtil;
import com.pineapplepiranha.games.util.ViewportUtil;

public class SneakyNessieGame extends Game implements IGameProcessor {

    private static final String CITY_LIGHTS_DATA_NAME = "SneakyNessieGame";

    public static final String MENU = "menu";
    public static final String OPTIONS = "options";
    public static final String GAME = "game";

    boolean isInitialized = false;

    protected AssetManager assetManager;

    private MenuScreen menu;
    private GameScreen gameScreen;

    @Override
    public void create () {
        assetManager = new AssetManager();
        loadAssets();

        Graphics.DisplayMode dm = Gdx.graphics.getDesktopDisplayMode();
        Gdx.app.log("DISPLAY", "W: " + dm.width + " H: " + dm.height + " X: " + dm.bitsPerPixel);
        //Gdx.graphics.setDisplayMode(dm.width, dm.height, true);
        Gdx.graphics.setVSync(true);
        //Gdx.input.setCursorCatched(true);
    }

    @Override
    public void render () {
        if(assetManager.update()){

            if(!isInitialized){
                KasetagenStateUtil.setDebugMode(false);
                changeToScreen(MENU);
                isInitialized = true;
            }

            Gdx.gl.glClearColor(0f, 0f, 0f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            super.render();
        }
    }

    public void loadAssets(){
        assetManager.load(AssetsUtil.TITLE_SCREEN, AssetsUtil.TEXTURE);
        assetManager.load(AssetsUtil.COURIER_FONT_12, AssetsUtil.BITMAP_FONT);
        assetManager.load(AssetsUtil.COURIER_FONT_18, AssetsUtil.BITMAP_FONT);
        assetManager.load(AssetsUtil.COURIER_FONT_32, AssetsUtil.BITMAP_FONT);

        assetManager.load(AssetsUtil.MENU_MUSIC, AssetsUtil.MUSIC);
        assetManager.load(AssetsUtil.GAME_MUSIC, AssetsUtil.MUSIC);
        assetManager.load(AssetsUtil.TREE_1, AssetsUtil.TEXTURE);
        assetManager.load(AssetsUtil.TREE_2, AssetsUtil.TEXTURE);
        assetManager.load(AssetsUtil.BUSH, AssetsUtil.TEXTURE);
        assetManager.load(AssetsUtil.FLASHLIGHT, AssetsUtil.TEXTURE);
        assetManager.load(AssetsUtil.MASK_ICON, AssetsUtil.TEXTURE);
        assetManager.load(AssetsUtil.GRASS, AssetsUtil.TEXTURE);
        assetManager.load(AssetsUtil.WAVES, AssetsUtil.TEXTURE);
        assetManager.load(AssetsUtil.LIGHT, AssetsUtil.TEXTURE);
        assetManager.load(AssetsUtil.MOON, AssetsUtil.TEXTURE);
        assetManager.load(AssetsUtil.INSTRUCTIONS, AssetsUtil.TEXTURE);
        assetManager.load(AssetsUtil.INSTRUCTIONS_CTRL, AssetsUtil.TEXTURE);
        assetManager.load(AssetsUtil.ALIEN_LIGHT, AssetsUtil.TEXTURE);
        assetManager.load(AssetsUtil.SPEECH_BUBBLE, AssetsUtil.TEXTURE);
        assetManager.load(AssetsUtil.BARREL, AssetsUtil.TEXTURE);
        assetManager.load(AssetsUtil.LAMP_POST, AssetsUtil.TEXTURE);
        assetManager.load(AssetsUtil.LANTERN, AssetsUtil.TEXTURE);
        assetManager.load(AssetsUtil.HANGER, AssetsUtil.TEXTURE);
        assetManager.load(AssetsUtil.PHONE_BOOTH, AssetsUtil.TEXTURE);
        assetManager.load(AssetsUtil.CORN_BACK, AssetsUtil.TEXTURE);
        assetManager.load(AssetsUtil.CORN_MID, AssetsUtil.TEXTURE);
        assetManager.load(AssetsUtil.CORN_FRONT, AssetsUtil.TEXTURE);
        assetManager.load(AssetsUtil.BRIDGE_FRONT, AssetsUtil.TEXTURE);
        assetManager.load(AssetsUtil.METAL_LAMP_POST, AssetsUtil.TEXTURE);
        assetManager.load(AssetsUtil.CRATE_1, AssetsUtil.TEXTURE);
        assetManager.load(AssetsUtil.CRATE_2, AssetsUtil.TEXTURE);



        assetManager.load(AssetsUtil.ALIEN_UP, AssetsUtil.SOUND);
        assetManager.load(AssetsUtil.WHISTLE, AssetsUtil.SOUND);
        assetManager.load(AssetsUtil.WALKING, AssetsUtil.SOUND);



        assetManager.load(AssetsUtil.ANIMATION_ATLAS, AssetsUtil.TEXTURE_ATLAS);
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
                menu = new MenuScreen(this);
            }

            setScreen(menu);
            Gdx.input.setInputProcessor(menu);

        }else if(GAME.equalsIgnoreCase(screenName)){
            //Load the Game Screen!!
            if(gameScreen == null){
                gameScreen = new GameScreen(this);
            }

            setScreen(gameScreen);
            Gdx.input.setInputProcessor(gameScreen.getStage());
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
        float value = 1f;
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
