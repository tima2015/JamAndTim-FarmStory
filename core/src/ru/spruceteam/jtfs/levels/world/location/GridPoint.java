package ru.spruceteam.jtfs.levels.world.location;

import ru.spruceteam.jtfs.objects.GameObject;

/**
 * Точка сетки карты
 */
public class GridPoint {

    /**
     * Цена прохождения через барьер
     */
    public static final int BARRIER_COST = 10000;

    /**
     * Координата x
     */
    public final int x;

    /**
     * Координата y
     */
    public final int y;

    /**
     * Цена прохождения через точку
     */
    private int cost = 1;

    /**
     * Предыдущая точка, в цепи.
     */
    private GridPoint previous = null;

    private GameObject object = null;

    /**
     * @param x Координата x
     * @param y Координата y
     */
    public GridPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return цену прохождения через клетку
     */
    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public GridPoint getPrevious() {
        return previous;
    }

    public void setPrevious(GridPoint previous) {
        this.previous = previous;
    }

    public GameObject getObject() {
        return object;
    }

    public void setObject(GameObject object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
