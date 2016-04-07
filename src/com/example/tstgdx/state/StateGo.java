package com.example.tstgdx.state;

import java.util.Stack;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.example.tstgdx.Api;
import com.example.tstgdx.Item;
import com.example.tstgdx.MainScreen;
import com.example.tstgdx.Roll;
import com.example.tstgdx.data.RollData;
import com.example.tstgdx.data.UserData;
import com.example.tstgdx.packet.RandFruit;
import com.example.tstgdx.tools.DeviceID;
import com.example.tstgdx.tools.GsonUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class StateGo extends StateNode {
	
	static float mStepTimeFast = 0.03f;
	static float mStepTime = 0.08f;
	static float mItemLife = 0.4f;
	static float mSetpLimit = 8;
	static float mFlashTime = 1.0f;
	static float mFlashTimeTick = 0.04f;
	
	static final int GoStateRandom = 1; //预测
	static final int GoStateReturn = 2; //实际
	static final int GoStateOver = 3; //结束
	
	private MainScreen mScreen;
	private State mState;
	private RollData mRollData;
	
	private int iGoState;
	private int iStep;
	private Item mItem;
	private float mTime;
	private Boolean anniuNumber[] = new Boolean[State.numberSize];
	
	private TaskNode mTask;
	private Stack<Item> mStack;
	
	HttpUtils mHttpUtils = null;
	HttpHandler<String> mHandler = null;
	
	public StateGo(MainScreen scn) {
		mScreen = scn;
		mState = mScreen.mState;
		mRollData = RollData.getInstance();
		setType(StateNode.StateNodeType_Go);
		
		mStack = new Stack<Item>();
	}
	
	public RollData getRollData() {
		return mRollData;
	}
	
	public Item getItem() {
		return mItem;
	}
	
	public void setItem(Item item) {
		mItem = item;
	}
	
	public void addBright(Item item) {
		mStack.push(item);
	}
	
	public Boolean findBright(Item item) {
        for (Item i : mStack) {
        		if( i.getIndex() == item.getIndex() )
        			return true;
        }
        return false;
	}
	
	@Override
	public void enter() {
		for(int i=0;i<State.numberSize;++i){
			mScreen.anniuNumber[i].setDisabled(true);
		}
		
		mScreen.anniuAll.setDisabled(true);
		mScreen.anniuZuoJian.setDisabled(true);
		mScreen.anniuYouJian.setDisabled(true);
		mScreen.anniu1_7.setDisabled(true);
		mScreen.anniu8_14.setDisabled(true);
		mScreen.anniuGo.setDisabled(true);
		
		iGoState = GoStateRandom;
		iStep = Roll.calculateRandomSetp();
		
		//如果之前的线程没有完成
		if (mHandler != null && mHandler.getState() != HttpHandler.State.FAILURE
				&& mHandler.getState() != HttpHandler.State.SUCCESS
				&& mHandler.getState() != HttpHandler.State.CANCELLED) {
			// 关闭handler后 onStart()和onLoading()还是会执行
			mHandler.cancel();
			mHandler = null;
		}
		
		mHttpUtils = new HttpUtils();
		// 设置当前请求的缓存时间
		mHttpUtils.configCurrentHttpCacheExpiry(0 * 1000);
		// 设置默认请求的缓存时间
		mHttpUtils.configDefaultHttpCacheExpiry(0);
		// 设置线程数
		mHttpUtils.configRequestThreadPoolSize(10);
		mHttpUtils.configResponseTextCharset("utf-8");
		
		//请求数据
		String url = Api.HTTP_GET_RAND_FRUIT+"?id="+DeviceID.strDeviceID;
		mHandler = mHttpUtils.send(HttpRequest.HttpMethod.GET, url,
				new RequestCallBack<String>() {
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						//成功获取数据
						Log.i("NET", responseInfo.result);
						RandFruit rt = GsonUtil.jsonToBean(responseInfo.result, RandFruit.class);
						if(rt.code == 0) {
							mRollData.bInit = true;
							mRollData.iType = rt.type;
							for(int k=0;k<rt.data.size();++k) {
								mRollData.iTarget[k] = rt.data.get(k);
							}
							mRollData.iWin = 10; //temp应该由服务器计算
						} else {
							//错误处理
							Log.e("NET","return code error. code : " + rt.code);
							onError();
						}
					}

					@Override
					public void onStart() {

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						//错误处理
						Log.e("NET",msg);
						onError();
					}
				});
		
		for(int i=0;i<State.numberSize;++i){
			anniuNumber[i] = false;
		}
		
		//清理之前的亮灯
		while(mStack.empty()==false) {
			Item item = mStack.pop();
			item.fadeOut(0.0f);
		}
		//定位首灯
		mItem = mScreen.findItem(UserData.getInstance().iPosition);
		mItem.fadeIn(0.0f);
    		mTime = 0.0f;
    	
    		mState.backupAllNumber();
	}
	
	private void onError() {
		
	}
	
	@Override 
	public void update() {
		switch(iGoState) {
		case GoStateRandom:
			if(iStep > 0) {
				mTime += Gdx.graphics.getDeltaTime();
	        		if( mTime > mStepTimeFast ) {
	        			mTime -= mStepTimeFast;
	        			iStep -= 1;
	        			mItem.fadeOut(mItemLife);
	        			mItem = mItem.getNext();
	        		}
	        } else {
	        		if(mTask==null) {
	        			if(mRollData.bInit==true) {
	        				switch(mRollData.iType) {
	        				case RollData.eResultType_Standard:
	        				{
	        					mTask = new StateGoUpdateStandard(this,0,true);
	        					mTask.enter();
	        				}
	        				break;
	        				case RollData.eResultType_Giving:
	        				{
	        					mTask = new StateGoUpdateGiving(this);
	        					mTask.enter();
	        				}
	        				break;
	        				case RollData.eResultType_Train:
	        				{
	        					mTask = new StateGoUpdateTrain(this);
	        					mTask.enter();
	        				}
	        				break;
	        				//temp
	        				case RollData.eResultType_SiTongBaDa:
	        				{
	        					mTask = new StateGoUpdateStandard(this,0,true);
	        					mTask.enter();
	        				}
	        				break;
	        				}
	        			}
	        		} else {
	        			if( mTask.update() == false ) {
	        				iGoState = GoStateReturn;
	        				mTask.exit();
	        				mTask = null;
	        			}
	        		}
	        }
			break;
		case GoStateReturn:
			iGoState = GoStateOver;
			
			mScreen.anniuZuoJian.setDisabled(false);
    			mScreen.anniuYouJian.setDisabled(false);
    			mScreen.anniu1_7.setDisabled(false);
    			mScreen.anniu8_14.setDisabled(false);
    			mScreen.anniuGo.setDisabled(false);
    			
    			for(int i=0;i<State.numberSize;++i) {
    				if(anniuNumber[i]==false){
    					mScreen.number[i].clear();
    				}
    			}
            	mState.numberWin = mRollData.iWin;
            	mState.backupNumberWin();
            	if(mState.numberWin == 0) {
        			mScreen.numberWin.clear();
			} else {
        			mScreen.numberWin.setNumber(mState.numberWin);
        		}
            	UserData.getInstance().iPosition = mItem.getIndex();
			break;
		case GoStateOver:
			if(mState.numberWin == 0) {
        			getMgr().nextState(StateNode.StateNodeType_Idle);
			}
			break;
		}
	}
	
	@Override
	public void exit() {
		if(mState.numberWin > 0) {
			mState.numberCredit += mState.numberWin;
			mScreen.numberCredit.setNumber(mState.numberCredit);
		}
		
		mState.clearAllNumber();
		mScreen.resetAllNumber();
	}
	
	public int calculateCoin(int idx) {
		if(idx==-1) {
			return 0;
		}

		Item itm = mScreen.findItem(idx);
		String name = itm.getName();
		int mul = mState.mapKeyMultiple.get(name);
		int win = 0;
    		int index = 0;
    		if(name.equals("pingguo") || name.equals("pingguoX3") ) {
    			index = State.numberPingGuo;
    		}
    		else if(name.equals("juzi") || name.equals("juziX3")) {
    			index = State.numberJuZi;
    		}
    		else if(name.equals("mangguo") || name.equals("mangguoX3")) {
    			index = State.numberMangGuo;
    		}
    		else if(name.equals("tongling") || name.equals("tonglingX3")) {
    			index = State.numberTongLing;
    		}
    		else if(name.equals("xigua") || name.equals("xiguaX3")) {
    			index = State.numberXiGua;
    		}
    		else if(name.equals("xingxing") || name.equals("xingxingX3")) {
    			index = State.numberXingXing;
    		}
    		else if(name.equals("77") || name.equals("77X3")) {
    			index = State.number77;
    		}
    		else if(name.equals("barX50") || name.equals("barX100")) {
    			index = State.numberBar;
    		} else {
    			return win;
    		}
    		anniuNumber[index] = true;
    		win = mState.number[index] * mul;
    		return win;
	}
}
