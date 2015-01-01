package com.clinchergt.jtetris;

import javax.swing.*;
public class Customize extends JFrame{
	public Customize(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(350, 10);
		this.setResizable(false);
		this.setLocation(500, 300);
		this.setVisible(true);
		CustomKeys customKeys = new CustomKeys();
		this.addKeyListener(customKeys);
		while(customKeys.keysPressed < 7){
			switch(customKeys.keysPressed){
				case 0: this.setTitle("Press your MOVE LEFT key"); break;
				case 1: this.setTitle("Press your MOVE RIGHT key"); break;
				case 2: this.setTitle("Press your HARD DROP key"); break;
				case 3: this.setTitle("Press your ROTATE CCW key"); break;
				case 4: this.setTitle("Press your ROTATE CW key"); break;
				case 5: this.setTitle("Press your HOLD key"); break;
				case 6: this.setTitle("Press your RESTART key"); break;
			}
		}
		this.setTitle("Done! Close this window");
	}
	public static void main(String[] args){
		new Customize();
	}
}