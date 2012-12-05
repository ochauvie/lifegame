package com.lifegame.activity;



import com.lifegame.R;
import com.lifegame.adapter.GridAdapter;
import com.lifegame.listener.IPlayCycleListener;
import com.lifegame.model.Cycle;
import com.lifegame.model.Grid;
import com.lifegame.model.Mode;
import com.lifegame.model.Parameter;
import com.lifegame.model.Turn;
import com.lifegame.task.PlayCycleTask;

import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;

public class StartActivity extends Activity implements IPlayCycleListener{
	
	// View elements
	private GridView gridView;
	private TextView turnView, statView;
	private ImageButton nextTurn;
	
	// Default parameters
	private Parameter parameter;
	
	// Play objects
	private GridAdapter adapter;
	private Grid grid;
	private Cycle cycle;
	private PlayCycleTask playCycleTask;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /**
		 *  Initialize the game
		 */
        
        // Default play parameters
        parameter = new Parameter();
        
        // Get parameters from settings
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
        	parameter = bundle.getParcelable("parameter");
        }
        
        // Initialize first grid with settings or default parameters
        grid = new Grid(parameter);
		
		// Initialize play cycle
		cycle = new Cycle(parameter.getMode(), grid, new Turn(parameter.getTurnSleep()));
		
		
		/**
		 *  Initialize the view
		 */
		
        // Text view
        turnView = (TextView) findViewById(R.id.textViewTurn);
        turnView.setText(getString(R.string.turn) + " " + cycle.getTurn().getTurn());
        
        // Text stat
        statView = (TextView) findViewById(R.id.textViewStat);
        statView.setText(getString(R.string.cell_in_life) + ": " + grid.getCellsInLife());
        
        // Grid view
        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setNumColumns(parameter.getGridX());
        adapter = new GridAdapter(this, grid);
        gridView.setAdapter(adapter);
        
        // Next turn button
        nextTurn = (ImageButton) findViewById(R.id.nextTurn);
        nextTurn.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		
        		boolean stop = false;
        		if (playCycleTask!=null) {
        			if (playCycleTask.getStatus().equals(Status.RUNNING)) {
        				playCycleTask.cancel(true);
        				stop = true;
        				nextTurn.setImageResource(R.drawable.start);
        			} 
        		} 
        		if (!stop) {
	        		playCycleTask = new PlayCycleTask();
	        		playCycleTask.addListener(StartActivity.this);
	        		playCycleTask.execute(cycle);
	        		if (cycle.getMode().getMode().equals(Mode.MODE_AUTO)) {
	        			nextTurn.setImageResource(R.drawable.stop);
	        		}
        		}
        		
        	}
        });    
    }
	
    /**
     * Listener on Play Cycle task to update the grid and text
     */
	@Override
	public void dataChanged(Cycle cycle) {
		this.cycle = cycle;
		adapter = new GridAdapter(StartActivity.this, cycle.getGrid());
		gridView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		turnView.setText(getString(R.string.turn) + " " + cycle.getTurn().getTurn());
		if (cycle.getMode().getMode().equals(Mode.MODE_AUTO)) {
	    	statView.setText(getString(R.string.cell_in_life) + ": " + cycle.getGrid().getCellsInLife());
		} else {
			if (cycle.getTurn().getStep()==Turn.STEP_MAJ) {
				statView.setText(getString(R.string.cell_dead) + ": " + cycle.getGrid().getCellsDead() + " - " +
					 getString(R.string.cell_new) + ": " + cycle.getGrid().getCellsNew());
			} else {
				statView.setText(getString(R.string.cell_in_life) + ": " + cycle.getGrid().getCellsInLife());
			}
		}
	}
    
    /**
     * Menu settings
     */
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
             myIntent.putExtra("parameter", parameter);
             startActivity(myIntent);
             finish();
             return true;
       }
       return false;}
    
   

    
}
