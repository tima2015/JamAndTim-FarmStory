package ru.spruceteam.jtfs.desktop;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class TextureProcessor {
    public static void packMapTiles(){
        TexturePacker.Settings settings = new TexturePacker.Settings();
        //settings.duplicatePadding = true;settings.edgePadding = true;
        settings.paddingX = settings.paddingY = 1;
        settings.fast = false;
        settings.debug = false;
        settings.maxWidth = settings.maxHeight = 1024;
        settings.format = Pixmap.Format.RGBA8888;
        settings.minWidth = settings.minHeight = 1024;
        settings.grid = true;

        settings.rotation = false;
        TexturePacker.process(settings, "C:\\Jam&Tim - Farm story\\textures\\tiles","C:\\Jam&Tim - Farm story\\android\\assets\\maps\\tiles", "tiles");
    }

    public static void main(String[] args) {
        packMapTiles();
    }
}
