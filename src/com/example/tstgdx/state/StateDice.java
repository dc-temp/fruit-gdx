package com.example.tstgdx.state;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.example.tstgdx.MainGame;
import com.example.tstgdx.MainScreen;
import com.example.tstgdx.data.DiceData;

public class StateDice extends StateNode {
	
	static final float fTimeAll = 2;
	static final float fTimeUpdate= 0.1f;
	
	static final int DiceStateAnim = 1;
	static final int DiceStateOver = 2;
	
	private MainScreen mScreen;
	private State mState;
	private ArrayList<Integer> mLst;
	private DiceData mDiceData;
	private int mDiceState;
	private float mTime;
	private float mTimeAll;
	
	public Boolean bBig;
	
	public StateDice(MainScreen scn) {
		mScreen = scn;
		mState = mScreen.mState;
		setType(StateNode.StateNodeType_Dice);
		
		mLst = new ArrayList<Integer>();
		mDiceData = DiceData.getInstance();
	}
	
	@Override
	public void enter() {
		//设置按钮状态
		for(int i=0;i<State.numberSize;++i){
			mScreen.anniuNumber[i].setDisabled(true);
		}
		
		mScreen.anniuAll.setDisabled(true);
		mScreen.anniuZuoJian.setDisabled(true);
		mScreen.anniuYouJian.setDisabled(true);
		mScreen.anniu1_7.setDisabled(true);
		mScreen.anniu8_14.setDisabled(true);
		mScreen.anniuGo.setDisabled(true);
		
		reset();
	}
	
	@Override 
	public void update() {
		switch(mDiceState) {
		case DiceStateAnim:
			float dt = Gdx.graphics.getDeltaTime();
			mTime += dt;
			mTimeAll += dt;
			if( mTime > fTimeUpdate ) {
				mTime -= fTimeUpdate;
				mScreen.numberDice.setNumber((int)(Math.random() * 13 + 1));
			}
			if( mTimeAll > fTimeAll ) {
				mDiceState = DiceStateOver;
				
				mState.numberWin = mDiceData.iWin;
				MainGame.getdbHelper().saveNumber(mDiceData.iNumber);
				mScreen.numberDice.setNumber(mDiceData.iNumber);
				mScreen.numberWin.setNumber(mDiceData.iWin);
				if(mDiceData.iWin>0) {
					mScreen.anniuZuoJian.setDisabled(false);
					mScreen.anniuYouJian.setDisabled(false);
					mScreen.anniu1_7.setDisabled(false);
					mScreen.anniu8_14.setDisabled(false);
					mScreen.anniuGo.setDisabled(false);
				}
				
				mState.backupNumberWin();
			}
			break;
		case DiceStateOver:
			if(mDiceData.iWin==0) {
				getMgr().nextState(StateNode.StateNodeType_Idle);
			}
			break;
		}
	}
	
	@Override
	public void exit() {
		if(mDiceData.iWin > 0) {
			mState.numberCredit += mDiceData.iWin;
			mScreen.numberCredit.setNumber(mState.numberCredit);
		}
		
		mState.clearAllNumber();
		mScreen.resetAllNumber();
		mScreen.numberWin.reset();
	}
	
	public void reset() {
		mDiceState = DiceStateAnim;
		mTime = fTimeUpdate;
		mTimeAll = 0;
		
		//显示猜大小记录
		showLog();
		
		{//temp
			mDiceData.iNumber = (int)(Math.random() * 13 + 1);
			if(mDiceData.iNumber>=1 && mDiceData.iNumber<=6) {
				if(bBig){
					mDiceData.iWin = 0;
				} else {
					mDiceData.iWin = mState.numberWin * 2;
				}
			} else if(mDiceData.iNumber>=8 && mDiceData.iNumber<=13) {
				if(bBig){
					mDiceData.iWin = mState.numberWin * 2;
				} else {
					mDiceData.iWin = 0;
				}
			} else {
				mDiceData.iWin = 0;
			}
		}
	}
	
	public void showLog() {
		mLst.clear();
		MainGame.getdbHelper().getNumberList(mLst);
		mScreen.clearAllNumber();
		int index = 0;
		for(int i=0;i<State.numberSize;++i){
			if(mLst.size()>index) {
				mScreen.number[i].setNumber(mLst.get(index));
				index++;
			}
		}
	}
}
