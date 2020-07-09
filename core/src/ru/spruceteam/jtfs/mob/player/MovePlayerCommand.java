package ru.spruceteam.jtfs.mob.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;

import ru.spruceteam.jtfs.Updatable;
import ru.spruceteam.jtfs.levels.Level;
import ru.spruceteam.jtfs.levels.world.location.Grid;
import ru.spruceteam.jtfs.levels.world.location.GridPoint;
import ru.spruceteam.jtfs.mob.Mob;
import ru.spruceteam.jtfs.mob.MobPath;
import ru.spruceteam.jtfs.objects.GameObject;

public class MovePlayerCommand implements PlayerCommand, Updatable {

    private static final String TAG = "MovePlayerCommand";

    private Level level;

    private enum State{
        STAY, MOVE
    }

    private GridPoint pos0;
    private State state = State.STAY;
    private MobPath path = null;
    private GameObject object = null;
    private GridPoint finish;

    MovePlayerCommand(Level level){
        this.level = level;
    }

    public MobPath getPath() {
        return path;
    }

    @Override
    public void execute(Object... params) throws WrongParamsException {
        if (!(params.length >= 2 && params[0] instanceof Integer && params[1] instanceof Integer))
            throw new WrongParamsException("Command must have 2 parameters! " +
                    "Coordinate x,y in integer.");
        Gdx.app.debug(TAG, "execute() called with: params = [" + Arrays.toString(params) + "]");
        finish = level.getLocation().getGrid().points[(int) params[0]][(int) params[1]];

        pos0 = level.getPlayer().getPosition();
        object = finish.getObject();
        path = new MobPath(pos0, finish, level.getLocation().getGrid());
        if (path.getPath().isEmpty()) setDirection(pos0, finish);

    }


    @Override
    public void update(float delta) {
        if (path == null || path.getPath().isEmpty()) {
            if (object != null) {
                object.executeCommands();
                object = null;
            }
            return;
        }
        level.getPlayer().setState(Mob.State.WALK);
        GridPoint pos1 = path.getPath().peek();
        if (pos1.x != pos0.x){
            boolean b = pos1.x > pos0.x;
            level.getPlayer().getDeltaPos().x += (b ? 1 : -1) * delta;
            level.getPlayer().setDirection(b ? Mob.Direction.RIGHT : Mob.Direction.LEFT);
            float v = level.getPlayer().getPosition().x + level.getPlayer().getDeltaPos().x;
            if ((b && v >= pos1.x) || (!b && v <= pos1.x)){
                pos0 = pos1;
                path.getPath().pop();
                level.getPlayer().getDeltaPos().x = 0;
                GridPoint p = level.getPlayer().getPosition();
                level.getPlayer()
                        .setPosition(level.getLocation().getGrid().points[p.x + (b ? 1 : -1)][p.y]);
                Gdx.app.debug(TAG, "update: Finish step on oX! Now position is " +
                        level.getPlayer().getPosition());
            }

        } else {
            boolean b = pos1.y > pos0.y;
            level.getPlayer().getDeltaPos().y += (b ? 1 : -1) * delta;
            level.getPlayer().setDirection(b ? Mob.Direction.UP : Mob.Direction.DOWN);
            float v = level.getPlayer().getPosition().y + level.getPlayer().getDeltaPos().y;
            if ((b && v >= pos1.y) || (!b && v <= pos1.y)){
                pos0 = pos1;
                path.getPath().pop();
                level.getPlayer().getDeltaPos().y = 0;
                GridPoint p = level.getPlayer().getPosition();
                level.getPlayer()
                        .setPosition(level.getLocation().getGrid().points[p.x][p.y + (b ? 1 : -1)]);
                Gdx.app.debug(TAG, "update: Finish step on oY! Now position is " +
                        level.getPlayer().getPosition());
            }
        }
        if (path.getPath().isEmpty()) {
            level.getPlayer().setState(Mob.State.STAY);
            setDirection(pos0, finish);
        }
    }

    private void setDirection(GridPoint from, GridPoint to){
        if (from.x != to.x){
            boolean b = from.x < to.x;
            level.getPlayer().setDirection(b ? Mob.Direction.RIGHT : Mob.Direction.LEFT);
        } else {
            boolean b = from.y < to.y;
            level.getPlayer().setDirection(b ? Mob.Direction.UP : Mob.Direction.DOWN);
        }
    }

}
