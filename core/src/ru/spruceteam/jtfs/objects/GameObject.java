package ru.spruceteam.jtfs.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.utils.Array;

import ru.spruceteam.jtfs.levels.world.location.GridPoint;

public abstract class GameObject extends Actor {

    private static final String TAG = "GameObject";

    public final String objectName;
    private final GridPoint pos;

    public GameObject(String objectName, GridPoint pos) {
        this.objectName = objectName;
        this.pos = pos;
        setPosition(pos.x, pos.y);
        setSize(1,1);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public static class PlayerExecuteObjectTaskEvent extends Event{

    }

    @Override
    public abstract void draw(Batch batch, float parentAlpha);

    static class NullObject extends GameObject{
        public NullObject() {
            super("Simple object", new GridPoint(-1, -1));
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {

        }
    }
}
