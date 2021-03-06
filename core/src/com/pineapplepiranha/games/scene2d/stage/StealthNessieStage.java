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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pineapplepiranha.games.delegate.IGameProcessor;
import com.pineapplepiranha.games.scene2d.GenericActor;
import com.pineapplepiranha.games.scene2d.GenericGroup;
import com.pineapplepiranha.games.scene2d.actor.*;
import com.pineapplepiranha.games.scene2d.decorator.ActorDecorator;
import com.pineapplepiranha.games.scene2d.decorator.OscillatingDectorator;
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

    /**
     * Top of Screen
     */
    public static final float TOS = 720f;
    public static final float WALKING_CYCLE_RATE = 1f / 5f;
    public static final float RUNNING_CYCLE_RATE = 1f / 6f;
    public static final float DISGUISE_CYCLE_RATE = 1f / 4f;
    private static float LEVEL_WIDTH = 12000f;
    private static float LEVEL_HEIGHT = TOS;

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

    //Spaceship
    private static final float SPACESHIP_CYCLE_RATE = 1f/9f;
    public static final float SPACESHIP_WIDTH = 356f;
    public static final float SPACESHIP_HEIGHT = 371f;
    public static final float SPACESHIP_GETAWAY_DURATION = 4f;
    public static final float ABDUCTION_SPEED = 200f;

    private static float ICON_SIZE = 75f;

    private static Vector2 playerPos, playerSize, distantParalaxPos,farParallaxPos, nearParallaxPos,
                           initialMoonPos, powerupsPos, pickupPointPos, phoneboothPos,
                           cornBackPos, cornMidPos, cornFrontPos, bridgeFrontPos;

    private boolean isShowingInstructions = true;

    static {
        playerPos = new Vector2(800f, 120f);
        playerSize = new Vector2(257f, 257f);
        distantParalaxPos = new Vector2(700f, -50f);
        farParallaxPos = new Vector2(600f, 0f);
        nearParallaxPos = new Vector2(475f, 0f);
        initialMoonPos = new Vector2(ViewportUtil.VP_WIDTH/2, (ViewportUtil.VP_HEIGHT/4)*3);
        powerupsPos = new Vector2(ICON_SIZE/2f, ViewportUtil.VP_HEIGHT - (ICON_SIZE*1.25f));
        pickupPointPos = new Vector2(10840  , 0f);
        phoneboothPos = new Vector2(5750f, MAX_Y-75f);
        cornBackPos = new Vector2(10500f, 0f);
        cornMidPos = new Vector2(10500f, 0f);
        cornFrontPos = new Vector2(10500f, 0f);
        bridgeFrontPos = new Vector2(5400f, -30f);

    }

    //Actor Groups
    public LevelBackground clouds;
    public LevelBackground background;
    public Waves waves;
    public Array<Cover> availableCover;
    public Array<Patrol> patrols;
    public Array<Disguise> disguises;
    public Array<AnimatedActor> stars;
    public Player player;
    public DisguisePowerUps disguisePowerUps;
    public Array<BlockingActor> blockingActors;

    private Spaceship spaceship;

    private boolean isComplete = false;

    //Ambiance
    private Music bgMusic;
    private int depthInitialIndex;
    private Sound alienUp;
    private Sound whistle;
    private Sound walking;
    private long walkId = -1L;


    public GenericActor moon;
    public LandingPad landingPad;
    public GenericActor endScreen;
    public GenericActor grass;
    public LevelBackground distantParallax;
    public LevelBackground farParallax;
    public LevelBackground nearParallax;
    public GenericGroup instructions;
    public GenericGroup lampPost;
    public AnimatedActor raincoat;
    public GenericActor hanger;
    public Disguise superManCostume;
    public Cover phonebooth;
    public GenericActor cornBack;
    public GenericActor cornMid;
    public GenericActor cornFront;
    public GenericActor bridgeFront;
    private Array<Vector2> lampPostPositions;

    public StealthNessieStage(IGameProcessor gameProcessor){
        super(gameProcessor);
        AssetManager am = gameProcessor.getAssetManager();
        TextureAtlas atlas = am.get(AssetsUtil.ANIMATION_ATLAS, AssetsUtil.TEXTURE_ATLAS);
        availableCover = new Array<Cover>();
        patrols = new Array<Patrol>();
        disguises = new Array<Disguise>();
        stars = new Array<AnimatedActor>();
        blockingActors = new Array<BlockingActor>();

        initializeInstructions(am, atlas);

        alienUp = am.get(AssetsUtil.ALIEN_UP, AssetsUtil.SOUND);
        whistle = am.get(AssetsUtil.WHISTLE, AssetsUtil.SOUND);
        walking = am.get(AssetsUtil.WALKING, AssetsUtil.SOUND);
        bgMusic = am.get(AssetsUtil.GAME_MUSIC, AssetsUtil.MUSIC);
        bgMusic.setVolume(0.5f);

        bgMusic.setLooping(true);
        //bgMusic.play();

        clouds = new LevelBackground(0f, 0f, LEVEL_WIDTH, LEVEL_HEIGHT, atlas.findRegions("clouds/Clouds"));
        addActor(clouds);
        clouds.setZIndex(depthInitialIndex++);

        TextureRegion moonTexture = new TextureRegion(am.get(AssetsUtil.MOON, AssetsUtil.TEXTURE));
        moon = new GenericActor(initialMoonPos.x, initialMoonPos.y, 150f, 150f, moonTexture, Color.YELLOW);
        addActor(moon);
        moon.setZIndex(depthInitialIndex++);

        distantParallax = new LevelBackground(distantParalaxPos.x, distantParalaxPos.y, 9201f, TOS, atlas.findRegions("bg/Mountains"));
        addActor(distantParallax);
        distantParallax.setZIndex(depthInitialIndex++);

        farParallax = new LevelBackground(farParallaxPos.x, farParallaxPos.y, 9201f, TOS, atlas.findRegions("bg/BTree"));
        addActor(farParallax);
        farParallax.setZIndex(depthInitialIndex++);

        nearParallax = new LevelBackground(nearParallaxPos.x, nearParallaxPos.y, 9891f, TOS, atlas.findRegions("bg/FTree"));
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
        landingPad = new LandingPad(pickupPointPos.x, pickupPointPos.y, 900f, 187f, glowingAnimation, 0f);
        addActor(landingPad);
        landingPad.setZIndex(depthInitialIndex++);

        initializeShoreline(am, atlas);


        addActor(lampPost);

        Animation walking = new Animation(WALKING_CYCLE_RATE, atlas.findRegions("nessie/Walking"));
        Animation sadNessie = new Animation(1f/7f, atlas.findRegions("nessie/Cry"));
        Animation runNessie = new Animation(RUNNING_CYCLE_RATE, atlas.findRegions("nessie/Run"));
        Animation fisherNessie = new Animation(DISGUISE_CYCLE_RATE, atlas.findRegions("nessie/Fisherman"));
        Animation superNessie = new Animation(DISGUISE_CYCLE_RATE, atlas.findRegions("nessie/Superman"));

        TextureRegion defaultCoverTr = atlas.findRegion("nessie/Cammo");
        defaultCoverTr.flip(true, false);
        TextureRegion bushCoverTr = atlas.findRegion("nessie/Branch");
        bushCoverTr.flip(true, false);
        TextureRegion shortCoverTr = atlas.findRegion("nessie/Cover");
        shortCoverTr.flip(true, false);

        TextureRegion normalTr = atlas.findRegion("nessie/Stills");
        normalTr.flip(true, false);

        TextureRegion disguisedTr = atlas.findRegion("nessie/Disguised");
        disguisedTr.flip(true, false);

        player = new Player(playerPos.x, playerPos.y, playerSize.x, playerSize.y, walking);
        player.sadAnimation = sadNessie;
        player.runningAnimation = runNessie;

        player.setHidingTexture(defaultCoverTr);
        player.setNormalTexture(normalTr);

        player.addCoverPose(CoverType.DFLT, defaultCoverTr);
        player.addCoverPose(CoverType.BRANCH, bushCoverTr);
        player.addCoverPose(CoverType.SHORT, shortCoverTr);


        player.addDisguiseTexture(DisguiseType.NOSE, disguisedTr);
        player.addDisguiseAnimation(DisguiseType.FISHERMAN, fisherNessie);
        player.addDisguiseAnimation(DisguiseType.SUPERMAN, superNessie);
        addActor(player);


        initializePatrols(am);
        initializeCover(am);
        initializeDisguises(am);
        initializeStars(am);
        initializeBlockingComponents();

        TextureRegion maskIcon = new TextureRegion(am.get(AssetsUtil.MASK_ICON, AssetsUtil.TEXTURE));
        disguisePowerUps = new DisguisePowerUps(powerupsPos.x,
                                                 powerupsPos.y,
                                                 ICON_SIZE * 4,
                                                 ICON_SIZE, maskIcon);
        addActor(disguisePowerUps);
        disguisePowerUps.addIndicator(DisguiseType.NOSE);

        TextureRegion tr = new TextureRegion(am.get(AssetsUtil.GRASS, AssetsUtil.TEXTURE));
        grass = new GenericActor(0f, 0f, tr.getRegionWidth(), tr.getRegionHeight(), tr, Color.WHITE);
        addActor(grass);

        TextureRegion bridgeRegion = new TextureRegion(am.get(AssetsUtil.BRIDGE_FRONT, AssetsUtil.TEXTURE));
        bridgeFront = new GenericActor(bridgeFrontPos.x, bridgeFrontPos.y, bridgeRegion.getRegionWidth(), bridgeRegion.getRegionHeight(), bridgeRegion, Color.GRAY);
        addActor(bridgeFront);

        TextureRegion cornBackRegion = new TextureRegion(am.get(AssetsUtil.CORN_BACK, AssetsUtil.TEXTURE));
        cornBack = new GenericActor(cornBackPos.x, cornBackPos.y, cornBackRegion.getRegionWidth(), cornBackRegion.getRegionHeight(), cornBackRegion, Color.YELLOW);
        addActor(cornBack);

        TextureRegion cornMidRegion = new TextureRegion(am.get(AssetsUtil.CORN_MID, AssetsUtil.TEXTURE));
        cornMid = new GenericActor(cornMidPos.x, cornMidPos.y, cornBackRegion.getRegionWidth(), cornBackRegion.getRegionHeight(), cornMidRegion, Color.YELLOW);
        addActor(cornMid);

        TextureRegion cornFrontRegion = new TextureRegion(am.get(AssetsUtil.CORN_FRONT, AssetsUtil.TEXTURE));
        cornFront = new GenericActor(cornFrontPos.x, cornFrontPos.y, cornBackRegion.getRegionWidth(), cornBackRegion.getRegionHeight(), cornFrontRegion, Color.YELLOW);
        addActor(cornFront);

        initializeInputListeners();
        sharderStuff();
    }

    private void initializeShoreline(AssetManager am, TextureAtlas atlas) {
        TextureRegion lampPostRegion = new TextureRegion(am.get(AssetsUtil.LAMP_POST, AssetsUtil.TEXTURE));
        lampPost = new GenericGroup(448f, 72f, lampPostRegion.getRegionWidth(), lampPostRegion.getRegionHeight(), lampPostRegion, Color.GREEN);
        TextureRegion lanternRegion = new TextureRegion(am.get(AssetsUtil.LANTERN, AssetsUtil.TEXTURE));
        GenericActor lantern = new GenericActor(356f, 210f, lanternRegion.getRegionWidth(), lanternRegion.getRegionHeight(), lanternRegion, Color.CYAN);
        lantern.addDecorator(new OscillatingDectorator(-5f, 5f, 5f));
        lantern.setOrigin(lantern.getWidth()/2, lantern.getHeight());
        lampPost.addActor(lantern);
        //lampPost.addActor(raincoat);
    }

    private void initializeInstructions(AssetManager am, TextureAtlas atlas) {
        TextureRegion instructionRegion = new TextureRegion(am.get(AssetsUtil.INSTRUCTIONS, AssetsUtil.TEXTURE));
//        TextureRegion instructionRegion = new TextureRegion(am.get(AssetsUtil.INSTRUCTIONS_CTRL, AssetsUtil.TEXTURE));
        instructions = new GenericGroup(0f, 0f, ViewportUtil.VP_WIDTH, LEVEL_HEIGHT, instructionRegion, Color.BLUE);
        addActor(instructions);

        Animation walkingAni = new Animation(WALKING_CYCLE_RATE, atlas.findRegions("nessie/Walking"));
        AnimatedActor walkingAnimation = new AnimatedActor(20f, 720f-263f, 257f, 257f, walkingAni, 0f);
        instructions.addActor(walkingAnimation);
        Animation runningAni = new Animation(RUNNING_CYCLE_RATE, atlas.findRegions("nessie/Run"));
        AnimatedActor runningAnimation = new AnimatedActor(680f, 720f-263f, 257f, 257f, runningAni, 0f);
        instructions.addActor(runningAnimation);


    }

    private void initializeCover(AssetManager am){


        Array<Vector2> bushPositions = new Array<Vector2>();
        bushPositions.add(new Vector2(940, 720-540));
        bushPositions.add(new Vector2(2040, 720-495));
        bushPositions.add(new Vector2(2080, 720-700));
        bushPositions.add(new Vector2(2575, 720-512));
        bushPositions.add(new Vector2(3356, 720-638));
        bushPositions.add(new Vector2(3682, 720-541));
        bushPositions.add(new Vector2(3816, 720-585));
        bushPositions.add(new Vector2(4378, 720-500));
        bushPositions.add(new Vector2(4570, 720-510));


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

        Array<Vector2> barrelPositions = new Array<Vector2>();
        barrelPositions.add(new Vector2(3139, 720 - 516));
        barrelPositions.add(new Vector2(3244, 720 - 717));
        barrelPositions.add(new Vector2(5356, 720 - 638));
        barrelPositions.add(new Vector2(8282, 720 - 640));
        barrelPositions.add(new Vector2(7816, 720 - 585));
        barrelPositions.add(new Vector2(6570, 720 - 520));

        barrelPositions.add(new Vector2(8575, 720 - 600));
        barrelPositions.add(new Vector2(9356, 720 - 535));
        barrelPositions.add(new Vector2(9378, 720 - 710));


        lampPostPositions = new Array<Vector2>();
        lampPostPositions.add(new Vector2(phoneboothPos.x -800f, MAX_Y-30f));//1350f, MAX_Y-30f));
        lampPostPositions.add(new Vector2(phoneboothPos.x + 2200f, MAX_Y-30f));

        Array<Vector2> cratePositions = new Array<Vector2>();
        cratePositions.add(new Vector2(6378, 720-620));
        cratePositions.add(new Vector2(7378, 720-700));
        cratePositions.add(new Vector2(7570, 720-590));
        cratePositions.add(new Vector2(8682, 720-541));
        cratePositions.add(new Vector2(9816, 720-585));

        Array<Vector2> darkCratePositions = new Array<Vector2>();
        darkCratePositions.add(new Vector2(3744, 720 - 717));
        darkCratePositions.add(new Vector2(9575, 720-512));
        darkCratePositions.add(new Vector2(10356, 720-535));
        darkCratePositions.add(new Vector2(10378, 720-710));

        TextureRegion bushRegion = new TextureRegion(am.get(AssetsUtil.BUSH, AssetsUtil.TEXTURE));
        float width = 129f;
        float height = 121f;
        for(Vector2 bv:bushPositions){
            Cover c = new Cover(bv.x, bv.y, width, height, bushRegion, 0, CoverType.BRANCH);
            availableCover.add(c);
            addActor(c);
        }

        TextureRegion ptRegion = new TextureRegion(am.get(AssetsUtil.TREE_2, AssetsUtil.TEXTURE));
        width = 161f;
        height = 279f;
        for(Vector2 ptv:pineTreePositions){
            Cover c = new Cover(ptv.x, ptv.y, width, height, ptRegion, 0, CoverType.DFLT);
            availableCover.add(c);
            addActor(c);
        }

        TextureRegion tRegion = new TextureRegion(am.get(AssetsUtil.TREE_1, AssetsUtil.TEXTURE));
        width = 150f;
        height = 254f;
        for(Vector2 tv:treePositions){
            Cover c = new Cover(tv.x, tv.y, width, height, tRegion, 0, CoverType.DFLT);
            availableCover.add(c);
            addActor(c);
        }

        TextureRegion bRegion = new TextureRegion(am.get(AssetsUtil.BARREL, AssetsUtil.TEXTURE));
        width = 85f;
        height = 110f;
        for(Vector2 bv:barrelPositions){
            Cover c = new Cover(bv.x, bv.y, width, height, bRegion, 0, CoverType.BRANCH);
            availableCover.add(c);
            addActor(c);
        }

        TextureRegion lampRegion = new TextureRegion(am.get(AssetsUtil.METAL_LAMP_POST, AssetsUtil.TEXTURE));
        width = 47f;
        height = 292f;
        for(Vector2 lv:lampPostPositions){
            Cover c = new Cover(lv.x, lv.y, width, height, lampRegion, 0, CoverType.BRANCH);
            availableCover.add(c);
            addActor(c);
        }

        TextureRegion crateRegion = new TextureRegion(am.get(AssetsUtil.CRATE_1, AssetsUtil.TEXTURE));
        width = 146f;
        height = 118f;
        for(Vector2 cv:cratePositions){
            Cover c = new Cover(cv.x, cv.y, width, height, crateRegion, 0, CoverType.SHORT);
            availableCover.add(c);
            addActor(c);
        }

        TextureRegion darkCrateRegion = new TextureRegion(am.get(AssetsUtil.CRATE_2, AssetsUtil.TEXTURE));
        for(Vector2 c2v:darkCratePositions){
            Cover c = new Cover(c2v.x, c2v.y, width, height, darkCrateRegion, 0, CoverType.SHORT);
            availableCover.add(c);
            addActor(c);
        }

        TextureRegion pbRegion = new TextureRegion(am.get(AssetsUtil.PHONE_BOOTH, AssetsUtil.TEXTURE));
        phonebooth = new Cover(phoneboothPos.x, phoneboothPos.y, pbRegion.getRegionWidth(), pbRegion.getRegionHeight(), pbRegion, 0, CoverType.BRANCH);
        availableCover.add(phonebooth);
        addActor(phonebooth);
    }

    private void initializePatrols(AssetManager am){

        TextureAtlas atlas = am.get(AssetsUtil.ANIMATION_ATLAS, AssetsUtil.TEXTURE_ATLAS);

        Array<Vector3> patrolVals = new Array<Vector3>();
        patrolVals.add(new Vector3(1926, 720-534, 600));
        patrolVals.add(new Vector3(2598, 720-596, 500));
        patrolVals.add(new Vector3(4050, 720-562, 800));
        patrolVals.add(new Vector3(5012, 720-530, 750));
        patrolVals.add(new Vector3(5620, 720-660, 1500));
        patrolVals.add(new Vector3(6482, 720-578, 500));
        patrolVals.add(new Vector3(7012, 720-530, 750));
        patrolVals.add(new Vector3(7620, 720-660, 1500));
        patrolVals.add(new Vector3(8482, 720-578, 300));
        patrolVals.add(new Vector3(9500, 720-715, 800));
        patrolVals.add(new Vector3(9000, 720-560, 200));
        patrolVals.add(new Vector3(10500, 720-532, 500));
        patrolVals.add(new Vector3(10500, 720-602, 500));
        patrolVals.add(new Vector3(10500, 720-702, 500));
        Texture flTexture = am.get(AssetsUtil.FLASHLIGHT, AssetsUtil.TEXTURE);

        float width = 105f;
        float height = 150f;
        for(Vector3 pv:patrolVals){
            String animationName = pv.z > 500 ? "patrol/Patrol" : "patrol/PatrolA";
            Animation animation = new Animation(1f/3f, atlas.findRegions(animationName));
            TextureRegion flashlightTexture = new TextureRegion(flTexture);
            Patrol p = new Patrol(pv.x, pv.y, width, height, animation, 0, pv.z);
            p.setFlashlightTextureRegion(flashlightTexture);
            patrols.add(p);
            addActor(p);
        }
    }

    private void initializeDisguises(AssetManager am){
        Texture disguiseTexture = am.get(AssetsUtil.MASK_ICON, AssetsUtil.TEXTURE);
        superManCostume = new Disguise(phonebooth.getX()+phonebooth.getWidth()/2, phonebooth.getY() + ICON_SIZE/2, ICON_SIZE, ICON_SIZE/2, disguiseTexture, DisguiseType.SUPERMAN);
        addActor(superManCostume);

        TextureAtlas atlas = am.get(AssetsUtil.ANIMATION_ATLAS, AssetsUtil.TEXTURE_ATLAS);
        Animation coat = new Animation(1f/2f, atlas.findRegions("disguises/raincoat"));
        raincoat = new AnimatedActor(648f, 262f, 111f, 138f, coat, 0f);
        addActor(raincoat);
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
    }

    private void initializeInputListeners(){
        this.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(!isComplete && !isShowingInstructions){
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
                    if(instructions.isVisible()){
                        instructions.setVisible(false);
                        isShowingInstructions = false;
                        bgMusic.play();

                    }else if(isComplete && !player.isVisible()){
                        resetLevel();
                    }else if(!player.isHiding && !player.isDisguised && !player.isFound() && disguisePowerUps.hasDisguise()){
                        player.isDisguised = true;
                        DisguiseIndicator d = disguisePowerUps.popIndicator();
                        player.setDisguiseType(d.disguiseType);

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
                if(!isComplete && !isShowingInstructions){
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
                    if(Input.Keys.SHIFT_LEFT == keycode || Input.Keys.SHIFT_RIGHT == keycode){
                        player.isRunning = false;
                        player.speed = player.speed/2f;
                        player.setXVelocity(player.velocity.x/2f);
                        player.setYVelocity(player.velocity.y/2f);
                        player.animation.setFrameDuration(player.animation.getFrameDuration()*2f);
                    }
                }


                return true;
            }
        });
    }

    @Override
    public void act(float delta) {

        resolveEndingInteractions();
        adjustParallaxVelocities();


        super.act(delta);
        instructions.setX(getCamera().position.x - (getCamera().viewportWidth/2));
        processHidingStatus();

        //Pickup Disguise!
        pickupDisguises();


        //player.depthPos = (int)Math.floor(MAX_Y-player.getY()/DEPTH_HEIGHT);

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

        //This isn't ready yet. Not even close for prime-time. Need real collision detection.
        Rectangle playerRect = new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight());
        if(player.getY() >= phonebooth.getY() && playerRect.overlaps(phonebooth.collider)){
            float playerVel = player.velocity.x;
            float phoneboothRight = (phonebooth.getX() + phonebooth.getWidth());
            if(playerVel > 0){
                //Coming in from the right
                if((player.getX() + player.getWidth()) > phoneboothRight){
                    player.setX(phoneboothRight-player.getWidth());
                    player.setXVelocity(0f);
                }
            }else if(playerVel < 0){
                //Coming in from the left
                if((player.getX() < phoneboothRight) && (player.getX() + player.getWidth()) > phoneboothRight) {
                    player.setXVelocity(0f);
                }
            }
        }

        reorderDepthActors();
        adjustCamera();

        //Handle Patrol Collisions!
        if(!player.isHiding && !player.isDisguised && !player.isFound()){
            boolean playerIsCaught = false;
            for(Patrol p: patrols){
                if(p.visionBox.overlaps(player.collider)){
                    playerIsCaught = true;
                    break;
                }
            }

            if(playerIsCaught){
                player.setIsFound(true);
                whistle.play();
                Random rand = new Random();
                for(Patrol p: patrols){
                    p.velocity.x = 1000f;
                    float targetX = p.getX();
                    if(p.getX() >= (player.getX() + (player.getWidth()*1.5f))){
                        targetX = (player.getX()+(player.getWidth()/2f)) + (player.getWidth()/2 + rand.nextInt((int)p.getWidth()*2));
                    }else if(p.getX() < (player.getX() + (player.getWidth()/2f))- player.getWidth()){
                        targetX = (player.getX() + (player.getWidth()/2f))- (player.getWidth()/2 +rand.nextInt((int)p.getWidth()*2));
                    }

                    p.sendToNessie(targetX);
                }
            }
        }

        if(player.velocity.x != 0f || player.velocity.y != 0f){
            float volume = player.isRunning ? 1f : 0.2f;
            if(walkId < 0L){
                walkId = walking.loop(volume);
            }else if(isComplete){
                walking.pause(walkId);
            }else{
                walking.resume(walkId);
                walking.setVolume(walkId, volume);
            }
        }else{
            walking.pause(walkId);
        }

    }

    private void resolveEndingInteractions() {
        //Initialize Ending Interactions.
        boolean isOnLandingPad = landingPad.collider.overlaps(player.collider) &&
                                 player.collider.x >= landingPad.collider.x &&
                                 (player.collider.x + player.collider.width) <= (landingPad.collider.x + landingPad.collider.width);
        if(!isComplete && isOnLandingPad && !player.isFound() && !player.isDisguised){
            alienUp.play();
            player.velocity.y = ABDUCTION_SPEED;
            player.velocity.x = 0f;
            isComplete = true;
            TextureAtlas atlas = gameProcessor.getAssetManager().get(AssetsUtil.ANIMATION_ATLAS, AssetsUtil.TEXTURE_ATLAS);
            Animation spaceshipAni = new Animation(SPACESHIP_CYCLE_RATE, atlas.findRegions("spaceship/SpaceShip"));
            spaceship = new Spaceship(player.getX() - ((SPACESHIP_WIDTH - player.getWidth())/2), TOS, SPACESHIP_WIDTH, SPACESHIP_HEIGHT, spaceshipAni);
            addActor(spaceship);
            MoveToAction moveSpaceshipAction = new MoveToAction();
            moveSpaceshipAction.setPosition(spaceship.getX(), TOS-SPACESHIP_HEIGHT);
            moveSpaceshipAction.setDuration(0.75f);
            spaceship.addAction(moveSpaceshipAction);

        }else if(isComplete && endScreen == null && player.getY() > spaceship.getY()){

            player.setVisible(false);
            MoveToAction moveSpaceshipAction = new MoveToAction();
            moveSpaceshipAction.setPosition(spaceship.getX()-1000f, TOS + spaceship.getHeight());
            moveSpaceshipAction.setDuration(SPACESHIP_GETAWAY_DURATION);
            spaceship.addAction(moveSpaceshipAction);

            spaceship.setSpeechBubble(gameProcessor.getAssetManager().get(AssetsUtil.SPEECH_BUBBLE, AssetsUtil.TEXTURE), SPACESHIP_WIDTH/2, -(SPACESHIP_HEIGHT/2));
        }
    }

    private void adjustParallaxVelocities() {
        //Adjust parallax velocities.
        boolean needsParallaxMovement = player.getX() > CAMERA_TRIGGER &&
                                        (player.getX()+player.getWidth()) < getMaximumXPosition() &&
                                        player.velocity.x != 0f &&
                                        !player.isFound() &&
                                        !player.isDisguised;
        if(needsParallaxMovement){
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
    }

    private void pickupDisguises() {
        if(hanger == null && player.collider.overlaps(raincoat.collider)){
            lampPost.removeActor(raincoat);
            raincoat.remove();
            disguisePowerUps.addIndicator(DisguiseType.FISHERMAN);
            TextureRegion region = new TextureRegion(gameProcessor.getAssetManager().get(AssetsUtil.HANGER, AssetsUtil.TEXTURE));
            hanger = new GenericActor(raincoat.getX()+10f, raincoat.getY()+(138f-47f), 74f, 47f, region, Color.BLACK);
            hanger.setOrigin(hanger.getWidth()/2, hanger.getHeight());

            hanger.addDecorator(new ActorDecorator() {
                float totalDuration = 1f;
                float elapsedTime = 0f;
                RotateByAction rotateBackToZero;

                @Override
                public void applyAdjustment(Actor a, float delta) {
                    if(elapsedTime < totalDuration){
                        a.rotateBy(-1080f*delta);
                        elapsedTime += delta;
                    }else if(a.getRotation() != 0f && rotateBackToZero == null){
                        rotateBackToZero = new RotateByAction();
                        rotateBackToZero.setDuration(0.5f);

                        float newRotation = a.getRotation()%360;
                        rotateBackToZero.setAmount(newRotation);
                        a.addAction(rotateBackToZero);
                    }
                }
            });

            addActor(hanger);
        }

        if(superManCostume != null && player.collider.overlaps(superManCostume.collider) && player.getY() >= phonebooth.getY()){
            disguisePowerUps.addIndicator(superManCostume.disguiseType);
            superManCostume.remove();
            superManCostume = null;
        }


        Disguise toRemove = null;
        for(Disguise d:disguises){
            if(player.collider.overlaps(d.collider)){
                toRemove = d;
                disguisePowerUps.addIndicator(d.disguiseType);//, new TextureRegion(gameProcessor.getAssetManager().get(AssetsUtil.MASK_ICON, AssetsUtil.TEXTURE)));
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
                if(player.collider.y > c.getY() && !player.isRunning){
                    player.isHiding = true;
                    player.setCurrentCover(c.coverType);
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
        instructions.setZIndex(initialZ--);
        if(spaceship != null){
            spaceship.setZIndex(initialZ--);
        }
        grass.setZIndex(initialZ--);
        bridgeFront.setZIndex(initialZ--);
        cornFront.setZIndex(initialZ--);
        waves.setZIndex(initialZ--);
        lampPost.setZIndex(initialZ--);
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
        cornBack.setZIndex(index++);
        cornMid.setZIndex(index++);
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
        if(spaceship != null){
            spaceship.remove();
        }

        for(Patrol p: patrols){
            p.remove();
        }
        patrols.clear();

        for(Disguise d:disguises){
            d.remove();
        }
        disguises.clear();
        if(superManCostume != null){
            superManCostume.remove();
        }

        //Clean up shoreline
        if(hanger != null){
            hanger.remove();
        }
        hanger = null;
        raincoat.remove();
        raincoat = null;


        for(Cover c:availableCover){
            c.remove();
        }
        availableCover.clear();

        player.setVisible(true);
        player.setIsFound(false);
        player.setPosition(playerPos.x, playerPos.y);
        player.setXVelocity(0f);
        player.setYVelocity(0f);

        while(disguisePowerUps.hasDisguise()){
            disguisePowerUps.popIndicator();
        }
        disguisePowerUps.addIndicator(DisguiseType.NOSE);//, new TextureRegion(gameProcessor.getAssetManager().get(AssetsUtil.MASK_ICON, AssetsUtil.TEXTURE)));

        if(lampPostPositions != null){
            lampPostPositions.clear();
        }

        initializeCover(gameProcessor.getAssetManager());
        initializePatrols(gameProcessor.getAssetManager());
        initializeDisguises(gameProcessor.getAssetManager());

        moon.setPosition(initialMoonPos.x, initialMoonPos.y);
        adjustCamera();
        instructions.setVisible(true);
        isShowingInstructions = true;
    }

    /*SHADER MAGIC*/
    private float lightSize = 1500f;
    private FrameBuffer fbo;
    private ShaderProgram finalShader;
    private ShaderProgram defaultShader;
    private Texture light;
    private TextureRegion lightRegion;
    private Texture alienLight;

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
        lightRegion = new TextureRegion(light);
        alienLight = gameProcessor.getAssetManager().get(AssetsUtil.ALIEN_LIGHT, AssetsUtil.TEXTURE);
    }

    public void resize(int width, int height) {
        fbo = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);

        getViewport().update(width, height);
        finalShader.begin();
        finalShader.setUniformf("resolution", width, height);
        finalShader.end();
    }


    @Override
    public void draw() {

        Viewport vp = getViewport();
        int screenW = vp.getScreenWidth();
        int screenH = vp.getScreenHeight();
        int leftCrop = vp.getLeftGutterWidth();
        int bottomCrop = vp.getBottomGutterHeight();
        int xPos = leftCrop;
        int yPos = bottomCrop;

        fbo.begin();
        Batch batch = getBatch();
        batch.setShader(defaultShader);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(xPos, yPos, screenW, screenH);

        batch.begin();

        if(!isShowingInstructions){
            float moonCenterX = (moon.getX() + (moon.getWidth()/2f));
            float moonCenterY = (moon.getY() + (moon.getHeight()/2f));
            batch.draw(light, moonCenterX - lightSize*0.5f + 0.5f,
                    moonCenterY + 0.5f - lightSize*0.5f,
                    lightSize, lightSize);
            for(Patrol p: patrols){
                float originX = p.visionBox.width/2;
                float originY = p.visionBox.height/2;
                batch.draw(lightRegion, p.visionBox.x, p.visionBox.y,
                           originX, originY, p.visionBox.width, p.visionBox.height,
                           1f+p.getCurrentAdjustment(), 1f+p.getCurrentAdjustment(), 0f);
            }

            if(lampPostPositions != null){
                for(Vector2 v:lampPostPositions){
                    batch.draw(lightRegion, v.x-100f, v.y, 247f, 292f);
                }
            }
            if(isComplete && player.isVisible() &&  spaceship != null && player.getY() < spaceship.getY()){
                batch.draw(alienLight, player.getX()-10f, 0f, player.getWidth() + 20f, spaceship.getY() + 30f);
            }
        }

        batch.end();
        fbo.end();

        Gdx.gl.glViewport(xPos, yPos, screenW, screenH);

        if(!isShowingInstructions){
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.setShader(finalShader);

            batch.begin();
            fbo.getColorBufferTexture().bind(1); //this is important! bind the FBO to the 2nd texture unit
            light.bind(0); //we force the binding of a texture on first texture unit to avoid artefacts
            //this is because our default and ambiant shader dont use multi texturing...
            //youc can basically bind anything, it doesnt matter
            //batch.draw(bg, 0f, 0f);
            batch.end();
        }
        super.draw();
    }
}
