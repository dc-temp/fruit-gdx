package com.example.tstgdx.state;

import com.badlogic.gdx.Gdx;
import com.example.tstgdx.Item;
import com.example.tstgdx.Roll;
import com.example.tstgdx.data.RollData;

public class StateGoUpdateStandard extends TaskNode {
	protected StateGo mState;
	protected int mStep;
	protected float mTime;
	protected int mTargetIndex;
	protected Boolean mRight;
	
	public StateGoUpdateStandard(StateGo state,int index,Boolean bRight) {
		nType = (int)RollData.eResultType_Standard;
		mState = state;
		mStep = 0;
		mTime = 0;
		mTargetIndex = index;
		mRight = bRight;
	}
	
	public void enter() {
		if(mRight) {
			mStep = Roll.calculateSetpRight(mState.getItem().getIndex(), mState.getRollData().iTarget[mTargetIndex]);
		} else {
			mStep = Roll.calculateSetpLeft(mState.getItem().getIndex(), mState.getRollData().iTarget[mTargetIndex]);
		}
	}
	
	public Boolean update() {
		if(mStep>0) {
			mTime += Gdx.graphics.getDeltaTime();
			float time = mStep<=StateGo.mSetpLimit ? StateGo.mStepTime : StateGo.mStepTimeFast;
			if(mTime > time) {
				mTime -= time;
				mStep -= 1;
				Item item = mState.getItem();
				if(mState.findBright(item)==false) {
					item.fadeOut(StateGo.mItemLife);
				}
				if(mRight) {
					mState.setItem(mState.getItem().getNext());
				} else {
					mState.setItem(mState.getItem().getPrev());
				}
			}
			return true;
		} else {
			mState.getItem().fadeIn(0.0f);
			mState.addBright(mState.getItem());
			return false;
		}
	}
	
	public void exit() {
		
	}
}

