package com.example.tstgdx.state;

import com.badlogic.gdx.Gdx;
import com.example.tstgdx.data.RollData;

public class StateGoUpdateGiving extends TaskNode {
	protected StateGo mState;
	protected TaskNode mNode;
	protected int mIndex;
	protected float mTime;
	protected float mTimeMax;
	protected Boolean mBright;
	
	public StateGoUpdateGiving(StateGo state) {
		nType = (int)RollData.eResultType_Giving;
		mState = state;
		mIndex= 0;
		mTime = 0;
		mTimeMax = 0;
		mBright = true;
	}
	
	public void enter() {
		mNode = new StateGoUpdateStandard(mState, mIndex, true);
		mNode.enter();
		mIndex += 1;
	}
	
	public Boolean update() {
		if(mTimeMax>0) {
			mTime += Gdx.graphics.getDeltaTime();
			if(mTime > StateGo.mFlashTimeTick) {
				if(mBright) {
					mState.getItem().fadeOut(0.0f);
				} else {
					mState.getItem().fadeIn(0.0f);
				}
				
				mTimeMax -= StateGo.mFlashTimeTick;
				if(mTimeMax<=0) {
					mState.getItem().fadeIn(0.0f);
				}
				mTime = 0;
			}
			return true;
		}
		
		if(mNode!=null) {
			if(mNode.update()==false) {
				if(mIndex<5 && mState.getRollData().iTarget[mIndex] != -1) {
					mNode.exit();
					mNode = null;
					
					if(mIndex%2 == 1) {
						mNode = new StateGoUpdateStandard(mState, mIndex, false);
					} else {
						mNode = new StateGoUpdateStandard(mState, mIndex, true);
					}
					mNode.enter();
					mIndex += 1;
					
					mTimeMax = StateGo.mFlashTime;
					mTime = 0;
					mBright = true;
					
					return true;
				} else {
					mNode.exit();
					mNode = null;
					return false;
				}
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
	
	public void exit() {
		
	}
}
