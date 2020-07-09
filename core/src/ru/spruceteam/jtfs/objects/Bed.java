package ru.spruceteam.jtfs.objects;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

import ru.spruceteam.jtfs.Core;
import ru.spruceteam.jtfs.LevelScreen;
import ru.spruceteam.jtfs.levels.world.location.GridPoint;
import ru.spruceteam.jtfs.ui.DialogScreenFactory;

public class Bed extends GameObject{

    public Bed(GridPoint pos) {
        super("bed", pos);
        addListener(new OpenSaveMenuListener());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

    }

    private static class OpenSaveMenuListener implements EventListener {

        @Override
        public boolean handle(Event event) {
            if (event instanceof GameObject.PlayerExecuteObjectTaskEvent) {
                Screen screen = (LevelScreen) Core.getCore().getScreen();
                Core.getCore().setScreen(DialogScreenFactory.newSaveScreen(screen));
                event.stop();
                return true;
            }
            return false;
        }
    }
}
