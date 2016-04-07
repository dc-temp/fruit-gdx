package com.example.tstgdx.state;

import com.example.tstgdx.MainScreen;

public class StateIdle extends StateNode {
	
	private MainScreen mScreen;
	private State mState;
	
	public StateIdle(MainScreen scn) {
		mScreen = scn;
		mState = mScreen.mState;
		setType(StateNode.StateNodeType_Idle);
	}
	
	@Override
	public void enter() {
		mScreen.resetAllNumber();
		mScreen.numberWin.reset();
		
		for(int i=0;i<State.numberSize;++i){
			mScreen.anniuNumber[i].setDisabled(false);
		}
		
		mScreen.anniuAll.setDisabled(false);
		mScreen.anniuZuoJian.setDisabled(true);
		mScreen.anniuYouJian.setDisabled(true);
		mScreen.anniu1_7.setDisabled(true);
		mScreen.anniu8_14.setDisabled(true);
		mScreen.anniuGo.setDisabled(true);
	}
	
	@Override 
	public void update() {
		if( mState.getAllNumber() > 0 ) {
			mScreen.anniuGo.setDisabled(false);
		} else if( mState.getAllNumberCopy() > 0 && mState.getAllNumberCopy() <= mState.numberCredit ) {
			mScreen.anniuGo.setDisabled(false);
		}
	}
	
	@Override
	public void exit() {
		
	}
}
