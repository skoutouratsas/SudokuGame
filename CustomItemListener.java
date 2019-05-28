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

//klash pou kanei extend ton itemListener
public class CustomItemListener implements ItemListener {
	
	
	protected CustomItemListener() {
		super();
	}


	public void itemStateChanged (ItemEvent E) {
		
		if (E.getItemSelectable() == Sudoku.verify) {
			
			
			if (E.getStateChange() == ItemEvent.SELECTED) {
				Sudoku.verifyMode = true;
				for (int i=0; i<9; i++) {
					for (int j=0; j<9; j++) {
						
						if (Sudoku.initGame[i][j] != Sudoku.solvedGame[i][j] && Sudoku.initGame[i][j] != '.') {
							Sudoku.sudoCells[i][j].setBackground(Color.BLUE);
							if(Sudoku.sudoCells[i][j].origColor != Color.RED){
								Sudoku.sudoCells[i][j].origColor = Color.BLUE;
							}
						}
						
						
						
					}
					
				}
				
			}
			else {
				Sudoku.verifyMode = false;
				for (int i=0; i<9; i++) {
					for (int j=0; j<9; j++) {
						
						if (Sudoku.sudoCells[i][j].origColor == Color.BLUE) {
							
							Sudoku.sudoCells[i][j].setBackground(Color.WHITE);
							Sudoku.sudoCells[i][j].origColor = Color.WHITE;
						}
						else if(Sudoku.sudoCells[i][j].origColor == Color.RED){
							Sudoku.sudoCells[i][j].setBackground(Sudoku.sudoCells[i][j].origColor);
							
						}
						
						
						
					}
					
				}
				
				
			}
			
			
			
			
			
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}