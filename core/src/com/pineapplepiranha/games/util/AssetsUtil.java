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

    //TextureAtlases
    public static final String ANIMATION_ATLAS = "animations/animations.atlas";

    //Images
    public static final String TITLE_SCREEN = "images/Title.jpg";
    public static final String TREE_1 = "images/Tree.png";
    public static final String TREE_2 = "images/Pine-Tree.png";
    public static final String BUSH = "images/Bush.png";
    public static final String FLASHLIGHT = "images/flashlight.png";
    public static final String MASK_ICON = "images/Mask_Icon_Circle.png";
    public static final String GRASS = "images/Front-Grass.png";
    public static final String WAVES = "images/Waves.png";
    public static final String LIGHT = "images/light-2.png";
    public static final String MOON = "images/Moon.png";
    public static final String INSTRUCTIONS = "images/Instruction_Screen.jpg";
    public static final String ALIEN_LIGHT = "images/alien-light.png";
    public static final String SPEECH_BUBBLE = "images/WHN_Speech.png";
    public static final String BARREL = "images/Barrel.png";
    public static final String LAMP_POST = "images/Lamp_Post.png";
    public static final String LANTERN = "images/Lantern.png";
    public static final String HANGER = "images/Hanger.png";


    //Font Paths
    public static final String COURIER_FONT_12 = "fonts/courier-new-bold-12.fnt";
    public static final String COURIER_FONT_18 = "fonts/courier-new-bold-18.fnt";
    public static final String COURIER_FONT_32 = "fonts/courier-new-bold-32.fnt";
    public static final String COURIER_FONT_IMG = "fonts/courier-new-bold.png";

    //Music
    public static final String GAME_MUSIC = "music/crystal_caves_1_2.mp3";
    public static final String MENU_MUSIC = "music/lazy_day_v0.9.mp3";

    //SFX
    public static final String ALIEN_UP = "sfx/alien_up.mp3";
    public static final String WHISTLE = "sfx/whistle.mp3";
    public static final String WALKING = "sfx/nessie_walk.mp3";
}
