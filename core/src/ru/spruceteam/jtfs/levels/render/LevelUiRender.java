package ru.spruceteam.jtfs.levels.render;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.FitViewport;

import ru.spruceteam.jtfs.AssetManager;
import ru.spruceteam.jtfs.Core;
import ru.spruceteam.jtfs.levels.Level;
import ru.spruceteam.jtfs.objects.clock.Clock;

public class LevelUiRender extends Render {

    private final Level level;
    private Clock clock;

    public LevelUiRender(Level level) {
        super(new FitViewport(1024, 1024));
        this.level = level;
        Core.getCore().manager.addOnLoadFinishListener(onLoadFinishListener);
    }

    private AssetManager.OnLoadFinishListener onLoadFinishListener =
            new AssetManager.OnLoadFinishListener() {
        @Override
        public void onLoadFinish(AssetManager manager) {
            clock = new Clock(manager.get("objects/clock/clock.atlas", TextureAtlas.class));
            clock.setPosition(0, viewport.getWorldHeight() - clock.getHeight());
        }
    };

    @Override
    protected void draw(float delta) {
        batch.begin();
        clock.setTime(level.getTime().getTime());
        clock.draw(batch);
        batch.end();
    }
}
