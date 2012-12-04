package com.lifegame.service;

import java.util.ArrayList;
import java.util.List;

import com.lifegame.service.IPlayCycleService;

import com.lifegame.model.Cell;
import com.lifegame.model.Cycle;
import com.lifegame.model.Grid;
import com.lifegame.model.Turn;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

public class PlayCycleService extends Service implements IPlayCycle  {

	private Cycle cycle;
	private List<IPlayCycleListener> listeners = null; 
	private static PlayCycleService service;
	
	public static PlayCycleService getService() { 
		return service; 
	} 
	 
	// Stub
	public IPlayCycleService.Stub mStub = new IPlayCycleService.Stub() {
    
		
		/**
		 * Play step life : kill and born cells
		 */
		public void playStepLife() throws RemoteException {
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
	    	fireDataChanged();
		}
		
		/**
		 * Play step update: update born cell to in life cell and delete dead cells
		 */
		public void playStepUpdateGrid() throws RemoteException {
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
			fireDataChanged();
		}
		
		/**
		 * Play full play turn
		 */
		public void playFullTurn() throws RemoteException {
           	playStepLife();
			playStepUpdateGrid();
			fireDataChanged();
		}
    };

    
	@Override
	public IBinder onBind(Intent intent) {
		return mStub;
	}
	
	@Override 
	public void onCreate() { 
	    super.onCreate(); 
	    service = this;
	} 

	@Override 
	public int onStartCommand(Intent intent, int flags, int startId) { 
		Bundle bundle = intent.getExtras();
        if (bundle!=null) {
        	cycle = bundle.getParcelable("cycle");
        }
        return START_NOT_STICKY;
	} 
	
	@Override 
	public void onDestroy() { 
		super.onDestroy();
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

	// Notification des listeners 
	private void fireDataChanged(){ 
	    if(listeners != null){ 
	        for(IPlayCycleListener listener: listeners){ 
	            listener.dataChanged(cycle); 
	        } 
	    } 
	}
	
	
}
