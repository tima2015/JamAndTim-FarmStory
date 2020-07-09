package ru.spruceteam.jtfs.objects.clock;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ru.spruceteam.jtfs.Core;

public class Clock  extends Actor { //TODO another actor

    private static final String TAG = "Clock";

    private Sprite bg, mArrow, hArrow;
    private Vector2 pos = new Vector2(0,0);
    private float time = 0;

    public Clock(TextureAtlas atlas){
        bg = new Sprite(atlas.findRegion("bg"));
        TextureAtlas.AtlasRegion arrow = atlas.findRegion("arrow");
        mArrow = new Sprite(arrow);
        mArrow.setOrigin(0,4);
        mArrow.setSize(bg.getWidth()*0.35f, mArrow.getHeight());
        hArrow = new Sprite(arrow);
        hArrow.setOrigin(0,4);
        hArrow.setSize(bg.getWidth()*0.2f, hArrow.getHeight());
    }


    public void draw(Batch batch, float x, float y) {
        bg.setPosition(x,y);
        bg.draw(batch);
        mArrow.setPosition(x + bg.getWidth()/2f, y + bg.getHeight()/2f);
        hArrow.setPosition(x + bg.getWidth()/2f, y + bg.getHeight()/2f);
        hArrow.setRotation(90 - 30*(time/60f));
        mArrow.setRotation(90 - 6*time);
        hArrow.draw(batch);
        mArrow.draw(batch);
        if (Gdx.app.getLogLevel() == Application.LOG_DEBUG){
            Core.getCore().font.draw(batch, (int)(time/60) + ":" + (int)time%60, x,
                    y - Core.getCore().font.getLineHeight());
        }
    }

    public void draw(Batch batch) {
        this.draw(batch, pos.x, pos.y);
    }

    public void setPosition(float x, float y) {
        pos.set(x,y);
    }

    public void setPosition(Vector2 pos) {
        this.pos.set(pos);
    }

    public Vector2 getPosition() {
        return pos;
    }

    @Override
    public float getWidth() {
        return bg.getWidth();
    }

    @Override
    public float getHeight() {
        return bg.getHeight();
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }
}
