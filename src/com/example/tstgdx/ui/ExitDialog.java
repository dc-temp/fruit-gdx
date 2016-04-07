package com.example.tstgdx.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ExitDialog extends Dialog{

	public ExitDialog(String title, WindowStyle windowStyle) {
		super(title, windowStyle);
		
		TextureRegionDrawable textureRegionDrawable = (TextureRegionDrawable) windowStyle.background;
		float regionWidth = textureRegionDrawable.getRegion().getRegionWidth();
		float regionHeight = textureRegionDrawable.getRegion().getRegionHeight();
		setColor(1, 1, 1, 0);
		setSize(regionWidth, regionHeight);
		setOrigin(getWidth()/2, getHeight()/2);
		
		//标题
		BitmapFont font24 = new BitmapFont(Gdx.files.internal("font/font_heiti24.fnt"),Gdx.files.internal("font/font_heiti24.png"), false);
		LabelStyle labelStyle24 = new LabelStyle(font24, Color.YELLOW);
		Label titleActor = new Label("退出游戏",labelStyle24);
		titleActor.setColor(Color.YELLOW);
		titleActor.setPosition((getWidth() - titleActor.getWidth())/2, getHeight() - 4 - titleActor.getHeight());
		addActor(titleActor);
		
		//正文
		BitmapFont font32 = new BitmapFont(Gdx.files.internal("font/font_heiti32.fnt"),Gdx.files.internal("font/font_heiti32.png"), false);
		LabelStyle labelStyle32 = new LabelStyle(font32, Color.YELLOW);
		getContentTable().add(new Label("要退出游戏么?", labelStyle32));
		
		Texture texture = new Texture(Gdx.files.internal("ui/button1.png"));  
        SpriteDrawable draw = new SpriteDrawable(new Sprite(texture));  
		
		//取消按钮
		Group cancel = new Group();
		final ImageButton cancelButton = new ImageButton(draw);
		cancel.addActor(cancelButton);
		Label cancelLabel = new Label("取 消", new LabelStyle(font24, Color.BLACK));
		cancelLabel.setPosition((cancelButton.getWidth() - cancelLabel.getWidth())/2, 4.0f + (cancelButton.getHeight() - cancelLabel.getHeight())/2);
		cancel.addActor(cancelLabel);
		cancel.setSize(cancelButton.getWidth(), cancelButton.getHeight());
		cancel.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				ExitDialog.this.hide();
			}
		});
		getButtonTable().add(cancel).padRight(5);
		
		//确定按钮
		Group confirm = new Group();
		final ImageButton confirmButton = new ImageButton(draw);
		confirm.addActor(confirmButton);
		Label confirmLabel = new Label("确 定", new LabelStyle(font24, Color.WHITE));
		confirmLabel.setPosition((confirmButton.getWidth() - confirmLabel.getWidth())/2, 4.0f + (confirmButton.getHeight() - confirmLabel.getHeight())/2);
		confirm.addActor(confirmLabel);
		confirm.setSize(confirmButton.getWidth(), confirmButton.getHeight());
		confirm.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gdx.app.exit();
			}
		});
		getButtonTable().add(confirm).padLeft(5);
		
		//内容表格单元
		getCells().get(0).padTop(40);
		//按钮表格单元
		getCells().get(1).padBottom(15);
	}
}
