package com.example.tstgdx.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.example.tstgdx.MainLogin;

public class RegisterDialog extends Dialog{
	
	ImageButton btnRegister;
	ImageButton btnCode;
	ImageButton btnClose;
	TextField textName;
	TextField textPW;
	TextField textCode;
	MainLogin scnLogin;

	public RegisterDialog(MainLogin scn,String title, WindowStyle windowStyle) {
		super(title, windowStyle);
		
		scnLogin = scn;
		
		TextureRegionDrawable textureRegionDrawable = (TextureRegionDrawable) windowStyle.background;
		float regionWidth = textureRegionDrawable.getRegion().getRegionWidth();
		float regionHeight = textureRegionDrawable.getRegion().getRegionHeight();
		setColor(1, 1, 1, 0);
		setSize(regionWidth, regionHeight);
		setOrigin(getWidth()/2, getHeight()/2);
		
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		textName = new TextField("", skin);
		textName.setPosition(140, 190);
		this.addActor(textName);
		
		textPW = new TextField("", skin);
		textPW.setPosition(140, 140);
		textPW.setPasswordMode(true);
		textPW.setPasswordCharacter('*');
		this.addActor(textPW);
		
		textCode = new TextField("", skin);
		textCode.setPosition(140, 98);
		textCode.setWidth(70);
		this.addActor(textCode);
		
		Texture tex = new Texture(Gdx.files.internal("480/anniu-guanbi.png"));
		btnClose = new ImageButton(new SpriteDrawable(new Sprite(tex)));
		btnClose.setPosition(getWidth() - btnClose.getWidth()+4, getHeight() - btnClose.getHeight()+4);
		btnClose.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				RegisterDialog.this.hide();
				Gdx.input.setOnscreenKeyboardVisible(false);
			}
		});
		this.addActor(btnClose);
		
		tex = new Texture(Gdx.files.internal("480/anniu-zhuce.png"));
		btnRegister = new ImageButton(new SpriteDrawable(new Sprite(tex)));
		btnRegister.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.input.setOnscreenKeyboardVisible(false);
				scnLogin.changeScene();
			}
		});
		getButtonTable().add(btnRegister);
		
		tex = new Texture(Gdx.files.internal("480/anniu-yanzhengma.png"));
		Texture tex2 = new Texture(Gdx.files.internal("480/anniu-yanzhengma1.png"));
		btnCode = new ImageButton(new SpriteDrawable(new Sprite(tex)),
				new SpriteDrawable(new Sprite(tex2)));
		btnCode.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		this.addActor(btnCode);
		btnCode.setPosition(215, 92);
		
		getCells().get(1).padBottom(20);
	}
}
