package com.example.tstgdx.state;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.example.tstgdx.Item;
import com.example.tstgdx.Roll;
import com.example.tstgdx.data.RollData;

public class StateGoUpdateTrain extends TaskNode {
	protected StateGo mState;
	private Stack<Item> mTrainStack;
	protected int mStep;
	protected float mTime;
	
	public StateGoUpdateTrain(StateGo state) {
		nType = (int)RollData.eResultType_Train;
		mState = state;
		mTrainStack = new Stack<Item>();
		mStep = 0;
		mTime = 0;
	}
	
	public void enter() {
		mTrainStack.clear();
		Item item = mState.getItem();
		for(int i=0;i<mState.getRollData().iTarget[1];++i) {
			mTrainStack.push(item);
			item.fadeIn(0.0f);
			item = item.getPrev();
		}
		
		mStep = Roll.calculateSetpRight(mState.getItem().getIndex(), mState.getRollData().iTarget[0]);
	}
	
	public Boolean update() {
		if(mStep>0) {
			mTime += Gdx.graphics.getDeltaTime();
			float time = mStep<=StateGo.mSetpLimit ? StateGo.mStepTime : StateGo.mStepTimeFast;
			if(mTime > time) {
				mTime -= time;
				mStep -= 1;
				Item endItem = mTrainStack.pop();
				endItem.fadeOut(StateGo.mItemLife);
				
				Item firstItem = mState.getItem().getNext();
				mState.setItem(firstItem);
				mTrainStack.add(1, firstItem);
				firstItem.fadeIn(0.0f);
			}
			return true;
		} else {
			return false;
		}
	}
	
	public void exit() {
		
	}
}
