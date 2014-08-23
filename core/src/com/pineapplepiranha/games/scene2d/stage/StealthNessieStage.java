package com.pineapplepiranha.games.scene2d.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.pineapplepiranha.games.delegate.IGameProcessor;
import com.pineapplepiranha.games.scene2d.GenericActor;
import com.pineapplepiranha.games.scene2d.actor.Cover;
import com.pineapplepiranha.games.scene2d.actor.LevelBackground;
import com.pineapplepiranha.games.scene2d.actor.Patroler;
import com.pineapplepiranha.games.scene2d.actor.Player;
import com.pineapplepiranha.games.util.AssetsUtil;
import com.pineapplepiranha.games.util.ViewportUtil;

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
    private static float MAX_Y = ViewportUtil.VP_HEIGHT/2;
    private static float CAMERA_TRIGGER = ViewportUtil.VP_WIDTH/2;
    private static float MAX_COVER = 10;
    private static float DEPTH_HEIGHT = 20f;

    //Actor Groups
    public LevelBackground background;
    public Array<Cover> availableCover;
    public Array<Patroler> patrolers;
    public Player player;


    public StealthNessieStage(IGameProcessor gameProcessor){
        super(gameProcessor);
        AssetManager am = gameProcessor.getAssetManager();
        availableCover = new Array<Cover>();
        patrolers = new Array<Patroler>();

        //TextureAtlas atlas = am.get(AssetUtils.ANIMATIONS, AssetsUtil.TEXTURE_ATLAS);

        TextureRegion circle = new TextureRegion(gameProcessor.getAssetManager().get(AssetsUtil.CIRCLE, AssetsUtil.TEXTURE));
        GenericActor bgActor = new GenericActor(0f, 0f, ViewportUtil.VP_WIDTH, ViewportUtil.VP_HEIGHT, circle, Color.RED);
        bgActor.setRotationSpeed(2f);
        addActor(bgActor);

        TextureRegion bgTextureRegion = new TextureRegion(gameProcessor.getAssetManager().get(AssetsUtil.CITYSCAPE, AssetsUtil.TEXTURE));
        addActor(new GenericActor(0f, ViewportUtil.VP_HEIGHT/2, ViewportUtil.VP_WIDTH, ViewportUtil.VP_HEIGHT/2, bgTextureRegion, Color.YELLOW));


        initializeCover();


        TextureRegion tr = new TextureRegion(gameProcessor.getAssetManager().get(AssetsUtil.TREE_2, AssetsUtil.TEXTURE));
        player = new Player(0f, 5f, 40f, 30f, tr);

        addActor(player);



        initializeInputListeners();
    }

    private void initializeCover(){
        Random rand = new Random(System.currentTimeMillis());
        for(int i=0;i<MAX_COVER;i++){
            float x = rand.nextInt((int)MAX_X);
            float y = rand.nextInt((int)MAX_Y);

            TextureRegion tr = new TextureRegion(gameProcessor.getAssetManager().get(AssetsUtil.TREE_1, AssetsUtil.TEXTURE));
            Cover c = new Cover(x, y, 30f, 30f, tr, (int)Math.floor(x/DEPTH_HEIGHT));
            availableCover.add(c);
            addActor(c);
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
                }

                return true;
            }
        });
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        for(Cover c:availableCover){
            if(player.collider.overlaps(c.collider)){
                if(player.getY() > c.getY()){
                    c.setZIndex(player.getZIndex()+1);
                }else{
                    player.setZIndex(c.getZIndex() + 1);
                }
            }
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

        if(player.getX() > CAMERA_TRIGGER){

            getCamera().position.set(player.getX(), getCamera().position.y, getCamera().position.z);
        }
    }
}
