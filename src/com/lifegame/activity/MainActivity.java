package com.lifegame.activity;


import com.lifegame.R;
import com.lifegame.model.Cell;

import adapter.ImageAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;
import android.view.View;

public class MainActivity extends Activity {
	
	private GridView gridView;
	private int gridX = 16;
	private int gridY = 16;
	private int[][] grid;
	private int step;
	private ImageAdapter adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        step = Cell.STEP_LIFE;
        
        // Init first grid
        initGrid();
        
        adapter = new ImageAdapter(this, grid, gridX, gridY);
        
        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (step==Cell.STEP_LIFE) {
            		lifeCycle();
                	step = Cell.STEP_MAJ;
                	adapter.notifyDataSetChanged();
            	} else if (step==Cell.STEP_MAJ) {
            		majGrid();
                	step = Cell.STEP_LIFE;
                	adapter.notifyDataSetChanged();
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
		int higher = 5;
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
		// TODO vivante only
		int lower = 0;
		int higher = 5;
		int random = (int)(Math.random() * (higher-lower)) + lower;
		return random;
	}

}
