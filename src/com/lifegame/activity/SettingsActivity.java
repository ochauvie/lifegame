package com.lifegame.activity;


import inputFilter.InputFilterMinMax;

import com.lifegame.R;
import com.lifegame.model.Grid;
import com.lifegame.model.Mode;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.text.InputFilter;
import android.view.View;

public class SettingsActivity extends Activity {
	
	private EditText line, column, density;
	private CheckBox checkBoxAuto;
	private ImageButton save;
	private int gridX = Grid.INITX;
	private int gridY = Grid.INITY;
	private int initDensity = Grid.INITDensity;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        
        line = (EditText) findViewById(R.id.line);
        column = (EditText) findViewById(R.id.column);
        density = (EditText) findViewById(R.id.density);
        
        line.setFilters(new InputFilter[]{ new InputFilterMinMax(Grid.MINY, Grid.MAXY)});
        column.setFilters(new InputFilter[]{ new InputFilterMinMax(Grid.MINX, Grid.MAXX)});
        density.setFilters(new InputFilter[]{ new InputFilterMinMax(Grid.MINDensity, Grid.MAXDensity)});
        
        checkBoxAuto = (CheckBox) findViewById(R.id.checkBoxAuto);
        checkBoxAuto.setChecked(false);
        
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
        	gridX = bundle.getInt("gridX");
        	gridY = bundle.getInt("gridY");
        	initDensity = bundle.getInt("initDensity");
        }
        
        line.setText(String.valueOf(gridY));
        column.setText(String.valueOf(gridX));
        density.setText(String.valueOf(initDensity));
        
        // Next turn button
        save = (ImageButton) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		if (line.getText().length()>0 && column.getText().length()>0 && density.getText().length()>0) { 
	        		Intent myIntent = new Intent(SettingsActivity.this, StartActivity.class);
	                myIntent.putExtra("gridY", line.getText().toString());
	                myIntent.putExtra("gridX", column.getText().toString());
	                myIntent.putExtra("initDensity", density.getText().toString());
	                if (checkBoxAuto.isChecked()) {
	                	myIntent.putExtra(Mode.MODE, Mode.MODE_AUTO);
	                } else {
	                	myIntent.putExtra(Mode.MODE, Mode.MODE_STEP);
	                }
	                startActivity(myIntent);
	                finish();
        		}
        	}
        });    
        
                
    }
	
}
