package ru.spruceteam.jtfs;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.utils.Array;

public class AssetManager extends com.badlogic.gdx.assets.AssetManager {
    private Array<OnLoadFinishListener> loadFinishListenerArray = new Array<>();

    public void addOnLoadFinishListener(OnLoadFinishListener listener){
        loadFinishListenerArray.add(listener);
    }

    public void removeOnLoadFinishListener(OnLoadFinishListener listener){
        loadFinishListenerArray.removeValue(listener,true);
    }

    public AssetManager() {
        super();
    }

    public AssetManager(FileHandleResolver resolver) {
        super(resolver);
    }

    public AssetManager(FileHandleResolver resolver, boolean defaultLoaders) {
        super(resolver, defaultLoaders);
    }

    @Override
    public synchronized boolean update() {
        boolean finish = super.update();
        if (finish)
            for (int i = 0; i < loadFinishListenerArray.size; i++)
                loadFinishListenerArray.get(i).onLoadFinish(this);
        return finish;
    }

    public interface OnLoadFinishListener{
        void onLoadFinish(AssetManager manager);
    }
}
