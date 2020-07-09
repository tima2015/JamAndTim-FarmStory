package ru.spruceteam.jtfs.levels.world.location;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import ru.spruceteam.jtfs.Core;
import ru.spruceteam.jtfs.LevelScreen;
import ru.spruceteam.jtfs.levels.Level;
import ru.spruceteam.jtfs.objects.GameObjectFactory;

/**
 * Сетка карты.
 * Каждая ячейка которой формально является серединой каждого тайла
 * карты (легче для понимания и представления),
 * но фактически каждая точка является левой нижней точкой каждого тайла.
 * Размер сетки равен размеру карты тайлов.
 */
public class Grid {
    /**
     * Точки сетки
     */
    public final GridPoint[][] points;

    public final GridPoint[] barrierPoints;

    public final int width;

    public final int height;

    /**
     * Инициализирует сетку по заданной карте.
     * Слой препядственых объектов должен называться "objects".
     * Слой проходимых объектов должен называться "points".
     * Объект границы мира представлен в виде линии и должен называться "barrier"
     * Объекты получают цену точки в размере заданом переменной {@link GridPoint#BARRIER_COST}
     * @param map карта тайлов по которой будет построена сетка
     */
    public Grid(TiledMap map) {
        MapProperties mapProp = map.getProperties();
        points = new GridPoint[width = mapProp.get("width", Integer.class)]
                [height = mapProp.get("height", Integer.class)];
        for (int x = 0; x < points.length; x++)
            for (int y = 0; y < points[x].length; y++)
                points[x][y] = new GridPoint(x,y);
        MapObjects objects = map.getLayers().get("objects").getObjects();
        Polygon barrier = ((PolygonMapObject)objects.get("barrier")).getPolygon();
        float[] vertices = barrier.getTransformedVertices();
        barrierPoints = new GridPoint[vertices.length/2];
        for (int i = 0; i < vertices.length; i += 2) {
            GridPoint point = points[(int) (vertices[i]) / 16][(int) (vertices[i + 1]) / 16];
            point.setCost(GridPoint.BARRIER_COST);
            barrierPoints[i/2] = point;
        }
        GameObjectFactory gameObjectFactory = new GameObjectFactory();
        for (RectangleMapObject object : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();
            GridPoint point = points[(int) rectangle.x / 16][(int) rectangle.y / 16];
            point.setCost(GridPoint.BARRIER_COST);
            point.setObject(gameObjectFactory.createObject(object.getName()));
        }
    }
}
