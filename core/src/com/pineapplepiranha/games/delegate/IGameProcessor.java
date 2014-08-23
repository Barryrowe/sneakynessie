package com.pineapplepiranha.games.delegate;

import com.badlogic.gdx.assets.AssetManager;
import com.pineapplepiranha.games.data.IDataSaver;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 5/26/14
 * Time: 1:04 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IGameProcessor {

    public AssetManager getAssetManager();
    public void changeToScreen(String screenName);
    public String getStoredString(String key);
    public int getStoredInt(String key);
    public float getStoredFloat(String key);

    public void saveGameData(IDataSaver saver);
}
