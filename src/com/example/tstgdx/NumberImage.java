package com.example.tstgdx;

import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class NumberImage {
	
	static public int ImageWidth = 22;
	static public int ImageHeight = 34;
	static public int ImageSmallWidth = 18;
	static public int ImageSmallHeight = 30;
	
	static public Image createNum(int n) {
		AssetManager asset = MainGame.getManager();
		Texture tex = asset.get("480/shuzi.png");
		Image img = null;
		if(n>=0 && n<10) {
			TextureRegion reg = new TextureRegion(tex,ImageWidth*n,0,ImageWidth,ImageHeight);
	    	img = new Image(reg);	
		}
    	return img;
	}
	
	static public Image createNumSmall(int n) {
		AssetManager asset = MainGame.getManager();
		Texture tex = asset.get("480/xiaoshuzi.png");
		Image img = null;
		if(n>=0 && n<10) {
			TextureRegion reg = new TextureRegion(tex,ImageSmallWidth*n,0,ImageSmallWidth,ImageSmallHeight);
	    	img = new Image(reg);	
		}
    	return img;
	}
	
	private Stage mStage;
	
	private int mNumber;
	
	private Boolean mSmall;
	private int mSize;
	private int mX;
	private int mY;
	
	private ArrayList<Image> array;
	
	public NumberImage(Boolean small,int size,Stage stage){
		mStage = stage;
		array = new ArrayList<Image>();
		mNumber = Integer.MIN_VALUE;
		mSmall = small;
		mSize = size;
		mX = 0;
		mY = 0;
	}
	
	public void setNumber(int num) {
		if(mNumber!=num){
			clear();
			mNumber = num;
			if(mSmall){
				if(mNumber==0){
					array.add(createNumSmall(mNumber));
				}else {
					int n = mNumber;
					while(n != 0){
						int key = n % 10;
						array.add(createNumSmall(key));
						n = n / 10;
					}
				}
			} else {
				if(mNumber==0){
					array.add(createNum(mNumber));
				}else {
					int n = mNumber;
					while(n != 0){
						int key = n % 10;
						array.add(createNum(key));
						n = n / 10;
					}
				}
			}
			
			for(int i=array.size();i>0;--i){
				Image img = array.get(i-1);
				mStage.addActor(img);
			}
			setPos(mX,mY);
		}
	}
	
	public void setPos(int x,int y) {
		mX = x;
		mY = y;
		int offset = 0;
		//计算偏移
		if(array.size()<mSize){
			offset = (mSize - array.size());
		}
		//设置位置
		for(int i=array.size();i>0;--i){
			Image img = array.get(i-1);
			if(mSmall){
				img.setPosition(x + (array.size() - i + offset) * ImageSmallWidth, y);	
			}else {
				img.setPosition(x + (array.size() - i + offset) * ImageWidth, y);
			}
		}	
	}
	
	public void clear() {
		for(int i=array.size();i>0;--i) {
			Image img = array.get(i-1);
			img.remove();
		}
		array.clear();
		
		mNumber = Integer.MIN_VALUE;
	}
	
	public void reset() {
		setNumber(0);
	}
}
