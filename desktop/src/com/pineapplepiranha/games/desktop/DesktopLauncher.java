package com.pineapplepiranha.games.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pineapplepiranha.games.SneakyNessieGame;
import com.pineapplepiranha.games.util.ViewportUtil;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "Sneaky Nessie";
        config.width = ViewportUtil.VP_WIDTH;
        config.height = ViewportUtil.VP_HEIGHT;
        //config.fullscreen = true;

		new LwjglApplication(new SneakyNessieGame(), config);
	}
}
