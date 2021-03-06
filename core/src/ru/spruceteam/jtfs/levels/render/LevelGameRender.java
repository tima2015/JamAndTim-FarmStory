package ru.spruceteam.jtfs.levels.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import ru.spruceteam.jtfs.AssetManager;
import ru.spruceteam.jtfs.Core;
import ru.spruceteam.jtfs.debug.MapDebugRender;
import ru.spruceteam.jtfs.levels.Level;

public class LevelGameRender extends Render{

    private static final String TAG = "LevelGameRender";

    private final Level level;

    private MapDebugRender mapDebugRender;

    public LevelGameRender(Level level) {
        super(level.getStage().getViewport());
        this.level = level;
        Core.getCore().manager.addOnLoadFinishListener(onLoadFinishListener);
        mapDebugRender = new MapDebugRender(true, true, true, true, level);
    }

    private AssetManager.OnLoadFinishListener onLoadFinishListener =
            new AssetManager.OnLoadFinishListener() {
        @Override
        public void onLoadFinish(AssetManager manager) {
            batch.setShader(Core.getCore().getShaders().shade);
        }
    };

    @Override
    protected void draw(float delta) {
        setShadeLevel();
        level.getStage().draw();
        batch.begin();
        //mapDebugRender.draw(batch, level.getLocation().getMap().getLayers().get("objects"));
        batch.end();
        //debugDraw();
    }

    private void setShadeLevel(){
        float shade = 0;
        switch (level.getTime().getState()){
            case DAY:
                shade = 0;
                break;
            case MORNING:
            case EVENING:
                shade = 0.25f;
                break;
            case NIGHT:
                shade = 0.5f;
                break;
        }

        Core.getCore().getShaders().shade.setShade(shade);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        Core.getCore().manager.removeOnLoadFinishListener(onLoadFinishListener);
        debugFont.dispose();
        mapDebugRender.dispose();
        debugBatch.dispose();//todo fixme
    }

    private BitmapFont debugFont = new BitmapFont();
    private Label label = new Label("", new Label.LabelStyle(debugFont, Color.WHITE));

    SpriteBatch debugBatch = new SpriteBatch();
    private void debugDraw(){
        Vector3 vector3 = viewport.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        BitmapFont f = Core.getCore().font;
        OrthographicCamera camera = (OrthographicCamera) getViewport().getCamera();
        debugBatch.begin();
        f.draw(debugBatch, String.format("Mouse position - X:%.1f  Y:%.1f \n" +
                "Camera position - X:%.1f, Y:%.1f \n" +
                "Is camera focused on Player: %b \n" +
                        "Camera zoom: %.3f \n" +
                        "FPS: %d \n" +
                        "Time: %s",
                vector3.x, vector3.y, camera.position.x, camera.position.y,
                level.isFocusedOnPlayer(), camera.zoom, Gdx.graphics.getFramesPerSecond(),
                level.getTime().toString()),
                0, Gdx.graphics.getHeight());
        debugBatch.end();
    }
}
