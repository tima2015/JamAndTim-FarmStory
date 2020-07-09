package ru.spruceteam.jtfs.mob;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import ru.spruceteam.jtfs.levels.world.location.Grid;
import ru.spruceteam.jtfs.levels.world.location.GridPoint;
import ru.spruceteam.jtfs.levels.world.location.Location;

public class MobPath {
    private static final String TAG = "MobPath";

    private Stack<GridPoint> path;
    private Vector2 pos;

    public MobPath(GridPoint from, GridPoint to, Grid grid) {
        Gdx.app.debug(TAG, "MobPath() called with: from = [" + from + "], to = [" + to + "], grid = [" + grid + "]");
        path = findPath(from, to, grid);
    }

    public Stack<GridPoint> getPath() {
        if (path == null)
            return new Stack<>();
        return path;
    }

    private Stack<GridPoint> findPath(GridPoint from, GridPoint to, Grid grid) {
        ArrayList<GridPoint> reachable = new ArrayList<>();
        ArrayList<GridPoint> explored = new ArrayList<>();
        reachable.add(from);

        while (!reachable.isEmpty()) {
            GridPoint point = chooseNode(reachable, to);

            if (point == to)
                return buildPath(point);

            reachable.remove(point);
            explored.add(point);

            Array<GridPoint> newReachable = createNewReachable(point, grid, explored);
            for (GridPoint adjacent : newReachable) {
                if (!reachable.contains(adjacent)) {
                    adjacent.setPrevious(point);
                    reachable.add(adjacent);
                }
            }
        }
        return null;
    }

    private Array<GridPoint> createNewReachable(GridPoint point, Grid grid, ArrayList<GridPoint> explored) {
        Array<GridPoint> newReachable = new Array<>();
        if (point.x > 0) {
            GridPoint o = grid.points[point.x - 1][point.y];
            if (!explored.contains(o)) newReachable.add(o);
        }
        if (point.x < grid.width - 1) {
            GridPoint o = grid.points[point.x + 1][point.y];
            if (!explored.contains(o)) newReachable.add(o);
        }
        if (point.y > 0) {
            GridPoint o = grid.points[point.x][point.y - 1];
            if (!explored.contains(o)) newReachable.add(o);
        }
        if (point.y < grid.height - 1) {
            GridPoint o = grid.points[point.x][point.y + 1];
            if (!explored.contains(o)) newReachable.add(o);
        }
        return newReachable;
    }

    private GridPoint chooseNode(ArrayList<GridPoint> reachable, GridPoint goal) {
        int minCost = GridPoint.BARRIER_COST * 10;
        GridPoint bestNode = null;

        for (GridPoint point : reachable) {
            int costNodeToGoal = (int) Math.sqrt(Math.abs(goal.x - point.x) + Math.abs(goal.y - point.y));
            int totalCost = costNodeToGoal + point.getCost();

            if (minCost > totalCost) {
                minCost = totalCost;
                bestNode = point;
            }
        }
        return bestNode;
    }

    private Stack<GridPoint> buildPath(GridPoint point) {
        Gdx.app.debug(TAG, "buildPath() called with: point = [" + point + "]");
        final Stack<GridPoint> path = new Stack<>();
        while (point.getPrevious() != null) {
            if (point.getCost() == GridPoint.BARRIER_COST)
                path.clear();
            else
                path.push(point);
            GridPoint oldPoint = point;
            point = point.getPrevious();
            oldPoint.setPrevious(null);

        }
        Gdx.app.debug(TAG, "Path was build! " + path.toString());
        return path;
    }

}
