package com.pineapplepiranha.games.scene2d.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.pineapplepiranha.games.data.IDataSaver;
import com.pineapplepiranha.games.delegate.IGameProcessor;
import com.pineapplepiranha.games.scene2d.GenericActor;
import com.pineapplepiranha.games.scene2d.actor.*;
import com.pineapplepiranha.games.util.AssetsUtil;
import com.pineapplepiranha.games.util.ViewportUtil;

import java.util.Comparator;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 8/23/14
 * Time: 9:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class StealthNessieStage extends BaseStage {

    private static float MIN_X = 1f;
    private static float MAX_X = ViewportUtil.VP_WIDTH*5;
    private static float MIN_Y = 1f;
    private static float MAX_Y = ViewportUtil.VP_HEIGHT/3;
    private static float CAMERA_TRIGGER = ViewportUtil.VP_WIDTH/2;
    private static float MAX_COVER = 20;
    private static float MAX_PATROLS = 5;
    private static float MAX_DISGUISE = 3;
    private static int NO_TREES_ZONE = 1400;

    private static float ICON_SIZE = 50f;

    private static float DEPTH_HEIGHT = 20f;

    private static Vector2 playerPos;

    static {
        playerPos = new Vector2(10f, 10f);
    }

    //Actor Groups
    public LevelBackground background;
    public Waves waves;
    public Array<Cover> availableCover;
    public Array<Patroler> patrolers;
    public Array<Disguise> disguises;
    public Player player;
    public DisguisePowerUps disguisePowerUps;

    //Ambiance
    private Music bgMusic;


    public StealthNessieStage(IGameProcessor gameProcessor){
        super(gameProcessor);
        AssetManager am = gameProcessor.getAssetManager();
        availableCover = new Array<Cover>();
        patrolers = new Array<Patroler>();
        disguises = new Array<Disguise>();

        bgMusic = am.get(AssetsUtil.GAME_MUSIC, AssetsUtil.MUSIC);
        bgMusic.setVolume(gameProcessor.getStoredFloat(IDataSaver.BG_MUSIC_VOLUME_PREF_KEY));

        bgMusic.setLooping(true);
        bgMusic.play();

        Texture bgTexture = am.get(AssetsUtil.GAME_BG, AssetsUtil.TEXTURE);
        background = new LevelBackground(0f, 0f, bgTexture.getWidth(), bgTexture.getHeight(), bgTexture);
        addActor(background);
        background.setZIndex(0);

        TextureRegion wavesTexture = new TextureRegion(am.get(AssetsUtil.WAVES, AssetsUtil.TEXTURE));
        waves = new Waves(-80f, -80f, wavesTexture, 0f, 0f);
        addActor(waves);
        waves.setZIndex(1);

        TextureAtlas atlas = am.get(AssetsUtil.ANIMATION_ATLAS, AssetsUtil.TEXTURE_ATLAS);
        Animation walking = new Animation(1f/5f, atlas.findRegions("nessie/Walk"));
        TextureRegion hidingTr = new TextureRegion(am.get(AssetsUtil.NESSIE_PTRN, AssetsUtil.TEXTURE));
        hidingTr.flip(true, false);
        TextureRegion normalTr = atlas.findRegion("nessie/Still");
        normalTr.flip(true, false);

        TextureRegion disguisedTr = atlas.findRegion("nessie/Disguised");
        disguisedTr.flip(true, false);

        player = new Player(0f, 5f, 150f, 150f, walking);
        player.setHidingTexture(hidingTr);
        player.setNormalTexture(normalTr);
        player.disguisedTexture = disguisedTr;
        addActor(player);

        initializeDisguies(am);
        initializePatrolers(am);
        initializeCover(am);

        TextureRegion maskIcon = new TextureRegion(am.get(AssetsUtil.MASK_ICON, AssetsUtil.TEXTURE));
        disguisePowerUps = new DisguisePowerUps(ICON_SIZE,
                                                 ViewportUtil.VP_HEIGHT - (ICON_SIZE*2),
                                                 ICON_SIZE * 4,
                                                 ICON_SIZE, maskIcon);
        addActor(disguisePowerUps);
        disguisePowerUps.addIndicator("NOSE", maskIcon);

        TextureRegion tr = new TextureRegion(am.get(AssetsUtil.GRASS, AssetsUtil.TEXTURE));
        GenericActor a = new GenericActor(0f, 0f, tr.getRegionWidth(), tr.getRegionHeight(), tr, Color.WHITE);
        addActor(a);

        initializeInputListeners();
    }

    private void initializeCover(AssetManager am){
        Random rand = new Random(System.currentTimeMillis());
        for(int i=0;i<MAX_COVER;i++){
            float x = rand.nextInt((int)MAX_X - (NO_TREES_ZONE)) + NO_TREES_ZONE;
            float y = rand.nextInt((int)MAX_Y);

            Texture t = null;
            float width = 10f;
            float height = 10f;
            switch(i%3){
                case 0:
                    t = am.get(AssetsUtil.TREE_1, AssetsUtil.TEXTURE);
                    width = 129f;
                    height = 121f;
                    break;
                case 1:
                    t = am.get(AssetsUtil.BUSH, AssetsUtil.TEXTURE);
                    width = 150f;
                    height = 254f;
                    break;
                case 2:
                    t = am.get(AssetsUtil.TREE_2, AssetsUtil.TEXTURE);
                    width = 161f;
                    height = 279f;
                    break;
                default:
                    break;
            }
            TextureRegion tr = new TextureRegion(t);
            Cover c = new Cover(x, y, width, height, tr, (int)Math.floor(x/DEPTH_HEIGHT));
            availableCover.add(c);
            addActor(c);
        }
    }

    private void initializePatrolers(AssetManager am){
        TextureRegion tr = new TextureRegion(am.get(AssetsUtil.TREE_1, AssetsUtil.TEXTURE));
        TextureRegion flashlightTexture = new TextureRegion(am.get(AssetsUtil.FLASHLIGHT, AssetsUtil.TEXTURE));
        Random rand = new Random(System.currentTimeMillis()*System.currentTimeMillis());
        for(int i=0;i<MAX_PATROLS;i++){
            float x = rand.nextInt((int)MAX_X - NO_TREES_ZONE) + NO_TREES_ZONE;
            float y = rand.nextInt((int)MAX_Y);


            Patroler p = new Patroler(x, y, 50f, 100f, tr, (int)Math.floor(x/DEPTH_HEIGHT), ViewportUtil.VP_WIDTH);
            p.setFlashlightTextureRegion(flashlightTexture);

            patrolers.add(p);
            addActor(p);
        }
    }

    private void initializeDisguies(AssetManager am){
        Texture disguiseTexture = am.get(AssetsUtil.MASK_ICON, AssetsUtil.TEXTURE);
        for(int i=0;i<MAX_DISGUISE;i++){
            float adjust = (background.getWidth()/2)/3;
            Disguise d = new Disguise(background.getWidth()/2 +(i*adjust), MAX_Y-ICON_SIZE, ICON_SIZE, ICON_SIZE/2, disguiseTexture, "NOSE");
            disguises.add(d);
            addActor(d);
        }

    }


    private void initializeInputListeners(){
        this.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(Input.Keys.RIGHT == keycode){
                    player.setXVelocity(player.speed);
                }else if(Input.Keys.LEFT == keycode){
                    player.setXVelocity(-player.speed);
                }

                if(Input.Keys.UP == keycode){
                    player.setYVelocity(player.speed);
                }else if(Input.Keys.DOWN == keycode){
                    player.setYVelocity(-player.speed);
                }

                if(Input.Keys.SHIFT_LEFT == keycode){
                    player.speed = player.speed*2f;
                    player.setXVelocity(player.velocity.x*2f);
                    player.setYVelocity(player.velocity.y*2f);
                    player.animation.setFrameDuration(player.animation.getFrameDuration()/2f);
                }


                if(Input.Keys.SPACE == keycode){
                    if(!player.isHiding && !player.isDisguised && disguisePowerUps.hasDisguise()){
                        player.isDisguised = true;
                        disguisePowerUps.popIndicator();
                    }else if(player.isDisguised){
                        player.isDisguised = false;
                    }
                }

                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if(Input.Keys.RIGHT == keycode && !Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                    player.setXVelocity(0);
                }else if(Input.Keys.LEFT == keycode && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                    player.setXVelocity(0);
                }

                if(Input.Keys.UP == keycode && !Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                    player.setYVelocity(0);
                }else if(Input.Keys.DOWN == keycode && !Gdx.input.isKeyPressed(Input.Keys.UP)){
                    player.setYVelocity(0);
                }

                if(Input.Keys.SHIFT_LEFT == keycode){
                    player.speed = player.speed/2f;
                    player.setXVelocity(player.velocity.x/2f);
                    player.setYVelocity(player.velocity.y/2f);
                    player.animation.setFrameDuration(player.animation.getFrameDuration()*2f);
                }

                return true;
            }
        });
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        player.isHiding = false;
        for(Cover c:availableCover){

            if(player.collider.overlaps(c.collider)){
                if(player.getY() > c.getY()){
                    player.isHiding = true;
                }
            }
        }

        //Pickup Disguise!
        Disguise toRemove = null;
        for(Disguise d:disguises){
            if(player.collider.overlaps(d.collider)){
                toRemove = d;
                disguisePowerUps.addIndicator(d.disguiseType, new TextureRegion(gameProcessor.getAssetManager().get(AssetsUtil.MASK_ICON, AssetsUtil.TEXTURE)));
            }
        }

        if(toRemove != null){
            disguises.removeValue(toRemove, true);
            toRemove.remove();
        }

        float y = player.getY();
        player.depthPos = (int)Math.floor(MAX_Y-y/DEPTH_HEIGHT);

        if(player.getX() < MIN_X){
            player.setX(MIN_X);
        }else if(player.getY() > MAX_X){
            player.setY(MAX_X);
        }

        if(player.getY() < MIN_Y){
            player.setY(MIN_Y);
        }else if(player.getY() > MAX_Y){
            player.setY(MAX_Y);
        }

        reorderDepthActors();
        adjustCamera();

        //Handle Patroler Collistions!
        if(!player.isHiding && !player.isDisguised){
            boolean reset = false;
            for(Patroler p:patrolers){
                if(p.visionBox.overlaps(player.collider)){
                    reset = true;
                    break;
                }
            }

            if(reset){
                resetLevel();
            }
        }

    }

    private void reorderDepthActors(){
        Array<DepthActor> depths = new Array<DepthActor>();
        for(Actor a:getActors()){
             if(a instanceof DepthActor){
                 depths.add((DepthActor)a);
             }
        }

        depths.sort(new Comparator<DepthActor>() {
            @Override
            public int compare(DepthActor o1, DepthActor o2) {
                return -1*Float.compare(o1.getY(), o2.getY());
            }
        });

        int size = depths.size;
        int initialZ = waves.getZIndex()+1;
        for(DepthActor d:depths){
            d.setZIndex(initialZ++);
        }
    }

    private void adjustCamera() {
        if(player.getX() > CAMERA_TRIGGER){
            getCamera().position.set(player.getX(), getCamera().position.y, getCamera().position.z);
        }else{
            getCamera().position.set(CAMERA_TRIGGER, getCamera().position.y, getCamera().position.z);
        }

        float cameraLeft = getCamera().position.x - (getCamera().viewportWidth/2);
        float cameraTop = getCamera().position.y + (getCamera().viewportHeight/2);

        float offset = ICON_SIZE*1.25f;
        disguisePowerUps.setPosition(cameraLeft + offset, cameraTop - offset);
    }

    private void resetLevel(){

        bgMusic.stop();

        for(Patroler p:patrolers){
            p.remove();
        }
        patrolers.clear();

        for(Disguise d:disguises){
            d.remove();
        }
        disguises.clear();

        for(Cover c:availableCover){
            c.remove();
        }
        availableCover.clear();

        player.setPosition(playerPos.x, playerPos.y);
        player.setXVelocity(0f);
        player.setYVelocity(0f);

        while(disguisePowerUps.hasDisguise()){
            disguisePowerUps.popIndicator();
        }
        disguisePowerUps.addIndicator("NOSE", new TextureRegion(gameProcessor.getAssetManager().get(AssetsUtil.MASK_ICON, AssetsUtil.TEXTURE)));

        initializeCover(gameProcessor.getAssetManager());
        initializePatrolers(gameProcessor.getAssetManager());
        initializeDisguies(gameProcessor.getAssetManager());

        adjustCamera();

        bgMusic.play();

    }
}
