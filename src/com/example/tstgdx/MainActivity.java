package com.example.tstgdx;

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.example.tstgdx.tools.DeviceID;
import com.testin.agent.TestinAgent;
import com.umeng.analytics.game.UMGameAgent;

public class MainActivity extends AndroidApplication {
	
	private MainGame game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        UMGameAgent.setDebugMode(true);//设置输出运行时日志
        UMGameAgent.init( this );
        
        TestinAgent.init(this, "6d298aaabb2a960edd3ddb41afdf0fa9", "test");
        
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useWakelock = true;
		config.useGLSurfaceView20API18 = true;
		game = new MainGame(this);
        initialize(game,config);
        
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        DeviceID.strDeviceID = tm.getDeviceId(); 
        Log.i("DEVICE", DeviceID.strDeviceID);
    }    
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
    		if(keyCode == KeyEvent.KEYCODE_BACK)
    		{
    			if( game.ExitGame() ) {
    				game.dispose();
        			finish();
        			return true;
    			}
    			return false;
    		}
    		return super.onKeyDown(keyCode, event);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        UMGameAgent.onResume(this);
    }
    
    @Override
    public void onPause() {
        super.onPause();
        UMGameAgent.onPause(this);
    }
}

