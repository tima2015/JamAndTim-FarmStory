package ru.spruceteam.jtfs.levels.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;

import ru.spruceteam.jtfs.Resizable;

public abstract class Render implements Disposable, Resizable {

    protected final SpriteBatch batch = new SpriteBatch();
    protected final Viewport viewport;

    public Render(Viewport viewport) {
        this.viewport = viewport;
    }

    public void render(float delta){
        batch.setProjectionMatrix(viewport.getCamera().combined);
        draw(delta);
    }

    protected abstract void draw(float delta);

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public Viewport getViewport() {
        return viewport;
    }
}
