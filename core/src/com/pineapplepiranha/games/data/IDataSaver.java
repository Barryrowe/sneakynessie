package com.pineapplepiranha.games.data;

import com.badlogic.gdx.Preferences;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 7/11/14
 * Time: 9:06 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IDataSaver {

    public static final String BG_MUSIC_VOLUME_PREF_KEY = "BG_MUSIC_VOLUME";
    public static final String SFX_MUSIC_VOLUME_PREF_KEY = "SFX_MUSIC_VOLUME";
    public void updatePreferences(Preferences prefs);
}
