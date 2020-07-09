package ru.spruceteam.jtfs.levels.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import ru.spruceteam.jtfs.Updatable;
import ru.spruceteam.jtfs.levels.Level;
import ru.spruceteam.jtfs.levels.world.location.GridPoint;

public class CameraController implements Updatable, InputProcessor, GestureDetector.GestureListener {

    private OrthographicCamera camera;
    private Level level;

    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean downPressed = false;
    private boolean upPressed = false;
    private boolean plusPressed = false;
    private boolean minusPressed = false;

    public CameraController(OrthographicCamera camera, Level level) {
        super();
        this.camera = camera;
        this.level = level;
    }

    @Override
    public void update(float delta) {
        if (!level.isFocusedOnPlayer()) {
            Vector3 nor = new Vector3(Vector3.Zero);
            if (leftPressed) nor.x += -1;
            if (rightPressed) nor.x += 1;
            if (downPressed) nor.y += -1;
            if (upPressed) nor.y += 1;
            camera.position.lerp(nor.add(camera.position), .05f);

            if (plusPressed && camera.zoom > .1f) camera.zoom += -.01f;
            if (minusPressed && camera.zoom < 1) camera.zoom += .01f;
        } else {
            GridPoint position = level.getPlayer().getPosition();
            Vector2 deltaPos = level.getPlayer().getDeltaPos();
            camera.position.lerp(new Vector3(position.x + deltaPos.x + level.getPlayer().getSize().x,
                    position.y + deltaPos.y + level.getPlayer().getSize().y, 0), 0.05f);
        }
        if (camera.zoom > 1) camera.zoom = 1;
        if (camera.zoom < .1f) camera.zoom = .1f;
        float minPosition = 12*camera.zoom;
        float maxPosition = 12 + 12*(1 - camera.zoom);
        if (camera.position.x < minPosition) camera.position.x = minPosition;
        if (camera.position.y < minPosition) camera.position.y = minPosition;
        if (camera.position.x > maxPosition) camera.position.x = maxPosition;
        if (camera.position.y > maxPosition) camera.position.y = maxPosition;
        camera.update();
        if (Gdx.input.isKeyJustPressed(Input.Keys.C))
            level.setFocusedOnPlayer(!level.isFocusedOnPlayer());
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                leftPressed = true;
                return true;
            case Input.Keys.RIGHT:
                rightPressed = true;
                return true;
            case Input.Keys.DOWN:
                downPressed = true;
                return true;
            case Input.Keys.UP:
                upPressed = true;
                return true;
            case Input.Keys.PLUS:
                plusPressed = true;
                return true;
            case Input.Keys.MINUS:
                minusPressed = true;
                return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                leftPressed = false;
                return true;
            case Input.Keys.RIGHT:
                rightPressed = false;
                return true;
            case Input.Keys.DOWN:
                downPressed = false;
                return true;
            case Input.Keys.UP:
                upPressed = false;
                return true;
            case Input.Keys.PLUS:
                plusPressed = false;
                return true;
            case Input.Keys.MINUS:
                minusPressed = false;
                return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        if (amount == 1 && camera.zoom < 1) camera.zoom += .025f;
        else if (amount == -1 && camera.zoom > .1f) camera.zoom += -.025f;
        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
