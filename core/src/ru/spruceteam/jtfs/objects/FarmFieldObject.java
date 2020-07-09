package ru.spruceteam.jtfs.objects;

import com.badlogic.gdx.graphics.g2d.Batch;

import ru.spruceteam.jtfs.levels.Level;

class FarmFieldObject extends GameObject{

    FarmFieldObject() {
        super("farm_field", null);
    }

    int plantDay = -1;

    @Override
    public void draw(Batch batch, float parentAlpha) {

    }
}
