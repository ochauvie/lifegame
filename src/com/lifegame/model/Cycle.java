package com.lifegame.model;


public class Cycle {

	private Turn turn;
	private Mode mode;
	private Grid grid;
	
	/**
	 * Constructor
	 * @param mode the play mode
	 * @param grid the grid
	 */
	public Cycle(Mode mode, Grid grid, Turn turn) {
		super();
		this.mode = mode;
		this.grid = grid;
		this.turn = turn;
	}
	/**
	 * Getter turn
	 * @return the turn
	 */
	public Turn getTurn() {
		return turn;
	}
	/**
	 * Setter turn
	 * @param turn the turn to set
	 */
	public void setTurn(Turn turn) {
		this.turn = turn;
	}
	/**
	 * Getter mode
	 * @return the mode
	 */
	public Mode getMode() {
		return mode;
	}
	/**
	 * Setter mode
	 * @param mode the mode to set
	 */
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	/**
	 * Getter grid
	 * @return the grid
	 */
	public Grid getGrid() {
		return grid;
	}
	/**
	 * Setter grid
	 * @param grid the grid to set
	 */
	public void setGrid(Grid grid) {
		this.grid = grid;
	}
	
	/**
	 * Play step : kill and born cells
	 */
	public void playStepLife() {
		grid.copyGridToTemp();
		Cell[][] tempCells = grid.getTempCells();
    	grid.setCellsDead(0);
    	grid.setCellsNew(0);
    	for (int x=0; x<grid.getGridX(); x++) {
			for (int y=0; y<grid.getGridY(); y++) {
				Cell tempCell = tempCells[x][y];
				int neighbor = grid.getTempNeighbor(x,y);
				
				// Kill cell with 2 or 3 neighbors
				if (tempCell.getStatus() == Cell.CEL_IN_LIFE) {
					if (neighbor!=2 && neighbor!=3) {
						grid.killCell(x,y);
					}
					
				// Born cell with 3 neighbors in life	
				} else if (tempCell.getStatus() == Cell.CEL_EMPTY) {
					if (neighbor==3) {
						grid.bornCell(x,y);
					}
				}
			}
		}
    	turn.setStep(Turn.STEP_MAJ);
	}
	
	/**
	 * Play step : update born cell to in life cell and delete dead cells
	 */
	public void playStepMajGrid() {
		grid.setCellsInLife(0);
    	grid.setCellsNew(0);
		grid.setCellsDead(0);
    	grid.setCellsNew(0);
		for (int x=0; x<grid.getGridX(); x++) {
			for (int y=0; y<grid.getGridY(); y++) {
				Cell cell = grid.getCell(x, y);
				if (cell.getStatus() == Cell.CEL_DEAD) {
					cell.setStatus(Cell.CEL_EMPTY);
				} else if (cell.getStatus() == Cell.CEL_NEW) {
					cell.setStatus(Cell.CEL_IN_LIFE);
					grid.addCellInLife();
				} else if (cell.getStatus() == Cell.CEL_IN_LIFE) {
					grid.addCellInLife();
				}
			}
		}
		turn.endTurn();
	}
	
}
