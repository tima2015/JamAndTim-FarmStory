package ru.spruceteam.jtfs.levels.world.location;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import ru.spruceteam.jtfs.AssetManager;
import ru.spruceteam.jtfs.Core;
import ru.spruceteam.jtfs.levels.Level;
import ru.spruceteam.jtfs.levels.world.TransferPoint;
import ru.spruceteam.jtfs.mob.player.Player;

public class Location extends Group implements Disposable {

    private static final String TAG = "Location";


    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private final TransferPoint transfer;
    private final Level level;
    private TransferPoint[] transferPoints;
    private Grid grid;
    private boolean begin = false;
    //locationswithpoint
    //objects

    private final AssetManager.OnLoadFinishListener onLoadFinishListener =
            new AssetManager.OnLoadFinishListener() {
                @Override
                public void onLoadFinish(AssetManager manager) {
                    setMap(manager.get(Location.this.transfer.target, TiledMap.class));
                    Array<RectangleMapObject> points = map.getLayers().get("points")
                            .getObjects().getByType(RectangleMapObject.class);
                    transferPoints = new TransferPoint[points.size];
                    for (int i = 0; i < transferPoints.length; i++) {
                        transferPoints[i] = new TransferPoint(points.get(i));
                    }
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
        mapRenderer = new OrthogonalTiledMapRenderer(null, 1 / 16f, level.getStage().getBatch());
    }

    public void begin() {
        if (begin) return;

        Player player = level.getPlayer();
        addActor(player);
        addListener(player.getMoveActionClickListener());

        player.setPosition(transfer.playerOnTargetPosition);
        begin = true;

    }

    public TiledMap getMap() {
        return map;
    }

    public TransferPoint getTransfer() {
        return transfer;
    }

    public TransferPoint[] getTransferPoints() {
        return transferPoints;
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
        this.map = map;
        grid = new Grid(map);
        mapRenderer.setMap(map);
        begin = false;
    }

    @Override
    public void dispose() {
        AssetManager manager = Core.getCore().manager;
        manager.unload(transfer.target);
        manager.removeOnLoadFinishListener(onLoadFinishListener);
        mapRenderer.dispose();
    }
}
