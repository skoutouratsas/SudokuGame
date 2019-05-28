package ce325.hw3;

//klash opou kratame tin 8esh ston pinaka kai tin proigoumeni timi pou eixe(xrisi stin stack energeiwn)
public class Action {
	
	protected int x,y;
	protected char prevVal;
	
	protected Action(int x,int y,char prevVal) {
		this.x = x;
		this.y = y;
		if(prevVal==' ')
			this.prevVal = '.';
		else
			this.prevVal = prevVal;
	}
	
}