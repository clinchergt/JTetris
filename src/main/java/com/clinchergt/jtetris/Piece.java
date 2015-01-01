package com.clinchergt.jtetris;

public class Piece{
	int piece, cstate, x, length;
	int[] xblocks, yblocks;
	public static final int
		I = 0,
		T = 1,
		Z = 2,
		S = 3,
		J = 4,
		L = 5,
		O = 6;
	public static final int[][][] X_STATES = {
		{{0, 1, 2, 3}, {2, 2, 2, 2}, {0, 1, 2, 3}, {1, 1, 1, 1}}, //I
		{{0, 1, 1, 2}, {1, 1, 1, 2}, {0, 1, 1, 2}, {0, 1, 1, 1}}, //T
		{{0, 1, 1, 2}, {1, 1, 2, 2}, {0, 1, 1, 2}, {0, 0, 1, 1}}, //Z
		{{0, 1, 1, 2}, {1, 1, 2, 2}, {0, 1, 1, 2}, {0, 0, 1, 1}}, //S
		{{0, 0, 1, 2}, {1, 1, 1, 2}, {0, 1, 2, 2}, {0, 1, 1, 1}}, //J
		{{0, 1, 2, 2}, {1, 1, 1, 2}, {0, 0, 1, 2}, {0, 1, 1, 1}}, //L
		{{1, 2, 1, 2}, {1, 2, 1, 2}, {1, 2, 1, 2}, {1, 2, 1, 2}} //O
	};
	public static final int[][][] Y_STATES = {
		{{1, 1, 1, 1}, {3, 2, 1, 0}, {2, 2, 2, 2}, {1, 2, 3, 0}}, //I
		{{1, 0, 1, 1}, {0, 1, 2, 1}, {1, 1, 2, 1}, {1, 0, 1, 2}}, //T
		{{0, 0, 1, 1}, {2, 1, 1, 0}, {1, 1, 2, 2}, {2, 1, 1, 0}}, //Z
		{{1, 1, 0, 0}, {0, 1, 1, 2}, {2, 2, 1, 1}, {0, 1, 1, 2}}, //S
		{{0, 1, 1, 1}, {0, 1, 2, 0}, {1, 1, 1, 2}, {2, 0, 1, 2}}, //J
		{{1, 1, 1, 0}, {0, 1, 2, 2}, {2, 1, 1, 1}, {0, 0, 1, 2}}, //L
		{{0, 0, 1, 1}, {0, 0, 1, 1}, {0, 0, 1, 1}, {0, 0, 1, 1}} //O
	};
	public Piece(int n){
		piece = n;
		cstate = 0;
		xblocks = new int[4];
		yblocks = new int[4];
		for(int i = 0; i < xblocks.length; i++)
			xblocks[i] = X_STATES[piece][0][i];
		for(int i = 0; i < yblocks.length; i++)
			yblocks[i] = Y_STATES[piece][0][i];
		x = 3;
		if(n == 0){
			length = 4;
		}else if(n == 6){
			length = 2;
		}else{
			length = 3;
		}
	}
	public Piece(Piece p){
		piece = p.piece;
		cstate = p.cstate;
		xblocks = p.xblocks;
		yblocks = p.yblocks;
		x = p.x;
		length = p.length;
	}
	public void rotate(boolean right){
		if(right)
			rotateRight();
		else
			rotateLeft();
		switch(piece){
			case 0: if(length == 4) length = 1;
					else  length = 4;
					break;
			case 6: break;
			default: if(length == 3) length = 2;
					else  length = 3;
					break;
		}
	}
	public void rotateRight(){
		if(cstate != 3)
			cstate++;
		else
			cstate = 0;
		for(int i = 0; i < xblocks.length; i++)
			xblocks[i] = X_STATES[piece][cstate][i];
		for(int i = 0; i < yblocks.length; i++)
			yblocks[i] = Y_STATES[piece][cstate][i];
	}
	public void rotateLeft(){
		if(cstate != 0)
			cstate--;
		else
			cstate = 3;
		for(int i = 0; i < xblocks.length; i++)
			xblocks[i] = X_STATES[piece][cstate][i];
		for(int i = 0; i < yblocks.length; i++)
			yblocks[i] = Y_STATES[piece][cstate][i];
	}
	public boolean shiftAllowed(boolean right){
		if(right){
			for(int i = 0; i < 4; i++){
				if(x + 1 + xblocks[i] >= 10)
					return false;
			}
		}else{
			for(int i = 0; i < 4; i++){
				 if(x - 1 + xblocks[i] < 0)
					return false;
			}
		}
		return true;
	}
	public boolean isOK(){
		for(int i = 0; i < 4; i++){
			if(x + xblocks[i] >= 10 || x + xblocks[i] < 0)
				return false;
		}
		return true;
	}
}