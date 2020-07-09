package ru.spruceteam.jtfs.ui;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ru.spruceteam.jtfs.Core;
import ru.spruceteam.jtfs.Utils;

public class DialogScreen implements Screen {

    protected Stage stage = new Stage(new ScreenViewport());
    private VerticalGroup group = new VerticalGroup();
    private Texture background = Utils.getScreenshot();
    private final Screen backScreen;
    private final Window window;

    public DialogScreen(String title, Screen backScreen) {
        this.backScreen = backScreen;
        shadeBackground();
        window = new Window(title, Core.getCore().uiSkin);
        stage.addActor(window);
        window.add(group);
        group.space(8f);
    }

    private void shadeBackground(){
        Pixmap pixmap = new Pixmap(background.getWidth(), background.getHeight(),
                Pixmap.Format.RGBA8888);
        pixmap.setColor(0,1,0, 0.5f);
        pixmap.fill();//Нужно использовать скрипты!!!TODO
        //background.draw(pixmap,0,0);
        pixmap.dispose();
    }

    protected final TextButton createButton(String text){
        TextButton button = new TextButton(text, Core.getCore().uiSkin);
        group.addActor(button);
        return button;
    }

    public void disposeAndBack(){
        dispose();
        Core.getCore().setScreen(backScreen);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.setDebugAll(Gdx.app.getLogLevel() == Application.LOG_DEBUG);
        stage.getBatch().setShader(Core.getCore().getShaders().shade);
    }

    @Override
    public void render(float delta) {
        stage.getBatch().begin();
        Core.getCore().getShaders().shade.setShade(0.4f);
        stage.getBatch().draw(background,  0,0, stage.getWidth(), stage.getHeight(),0,0,background.getWidth(), background.getHeight(), false, true);
        stage.getBatch().end();
        Core.getCore().getShaders().shade.setShade(0);
        stage.act();
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        window.setPosition(stage.getViewport().getWorldWidth()/2f - window.getWidth()/2f,
                stage.getViewport().getWorldHeight()/2f - window.getPrefHeight()/2f);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
    }
}
