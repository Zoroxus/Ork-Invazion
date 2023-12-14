package com.orkin.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
//import com.orkin.game.OrkIn;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(1392, 816);
		//config.setWindowedMode(1856-4*64, 1088);
		config.setForegroundFPS(120);
		config.setTitle("Ork Invazion - A Warhammer 40.000 fangame");
		new Lwjgl3Application(new OrkIn(), config);
		//GameLoop
		//Winning / Losing condition
		// Display info on the console to visualize each turn
		// 3 types of ennemy
		// 3 item type
		// Avoid duplication, use polymorphism
		// HAVE A DOC FOR IMPORTANT METHODS
		// Readme !
	}
}