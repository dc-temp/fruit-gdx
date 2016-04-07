package com.example.tstgdx.data;

public class RollData {
	
	public static final int eResultType_Standard = 1; //标准
	public static final int eResultType_Giving = 2; //送灯
	public static final int eResultType_Train = 3; //火车
	public static final int eResultType_SiTongBaDa = 4; //四通八达
	
	public boolean bInit = false; //是否初始化
	public int iTarget[] = new int[5]; //目标位置
	public int iType = 0; //类型
	public int iWin = 0; //获得分数 
	
	static public RollData data;
	static public RollData getInstance() {
		if(data==null){
			data = new RollData();
		}
		return data;
	}
}
