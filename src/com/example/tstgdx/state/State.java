package com.example.tstgdx.state;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.tstgdx.data.UserData;


//###执行流程###
//押注
//转盘【网络】
//上分（只有一次，获得分值倍数）、下分、猜大小【网络】、显示大小记录
//结算
public class State {
	public int numberCredit; //币
	
	static final public int numberSize = 8;
	static final public int numberBar = 0;
	static final public int number77 = 1;
	static final public int numberXingXing = 2;
	static final public int numberXiGua = 3;
	static final public int numberTongLing = 4;
	static final public int numberMangGuo = 5;
	static final public int numberJuZi = 6;
	static final public int numberPingGuo = 7;
	static final public String[] numberName = {"anniu_bar","anniu_77","anniu_xingxing","anniu_xigua","anniu_tongzhong",
			"anniu_ningmeng","anniu_juzi","anniu_pingguo"};

	public int[] number = new int[numberSize];
	public int numberWin;
	
	public int[] numberCopy = new int[numberSize];
	public int numberWinCopy;
	
	public HashMap<String,Integer> mapKeyMultiple; //倍率表
	public ArrayList<String> lstKey; //Key表
	static public String strKey = "{\"default\":[\"juzi\",\"tongling\",\"barX50\",\"barX100\",\"pingguo\"," +
			"\"pingguoX3\",\"mangguo\",\"xigua\",\"xiguaX3\",\"luckyou\",\"pingguo\"," +
			"\"juziX3\",\"juzi\",\"tongling\",\"77X3\",\"77\",\"pingguo\",\"mangguoX3\"," +
			"\"mangguo\",\"xingxing\",\"xingxingX3\",\"luckzuo\",\"pingguo\",\"tonglingX3\"]}";
	
	public State() {
		numberCredit = 999999;
		clearAllNumber();
		numberWin = 0;
	}
	
	public void init() {
		mapKeyMultiple = new HashMap<String,Integer>();
		mapKeyMultiple.put("pingguo", 5);
		mapKeyMultiple.put("juzi", 10);
		mapKeyMultiple.put("mangguo", 15);
		mapKeyMultiple.put("tongling", 20);
		mapKeyMultiple.put("xigua", 20);
		mapKeyMultiple.put("xingxing", 30);
		mapKeyMultiple.put("77", 40);
		
		mapKeyMultiple.put("barX50", 50);
		mapKeyMultiple.put("barX100", 100);
		
		mapKeyMultiple.put("pingguoX3", 3);
		mapKeyMultiple.put("juziX3", 3);
		mapKeyMultiple.put("mangguoX3", 3);
		mapKeyMultiple.put("tonglingX3", 3);
		mapKeyMultiple.put("xiguaX3", 3);
		mapKeyMultiple.put("xingxingX3", 3);
		mapKeyMultiple.put("77X3", 3);
		
		mapKeyMultiple.put("luckyou", 0);
		mapKeyMultiple.put("luckzuo", 0);
		
		lstKey = new ArrayList<String>();
		try {
			JSONArray jsonObjs = new JSONObject(strKey).getJSONArray("default");
			for(int i = 0; i < jsonObjs.length() ; i++){
				String s = jsonObjs.getString(i);
				lstKey.add(s);
			}
		} catch (JSONException e) { 
            System.out.println("Jsons parse error !"); 
            e.printStackTrace(); 
        } 
	}
	
	public void clearAllNumber() {
		for(int i=0;i<numberSize;++i) {
			number[i]=0;
		}
	}
	
	public int getAllNumber() {
		int num=0;
		for(int i=0;i<numberSize;++i) {
			num += number[i];
		}
		return num;
	}
	
	public int getAllNumberCopy() {
		int num=0;
		for(int i=0;i<numberSize;++i) {
			num += numberCopy[i];
		}
		return num;
	}
	
	public Boolean canAdd(int index) {
		if(number[index]<UserData.getInstance().iNumberMax){
			return true;
		}
		return false;
	}
	
	public int allNeedNumber() {
		int num = 0;
		for(int i=0;i<numberSize;++i) {
			if(number[i]<UserData.getInstance().iNumberMax){
				num += 1;
			}
		}
		return num;
	}
	
	public void backupAllNumber() {
		for(int i=0;i<numberSize;++i) {
			numberCopy[i] = number[i];
		}
	}
	
	public void restoreAllNumber() {
		for(int i=0;i<numberSize;++i) {
			number[i] = numberCopy[i];
		}
	}
	
	public void backupNumberWin() {
		numberWinCopy = numberWin;
	}
	
	public void restoreNumberWin() {
		numberWin = numberWinCopy;
	}
}
