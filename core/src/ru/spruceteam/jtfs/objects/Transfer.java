package ru.spruceteam.jtfs.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

import ru.spruceteam.jtfs.Drawable;

public class Transfer implements Drawable, Disposable {

    private final boolean externalTexture;
    private Animation<TextureRegion> animation;
    private Texture texture;

    @Deprecated
    public Transfer(TextureAtlas atlas){
        externalTexture = true;//TODO
    }

    public Transfer(){
        externalTexture = false;
        Pixmap pixmap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.CYAN);
        for (int i = 4; i < 16; i+= 4) {
            pixmap.drawCircle(16,16,i);
        }
        texture = new Texture(pixmap);
        pixmap.dispose();
    }



    @Override
    public void draw(Batch batch, float x, float y) {
        if (!externalTexture)
            batch.draw(texture, x, y, 1 ,1);
    }

    @Override
    public void dispose() {
        if (!externalTexture)
            texture.dispose();
    }
}
