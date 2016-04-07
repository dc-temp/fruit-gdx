package com.example.tstgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.example.tstgdx.ui.LoginDialog;
import com.example.tstgdx.ui.RegisterDialog;

public class MainLogin extends ScreenAdapter {
	
	private static final int VIRTUAL_WIDTH = 480;
    private static final int VIRTUAL_HEIGHT = 800;
    private static final float ASPECT_RATIO = (float) VIRTUAL_WIDTH / (float) VIRTUAL_HEIGHT;

    protected Rectangle mViewport;
	protected MainGame mGame;
	protected Stage mStage; 
	
	public ImageButton anniuStart;
	public ImageButton anniuRegister;
	public ImageButton anniuLogin;
	
	public MainLogin(MainGame game) {
        this.mGame = game;
    }
	
	@Override  
    public void render(float delta)  
    {  
        Gdx.gl.glViewport((int) mViewport.x, (int) mViewport.y,(int) mViewport.width, (int) mViewport.height);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        mStage.act(Gdx.graphics.getDeltaTime());  
        mStage.draw();
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
    	mStage = new Stage(new StretchViewport(VIRTUAL_WIDTH,VIRTUAL_HEIGHT));
    	
    	InputMultiplexer multiplexer = new InputMultiplexer(); // 多输入接收器
    	multiplexer.addProcessor(new MyDetector(mStage,new MyListener()));
    	multiplexer.addProcessor(mStage);
        Gdx.input.setInputProcessor(multiplexer);
        
        Texture tex = new Texture(Gdx.files.internal("480/denglubk.png"));
        Image bkImage = new Image(tex);
        mStage.addActor(bkImage);
        
        tex = new Texture(Gdx.files.internal("480/anniu-kaishi.png"));
    	SpriteDrawable draw = new SpriteDrawable(new Sprite(tex));
    	anniuStart = new ImageButton(draw);
    	anniuStart.setPosition(150, 160);
    	mStage.addActor(anniuStart);
    	anniuStart.addListener(new ClickListener(){  
    		public void clicked(InputEvent event,float x,float y){
        	changeScene();
        }  
    	});
    	
    	tex = new Texture(Gdx.files.internal("480/anniu-denglu.png"));
    	draw = new SpriteDrawable(new Sprite(tex));
    	anniuLogin = new ImageButton(draw);
    	anniuLogin.setPosition(150, 95);
    	mStage.addActor(anniuLogin);
    	anniuLogin.addListener(new ClickListener(){
    		public void clicked(InputEvent event,float x,float y){
    	        Texture tex = new Texture(Gdx.files.internal("480/dengludlgbk.png"));
    			TextureRegion reg = new TextureRegion(tex);
    			WindowStyle windowStyle = new WindowStyle(new BitmapFont(), Color.YELLOW, new TextureRegionDrawable(reg));
    			LoginDialog dlg = new LoginDialog(MainLogin.this,"", windowStyle);
    			dlg.show(mStage);
    		}
    	});
    	
    	tex = new Texture(Gdx.files.internal("480/anniu-zhuce.png"));
    	draw = new SpriteDrawable(new Sprite(tex));
    	anniuRegister = new ImageButton(draw);
    	anniuRegister.setPosition(150, 30);
    	mStage.addActor(anniuRegister);
    	anniuRegister.addListener(new ClickListener(){
    		public void clicked(InputEvent event,float x,float y){
    	        Texture tex = new Texture(Gdx.files.internal("480/zhucedlgbk.png"));
    			TextureRegion reg = new TextureRegion(tex);
    			WindowStyle windowStyle = new WindowStyle(new BitmapFont(), Color.YELLOW, new TextureRegionDrawable(reg));
    			RegisterDialog dlg = new RegisterDialog(MainLogin.this,"", windowStyle);
    			dlg.show(mStage);
    		}
    	});
    } 
    
    @Override
    public void pause () { 
    }
    
    @Override
    public void resume() {
    }
    
    @Override
    public void dispose() {
        mStage.dispose();
    }
    
    public void changeScene() {
    	MainScreen scn = new MainScreen(mGame);
    	mGame.setScreen(scn);
    	dispose();
    }
}
