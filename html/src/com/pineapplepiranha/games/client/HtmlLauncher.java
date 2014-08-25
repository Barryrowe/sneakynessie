package com.pineapplepiranha.games.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.pineapplepiranha.games.SneakyNessieGame;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
            GwtApplicationConfiguration config = new GwtApplicationConfiguration(960, 540);
            return config;
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new SneakyNessieGame();
        }
}