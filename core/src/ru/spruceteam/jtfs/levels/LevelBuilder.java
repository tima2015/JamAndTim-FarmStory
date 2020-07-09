package ru.spruceteam.jtfs.levels;

import ru.spruceteam.jtfs.levels.world.location.Location;
import ru.spruceteam.jtfs.mob.player.Player;

public class LevelBuilder {

    private final Level level;

    public LevelBuilder(){
        level = new Level();
    }

    public LevelBuilder setStartLocation(Location part){
        level.location = part;
        return this;
    }

    public LevelBuilder setPlayer(Player player){
        level.player = player;
        return this;
    }

    public Level build(){
        return level;
    }
}
