package ru.spruceteam.jtfs.mob.player.items;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Hand extends Item{

    protected Hand(TextureAtlas atlas) {
        super(atlas.findRegion("hand"));
        count = 1;
    }

    @Override
    public String getName() {
        return "Hand";
    }
}
