package ru.spruceteam.jtfs.mob.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.Stack;

import ru.spruceteam.jtfs.levels.Level;
import ru.spruceteam.jtfs.levels.world.location.GridPoint;
import ru.spruceteam.jtfs.mob.Mob;
import ru.spruceteam.jtfs.mob.MobPath;

public class MoveAction extends TemporalAction {

    private static final String TAG = "MoveAction";

    private final Level level;
    private final Stack<GridPoint> path = new Stack<>();

    private GridPoint from = new GridPoint(-1,-1);
    private GridPoint to = new GridPoint(-1,-1);

    private final ActionClickListener clickListener = new ActionClickListener();

    MoveAction(Level level) {
        super(10000000);// FIXME: 08.07.2020 I dont know what I must to do with it =(
        this.level = level;
        finish();
    }

    @Override
    protected void begin() {
        Gdx.app.debug(TAG, "begin() called");
        level.getPlayer().setState(Mob.State.WALK);
    }

    @Override
    protected void end() {
        Gdx.app.debug(TAG, "end() called");
        level.getPlayer().setState(Mob.State.STAY);
        if (to.getObject() != null) to.getObject().executeCommands();
        setDirection();
    }

    @Override
    protected void update(float percent) {
        if (path.isEmpty()) {
            finish();
            return;
        }
        GridPoint pos1 = path.peek();
        if (pos1.x != from.x) {
            boolean b = pos1.x > from.x;
            level.getPlayer().getDeltaPos().x += (b ? 1 : -1) * Gdx.graphics.getDeltaTime();
            level.getPlayer().setDirection(b ? Mob.Direction.RIGHT : Mob.Direction.LEFT);
            float v = level.getPlayer().getPosition().x + level.getPlayer().getDeltaPos().x;
            if ((b && v >= pos1.x) || (!b && v <= pos1.x)) {
                from = pos1;
                path.pop();
                level.getPlayer().getDeltaPos().x = 0;
                GridPoint p = level.getPlayer().getPosition();
                level.getPlayer()
                        .setPosition(level.getLocation().getGrid().points[p.x + (b ? 1 : -1)][p.y]);
                Gdx.app.debug(TAG, "update: Finish step on oX! Now position is " +
                        level.getPlayer().getPosition());
            }

        } else {
            boolean b = pos1.y > from.y;
            level.getPlayer().getDeltaPos().y += (b ? 1 : -1) * Gdx.graphics.getDeltaTime();
            level.getPlayer().setDirection(b ? Mob.Direction.UP : Mob.Direction.DOWN);
            float v = level.getPlayer().getPosition().y + level.getPlayer().getDeltaPos().y;
            if ((b && v >= pos1.y) || (!b && v <= pos1.y)) {
                from = pos1;
                path.pop();
                level.getPlayer().getDeltaPos().y = 0;
                GridPoint p = level.getPlayer().getPosition();
                level.getPlayer()
                        .setPosition(level.getLocation().getGrid().points[p.x][p.y + (b ? 1 : -1)]);
                Gdx.app.debug(TAG, "update: Finish step on oY! Now position is " +
                        level.getPlayer().getPosition());
            }
        }
    }

    private void setDirection() {
        if (from.x != to.x) {
            boolean b = from.x < to.x;
            level.getPlayer().setDirection(b ? Mob.Direction.RIGHT : Mob.Direction.LEFT);
        } else {
            boolean b = from.y < to.y;
            level.getPlayer().setDirection(b ? Mob.Direction.UP : Mob.Direction.DOWN);
        }
    }

    ActionClickListener getClickListener() {
        return clickListener;
    }

    public Stack<GridPoint> getPath() {
        return path;
    }

    public class ActionClickListener extends ClickListener {

        private ActionClickListener(){}

        private static final String TAG = "ActionClickListener";

        @Override
        public void clicked(InputEvent event, float x, float y) {
            Gdx.app.debug(TAG, "clicked() called with: x = [" + x + "], y = [" + y + "]");
            to = level.getLocation().getGrid().points[(int) x][(int) y];
            from = level.getPlayer().getPosition();
            path.clear();
            path.addAll(new MobPath(from, to, level.getLocation().getGrid()).getPath());
            restart();
            level.getPlayer().addAction(MoveAction.this);
        }
    }
}
