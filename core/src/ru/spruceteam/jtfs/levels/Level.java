package ru.spruceteam.jtfs.levels;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Hashtable;

import ru.spruceteam.jtfs.AssetManager;
import ru.spruceteam.jtfs.Core;
import ru.spruceteam.jtfs.levels.world.Time;
import ru.spruceteam.jtfs.levels.world.location.GridPoint;
import ru.spruceteam.jtfs.levels.world.location.Location;
import ru.spruceteam.jtfs.mob.player.Player;

public class Level implements Disposable { //extends Stage

    private static final String TAG = "Level";

    private boolean focusedOnPlayer = false;

    //buildable fields
    Location location;
    Player player;
    private final Time time = new Time();
    private final Stage stage = new Stage(new FitViewport(24,24));
    public final Hashtable<String, String> hash = new Hashtable<>();
    //location.getGrid().points[(int)point.x] [(int)point.y]

    public Level(){
        AssetManager manager = Core.getCore().manager;
        manager.load("mobs/Jam.atlas", TextureAtlas.class);
        manager.load("items/items.atlas", TextureAtlas.class);
        stage.setDebugAll(Gdx.app.getLogLevel() == Application.LOG_DEBUG);
    }

    public boolean isFocusedOnPlayer() {
        return focusedOnPlayer;
    }

    public void setFocusedOnPlayer(boolean focusedOnPlayer) {
        this.focusedOnPlayer = focusedOnPlayer;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        if (this.location != null){
            this.location.dispose();
            this.location.remove();
        }
        this.location = location;
        stage.addActor(location);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Time getTime() {
        return time;
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public void dispose() {
        location.dispose();
        stage.dispose();
    }

    public void switchLocation(Location location){
        Location old = this.location;
        if (old != null) old.dispose();
        this.location = location;
    }

    public void load(Preferences pref){
        player.load(pref);
        time.load(pref);
    }

    public void save(Preferences pref){
        Gdx.app.debug(TAG, "save() called");
        player.save(pref);
        time.save(pref);
        Gdx.app.debug(TAG, "save(): completed!");
    }
}
