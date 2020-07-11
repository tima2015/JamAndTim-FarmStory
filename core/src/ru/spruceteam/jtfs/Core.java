package ru.spruceteam.jtfs;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ru.spruceteam.jtfs.levels.Level;
import ru.spruceteam.jtfs.levels.LevelBuilder;
import ru.spruceteam.jtfs.levels.world.location.Location;
import ru.spruceteam.jtfs.levels.world.TransferPoint;
import ru.spruceteam.jtfs.mob.player.Player;
import ru.spruceteam.jtfs.shaders.Shaders;

public class Core extends Game {

	private TextureAtlas tempAtlas;

	public static Core getCore(){
		return (Core) Gdx.app.getApplicationListener();
	}

	public final AssetManager manager = new AssetManager();
	public final Skin uiSkin = new Skin();
	private Shaders shaders;
	public BitmapFont font = null;

	public Shaders getShaders() {
		return shaders;
	}

	public BitmapFont getFont() {
		return font;
	}

	public Core() {
		manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
	}

	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		Level level = new Level();
		level.setLocation(new Location(TransferPoint.SPAWN_TRANSFER, level));
		level.setPlayer(new Player(Player.JAM, level));
		LevelScreen screen = new LevelScreen(level);
		setScreen(screen);
		//TODO remove
		tempAtlas = new TextureAtlas("debug/uiskin.atlas");
		uiSkin.addRegions(tempAtlas);
		uiSkin.load(Gdx.files.internal("debug/uiskin.json"));
		shaders = new Shaders();
		font = new BitmapFont();
	}

	@Override
	public void render () {
		//Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA,GL20.GL_SRC_ALPHA);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
		if (Gdx.input.isKeyJustPressed(Input.Keys.DEL))
			Gdx.app.exit();
	}


	@Override
	public void dispose() {
		super.dispose();
		manager.dispose();
		tempAtlas.dispose();
		uiSkin.dispose();
		shaders.dispose();
		font.dispose();
	}
}
