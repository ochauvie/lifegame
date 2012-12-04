package com.lifegame.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Grid implements Parcelable {
	
	private int gridX; // Number of lines
	private int gridY; // Number of columns
	private int initDensity; // Population density
	private Cell[][] cells; // Cells of the grid
	private Cell[][] tempCells; // Temp cells of the grid
	
	private int cellsInLife; // Number of cells in life
	private int cellsNew; // Number of cells born
	private int cellsDead; // Number of cells dead
	
	/**
	 * Constructor
	 * @param gridX : number of lines
	 * @param gridY : number of columns
	 * @param initDensity : density to populate the grid
	 */
	public Grid(Parameter parameter) {
		super();
		this.gridX = parameter.getGridX();
		this.gridY = parameter.getGridY();
		this.initDensity = parameter.getGridDensity();
		cells = new Cell[gridX][gridY];
		tempCells = new Cell[gridX][gridY];
		
		// Populate the grid
		populateGrid();
	}
	
	public Grid(Parcel parcel) {
		gridX = parcel.readInt();
		gridY = parcel.readInt();
		initDensity = parcel.readInt();
		cells = new Cell[gridX][gridY];
		tempCells = new Cell[gridX][gridY];
		for (int x=0; x<gridX; x++) {
			for (int y=0; y<gridY; y++) {
				Cell cell = parcel.readParcelable(Cell.class.getClassLoader());
				cells[x][y] = cell;
			}
		}
		this.cellsInLife = parcel.readInt();
		this.cellsNew = parcel.readInt();
		this.cellsDead = parcel.readInt();
	}
	
	public static final Parcelable.Creator<Grid> CREATOR = new Parcelable.Creator<Grid>()
		{
		    @Override
		    public Grid createFromParcel(Parcel source)
		    { return new Grid(source);}

		    @Override
		    public Grid[] newArray(int size)
		    { return new Grid[size];}
		};
	
	
	
	/**
	 * Getter gridX
	 * @return the gridX
	 */
	public int getGridX() {
		return gridX;
	}
	/**
	 * Setter gridX
	 * @param gridX the gridX to set
	 */
	public void setGridX(int gridX) {
		this.gridX = gridX;
	}
	/**
	 * Getter gridY
	 * @return the gridY
	 */
	public int getGridY() {
		return gridY;
	}
	/**
	 * Setter gridY
	 * @param gridY the gridY to set
	 */
	public void setGridY(int gridY) {
		this.gridY = gridY;
	}
	/**
	 * Getter initDensity
	 * @return the initDensity
	 */
	public int getInitDensity() {
		return initDensity;
	}
	/**
	 * Setter initDensity
	 * @param initDensity the initDensity to set
	 */
	public void setInitDensity(int initDensity) {
		this.initDensity = initDensity;
	}
	/**
	 * Getter cells
	 * @return the cells
	 */
	public Cell[][] getCells() {
		return cells;
	}
	/**
	 * Setter cells
	 * @param cells the cells to set
	 */
	public void setCells(Cell[][] cells) {
		this.cells = cells;
	} 
	
	/**
	 * Getter cellsInLife
	 * @return the cellsInLife
	 */
	public int getCellsInLife() {
		return cellsInLife;
	}

	/**
	 * Setter cellsInLife
	 * @param cellsInLife the cellsInLife to set
	 */
	public void setCellsInLife(int cellsInLife) {
		this.cellsInLife = cellsInLife;
	}
	
	public void addCellInLife() {
		this.cellsInLife++;
	}

	/**
	 * Getter cellsNew
	 * @return the cellsNew
	 */
	public int getCellsNew() {
		return cellsNew;
	}

	/**
	 * Setter cellsNew
	 * @param cellsNew the cellsNew to set
	 */
	public void setCellsNew(int cellsNew) {
		this.cellsNew = cellsNew;
	}
	
	public void addCellNew() {
		this.cellsNew++;
	}

	/**
	 * Getter cellsDead
	 * @return the cellsDead
	 */
	public int getCellsDead() {
		return cellsDead;
	}

	/**
	 * Setter cellsDead
	 * @param cellsDead the cellsDead to set
	 */
	public void setCellsDead(int cellsDead) {
		this.cellsDead = cellsDead;
	}
	
	public void addCellDead() {
		this.cellsDead++;
	}
	
	/**
	 * Getter tempCells
	 * @return the tempCells
	 */
	public Cell[][] getTempCells() {
		return tempCells;
	}

	/**
     * Random population using density parameter
     */
    private void populateGrid() {
    	cellsInLife = 0;
		cellsNew = 0;
		cellsDead = 0;
		int lower = 0;
		int higher = 10;
		for (int x=0; x<gridX; x++) {
			for (int y=0; y<gridY; y++) {
				int random = (int)(Math.random() * (higher-lower)) + lower;
				Cell cell = new Cell(x, y, Cell.CEL_EMPTY);
				if (random<=initDensity) {
					cell.setStatus(Cell.CEL_IN_LIFE);
					addCellInLife();
				} 
				cells[x][y] = cell;
			}
		}
	}
    
    /**
     * Copy current grid in temp grid
     */
    public void copyGridToTemp() {
    	for (int x=0; x<gridX; x++) {
			for (int y=0; y<gridY; y++) {
				Cell tempCell = new Cell(x, y, cells[x][y].getStatus());
				tempCells[x][y] = tempCell;
			}
    	}
    }
	
    public Cell getCell(int x, int y) {
    	Cell cell = null;
    	if (x >=0 && y>=0 && (x<=gridX) && (y<=gridY)) {
    		cell = cells[x][y];
    	}
    	return cell;
    }
    
    public void killCell(int x, int y) {
    	Cell cell = null;
    	if (x >=0 && y>=0 && (x<=gridX) && (y<=gridY)) {
    		cell = cells[x][y];
    		cell.setStatus(Cell.CEL_DEAD);
    		cellsDead++;
    	}
    }
    
    public void bornCell(int x, int y) {
    	Cell cell = null;
    	if (x >=0 && y>=0 && (x<=gridX) && (y<=gridY)) {
    		cell = cells[x][y];
    		cell.setStatus(Cell.CEL_NEW);
    		cellsNew++;
    	}
    }
    
    /**
     * Get neighbor in life cells
     * @param x
     * @param y
     * @return
     */
    public int getTempNeighbor(int x, int y) {
		int neighbor = 0;
		
		// Up ligne
		if (x>0) {
			if (y>0) {
				if (tempCells[x-1][y-1].getStatus()==Cell.CEL_IN_LIFE) {neighbor++;}
			}
			if (tempCells[x-1][y].getStatus()==Cell.CEL_IN_LIFE) {neighbor++;}
			if (y<(gridY-1)) {
				if (tempCells[x-1][y+1].getStatus()==Cell.CEL_IN_LIFE) {neighbor++;}
			}
		}
		
		// Current line
		if (y>0) {
			if (tempCells[x][y-1].getStatus()==Cell.CEL_IN_LIFE) {neighbor++;}
		}
		if (y<(gridY-1)) {
			if (tempCells[x][y+1].getStatus()==Cell.CEL_IN_LIFE) {neighbor++;}
		}
		
		// Under ligne
		if (x<gridX-1) {
			if (y>0) {
				if (tempCells[x+1][y-1].getStatus()==Cell.CEL_IN_LIFE) {neighbor++;}
			}
			if (tempCells[x+1][y].getStatus()==Cell.CEL_IN_LIFE) {neighbor++;}
			if (y<(gridY-1)) {
				if (tempCells[x+1][y+1].getStatus()==Cell.CEL_IN_LIFE) {neighbor++;}
			}
		}
		return neighbor;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(gridX);
		parcel.writeInt(gridY);
		parcel.writeInt(initDensity);
		for (int x=0; x<gridX; x++) {
			for (int y=0; y<gridY; y++) {
				parcel.writeParcelable(getCell(x, y), flags);
			}
		}
		parcel.writeInt(cellsInLife);
		parcel.writeInt(cellsNew);
		parcel.writeInt(cellsDead);
	}
    
}
