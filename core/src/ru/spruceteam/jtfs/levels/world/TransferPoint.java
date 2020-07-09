package ru.spruceteam.jtfs.levels.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import ru.spruceteam.jtfs.levels.world.location.GridPoint;
import ru.spruceteam.jtfs.objects.GameObject;

public class TransferPoint extends GameObject implements Disposable {
    public final String target;
    public final GridPoint playerOnTargetPosition;

    private Animation<TextureRegion> animation;
    private Texture texture;//TOdO normal texture
    private final Sprite sprite = new Sprite();

    public TransferPoint(GridPoint pos, String target, GridPoint playerOnTargetPosition) {
        super("transferPoint",pos);
        this.target = target;
        this.playerOnTargetPosition = playerOnTargetPosition;

        Pixmap pixmap = new Pixmap(128, 128, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.CYAN);
        for (int x = 12; x < 128; x+= 24)
            for (int y = 12; y < 128; y+= 24) {
                pixmap.fillCircle(x,y,8);
            }
        texture = new Texture(pixmap);
        sprite.setTexture(texture);
        sprite.setSize(1,1);
        sprite.setPosition(pos.x, pos.y);
        pixmap.dispose();
    }

    private TransferPoint(){
        this(new GridPoint(-1,-1),"maps/home_house.tmx", new GridPoint(12,12));
    }

    public static final TransferPoint SPAWN_TRANSFER = new TransferPoint();

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //sprite.draw(batch,parentAlpha);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
