package ru.spruceteam.jtfs.mob;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import ru.spruceteam.jtfs.AssetManager;
import ru.spruceteam.jtfs.Core;
import ru.spruceteam.jtfs.levels.world.location.GridPoint;

public abstract class Mob extends Actor{

    public final String TAG = getClass().getSimpleName();

    private static float mobFrameDuration = 0;

    static{
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("properties/mob.properties"));
            mobFrameDuration = Float.parseFloat(properties.getProperty("mobFrameDuration"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public enum Direction {
        DOWN, LEFT, RIGHT, UP;

        protected static final int COUNT = 4;
    }

    public enum State{
        STAY, WALK, ACTION
    }

    protected Direction direction = Direction.DOWN;
    protected State state = State.STAY;
    private final Array<Animation<TextureRegion>> animations = new Array<>(Direction.COUNT);
    private float time = 0;
    private GridPoint pos;
    private final String name;
    private Vector2 size = Vector2.Zero;
    private final Vector2 deltaPos;
    private final Sprite drawable = new Sprite();

    public Mob(GridPoint pos, String name) {
        this.pos = pos;
        this.name = name;
        Core.getCore().manager.load("mobs/" + name + ".atlas", TextureAtlas.class);
        Core.getCore().manager.addOnLoadFinishListener(new AssetManager.OnLoadFinishListener() {
            @Override
            public void onLoadFinish(AssetManager manager) {
                TextureAtlas atlas = manager.get("mobs/" + Mob.this.name + ".atlas",
                        TextureAtlas.class);
                animations.add(
                        animationForDirection(Direction.DOWN, atlas),
                        animationForDirection(Direction.LEFT, atlas),
                        animationForDirection(Direction.RIGHT, atlas),
                        animationForDirection(Direction.UP, atlas)
                );
            }
        });
        deltaPos = new Vector2(0,0);
    }

    private Animation<TextureRegion> animationForDirection(Direction direction, TextureAtlas atlas){
        TextureAtlas.AtlasRegion region = atlas.findRegion(direction.name() + '0');
        return new Animation<TextureRegion>(mobFrameDuration,
                region,
                atlas.findRegion(direction.name() + '1'),
                region,
                atlas.findRegion(direction.name() + '2'));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!isVisible()) return;
        Animation<TextureRegion> animation = animations.get(direction == Direction.DOWN ? 0 :
                direction == Direction.LEFT ? 1 : direction == Direction.RIGHT ? 2 : 3);
        TextureRegion frame = animation.getKeyFrame(time);
        size = new Vector2(frame.getRegionWidth(), frame.getRegionHeight()).nor();
        drawable.setRegion(frame);
        setSize(size.x, size.y);
        drawable.setSize(size.x, size.y);
        drawable.setPosition(pos.x + deltaPos.x + size.x*.5f, pos.y + deltaPos.y);
        drawable.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setPosition(pos.x + deltaPos.x + size.x*.5f, pos.y + deltaPos.y);
        time = state == State.STAY ? 0 : time + delta;
        if (time > animations.get(0).getAnimationDuration())
            time = 0;
    }

    public Vector2 getSize() {
        return size;
    }

    public void setPosition(GridPoint pos) {
        this.pos = pos;
        setPosition(pos.x, pos.y);
    }

    public GridPoint getPosition() {
        return pos;
    }

    public Vector2 getDeltaPos() {
        return deltaPos;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public String getName() {
        return name;
    }
}
