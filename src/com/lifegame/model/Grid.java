package com.lifegame.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Grid implements Parcelable {
	
	private int gridX; // Number of lines
	private int gridY; // Number of columns
	private int initDensity; // Population density
	private Cell[][] cells; // Cells of the grid
	private Cell[][] tempCells; // Temp cells of the grid
	
	private int cellsInLife; // Number of cells in life
	private int cellsInLifePlayer1; // Number of cells in life for player 1
	private int cellsInLifePlayer2; // Number of cells in life for player 2
	private int cellsNew; // Number of cells born
	private int cellsDead; // Number of cells dead
	
	/**
	 * Constructor
	 * @param parameter : game parameters
	 */
	public Grid(Parameter parameter) {
		super();
		this.gridX = parameter.getGridX();
		this.gridY = parameter.getGridY();
		this.initDensity = parameter.getGridDensity();
		cells = new Cell[gridX][gridY];
		tempCells = new Cell[gridX][gridY];
		
		// Populate the grid
		populateGrid(parameter.getNbPlayer());
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
		this.cellsInLifePlayer1 = parcel.readInt();
		this.cellsInLifePlayer2 = parcel.readInt();
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
	
	public void addCellInLife(int owner) {
		if (owner==1) {
			cellsInLifePlayer1++;
		} else if (owner==2) {
			cellsInLifePlayer2++;
		}
		this.cellsInLife++;
	}
	

	/**
	 * Getter cellsInLifePlayer1
	 * @return the cellsInLifePlayer1
	 */
	public int getCellsInLifePlayer1() {
		return cellsInLifePlayer1;
	}

	/**
	 * Setter cellsInLifePlayer1
	 * @param cellsInLifePlayer1 the cellsInLifePlayer1 to set
	 */
	public void setCellsInLifePlayer1(int cellsInLifePlayer1) {
		this.cellsInLifePlayer1 = cellsInLifePlayer1;
	}

	/**
	 * Getter cellsInLifePlayer2
	 * @return the cellsInLifePlayer2
	 */
	public int getCellsInLifePlayer2() {
		return cellsInLifePlayer2;
	}

	/**
	 * Setter cellsInLifePlayer2
	 * @param cellsInLifePlayer2 the cellsInLifePlayer2 to set
	 */
	public void setCellsInLifePlayer2(int cellsInLifePlayer2) {
		this.cellsInLifePlayer2 = cellsInLifePlayer2;
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
     * @param nbPlayer: number of player
     */
    private void populateGrid(int nbPlayer) {
    	cellsInLife = 0;
    	cellsInLifePlayer1 = 0;
    	cellsInLifePlayer2 = 0;
		cellsNew = 0;
		cellsDead = 0;
		int lower = 0;
		int higher = 10;
		int owner = 0;
		for (int x=0; x<gridX; x++) {
			for (int y=0; y<gridY; y++) {
				int random = (int)(Math.random() * (higher-lower)) + lower;
				Cell cell = new Cell(x, y, Cell.CEL_EMPTY, null, 0);
				if (random<=initDensity) {
					cell.setStatus(Cell.CEL_IN_LIFE);
					owner = 1;
					if (nbPlayer>1) {
						int randomOwner = (int)(Math.random() * (higher-lower)) + lower;
						if (randomOwner<=5) {
							owner = 2;
						} 
					}
					cell.setOwner(owner);
					addCellInLife(owner);
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
				Cell tempCell = new Cell(x, y, cells[x][y].getStatus(), cells[x][y].getVirus(), cells[x][y].getOwner());
				tempCells[x][y] = tempCell;
			}
    	}
    }
	
    /**
     * Return cell in the grid by x and y position
     * @param x
     * @param y
     * @return the cell
     */
    public Cell getCell(int x, int y) {
    	Cell cell = null;
    	if (x >=0 && y>=0 && (x<gridX) && (y<gridY)) {
    		cell = cells[x][y];
    	}
    	return cell;
    }
    
    /**
     * Kill cell in x and y position
     * @param x
     * @param y
     */
    public void killCell(int x, int y) {
    	Cell cell = null;
    	if (x >=0 && y>=0 && (x<=gridX) && (y<=gridY)) {
    		cell = cells[x][y];
    		cell.setStatus(Cell.CEL_DEAD);
    		cell.setOwner(0);
    		cellsDead++;
    	}
    }
    
    /**
     * Born new cell in x and y position belonging to given owner
     * @param x
     * @param y
     * @param owner: the player
     */
    public void bornCell(int x, int y, int owner) {
    	Cell cell = null;
    	if (x >=0 && y>=0 && (x<=gridX) && (y<=gridY)) {
    		cell = cells[x][y];
    		cell.setStatus(Cell.CEL_NEW);
    		cell.setOwner(owner);
    		cellsNew++;
    	}
    }
    
    /**
     * Set a empty cell in x and y position
     * @param x
     * @param y
     */
    public void emptyCell(int x, int y) {
    	Cell cell = null;
    	if (x >=0 && y>=0 && (x<=gridX) && (y<=gridY)) {
    		cell = cells[x][y];
    		cell.setStatus(Cell.CEL_EMPTY);
    		cell.setOwner(0);
    	}
    }
    
    /**
     * Get neighbor cells in temp grid 
     * @param x: x position of current cell
     * @param y: y position of current cell
     * @param range: range of cell to find neighbors
     * @return: list of neighbors
     */
    public List<Cell> getNeighborByRange(int x, int y, int range) {
		List<Cell> neigghborCells = new ArrayList<Cell>();
		for (int r=1; r<=range; r++) {
			// Up line
			if ((x-r)>=0) {
				if ((y-r)>=0) {	
					neigghborCells.add(tempCells[x-r][y-r]);	
				}
				neigghborCells.add(tempCells[x-r][y]);
				if ((y+r)<(gridY)) {
					neigghborCells.add(tempCells[x-r][y+r]);
				}	
			}
			
			// Current line
			if ((y-r)>=0) {
				neigghborCells.add(tempCells[x][y-r]);
			}
			if ((y+r)<(gridY)) {
				neigghborCells.add(tempCells[x][y+r]);
			}
			
			// Under line
			if ((x+r)<(gridX)) {
				if ((y-r)>=0) {
					neigghborCells.add(tempCells[x+r][y-r]);
				}
				neigghborCells.add(tempCells[x+r][y]);
				if ((y+r)<(gridY)) {
					neigghborCells.add(tempCells[x+r][y+r]);
				}
			}
		}
		return neigghborCells;
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
		parcel.writeInt(cellsInLifePlayer1);
		parcel.writeInt(cellsInLifePlayer2);
		parcel.writeInt(cellsNew);
		parcel.writeInt(cellsDead);
	}
    
}
