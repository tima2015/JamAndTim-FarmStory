package ru.spruceteam.jtfs.mob.player.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class Items {
    private static final String TAG = "Items";
    private final Item[] items;
    private int current = 0;

    public Items(TextureAtlas atlas) {
        items = new Item[]{ new Hand(atlas), new WateringCan(atlas)};
    }

    public void nextItem(){
        if (++current >= items.length) current = 0;
    }

    public void previousItem(){
        if (--current < 0) current = items.length - 1;
    }

    public Item getCurrentItem(){
        return items[current];
    }

    public void load(Preferences pref){
        for (int i = 0; i < items.length; i++) {
            items[i].load(pref);
        }
    }

    public void save(Preferences pref){
        Gdx.app.debug(TAG, "save() called");
        for (int i = 0; i < items.length; i++) {
            items[i].save(pref);
        }
        Gdx.app.debug(TAG, "save(): completed!");
    }
}
