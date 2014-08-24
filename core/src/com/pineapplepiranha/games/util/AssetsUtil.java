package com.pineapplepiranha.games.util;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 5/26/14
 * Time: 12:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class AssetsUtil {

    public static Class<BitmapFont> BITMAP_FONT = BitmapFont.class;
    public static Class<Texture> TEXTURE = Texture.class;
    public static Class<Sound> SOUND = Sound.class;
    public static Class<Music> MUSIC = Music.class;
    public static Class<TextureAtlas> TEXTURE_ATLAS = TextureAtlas.class;
    public static Class<ParticleEffect> PARTICLE = ParticleEffect.class;
    public static Class<Skin> SKIN = Skin.class;

    //TextureAtlases
    public static final String ANIMATION_ATLAS = "animations/animations.atlas";

    //Skin
    public static final String DEFAULT_SKIN = "uiskin.json";

    //Images
    public static final String TITLE_SCREEN = "images/bg_city.png";
    public static final String GAME_BG = "images/Background-D.png";
    public static final String TREE_1 = "images/Tree.png";
    public static final String TREE_2 = "images/Pine-Tree.png";
    public static final String BUSH = "images/Bush.png";
    public static final String NESSIE_PTRN = "images/Nessi-Pattern.png";
    public static final String FLASHLIGHT = "images/flashlight.png";
    public static final String MASK_ICON = "images/Mask_Icon.png";
    public static final String GRASS = "images/Front-Grass.png";
    public static final String WAVES = "images/Waves.png";
    public static final String FAR_TREES = "images/Background-Tree-Back.png";
    public static final String NEAR_TREES = "images/Background-Tree-Front.png";
    public static final String CLOUDS = "images/Dark-Clouds.jpg";
    public static final String LIGHT = "images/light-2.png";
    public static final String MOON = "images/Moon.png";
    public static final String MOUNTAINS = "images/Mountains.png";
    public static final String END_SCREEN = "images/endscreen.png";

    //Font Paths
    public static final String COURIER_FONT_12 = "fonts/courier-new-bold-12.fnt";
    public static final String COURIER_FONT_18 = "fonts/courier-new-bold-18.fnt";
    public static final String COURIER_FONT_32 = "fonts/courier-new-bold-32.fnt";
    public static final String COURIER_FONT_IMG = "fonts/courier-new-bold.png";

    //Music
    public static final String GAME_MUSIC = "music/crystal_caves_1_2.mp3";//"music/cityofscrap.mp3";

    //SFX
    public static final String ALIEN_UP = "sfx/alien_up.mp3";
    public static final String WHISTLE = "sfx/whistle.mp3";
}
