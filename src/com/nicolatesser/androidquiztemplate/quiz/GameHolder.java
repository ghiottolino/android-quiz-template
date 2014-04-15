package com.nicolatesser.androidquiztemplate.quiz;

public class GameHolder {

	public static Game instance = null;
	
	public static Game getInstance() {
		if (instance == null) {
			throw new IllegalAccessError();
		}
		return instance;
	}

	public static void setInstance(Game game) {
		instance = game;
	}
	
	public static boolean isInit() {
		return instance!=null;
	}
	
}
