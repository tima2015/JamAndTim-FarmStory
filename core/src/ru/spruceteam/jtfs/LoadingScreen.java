package ru.spruceteam.jtfs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.Locale;

public class LoadingScreen extends ScreenAdapter {

    private Screen previous;

    private Label label;
    private SpriteBatch batch;

    public LoadingScreen(Screen previous) {
        this.previous = previous;
    }

    @Override
    public void show() {
        label = new Label("nn%", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        label.setScale(3);
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        if (Core.getCore().manager.update())
            Core.getCore().setScreen(previous);
        label.setText(String.format(Locale.getDefault(),"%d%%",
                (int)(Core.getCore().manager.getProgress()*100)));
        batch.begin();
        label.draw(batch, 1);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        label.setPosition(width/2f - label.getPrefWidth()/2f,
                height/2f - label.getPrefHeight());
    }


    @Override
    public void dispose() {
        label.getStyle().font.dispose();
        batch.dispose();
    }
}
