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
    public static final String CITYSCAPE = "images/cityscape.png";
    public static final String CIRCLE = "images/windrose.png";
    public static final String TREE_1 = "images/tree-1.png";
    public static final String TREE_2 = "images/tree-2.png";
    public static final String NESSIE_PTRN = "images/Nessi-Pattern.png";
    public static final String NESSIE = "images/Nessi-Teal.png";
    public static final String FLASHLIGHT = "images/flashlight.png";

    //Font Paths
    public static final String COURIER_FONT_12 = "fonts/courier-new-bold-12.fnt";
    public static final String COURIER_FONT_18 = "fonts/courier-new-bold-18.fnt";
    public static final String COURIER_FONT_32 = "fonts/courier-new-bold-32.fnt";
    public static final String COURIER_FONT_IMG = "fonts/courier-new-bold.png";

    //Music
    public static final String BG_MUSIC = "music/robowack.mp3";
    public static final String GAME_MUSIC = "music/cityofscrap.mp3";
}
