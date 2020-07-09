package ru.spruceteam.jtfs.mob.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ru.spruceteam.jtfs.AssetManager;
import ru.spruceteam.jtfs.Core;
import ru.spruceteam.jtfs.levels.Level;
import ru.spruceteam.jtfs.levels.world.location.GridPoint;
import ru.spruceteam.jtfs.mob.Mob;
import ru.spruceteam.jtfs.mob.player.items.Items;

public class Player extends Mob {

    public static final String JAM = "Jam";

    private int energy = 100;
    private int money = 100;
    private Items items;

    //Actions
    private final MoveAction moveAction;

    //Listener
    private final ClickListener focusCameraClickListener;

    public Player(String name, final Level level) {
        super(new GridPoint(0,0), name);
        Core.getCore().manager.load("items/items.atlas", TextureAtlas.class);
        moveAction = new MoveAction(level);
        Core.getCore().manager.addOnLoadFinishListener(new AssetManager.OnLoadFinishListener() {
            @Override
            public void onLoadFinish(AssetManager manager) {
                items = new Items(Core.getCore().manager.get("items/items.atlas", TextureAtlas.class));
            }
        });
        addListener(focusCameraClickListener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                level.setFocusedOnPlayer(true);
            }
        });
    }

    public MoveAction.ActionClickListener getMoveActionClickListener(){
        return  moveAction.getClickListener();
    }

    public MoveAction getMoveAction() {
        return moveAction;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public Items getItems() {
        return items;
    }

    public void load(Preferences pref){
        pref.putInteger("money", money);
        items.load(pref);
    }

    public void save(Preferences pref){
        Gdx.app.debug(TAG, "save() called");
        money = pref.getInteger("money", money);
        items.save(pref);
        Gdx.app.debug(TAG, "save(): completed!");
    }
}
