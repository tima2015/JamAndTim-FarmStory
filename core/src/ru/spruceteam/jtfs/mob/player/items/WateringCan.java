package ru.spruceteam.jtfs.mob.player.items;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class WateringCan extends Item {

    public static final int MAX_WATER_LEVEL = 3;

    private int waterLevel = 0;

    WateringCan(TextureAtlas atlas){
        super(atlas.findRegion("watering_can"));
        count = 1;
    }

    public void fill(){
        waterLevel = MAX_WATER_LEVEL;
    }

    public void dropOne(){
        waterLevel--;
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    @Override
    public String getName() {
        return "Watering Can";
    }

    @Override
    public void load(Preferences pref) {
        super.load(pref);
        waterLevel = pref.getInteger("waterLevel", waterLevel);
    }

    @Override
    public void save(Preferences pref) {
        super.save(pref);
        pref.putInteger("waterLevel", waterLevel);
    }
}
