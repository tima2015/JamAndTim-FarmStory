package ru.spruceteam.jtfs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Utils {
    public static Texture getScreenshot(){
        Texture texture = new Texture(Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        Gdx.gl.glEnable(GL20.GL_TEXTURE_2D);
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
        texture.bind();
        Gdx.gl.glCopyTexImage2D(GL20.GL_TEXTURE_2D, 0, GL20.GL_RGB,0, 0,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight(),
                0);
        Gdx.gl.glDisable(GL20.GL_TEXTURE_2D);
        return texture;
    }
}
