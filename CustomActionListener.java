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
import java.util.*;
// klash poy kanei extend ton action Listener 
public class CustomActionListener implements ActionListener {
	
	protected CustomActionListener() {
		super();
		
	}
	
	
	public void actionPerformed(ActionEvent E) {
		if ((E.getSource() == Sudoku.ez) || (E.getSource() == Sudoku.im) || (E.getSource() == Sudoku.ex)) {
			Sudoku.refreshBoard();
			Sudoku.selectedCell = null;
			Scanner sc;
			String line;
			String link;
			if(E.getSource() == Sudoku.ez)
				link="easy";
			else if(E.getSource() == Sudoku.im)
				link="intermediate";
			else
				link="expert";

	
		
		
			try{
				URL url = new URL("http://gthanos.inf.uth.gr/~gthanos/sudoku/exec.php?difficulty="+link);
				sc = new Scanner(url.openStream());
			
				int i=0;
				while(sc.hasNext()){
					line = sc.next();
					for(int j=0; j<9; j++){
						Sudoku.initGame[i][j] = line.charAt(j);
						Sudoku.solvedGame[i][j] = line.charAt(j);
					}
					i++;
				}
			}
			catch(MalformedURLException ex){
				System.out.println("Malformed URL exception!\n");
			}
			catch(IOException ex){
				System.out.println("IO Exception!\n");
			}
			
			for (int i=0; i<9; i++) {
				for (int j=0; j<9; j++) {
					if(Sudoku.initGame[i][j]=='.'){
						Sudoku.sudoCells[i][j].setText(" ");
						Sudoku.sudoCells[i][j].setBackground(Color.WHITE);
						Sudoku.sudoCells[i][j].origColor=Color.WHITE;
					}
					else{
						Sudoku.sudoCells[i][j].setText(Character.toString(Sudoku.initGame[i][j]));
						Sudoku.sudoCells[i][j].setBackground(Color.LIGHT_GRAY);
						Sudoku.sudoCells[i][j].origColor=Color.LIGHT_GRAY;
						Sudoku.sudoCells[i][j].setEditable(false);
					}
					
				}
			}
			
			Sudoku.solve();

			
			
			return;
			
		}
		
		for(int k=0; k<9; k++){
			if(E.getSource() == Sudoku.control[k]){
				if(Sudoku.selectedCell.isEditable()){
						Sudoku.actionHistory.push(new Action(Sudoku.selectedCell.posx, Sudoku.selectedCell.posy, (Sudoku.selectedCell.getText()).charAt(0)));
						
						Sudoku.selectedCell.setText(Integer.toString(k+1));
						Sudoku.initGame[Sudoku.selectedCell.posx][Sudoku.selectedCell.posy]=(Integer.toString(k+1)).charAt(0);
						
						if(!Sudoku.checkConstraints(Sudoku.initGame,Sudoku.selectedCell.posx, Sudoku.selectedCell.posy, (Integer.toString(k+1)).charAt(0))){
							if(Sudoku.verifyMode){
								if( Sudoku.initGame[Sudoku.selectedCell.posx][Sudoku.selectedCell.posy]!= Sudoku.solvedGame[Sudoku.selectedCell.posx][Sudoku.selectedCell.posy]){
									Sudoku.selectedCell.setBackground(Color.BLUE);
									Sudoku.selectedCell.origColor=Color.RED;
								}
								else{
									Sudoku.selectedCell.setBackground(Color.WHITE);
									Sudoku.selectedCell.origColor=Color.WHITE;
								}
							}
							else{
								Sudoku.selectedCell.setBackground(Color.RED);
								Sudoku.selectedCell.origColor=Color.RED;
								
							}
						}
						else{
							if(Sudoku.verifyMode ){
								if(Sudoku.initGame[Sudoku.selectedCell.posx][Sudoku.selectedCell.posy]!= Sudoku.solvedGame[Sudoku.selectedCell.posx][Sudoku.selectedCell.posy]){
									Sudoku.selectedCell.setBackground(Color.BLUE);
									Sudoku.selectedCell.origColor=Color.BLUE;
								}
								else{
									Sudoku.selectedCell.setBackground(Color.WHITE);
									Sudoku.selectedCell.origColor=Color.WHITE;
									
								}
							}
							else{
								Sudoku.selectedCell.setBackground(Color.WHITE);
								Sudoku.selectedCell.origColor=Color.WHITE;
							}
						}
						
						
				}

				if(Arrays.deepEquals(Sudoku.initGame,Sudoku.solvedGame)){
					Component[] com = Sudoku.controlPanel.getComponents();
					for (int i=0; i<com.length; i++) {
						com[i].setEnabled(false);
					}
					JOptionPane.showMessageDialog(null, "Congratulations! You solved the puzzle. ", "Success!" , JOptionPane.INFORMATION_MESSAGE);
				}
					
				return;
			}
		}
		
		if (E.getSource()== Sudoku.eraser){
			if(Sudoku.selectedCell.isEditable()){
					Sudoku.selectedCell.setBackground(Color.WHITE);
					Sudoku.selectedCell.origColor=Color.WHITE;
					Sudoku.actionHistory.push(new Action(Sudoku.selectedCell.posx, Sudoku.selectedCell.posy, (Sudoku.selectedCell.getText()).charAt(0)));
					
					Sudoku.selectedCell.setText(" ");
					Sudoku.initGame[Sudoku.selectedCell.posx][Sudoku.selectedCell.posy]=('.');
				}
				return;
		}
		
		if(E.getSource() == Sudoku.undo){
			if(!Sudoku.actionHistory.isEmpty()){
				Action prev = Sudoku.actionHistory.pop();
				
				if(prev.prevVal == '.'){
					Sudoku.sudoCells[prev.x][prev.y].setText(" ");
					Sudoku.sudoCells[prev.x][prev.y].setBackground(Color.WHITE);
					Sudoku.sudoCells[prev.x][prev.y].origColor=Color.WHITE;
				}
				else{
					Sudoku.sudoCells[prev.x][prev.y].setText(Character.toString(prev.prevVal));
						if(!Sudoku.checkConstraints(Sudoku.initGame,prev.x, prev.y, prev.prevVal)) {
							if(Sudoku.verifyMode){
								if(Sudoku.initGame[prev.x][prev.y]!= Sudoku.solvedGame[prev.x][prev.y]){
									Sudoku.sudoCells[prev.x][prev.y].setBackground(Color.BLUE);
									Sudoku.sudoCells[prev.x][prev.y].origColor=Color.RED;
								}
								else{
									Sudoku.sudoCells[prev.x][prev.y].setBackground(Color.WHITE);
									Sudoku.sudoCells[prev.x][prev.y].origColor=Color.RED;
								}
							}
							else{
								Sudoku.sudoCells[prev.x][prev.y].setBackground(Color.RED);
								Sudoku.sudoCells[prev.x][prev.y].origColor=Color.RED;
							}
						}
						else {
							if(Sudoku.verifyMode){
								if(Sudoku.initGame[prev.x][prev.y]!= Sudoku.solvedGame[prev.x][prev.y]){
								Sudoku.sudoCells[prev.x][prev.y].setBackground(Color.BLUE);
								Sudoku.sudoCells[prev.x][prev.y].origColor=Color.BLUE;
								}
								else{
									Sudoku.sudoCells[prev.x][prev.y].setBackground(Color.WHITE);
									Sudoku.sudoCells[prev.x][prev.y].origColor=Color.WHITE;
								}
							}
							else{
								Sudoku.sudoCells[prev.x][prev.y].setBackground(Color.WHITE);
								Sudoku.sudoCells[prev.x][prev.y].origColor=Color.WHITE;
							}
						}
				}
				
				Sudoku.initGame[prev.x][prev.y] = prev.prevVal;
			}
			
			return;
		}
		

		
		
		
		
		
		
		
		
		
		
		if(E.getSource() == Sudoku.solve) {
			
			for (int i=0; i<9; i++) {
				for (int j=0; j<9; j++) {
					Sudoku.sudoCells[i][j].setText(Character.toString(Sudoku.solvedGame[i][j]));
					Sudoku.initGame[i][j] = Sudoku.solvedGame[i][j];
					if (Sudoku.sudoCells[i][j].isEditable()) {
						Sudoku.sudoCells[i][j].setBackground(Color.WHITE);
					}
					else {
						Sudoku.sudoCells[i][j].setBackground(Color.LIGHT_GRAY);
					}
				}
			}
			
			Component[] com = Sudoku.controlPanel.getComponents();
			for (int i=0; i<com.length; i++) {
				com[i].setEnabled(false);
			}
		
		}
	}
	
	
	
}