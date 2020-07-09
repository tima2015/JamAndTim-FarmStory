package ru.spruceteam.jtfs.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import java.util.Arrays;

public class ShadeShader extends ShaderProgram {

    private static final String TAG = "ShadeShader";

    public ShadeShader() {
        super(Gdx.files.internal("shaders/shade.vert"),
                Gdx.files.internal("shaders/shade.frag"));
        if (!isCompiled()) throw new RuntimeException("Shader complation failed:\n" + getLog());
    }

    public void setShade(float shade){
        setAttributef("a_alpha", shade, shade, shade,shade);
    }
}
