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

public class LoginDialog extends Dialog{
	
	ImageButton btnLogin;
	ImageButton btnClose;
	TextField textName;
	TextField textPW;
	MainLogin scnLogin;

	public LoginDialog(MainLogin scn,String title, WindowStyle windowStyle) {
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
		textName.setPosition(108, 175);
		this.addActor(textName);
		
		textPW = new TextField("", skin);
		textPW.setPosition(108, 115);
		textPW.setPasswordMode(true);
		textPW.setPasswordCharacter('*');
		this.addActor(textPW);
		
		Texture tex = new Texture(Gdx.files.internal("480/anniu-guanbi.png"));
		btnClose = new ImageButton(new SpriteDrawable(new Sprite(tex)));
		btnClose.setPosition(getWidth() - btnClose.getWidth()+4, getHeight() - btnClose.getHeight()+4);
		btnClose.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				LoginDialog.this.hide();
				Gdx.input.setOnscreenKeyboardVisible(false);
			}
		});
		this.addActor(btnClose);
		
		tex = new Texture(Gdx.files.internal("480/anniu-denglu.png"));
		btnLogin = new ImageButton(new SpriteDrawable(new Sprite(tex)));
		btnLogin.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.input.setOnscreenKeyboardVisible(false);
				scnLogin.changeScene();
			}
		});
		getButtonTable().add(btnLogin);
		
		getCells().get(1).padBottom(20);
	}
}
