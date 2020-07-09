package ru.spruceteam.jtfs.levels.world;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ru.spruceteam.jtfs.levels.world.location.GridPoint;

public class TransferPoint {
    public final GridPoint pos;
    public final String target;
    public final GridPoint playerOnTargetPosition;

    public TransferPoint(RectangleMapObject point) {
        pos = new GridPoint((int)point.getRectangle().x, (int)point.getRectangle().y);
        MapProperties prop = point.getProperties();
        target = prop.get("target", String.class);
        playerOnTargetPosition = new GridPoint(prop.get("playerOnTargetPositionX", Integer.class),
                prop.get("playerOnTargetPositionY", Integer.class));
    }

    private TransferPoint(){
        pos = new GridPoint(0,0);
        target = "maps/home_house.tmx";
        playerOnTargetPosition = new GridPoint(13,12);
    }

    public static final TransferPoint SPAWN_TRANSFER = new TransferPoint();
}
