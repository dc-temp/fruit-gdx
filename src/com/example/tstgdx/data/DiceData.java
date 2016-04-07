package com.example.tstgdx.data;

public class DiceData {
	public int iNumber; //大小数值
	public int iWin; //获得分数
	
	static public DiceData data;
	static public DiceData getInstance() {
		if(data==null){
			data = new DiceData();
		}
		return data;
	}
}
