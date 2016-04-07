package com.example.tstgdx;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Item {
	
	private Image mItemImage;
	private Item mPrev;
	private Item mNext;
	private int mIndex;
	
	public Item(Stage stage,TextureAtlas atlas,String strFilename,int index) {
		mItemImage = new Image(atlas.findRegion(strFilename));
		mItemImage.setName(strFilename);
		mIndex = index;
		
		stage.addActor(mItemImage);
	}
	
	public void setNext(Item next){
		mNext = next;
	}
	
	public Item getNext(){
		return mNext;
	}
	
	public void setPrev(Item prev){
		mPrev = prev;
	}
	
	public Item getPrev(){
		return mPrev;
	}
	
	public int getIndex() {
		return mIndex;
	}
	
	public void setPosition(float x,float y){
		mItemImage.setPosition(x, y);
	}
	
	public void fadeOut(float time){
		Action fadeto = Actions.sequence(Actions.alpha(1.0f), Actions.fadeOut(time));
		mItemImage.addAction(fadeto);
	}
	
	public void fadeIn(float time){
		Action fadeto = Actions.sequence(Actions.alpha(0.0f), Actions.fadeIn(time));
		mItemImage.addAction(fadeto);
	}
	
	public String getName() {
		return mItemImage.getName();
	}
}
