package com.example.tstgdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.example.tstgdx.ui.ExitDialog;

public class TestScreen extends ScreenAdapter {
	
    private static final int VIRTUAL_WIDTH = 480;
    private static final int VIRTUAL_HEIGHT = 800;
    private static final float ASPECT_RATIO = (float) VIRTUAL_WIDTH / (float) VIRTUAL_HEIGHT;

    protected Rectangle mViewport;
	protected Game mGame;
	protected Stage mStage; 
	
	public TestScreen(Game game) {
        this.mGame = game;
    }
	  
    @Override  
    public void render(float delta)  
    {  
        Gdx.gl.glViewport((int) mViewport.x, (int) mViewport.y,(int) mViewport.width, (int) mViewport.height);
    		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        if( MainGame.getManager().update() )
        {
        }
        
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
        
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        
        Texture texBg = new Texture(Gdx.files.internal("ui/dialogbg.png"));
		TextureRegion regionBg = new TextureRegion(texBg);
		WindowStyle windowStyle = new WindowStyle(new BitmapFont(), Color.YELLOW, new TextureRegionDrawable(regionBg));
		ExitDialog exitWindow = new ExitDialog("", windowStyle);
		exitWindow.show(mStage);
//		Dialog dlg = new Dialog("TEST",windowStyle);
//		BitmapFont font24 = new BitmapFont(Gdx.files.internal("font/font_heiti24.fnt"),Gdx.files.internal("font/font_heiti24.png"), false);
//		LabelStyle labelStyle24 = new LabelStyle(font24, Color.YELLOW);
//		Label titleActor = new Label("退出游戏",labelStyle24);
//		titleActor.setColor(Color.YELLOW);
//		titleActor.setPosition((dlg.getWidth() - titleActor.getWidth())/2, dlg.getHeight() - 4 - titleActor.getHeight());
//		dlg.show(mStage);
        
//        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
//		
//        Table container = new Table();
//        mStage.addActor(container);
//		container.setFillParent(true);
//
//		Table table = new Table();
//		final ScrollPane scroll = new ScrollPane(table,skin);
//
//		InputListener stopTouchDown = new InputListener() {
//			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
//				event.stop();
//				return false;
//			}
//		};
//
//		table.pad(10).defaults().expandX().space(4);
//		for (int i = 0; i < 100; i++) {
//			table.row();
//			Group tb = new Group();
//			tb.setWidth(400);
//			tb.setHeight(80);
//			Label lable = new Label(i + "uno",skin);
//			tb.addActor(lable);
//			table.add(tb);
//		}
//
//		final TextButton flickButton = new TextButton("Flick", skin);
//		flickButton.setChecked(true);
//		flickButton.addListener(new ChangeListener() {
//			public void changed (ChangeEvent event, Actor actor) {
//				scroll.setFlickScroll(flickButton.isChecked());
//			}
//		});
//
//		final TextButton fadeButton = new TextButton("Fade", skin);
//		fadeButton.setChecked(true);
//		fadeButton.addListener(new ChangeListener() {
//			public void changed (ChangeEvent event, Actor actor) {
//				scroll.setFadeScrollBars(fadeButton.isChecked());
//			}
//		});
//
//		final TextButton smoothButton = new TextButton("Smooth", skin);
//		smoothButton.setChecked(true);
//		smoothButton.addListener(new ChangeListener() {
//			public void changed (ChangeEvent event, Actor actor) {
//				scroll.setSmoothScrolling(smoothButton.isChecked());
//			}
//		});
//
//		final TextButton onTopButton = new TextButton("On Top", skin);
//		onTopButton.addListener(new ChangeListener() {
//			public void changed (ChangeEvent event, Actor actor) {
//				scroll.setScrollbarsOnTop(onTopButton.isChecked());
//			}
//		});
//
//		container.add(scroll).expand().fill().colspan(4);
//		container.row().space(10).padBottom(10);
//		container.add(flickButton).right().expandX();
//		container.add(onTopButton);
//		container.add(smoothButton);
//		container.add(fadeButton).left().expandX();
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
}

