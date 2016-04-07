package com.example.tstgdx;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.example.tstgdx.tools.DatabaseHelper;

public class MainGame extends Game {
	
	public MainActivity activity;
	
	static AssetManager manager;
	static DatabaseHelper dbHelper;
	
	public MainGame(MainActivity act) {
		activity = act;
		dbHelper = new DatabaseHelper(activity, "data_db");
	}
	
	@Override
    public void create() {
//		MainScreen screen = new MainScreen(this);
//		TestScreen screen = new TestScreen(this);
		MainLogin screen = new MainLogin(this);
        this.setScreen(screen);
    }

    public static AssetManager getManager() {
        if (manager == null) {
            manager = new AssetManager();
        }
        return manager;
    }
    
    public static DatabaseHelper getdbHelper() {
    		return dbHelper;
    }
    
    public Boolean ExitGame() {
    		return true;
    }
}

