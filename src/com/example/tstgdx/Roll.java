package com.example.tstgdx;

public class Roll {
	public static int NumberMax = 24;
	public static int SetpMin = 40;
	public static int SetpMax = 60;
	
	static public int calculateRandomSetp() {
		return (int)(Math.random() * (SetpMax-SetpMin)) + SetpMin;
	}
	
	static public int calculateSetpLeft(int bgn,int end) {
		int num=0;
		if(end > bgn) {
			num = bgn + NumberMax - end;
		} else {
			num = bgn - end;
		}
		return num;
	}
	
	static public int calculateSetpRight(int bgn,int end) {
		int num = 0;
		if(end > bgn) {
			num = end - bgn;
		} else {
			num = NumberMax - bgn + end;
		}
		return num;
	}
	
	static public int randomRoll(int max) {
		int rate = (int)(Math.random() * max + 1);
		return rate * NumberMax;
	}
}
