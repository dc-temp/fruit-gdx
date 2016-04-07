package com.example.tstgdx.data;

public class UserData {
	public String strOnlyID;
	public String strPhoneCode;
	
	public int iGold;
	public int iBonusVolume; // 红利卷
	public int iGeneralVolume; // 通用
	public int iSilverVolum; // 银
	public int iGoldVolum; // 金
	public int iPlatinumVolum; // 铂金
	
	public int iVIP;
	public int iVIPStoredValue;
	
	public int iPosition;
	
	public int iNumberMax;
	
	public UserData() {
		iPosition = 0;
		iNumberMax = 10;
	}
	
	static public UserData data;
	static public UserData getInstance() {
		if(data==null){
			data = new UserData();
		}
		return data;
	}
}
