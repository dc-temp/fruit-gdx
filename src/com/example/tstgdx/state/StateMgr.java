package com.example.tstgdx.state;

public class StateMgr {
	private StateNode node;
	private int nextNode;
	
	public StateMgr() {
		node = null;
		nextNode = StateNode.StateNodeType_Null;
	}
	
	public void change(StateNode n) {
		if(node!=null) {
			node.exit();
			node = null;
		}
		node = n;
		if(node!=null) {
			node.enter();
		}
	}
	
	public void update() {
		if(node!=null) {
			node.update();
		}
	}
	
	public int getStateType() {
		if(node!=null){
			return node.getType();
		}
		return StateNode.StateNodeType_Null;
	}
	
	public void nextState(int type) {
		nextNode = type;
	}
	
	public int getNextState() {
		return nextNode;
	}
}
