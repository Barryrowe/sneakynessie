package com.pineapplepiranha.games.scene2d.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Array;
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

    private static float LEVEL_WIDTH = 12000f;
    private static float LEVEL_HEIGHT = 720f;

    private static float MIN_X = 1f;
    private static float MAX_X = 12000f;
    private static float MIN_Y = 1f;
    private static float MAX_Y = ViewportUtil.VP_HEIGHT/3;
    private static float CAMERA_TRIGGER = ViewportUtil.VP_WIDTH/2;
    private static float MAX_DISGUISE = 3;
    private static float MOON_PARALLAX_RATIO = 1f/1.02f;
    private static float DISTANT_PARALLAX_RATIO = 1f/2.5f;
    private static float FAR_PARALLAX_RATIO = 1f/5f;
    private static float NEAR_PARALLAX_RATIO = 1f/10f;

    //Stars
    private static float STAR_TWINKLE_RATE = 1f/3f;
    private static int MAX_STARS = 20;
    private static float STAR_SIZE = 30f;

    private static float ICON_SIZE = 50f;

    private static float DEPTH_HEIGHT = 20f;

    private static Vector2 playerPos, distantParalaxPos,farParallaxPos, nearParallaxPos, initialMoonPos, powerupsPos, pickupPointPos;

    static {
        playerPos = new Vector2(250f, 170f);
        distantParalaxPos = new Vector2(700f, -50f);
        farParallaxPos = new Vector2(600f, 0f);
        nearParallaxPos = new Vector2(475f, 0f);
        initialMoonPos = new Vector2(ViewportUtil.VP_WIDTH/2, (ViewportUtil.VP_HEIGHT/4)*3);
        powerupsPos = new Vector2((ViewportUtil.VP_WIDTH/2) - (ICON_SIZE*3/2), ViewportUtil.VP_HEIGHT - (ICON_SIZE*1.5f));
        pickupPointPos = new Vector2(10840  , 0f);
    }

    //Actor Groups
    public LevelBackground clouds;
    public LevelBackground background;
    public Waves waves;
    public Array<Cover> availableCover;
    public Array<Patroler> patrolers;
    public Array<Disguise> disguises;
    public Array<AnimatedActor> stars;
    public Player player;
    public DisguisePowerUps disguisePowerUps;
    public Array<BlockingActor> blockingActors;

    private boolean isComplete = false;

    //Ambiance
    private Music bgMusic;
    private int depthInitialIndex;
    private Sound alienUp;
    private Sound whistle;
    private Sound walking;
    private long walkId = -1L;


    public GenericActor moon;
    public AnimatedActor landingPad;
    public GenericActor endScreen;
    public GenericActor grass;
    public LevelBackground distantParallax;
    public LevelBackground farParallax;
    public LevelBackground nearParallax;

    public StealthNessieStage(IGameProcessor gameProcessor){
        super(gameProcessor);
        AssetManager am = gameProcessor.getAssetManager();
        TextureAtlas atlas = am.get(AssetsUtil.ANIMATION_ATLAS, AssetsUtil.TEXTURE_ATLAS);
        availableCover = new Array<Cover>();
        patrolers = new Array<Patroler>();
        disguises = new Array<Disguise>();
        stars = new Array<AnimatedActor>();
        blockingActors = new Array<BlockingActor>();

        alienUp = am.get(AssetsUtil.ALIEN_UP, AssetsUtil.SOUND);
        whistle = am.get(AssetsUtil.WHISTLE, AssetsUtil.SOUND);
        walking = am.get(AssetsUtil.WALKING, AssetsUtil.SOUND);
        bgMusic = am.get(AssetsUtil.GAME_MUSIC, AssetsUtil.MUSIC);
        bgMusic.setVolume(0.5f);//gameProcessor.getStoredFloat(IDataSaver.BG_MUSIC_VOLUME_PREF_KEY));

        bgMusic.setLooping(true);
        bgMusic.play();

        clouds = new LevelBackground(0f, 0f, LEVEL_WIDTH, LEVEL_HEIGHT, atlas.findRegions("clouds/Clouds"));
        addActor(clouds);
        clouds.setZIndex(depthInitialIndex++);

        TextureRegion moonTexture = new TextureRegion(am.get(AssetsUtil.MOON, AssetsUtil.TEXTURE));
        moon = new GenericActor(initialMoonPos.x, initialMoonPos.y, 150f, 150f, moonTexture, Color.YELLOW);
        addActor(moon);
        moon.setZIndex(depthInitialIndex++);

        distantParallax = new LevelBackground(distantParalaxPos.x, distantParalaxPos.y, 9201f, 720f, atlas.findRegions("bg/Mountains"));
        addActor(distantParallax);
        distantParallax.setZIndex(depthInitialIndex++);

        farParallax = new LevelBackground(farParallaxPos.x, farParallaxPos.y, 9201f, 720f, atlas.findRegions("bg/BTree"));
        addActor(farParallax);
        farParallax.setZIndex(depthInitialIndex++);

        nearParallax = new LevelBackground(nearParallaxPos.x, nearParallaxPos.y, 9891f, 720f, atlas.findRegions("bg/FTree"));
        addActor(nearParallax);
        nearParallax.setZIndex(depthInitialIndex++);


        background = new LevelBackground(0f, 0f, LEVEL_WIDTH, LEVEL_HEIGHT, atlas.findRegions("bg/Background"));
        addActor(background);
        background.setZIndex(depthInitialIndex++);

        TextureRegion wavesTexture = new TextureRegion(am.get(AssetsUtil.WAVES, AssetsUtil.TEXTURE));
        waves = new Waves(-80f, -80f, wavesTexture, 0f, 0f);
        addActor(waves);
        waves.setZIndex(depthInitialIndex++);

        Animation glowingAnimation = new Animation(STAR_TWINKLE_RATE, atlas.findRegions("bg/Glow"));
        landingPad = new AnimatedActor(pickupPointPos.x, pickupPointPos.y, 900f, 187f, glowingAnimation, 0f);
        addActor(landingPad);
        landingPad.setZIndex(depthInitialIndex++);


        Animation walking = new Animation(1f/5f, atlas.findRegions("nessie/Walking"));
        Animation sadNessie = new Animation(1f/7f, atlas.findRegions("nessie/Cry"));
        Animation runNessie = new Animation(1f/6f, atlas.findRegions("nessie/Run"));
        TextureRegion hidingTr = atlas.findRegion("nessie/Cammo");
        hidingTr.flip(true, false);
        TextureRegion normalTr = atlas.findRegion("nessie/Stills");
        normalTr.flip(true, false);

        TextureRegion disguisedTr = atlas.findRegion("nessie/Disguised");
        disguisedTr.flip(true, false);

        player = new Player(playerPos.x, playerPos.y, 200f, 200f, walking);
        player.sadAnimation = sadNessie;
        player.runningAnimation = runNessie;
        player.setHidingTexture(hidingTr);
        player.setNormalTexture(normalTr);
        player.disguisedTexture = disguisedTr;
        addActor(player);

        initializeDisguies(am);
        initializePatrolers(am);
        initializeCover(am);
        initializeStars(am);
        initializeBlockingComponents();

        TextureRegion maskIcon = new TextureRegion(am.get(AssetsUtil.MASK_ICON, AssetsUtil.TEXTURE));
        disguisePowerUps = new DisguisePowerUps(powerupsPos.x,
                                                 powerupsPos.y,
                                                 ICON_SIZE * 4,
                                                 ICON_SIZE, maskIcon);
        addActor(disguisePowerUps);
        disguisePowerUps.addIndicator("NOSE", maskIcon);

        TextureRegion tr = new TextureRegion(am.get(AssetsUtil.GRASS, AssetsUtil.TEXTURE));
        grass = new GenericActor(0f, 0f, tr.getRegionWidth(), tr.getRegionHeight(), tr, Color.WHITE);
        addActor(grass);

        initializeInputListeners();
        sharderStuff();
    }

    private void initializeCover(AssetManager am){


        Array<Vector2> bushPositions = new Array<Vector2>();
        bushPositions.add(new Vector2(940, 720-540));
        bushPositions.add(new Vector2(2040, 720-495));
        bushPositions.add(new Vector2(2060, 720-700));
        bushPositions.add(new Vector2(2575, 720-512));
        bushPositions.add(new Vector2(3356, 720-638));
        bushPositions.add(new Vector2(3682, 720-541));
        bushPositions.add(new Vector2(3816, 720-585));
        bushPositions.add(new Vector2(4378, 720-500));
        bushPositions.add(new Vector2(4570, 720-510));

        bushPositions.add(new Vector2(4575, 720-512));
        bushPositions.add(new Vector2(5356, 720-638));
        bushPositions.add(new Vector2(7682, 720-541));
        bushPositions.add(new Vector2(7816, 720-585));
        bushPositions.add(new Vector2(6378, 720-500));
        bushPositions.add(new Vector2(6570, 720-510));

        bushPositions.add(new Vector2(8575, 720-512));
        bushPositions.add(new Vector2(9356, 720-535));
        bushPositions.add(new Vector2(9378, 720-710));
        bushPositions.add(new Vector2(9570, 720-590));


        Array<Vector2> pineTreePositions = new Array<Vector2>();
        pineTreePositions.add(new Vector2(1260, 720-504));
        pineTreePositions.add(new Vector2(1530, 720-630));
        pineTreePositions.add(new Vector2(1803, 720-591));
        pineTreePositions.add(new Vector2(2289, 720-513));
        pineTreePositions.add(new Vector2(2403, 720-660));

        Array<Vector2> treePositions = new Array<Vector2>();
        treePositions.add(new Vector2(2154, 720-594));
        treePositions.add(new Vector2(2730, 720-666));
        treePositions.add(new Vector2(2805, 720-552));
        treePositions.add(new Vector2(2940, 720-651));
        treePositions.add(new Vector2(3039, 720-516));
        treePositions.add(new Vector2(3144, 720-717));
        treePositions.add(new Vector2(3450, 720-540));
        /*Bushes:
            bushPositions.add(new Vector2(940, 720-540));
            bushPositions.add(new Vector2(2040, 720-495));
            bushPositions.add(new Vector2(2060, 720-700));
            bushPositions.add(new Vector2(2575, 720-512));
            bushPositions.add(new Vector2(3356, 720-638));
            bushPositions.add(new Vector2(3682, 720-541));
            bushPositions.add(new Vector2(3816, 720-585));
            bushPositions.add(new Vector2(4378, 720-461));
            bushPositions.add(new Vector2(4570, 720-471));

        Pine Trees
            pineTreePositions.add(new Vector2(1260, 720-504));
            pineTreePositions.add(new Vector2(1530, 720-630));
            pineTreePositions.add(new Vector2(1803, 720-591));
            pineTreePositions.add(new Vector2(2289, 720-513));
            pineTreePositions.add(new Vector2(2403, 720-660));

        Trees
            reePositions.add(new Vector2(2154, 720-594));
            reePositions.add(new Vector2(2730, 720-666));
            reePositions.add(new Vector2(2805, 720-552));
            reePositions.add(new Vector2(2940, 720-651));
            reePositions.add(new Vector2(3039, 720-516));
            reePositions.add(new Vector2(3144, 720-717));
            reePositions.add(new Vector2(3450, 720-540));
         */

        TextureRegion bushRegion = new TextureRegion(am.get(AssetsUtil.BUSH, AssetsUtil.TEXTURE));
        float width = 129f;
        float height = 121f;
        for(Vector2 bv:bushPositions){
            Cover c = new Cover(bv.x, bv.y, width, height, bushRegion, 0);
            availableCover.add(c);
            addActor(c);
        }

        TextureRegion ptRegion = new TextureRegion(am.get(AssetsUtil.TREE_2, AssetsUtil.TEXTURE));
        width = 161f;
        height = 279f;
        for(Vector2 ptv:pineTreePositions){
            Cover c = new Cover(ptv.x, ptv.y, width, height, ptRegion, 0);
            availableCover.add(c);
            addActor(c);
        }

        TextureRegion tRegion = new TextureRegion(am.get(AssetsUtil.TREE_1, AssetsUtil.TEXTURE));
        width = 150f;
        height = 254f;
        for(Vector2 tv:treePositions){
            Cover c = new Cover(tv.x, tv.y, width, height, tRegion, 0);
            availableCover.add(c);
            addActor(c);
        }

    }

    private void initializePatrolers(AssetManager am){

        TextureAtlas atlas = am.get(AssetsUtil.ANIMATION_ATLAS, AssetsUtil.TEXTURE_ATLAS);

        Array<Vector3> patrolVals = new Array<Vector3>();
        patrolVals.add(new Vector3(1926, 720-504, 600));
        patrolVals.add(new Vector3(2598, 720-596, 500));
        patrolVals.add(new Vector3(4050, 720-562, 800));
        patrolVals.add(new Vector3(5012, 720-500, 750));
        patrolVals.add(new Vector3(5620, 720-660, 1500));
        patrolVals.add(new Vector3(6482, 720-578, 500));
        patrolVals.add(new Vector3(7012, 720-500, 750));
        patrolVals.add(new Vector3(7620, 720-660, 1500));
        patrolVals.add(new Vector3(8482, 720-578, 300));
        patrolVals.add(new Vector3(9500, 720-715, 800));
        patrolVals.add(new Vector3(9000, 720-560, 200));
        patrolVals.add(new Vector3(10500, 720-502, 500));
        patrolVals.add(new Vector3(10500, 720-602, 500));
        patrolVals.add(new Vector3(10500, 720-702, 500));
        Texture flTexture = am.get(AssetsUtil.FLASHLIGHT, AssetsUtil.TEXTURE);

        float width = 75f;
        float height = 100f;
        for(Vector3 pv:patrolVals){
            String animationName = pv.z > 500 ? "patrol/Patrol" : "patrol/PatrolA";
            Animation animation = new Animation(1f/3f, atlas.findRegions(animationName));
            TextureRegion flashlightTexture = new TextureRegion(flTexture);
            Patroler p = new Patroler(pv.x, pv.y, width, height, animation, 0, pv.z);
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

    private void initializeStars(AssetManager am){
        TextureAtlas atlas = am.get(AssetsUtil.ANIMATION_ATLAS, AssetsUtil.TEXTURE_ATLAS);
        Random rand = new Random(System.currentTimeMillis()*System.currentTimeMillis());
        Animation ani = new Animation(STAR_TWINKLE_RATE, atlas.findRegions("star/Star"));
        for(int i=0;i<MAX_STARS;i++){
            float x = rand.nextInt((int)MAX_X);
            float y = rand.nextInt((int)getHeight()- (int)initialMoonPos.y) + initialMoonPos.y;
            float width = rand.nextInt((int)(STAR_SIZE*1.25f)) + STAR_SIZE/4;
            float keyFrame = rand.nextInt((int)(1/STAR_TWINKLE_RATE))*STAR_TWINKLE_RATE;
            AnimatedActor star = new AnimatedActor(x, y, width, width, ani, keyFrame);
            stars.add(star);
            clouds.addActor(star);
            star.setZIndex(clouds.getChildren().size -1);
        }
    }

    private void initializeBlockingComponents(){

//        BlockingActor verticalBlock = new BlockingActor(0f, MAX_Y, 12000f, 300f);
//        blockingActors.add(verticalBlock);
//        addActor(verticalBlock);
//
//
//        BlockingActor startingBlock = new BlockingActor(500f, 0f, 20f, 720f);
//        blockingActors.add(startingBlock);
//        addActor(startingBlock);
//
//        //End Wall
//        BlockingActor ba = new BlockingActor(12000f, 0f, 10f, 720f);
//        blockingActors.add(ba);
//        addActor(ba);
    }

    private void initializeInputListeners(){
        this.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(!isComplete){
                    if(Input.Keys.RIGHT == keycode || Input.Keys.D == keycode){
                        player.setXVelocity(player.speed);
                    }else if(Input.Keys.LEFT == keycode || Input.Keys.A == keycode){
                        player.setXVelocity(-player.speed);
                    }

                    if(Input.Keys.UP == keycode || Input.Keys.W == keycode){
                        player.setYVelocity(player.speed);
                    }else if(Input.Keys.DOWN == keycode || Input.Keys.S == keycode){
                        player.setYVelocity(-player.speed);
                    }

                    if(Input.Keys.SHIFT_LEFT == keycode || Input.Keys.SHIFT_RIGHT == keycode){
                        player.isRunning = true;
                        player.speed = player.speed*2f;
                        player.setXVelocity(player.velocity.x*2f);
                        player.setYVelocity(player.velocity.y*2f);
                        player.animation.setFrameDuration(player.animation.getFrameDuration()/2f);
                    }
                }


                if(Input.Keys.SPACE == keycode){
                    if(!player.isHiding && !player.isDisguised && !player.isFound() && disguisePowerUps.hasDisguise()){
                        player.isDisguised = true;
                        disguisePowerUps.popIndicator();
                    }else if(player.isDisguised){
                        player.isDisguised = false;
                    }else if(player.isFound()){
                        resetLevel();
                    }
                }

                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if(!isComplete){
                    if((Input.Keys.RIGHT == keycode || Input.Keys.D == keycode) && !Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                        player.setXVelocity(0);
                    }else if((Input.Keys.LEFT == keycode || Input.Keys.A == keycode) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                        player.setXVelocity(0);
                    }

                    if((Input.Keys.UP == keycode || Input.Keys.W == keycode) && !Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                        player.setYVelocity(0);
                    }else if((Input.Keys.DOWN == keycode || Input.Keys.S == keycode) && !Gdx.input.isKeyPressed(Input.Keys.UP)){
                        player.setYVelocity(0);
                    }
                }
                if(Input.Keys.SHIFT_LEFT == keycode || Input.Keys.SHIFT_RIGHT == keycode){
                    player.isRunning = false;
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

        //Initialize Ending Interactions.
        if(!isComplete && landingPad.collider.contains(player.collider) && !player.isFound() && !player.isDisguised){
            alienUp.play();
            player.velocity.y = player.speed*3;
            player.velocity.x = 0f;
            isComplete = true;
        }else if(isComplete && endScreen == null && player.getY() > 720f){

            TextureRegion tr = new TextureRegion(gameProcessor.getAssetManager().get(AssetsUtil.END_SCREEN, AssetsUtil.TEXTURE));
            endScreen = new GenericActor(getCamera().position.x - getCamera().viewportWidth/2, 0f, getWidth(), getHeight(), tr, Color.GREEN);
            addActor(endScreen);

        }

        //Adjust parallax velocities.
        if(player.getX() > CAMERA_TRIGGER && (player.getX()+player.getWidth()) < getMaximumXPosition() && player.velocity.x != 0f && !player.isFound() && !player.isDisguised){
            distantParallax.velocity.x = DISTANT_PARALLAX_RATIO * player.velocity.x;
            moon.velocity.x = MOON_PARALLAX_RATIO * player.velocity.x;
            farParallax.velocity.x = FAR_PARALLAX_RATIO * player.velocity.x;
            nearParallax.velocity.x = NEAR_PARALLAX_RATIO * player.velocity.x;
        }else{
            moon.velocity.x = 0f;
            distantParallax.velocity.x = 0f;
            farParallax.velocity.x = 0f;
            nearParallax.velocity.x = 0f;
        }

        super.act(delta);

        processHidingStatus();

        //Pickup Disguise!
        pickupDisguises();

        float y = player.getY();
        player.depthPos = (int)Math.floor(MAX_Y-y/DEPTH_HEIGHT);

        if(player.getX() < MIN_X){
            player.setX(MIN_X);
        }else if(player.getX()+player.getWidth() >= MAX_X){
            player.setX(MAX_X-player.getWidth());
        }

        if(player.getY() < MIN_Y){
            player.setY(MIN_Y);
        }else if(player.getY() > MAX_Y && !isComplete){
            player.setY(MAX_Y);
        }

        reorderDepthActors();
        adjustCamera();

        //Handle Patroler Collistions!
        if(!player.isHiding && !player.isDisguised && !player.isFound()){
            boolean reset = false;
            for(Patroler p:patrolers){
                if(p.visionBox.overlaps(player.collider)){
                    reset = true;
                    break;
                }
            }

            if(reset){
                player.setIsFound(true);
                whistle.play();
                Random rand = new Random();
                for(Patroler p:patrolers){
                    p.goingToNessie = true;
                    p.velocity.x = 1000f;
                    if(p.getX() >= player.getOriginX() + player.getWidth()){
                        p.targetX = player.getOriginX() + (player.getWidth()/2 + rand.nextInt((int)p.getWidth()*2));
                        p.velocity.x *= -1f;
                    }else if(p.getX() < player.getOriginX()- player.getWidth()){
                        p.targetX = player.getOriginX() - (player.getWidth()/2 +rand.nextInt((int)p.getWidth()*2));
                    }
                }
                //resetLevel();
            }
        }

        if(player.velocity.x != 0f || player.velocity.y != 0f){
            if(walkId < 0L){
                walkId = walking.loop();
            }else if(isComplete){
                walking.pause(walkId);
            }else{
                walking.resume(walkId);
            }
        }else{
            walking.pause(walkId);
        }

    }

    private void pickupDisguises() {
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
    }

    private void processHidingStatus() {
        player.isHiding = false;
        for(Cover c:availableCover){

            if(player.collider.overlaps(c.collider)){
                if(player.getY() > c.getY()){
                    player.isHiding = true;
                }
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

        //Order front to back, then apply Z Indexes highest to lowest
        depths.sort(new Comparator<DepthActor>() {
            @Override
            public int compare(DepthActor o1, DepthActor o2) {
                return Float.compare(o1.getY(), o2.getY());
            }
        });

        int initialZ = getActors().size -1;
        grass.setZIndex(initialZ--);
        for(DepthActor d:depths){
            d.setZIndex(initialZ--);
        }

        int index = 0;
        clouds.setZIndex(index++);
        moon.setZIndex(index++);
        distantParallax.setZIndex(index++);
        farParallax.setZIndex(index++);
        nearParallax.setZIndex(index++);
        background.setZIndex(index++);
        waves.setZIndex(index++);
        landingPad.setZIndex(index);
    }

    private void adjustCamera() {

        if(player.getX() > getMaximumXPosition()){
            getCamera().position.set(getMaximumXPosition(), getCamera().position.y, getCamera().position.z);
        }else if(player.getX() > CAMERA_TRIGGER){
            getCamera().position.set(player.getX(), getCamera().position.y, getCamera().position.z);
        }else{
            getCamera().position.set(CAMERA_TRIGGER, getCamera().position.y, getCamera().position.z);
        }

        float cameraLeft = getCamera().position.x - (getCamera().viewportWidth/2);

        float offset = powerupsPos.x;
        disguisePowerUps.setPosition(cameraLeft + offset, powerupsPos.y);
    }

    private float getMaximumXPosition(){
        return MAX_X - CAMERA_TRIGGER;
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

        player.setIsFound(false);
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

        moon.setPosition(initialMoonPos.x, initialMoonPos.y);
        adjustCamera();

        bgMusic.play();

    }

    /*SHADER MAGIC*/
    private float lightSize = 1500f;
    private FrameBuffer fbo;
    private ShaderProgram finalShader;
    private ShaderProgram defaultShader;
    private Texture light;
    private Texture alienLight;
    private Texture bg;

    public static final float ambientIntensity = .7f;
    public static final Vector3 ambientColor = new Vector3(0.3f, 0.3f, 0.7f);
    private static final String vertexShader = "attribute vec4 a_position;\n" +
            "attribute vec4 a_color;\n" +
            "attribute vec2 a_texCoord0;\n" +
            "uniform mat4 u_projTrans;\n" +
            "varying vec4 vColor;\n" +
            "varying vec2 vTexCoord;\n" +
            "\n" +
            "void main() {\n" +
            "\tvColor = a_color;\n" +
            "\tvTexCoord = a_texCoord0;\n" +
            "\tgl_Position = u_projTrans * a_position;\t\t\n" +
            "}";

    private static final String defaultPixelShader = "#ifdef GL_ES\n" +
            "#define LOWP lowp\n" +
            "precision mediump float;\n" +
            "#else\n" +
            "#define LOWP\n" +
            "#endif\n" +
            "\n" +
            "varying LOWP vec4 vColor;\n" +
            "varying vec2 vTexCoord;\n" +
            "\n" +
            "//our texture samplers\n" +
            "uniform sampler2D u_texture; //diffuse map\n" +
            "\n" +
            "void main() {\n" +
            "\tvec4 DiffuseColor = texture2D(u_texture, vTexCoord);\n" +
            "\tgl_FragColor = vColor * DiffuseColor;\n" +
            "}";

    private static final String finalPixelShader = "#ifdef GL_ES\n" +
            "#define LOWP lowp\n" +
            "precision mediump float;\n" +
            "#else\n" +
            "#define LOWP\n" +
            "#endif\n" +
            "\n" +
            "varying LOWP vec4 vColor;\n" +
            "varying vec2 vTexCoord;\n" +
            "\n" +
            "//texture samplers\n" +
            "uniform sampler2D u_texture; //diffuse map\n" +
            "uniform sampler2D u_lightmap;   //light map\n" +
            "\n" +
            "//additional parameters for the shader\n" +
            "uniform vec2 resolution; //resolution of screen\n" +
            "uniform LOWP vec4 ambientColor; //ambient RGB, alpha channel is intensity \n" +
            "\n" +
            "void main() {\n" +
            "\tvec4 diffuseColor = texture2D(u_texture, vTexCoord);\n" +
            "\tvec2 lighCoord = (gl_FragCoord.xy / resolution.xy);\n" +
            "\tvec4 light = texture2D(u_lightmap, lighCoord);\n" +
            "\t\n" +
            "\tvec3 ambient = ambientColor.rgb * ambientColor.a;\n" +
            "\tvec3 intensity = ambient + light.rgb;\n" +
            " \tvec3 finalColor = diffuseColor.rgb * intensity;\n" +
            "\t\n" +
            "\tgl_FragColor = vColor * vec4(finalColor, diffuseColor.a);\n" +
            "}\n";
    private void sharderStuff(){
        ShaderProgram.pedantic = false;

        defaultShader = new ShaderProgram(vertexShader, defaultPixelShader);

        finalShader = new ShaderProgram(vertexShader, finalPixelShader);
        finalShader.begin();
        finalShader.setUniformi("u_lightmap", 1);
        finalShader.setUniformf("ambientColor", ambientColor.x, ambientColor.y,
                ambientColor.z, ambientIntensity);
        finalShader.end();

        light = gameProcessor.getAssetManager().get(AssetsUtil.LIGHT, AssetsUtil.TEXTURE);
        alienLight = gameProcessor.getAssetManager().get(AssetsUtil.ALIEN_LIGHT, AssetsUtil.TEXTURE);
        //bg = gameProcessor.getAssetManager().get(AssetsUtil.GAME_BG, AssetsUtil.TEXTURE);
    }

    public void resize(int width, int height) {
        fbo = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);

        finalShader.begin();
        finalShader.setUniformf("resolution", width, height);
        finalShader.end();
    }


    @Override
    public void draw() {
        fbo.begin();
        Batch batch = getBatch();
        batch.setShader(defaultShader);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(light, moon.getOriginX() - lightSize*0.5f + 0.5f,
                moon.getOriginY() + 0.5f - lightSize*0.5f,
                lightSize, lightSize);
        for(Patroler p:patrolers){
            batch.draw(light, p.visionBox.x, p.visionBox.y, p.visionBox.width, p.visionBox.height);
        }

        if(isComplete && player.getY() < 720f){
            batch.draw(alienLight, player.getX()-10f, 0f, player.getWidth()*1.25f, 720f);
        }
        batch.end();
        fbo.end();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setShader(finalShader);
        batch.begin();
        fbo.getColorBufferTexture().bind(1); //this is important! bind the FBO to the 2nd texture unit
        light.bind(0); //we force the binding of a texture on first texture unit to avoid artefacts
        //this is because our default and ambiant shader dont use multi texturing...
        //youc can basically bind anything, it doesnt matter
        //tilemap.render(batch, dt);
        //batch.draw(bg, 0f, 0f);
        batch.end();
        super.draw();
    }
}
