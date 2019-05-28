package ce325.hw3;

import javax.swing.*;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.border.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.net.*;

//klash pou kanei extend ton mouseListener 
public class CustomMouseListener extends MouseAdapter{
	
	
	
	
	
	protected CustomMouseListener() {
		super();
		
		
		
	}
	
	public void mouseClicked(MouseEvent E){
		
		if(!Sudoku.verifyMode){
			if(Sudoku.selectedCell!=null){ 
				
				
				if(Sudoku.selectedCell.getBackground()!=Color.RED) {
					Sudoku.selectedCell.setBackground(Sudoku.selectedCell.origColor);
					for (int i=0; i<9; i++) {
						for (int j=0; j<9; j++) {
							if (Sudoku.initGame[i][j] != ((Cell)((E.getComponent()))).getText().charAt(0)) {

									Sudoku.sudoCells[i][j].setBackground(Sudoku.sudoCells[i][j].origColor);

							}
						}
					}
				}
				
			}
			else {

				Sudoku.selectedCell =new Cell((Cell)E.getComponent());
				
			}
			
			
			Sudoku.selectedCell = (Cell)E.getComponent();
			
			for (int i=0; i<9; i++) {
				for (int j=0; j<9; j++) {
					
					if (Sudoku.initGame[i][j] == ((Cell)((E.getComponent()))).getText().charAt(0)) {
						Sudoku.sudoCells[i][j].setBackground(new Color(255,255,200));
						
					}
				}
			}
			E.getComponent().setBackground(new Color(255,255,200));
		}
		else{
			
			if(Sudoku.selectedCell!=null){ 
					Sudoku.selectedCell.setBackground(Sudoku.selectedCell.origColor);
					for (int i=0; i<9; i++) {
						for (int j=0; j<9; j++) {
							if (Sudoku.initGame[i][j] != ((Cell)((E.getComponent()))).getText().charAt(0)) {
								if(Sudoku.sudoCells[i][j].origColor==Color.RED&&Sudoku.sudoCells[i][j].getBackground()!=Color.WHITE)
									Sudoku.sudoCells[i][j].setBackground(Color.BLUE);
								else
									
										Sudoku.sudoCells[i][j].setBackground(Sudoku.sudoCells[i][j].origColor);

							}
						}
					}
				
				
			}
			else {

				Sudoku.selectedCell =new Cell((Cell)E.getComponent());
				
			}
			
			
			Sudoku.selectedCell = (Cell)E.getComponent();
			
			for (int i=0; i<9; i++) {
				for (int j=0; j<9; j++) {
					
					if (Sudoku.initGame[i][j] == ((Cell)((E.getComponent()))).getText().charAt(0)) {
						Sudoku.sudoCells[i][j].setBackground(new Color(255,255,200));
						
					}
				}
			}
			E.getComponent().setBackground(new Color(255,255,200));
		}
			
			
			
		}
		
		
	} 

	
	
	
	
	
	
	
	
