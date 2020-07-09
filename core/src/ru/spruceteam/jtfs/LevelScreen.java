package ru.spruceteam.jtfs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spruceteam.jtfs.levels.Level;
import ru.spruceteam.jtfs.levels.controller.LevelController;
import ru.spruceteam.jtfs.levels.render.LevelGameRender;
import ru.spruceteam.jtfs.levels.render.LevelUiRender;
import ru.spruceteam.jtfs.ui.DialogScreenFactory;

public class LevelScreen extends ScreenAdapter {
    private final LevelController controller;
    private final LevelGameRender gameRender;
    private final LevelUiRender uiRender;
    private final Level level;

    public LevelScreen(final Level level){
        gameRender = new LevelGameRender(level);
        controller = new LevelController(level, gameRender.getViewport().getCamera());
        uiRender = new LevelUiRender(level);
        this.level = level;
    }

    public LevelController getController() {
        return controller;
    }

    public LevelGameRender getGameRender() {
        return gameRender;
    }

    public LevelUiRender getUiRender() {
        return uiRender;
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public void show() {
        AssetManager manager = Core.getCore().manager;
        String clock = "objects/clock/clock.atlas";
        if (!manager.isLoaded(clock)) manager.load(clock, TextureAtlas.class);
        if (!manager.isFinished())
            Core.getCore().setScreen(new LoadingScreen(this));
        else
            level.getLocation().begin();
        Gdx.input.setInputProcessor(controller.getInputProcessor());
    }

    @Override
    public void render(float delta) {
        gameRender.render(delta);
        //uiRender.render(delta);
        controller.update(delta);
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            Core.getCore().setScreen(DialogScreenFactory.newPauseScreen(this));
    }

    @Override
    public void resize(int width, int height) {
        gameRender.resize(width, height);
        uiRender.resize(width, height);
    }

    @Override
    public void dispose() {
        gameRender.dispose();
        uiRender.dispose();
        level.dispose();
    }
}
