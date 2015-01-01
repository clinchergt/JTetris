package com.clinchergt.jtetris;

import java.awt.event.*;
import java.io.*;
public class CustomKeys implements KeyListener{
	FileOutputStream out;
	int keysPressed;
	PrintStream printS;
	public CustomKeys(){
		try{
			out = new FileOutputStream("settings.txt");
			printS = new PrintStream(out);
		}catch(Exception e){}
		keysPressed = 0;
	}
	public void keyPressed(KeyEvent e){
		try{
			if(keysPressed < 7){
				printS.println(e.getKeyCode());
				keysPressed++;
			}
			if(keysPressed == 7)
				out.close();
		}catch(Exception exception){}
	}
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
}