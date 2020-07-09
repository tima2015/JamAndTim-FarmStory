package ru.spruceteam.jtfs.objects;

import com.badlogic.gdx.graphics.g2d.Batch;

import ru.spruceteam.jtfs.levels.Level;

public class GameObjectFactory {

    public GameObject createObject(String type){
        if (type == null) return new GameObject.NullObject();
        switch (type){
            case "bed":
                return new Bed();
            default:
                return new GameObject.NullObject();

        }
    }




}
