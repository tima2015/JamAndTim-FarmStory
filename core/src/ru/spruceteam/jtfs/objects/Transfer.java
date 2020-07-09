package ru.spruceteam.jtfs.objects;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

import ru.spruceteam.jtfs.Core;
import ru.spruceteam.jtfs.LevelScreen;
import ru.spruceteam.jtfs.levels.world.location.GridPoint;
import ru.spruceteam.jtfs.ui.DialogScreenFactory;

public class Transfer extends Actor implements Disposable {
    private final GridPoint pos;//TODO texture

    private Animation<TextureRegion> animation;
    private Texture texture;
    private final Sprite sprite = new Sprite();

    public Transfer(GridPoint pos){
        this.pos = pos;
        setPosition(pos.x, pos.y);
        setSize(1,1);
        Pixmap pixmap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.CYAN);
        for (int i = 4; i < 16; i+= 4) {
            pixmap.drawCircle(16,16,i);
        }
        texture = new Texture(pixmap);
        sprite.setTexture(texture);
        pixmap.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
       sprite.draw(batch,parentAlpha);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
