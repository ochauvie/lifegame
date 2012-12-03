package com.lifegame.activity;


import com.lifegame.R;
import com.lifegame.model.Cycle;
import com.lifegame.model.Grid;
import com.lifegame.model.Mode;
import com.lifegame.model.Turn;

import adapter.GridAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;

public class StartActivity extends Activity {
	
	// View elements
	private GridView gridView;
	private TextView turnView, statView;
	private ImageButton nextTurn;
	
	// Default parameters
	private int initDensity = Grid.INITDensity;
	private int gridX = Grid.INITX;
	private int gridY = Grid.INITY;
	
	// Play objects
	private GridAdapter adapter;
	private Mode mode;
	private Grid grid;
	private Cycle cycle;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Init play mode
        mode = new Mode(Mode.MODE_STEP);
        
        // Get activity extra (from settings)
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
        	gridX = Integer.valueOf(bundle.getString("gridX"));
        	gridY = Integer.valueOf(bundle.getString("gridY"));
        	initDensity = Integer.valueOf(bundle.getString("initDensity"));
        	mode.setMode(bundle.getString(Mode.MODE));
        }
        
        // Init first grid with settings or default parameters
        grid = new Grid(gridX, gridY, initDensity);
		
		// Init play cycle
		cycle = new Cycle(mode, grid, new Turn());
		
        // Text view
        turnView = (TextView) findViewById(R.id.textViewTurn);
        turnView.setText(getString(R.string.turn) + " " + cycle.getTurn().getTurn());
        
        // Text stat
        statView = (TextView) findViewById(R.id.textViewStat);
        statView.setText(getString(R.string.cell_in_life) + ": " + grid.getCellsInLife());
        
        // Grid view
        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setNumColumns(gridX);
        adapter = new GridAdapter(this, grid);
        gridView.setAdapter(adapter);
        
        // Next turn button
        nextTurn = (ImageButton) findViewById(R.id.nextTurn);
        nextTurn.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		
        		// Mode step by step
        		if (Mode.MODE_STEP.equals(mode.getMode())) {
        			if (cycle.getTurn().getStep()==Turn.STEP_LIFE) {
        				cycle.playStepLife();
	                	adapter.notifyDataSetChanged();
	                	statView.setText(getString(R.string.cell_dead) + ": " + grid.getCellsDead() + " - " +
	              		                 getString(R.string.cell_new) + ": " + grid.getCellsNew());
	            	} else if (cycle.getTurn().getStep()==Turn.STEP_MAJ) {
	            		cycle.playStepMajGrid();
	                	adapter.notifyDataSetChanged();
	                	turnView.setText(getString(R.string.turn) + " " + cycle.getTurn().getTurn());
	                	statView.setText(getString(R.string.cell_in_life) + ": " + grid.getCellsInLife());
	            	}
        			
        		// Mode auto	
        		} else if (Mode.MODE_AUTO.equals(mode.getMode() )) {
        			// TODO
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
     * Call when menu item is selected
     */
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
          case R.id.menu_settings:
             Intent myIntent = new Intent(StartActivity.this, SettingsActivity.class);
             myIntent.putExtra("gridX", gridX);
             myIntent.putExtra("gridY", gridY);
             myIntent.putExtra("initDensity", initDensity);
             startActivity(myIntent);
             finish();
             return true;
       }
       return false;}
}
