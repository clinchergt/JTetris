package com.clinchergt.jtetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
public class Renderer extends JFrame{
	public static void main(String[] args){
		new Renderer();
	}
	public Renderer(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(550, 620);
		this.setResizable(false);
		this.setTitle("JTetris");
		this.setLocation(300, 70);
		this.setVisible(true);
		Image block;
		this.createBufferStrategy(2);
		BufferStrategy buffer = getBufferStrategy();
		Graphics g = null;
		Keys keys = new Keys();
		JTetris game = new JTetris();
		double DEFAULTFRAME = 16666666.666666666667;
		long frametime = System.nanoTime();
		long previoustime = frametime;
		long executionTime = 0;
		long waitTime;
		int timeSecs = 0, timeMillis = 0;
		String placeHolder = "";
		Font font = new Font("Tahoma", Font.BOLD, 40);
		this.addKeyListener(keys);
		while(true){
			game.process(keys.getInputs());
			g = buffer.getDrawGraphics();
			block = (new ImageIcon(getClass().getResource("/resources/blocks.jpg"))).getImage();
			g.setColor(this.getBackground());
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(Color.BLACK);
			g.fill3DRect(26, 122, 200, 400, true); //field
			g.fill3DRect(25, 70, 53, 45, true); //hold
			g.fill3DRect(80, 55, 90, 60, true); //previews
			g.fill3DRect(170, 75, 85, 40, true);
			for(int i = 0; i < 4; i++){
				renderPiece(g, block, game.currentPiece, 26, 22, 20); //ghost piece
				renderPiece(g, block, game.currentPiece, 26,122, 20); //current piece

				g.drawImage(block, //nextOne
							(game.nextOne.x * 20) + 26 + (game.nextOne.xblocks[i] * 20), 65 + (game.nextOne.yblocks[i] * 20),
							(game.nextOne.x * 20) + 46 + (game.nextOne.xblocks[i] * 20), 85 + (game.nextOne.yblocks[i] * 20),
							game.nextOne.piece * 20, 0,
							(game.nextOne.piece * 20) + 20, 20, null);
				g.drawImage(block, //nexttwo
							170 + (game.nextTwo.xblocks[i] * 10), 85 + (game.nextTwo.yblocks[i] * 10),
							180 + (game.nextTwo.xblocks[i] * 10), 95 + (game.nextTwo.yblocks[i] * 10),
							game.nextTwo.piece * 20, 0,
							(game.nextTwo.piece * 20) + 20, 20, null);
				g.drawImage(block, //nextthree
							210 + (game.nextThree.xblocks[i] * 10), 85 + (game.nextThree.yblocks[i] * 10),
							220 + (game.nextThree.xblocks[i] * 10), 95 + (game.nextThree.yblocks[i] * 10),
							game.nextThree.piece * 20, 0,
							(game.nextThree.piece * 20) + 20, 20, null);
				if(game.holdPiece != null)
					g.drawImage(block, //holdpiece
								35 + (game.holdPiece.xblocks[i] * 10), 85 + (game.holdPiece.yblocks[i] * 10),
								45 + (game.holdPiece.xblocks[i] * 10), 95 + (game.holdPiece.yblocks[i] * 10),
								game.holdPiece.piece * 20, 0,
								(game.holdPiece.piece * 20) + 20, 20, null);
			}
			g.setColor(Color.GRAY);
			for(int i = 0; i < 10; i++){
				for(int j = 4; j < 24; j++){
					if(game.field.getField()[i][j])
						g.fill3DRect(26 + i * 20, 42 + (j * 20), 20, 20, true);
				}
			}
			g.setFont(font);
			g.setColor(Color.BLACK);
			g.drawString("" + game.lines, 240, 440);
			font = new Font("Tahoma", Font.BOLD, 30);
			g.setFont(font);
			if(game.countdown == 0 && !game.gameOver){
				timeSecs = (int)(System.currentTimeMillis() - game.time) / 1000;
				timeMillis = (int)(System.currentTimeMillis() - game.time) % 1000;
				if(timeMillis < 10){
					placeHolder = ".00";
				}else if(timeMillis < 100){
					placeHolder = ".0";
				}else{
					placeHolder = ".";
				}
			}
			g.drawString(timeSecs + placeHolder + timeMillis, 80, 560);
			if(game.countdown > 0){
				g.setColor(Color.WHITE);
				g.drawString("READY?", 70, 310);
			}
			frametime = System.nanoTime();
			executionTime = frametime - previoustime;
			waitTime = (long)DEFAULTFRAME - executionTime;
			g.dispose();
			buffer.show();
			try{
				Thread.sleep(
	                (long)(waitTime / 1000000),
	                (int)(waitTime % 1000000));
			}catch(Exception e){}
			previoustime = System.nanoTime();
		}
	}

	public void renderPiece(Graphics g, Image fill, Piece piece, int off1, int off2, int scale){
		for(int i = 0; i < 4; i++){
			g.drawImage(fill,
				(piece.x * scale) + off1 + (piece.xblocks[i] * scale), off2 + (piece.yblocks[i] * scale),
				(piece.x * scale) + off1 + scale + (piece.xblocks[i] * scale), off2 + scale + (piece.yblocks[i] * scale),
				piece.piece * scale, 0,
				(piece.piece * scale) + 20, 20, null);
		}
	}
}
