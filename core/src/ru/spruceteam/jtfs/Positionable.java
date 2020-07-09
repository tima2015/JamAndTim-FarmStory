package ru.spruceteam.jtfs;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import ru.spruceteam.jtfs.levels.world.location.GridPoint;

public interface Positionable {
    void setPosition(GridPoint pos);
    GridPoint getPosition();
    interface PosDrawable{
        void draw(Batch batch);
    }
}
