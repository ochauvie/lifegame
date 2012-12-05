package com.lifegame.task;

import java.util.ArrayList;
import java.util.List;

import com.lifegame.listener.IPlayCycle;
import com.lifegame.listener.IPlayCycleListener;
import com.lifegame.model.Cell;
import com.lifegame.model.Cycle;
import com.lifegame.model.Grid;
import com.lifegame.model.Mode;
import com.lifegame.model.Parameter;
import com.lifegame.model.Turn;

import android.os.AsyncTask;

public class PlayCycleTask extends AsyncTask<Cycle, Integer, Cycle> implements IPlayCycle {

	private Cycle cycle; // Game cycle to play
	private List<IPlayCycleListener> listeners = null;
	
	@Override
	protected Cycle doInBackground(Cycle... params) {
		if (params==null || params.length==0) {
			return null;
		}
		
		cycle = params[0];

		// Auto mode
		if (Mode.MODE_AUTO.equals(cycle.getMode().getMode())) {
			
			// TODO : si on lance le mode auto depuis le step LIFE, il faut d'abord le finir
			
			
			// Running until : task is cancelled or all cells died or max turn is reached
			int endTurn = cycle.getTurn().getTurn() + Parameter.MAXTurn;
			while (cycle.getGrid().getCellsInLife()>0 
					&& cycle.getTurn().getTurn() < endTurn) {
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				playFullTurn();
				publishProgress();
				if (isCancelled()) break;
			}

			
		// Step by step mode
		} else if (Mode.MODE_STEP.equals(cycle.getMode().getMode())) {
			// Dead an born cells
			if (cycle.getTurn().getStep()== Turn.STEP_LIFE) {
				playStepLife();
				publishProgress(Turn.STEP_LIFE);
				
			// Refresh grid with only in life cells	
			} else if (cycle.getTurn().getStep()== Turn.STEP_MAJ) {
				playStepUpdateGrid();
				publishProgress(Turn.STEP_MAJ);
			}
		}
		return cycle;
	}
	
	/**
	 * On task progression
	 * Inform listener 
	 */
	protected void onProgressUpdate(Integer... progress) {
		fireDataChanged();
    }

	/**
	 * Play step life : kill and born cells
	 */
	private void playStepLife() {
		Grid grid = cycle.getGrid();
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
    	cycle.getTurn().setStep(Turn.STEP_MAJ);
	}
	
	/**
	 * Play step update: update born cell to in life cell and delete dead cells
	 */
	private void playStepUpdateGrid() {
		Grid grid = cycle.getGrid();
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
		cycle.getTurn().endTurn();
	}
	
	/**
	 * Play full play turn (step life and step update)
	 */
	private void playFullTurn()  {
		Grid grid = cycle.getGrid();
		grid.copyGridToTemp();
		Cell[][] tempCells = grid.getTempCells();
		grid.setCellsInLife(0);
		grid.setCellsDead(0);
    	grid.setCellsNew(0);
    	for (int x=0; x<grid.getGridX(); x++) {
			for (int y=0; y<grid.getGridY(); y++) {
				Cell tempCell = tempCells[x][y];
				int neighbor = grid.getTempNeighbor(x,y);
				
				// Kill cell with 2 or 3 neighbors
				if (tempCell.getStatus() == Cell.CEL_IN_LIFE) {
					if (neighbor!=2 && neighbor!=3) {
						grid.emptyCell(x,y);
					} else {
						grid.addCellInLife();
					}
					
				// Born cell with 3 neighbors in life	
				} else if (tempCell.getStatus() == Cell.CEL_EMPTY) {
					if (neighbor==3) {
						grid.inLifeCell(x, y);
					}
				}
			}
		}
		cycle.getTurn().endTurn();
	}

	/**
	 * Inform listener
	 */
	private void fireDataChanged(){ 
	    if(listeners != null){ 
	        for(IPlayCycleListener listener: listeners){ 
	            listener.dataChanged(cycle); 
	        } 
	    } 
	}
	
	@Override
	public void addListener(IPlayCycleListener listener) {
		if(listeners == null){ 
	        listeners = new ArrayList<IPlayCycleListener>(); 
	    } 
	    listeners.add(listener); 
		
	}

	@Override
	public void removeListener(IPlayCycleListener listener) {
		if(listeners != null){ 
	        listeners.remove(listener); 
	    } 
	}
}
