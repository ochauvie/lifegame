package com.lifegame.activity;



import com.lifegame.R;
import com.lifegame.inputFilter.InputFilterMinMax;
import com.lifegame.model.Mode;
import com.lifegame.model.Parameter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.text.InputFilter;
import android.view.View;

public class SettingsActivity extends Activity {
	
	private EditText line, column, density, sleep;
	private CheckBox checkBoxAuto;
	private ImageButton save;
	private Parameter parameter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        
        line = (EditText) findViewById(R.id.line);
        column = (EditText) findViewById(R.id.column);
        density = (EditText) findViewById(R.id.density);
        sleep = (EditText) findViewById(R.id.sleep);
        
        line.setFilters(new InputFilter[]{ new InputFilterMinMax(Parameter.MINY, Parameter.MAXY)});
        column.setFilters(new InputFilter[]{ new InputFilterMinMax(Parameter.MINX, Parameter.MAXX)});
        density.setFilters(new InputFilter[]{ new InputFilterMinMax(Parameter.MINDensity, Parameter.MAXDensity)});
        density.setFilters(new InputFilter[]{ new InputFilterMinMax(Parameter.MINSleep, Parameter.MAXSleep)});
        checkBoxAuto = (CheckBox) findViewById(R.id.checkBoxAuto);
        
        parameter = new Parameter();
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
        	parameter = getIntent().getExtras().getParcelable("parameter");
        }
        
        line.setText(String.valueOf(parameter.getGridY()));
        column.setText(String.valueOf(parameter.getGridX()));
        density.setText(String.valueOf(parameter.getGridDensity()));
        sleep.setText(String.valueOf(parameter.getTurnSleep()));
        if (Mode.MODE_AUTO.equals(parameter.getMode().getMode())) {
        	checkBoxAuto.setChecked(true);
        } else {
        	checkBoxAuto.setChecked(false);
        }
          
        
        // Next turn button
        save = (ImageButton) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		if (line.getText().length()>0 && column.getText().length()>0 && density.getText().length()>0) { 
	        		Intent myIntent = new Intent(SettingsActivity.this, StartActivity.class);
	        		parameter.setGridY(Integer.valueOf(line.getText().toString()));
	        		parameter.setGridX(Integer.valueOf(column.getText().toString()));
	        		parameter.setGridDensity(Integer.valueOf(density.getText().toString()));
	        		parameter.setTurnSleep(Integer.valueOf(sleep.getText().toString()));
	        		if (checkBoxAuto.isChecked()) {
	                	myIntent.putExtra(Mode.MODE, Mode.MODE_AUTO);
	                	parameter.setMode(new Mode(Mode.MODE_AUTO));
	                } else {
	                	parameter.setMode(new Mode(Mode.MODE_STEP));
	                }
	                myIntent.putExtra("parameter", parameter);
	                startActivity(myIntent);
	                finish();
        		}
        	}
        });    
        
                
    }
	
}
