package ru.spruceteam.jtfs.debug;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import java.util.Stack;

import ru.spruceteam.jtfs.levels.Level;
import ru.spruceteam.jtfs.levels.world.location.Grid;
import ru.spruceteam.jtfs.levels.world.location.GridPoint;
import ru.spruceteam.jtfs.mob.MobPath;

public class MapDebugRender implements Disposable {

    private final Level level;

    private final boolean drawRectangles;
    private final boolean drawPaths;
    private final boolean drawGrid;
    private final boolean drawBarrier;

    private Texture rectangle;
    private Texture pathPoint;
    private Texture gridTexture;
    private Texture barrierTexture;

    public MapDebugRender(boolean drawRectangles, boolean drawPaths, boolean drawGrid, boolean drawBarrier, Level level) {
        this.drawRectangles = drawRectangles;
        this.level = level;
        if (drawRectangles)
            rectangle = rectangleTexture();
        this.drawPaths = drawPaths;
        if (drawPaths)
            pathPoint = pathPointTexture();
        this.drawGrid = drawGrid;
        if (drawGrid)
            gridTexture = gridTexture();
        this.drawBarrier = drawBarrier;
        if (drawBarrier)
            barrierTexture = barrierTexture();

    }

    private Texture barrierTexture() {
        Pixmap pixmap = new Pixmap(128,128, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.MAGENTA);
        pixmap.fillCircle(16, 16, 8);
        pixmap.fillCircle(127 - 16, 127 - 16, 8);
        pixmap.fillCircle(127 - 16, 16, 8);
        pixmap.fillCircle(16, 127 - 16, 8);
        pixmap.fillCircle(64,64,16);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    private Texture rectangleTexture(){
        Pixmap pixmap = new Pixmap(32,32, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.PURPLE);
        pixmap.drawLine(0,0,0,pixmap.getHeight() - 1);
        pixmap.drawLine(0,pixmap.getHeight() - 1,pixmap.getWidth()-1,pixmap.getHeight() - 1);
        pixmap.drawLine(pixmap.getWidth()-1,pixmap.getHeight() - 1,pixmap.getWidth()-1, 0);
        pixmap.drawLine(pixmap.getWidth()-1,0,0,0);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    private Texture pathPointTexture(){
        Pixmap pixmap = new Pixmap(32,32, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.PURPLE);
        pixmap.fillCircle(16,16,6);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    private Texture gridTexture(){
        Pixmap pixmap = new Pixmap(64,64, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.drawLine(0, 0, 0, pixmap.getHeight() - 1);
        pixmap.drawLine(0,pixmap.getHeight() - 1, pixmap.getWidth() - 1,
                pixmap.getHeight() - 1);
        pixmap.setColor(Color.LIME);
        pixmap.fillCircle(0,pixmap.getHeight() - 1, 8);
        pixmap.fillCircle(32,32, 12);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    public void draw(Batch batch, MapLayer objectLayer) {
        Array<RectangleMapObject> byType = objectLayer.getObjects().getByType(RectangleMapObject.class);
        if (drawRectangles) for (RectangleMapObject obj : byType) {
            Rectangle rect = obj.getRectangle();
            batch.draw(rectangle, rect.x/16,rect.y/16, 1,1);
        }
        Grid grid = level.getLocation().getGrid();
        if (drawGrid)
            for (int x = 0; x < grid.points.length; x++)
                for (int y = 0; y < grid.points[x].length; y++)
                    batch.draw(gridTexture, grid.points[x][y].x, grid.points[x][y].y, 1, 1);

        Stack<GridPoint> path = level.getPlayer().getMoveAction().getPath();
        if (drawPaths && path != null && !path.isEmpty())
            for (GridPoint point : path) batch.draw(pathPoint, point.x, point.y, 1, 1);

        if (drawBarrier){
            for (GridPoint barrierPoint : level.getLocation().getGrid().barrierPoints) {
                batch.draw(barrierTexture, barrierPoint.x, barrierPoint.y, 1,1);
            }
        }

    }

    @Override
    public void dispose() {
        if (drawRectangles)
            rectangle.dispose();
        if (drawPaths)
            pathPoint.dispose();
        if (drawGrid)
            gridTexture.dispose();
        if (drawBarrier)
            barrierTexture.dispose();
    }
}
