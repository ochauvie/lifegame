package com.lifegame.activity;


import com.lifegame.R;
import com.lifegame.model.Cell;

import adapter.ImageAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends Activity {
	
	private GridView gridView;
	private int initDensity = 5; // TODO settings
	private int gridX = 16;	// TODO settings
	private int gridY = 16; // TODO settings
	private int[][] grid;
	private int step;
	private ImageAdapter adapter;
	private int turn;
	private TextView turnView;
	private ImageButton nextTurn;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        step = Cell.STEP_LIFE;
        
        // Init first grid
        turn = 1;
        initGrid();
        
        // Text view
        turnView = (TextView) findViewById(R.id.textViewTurn);
        turnView.setText(getString(R.string.turn) + " " + turn);
        
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
            	} else if (step==Cell.STEP_MAJ) {
            		majGrid();
                	step = Cell.STEP_LIFE;
                	adapter.notifyDataSetChanged();
                	turn++;
                	turnView.setText(getString(R.string.turn) + " " + turn);
            	}
        	}
        });    
        
                
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    /**
     * Random init grid
     */
    private void initGrid() {
		grid = new int[gridX][gridY];
		int lower = 0;
		int higher = initDensity;
		for (int x=0; x<gridX; x++) {
			for (int y=0; y<gridY; y++) {
				int random = (int)(Math.random() * (higher-lower)) + lower;
				if (random==0)
					grid[x][y] = Cell.CEL_IN_LIFE;
			}
		}
	}

    /**
     * Kill and new cell generation
     */
    private void lifeCycle() {
		for (int x=0; x<gridX; x++) {
			for (int y=0; y<gridY; y++) {
				int neighbor = getNeighbor(x,y);
				if (grid[x][y] == Cell.CEL_IN_LIFE) {
					if (neighbor!=2 && neighbor!=3) {
						grid[x][y] = Cell.CEL_DEAD;
					}
				} else if (grid[x][y] == Cell.CEL_EMPTY) {
					if (neighbor==3) {
						grid[x][y] = Cell.CEL_NEW;
					}
					
				}
			}
		}
	}
    
    /**
     * Maj cell color
     */
    private void majGrid() {
		for (int x=0; x<gridX; x++) {
			for (int y=0; y<gridY; y++) {
				if (grid[x][y] == Cell.CEL_DEAD) {
					grid[x][y] = Cell.CEL_EMPTY;
				} else if (grid[x][y] == Cell.CEL_NEW) {
					grid[x][y] = Cell.CEL_IN_LIFE;
					
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
				if (grid[x-1][y-1]==Cell.CEL_IN_LIFE) {neighbor++;}
			}
			if (grid[x-1][y]==Cell.CEL_IN_LIFE) {neighbor++;}
			if (y<(gridY-1)) {
				if (grid[x-1][y+1]==Cell.CEL_IN_LIFE) {neighbor++;}
			}
		}
		
		// Current line
		if (y>0) {
			if (grid[x][y-1]==Cell.CEL_IN_LIFE) {neighbor++;}
		}
		if (y<(gridY-1)) {
			if (x>0) {
				if (grid[x-1][y+1]==Cell.CEL_IN_LIFE) {neighbor++;}
			}
		}
		
		// Under ligne
		if (x<gridX-1) {
			if (y>0) {
				if (grid[x+1][y-1]==Cell.CEL_IN_LIFE) {neighbor++;}
			}
			if (grid[x+1][y]==Cell.CEL_IN_LIFE) {neighbor++;}
			if (y<(gridY-1)) {
				if (grid[x+1][y+1]==Cell.CEL_IN_LIFE) {neighbor++;}
			}
		}
		return neighbor;
	}

}
