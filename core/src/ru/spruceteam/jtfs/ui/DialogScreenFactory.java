package ru.spruceteam.jtfs.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ru.spruceteam.jtfs.LevelScreen;

public class DialogScreenFactory {
    public static DialogScreen newPauseScreen(Screen backScreen){
        final DialogScreen screen = new DialogScreen("Pause", backScreen);
        screen.createButton("MainMenu").addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        screen.createButton("Return").addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.disposeAndBack();
            }
        });
        screen.stage.addListener(new InputListener(){

            private boolean escPressed = false;

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                return escPressed = keycode == Input.Keys.ESCAPE;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE && escPressed){
                    screen.disposeAndBack();
                    escPressed = false;
                    return true;
                }
                return false;
            }
        });
        return screen;
    }

    public static DialogScreen newSaveScreen(final Screen backScreen){
        final DialogScreen screen = new DialogScreen("Pause", backScreen);
        screen.createButton("Save").addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((LevelScreen)backScreen).getLevel().getTime().nextDay();
                ((LevelScreen)backScreen).getLevel().save(Gdx.app.getPreferences("save"));
                screen.disposeAndBack();
            }
        });
        screen.createButton("Return").addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.disposeAndBack();
            }
        });
        screen.stage.addListener(new InputListener(){

            private boolean escPressed = false;

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                return escPressed = keycode == Input.Keys.ESCAPE;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE && escPressed){
                    screen.disposeAndBack();
                    escPressed = false;
                    return true;
                }
                return false;
            }
        });
        return screen;
    }
}
