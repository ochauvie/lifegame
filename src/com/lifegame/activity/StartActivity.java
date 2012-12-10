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
import android.graphics.Color;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class StartActivity extends Activity implements IPlayCycleListener{
	
	// View elements
	private GridView gridView;
	private TextView turnView, stat1View, stat2View;
	private ImageButton nextTurn;
	private ImageButton virusA, virusB, virusC, virusD, virusE;
	private CheckBox checkBoxAuto;
	private Animation virusAnimation;
	
	// Default parameters
	private Parameter parameter;
	
	// Play objects
	private GridAdapter adapter;
	private Grid grid;
	private Cycle cycle;
	private Virus currentVirus;
	private Virus aVirus, bVirus, cVirus, dVirus, eVirus;
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
		
		// Animation
		virusAnimation = AnimationUtils.loadAnimation(this,R.anim.virus_selection);
		
        // Text view
        turnView = (TextView) findViewById(R.id.textViewTurn);
        turnView.setText(getString(R.string.turn) + " " + cycle.getTurn().getTurn());
        
        // Text stat
        stat1View = (TextView) findViewById(R.id.textViewStat1);
        stat2View = (TextView) findViewById(R.id.textViewStat2);
        if (parameter.getNbPlayer()>1) {
			stat1View.setText(getString(R.string.cell_in_life) + ": " + cycle.getGrid().getCellsInLifePlayer1());
			stat1View.setTextColor(Color.GREEN);
			stat2View.setText(getString(R.string.cell_in_life) + ": " + cycle.getGrid().getCellsInLifePlayer2());
			stat2View.setTextColor(Color.MAGENTA);
		} else {
			stat1View.setText(getString(R.string.cell_in_life) + ": " + cycle.getGrid().getCellsInLife());
			stat2View.setText("");
		}
        
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
        gridView.setNumColumns(parameter.getGridY());	// Number of columns
        adapter = new GridAdapter(StartActivity.this, cycle.getGrid());
        gridView.setAdapter(adapter);
        
        
        // Cell selection in the grid
        gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
			   
				if (currentVirus!=null) {
				
					// Get cell by position
			        int xPosition =  (int) Math.floor(position/(grid.getGridY()));
			        int yPosition = position - (xPosition*(grid.getGridY()));
			        Cell cell = cycle.getGrid().getCell(xPosition, yPosition);
					
			        // Virus injection
			        Virus infection = new Virus(currentVirus.getId(), currentVirus.getName(), currentVirus.getRange(), currentVirus.getDuration(), currentVirus.getEffect());
			        cell.setVirus(infection);
			        
			        // TODO gerer un nombre de virus (pharmacy)
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
        
        // Virus
        virusA = (ImageButton) findViewById(R.id.virusA);
        virusB = (ImageButton) findViewById(R.id.virusB);
        virusC = (ImageButton) findViewById(R.id.virusC);
        virusD = (ImageButton) findViewById(R.id.virusD);
        virusE = (ImageButton) findViewById(R.id.virusE);
        
        virusA.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		selectVirus(virusA, aVirus);
        	}
        });
        virusB.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		selectVirus(virusB, bVirus);
        	}
        });
        virusC.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		selectVirus(virusC, cVirus);
        	}
        });
        virusD.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		selectVirus(virusD, dVirus);
        	}
        });
        virusE.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		selectVirus(virusE, eVirus);
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
			if (parameter.getNbPlayer()>1) {
				stat1View.setText(getString(R.string.cell_in_life) + ": " + cycle.getGrid().getCellsInLifePlayer1());
				stat1View.setTextColor(Color.GREEN);
				stat2View.setText(getString(R.string.cell_in_life) + ": " + cycle.getGrid().getCellsInLifePlayer2());
				stat2View.setTextColor(Color.MAGENTA);
				
				if (cycle.getGrid().getCellsInLifePlayer1()==0 || cycle.getGrid().getCellsInLifePlayer2()==0) {
					stopToPlay();
				}
				
				
			} else {
				stat1View.setText(getString(R.string.cell_in_life) + ": " + cycle.getGrid().getCellsInLife());
				stat2View.setText("");
			}
		} else {
			if (cycle.getTurn().getStep()==Turn.STEP_VIRUS) {
				stat1View.setText(getString(R.string.virus));
			
			} else if (cycle.getTurn().getStep()==Turn.STEP_UPDATE) {
				stat1View.setText(getString(R.string.cell_dead) + ": " + cycle.getGrid().getCellsDead() + " - " +
					 getString(R.string.cell_new) + ": " + cycle.getGrid().getCellsNew());
			} else {
				if (parameter.getNbPlayer()>1) {
					stat1View.setText(getString(R.string.cell_in_life) + ": " + cycle.getGrid().getCellsInLifePlayer1());
					stat1View.setTextColor(Color.GREEN);
					stat2View.setText(getString(R.string.cell_in_life) + ": " + cycle.getGrid().getCellsInLifePlayer2());
					stat2View.setTextColor(Color.MAGENTA);
				} else {
					stat1View.setText(getString(R.string.cell_in_life) + ": " + cycle.getGrid().getCellsInLife());
					stat2View.setText("");
				}
			}
		}
	}
	
	/**
	 * Virus selection
	 * @param virusName
	 */
	private void selectVirus(ImageButton imageButton, Virus virus) {
		Toast.makeText(this, virus.getName(), Toast.LENGTH_SHORT ).show();
		currentVirus = virus;
		imageButton.clearAnimation();
		imageButton.startAnimation(virusAnimation);
	}
	
	private void initVirus() {
		aVirus = new Virus(Virus.VIRUS_ID_A, getString(R.string.virus_A), 2, 1, Cell.CEL_EMPTY);
		bVirus = new Virus(Virus.VIRUS_ID_B, getString(R.string.virus_B), 1, 2, Cell.CEL_EMPTY);
		cVirus = new Virus(Virus.VIRUS_ID_C, getString(R.string.virus_C), 3, 1, Cell.CEL_IN_LIFE);
		dVirus = new Virus(Virus.VIRUS_ID_D, getString(R.string.virus_D), 1, 3, Cell.CEL_IN_LIFE);
		eVirus = new Virus(Virus.VIRUS_ID_E, getString(R.string.virus_E), 1, 3, Cell.CEL_FROZEN);
		currentVirus = null;
	}
    
	/**
	 * Stop to play if a player win
	 */
	private void stopToPlay() {
		if (cycle.getMode().getMode().equals(Mode.MODE_AUTO)) {
			if (parameter.getNbPlayer()>1) {
				if (cycle.getGrid().getCellsInLifePlayer1()==0) {
					Toast.makeText(this, getString(R.string.playeur2_win), Toast.LENGTH_SHORT).show();
				} else if (cycle.getGrid().getCellsInLifePlayer2()==0) {
					Toast.makeText(this, getString(R.string.playeur1_win), Toast.LENGTH_SHORT).show();
				}
			}
			cycle.getMode().setMode(Mode.MODE_STEP);
			nextTurn.setImageResource(R.drawable.start);
			checkBoxAuto.setChecked(false);
			
			// Stop player task
			if (playCycleTask!=null) {
	    		playCycleTask.cancel(true);
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
    
    @Override
    public void onStop() {
    	super.onStop();
    	// End play task and remove listener
    	if (playCycleTask!=null) {
    		playCycleTask.cancel(true);
    		playCycleTask.removeListener(this);
    	} 
    }

    
}
