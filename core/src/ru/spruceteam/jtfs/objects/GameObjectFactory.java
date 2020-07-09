package ru.spruceteam.jtfs.objects;

import com.badlogic.gdx.graphics.g2d.Batch;

import ru.spruceteam.jtfs.levels.Level;
import ru.spruceteam.jtfs.levels.world.location.GridPoint;

public class GameObjectFactory {

    public GameObject createObject(String type, GridPoint pos){
        if (type == null) return new GameObject.NullObject();
        switch (type){
            case "bed":
                return new Bed(pos);
            default:
                return null;

        }
    }




}
