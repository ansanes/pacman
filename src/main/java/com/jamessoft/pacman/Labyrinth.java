package com.jamessoft.pacman;

public class Labyrinth {

	private final int IMAGE_WIDTH = 16;
	private final int IMAGE_HEIGHT = 16;
	
	// Labyrinth array
	
    public String LaberintoActual[][] = {
        {"N", "N", "N","CSI","PI","PI","PI","PI","PI","PI","PI","PI","CII","N","N","N","PN","N","PS","N","N","N","CSI","PI","PI","PI","PI","CAII","CASI","PI","PI","PI","PI","CII","N","N"},
        {"N", "N", "N","PN","P","P","PG","P","P","P","P","P","PS","N","N","N","PN","N","PS","N","N","N","PN","P","P","P","PG","PC","LSC","P","P","P","P","PS","N","N"},
        {"N", "N", "N","PN","P","CUSI","LIC","CUII","P","CUSI","CUII","P","PS","N","N","N","PN","N","PS","N","N","N","PN","P","CUSI","CUII","P","CUSD","CUID","P","CUSI","CUII","P","PS","VSI","VII"},
        {"N", "N", "N","PN","P","PC","N","LSC","P","PC","LSC","P","PS","N","N","N","PN","N","PS","N","N","N","PN","P","PC","LSC","P","P","P","P","PC","LSC","P","PS","VSD","VID"},
        {"N", "N", "N","PN","P","PC","N","LSC","P","PC","LSC","P","PS","N","N","N","PN","N","PS","N","N","N","PN","P","PC","CASD","LIC","LIC","CUII","P","PC","LSC","P","PS","N","N"},
        {"N", "N", "N","PN","P","CUSD","LDC","CUID","P","CUSD","CUID","P","CUSD","PI","PI","PI","CUID","N","CUSD","PI","PI","PI","CUID","P","CUSD","LDC","LDC","LDC","CUID","P","PC","LSC","P","PS","N","N"},
        {"N", "N", "N","PN","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","PC","LSC","P","PS","N","N"},
        {"N", "N", "N","PN","P","CUSI","LIC","CUII","P","CUSI","LIC","LIC","LIC","LIC","LIC","LIC","CUII","N","CUSI","LIC","LIC","LIC","CUII","P","CUSI","CUII","P","CUSI","LIC","LIC","CGID","LSC","P","PS","N","N"},
        {"N", "N", "N","PN","P","PC","N","LSC","P","CUSD","LDC","LDC","CGII","CGSI2","LDC","LDC","CUID","N","CUSD","LDC","LDC","LDC","CUID","P","PC","LSC","P","CUSD","LDC","LDC","CGII","LSC","P","PS","N","N"},
        {"H", "N", "N","PN","P","PC","N","LSC","P","P","P","P","PC","LSC","N","N","N","N","N","N","N","N","N","P","PC","LSC","P","P","P","P","PC","LSC","P","PS","N","N"},
        {"I", "N", "N","PN","P","PC","N","LSC","P","CUSI","CUII","P","PC","LSC","N","ESI","PID","PID","PID","EII","N","CUSI","CUII","P","PC","LSC","P","CUSI","CUII","P","PC","LSC","P","PS","N","N"},
        {"G", "N", "N","PN","P","CUSD","LDC","CUID","P","PC","LSC","P","CUSD","CUID","N","PSI","FR1","FR2","FR3","PIS","N","PC","LSC","P","CUSD","CUID","P","PC","LSC","P","CUSD","CUID","P","PS","N","N"},
        {"H", "N", "N","PN","P","P","P","P","P","PC","LSC","P","N","N","N","PSI","FR4","FR5","FR6","PIS","N","PC","LSC","P","P","P","P","PC","LSC","P","P","P","P","PS","N","N"},
        {"N", "N", "N","CGSD","LIC","LIC","LIC","CUII","P","PC","CASD","LIC","LIC","CUII","N","LS","FPINK1","FPINK2","FPINK3","PIS","N","PC","CASD","LIC","LIC","CUII","N","PC","CASD","LIC","LIC","CUII","P","PS","N","N"},
        {"S", "N", "N","CGSI","LDC","LDC","LDC","CUID","P","PC","CGSI2","LDC","LDC","CUID","N","LS","FPINK4","FPINK5","FPINK6","PIS","N","PC","CGSI2","LDC","LDC","CUID","N","PC","CGSI2","LDC","LDC","CUID","P","PS","N","N"},
        {"C", "N", "N","PN","P","P","P","P","P","PC","LSC","P","N","N","N","PSI","FN1","FN2","FN3","PIS","N","PC","LSC","P","P","P","P","PC","LSC","P","P","P","P","PS","N","N"},
        {"O", "N", "N","PN","P","CUSI","LIC","CUII","P","PC","LSC","P","CUSI","CUII","N","PSI","FN4","FN5","FN6","PIS","N","PC","LSC","P","CUSI","CUII","P","PC","LSC","P","CUSI","CUII","P","PS","N","N"},
        {"R", "N", "N","PN","P","PC","N","LSC","P","CUSD","CUID","P","PC","LSC","N","ESD","PDI","PDI","PDI","EID","N","CUSD","CUID","P","PC","LSC","P","CUSD","CUID","P","PC","LSC","P","PS","N","N"},
        {"E", "N", "N","PN","P","PC","N","LSC","P","P","P","P","PC","LSC","N","N","N","N","N","N","N","N","N","P","PC","LSC","P","P","P","P","PC","LSC","P","PS","N","N"},
        {"N", "N", "N","PN","P","PC","N","LSC","P","CUSI","LIC","LIC","CGID","CASD","LIC","LIC","CUII","N","CUSI","LIC","LIC","LIC","CUII","P","PC","LSC","P","CUSI","LIC","LIC","CGID","LSC","P","PS","N","N"},
        {"N", "N", "N","PN","P","CUSD","LDC","CUID","P","CUSD","LDC","LDC","LDC","LDC","LDC","LDC","CUID","N","CUSD","LDC","LDC","LDC","CUID","P","CUSD","CUID","P","CUSD","LDC","LDC","CGII","LSC","P","PS","N","N"},
        {"N", "N", "N","PN","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","PC","LSC","P","PS","N","N"},
        {"N", "N", "N","PN","P","CUSI","LIC","CUII","P","CUSI","CUII","P","CPSI","PD","PD","PD","CPII","N","CPSI","PD","PD","PD","CPII","P","CUSI","LIC","LIC","LIC","CUII","P","PC","LSC","P","PS","N","N"},
        {"N", "N", "N","PN","P","PC","N","LSC","P","PC","LSC","P","PS","N","N","N","PN","N","PS","N","N","N","PN","P","PC","CGSI2","LDC","LDC","CUID","P","PC","LSC","P","PS","N","N"},
        {"N", "N", "N","PN","P","PC","N","LSC","P","PC","LSC","P","PS","N","N","N","PN","N","PS","N","N","N","PN","P","PC","LSC","P","P","P","P","PC","LSC","P","PS","CEREZA1","CEREZA2"},
        {"N", "N", "N","PN","P","CUSD","LDC","CUID","P","CUSD","CUID","P","PS","N","N","N","PN","N","PS","N","N","N","PN","P","CUSD","CUID","P","CUSI","CUII","P","CUSD","CUID","P","PS","CEREZA3","CEREZA4"},
        {"N", "N", "N","PN","P","P","PG","P","P","P","P","P","PS","N","N","N","PN","N","PS","N","N","N","PN","P","P","P","PG","PC","LSC","P","P","P","P","PS","N","N"},
        {"N", "N", "N","CSD","PD","PD","PD","PD","PD","PD","PD","PD","CID","N","N","N","PN","N","PS","N","N","N","CSD","PD","PD","PD","PD","CGID2","CGSD2","PD","PD","PD","PD","CID","N","N"},
    };
    
	// labyrint array types
	// 
	// -1: wall.
	// 0: empty.
	// 1: small ball.
	// 2: big ball.
    
    public int cellType [][]={
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,1,1,2,1,1,1,1,1,-1,-1,-1,-1,-1,0,-1,-1,-1,-1,-1,1,1,1,2,-1,-1,1,1,1,1,-1,-1,-1},
        {-1,-1,-1,-1,1,-1,-1,-1,1,-1,-1,1,-1,-1,-1,-1,-1,0,-1,-1,-1,-1,-1,1,-1,-1,1,-1,-1,1,-1,-1,1,-1,-1,-1},
        {-1,-1,-1,-1,1,-1,-1,-1,1,-1,-1,1,-1,-1,-1,-1,-1,0,-1,-1,-1,-1,-1,1,-1,-1,1,1,1,1,-1,-1,1,-1,-1,-1},
        {-1,-1,-1,-1,1,-1,-1,-1,1,-1,-1,1,-1,-1,-1,-1,-1,0,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,1,-1,-1,1,-1,-1,-1},
        {-1,-1,-1,-1,1,-1,-1,-1,1,-1,-1,1,-1,-1,-1,-1,-1,0,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,1,-1,-1,1,-1,-1,-1},
        {-1,-1,-1,-1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,-1,-1,1,-1,-1,-1},
        {-1,-1,-1,-1,1,-1,-1,-1,1,-1,-1,-1,-1,-1,-1,-1,-1,0,-1,-1,-1,-1,-1,1,-1,-1,1,-1,-1,-1,-1,-1,1,-1,-1,-1},
        {-1,-1,-1,-1,1,-1,-1,-1,1,-1,-1,-1,-1,-1,-1,-1,-1,0,-1,-1,-1,-1,-1,1,-1,-1,1,-1,-1,-1,-1,-1,1,-1,-1,-1},
        {-1,-1,-1,-1,1,-1,-1,-1,1,1,1,1,-1,-1,0,0,0,0,0,0,0,0,0,1,-1,-1,1,1,1,1,-1,-1,1,-1,-1,-1},
        {-1,-1,-1,-1,1,-1,-1,-1,1,-1,-1,1,-1,-1,0,-1,-1,-1,-1,-1,0,-1,-1,1,-1,-1,1,-1,-1,1,-1,-1,1,-1,-1,-1},
        {-1,-1,-1,-1,1,-1,-1,-1,1,-1,-1,1,-1,-1,0,-1,-1,-1,-1,-1,0,-1,-1,1,-1,-1,1,-1,-1,1,-1,-1,1,-1,-1,-1},
        {-1,-1,-1,-1,1,1,1,1,1,-1,-1,1,0,0,0,-1,-1,-1,-1,-1,0,-1,-1,1,1,1,1,-1,-1,1,1,1,1,-1,-1,-1},
        {-1,-1,-1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,0,-1,-1,-1,-1,-1,0,-1,-1,-1,-1,-1,0,-1,-1,-1,-1,-1,1,-1,-1,-1},
        {-1,-1,-1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,0,-1,-1,-1,-1,-1,0,-1,-1,-1,-1,-1,0,-1,-1,-1,-1,-1,1,-1,-1,-1},
        {-1,-1,-1,-1,1,1,1,1,1,-1,-1,1,0,0,0,-1,-1,-1,-1,-1,0,-1,-1,1,1,1,1,-1,-1,1,1,1,1,-1,-1,-1},
        {-1,-1,-1,-1,1,-1,-1,-1,1,-1,-1,1,-1,-1,0,-1,-1,-1,-1,-1,0,-1,-1,1,-1,-1,1,-1,-1,1,-1,-1,1,-1,-1,-1},
        {-1,-1,-1,-1,1,-1,-1,-1,1,-1,-1,1,-1,-1,0,-1,-1,-1,-1,-1,0,-1,-1,1,-1,-1,1,-1,-1,1,-1,-1,1,-1,-1,-1},
        {-1,-1,-1,-1,1,-1,-1,-1,1,1,1,1,-1,-1,0,0,0,0,0,0,0,0,0,1,-1,-1,1,1,1,1,-1,-1,1,-1,-1,-1},
        {-1,-1,-1,-1,1,-1,-1,-1,1,-1,-1,-1,-1,-1,-1,-1,-1,0,-1,-1,-1,-1,-1,1,-1,-1,1,-1,-1,-1,-1,-1,1,-1,-1,-1},
        {-1,-1,-1,-1,1,-1,-1,-1,1,-1,-1,-1,-1,-1,-1,-1,-1,0,-1,-1,-1,-1,-1,1,-1,-1,1,-1,-1,-1,-1,-1,1,-1,-1,-1},
        {-1,-1,-1,-1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,-1,-1,1,-1,-1,-1},
        {-1,-1,-1,-1,1,-1,-1,-1,1,-1,-1,1,-1,-1,-1,-1,-1,0,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,1,-1,-1,1,-1,-1,-1},
        {-1,-1,-1,-1,1,-1,-1,-1,1,-1,-1,1,-1,-1,-1,-1,-1,0,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,1,-1,-1,1,-1,-1,-1},
        {-1,-1,-1,-1,1,-1,-1,-1,1,-1,-1,1,-1,-1,-1,-1,-1,0,-1,-1,-1,-1,-1,1,-1,-1,1,1,1,1,-1,-1,1,-1,-1,-1},
        {-1,-1,-1,-1,1,-1,-1,-1,1,-1,-1,1,-1,-1,-1,-1,-1,0,-1,-1,-1,-1,-1,1,-1,-1,1,-1,-1,1,-1,-1,1,-1,-1,-1},
        {-1,-1,-1,-1,1,1,2,1,1,1,1,1,-1,-1,-1,-1,-1,0,-1,-1,-1,-1,-1,1,1,1,2,-1,-1,1,1,1,1,-1,-1,-1},
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
    };
	
	
	


	public int getImageWidth() {
		return IMAGE_WIDTH;
	}

	

	public int getImageHeight() {
		return IMAGE_HEIGHT;
	}

	

	public int getRowNumber() {
		return LaberintoActual.length;
	}

	

	public int getColumnNumber() {
		return LaberintoActual[0].length;
	}

	

	public String getImageACell(int row, int column) {
		return LaberintoActual[row][column];
	}

	

	public Integer getValueAt(Integer x, Integer y) {
		return cellType[x][y];
	}

	

	public Integer getColumn(Integer x) {
		Double columna = Math.floor(x / 16);
		return columna.intValue();
	}

	

	public Integer getRow(Integer y) {
		Double fila = Math.floor(y / 16);
		return fila.intValue();
	}

}