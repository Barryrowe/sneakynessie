package com.pineapplepiranha.games.scene2d.stage;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.pineapplepiranha.games.delegate.IGameProcessor;
import com.pineapplepiranha.games.scene2d.actor.Cover;
import com.pineapplepiranha.games.scene2d.actor.LevelBackground;
import com.pineapplepiranha.games.scene2d.actor.Patroler;
import com.pineapplepiranha.games.scene2d.actor.Player;
import com.pineapplepiranha.games.util.AssetsUtil;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 8/23/14
 * Time: 9:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class StealthNessieStage extends BaseStage {


    //Actor Groups
    public LevelBackground background;
    public Array<Cover> availableCover;
    public Array<Patroler> patrolers;
    public Player player;


    public StealthNessieStage(IGameProcessor gameProcessor){
        super(gameProcessor);
        AssetManager am = gameProcessor.getAssetManager();
        //TextureAtlas atlas = am.get(AssetUtils.ANIMATIONS, AssetsUtil.TEXTURE_ATLAS);


        player = new Player(0f, 5f, 40f, 30f, null);
        addActor(player);


        initializeInputListeners();
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

                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if(Input.Keys.RIGHT == keycode){
                    player.setXVelocity(0);
                }else if(Input.Keys.LEFT == keycode){
                    player.setXVelocity(0);
                }

                if(Input.Keys.UP == keycode){
                    player.setYVelocity(0);
                }else if(Input.Keys.DOWN == keycode){
                    player.setYVelocity(0);
                }

                return true;
            }
        });
    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }
}
