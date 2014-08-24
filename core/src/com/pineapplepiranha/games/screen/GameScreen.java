package com.pineapplepiranha.games.screen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pineapplepiranha.games.data.IDataSaver;
import com.pineapplepiranha.games.delegate.IGameProcessor;
import com.pineapplepiranha.games.delegate.IStageManager;
import com.pineapplepiranha.games.scene2d.stage.BaseStage;
import com.pineapplepiranha.games.scene2d.GenericActor;
import com.pineapplepiranha.games.scene2d.stage.StealthNessieStage;
import com.pineapplepiranha.games.util.AssetsUtil;
import com.pineapplepiranha.games.util.ViewportUtil;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 8/23/14
 * Time: 1:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameScreen extends ApplicationAdapter implements Screen, InputProcessor, IStageManager {

    protected IGameProcessor gameProcessor;

    private StealthNessieStage stage;
    private TextureRegion bgTextureRegion;
//    private FrameBuffer fbo;
//    private ShaderProgram finalShader;
//    private ShaderProgram defaultShader;
//    public float zAngle;
//    public static final float zSpeed = 15.0f;
//    public static final float PI2 = 3.1415926535897932384626433832795f * 2.0f;
//    private boolean	lightMove = false;
//    private boolean lightOscillate = false;
//    private Texture light;
//    private Texture bg;
//
//    public static final float ambientIntensity = .7f;
//    public static final Vector3 ambientColor = new Vector3(0.3f, 0.3f, 0.7f);
//    private static final String vertexShader = "attribute vec4 a_position;\n" +
//            "attribute vec4 a_color;\n" +
//            "attribute vec2 a_texCoord0;\n" +
//            "uniform mat4 u_projTrans;\n" +
//            "varying vec4 vColor;\n" +
//            "varying vec2 vTexCoord;\n" +
//            "\n" +
//            "void main() {\n" +
//            "\tvColor = a_color;\n" +
//            "\tvTexCoord = a_texCoord0;\n" +
//            "\tgl_Position = u_projTrans * a_position;\t\t\n" +
//            "}";
//    private static final String ambientPixelShader = "#ifdef GL_ES\n" +
//            "#define LOWP lowp\n" +
//            "precision mediump float;\n" +
//            "#else\n" +
//            "#define LOWP\n" +
//            "#endif\n" +
//            "\n" +
//            "varying LOWP vec4 vColor;\n" +
//            "varying vec2 vTexCoord;\n" +
//            "\n" +
//            "//texture samplers\n" +
//            "uniform sampler2D u_texture; //diffuse map\n" +
//            "\n" +
//            "//additional parameters for the shader\n" +
//            "uniform LOWP vec4 ambientColor;\n" +
//            "\n" +
//            "void main() {\n" +
//            "\tvec4 diffuseColor = texture2D(u_texture, vTexCoord);\n" +
//            "\tvec3 ambient = ambientColor.rgb * ambientColor.a;\n" +
//            "\t\n" +
//            "\tvec3 final = vColor * diffuseColor.rgb * ambient;\n" +
//            "\tgl_FragColor = vec4(final, diffuseColor.a);\n" +
//            "}";
//
//    private static final String defaultPixelShader = "#ifdef GL_ES\n" +
//            "#define LOWP lowp\n" +
//            "precision mediump float;\n" +
//            "#else\n" +
//            "#define LOWP\n" +
//            "#endif\n" +
//            "\n" +
//            "varying LOWP vec4 vColor;\n" +
//            "varying vec2 vTexCoord;\n" +
//            "\n" +
//            "//our texture samplers\n" +
//            "uniform sampler2D u_texture; //diffuse map\n" +
//            "\n" +
//            "void main() {\n" +
//            "\tvec4 DiffuseColor = texture2D(u_texture, vTexCoord);\n" +
//            "\tgl_FragColor = vColor * DiffuseColor;\n" +
//            "}";
//
//    private static final String lightPixelShader = "#ifdef GL_ES\n" +
//            "#define LOWP lowp\n" +
//            "precision mediump float;\n" +
//            "#else\n" +
//            "#define LOWP\n" +
//            "#endif\n" +
//            "\n" +
//            "varying LOWP vec4 vColor;\n" +
//            "varying vec2 vTexCoord;\n" +
//            "\n" +
//            "//our texture samplers\n" +
//            "uniform sampler2D u_texture; //diffuse map\n" +
//            "uniform sampler2D u_lightmap;   //light map\n" +
//            "\n" +
//            "//resolution of screen\n" +
//            "uniform vec2 resolution; \n" +
//            "\n" +
//            "void main() {\n" +
//            "\tvec2 lighCoord = (gl_FragCoord.xy / resolution.xy);\n" +
//            "\tvec4 Light = texture2D(u_lightmap, lighCoord);\n" +
//            "\t\n" +
//            "\tgl_FragColor = vColor * Light;\n" +
//            "}";
//
//    private static final String finalPixelShader = "#ifdef GL_ES\n" +
//            "#define LOWP lowp\n" +
//            "precision mediump float;\n" +
//            "#else\n" +
//            "#define LOWP\n" +
//            "#endif\n" +
//            "\n" +
//            "varying LOWP vec4 vColor;\n" +
//            "varying vec2 vTexCoord;\n" +
//            "\n" +
//            "//texture samplers\n" +
//            "uniform sampler2D u_texture; //diffuse map\n" +
//            "uniform sampler2D u_lightmap;   //light map\n" +
//            "\n" +
//            "//additional parameters for the shader\n" +
//            "uniform vec2 resolution; //resolution of screen\n" +
//            "uniform LOWP vec4 ambientColor; //ambient RGB, alpha channel is intensity \n" +
//            "\n" +
//            "void main() {\n" +
//            "\tvec4 diffuseColor = texture2D(u_texture, vTexCoord);\n" +
//            "\tvec2 lighCoord = (gl_FragCoord.xy / resolution.xy);\n" +
//            "\tvec4 light = texture2D(u_lightmap, lighCoord);\n" +
//            "\t\n" +
//            "\tvec3 ambient = ambientColor.rgb * ambientColor.a;\n" +
//            "\tvec3 intensity = ambient + light.rgb;\n" +
//            " \tvec3 finalColor = diffuseColor.rgb * intensity;\n" +
//            "\t\n" +
//            "\tgl_FragColor = vColor * vec4(finalColor, diffuseColor.a);\n" +
//            "}\n";
//    private void sharderStuff(){
//        ShaderProgram.pedantic = false;
////        ShaderProgram ambientShader = new ShaderProgram(vertexShader, ambientPixelShader);
////        ambientShader.begin();
////        ambientShader.setUniformf("ambientColor", ambientColor.x, ambientColor.y,
////                ambientColor.z, ambientIntensity);
////        ambientShader.end();
//
//
//        defaultShader = new ShaderProgram(vertexShader, defaultPixelShader);
//
//        finalShader = new ShaderProgram(vertexShader, finalPixelShader);
//        finalShader.begin();
//        finalShader.setUniformi("u_lightmap", 1);
//        finalShader.setUniformf("ambientColor", ambientColor.x, ambientColor.y,
//                ambientColor.z, ambientIntensity);
//        finalShader.end();
//
//        light = gameProcessor.getAssetManager().get(AssetsUtil.LIGHT, AssetsUtil.TEXTURE);
//        bg = gameProcessor.getAssetManager().get(AssetsUtil.GAME_BG, AssetsUtil.TEXTURE);
//    }


    public GameScreen(IGameProcessor delegate){
        gameProcessor = delegate;

        stage = new StealthNessieStage(gameProcessor);
//        sharderStuff();
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean keyTyped(char character) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean scrolled(int amount) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void hide() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.resize(width, height);
    }


}
