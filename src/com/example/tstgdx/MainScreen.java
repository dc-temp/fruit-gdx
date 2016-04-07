package com.example.tstgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.example.tstgdx.data.UserData;
import com.example.tstgdx.state.State;
import com.example.tstgdx.state.StateDice;
import com.example.tstgdx.state.StateGo;
import com.example.tstgdx.state.StateIdle;
import com.example.tstgdx.state.StateMgr;
import com.example.tstgdx.state.StateNode;

public class MainScreen extends ScreenAdapter {
	
    private static final int VIRTUAL_WIDTH = 480;
    private static final int VIRTUAL_HEIGHT = 800;
    private static final float ASPECT_RATIO = (float) VIRTUAL_WIDTH / (float) VIRTUAL_HEIGHT;

    protected Rectangle mViewport;
	protected MainGame mGame;
	protected Stage mStage; 
	
	protected Image mImgBk;
	
	public Item mItemHead;
	
	public ImageButton anniuBack;
	public ImageButton anniuInfo;
	public ImageButton anniuGlod;
	public ImageButton anniuGongGao;
	public ImageButton anniuVIP;
	
	public ImageButton anniuNumber[] = new ImageButton[State.numberSize];
	
	public ImageButton anniuAll;
	public ImageButton anniuZuoJian;
	public ImageButton anniuYouJian;
	public ImageButton anniu1_7;
	public ImageButton anniu8_14;
	public ImageButton anniuGo;
	
	public NumberImage numberCredit;
	public NumberImage numberWin;
	public NumberImage numberDice;
	
	public NumberImage[] number = new NumberImage[State.numberSize];
	
	public State mState;
	public StateMgr mStateMgr;
	public StateIdle mStateIdle;
	public StateGo mStateGo;
	public StateDice mStateDice;
	
	public MainScreen(MainGame game) {
        this.mGame = game;
        mState = new State();
        mState.init();
        mStateMgr = new StateMgr();
        mStateIdle = new StateIdle(this);
        mStateIdle.setMgr(mStateMgr);
        mStateGo = new StateGo(this);
        mStateGo.setMgr(mStateMgr);
        mStateDice = new StateDice(this);
        mStateDice.setMgr(mStateMgr);
    }
	  
    @Override  
    public void render(float delta)  
    {  
        Gdx.gl.glViewport((int) mViewport.x, (int) mViewport.y,(int) mViewport.width, (int) mViewport.height);
    		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        if( MainGame.getManager().update() )
        {
        	if(mImgBk == null)
        	{
        		AssetManager asset = MainGame.getManager();
        		Texture tex = asset.get("480/machine1bg.png");
        		mImgBk = new Image(tex);
        		mStage.addActor(mImgBk);
        		
        		tex = asset.get("480/dikuang.png");
        		Image imgDiKuang = new Image(tex);
        		mStage.addActor(imgDiKuang);
        		imgDiKuang.setPosition(208, 360);
        		
        		tex = asset.get("480/dikuangbox.png");
        		Image imgDiKuangBox = new Image(tex);
        		mStage.addActor(imgDiKuangBox);
        		imgDiKuangBox.setPosition(214, 410);
        		
        		initItems();
        		Item itm = findItem(UserData.getInstance().iPosition);
        		itm.fadeIn(0.0f);
        		
        		initButton();
        		initButton2();
        		initNumber();
        		
        		mStateMgr.change(mStateIdle);
        	}
        }
        
        if(mStateMgr.getNextState()==StateNode.StateNodeType_Idle){
        	mStateMgr.change(mStateIdle);
        	mStateMgr.nextState(StateNode.StateNodeType_Null);
        }
        mStateMgr.update();
        
        mStage.act(Gdx.graphics.getDeltaTime());  
        mStage.draw();  
    }  
    
    private ImageButton createButton(String filename) {
    	Texture Tex01 = new Texture(Gdx.files.internal("480/" + filename + "_01.png"));
    	SpriteDrawable Draw01 = new SpriteDrawable(new Sprite(Tex01));
    	Texture Tex02 = new Texture(Gdx.files.internal("480/" + filename + "_02.png"));
    	SpriteDrawable Draw02 = new SpriteDrawable(new Sprite(Tex02));
    	Texture Tex03 = new Texture(Gdx.files.internal("480/" + filename + "_03.png"));
    	SpriteDrawable Draw03 = new SpriteDrawable(new Sprite(Tex03));
    	ImageButton btn = new ImageButton(Draw01,Draw02);
    	btn.getStyle().imageDisabled = Draw03;
    	return btn;
    }
    
    public void initButton() {
    	anniuBack = createButton("anniu_fanhui");
    	mStage.addActor(anniuBack);
    	anniuBack.setPosition(0, 758);
    	
    	anniuInfo= createButton("anniu_yiwen");
    	mStage.addActor(anniuInfo);
    	anniuInfo.setPosition(394, 758);
    	
    	Texture tex = new Texture(Gdx.files.internal("480/jinbikuang.png"));
    	SpriteDrawable draw = new SpriteDrawable(new Sprite(tex));
    	anniuGlod = new ImageButton(draw);
    	anniuGlod.setPosition(180, 758);
    	mStage.addActor(anniuGlod);
    	
    	tex = new Texture(Gdx.files.internal("480/gonggao.png"));
    	draw = new SpriteDrawable(new Sprite(tex));
    	anniuGongGao = new ImageButton(draw);
    	anniuGongGao.setPosition(118, 525);
    	mStage.addActor(anniuGongGao);
    	
    	tex = new Texture(Gdx.files.internal("480/shuiguoji-VIP-0.png"));
    	draw = new SpriteDrawable(new Sprite(tex));
    	anniuVIP = new ImageButton(draw);
    	anniuVIP.setPosition(78, 758);
    	mStage.addActor(anniuVIP);
    	
    	int iOffectY = 18;
    	for(int i=0;i<State.numberSize;++i) {
    		anniuNumber[i] = createButton(State.numberName[i]);
    		anniuNumber[i].setName(String.valueOf(i));
    		mStage.addActor(anniuNumber[i]);
    		anniuNumber[i].setPosition(i*61,iOffectY);
    	}
    anniuNumber[0].addListener(new ClickListener(){  
            public void clicked(InputEvent event,float x,float y){
            	int idx = 0;
            	if( anniuNumber[idx].isDisabled()==false ) {
            		addNumber(idx);
            	}
            }  
        }); 
    anniuNumber[1].addListener(new ClickListener(){  
        public void clicked(InputEvent event,float x,float y){
        	int idx = 1;
        	if( anniuNumber[idx].isDisabled()==false ) {
        		addNumber(idx);
        	}
        }  
    }); 
    anniuNumber[2].addListener(new ClickListener(){  
        public void clicked(InputEvent event,float x,float y){
        	int idx = 2;
        	if( anniuNumber[idx].isDisabled()==false ) {
        		addNumber(idx);
        	}
        }  
    }); 
    anniuNumber[3].addListener(new ClickListener(){  
        public void clicked(InputEvent event,float x,float y){
        	int idx = 3;
        	if( anniuNumber[idx].isDisabled()==false ) {
        		addNumber(idx);
        	}
        }  
    }); 
    anniuNumber[4].addListener(new ClickListener(){  
        public void clicked(InputEvent event,float x,float y){
        	int idx = 4;
        	if( anniuNumber[idx].isDisabled()==false ) {
        		addNumber(idx);
        	}
        }  
    }); 
    anniuNumber[5].addListener(new ClickListener(){  
        public void clicked(InputEvent event,float x,float y){
        	int idx = 5;
        	if( anniuNumber[idx].isDisabled()==false ) {
        		addNumber(idx);
        	}
        }  
    }); 
    anniuNumber[6].addListener(new ClickListener(){  
        public void clicked(InputEvent event,float x,float y){
        	int idx = 6;
        	if( anniuNumber[idx].isDisabled()==false ) {
        		addNumber(idx);
        	}
        }  
    }); 
    anniuNumber[7].addListener(new ClickListener(){  
        public void clicked(InputEvent event,float x,float y){
        	int idx = 7;
        	if( anniuNumber[idx].isDisabled()==false ) {
        		addNumber(idx);
        	}
        }  
    }); 
    }
    
    private void initButton2(){
    	int iOffectY = 156;
    	
    	anniuAll = createButton("anniu_all+1");
    	mStage.addActor(anniuAll);
    	anniuAll.addListener(new ClickListener(){  
            public void clicked(InputEvent event,float x,float y){  
            	if( anniuAll.isDisabled()==false ) {
            		addAllNumber();
            	}
            }  
        });
    	anniuAll.setPosition(16,iOffectY);
    	
    	anniuZuoJian = createButton("anniu_zuojian");
    	mStage.addActor(anniuZuoJian);
    	anniuZuoJian.addListener(new ClickListener(){  
            public void clicked(InputEvent event,float x,float y){  
            	if(anniuZuoJian.isDisabled()==false){
            		if(mStateMgr.getStateType()==StateNode.StateNodeType_Go ||
            				mStateMgr.getStateType()==StateNode.StateNodeType_Dice) {
            			if(mState.numberWinCopy>0){
            				int num = mState.numberWinCopy * 2;
            				num -= mState.numberWin;
            				if(num > mState.numberCredit)
            					num = mState.numberCredit;
            				if(num>0){
            					mState.numberWin += num;
                				mState.numberCredit -= num;
                				numberWin.setNumber(mState.numberWin);
                				numberCredit.setNumber(mState.numberCredit);
            				}
            			}
            		}
            	}
            }  
        });
    	anniuZuoJian.setPosition(68,iOffectY);
    	
    	anniuYouJian = createButton("anniu_youjian");
    	mStage.addActor(anniuYouJian);
    	anniuYouJian.addListener(new ClickListener(){  
            public void clicked(InputEvent event,float x,float y){  
            	if(anniuZuoJian.isDisabled()==false){
            		if(mStateMgr.getStateType()==StateNode.StateNodeType_Go || 
            				mStateMgr.getStateType() == StateNode.StateNodeType_Dice) {
            			if(mState.numberWin>0){
            				mState.numberWin -= 1;
            				mState.numberCredit += 1;
            				numberWin.setNumber(mState.numberWin);
            				numberCredit.setNumber(mState.numberCredit);
            			}
            		}
            	}
            }  
        });
    	anniuYouJian.setPosition(150,iOffectY);
    	
    	anniu1_7 = createButton("anniu_1-7");
    	mStage.addActor(anniu1_7);
    	anniu1_7.addListener(new ClickListener(){  
            public void clicked(InputEvent event,float x,float y){  
            	if( mState.numberWin > 0 ) {
            		mStateDice.bBig = false;
            		if(mStateMgr.getStateType() == StateNode.StateNodeType_Dice) {
            			mStateDice.reset();
            		} else if(mStateMgr.getStateType() == StateNode.StateNodeType_Go) {
            			mStateMgr.change(mStateDice);
            		}
            	}
            }  
        });
    	anniu1_7.setPosition(238,iOffectY);
    	
    	anniu8_14 = createButton("anniu_8-14");
    	mStage.addActor(anniu8_14);
    	anniu8_14.addListener(new ClickListener(){  
            public void clicked(InputEvent event,float x,float y){
            	if( mState.numberWin > 0 ) {
            		mStateDice.bBig = true;
                	if(mStateMgr.getStateType() == StateNode.StateNodeType_Dice) {
            			mStateDice.reset();
            		} else if(mStateMgr.getStateType() == StateNode.StateNodeType_Go) {
            			mStateMgr.change(mStateDice);
            		}
            	}
            }  
        });
    	anniu8_14.setPosition(298,iOffectY);
    	
    	anniuGo = createButton("anniu_kaishi");
    	mStage.addActor(anniuGo);
    	anniuGo.addListener(new ClickListener(){  
            public void clicked(InputEvent event,float x,float y) {
            	if(anniuGo.isDisabled()==false){
            		if(mStateMgr.getStateType() == StateNode.StateNodeType_Idle) {
            			//转盘
            			if(mState.getAllNumber() > 0) {
            				mStateMgr.change(mStateGo);
            			} else if(mState.getAllNumberCopy() > 0 &&  mState.getAllNumberCopy() <= mState.numberCredit ) {
            				mState.restoreAllNumber();
            				syncAllNumber();
            				if( testCoin(mState.getAllNumber()) ) {
            					mStateMgr.change(mStateGo);
            				}
            			}
            		} else if(mStateMgr.getStateType() == StateNode.StateNodeType_Go || 
            				mStateMgr.getStateType() == StateNode.StateNodeType_Dice) {
            			//下分
            			mStateMgr.change(mStateIdle);
            		}
            		
            	}
            }  
        });
    	anniuGo.setPosition(354,iOffectY);
    }
    
    private void initNumber() {
    	int offset = 55;
    	for(int i=0;i<State.numberSize;++i) {
    		number[i] = new NumberImage(true,2,mStage);
    		number[i].setPos(30 + offset * i, 78);
    	}
    	
    	numberCredit = new NumberImage(false,8,mStage);
    	numberCredit.setPos(262,638);
    	numberCredit.setNumber(mState.numberCredit);
    	
    	numberWin = new NumberImage(false,8,mStage);
    	numberWin.setPos(44,638);
    	
    	numberDice = new NumberImage(false,2,mStage);
    	numberDice.setPos(222,364);
    }
    
    private void initItems() {
    		AssetManager asset = MainGame.getManager();
    		TextureAtlas atlas = asset.get("480/pack");
		Item cur = null;
		float fBeginPosX = 32;
		float fBeginPosY = 800-241;
		float fSize = 57;
		
		for(int i=0;i<mState.lstKey.size();++i) {
			Item item = new Item(mStage,atlas,mState.lstKey.get(i),i);
			if(cur!=null) {
				cur.setNext(item);
				item.setPrev(cur);
			} else {
				mItemHead = item;
			}
			item.fadeOut(0.0f);
			cur = item;
		}
		// 封口
		cur.setNext(mItemHead);
		mItemHead.setPrev(cur);
		
		cur = mItemHead;
		cur.setPosition(fBeginPosX, fBeginPosY);
		cur = cur.getNext();
		cur.setPosition(fBeginPosX + fSize, fBeginPosY);
		cur = cur.getNext();
		cur.setPosition(fBeginPosX + 2*fSize, fBeginPosY);
		cur = cur.getNext();
		cur.setPosition(fBeginPosX + 3*fSize, fBeginPosY);
		cur = cur.getNext();
		cur.setPosition(fBeginPosX + 4*fSize, fBeginPosY);
		cur = cur.getNext();
		cur.setPosition(fBeginPosX + 5*fSize, fBeginPosY);
		cur = cur.getNext();
		cur.setPosition(fBeginPosX + 6*fSize, fBeginPosY);
		cur = cur.getNext();
		cur.setPosition(fBeginPosX + 6*fSize, fBeginPosY - fSize);
		cur = cur.getNext();
		cur.setPosition(fBeginPosX + 6*fSize, fBeginPosY - 2*fSize);
		cur = cur.getNext();
		cur.setPosition(fBeginPosX + 6*fSize, fBeginPosY - 3*fSize);
		cur = cur.getNext();
		cur.setPosition(fBeginPosX + 6*fSize, fBeginPosY - 4*fSize);
		cur = cur.getNext();
		cur.setPosition(fBeginPosX + 6*fSize, fBeginPosY - 5*fSize);
		cur = cur.getNext();
		cur.setPosition(fBeginPosX + 6*fSize, fBeginPosY - 6*fSize);
		cur = cur.getNext();
		cur.setPosition(fBeginPosX + 5*fSize, fBeginPosY - 6*fSize);
		cur = cur.getNext();
		cur.setPosition(fBeginPosX + 4*fSize, fBeginPosY - 6*fSize);
		cur = cur.getNext();
		cur.setPosition(fBeginPosX + 3*fSize, fBeginPosY - 6*fSize);
		cur = cur.getNext();
		cur.setPosition(fBeginPosX + 2*fSize, fBeginPosY - 6*fSize);
		cur = cur.getNext();
		cur.setPosition(fBeginPosX + 1*fSize, fBeginPosY - 6*fSize);
		cur = cur.getNext();
		cur.setPosition(fBeginPosX, fBeginPosY - 6*fSize);
		cur = cur.getNext();
		cur.setPosition(fBeginPosX, fBeginPosY - 5*fSize);
		cur = cur.getNext();
		cur.setPosition(fBeginPosX, fBeginPosY - 4*fSize);
		cur = cur.getNext();
		cur.setPosition(fBeginPosX, fBeginPosY - 3*fSize);
		cur = cur.getNext();
		cur.setPosition(fBeginPosX, fBeginPosY - 2*fSize);
		cur = cur.getNext();
		cur.setPosition(fBeginPosX, fBeginPosY - 1*fSize);
		cur = cur.getNext();
	}
    
	public Item findItem(int idx) {
		Item item = mItemHead;
		while(item!=null){
			if( item.getIndex() == idx )
			{
				return item;
			}
			item = item.getNext();
		}
		return null;
	}
    
    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float)width/(float)height;
        float scale = 1f;
        Vector2 crop = new Vector2(0f, 0f);

        if(aspectRatio > ASPECT_RATIO) {
            scale = (float) height / (float) VIRTUAL_HEIGHT;
            crop.x = (width - VIRTUAL_WIDTH * scale) / 2f;
        } else if(aspectRatio < ASPECT_RATIO) {
            scale = (float) width / (float) VIRTUAL_WIDTH;
            crop.y = (height - VIRTUAL_HEIGHT * scale) / 2f;
        } else {
            scale = (float) width / (float) VIRTUAL_WIDTH;
        }

        float w = (float) VIRTUAL_WIDTH * scale;
        float h = (float) VIRTUAL_HEIGHT * scale;
        mViewport = new Rectangle(crop.x, crop.y, w, h);
    }
  
    @Override  
    public void show()  
    {  
    	loadAsset();
    	
    	mStage = new Stage(new StretchViewport(VIRTUAL_WIDTH,VIRTUAL_HEIGHT));
    	
    	InputMultiplexer multiplexer = new InputMultiplexer(); // 多输入接收器
    	multiplexer.addProcessor(new MyDetector(mStage,new MyListener()));
    	multiplexer.addProcessor(mStage);
        Gdx.input.setInputProcessor(multiplexer);
    } 
    
    private void loadAsset(){
    	AssetManager asset = MainGame.getManager();
    	asset.clear();
    	asset.load("480/machine1bg.png",Texture.class);
    	asset.load("480/dikuang.png",Texture.class);
    	asset.load("480/dikuangbox.png",Texture.class);
    	asset.load("480/pack",TextureAtlas.class);
    	asset.load("480/shuzi.png",Texture.class);
    	asset.load("480/xiaoshuzi.png",Texture.class);
    }
    
    private void reloadAsset(){
    	loadAsset();
    	
    	mImgBk = null;
    }
    
    @Override
    public void pause () { 
    	reloadAsset();
    }
    
    @Override
    public void resume() {
    	reloadAsset();
    }
    
    @Override
    public void dispose() {
        mStage.dispose();
    }
    
    //没有币了
    public void onNoCoin() {
    	
    }
    
    //投币
    public void onAddCoin() {
    	
    }
    
    //测试币消耗
    public Boolean testCoin(int num) {
    	if( mState.numberCredit >= num ) {
    		mState.numberCredit -= num;
    		numberCredit.setNumber(mState.numberCredit);
    		return true;
    	} else {
    		onNoCoin();
    		return false;
    	}
    }
    
    //加分
    public void addNumber(int index) {
    	if( mState.canAdd(index) && testCoin(1) ) {
    		mState.number[index] += 1;
    		number[index].setNumber(mState.number[index]);
    		onAddCoin();
    	}
    }
    
    public void addAllNumber() {
    	int num = mState.allNeedNumber();
    	if( num>0 && testCoin(num) ) {
    		for(int i=0;i<State.numberSize;++i) {
    		addNumber(i);	
    		}
    	}
    }
    
    public void clearAllNumber() {
    	for(int i=0;i<State.numberSize;++i) {
    		number[i].clear();
    		}
    }
    
    public void resetAllNumber() {
    	for(int i=0;i<State.numberSize;++i) {
    		number[i].reset();
    		}
    }
    
    public void syncAllNumber() {
    	for(int i=0;i<State.numberSize;++i) {
    		number[i].setNumber(mState.number[i]);
    		}
    }
}
