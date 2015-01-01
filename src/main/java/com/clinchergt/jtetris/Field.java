package com.clinchergt.jtetris;

import java.util.Arrays;
public class Field{
	private boolean[][] field;
	public Field(){
		field = new boolean[10][21];
		for(int i = 0; i < 10; i++){
			field[i][20] = true;
		}
	}
	public int clearLines(){
		int linesCleared = 0;
		for(int i = 19; i >= 0; i--){
			if(field[0][i] && field[1][i] && field[2][i] && field[3][i] && field[4][i] &&
			   field[5][i] && field[6][i] && field[7][i] && field[8][i] && field[9][i]){
				
				for(int j = i; j >= 1; j--){
					for(int k = 0; k < 10; k++){
						field[k][j] = field[k][j - 1];
					}
				}
				for(int j = 0; j < 10; j++){
					field[j][0] = false;
				}
				i++;
				linesCleared++;
			}
		}
		return linesCleared;
	}
	public boolean[][] getField(){
		return field;
	}
	public int placePiece(Piece p){
		for(int i = 0; i <20; i++){
			if((field[p.x + p.xblocks[0]][i + p.yblocks[0]] ||
				field[p.x + p.xblocks[1]][i + p.yblocks[1]] ||
				field[p.x + p.xblocks[2]][i + p.yblocks[2]] ||
				field[p.x + p.xblocks[3]][i + p.yblocks[3]])){
					return i;
				}
		}
		return -1;
	}
	public void lockPiece(Piece p, int i){
		for(int j = 0; j < 4; j++){
			field[p.x + p.xblocks[j]][i - 1 + p.yblocks[j]] = true;
		}
	}
	
}