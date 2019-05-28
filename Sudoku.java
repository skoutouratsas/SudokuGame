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



public class Sudoku extends JFrame {
	protected static Stack<Action> actionHistory = new Stack<Action>();
	
	protected static Cell[][] sudoCells = new Cell[9][9];		//custom JLabels 9x9
	protected static char[][] initGame = new char[9][9];
	protected static char[][] solvedGame = new char[9][9];
	protected static JPanel[][] sudoGrid = new JPanel[3][3];		//panel pou periexei 9 Jlabels (subgrid) 
	protected static JPanel controlPanel = new JPanel();				//panel gia ta koumpia elegxou
	protected static JPanel bigPanel = new JPanel();						//periexei to sudoku 9x9 JLabels
	
	protected static JButton control[] = new JButton[9];				//koumpia ari8mwn
	protected static JButton eraser;
	protected static JButton undo;
	protected static JButton solve;
	
	protected static JCheckBox verify;
	
	protected static JMenuItem ez = new JMenuItem("Easy");
	protected static JMenuItem im = new JMenuItem("Intermediate");
	protected static JMenuItem ex = new JMenuItem("Expert");
	
	protected static JFrame header = new JFrame(" Sudoku ");
	protected static JMenuBar menu = new JMenuBar();
	protected static JMenu newGame = new JMenu("New Game");
	
	protected static Cell selectedCell;
	
	protected static boolean verifyMode;			//eidiko mode opou i mono sigkrisi pou ginetai einai me ton lummeno pinaka
	
	protected static void createAndShowGUI() {
		
			//hooks
			CustomItemListener il = new CustomItemListener();
			
			CustomActionListener ac = new CustomActionListener();
			ez.addActionListener(ac);
			im.addActionListener(ac);
			ex.addActionListener(ac);
			
			
			
			
			header.setLayout(new BorderLayout());
			
			

			
			menu.add(newGame);//epiloges ekkinisis
			
			newGame.add(ez);
			newGame.add(im);
			newGame.add(ex);
			
			header.setJMenuBar(menu);
			header.setSize(1000,1000);
			header.setVisible(true);
			
		
		bigPanel.setLayout(new GridLayout(3,3));    
		header.add(bigPanel, BorderLayout.CENTER);
		
		controlPanel.setLayout(new GridLayout(0,8,5,5));    
		header.add(controlPanel, BorderLayout.SOUTH);

		//koumpia allagis periexomenou
		for (int i=0; i<9; i++) {
			control[i] = new JButton(Integer.toString(i+1));
			controlPanel.add(control[i]);
			control[i].addActionListener(ac);
			
		}
		//koumpi diagrafis
		ImageIcon icon1 = new ImageIcon("eraser.png");
		Image image = icon1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
		icon1 = new ImageIcon(image);
		
		eraser = new JButton(icon1);
		controlPanel.add(eraser);
		eraser.addActionListener(ac);
		
		//koumpi undo
		ImageIcon icon2 = new ImageIcon("undo.png");
		Image image1 = icon2.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
		icon2 = new ImageIcon(image1);
		
		undo = new JButton(icon2);
		controlPanel.add(undo);
		undo.addActionListener(ac);
		
		//checkbox gia verify mode
		verify = new JCheckBox("Verify Against Solution");
		controlPanel.add(verify);
		verifyMode = false;
		verify.addItemListener(il);
		
		//to koumpi epilisis
		ImageIcon icon3 = new ImageIcon("rubik.png");
		Image image3 = icon3.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
		icon3 = new ImageIcon(image3);
		
		solve = new JButton(icon3);
		controlPanel.add(solve);
		solve.addActionListener(ac);
		
		
		
		//Ftiaxnoume to graphiko periballon.Enan 3x3 pinaka 9 fores.
		for(int i=0;i<3;i++){
			for(int j=0; j<3; j++){
				sudoGrid[i][j] = new JPanel();
				sudoGrid[i][j].setLayout(new GridLayout(3,3));
				bigPanel.add(sudoGrid[i][j]);
				sudoGrid[i][j].setBorder(new EmptyBorder(5,5,5,5));
			}
		}
		CustomMouseListener ml = new CustomMouseListener();
			
		
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				sudoCells[i][j] = new Cell(" ",SwingConstants.CENTER,i,j);
				sudoGrid[i/3][j/3].add(sudoCells[i][j]);
				sudoCells[i][j].setOpaque(true);
				sudoCells[i][j].setBorder(new LineBorder(Color.BLACK));
				
				sudoCells[i][j].setFont(new Font("Monospaced", Font.BOLD, 20));
				sudoCells[i][j].setForeground(Color.BLACK);
				sudoCells[i][j].setBackground(Color.WHITE);
				sudoCells[i][j].addMouseListener(ml);
			}
			}
			
			header.pack();		


	}
	
	
	//anadromiki epilisi tou sudoku me brute force
	protected static boolean solve() {
		for(int row=0; row<9 ; row++){
			for(int col=0; col<9 ; col++){
				if(solvedGame[row][col] == '.'){
					for (int k=1; k<=9; k++){
						solvedGame[row][col] = Integer.toString(k).charAt(0);
						if(checkConstraints(solvedGame,row,col,Integer.toString(k).charAt(0)) && solve()){
							return true;
						}
						solvedGame[row][col]= '.';
					}
					return false;
				}
				
			}
			
			
		}
		return true;
	}
	
	
	
	
	//elegxei an to value uparxei stin sigkekrimeni grammh
	protected static boolean rowConstraint(char[][] board,int row ,int column, char value){
		for( int k=0 ; k<9; k++){
			if(board[row][k]==value && k!=column){
				return false;
			}
			
		}
		return true;
		
	}
	
	//elegxei an to value uparxei stin sigkekrimeni sthlh
	protected static boolean columnConstraint(char[][] board, int row ,int column, char value){
		for( int k=0 ; k<9; k++){
			if(board[k][column]==value && k!=row){
				return false;
			}
			
		}
		return true;
		
	}
	
	//elegxei an to value uparxei sto sigkekrimeno subgrid
	protected static boolean subGridConstraint(char[][] board, int row  ,int column, char value){
		
		int i,j;
		
		i = row/3;
		j = column/3;
		
		for (int k=i*3; k<i*3+3; k++) {
			for (int v=j*3; v<j*3+3; v++) {
				if(board[k][v]==value && k!=row && v!=column){
					return false;
				}
			}
		}
		
		return true;
	}
	
	// elegxei an to value einai nomimi pros8iki
	protected static boolean checkConstraints(char[][] board, int row  ,int column, char value){
		
		return (rowConstraint(board,row,column,value))&&(columnConstraint(board,row,column,value))&&(subGridConstraint(board,row,column,value));
		
	}
	
	//midenizei tous pinakes solved init, svinei ta JLabels kai kanei enable ta control components
	protected static void refreshBoard(){
		for(int i=0;i<9;i++){
			for(int j=0; j<9; j++){
						Sudoku.initGame[i][j] = '.';
						Sudoku.solvedGame[i][j] = '.';
						sudoCells[i][j].setText(" ");
						sudoCells[i][j].setBackground(Color.WHITE);
						sudoCells[i][j].origColor=Color.WHITE;
						sudoCells[i][j].editable = true;
				}
			}
			Component[] com = controlPanel.getComponents();
			for (int i=0; i<com.length; i++) {
				com[i].setEnabled(true);
			}
	
	}
	
	
	



	
	public static void main(String args[]) {
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        createAndShowGUI();
      } 
    });
		
	}
} 