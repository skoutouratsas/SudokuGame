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


//kanoyme extend thn klash Jlabel etsi oste na kseroume se poia 8esh tou pinaka antistoixei to ka8e stoixeio, an einai editable kai se poio xrwma prepei an girisei otan ginei deselect
public class Cell extends JLabel {
	protected boolean editable;
	protected int posx,posy;
	protected Color origColor;
	
	
	protected Cell(Cell cpcell) {
		
		this.posx = cpcell.posx;
		this.posy = cpcell.posy;
		this.editable = cpcell.editable;
		this.origColor = cpcell.origColor;
	}
	
	protected Cell(String text, int horizontalAlignment,int posx,int posy) {
		super(text, horizontalAlignment);
		this.posx = posx;
		this.posy = posy;
		this.editable = true;
	}
	
	protected Cell() {
		super();
	}
	
	protected void setEditable(boolean ed) {
		
		this.editable = ed;
		
	}
	
	protected boolean isEditable() {
		
			return this.editable;
			
	}
	
	
}