package ru.spruceteam.jtfs.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;

import ru.spruceteam.jtfs.Command;
import ru.spruceteam.jtfs.Drawable;
import ru.spruceteam.jtfs.Updatable;
import ru.spruceteam.jtfs.levels.Level;

public abstract class GameObject implements Drawable {

    private static final String TAG = "GameObject";

    final Array<Command> commands = new Array<>();
    public final String objectName;

    public GameObject(String objectName) {
        this.objectName = objectName;
    }

    public void executeCommands(){
        for (int i = 0; i < commands.size; i++) {
            commands.get(i).execute();
        }
    }

    static class NullObject extends GameObject{
        public NullObject() {
            super("Simple object");
        }

        @Override
        public void draw(Batch batch, float x, float y) {

        }
    }
}
