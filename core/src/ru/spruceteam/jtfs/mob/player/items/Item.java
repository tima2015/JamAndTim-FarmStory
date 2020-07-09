package ru.spruceteam.jtfs.mob.player.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.spruceteam.jtfs.Drawable;

public abstract class Item implements Drawable {

    private final String TAG = getClass().getSimpleName();

    private final TextureRegion region;
    protected int count = 0;

    protected Item(TextureRegion region){
        this.region = region;
    }

    @Override
    public void draw(Batch batch, float x, float y) {
        batch.draw(region, x,y);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public abstract String getName();

    public void load(Preferences pref){
        count = pref.getInteger(getName(), count);
    }

    public void save(Preferences pref){
        Gdx.app.debug(TAG, "save() called");
        pref.putInteger(getName(), count);
        Gdx.app.debug(TAG, "save(): completed!");
    }
}
