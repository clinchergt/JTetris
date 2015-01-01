package com.clinchergt.jtetris;

import java.util.LinkedList;
import java.io.*;
import java.awt.event.*;
public class Keys implements KeyListener{
	LinkedList<Integer> inputs, defaults;
	InputStream in;
	public static int
		HD = KeyEvent.VK_K,
		LEFT = KeyEvent.VK_J,
		RIGHT = KeyEvent.VK_L,
		A = KeyEvent.VK_A,
		B = KeyEvent.VK_S,
		D = KeyEvent.VK_D,
		RESTART = KeyEvent.VK_R;
	public void setDefaults(){
		try{
			in = getClass().getResourceAsStream("/config/settings.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			LEFT = Integer.parseInt(br.readLine());
			RIGHT = Integer.parseInt(br.readLine());
			HD = Integer.parseInt(br.readLine());
			A = Integer.parseInt(br.readLine());
			B = Integer.parseInt(br.readLine());
			D = Integer.parseInt(br.readLine());
			RESTART = Integer.parseInt(br.readLine());
		}catch(Exception e){ System.out.println(e.getMessage());}
	}
	public void keyPressed(KeyEvent e) {
		if(defaults.contains(new Integer(e.getKeyCode())) && !inputs.contains(new Integer(e.getKeyCode()))){
			inputs.add(new Integer(e.getKeyCode()));
		}
	}
	public void keyReleased(KeyEvent e){
		if(inputs.contains(new Integer(e.getKeyCode()))){
			inputs.remove(new Integer(e.getKeyCode()));
		}
	}
	public void keyTyped(KeyEvent e){

	}
	public LinkedList getInputs(){
		return (LinkedList)inputs.clone();
	}
	public Keys(){
		defaults = new LinkedList<Integer>();
		inputs = new LinkedList<Integer>();
		setDefaults();
		defaults.add(new Integer(LEFT));
		defaults.add(new Integer(HD));
		defaults.add(new Integer(RIGHT));
		defaults.add(new Integer(A));
		defaults.add(new Integer(B));
		defaults.add(new Integer(D));
		defaults.add(new Integer(RESTART));
	}
}