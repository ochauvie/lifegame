package com.lifegame.activity;



import com.lifegame.R;
import com.lifegame.adapter.GridAdapter;
import com.lifegame.listener.IPlayCycleListener;
import com.lifegame.model.Cell;
import com.lifegame.model.Cycle;
import com.lifegame.model.Grid;
import com.lifegame.model.Mode;
import com.lifegame.model.Parameter;
import com.lifegame.model.Turn;
import com.lifegame.model.Virus;
import com.lifegame.task.PlayCycleTask;

import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.view.View;

public class StartActivity extends Activity implements IPlayCycleListener{
	
	// View elements
	private GridView gridView;
	private TextView turnView, statView;
	private ImageButton nextTurn;
	private ImageButton virusA, virusB, virusC, virusD;
	private CheckBox checkBoxAuto;
	
	// Default parameters
	private Parameter parameter;
	
	// Play objects
	private GridAdapter adapter;
	private Grid grid;
	private Cycle cycle;
	private Virus currentVirus;
	private Virus aVirus, bVirus, cVirus, dVirus;
	private PlayCycleTask playCycleTask;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /**
		 *  Initialize the game
		 */
        // Initialize virus
        initVirus();
        
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
        
        // Checkbox play mode
        checkBoxAuto = (CheckBox) findViewById(R.id.auto);
        if (Mode.MODE_AUTO.equals(parameter.getMode().getMode())) {
        	checkBoxAuto.setChecked(true);
        } else {
        	checkBoxAuto.setChecked(false);
        }
        checkBoxAuto.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					parameter.getMode().setMode(Mode.MODE_AUTO);
					cycle.getMode().setMode(Mode.MODE_AUTO);
                } else {
                	parameter.getMode().setMode(Mode.MODE_STEP);
                	cycle.getMode().setMode(Mode.MODE_STEP);
                }
			}
        });       
        
        // Grid view
        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setNumColumns(parameter.getGridX());
        adapter = new GridAdapter(this, grid);
        gridView.setAdapter(adapter);
        
        // Cell selection in the grid
        gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
			   
				if (currentVirus!=null) {
				
					// TODO : pb pour les grilles non carrée
			        int xPosition =  (int) Math.floor(position/(grid.getGridY()));
			        int yPosition = position - (xPosition*(grid.getGridY()));
			        Cell cell = cycle.getGrid().getCell(xPosition, yPosition);
					
			        // TODO Test virus
			        cell.setStatus(currentVirus.getEffect());
		        
			        // TODO 
			        cell.setVirus(currentVirus);
			        
				}
				
				
			}
		});
 
        
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
        
        virusA = (ImageButton) findViewById(R.id.virusA);
        virusB = (ImageButton) findViewById(R.id.virusB);
        virusC = (ImageButton) findViewById(R.id.virusC);
        virusD = (ImageButton) findViewById(R.id.virusD);
        
        virusA.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		selectVirus(aVirus);
        	}
        });
        virusB.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		selectVirus(bVirus);
        	}
        });
        virusC.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		selectVirus(cVirus);
        	}
        });
        virusD.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		selectVirus(dVirus);
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
	 * Virus selection
	 * @param virusName
	 */
	private void selectVirus(Virus virus) {
		Toast.makeText(this, virus.getName(), Toast.LENGTH_SHORT ).show();
		currentVirus = virus;
	}
	
	private void initVirus() {
		aVirus = new Virus(getString(R.string.virus_A), 1, 1, Cell.CEL_EMPTY);
		bVirus = new Virus(getString(R.string.virus_B), 1, 1, Cell.CEL_EMPTY);
		cVirus = new Virus(getString(R.string.virus_C), 1, 1, Cell.CEL_EMPTY);
		dVirus = new Virus(getString(R.string.virus_D), 1, 1, Cell.CEL_IN_LIFE);
		currentVirus = null;
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
    
    @Override
    public void onStop() {
    	super.onStop();
    	if (playCycleTask!=null) {
    		playCycleTask.cancel(true);
    		playCycleTask.removeListener(this);
    	} 
    }

    
}
