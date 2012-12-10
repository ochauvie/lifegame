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
import com.lifegame.model.Virus;

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
			
			// If auto mode is launch in step "virus", we must end the turn before continue
			if (cycle.getTurn().getStep()== Turn.STEP_VIRUS) {
				playStepVirus();
			}
			
			// If auto mode is launch in step "update", we must end the turn before continue
			if (cycle.getTurn().getStep()== Turn.STEP_UPDATE) {
				playStepUpdateGrid();
			}
			
			// Running until : task is cancelled or all cells died or max turn is reached
			int endTurn = cycle.getTurn().getTurn() + Parameter.MAXTurn;
			while (cycle.getGrid().getCellsInLife()>0 
					&& cycle.getTurn().getTurn() < endTurn) {
				try {
					Thread.sleep(cycle.getTurn().getSleep());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				playStepVirus();
				playStepLife();
				playStepUpdateGrid();
				publishProgress();
				if (isCancelled()) break;
			}

			
		// Step by step mode
		} else if (Mode.MODE_STEP.equals(cycle.getMode().getMode())) {
			// Virus
			if (cycle.getTurn().getStep()== Turn.STEP_VIRUS) {
				playStepVirus();
				publishProgress(Turn.STEP_VIRUS);
			
			// Dead an born cells
			} else if (cycle.getTurn().getStep()== Turn.STEP_LIFE) {
				playStepLife();
				publishProgress(Turn.STEP_LIFE);
				
			// Refresh grid with only in life cells	
			} else if (cycle.getTurn().getStep()== Turn.STEP_UPDATE) {
				playStepUpdateGrid();
				publishProgress(Turn.STEP_UPDATE);
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
	 * Play step virus: virus life cycle and effect 
	 */
	private void playStepVirus() {
		Grid grid = cycle.getGrid();
		grid.copyGridToTemp();
		Cell[][] tempCells = grid.getTempCells();
		grid.setCellsDead(0);
    	grid.setCellsNew(0);
    	for (int x=0; x<grid.getGridX(); x++) {
			for (int y=0; y<grid.getGridY(); y++) {
				Cell tempCell = tempCells[x][y];
				Virus virus = tempCell.getVirus();
				if (virus!=null) {
					
					// Virus life cycle
					if (virus.getDuration()>0) {
						virus.setDuration(virus.getDuration()-1);
						
						// Virus effect
						if (Cell.CEL_FROZEN!=virus.getEffect()) {
							grid.getCell(tempCell.getX(), tempCell.getY()).setStatus(virus.getEffect());
						}
						
						// For each cells in range of infected cell 
						List<Cell> tempNeighbors = grid.getNeighborByRange(x, y, virus.getRange());
						for (Cell tempNeighbor:tempNeighbors) {
							
							// A cell can be infected only by one virus
							if (tempNeighbor.getVirus()==null) {
								Cell realCell = grid.getCell(tempNeighbor.getX(), tempNeighbor.getY());
								Virus newVirus = new Virus(virus.getId(), virus.getName(), virus.getRange(), virus.getDuration(), virus.getEffect());
								realCell.setVirus(newVirus);
								if (virus.getEffect()==Cell.CEL_IN_LIFE) {
									realCell.setOwner(1);	// TODO can be player 2 if is not computer ?
								}
								
								if (Cell.CEL_FROZEN!=virus.getEffect()) {
									realCell.setStatus(newVirus.getEffect());
								}
							}
						}
					} else {
						// Remove virus
						grid.getCell(tempCell.getX(), tempCell.getY()).setVirus(null);
					}
				}
			}
    	}
		
		cycle.getTurn().setStep(Turn.STEP_LIFE);
	}
	
	/**
	 * Play step life : kill and born cells
	 * excepted frozen cells
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

				// Test frozen cell
				boolean frozen = false;
				Virus virus = tempCell.getVirus();
				if (virus!=null && virus.getEffect()==Cell.CEL_FROZEN) {
					frozen = true;
				}
				
				// Cell not frozen
				if (!frozen) {
					//int neighbor = grid.getTempNeighbor(x,y);
					List<Cell> neighbors = grid.getNeighborByRange(x, y, 1);
					int player1Neighbors = 0;
					int player2Neighbors = 0;
					for (Cell neighbor:neighbors) {
						if (Cell.CEL_IN_LIFE==neighbor.getStatus()) {
							if (neighbor.getOwner()==1) {
								player1Neighbors++;
							}
							if (neighbor.getOwner()==2) {
								player2Neighbors++;
							}
						}
					}
					int totalNeighbors = player1Neighbors + player2Neighbors;
					// Kill cell with 2 or 3 neighbors
					if (tempCell.getStatus() == Cell.CEL_IN_LIFE) {
						if (totalNeighbors!=2 && totalNeighbors!=3) {
							grid.killCell(x,y);
						}
						
					// Born cell with 3 neighbors in life	
					} else if (tempCell.getStatus() == Cell.CEL_EMPTY) {
						if (totalNeighbors==3) {
							int winerOwner = 1;
							if (player2Neighbors>player1Neighbors) {
								winerOwner = 2;
							}
							grid.bornCell(x,y, winerOwner);
						}
					}
				}
			}
		}
    	cycle.getTurn().setStep(Turn.STEP_UPDATE);
	}
	
	/**
	 * Play step update: update born cell to in life cell and delete dead cells
	 */
	private void playStepUpdateGrid() {
		Grid grid = cycle.getGrid();
		grid.setCellsInLife(0);
		grid.setCellsInLifePlayer1(0);
		grid.setCellsInLifePlayer2(0);
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
					grid.addCellInLife(cell.getOwner());
				} else if (cell.getStatus() == Cell.CEL_IN_LIFE) {
					grid.addCellInLife(cell.getOwner());
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
