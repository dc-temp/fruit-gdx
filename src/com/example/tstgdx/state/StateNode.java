package com.example.tstgdx.state;

//状态基类
public abstract class StateNode {
	//定义状态类型
	static public int StateNodeType_Null = 0;
	static public int StateNodeType_Idle = 1;
	static public int StateNodeType_Go = 2;
	static public int StateNodeType_Dice = 3;
	
	//类型
	private int nType;
	
	private StateMgr mMgr;
	
	public StateNode() {
		nType = StateNodeType_Null;
	}
	
	public void setType(int type) {
		nType = type;
	}
	
	public int getType() {
		return nType;
	}
	
	public void setMgr(StateMgr mgr) {
		mMgr = mgr;
	}
	
	public StateMgr getMgr() {
		return mMgr;
	}
	
	public abstract void enter();
	
	public abstract void update();
	
	public abstract void exit();
}
