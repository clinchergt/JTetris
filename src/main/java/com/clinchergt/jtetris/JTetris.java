package com.clinchergt.jtetris;

import java.util.Random;
import java.util.LinkedList;
import java.awt.event.*;
public class JTetris{
	Piece currentPiece, nextOne, nextTwo, nextThree, holdPiece, temp;
	Field field;
	boolean holdEnabled = true, gameOver = false;
	static Random randomizer = new Random();
	int das, lines, direction, countdown;
	long time;
	int[] history = {Piece.Z, Piece.S, Piece.Z, Piece.S};
	public static final int[] initialHistory = {Piece.Z, Piece.S, Piece.Z, Piece.S};
	LinkedList<Integer> previousInputs;
	Integer right, left, hd, a, b, d, restart;
	public JTetris(){
		reset();
	}
	public void reset(){
		gameOver = false;
		field = new Field();
		for(int i = 0; i < 4; i++)
			history[i] = initialHistory[i];
		currentPiece = new Piece(randomize(6));
		nextOne = new Piece(randomize(6));
		nextTwo = new Piece(randomize(6));
		nextThree = new Piece(randomize(6));
		das = 0;
		lines = 0;
		countdown = 60;
		holdEnabled = true;
		previousInputs = new LinkedList<Integer>();
		holdPiece = null;
		time = System.currentTimeMillis();
		right = new Integer(Keys.RIGHT);
		left = new Integer(Keys.LEFT);
		hd = new Integer(Keys.HD);
		a = new Integer(Keys.A);
		b = new Integer(Keys.B);
		d = new Integer(Keys.D);
		restart = new Integer(Keys.RESTART);
	}
	public void process(LinkedList inputs){
		if(countdown > 0){
			countdown--;
			if(inputs.contains(left) || inputs.contains(right))
				das++;
			else
				das = 0;
			if(countdown == 0)
				time = System.currentTimeMillis();
		}else if(!gameOver){
			if(inputs.size() == 0){
				previousInputs.clear();
				das = 0;
				return;
			}else{
				if(inputs.contains(hd) && !previousInputs.contains(hd)){
					field.lockPiece(currentPiece, field.placePiece(currentPiece));
					lines += field.clearLines();
					if(lines >= 40){
						gameOver = true;
					}
					currentPiece = new Piece(nextOne);
					nextOne = new Piece(nextTwo);
					nextTwo = new Piece(nextThree);
					nextThree = new Piece(randomize(6));
					holdEnabled = true;
				}
				if(inputs.contains(left) && inputs.contains(right)){
					if(!previousInputs.contains(left) && previousInputs.contains(right)){
						das = 0;
						direction = -1;
					}else if(!previousInputs.contains(right) && previousInputs.contains(left)){
						das = 0;
						direction = 1;
					}else if(!previousInputs.contains(right) && !previousInputs.contains(left)){
						direction = 0;
					}
					if(direction == 1){
						shiftRight();
					}else if(direction == -1){
						shiftLeft();
					}
				}else{
					if(inputs.contains(left)){
						if(direction == 1){
							das = 0;
							direction = -1;
						}
						shiftLeft();
					}
					if(inputs.contains(right)){
						if(direction == -1){
							das = 0;
							direction = 1;
						}
						shiftRight();
					}
				}
				if(inputs.contains(a) && !previousInputs.contains(a)){
					currentPiece.rotate(false);
					if(!currentPiece.isOK()){
						currentPiece.x++;
						if(!currentPiece.isOK()){
							currentPiece.x -= 2;
							if(!currentPiece.isOK()){
								currentPiece.x++;
								currentPiece.rotate(true);
							}
						}
					}
				}
				if(inputs.contains(b) && !previousInputs.contains(b)){
					currentPiece.rotate(true);
					if(!currentPiece.isOK()){
						currentPiece.x++;
						if(!currentPiece.isOK()){
							currentPiece.x -= 2;
							if(!currentPiece.isOK()){
								currentPiece.x++;
								currentPiece.rotate(false);
							}
						}
					}
				}
				if(inputs.contains(d) && holdEnabled && !previousInputs.contains(d)){
					if(holdPiece == null){
						holdPiece = new Piece(currentPiece.piece);
						currentPiece = new Piece(nextOne);
						nextOne = new Piece(nextTwo);
						nextTwo = new Piece(nextThree);
						nextThree = new Piece(randomize(6));
					}else{
						temp = new Piece(holdPiece);
						holdPiece = new Piece(currentPiece.piece);
						currentPiece = new Piece(temp);
					}
					holdEnabled = false;
				}
			}
		}

		if(inputs.contains(restart) && !previousInputs.contains(restart)){
			reset();
		}

		previousInputs.clear();
		while(inputs.size() != 0){
			previousInputs.add((Integer)inputs.remove());
		}
	}

	public int randomize(int rerolls){
		int retries, trial;
		trial = 0;
		for(int i = 0; i < rerolls; i++){
			trial = randomizer.nextInt(7);
			if(!contains(trial, history))
				i = rerolls;
		}
		for(int i = 3; i >= 1;){
			history[i] = history[--i];
		}
		history[0] = trial;
		return trial;
	}
	public void shiftLeft(){
		if(das == 0){
			if(currentPiece.shiftAllowed(false))
				currentPiece.x--;
				das++;
		}
		if(previousInputs.contains(left)){
			if(++das >= 8)
				while(currentPiece.shiftAllowed(false))
					currentPiece.x--;
		}
	}
	public void shiftRight(){
		if(das == 0){
			if(currentPiece.shiftAllowed(true))
				currentPiece.x++;
				das++;
		}
		if(previousInputs.contains(right)){
			if(++das >= 8)
				while(currentPiece.shiftAllowed(true))
					currentPiece.x++;
		}
	}
	public boolean contains(int n, int[] a){
		boolean present = false;
		for(int j = 0; j < a.length; j++){
			if(n == a[j]) return true;
		}
		return false;
	}
}
