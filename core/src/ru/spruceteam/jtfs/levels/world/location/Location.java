package ru.spruceteam.jtfs.levels.world.location;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import ru.spruceteam.jtfs.AssetManager;
import ru.spruceteam.jtfs.Core;
import ru.spruceteam.jtfs.levels.Level;
import ru.spruceteam.jtfs.levels.world.TransferPoint;
import ru.spruceteam.jtfs.mob.player.Player;
import ru.spruceteam.jtfs.objects.GameObject;

public class Location extends Group implements Disposable {

    private static final String TAG = "Location";


    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private TransferPoint transfer;
    private final Level level;
    private Grid grid;
    private boolean begin = false;
    //locationswithpoint
    //objects

    private final AssetManager.OnLoadFinishListener onLoadFinishListener =
            new AssetManager.OnLoadFinishListener() {
                @Override
                public void onLoadFinish(AssetManager manager) {
                    setMap(manager.get(Location.this.transfer.target, TiledMap.class));
                }
            };

    public Location(TransferPoint transfer, Level level) {
        this.transfer = transfer;
        this.level = level;
        AssetManager manager = Core.getCore().manager;
        manager.load(transfer.target, TiledMap.class);
        manager.addOnLoadFinishListener(onLoadFinishListener);
        setPosition(0, 0);
        setSize(24, 24);
        mapRenderer = new OrthogonalTiledMapRenderer(null, 1 / 32f, level.getStage().getBatch());
    }

    public void begin() {
        if (begin) return;

        Player player = level.getPlayer();
        addActor(player);
        addListener(player.getMoveActionClickListener());
        for (int x = 0; x < grid.points.length; x++)
            for (int y = 0; y < grid.points[x].length; y++)
                if (grid.points[x][y].getObject() != null)
                    addActor(grid.points[x][y].getObject());

        player.setPosition(transfer.playerOnTargetPosition);
        begin = true;

    }

    public TiledMap getMap() {
        return map;
    }

    public TransferPoint getTransfer() {
        return transfer;
    }

    public Grid getGrid() {
        return grid;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        mapRenderer.setView((OrthographicCamera) getStage().getCamera());
        mapRenderer.render();
        batch.begin();
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void setMap(TiledMap map) {
        //Maybe I must do dispose?
        clearChildren();
        this.map = map;
        grid = new Grid(map);
        mapRenderer.setMap(map);
        begin = false;
    }

    public void setTransfer(TransferPoint transfer) {
        this.transfer = transfer;
    }

    @Override
    public void dispose() {
        AssetManager manager = Core.getCore().manager;
        manager.unload(transfer.target);
        manager.removeOnLoadFinishListener(onLoadFinishListener);
        mapRenderer.dispose();
        for (int x = 0; x < grid.points.length; x++) {
            for (int y = 0; y < grid.points[x].length; y++) {
                GameObject object = grid.points[x][y].getObject();
                if (object instanceof Disposable)
                    ((Disposable) object).dispose();
            }
        }
    }
}
