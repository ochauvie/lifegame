package com.lifegame.activity;


import com.lifegame.service.IPlayCycleService;

import com.lifegame.R;
import com.lifegame.adapter.GridAdapter;
import com.lifegame.model.Cycle;
import com.lifegame.model.Grid;
import com.lifegame.model.Mode;
import com.lifegame.model.Parameter;
import com.lifegame.model.Turn;
import com.lifegame.service.PlayCycleService;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
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
	private Parameter parameter;
	
	// Play objects
	private GridAdapter adapter;
	private Grid grid;
	private Cycle cycle;
	private IPlayCycleService iPlayCycleService;
	private boolean boundPlayCycleService;

	// Service connection
	ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceDisconnected(ComponentName name) {
        	iPlayCycleService = null;
        	boundPlayCycleService = false;
        }
        public void onServiceConnected(ComponentName name, IBinder service) {
        	iPlayCycleService = IPlayCycleService.Stub.asInterface(service);
        	boundPlayCycleService = true;
        }
    };
	
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
		cycle = new Cycle(parameter.getMode(), grid, new Turn());
		
        // Start service
     	Intent intentService = new Intent(this, PlayCycleService.class);
     	intentService.putExtra("cycle", cycle);
     	startService(intentService);
		
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
        		
        		// Mode step by step
        		if (Mode.MODE_STEP.equals(parameter.getMode().getMode())) {
        			if (cycle.getTurn().getStep()==Turn.STEP_LIFE) {
        				
        				if(boundPlayCycleService) {
                            try {
                            	cycle = iPlayCycleService.playStepLife();
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }                   
                        }
        				adapter = new GridAdapter(StartActivity.this, cycle.getGrid());
        				gridView.setAdapter(adapter);
        				adapter.notifyDataSetChanged();
        				statView.setText(getString(R.string.cell_dead) + ": " + cycle.getGrid().getCellsDead() + " - " +
	              		                 getString(R.string.cell_new) + ": " + cycle.getGrid().getCellsNew());
	            	} else if (cycle.getTurn().getStep()==Turn.STEP_MAJ) {
	            		if(boundPlayCycleService) {
                            try {
                            	cycle = iPlayCycleService.playStepUpdateGrid();
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }                   
                        }
	                	adapter = new GridAdapter(StartActivity.this, cycle.getGrid());
	                	gridView.setAdapter(adapter);
	            		adapter.notifyDataSetChanged();
	                	turnView.setText(getString(R.string.turn) + " " + cycle.getTurn().getTurn());
	                	statView.setText(getString(R.string.cell_in_life) + ": " + cycle.getGrid().getCellsInLife());
	            	}
        			
        		
        			// Mode auto	
        		} else if (Mode.MODE_AUTO.equals(parameter.getMode().getMode() )) {
        			int startCycle = cycle.getTurn().getTurn(); 
        			while (cycle.getTurn().getTurn()<(10+startCycle) && cycle.getGrid().getCellsInLife()>0) {  // TODO
	        			
            	        if(boundPlayCycleService) {
	                        try {
	                        	cycle = iPlayCycleService.playFullTurn();
	                        } catch (RemoteException e) {
	                            e.printStackTrace();
	                        }                   
	                    }
	    				adapter = new GridAdapter(StartActivity.this, cycle.getGrid());
	    				gridView.setAdapter(adapter);
	    				adapter.notifyDataSetChanged();
	    				turnView.setText(getString(R.string.turn) + " " + cycle.getTurn().getTurn());
	                	statView.setText(getString(R.string.cell_in_life) + ": " + cycle.getGrid().getCellsInLife());
	                           
        			}
        			
        		}
        		
        	}
        });    
        
        
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
    protected void onStart() {
        super.onStart();
        
        // Start play cycle service
     	Intent intent = new Intent(StartActivity.this, PlayCycleService.class);
     	intent.putExtra("cycle", cycle);
   		bindService(intent, mConnection, BIND_AUTO_CREATE);
    }
    
    @Override
    public void onStop() {
    	super.onStop();
        if(boundPlayCycleService) {
            unbindService(mConnection);
            boundPlayCycleService = false;
        }
        Intent intentService = new Intent(StartActivity.this, PlayCycleService.class);
		stopService(intentService);
    }
}
