package ru.spruceteam.jtfs.levels.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ru.spruceteam.jtfs.levels.Level;
import ru.spruceteam.jtfs.Updatable;

public class LevelController implements Updatable {

    //###########################
    private final Level level;//#
    //###########################
    private final CameraController cameraController;
    private final Camera camera;
    private final InputMultiplexer inputProcessor = new InputMultiplexer();
    private final ClickListener cameraFocusedClickListener = new ClickListener(){
        @Override
        public void clicked(InputEvent event, float x, float y) {
            level.setFocusedOnPlayer(!level.isFocusedOnPlayer());
        }
    };


    public LevelController(Level level, Camera camera) {
        this.level = level;
        cameraController = new CameraController((OrthographicCamera) camera, level);
        this.camera = camera;
        inputProcessor.addProcessor(cameraController);
        inputProcessor.addProcessor(level.getStage());
    }

    @Override
    public void update(float delta) {
        level.getTime().update(delta);
        cameraController.update(delta);
        level.getStage().act(delta);
    }

    public InputMultiplexer getInputProcessor() {
        return inputProcessor;
    }

    public ClickListener getCameraFocusedClickListener() {
        return cameraFocusedClickListener;
    }
}
