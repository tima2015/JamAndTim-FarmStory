package ru.spruceteam.jtfs.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spruceteam.jtfs.Command;
import ru.spruceteam.jtfs.Core;
import ru.spruceteam.jtfs.LevelScreen;
import ru.spruceteam.jtfs.levels.Level;
import ru.spruceteam.jtfs.levels.world.location.GridPoint;
import ru.spruceteam.jtfs.ui.DialogScreenFactory;

public class Bed extends GameObject{

    public Bed(GridPoint pos) {
        super("bed", pos);
        commands.add(new OpenSaveMenuCommand());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

    }

    private static class OpenSaveMenuCommand implements Command{

        @Override
        public void execute() {
            Screen screen = (LevelScreen) Core.getCore().getScreen();
            Core.getCore().setScreen(DialogScreenFactory.newSaveScreen(screen));
        }
    }
}
