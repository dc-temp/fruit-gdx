package com.example.tstgdx.state;

//状态基类
public abstract class TaskNode {
	
	//类型
	protected int nType;
	
	public TaskNode() {
		nType = 0;
	}
	
	public void setType(int type) {
		nType = type;
	}
	
	public int getType() {
		return nType;
	}
	
	public abstract void enter();
	
	public abstract Boolean update();
	
	public abstract void exit();
}
