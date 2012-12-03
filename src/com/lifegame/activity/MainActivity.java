package com.lifegame.activity;


import com.lifegame.R;
import com.lifegame.model.Cell;

import adapter.ImageAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends Activity {
	
	private GridView gridView;
	private int initDensity = Cell.INITDensity;
	private int gridX = Cell.INITX;
	private int gridY = Cell.INITY;
	private int[][] grid;
	private int[][] tempGrid;
	private int step;
	private int cellsInLife, newCells, deadCells;
	private ImageAdapter adapter;
	private int turn;
	private TextView turnView, statView;
	private ImageButton nextTurn;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
        	gridX = Integer.valueOf(bundle.getString("gridX"));
        	gridY = Integer.valueOf(bundle.getString("gridY"));
        	initDensity = Integer.valueOf(bundle.getString("initDensity"));
        }
        
        step = Cell.STEP_LIFE;
        
        // Init first grid
        initGrid();
        
        // Text view
        turnView = (TextView) findViewById(R.id.textViewTurn);
        turnView.setText(getString(R.string.turn) + " " + turn);
        
        // Text stat
        statView = (TextView) findViewById(R.id.textViewStat);
        statView.setText(getString(R.string.cell_in_life) + ": " + cellsInLife);
        
        // Grid view
        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setNumColumns(gridX);
        adapter = new ImageAdapter(this, grid, gridX, gridY);
        gridView.setAdapter(adapter);
        
        // Next turn button
        nextTurn = (ImageButton) findViewById(R.id.nextTurn);
        nextTurn.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		if (step==Cell.STEP_LIFE) {
            		lifeCycle();
                	step = Cell.STEP_MAJ;
                	adapter.notifyDataSetChanged();
                	statView.setText(getString(R.string.cell_dead) + ": " + deadCells + " - " +
              		                 getString(R.string.cell_new) + ": " + newCells);
            	} else if (step==Cell.STEP_MAJ) {
            		majGrid();
                	step = Cell.STEP_LIFE;
                	adapter.notifyDataSetChanged();
                	turn++;
                	turnView.setText(getString(R.string.turn) + " " + turn);
                	statView.setText(getString(R.string.cell_in_life) + ": " + cellsInLife);
            	}
        		
        	}
        });    
        
                
    }
    
    /**
     * Random init grid
     */
    private void initGrid() {
    	turn = 1;
		grid = new int[gridX][gridY];
		tempGrid = new int[gridX][gridY];
		cellsInLife = 0;;
		newCells = 0;;
		deadCells = 0;
		int lower = 0;
		int higher = 10;
		for (int x=0; x<gridX; x++) {
			for (int y=0; y<gridY; y++) {
				int random = (int)(Math.random() * (higher-lower)) + lower;
				if (random<=initDensity)
					grid[x][y] = Cell.CEL_IN_LIFE;
					cellsInLife++;
			}
		}
	}

    /**
     * Kill and new cell generation
     */
    private void lifeCycle() {
    	copyGridToTemp();
    	deadCells=0;
    	newCells=0;
		for (int x=0; x<gridX; x++) {
			for (int y=0; y<gridY; y++) {
				int neighbor = getNeighbor(x,y);
				if (tempGrid[x][y] == Cell.CEL_IN_LIFE) {
					if (neighbor!=2 && neighbor!=3) {
						grid[x][y] = Cell.CEL_DEAD;
						deadCells++;
					}
				} else if (tempGrid[x][y] == Cell.CEL_EMPTY) {
					if (neighbor==3) {
						grid[x][y] = Cell.CEL_NEW;
						newCells++;
					}
					
				}
			}
		}
	}
    
    /**
     * Copy current grid in temp grid
     */
    private void copyGridToTemp() {
    	for (int x=0; x<gridX; x++) {
			for (int y=0; y<gridY; y++) {
				tempGrid[x][y] = grid[x][y];
			}
    	}
    }
    
    /**
     * Maj cell color
     */
    private void majGrid() {
    	cellsInLife = 0;;
		newCells = 0;;
		deadCells = 0;
		for (int x=0; x<gridX; x++) {
			for (int y=0; y<gridY; y++) {
				if (grid[x][y] == Cell.CEL_DEAD) {
					grid[x][y] = Cell.CEL_EMPTY;
				} else if (grid[x][y] == Cell.CEL_NEW) {
					grid[x][y] = Cell.CEL_IN_LIFE;
					cellsInLife++;
				} else if (grid[x][y] == Cell.CEL_IN_LIFE) {
					cellsInLife++;
				}
			}
		}
	}
		
    /**
     * Get neighbor in life cells
     * @param x
     * @param y
     * @return
     */
	private int getNeighbor(int x, int y) {
		int neighbor = 0;
		
		// Up ligne
		if (x>0) {
			if (y>0) {
				if (tempGrid[x-1][y-1]==Cell.CEL_IN_LIFE) {neighbor++;}
			}
			if (tempGrid[x-1][y]==Cell.CEL_IN_LIFE) {neighbor++;}
			if (y<(gridY-1)) {
				if (tempGrid[x-1][y+1]==Cell.CEL_IN_LIFE) {neighbor++;}
			}
		}
		
		// Current line
		if (y>0) {
			if (tempGrid[x][y-1]==Cell.CEL_IN_LIFE) {neighbor++;}
		}
		if (y<(gridY-1)) {
			if (tempGrid[x][y+1]==Cell.CEL_IN_LIFE) {neighbor++;}
		}
		
		// Under ligne
		if (x<gridX-1) {
			if (y>0) {
				if (tempGrid[x+1][y-1]==Cell.CEL_IN_LIFE) {neighbor++;}
			}
			if (tempGrid[x+1][y]==Cell.CEL_IN_LIFE) {neighbor++;}
			if (y<(gridY-1)) {
				if (tempGrid[x+1][y+1]==Cell.CEL_IN_LIFE) {neighbor++;}
			}
		}
		return neighbor;
	}

	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
	
	/**
     * Call when menu item is selected
     */
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
          case R.id.menu_settings:
             Intent myIntent = new Intent(MainActivity.this, SettingsActivity.class);
             myIntent.putExtra("gridX", gridX);
             myIntent.putExtra("gridY", gridY);
             myIntent.putExtra("initDensity", initDensity);
             startActivity(myIntent);
             finish();
             return true;
       }
       return false;}
}
