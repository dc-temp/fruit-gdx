package com.example.tstgdx;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MyDetector extends GestureDetector {
	Stage stage;

    public MyDetector(Stage stage, GestureDetector.GestureListener listener) {
        super(listener);
        this.stage = stage;
        this.setLongPressSeconds(1f); //设置长按的判断标准为1秒
    }

    @Override
    public boolean touchUp(float x, float y, int pointer, int button) {
        return super.touchUp(x, y, pointer, button);
    }
}
