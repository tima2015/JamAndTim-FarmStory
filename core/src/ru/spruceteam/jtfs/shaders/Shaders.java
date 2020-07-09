package ru.spruceteam.jtfs.shaders;

import com.badlogic.gdx.utils.Disposable;

public class Shaders implements Disposable {
    public final ShadeShader shade = new ShadeShader();


    @Override
    public void dispose() {
        shade.dispose();
    }
}
